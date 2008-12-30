// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/AeRecoveryQueueManager.java,v 1.20 2007/07/09 16:28:4
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
package org.activebpel.rt.bpel.server.engine.recovery;

import commonj.timers.Timer;
import commonj.timers.TimerListener;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.coord.AeCoordinationDetail;
import org.activebpel.rt.bpel.coord.AeCoordinationException;
import org.activebpel.rt.bpel.coord.IAeCoordinator;
import org.activebpel.rt.bpel.impl.*;
import org.activebpel.rt.bpel.impl.activity.AeActivityInvokeImpl;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.queue.AeAlarm;
import org.activebpel.rt.bpel.impl.queue.AeInboundReceive;
import org.activebpel.rt.bpel.impl.queue.AeMessageReceiver;
import org.activebpel.rt.bpel.impl.queue.AeReply;
import org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.receive.AeDefaultReceiveHandler;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeInvokeTransmittedJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.recovered.*;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.wsio.IAeMessageAcknowledgeCallback;

import java.util.*;

/**
 * Implements a queue manager for recovery. Extends {@link AeBaseQueueManager}
 * to leverage its functionality for matching inbound receives to message
 * receivers.
 */
public class AeRecoveryQueueManager extends AeBaseQueueManager implements IAeRecoveryQueueManager
{
   /** The set of alarm and queue manager items generated during recovery. */
   private IAeRecoveredItemsSet mRecoveredItemsSet;

   /** The process that is being recovered. */
   private IAeBusinessProcess mRecoveryProcess;

   /** The sent replies. These are the replies that we recorded the process to have sent. */
   private List mSentReplies;
   
   /** The BPEL receive handler */
   private AeDefaultReceiveHandler mReceiveHandler = new AeDefaultReceiveHandler();

   /**
    * The invoke transmitted journal entries. These entries associate invoke
    * location ids with transmission ids.
    */
   private List mInvokeTransmittedEntries;

   /**
    * Constructs a queue manager for recovery.
    */
   public AeRecoveryQueueManager()
   {
      super(Collections.EMPTY_MAP);
   }

