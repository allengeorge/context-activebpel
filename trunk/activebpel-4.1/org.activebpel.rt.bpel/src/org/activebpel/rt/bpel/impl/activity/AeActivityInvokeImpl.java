// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeActivityInvokeImpl.java,v 1.57 2007/08/29 15:45:3
/*
 * Copyright (c) 2004-2006 Active Endpoints, Inc.
 *
 * This program is licensed under the terms of the GNU General Public License
 * Version 2 (the "License") as published by the Free Software Foundation, and 
 * the ActiveBPEL Licensing Policies (the "Policies").  A copy of the License 
 * and the Policies were distributed with this program.  
 *
 * The License is available at:
 * http: *www.gnu.org/copyleft/gpl.html
 *
 * The Policies are available at:
 * http: *www.activebpel.org/licensing/index.html
 *
 * Unless required by applicable law or agreed to in writing, this program is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied.  See the License and the Policies
 * for specific language governing the use of this program.
 */
package org.activebpel.rt.bpel.impl.activity;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.IAeInvokeActivity;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.AePartnerLink;
import org.activebpel.rt.bpel.impl.AePartnerLinkOpImplKey;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.IAeAlarmReceiver;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.IAeMessageReceiver;
import org.activebpel.rt.bpel.impl.IAeProcessManager;
import org.activebpel.rt.bpel.impl.activity.support.AeInvokeRetryPolicy;
import org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeUtil;

/**
 * Implementation of the bpel invoke activity.
 */
public class AeActivityInvokeImpl extends AeWSIOActivityImpl implements IAeInvokeActivity, IAeMessageReceiver, IAeAlarmReceiver
{
   /** Boolean indicating that the invoke has been queued to be tried at a later time. */
   private boolean mQueued;

   /** contains the code and state for implementing retries for invokes */
   private AeInvokeRetryPolicy mRetryPolicy = new AeInvokeRetryPolicy(this);

   /**
    * Invoke transmission id used in durable invokes.
    */
   private long mTransmissionId = IAeTransmissionTracker.NULL_TRANSREC_ID;

   /**
    * Id of the engine where the invoke was executed.
    */
   private int mEngineId;

   /** Alarm execution instance reference id. */
   private int mAlarmId;

   /** Pending invoke journal entry id. */
   private long mJournalId = IAeProcessManager.NULL_JOURNAL_ID;

   /** default constructor for activity */
   public AeActivityInvokeImpl(AeActivityInvokeDef aActivityDef, IAeActivityParent aParent)
   {
      super(aActivityDef, aParent);
      resetTransmissionId();
      setAlarmId(-1);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept( IAeImplVisitor aVisitor ) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      // if we are executing in a loop make sure to clear the state of retries
      setRetries(0);
      setQueued(false);

      // Set the engine id. This id will be reset when state is final.
      setEngineId( getProcess().getEngine().getEngineId() );

      // Journal the pending invoke.
      long journalId = getProcess().getEngine().getProcessManager().journalInvokePending(getProcess().getProcessId(), getLocationId());
      setJournalId(journalId);

      // Set the next non-durable/persistent transmission id if one has not been assigned already.
      // This id is used to match up the invoke when the invoke message data or faulted return is
      // dispatched to the activity via the engine. Per defect 1852, if the ref id associated with
      // the response data/fault does not match the current id, then the response data is 
      // dropped/ignored.
      //
      // A new id is assigned during execution (i.e. here) only if one has not been already
      // assigned. The only time you should see a id (id > 0) "pre-assigned" at this
      // point is during playback of durable invoke journals or during state restoration.

      // Assign invoke/tx id iff current tx id is null.
      if (getTransmissionId() == IAeTransmissionTracker.NULL_TRANSREC_ID)
      {
         // Assign a new  non-durable/non-persistent transmission id since the invoke does not have a valid xmt id
         // i.e. this is not a playback of a durable invoke journal. (journaled invokes would already
         // have a (positive) transmission id set via journal playback).
         setTransmissionId(  getProcess().getNextInvokeId() );
      }

      // Note: The transmission-id used in durable invokes is reset on the onMessage(), onFault() and terminate()
      // callbacks i.e. once a response to the invoke has been received. This id should not
      // be reset here because in failover/recovery scenarios, the invoke activity will be
      // re-executed by the engine, at which point the durable invoke should use the previous
      // transmission id.

      // get the variable we're working with (if there is one)
      IAeMessageData inputMessage = getInputMessageData();

      // initiates any correlation sets that are defined to initiate with OUTBOUND data
      if (getRequestCorrelations() != null)
         getRequestCorrelations().initiateOrValidate(inputMessage, getDef().getProducerMessagePartsMap());

      AePartnerLink plink = findPartnerLink(getDef().getPartnerLink());
      // Queue our invocation of the partner operation we will be called back when it is done
      // note that we may be called back via onMessage or onFault before this returns
      getProcess().queueInvoke(this,
                               inputMessage,
                               plink,
                               new AePartnerLinkOpImplKey(plink, getDef().getOperation()));
   }

