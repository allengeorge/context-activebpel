// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeOnMessage.java,v 1.42 2007/01/27 14:44:3
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
package org.activebpel.rt.bpel.impl.activity.support;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.def.AePartnerLinkOpKey;
import org.activebpel.rt.bpel.def.activity.AeActivityPickDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef;
import org.activebpel.rt.bpel.impl.AePartnerLink;
import org.activebpel.rt.bpel.impl.AePartnerLinkOpImplKey;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.IAeMessageValidator;
import org.activebpel.rt.bpel.impl.activity.AeMultiStartHelper;
import org.activebpel.rt.bpel.impl.activity.IAeEventParent;
import org.activebpel.rt.bpel.impl.activity.IAeMessageDispatcher;
import org.activebpel.rt.bpel.impl.activity.IAeMessageReceiverActivity;
import org.activebpel.rt.bpel.impl.activity.IAeWSIOActivity;
import org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumer;
import org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducer;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.wsio.receive.IAeMessageContext;

/**
 * Models the <code>onMessage</code> that is part of a <code>pick</code>
 * or the <code>eventHandlers</code>.
 */
public class AeOnMessage extends AeBaseEvent implements IAeActivityParent, IAeMessageReceiverActivity, IAeWSIOActivity
{
   /** impl for correlations */
   private IAeCorrelations mCorrelations;
   /** impl for message validation */
   private IAeMessageValidator mMessageValidator;
   /** impl for consuming incoming message data */
   private IAeMessageDataConsumer mMessageDataConsumer;
   
