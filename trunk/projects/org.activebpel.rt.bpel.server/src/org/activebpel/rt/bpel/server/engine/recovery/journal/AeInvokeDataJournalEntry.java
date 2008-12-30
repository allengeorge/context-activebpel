// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/journal/AeInvokeDataJournalEntry.java,v 1.5 2007/07/26 14:12:1
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

import java.util.Map;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.impl.AeMessageDataDeserializer;
import org.activebpel.rt.bpel.impl.AeMessageDataSerializer;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.activity.AeActivityInvokeImpl;
import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.impl.fastdom.AeFastElement;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Implements journal entry for invoke data.
 */
public class AeInvokeDataJournalEntry extends AeAbstractJournalEntry implements IAeJournalEntry
{
   /** XML tag name for serialization. */
   private static final String TAG_RECEIVED_INVOKE_DATA = "receivedInvokeData"; //$NON-NLS-1$

   /** The message data. */
   private IAeMessageData mMessageData;

   /** The associated process properties. */
   private Map mProcessProperties;

   /** The invoke activity implementation object. */
   private AeActivityInvokeImpl mInvoke;

   /** The process to use to deserialize the storage document. */
   private IAeBusinessProcess mProcess;
   
   /** Invoke activity transmissiond id. */
   private long mTransmissionId; 

   /**
    * Constructs journal entry to persist to storage.
    */
   public AeInvokeDataJournalEntry(int aLocationId, long aTransmissionId, IAeMessageData aMessageData, Map aProcessProperties)
   {
      super(JOURNAL_INVOKE_DATA, aLocationId);

      mMessageData = aMessageData;
      mProcessProperties = aProcessProperties;
      mTransmissionId = aTransmissionId;
   }

   /**
    * Constructs journal entry to restore from storage.
    */
   public AeInvokeDataJournalEntry(int aLocationId, long aJournalId, Document aStorageDocument)
   {
      super(JOURNAL_INVOKE_DATA, aLocationId, aJournalId, aStorageDocument);
   }

   /**
    * Overrides method to dispatch the invoke data to the specified process
    * through the recovery engine.
    *
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry#dispatchToProcess(org.activebpel.rt.bpel.IAeBusinessProcess)
    */
   public void dispatchToProcess(IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      // Set process to use to deserialize the storage document.
      setProcess(aProcess);
      
      // Only dispatch if the invoke is not one-way.  One-way invokes
      // will be re-executed and completed when the JOURNAL_INVOKE_TRANSMITTED
      // journal entry is replayed.
      if (!getInvoke().isOneWay())
      {
         IAeBusinessProcessEngineInternal engine = aProcess.getEngine();
         long processId = aProcess.getProcessId();
         String locationPath = getInvoke().getLocationPath();
         IAeMessageData messageData = getMessageData();
         Map processProperties = getProcessProperties();
         engine.queueInvokeData(processId, locationPath,  getTransmissionId() , messageData, processProperties);
      }
   }

   /**
    * Returns the invoke activity implementation object.
    */
   protected AeActivityInvokeImpl getInvoke() throws AeBusinessProcessException
   {
      if (mInvoke == null)
      {
         IAeBpelObject object = getProcess().findBpelObject(getLocationId());

         if (!(object instanceof AeActivityInvokeImpl))
         {
            throw new AeBusinessProcessException(AeMessages.format("AeInvokeDataJournalEntry.ERROR_0", getLocationId())); //$NON-NLS-1$
         }

         mInvoke = (AeActivityInvokeImpl) object;
      }

      return mInvoke;
   }

   /**
    * Returns the message data.
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
         throw new IllegalStateException(AeMessages.getString("AeInvokeDataJournalEntry.ERROR_1")); //$NON-NLS-1$
      }

      return mProcess;
   }

   /**
    * Returns the associated process properties.
    */
   protected Map getProcessProperties() throws AeBusinessProcessException
   {
      deserialize();
      return mProcessProperties;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.AeAbstractJournalEntry#internalDeserialize(org.w3c.dom.Document)
    */
   protected void internalDeserialize(Document aStorageDocument) throws AeBusinessProcessException
   {
      Element root = aStorageDocument.getDocumentElement();
      String txIdString = root.getAttribute( STATE_TRANSMISSION_ID );
      mTransmissionId = 0;
      if ( AeUtil.notNullOrEmpty(txIdString) )
      {
         try
         {
            mTransmissionId = Long.parseLong(txIdString); 
         }
         catch(Exception e)
         {            
         }
      }
      Element messageDataElement = AeXmlUtil.findSubElement(root, STATE_MESSAGEDATA);

      if (messageDataElement == null)
      {
         mMessageData = null;
      }
      else
      {
         // Deserialize the message data.
         AeMessageDataDeserializer deserializer = new AeMessageDataDeserializer();
         deserializer.setMessageDataElement(messageDataElement);
         deserializer.setTypeMapping(getProcess().getEngine().getTypeMapping());
         deserializer.setVariable(getInvoke().getOutputVariable());

         mMessageData = deserializer.getMessageData();
      }

      mProcessProperties = deserializeProcessProperties(root);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.AeAbstractJournalEntry#internalSerialize(org.activebpel.rt.xml.schema.AeTypeMapping)
    */
   protected AeFastDocument internalSerialize(AeTypeMapping aTypeMapping) throws AeBusinessProcessException
   {
      if (aTypeMapping == null)
      {
         throw new IllegalStateException(AeMessages.getString("AeInvokeDataJournalEntry.ERROR_2")); //$NON-NLS-1$
      }

      AeFastElement root = new AeFastElement(TAG_RECEIVED_INVOKE_DATA);
      root.setAttribute( STATE_TRANSMISSION_ID, String.valueOf( getTransmissionId() ) );
      serializeProcessProperties(root, getProcessProperties());

      if (getMessageData() != null)
      {
         AeMessageDataSerializer serializer = new AeMessageDataSerializer(aTypeMapping);
         serializer.setMessageData(getMessageData());

         AeFastElement messageDataElement = serializer.getMessageDataElement();
         root.appendChild(messageDataElement);
      }

      return new AeFastDocument(root);
   }

   /**
    * Sets the process to use to deserialize the storage document.
    */
   protected void setProcess(IAeBusinessProcess aProcess)
   {
      mProcess = aProcess;
   }

   /**
    * @return Returns the transmission id.
    */
   protected long getTransmissionId() throws AeBusinessProcessException
   {
      deserialize();
      return mTransmissionId;
   }
   
}