   /**
    * Overrides method to reset transmission id.
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#terminate()
    */
   public void terminate() throws AeBusinessProcessException
   {
      resetTransmissionId();
      super.terminate();
   }

   /**
    * Gets the data for the invoke. If there is a variable defined but not initialized
    * then you'll get an exception.
    */
   private IAeMessageData getInputMessageData() throws AeBusinessProcessException
   {
      IAeMessageData inputMessage = getMessageDataProducer().produceMessageData();

      getMessageValidator().validate(getProcess(), inputMessage, getDef().getProducerMessagePartsMap());

      return inputMessage;
   }

   /**
    * Convenience method to avoid having to cast the definition object in order
    * to access methods provided by invoke def.
    */
   protected AeActivityInvokeDef getDef()
   {
      AeActivityInvokeDef def = (AeActivityInvokeDef)getDefinition();
      return def;
   }

   /**
    * Handle the receipt of our output message.
    * @see org.activebpel.rt.bpel.impl.IAeMessageReceiver#onMessage(org.activebpel.rt.message.IAeMessageData)
    */
   public void onMessage(IAeMessageData aMessage) throws AeBusinessProcessException
   {
      if (getState().isFinal())
      {
         return;
      }
      if (aMessage != null)
      {
         getMessageValidator().validate(getProcess(), aMessage, getDef().getConsumerMessagePartsMap());

         // initiates any correlation sets that are defined to initiate with INBOUND data
         if (getResponseCorrelations() != null)
            getResponseCorrelations().initiateOrValidate(aMessage, getDef().getConsumerMessagePartsMap());

         getMessageDataConsumer().consumeMessageData(aMessage);
      }

      // we are done
      objectCompleted();
   }

   /**
    * Convenience method for getting the output variable. If none is defined, then
    * we return null
    */
   public IAeVariable getOutputVariable()
   {
      if (AeUtil.notNullOrEmpty(getDef().getOutputVariable()))
         return findVariable(getDef().getOutputVariable());
      else
         return null;
   }

   /**
    * Handle the receipt of a fault from our invoke.
    * @see org.activebpel.rt.bpel.impl.IAeMessageReceiver#onFault(org.activebpel.rt.bpel.IAeFault)
    */
   public void onFault(IAeFault aFault) throws AeBusinessProcessException
   {
      if (!getState().isFinal())
      {
         if(! getRetryPolicy().reschedule(aFault))
         {
            objectCompletedWithFault(aFault);
         }
      }
   }

   /**
    * @see org.activebpel.rt.bpel.IAeInvokeActivity#isOneWay()
    */
   public boolean isOneWay()
   {
      return getDef().getConsumerMessagePartsMap() == null;
   }

   /**
    * Implements method by dispatching an invoke retry after marking ourselves as dequeued
    * and incrementing the count of times we have attempted retries.
    * @see org.activebpel.rt.bpel.impl.IAeAlarmReceiver#onAlarm()
    */
   public void onAlarm() throws AeBusinessProcessException
   {
      // if for some reason we are not queued then this alarm is in error
      if(isQueued())
      {
         // TODO (ck) add an info event to log that we are retrying
         setQueued(false);
         setRetries(getRetries() + 1);

         // retry the invoke...

         AePartnerLink plink = findPartnerLink(getDef().getPartnerLink());
         // Queue our invocation of the partner operation we will be called back when it is done
         // note that we may be called back via onMessage or onFault before this returns
         getProcess().queueInvoke(this, getInputMessageData(), plink,
               new AePartnerLinkOpImplKey(plink, getDef().getOperation()));
      }
   }

   /**
    * Overrides method to extend base in order to reset the activity and dequeue
    * any queued alarm. We have to override both this method and
    * {@link #setFaultedState(IAeFault)} in order to catch all transitions to
    * final states.
    *
    * @see org.activebpel.rt.bpel.impl.activity.AeActivityImpl#setState(org.activebpel.rt.bpel.impl.AeBpelState)
    */
   public void setState(AeBpelState aNewState) throws AeBusinessProcessException
   {
      if (aNewState.isFinal())
      {
         reset();
      }
      dequeue();
      super.setState(aNewState);
   }