   /**
    * Requires the def object and parent.
    * 
    * @param aDef
    * @param aParent
    */
   public AeOnMessage(AeOnMessageDef aDef, IAeEventParent aParent)
   {
      super(aDef, aParent);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageReceiverActivity#getPartnerLinkDef()
    */
   public AePartnerLinkDef getPartnerLinkDef()
   {
      return getDef().getPartnerLinkDef();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageReceiverActivity#findPartnerLink()
    */
   public AePartnerLink findPartnerLink()
   {
      AePartnerLinkDef def = getPartnerLinkDef();
      if (def == null)
         return null;
      
      return findPartnerLink(getPartnerLinkDef().getName());
   }
   
   /**
    * Convenience method to avoid casting
    */
   private AeOnMessageDef getDef()
   {
      return (AeOnMessageDef) getDefinition();
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept( IAeImplVisitor aVisitor ) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }
   
   /**
    * Implements on message execution logic.  The on message queues a waiting for 
    * message with the engine.  The engine will then call back the on message
    * when its message is ready.
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      boolean okToQueue = AeMultiStartHelper.checkForMultiStartBehavior(this);
      
      if (okToQueue)
      {
         queueReceive();
      }
   }

   /**
    * Queues the message receiver.
    */
   protected void queueReceive() throws AeBusinessProcessException
   {
      setQueued(true);
      
      getProcess().queueMessageReceiver(this, getGroupId());
   }

   /**
    * Gets the OnMessage's group id.
    */
   protected int getGroupId()
   {
      if (getDef().isPickMessage())
      {
         return getParent().getLocationId();
      }
      else
      {
         return getLocationId();
      }
   }

   /**
    * Extracts the message data into the variable and initiates or validates any correlation sets.
    * If there are any errors during this process then an exception will be thrown or false returned
    * indicating that the child activity shouldn't be executed.
    * @param aMessageData
    * @return true if the data was extracted w/o any errors and the child activity should be executed.
    * @throws AeBusinessProcessException
    */
   protected void extractMessageData(IAeMessageData aMessageData) throws AeBusinessProcessException
   {
      getMessageValidator().validate(getProcess(), aMessageData, getDef().getConsumerMessagePartsMap());

      if (getRequestCorrelations() != null)
         getRequestCorrelations().initiateOrValidate(aMessageData, getDef().getConsumerMessagePartsMap());

      getMessageDataConsumer().consumeMessageData(aMessageData);
   }

   /**
    * Dequeue our message receiver.
    * @see org.activebpel.rt.bpel.impl.activity.support.AeBaseEvent#dequeue()
    */
   protected void dequeue() throws AeBusinessProcessException
   {
      if (isQueued())
      {
         getProcess().dequeueMessageReceiver(this);
         setQueued(false);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageReceiverActivity#canCreateInstance()
    */
   public boolean canCreateInstance()
   {
      boolean canCreate = false;
      if (getDef().isPickMessage())
      {
         AeActivityPickDef pickDef = (AeActivityPickDef) getDef().getParent();
         canCreate = pickDef.isCreateInstance();
      }
      return canCreate;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageReceiverActivity#getCorrelations()
    */
   public IAeIMACorrelations getCorrelations()
   {
      return (IAeIMACorrelations) getRequestCorrelations();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeCorrelationListener#correlationSetInitialized(org.activebpel.rt.bpel.impl.activity.support.AeCorrelationSet)
    */
   public void correlationSetInitialized(AeCorrelationSet aSet)
      throws AeBusinessProcessException
   {
      aSet.removeCorrelationListener(this);
      
      if (AeMultiStartHelper.isCorrelatedDataAvailable(this))
      {
         queueReceive();
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageReceiverActivity#getPartnerLinkOperationKey()
    */
   public AePartnerLinkOpKey getPartnerLinkOperationKey()
   {
      return getDef().getPartnerLinkOperationKey();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageReceiverActivity#getPartnerLinkOperationImplKey()
    */
   public AePartnerLinkOpImplKey getPartnerLinkOperationImplKey()
   {
      return new AePartnerLinkOpImplKey(findPartnerLink(), getDef().getOperation());
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeWSIOActivity#setMessageValidator(org.activebpel.rt.bpel.impl.IAeMessageValidator)
    */
   public void setMessageValidator(IAeMessageValidator aValidator)
   {
      mMessageValidator = aValidator;
   }
   
   /**
    * Getter for the message validator
    */
   protected IAeMessageValidator getMessageValidator()
   {
      return mMessageValidator;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeWSIOActivity#setRequestCorrelations(org.activebpel.rt.bpel.impl.activity.support.IAeCorrelations)
    */
   public void setRequestCorrelations(IAeCorrelations aCorrelations)
   {
      mCorrelations = aCorrelations;
   }
   
   /**
    * Getter for the request correlations
    */
   protected IAeCorrelations getRequestCorrelations()
   {
      return mCorrelations;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeWSIOActivity#setResponseCorrelations(org.activebpel.rt.bpel.impl.activity.support.IAeCorrelations)
    */
   public void setResponseCorrelations(IAeCorrelations aCorrelations)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeWSIOActivity#setMessageDataConsumer(org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumer)
    */
   public void setMessageDataConsumer(IAeMessageDataConsumer aMessageDataConsumer)
   {
      mMessageDataConsumer = aMessageDataConsumer;
   }

   /**
    * Getter for the message data consumer
    */
   protected IAeMessageDataConsumer getMessageDataConsumer()
   {
      return mMessageDataConsumer;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeWSIOActivity#setMessageDataProducer(org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducer)
    */
   public void setMessageDataProducer(IAeMessageDataProducer aMessageDataProducer)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageReceiverActivity#isConcurrent()
    */
   public boolean isConcurrent()
   {
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeMessageReceiverActivity#createDispatcher(org.activebpel.wsio.receive.IAeMessageContext)
    */
   public IAeMessageDispatcher createDispatcher(IAeMessageContext aMessageContext)
   {
      return new AeMessageDispatcher();
   }
   
   /**
    * Handles the dispatching of the fault or message to the onMessage.
    */
   private class AeMessageDispatcher implements IAeMessageDispatcher
   {
      // fixme (MF) change to have the onMessage implement this interface directly once onEvent no longer extends onMessage 
      
      /**
       * @see org.activebpel.rt.bpel.impl.activity.IAeMessageDispatcher#getLocationPath()
       */
      public String getLocationPath()
      {
         return AeOnMessage.this.getLocationPath();
      }

      /**
       * @see org.activebpel.rt.bpel.impl.activity.IAeMessageDispatcher#getMessageExchangePathForOpenIMA()
       */
      public String getMessageExchangePathForOpenIMA() throws AeBusinessProcessException
      {
         String messageExchangePath = findEnclosingScope().getMessageExchangePath(getDef().getMessageExchange());
         return messageExchangePath;
      }

      /**
       * @see org.activebpel.rt.bpel.impl.activity.IAeMessageDispatcher#getPartnerLinkOperationImplKey()
       */
      public AePartnerLinkOpImplKey getPartnerLinkOperationImplKey()
      {
         return AeOnMessage.this.getPartnerLinkOperationImplKey();
      }

      /**
       * @see org.activebpel.rt.bpel.impl.activity.IAeMessageDispatcher#isPartnerLinkReadyForUpdate()
       */
      public boolean isPartnerLinkReadyForUpdate()
      {
         return true;
      }

      /**
       * @see org.activebpel.rt.bpel.impl.activity.IAeMessageDispatcher#onFault(org.activebpel.rt.bpel.IAeFault)
       */
      public void onFault(IAeFault aFault) throws AeBusinessProcessException
      {
         objectCompletedWithFault(aFault);
      }

      /**
       * @see org.activebpel.rt.bpel.impl.activity.IAeMessageDispatcher#onMessage(org.activebpel.rt.message.IAeMessageData)
       */
      public void onMessage(IAeMessageData aMessage) throws AeBusinessProcessException
      {
         getProcess().removeReceiverKeyForConflictingReceives(AeOnMessage.this);
         
         // set that we are no longer queued since we have been called
         setQueued(false);

         extractMessageData(aMessage);
         
         if (getDef().isPickMessage())
         {
            // There is a case where the process will deadlock if it reaches an
            // isolated scope nested within a pick's onMessage. If this isolated
            // scope accesses the variable that was just received into, then the
            // scope will not enter the READY_TO_EXECUTE state until it has 
            // acquired an exclusive lock on the variable. This will never occur
            // since the pick will have acquired the lock and not release it
            // until its onMessage completes - hence the deadlock.
            //
            // The solution is to add the line below that releases all of the
            // locks that the pick would have acquired. These locks will include
            // the lock on the variable just received into as well as any locks
            // acquired for other onMessages that will not execute. The pick 
            // should not have acquired any other locks apart from those relating
            // to consuming message data.
            //
            getProcess().getVariableLocker().releaseLocks(getParent().getLocationPath());
         }
         executeChild();
      }

      /**
       * @see org.activebpel.rt.bpel.impl.activity.IAeMessageDispatcher#getTarget()
       */
      public IAeBpelObject getTarget()
      {
         return AeOnMessage.this;
      }
   }
}
