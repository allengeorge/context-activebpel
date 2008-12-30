// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/journal/AeInboundReceiveJournalEntry.java,v 1.9 2006/10/26 14:05:5
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
package org.activebpel.rt.bpel.server.engine.recovery.journal;

import java.util.Collections;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.def.AePartnerLinkOpKey;
import org.activebpel.rt.bpel.impl.AeMessageDataDeserializer;
import org.activebpel.rt.bpel.impl.IAeImplStateNames;
import org.activebpel.rt.bpel.impl.activity.IAeMessageReceiverActivity;
import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.impl.queue.AeInboundReceive;
import org.activebpel.rt.bpel.impl.reply.IAeDurableReplyFactory;
import org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver;
import org.activebpel.rt.bpel.impl.storage.AeInboundReceiveDeserializer;
import org.activebpel.rt.bpel.impl.storage.AeInboundReceiveSerializer;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryEngine;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.activebpel.wsio.receive.AeMessageContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Implements journal entry for inbound receive.
 */
public class AeInboundReceiveJournalEntry extends AeAbstractJournalEntry implements IAeJournalEntry, IAeImplStateNames
{
   /** The inbound receive. */
   private AeInboundReceive mInboundReceive;

   /** The message data for legacy cases. */
   private IAeMessageData mMessageData;

   /** The process to use to deserialize the storage document. */
   private IAeBusinessProcess mProcess;

   /**
    * Constructs journal entry to persist to storage.
    */
   public AeInboundReceiveJournalEntry(int aLocationId, AeInboundReceive aInboundReceive)  //, boolean aReplyWaiting)
   {
      super(JOURNAL_INBOUND_RECEIVE, aLocationId);
      mInboundReceive = aInboundReceive;
   }

   /**
    * Constructs journal entry to restore from persisted document.
    */
   public AeInboundReceiveJournalEntry(int aLocationId, long aJournalId, Document aStorageDocument) throws AeMissingStorageDocumentException
   {
      super(JOURNAL_INBOUND_RECEIVE, aLocationId, aJournalId, aStorageDocument);

      if (aStorageDocument == null)
      {
         throw new AeMissingStorageDocumentException(aLocationId);
      }
   }

   /**
    * Overrides method to dispatch the inbound receive to the given process.
    *
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry#dispatchToProcess(org.activebpel.rt.bpel.IAeBusinessProcess)
    */
   public void dispatchToProcess(IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      // Set process to use to deserialize the storage document.
      setProcess(aProcess);

      IAeRecoveryEngine engine = (IAeRecoveryEngine) aProcess.getEngine();
      long processId = aProcess.getProcessId();

      if (getMessageData() != null)
      {
         int locationId = getLocationId();
         IAeMessageData messageData = getMessageData();

         // This is the legacy case. We used to save only the message data for
         // received messages. We'll dispatch the message data directly to the
         // process.
         IAeMessageReceiverActivity activity = (IAeMessageReceiverActivity) aProcess.findBpelObject(locationId);
         AePartnerLinkOpKey plinkKey = activity.getPartnerLinkOperationImplKey();
         AeInboundReceive inboundReceive = new AeInboundReceive(plinkKey, Collections.EMPTY_MAP, aProcess.getProcessPlan(), messageData, new AeMessageContext(), null);
         
         engine.getQueueManager().removeMessageReceiver(processId, locationId);
         aProcess.dispatchReceiveData(locationId, inboundReceive, IAeReplyReceiver.NULL_REPLY_ID);
      }
      else
      {
         AeInboundReceive inboundReceive = getInboundReceive();

         // Set the reply id to match the reply that might have been sent and
         // journaled. AeBaseQueueManager#matchInboundReceive and 
         // AeBusinessProcessEngine#internalCreateProcessWithMessage use the
         // journal id for the reply id, so we do the same thing here.
         inboundReceive.setReplyId(getJournalId());

         engine.queueReceiveData(inboundReceive, null, true);
      }
   }