   /**
    * Overrides method to extend base in order to reset the activity and dequeue
    * any queued alarm. We have to override both this method and
    * {@link #setState(AeBpelState)} in order to catch all transitions to final
    * states.
    *
    * @see org.activebpel.rt.bpel.impl.activity.AeActivityImpl#setFaultedState(org.activebpel.rt.bpel.IAeFault)
    */
   public void setFaultedState(IAeFault aFault) throws AeBusinessProcessException
   {
      reset();
      super.setFaultedState(aFault);
   }

   /**
    * Resets the state of this activity.
    */
   protected void reset() throws AeBusinessProcessException
   {
      // reset durable invoke data.
      resetTransmissionId();
      // reset engine id
      resetEngineId();
      // reset journal id
      resetJournalId();

      dequeue();
   }

   /**
    * Dequeues the alarm from the process.
    */
   protected void dequeue() throws AeBusinessProcessException
   {
      if (isQueued())
      {
         getProcess().dequeueAlarm(this);
         setQueued(false);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeAlarmReceiver#getGroupId()
    */
   public int getGroupId()
   {
      return getLocationId();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeAlarmReceiver#isQueued()
    */
   public boolean isQueued()
   {
      return mQueued;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeAlarmReceiver#isQueued()
    */
   public void setQueued(boolean aQueued)
   {
      mQueued = aQueued;
   }

   /**
    * @return Returns the retries.
    */
   public int getRetries()
   {
      return getRetryPolicy().getRetries();
   }

   /**
    * @param aRetries The retries to set.
    */
   public void setRetries(int aRetries)
   {
      getRetryPolicy().setRetries(aRetries);
   }

   /**
    * @return Returns the transmission Id.
    */
   public long getTransmissionId()
   {
      return mTransmissionId;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeInvokeActivity#setTransmissionId(long)
    */
   public void setTransmissionId(long aTransmissionId)
   {
      mTransmissionId = aTransmissionId;
   }

   /**
    * Resets the transmission information and prepare for the next invoke execution.
    */
   private void resetTransmissionId()
   {
      // if the previous tx id is persistent/durable (ie. a positive number), then add it to
      // current invoke tx id collection so that the corresponding entries in the transmision
      // tracker can be deleted.
      if (getTransmissionId() > IAeTransmissionTracker.NULL_TRANSREC_ID)
      {
         getProcess().getEngine().getProcessManager().transmissionIdDone(getProcess().getProcessId(), getTransmissionId() );
      }
      setTransmissionId(IAeTransmissionTracker.NULL_TRANSREC_ID);
   }

   /**
    * @return Returns the engineId.
    */
   public int getEngineId()
   {
      return mEngineId;
   }

   /**
    * @param aEngineId The engineId to set.
    */
   public void setEngineId(int aEngineId)
   {
      mEngineId = aEngineId;
   }

   /**
    * Resets the engine id.
    */
   private void resetEngineId()
   {
      setEngineId(IAeBusinessProcessEngineInternal.NULL_ENGINE_ID);
   }

   /**
    * Getter for the retry policy
    */
   protected AeInvokeRetryPolicy getRetryPolicy()
   {
      return mRetryPolicy;
   }

   /**
    * @return Returns the alarmId.
    */
   public int getAlarmId()
   {
      return mAlarmId;
   }

   /**
    * @param aAlarmId The alarmId to set.
    */
   public void setAlarmId(int aAlarmId)
   {
      mAlarmId = aAlarmId;
   }

   /**
    * Returns the pending invoke journal entry id.
    */
   public long getJournalId()
   {
      return mJournalId;
   }

   /**
    * Sets the pending invoke journal entry id.
    */
   public void setJournalId(long aJournalId)
   {
      mJournalId = aJournalId;
   }

   /**
    * Marks the pending invoke journal entry done and resets the pending invoke
    * journal entry id.
    */
   protected void resetJournalId()
   {
      if (getJournalId() != IAeProcessManager.NULL_JOURNAL_ID)
      {
         getProcess().getEngine().getProcessManager().journalEntryDone(getProcess().getProcessId(), getJournalId());
      }

      setJournalId(IAeProcessManager.NULL_JOURNAL_ID);
   }
}