   /**
    * Verifies that the given process id matches the process id of the process
    * being recovered. Throws {@link java.lang.IllegalStateException} if the
    * process id does not match.
    *
    * @param aProcessId
    */
   protected void checkProcessId(long aProcessId)
   {
      if (aProcessId != getRecoveryProcess().getProcessId())
      {
         throw new IllegalStateException(AeMessages.format("AeRecoveryQueueManager.ERROR_0", //$NON-NLS-1$
                                                           new Object[] { new Long(aProcessId), new Long(getRecoveryProcess().getProcessId()) }));
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryQueueManager#getRecoveredItemsSet()
    */
   public IAeRecoveredItemsSet getRecoveredItemsSet()
   {
      return mRecoveredItemsSet;
   }

   /**
    * Returns the process that is being recovered.
    */
   protected IAeBusinessProcess getRecoveryProcess()
   {
      return mRecoveryProcess;
   }

   /**
    * Returns the sent replies.
    */
   protected List getSentReplies()
   {
      return mSentReplies;
   }

   /**
    * Returns the invoke transmitted journal entries.
    */
   protected List getInvokeTransmittedEntries()
   {
      return mInvokeTransmittedEntries;
   }

   /**
    * Removes the next invoke transmitted journal entry for the given location
    * id. If such a journal entry exists, then returns the associated
    * transmission id; otherwise, returns
    * {@link IAeTransmissionTracker#NULL_TRANSREC_ID}.
    *
    * @param aLocationId
    */
   protected long removeInvokeTransmittedEntry(int aLocationId) throws AeBusinessProcessException
   {
      for (Iterator i = getInvokeTransmittedEntries().iterator(); i.hasNext(); )
      {
         AeInvokeTransmittedJournalEntry entry = (AeInvokeTransmittedJournalEntry) i.next();
         if (entry.getLocationId() == aLocationId)
         {
            // Remove this entry.
            i.remove();

            // Return its transmission id.
            return entry.getTransmissionId();
         }
      }

      return IAeTransmissionTracker.NULL_TRANSREC_ID;
   }

   /**
    * Removes the next item in the list of sent replies that matches the given
    * reply object. Returns <code>true</code> if and only if there was a
    * matching item.
    *
    * @param aReply
    */
   protected boolean removeSentReply(AeReply aReply)
   {
      return getSentReplies().remove(aReply);
   }

   /**
    * Restores the coordination id for a subprocess invoke to the invoke's
    * enclosing scope.
    *
    * @param aProcessId
    * @param aLocationId
    */
   protected void restoreInvokeCoordinationId(long aProcessId, int aLocationId)
   {
      try
      {
         IAeBusinessProcess process = getEngine().getProcessManager().getProcess(aProcessId);
         AeActivityInvokeImpl impl = (AeActivityInvokeImpl) process.findBpelObject(aLocationId);
     
         // Get all the coordinations owned by this process.
         List list = getEngine().getCoordinationManager().getParticipantDetail(aProcessId);

         // Find the coordination for this invoke location, if any.
         for (Iterator i = list.iterator(); i.hasNext(); )
         {
            AeCoordinationDetail detail = (AeCoordinationDetail) i.next();
            String coordinationId = detail.getCoordinationId();
            IAeCoordinator coordinator = getEngine().getCoordinationManager().getCoordinator(coordinationId);

            if (AeUtil.compareObjects(coordinator.getLocationPath(), impl.getLocationPath()))
            {
               AeActivityScopeImpl scope = impl.findEnclosingScope();

               // Add the coordination id to the scope's coordination container.
               scope.getCoordinationContainer().registerCoordinationId(coordinationId);
               break;
            }
         }
      }
      catch (AeBusinessProcessException ignore)
      {
         // If getProcess() or findBpelObject() fail, then ignore this exception
         // and we just won't restore the coordination id.
      }
      catch (AeCoordinationException ignore)
      {
         // If this is not being coordinated or the coordination is already
         // registered, then ignore this exception.
      }
   }

   /*======================================================================
    * org.activebpel.rt.bpel.impl.AeBaseQueueManager methods
    *======================================================================
    */

   /**
    * Overrides method to throw {@link IllegalStateException}.
    *
    * @see org.activebpel.rt.bpel.impl.AeBaseQueueManager#createAlarmListener(org.activebpel.rt.bpel.impl.queue.AeAlarm)
    */
   protected TimerListener createAlarmListener(AeAlarm aAlarm)
   {
      // Recovery version does nothing - this method is only called by scheduleAlarm in the
      // base queue manager.  The recovery queue manager overrides scheduleAlarm and so this is 
      // never called.  It is abstract, however, so must be implemented here.
      throw new IllegalStateException(AeMessages.getString("AeRecoveryQueueManager.ERROR_1")); //$NON-NLS-1$
   }

   /**
    * Overrides method to throw {@link IllegalStateException}.
    *
    * @see org.activebpel.rt.bpel.impl.AeBaseQueueManager#getInvokes()
    */
   protected List getInvokes()
   {
      // This is basically an assertion that we are not using this portion of
      // AeBaseQueueManager.
      throw new IllegalStateException(AeMessages.getString("AeRecoveryQueueManager.ERROR_1")); //$NON-NLS-1$
   }

   /**
    * Overrides method to throw {@link IllegalStateException}.
    *
    * @see org.activebpel.rt.bpel.impl.AeBaseQueueManager#getReplies()
    */
   protected Map getReplies()
   {
      // This is basically an assertion that we are not using this portion of
      // AeBaseQueueManager.
      throw new IllegalStateException(AeMessages.getString("AeRecoveryQueueManager.ERROR_1")); //$NON-NLS-1$
   }

   /**
    * Overrides method to throw {@link IllegalStateException}.
    *
    * @see org.activebpel.rt.bpel.impl.AeBaseQueueManager#schedule(commonj.timers.TimerListener, java.util.Date)
    */
   protected Timer schedule(TimerListener aListener, Date aDeadline)
   {
      // Recovery version does nothing - this method is only called by scheduleAlarm in the
      // base queue manager.  The recovery queue manager overrides scheduleAlarm and so this is 
      // never called.  It is abstract, however, so must be implemented here.
      throw new IllegalStateException(AeMessages.getString("AeRecoveryQueueManager.ERROR_1")); //$NON-NLS-1$
   }

   /*======================================================================
    * org.activebpel.rt.bpel.impl.IAeQueueManager methods
    *======================================================================
    */

   /**
    * @see org.activebpel.rt.bpel.impl.IAeQueueManager#addInvoke(org.activebpel.rt.bpel.impl.IAeProcessPlan, org.activebpel.rt.bpel.impl.IAeInvokeInternal) 
    */
   public void addInvoke(IAeProcessPlan aProcessPlan, IAeInvokeInternal aInvokeQueueObject) throws AeBusinessProcessException
   {
      long processId = aInvokeQueueObject.getProcessId();
      checkProcessId(processId);

      // If we journaled a transmission id for this invoke queue object, then
      // restore that transmission id now.
      int locationId = aInvokeQueueObject.getLocationId();
      long transmissionId = removeInvokeTransmittedEntry(locationId);
      if (transmissionId != IAeTransmissionTracker.NULL_TRANSREC_ID)
      {
         aInvokeQueueObject.setRecoveredTransmissionId(transmissionId);

         // Restore coordination id to invoke's enclosing scope.
         restoreInvokeCoordinationId(processId, locationId);
      }

      IAeRecoveredItem addInvokeItem = new AeRecoveredAddInvokeItem(aInvokeQueueObject, aProcessPlan);
      try
      {
         getRecoveredItemsSet().addRecoveredItem(addInvokeItem);
      }
      catch (AeRecoveryConflictingRequestException e)
      {
         throw new AeConflictingRequestException(getRecoveryProcess().getBPELNamespace());
      }
   }

   public void addSubscribe(IAeProcessPlan aPlan, IAagSubscribeInvokeInternal aSubsInvokeQueueObject) throws AeBusinessProcessException {
      throw new UnsupportedOperationException("addSubscribe is not supported by the Recovery Queue Manager");
   }
   
   public void addConsumerFactoryInvoke(IAeProcessPlan aPlan, IAagConsumerFactoryInvokeInternal aCreateConsumerQueueObject) throws AeBusinessProcessException
   {
      throw new UnsupportedOperationException("addConsumerFactoryInvoke is not supported by the Recovery Queue Manager");
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeQueueManager#addMessageReceiver(org.activebpel.rt.bpel.impl.queue.AeMessageReceiver)
    */
   public void addMessageReceiver(AeMessageReceiver aMessageReceiver) throws AeBusinessProcessException
   {
      checkProcessId(aMessageReceiver.getProcessId());

      // Add the message receiver to AeBaseQueueManager's internal structure
      // for matchInboundReceive().
      super.addMessageReceiver(aMessageReceiver);

      // Add a recovered item to add the message receiver to the "real" queue
      // manager.
      IAeRecoveredItem addReceiverItem = new AeRecoveredAddReceiverItem(aMessageReceiver);
      try
      {
         getRecoveredItemsSet().addRecoveredItem(addReceiverItem);
      }
      catch (AeRecoveryConflictingRequestException e)
      {
         throw new AeConflictingRequestException(getRecoveryProcess().getBPELNamespace());
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeQueueManager#addNonDurableReply(org.activebpel.rt.bpel.impl.queue.AeReply, org.activebpel.rt.bpel.impl.queue.AeMessageReceiver)
    */
   public void addNonDurableReply(AeReply aReply, AeMessageReceiver aMessageReceiver) throws AeConflictingRequestException
   {
      checkProcessId(aReply.getProcessId());

      IAeRecoveredItem addReplyItem = new AeRecoveredAddReplyItem(aReply, aMessageReceiver);
      try
      {
         getRecoveredItemsSet().addRecoveredItem(addReplyItem);
      }
      catch (AeRecoveryConflictingRequestException e)
      {
         throw new AeConflictingRequestException(getRecoveryProcess().getBPELNamespace());
      }
   }

   /**
    * Overrides method simply to comment on the implementation.
    * 
    * @see org.activebpel.rt.bpel.impl.IAeQueueManager#matchInboundReceive(org.activebpel.rt.bpel.impl.queue.AeInboundReceive, org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver, boolean)
    */
   public AeMessageReceiver matchInboundReceive(AeInboundReceive aInboundReceive, boolean aQueueIfNotFoundFlag, IAeMessageAcknowledgeCallback aAckCallback) throws AeCorrelationViolationException, AeConflictingRequestException, AeBusinessProcessException
   {
      // The base implementation calls removeAlarmsInGroup if there is a match.
      // We will ignore that call and depend instead on the alarm dequeuing
      // itself directly. In other words, we will wait for the call to
      // removeAlarm to manage the recovered item for the alarm.
      return super.matchInboundReceive(aInboundReceive, aQueueIfNotFoundFlag, aAckCallback);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeQueueManager#removeAlarm(long, int, int)
    */
   public boolean removeAlarm(long aProcessId, int aLocationId, int aAlarmId) throws AeBusinessProcessException
   {
      checkProcessId(aProcessId);

      // Remove the schedule alarm item (if there is one).
      IAeRecoveredItem scheduleAlarmItem = new AeRecoveredScheduleAlarmItem(aLocationId, aAlarmId);
      getRecoveredItemsSet().removeRecoveredItem(scheduleAlarmItem);
      return true;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeQueueManager#removeAlarmForDispatch(long, int, int, int)
    */
   public long removeAlarmForDispatch(long aProcessId, int aGroupId, int aLocationId, int aAlarmId) throws AeBusinessProcessException
   {
      checkProcessId(aProcessId);

      // If we get here, that means a journaled alarm entry has asked the
      // recovery engine to dispatch an alarm to the process. The journaled
      // alarm entry would have been created only by a successful call to
      // removeAlarmForDispatch in the normal engine.

      // Remove the schedule alarm item (if there is one) or add a remove alarm
      // item.
      removeAlarm(aProcessId, aLocationId, aAlarmId);

      // The base implementation calls removeMessageReceiversInGroup. We will
      // ignore that call and depend instead on each message receiver dequeuing
      // itself individually. In other words, we will wait for the individual
      // calls to removeMessageReceiver to manage the recovered item for each
      // message receiver.

      // Return a successful result.
      return getNextJournalId();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeQueueManager#removeMessageReceiver(long, int)
    */
   public boolean removeMessageReceiver(long aProcessId, int aLocationId) throws AeBusinessProcessException
   {
      checkProcessId(aProcessId);

      // Remove the message receiver from AeBaseQueueManager's internal
      // structure for matchInboundReceive().
      super.removeMessageReceiver(aProcessId, aLocationId);

      // Remove the add message receiver item (if there is one).
      IAeRecoveredItem addMessageReceiverItem = new AeRecoveredAddReceiverItem(aLocationId);
      getRecoveredItemsSet().removeRecoveredItem(addMessageReceiverItem);
      return true;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeBaseQueueManager#removeMessageReceiversInGroup(long, int, int)
    */
   protected int removeMessageReceiversInGroup(long aProcessId, int aGroupId, int aLocationId) throws AeBusinessProcessException
   {
      checkProcessId(aProcessId);

      // Remove the message receiver from AeBaseQueueManager's internal
      // structure for matchInboundReceive().
      // The super call will remove one or more message receivers, depending on 
      // whether the message receiver was in a group (i.e. an onMessage/onAlarm 
      // within a pick).
      int count = super.removeMessageReceiversInGroup(aProcessId, aGroupId,
            aLocationId);
      
      // Remove the add message receiver item (if there is one).
      // We should remove any previously recovered message receiver. We're only
      // removing the receiver with the given location id since the others in 
      // the same group (if any) will be removed by the pick when the onMessage
      // or onAlarm is processed. see the removeMessageReceiver method w/in this
      // class.
      IAeRecoveredItem addMessageReceiverItem = new AeRecoveredAddReceiverItem(aLocationId);
      getRecoveredItemsSet().removeRecoveredItem(addMessageReceiverItem);

      return count;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeQueueManager#scheduleAlarm(long, int, int, int, java.util.Date)
    */
   public void scheduleAlarm(long aProcessId, int aLocationId, int aGroupId, int aAlarmId, Date aDeadline) throws AeBusinessProcessException
   {
      checkProcessId(aProcessId);

      IAeRecoveredItem scheduleAlarmItem = new AeRecoveredScheduleAlarmItem(aProcessId, aLocationId, aGroupId, aAlarmId, aDeadline);
      try
      {
         getRecoveredItemsSet().addRecoveredItem(scheduleAlarmItem);
      }
      catch (AeRecoveryConflictingRequestException e)
      {
         throw new AeConflictingRequestException(getRecoveryProcess().getBPELNamespace());
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeQueueManager#sendReply(org.activebpel.rt.bpel.impl.queue.AeReply, org.activebpel.rt.message.IAeMessageData, org.activebpel.rt.bpel.IAeFault, java.util.Map)
    */
   public void sendReply(AeReply aReply, IAeMessageData aMessageData, IAeFault aFault, Map aProcessProperties) throws AeBusinessProcessException
   {
      checkProcessId(aReply.getProcessId());

      // Remove the matching add reply item.
      IAeRecoveredItem addReplyItem = new AeRecoveredAddReplyItem(aReply, null);
      IAeRecoveredItem waitingReplyItem = getRecoveredItemsSet().removeRecoveredItem(addReplyItem);

      // If the reply was sent in the past, then we just needed to consume the
      // sent reply item and the add reply item.
      if (removeSentReply(aReply))
      {
         // By the way, it's possible that waitingReplyItem is null here, but
         // that's legitimate, because the process may have originally
         // responded to a reply object from an inbound receive that arrived
         // earlier than the first inbound receive in this batch of journal
         // entries.
      }
      // Otherwise, if there is no waiting reply object, then add an item to
      // send the reply from the "real" queue manager.
      else if (waitingReplyItem == null)
      {
         IAeRecoveredItem sendReplyItem = new AeRecoveredSendReplyItem(aReply, aMessageData, aFault, aProcessProperties);
         try
         {
            getRecoveredItemsSet().addRecoveredItem(sendReplyItem);
         }
         catch (AeRecoveryConflictingRequestException e)
         {
            throw new AeConflictingRequestException(getRecoveryProcess().getBPELNamespace());
         }
      }
      // Otherwise, send the reply to the waiting reply object. This will
      // probably throw an exception, because the reply is probably an instance
      // of AeMissingReplyReceiver constructed by AeInboundReceiveJournalEntry.
      else
      {
         AeReply waitingReply = ((AeRecoveredAddReplyItem) waitingReplyItem).getReply();
         // use the base class to send the reply.
         super.sendReply(waitingReply, aMessageData, aFault, aProcessProperties);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeBaseQueueManager#getReceiveHandler(org.activebpel.wsio.receive.IAeMessageContext)
    */
   public IAeReceiveHandler getReceiveHandler(String aProtocol) throws AeBusinessProcessException
   {
      return mReceiveHandler;
   }
   
   /*==============================================================================
    * org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryQueueManager methods
    *==============================================================================
    */

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryQueueManager#setInvokeTransmittedEntries(java.util.List)
    */
   public void setInvokeTransmittedEntries(List aInvokeTransmittedEntries)
   {
      mInvokeTransmittedEntries = aInvokeTransmittedEntries;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryQueueManager#setRecoveredItemsSet(org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveredItemsSet)
    */
   public void setRecoveredItemsSet(IAeRecoveredItemsSet aRecoveredItemsSet)
   {
      mRecoveredItemsSet = aRecoveredItemsSet;

      // Initialize AeBaseQueueManager's internal structure for
      // matchInboundReceive().
      getMessageReceivers().clear();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryQueueManager#setRecoveryProcess(org.activebpel.rt.bpel.IAeBusinessProcess)
    */
   public void setRecoveryProcess(IAeBusinessProcess aRecoveryProcess)
   {
      mRecoveryProcess = aRecoveryProcess;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryQueueManager#setSentReplies(java.util.List)
    */
   public void setSentReplies(List aSentReplies)
   {
      // Copy the list, so that we can modify it without affecting the caller.
      mSentReplies = new LinkedList(aSentReplies);
   }

}