   /**
    * Returns the inbound receive.
    */
   public AeInboundReceive getInboundReceive() throws AeBusinessProcessException
   {
      deserialize();
      return mInboundReceive;
   }

   /**
    * Returns the message data for legacy cases.
    */
   protected IAeMessageData getMessageData() throws AeBusinessProcessException
   {
      deserialize();
      return mMessageData;
   }

   /**
    * Returns the process to use to deserialize the storage document.
    */
   protected IAeBusinessProcess getProcess()
   {
      if (mProcess == null)
      {
         throw new IllegalStateException(AeMessages.getString("AeInboundReceiveJournalEntry.ERROR_0")); //$NON-NLS-1$
      }

      return mProcess;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.AeAbstractJournalEntry#internalDeserialize(org.w3c.dom.Document)
    */
   protected void internalDeserialize(Document aStorageDocument) throws AeBusinessProcessException
   {
      Element root = aStorageDocument.getDocumentElement();

      if (root.getTagName() == IAeImplStateNames.STATE_MESSAGEDATA)
      {
         // Handle legacy instances serialized by a previous version of the
         // engine. We used to serialize only the message data.
         AeMessageDataDeserializer deserializer = new AeMessageDataDeserializer();
         deserializer.setMessageDataElement(root);

         mInboundReceive = null;
         mMessageData = deserializer.getMessageData();
      }
      else
      {
         AeInboundReceiveDeserializer deserializer = new AeInboundReceiveDeserializer();
         deserializer.setInboundReceiveElement(root);
         deserializer.setProcessPlan(getProcess().getProcessPlan());
         deserializer.setTypeMapping(getProcess().getEngine().getTypeMapping());

         mInboundReceive = deserializer.getInboundReceive();
         mMessageData = null;
         boolean replyWaiting = !mInboundReceive.isOneway();
         // handle legacy case - pre 2.1
         if (!replyWaiting && AeUtil.notNullOrEmpty( root.getAttribute(STATE_REPLYWAITING) ) )
         {
            replyWaiting = "true".equals(root.getAttribute(STATE_REPLYWAITING)); //$NON-NLS-1$
            mInboundReceive.setOneway(!replyWaiting);
         }         
         //create reply receiver.         
         if (replyWaiting)
         {
            IAeReplyReceiver replyReceiver = null;
            IAeDurableReplyFactory factory = getProcess().getEngine().getTransmissionTracker().getDurableReplyFactory();
            replyReceiver = factory.createReplyReceiver(mInboundReceive.getReplyId(), deserializer.getDurableReplyInfo());
            mInboundReceive.setReplyReceiver(replyReceiver);
         }
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.AeAbstractJournalEntry#internalSerialize(org.activebpel.rt.xml.schema.AeTypeMapping)
    */
   protected AeFastDocument internalSerialize(AeTypeMapping aTypeMapping) throws AeBusinessProcessException
   {
      if (aTypeMapping == null)
      {
         throw new IllegalStateException(AeMessages.getString("AeInboundReceiveJournalEntry.ERROR_1")); //$NON-NLS-1$
      }

      AeInboundReceiveSerializer serializer = new AeInboundReceiveSerializer();
      serializer.setInboundReceive(getInboundReceive());
      serializer.setTypeMapping(aTypeMapping);

      AeFastDocument result = serializer.getInboundReceiveDocument();
      return result;
   }

   /**
    * Returns the reply waiting flag.
    */
   public boolean isReplyWaiting() throws AeBusinessProcessException
   {
      deserialize();
      if (getInboundReceive() != null)
      {
         return !getInboundReceive().isOneway();
      }
      else
      {
         return  false;
      }
   }

   /**
    * Sets the process to use to deserialize the storage document.
    */
   public void setProcess(IAeBusinessProcess aProcess)
   {
      mProcess = aProcess;
   }

}
