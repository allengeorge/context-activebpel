// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/journal/AeEngineFailureJournalEntry.java,v 1.1 2006/11/08 18:18:5
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.impl.fastdom.AeFastElement;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Document;

/**
 * Implements journal entry for engine failure.
 */
public class AeEngineFailureJournalEntry extends AeAbstractJournalEntry implements IAeJournalEntry
{
   private static final String ENGINE = "engine"; //$NON-NLS-1$
   private static final String ENGINE_ID = "engine-id"; //$NON-NLS-1$

   /** The engine id. */
   private int mEngineId;

   /**
    * Constructs journal entry to persist to storage.
    */
   public AeEngineFailureJournalEntry(int aEngineId)
   {
      // Pass 0 for location id.
      super(JOURNAL_ENGINE_FAILURE, 0);

      setEngineId(aEngineId);
   }

   /**
    * Constructs journal entry to restore from storage.
    */
   public AeEngineFailureJournalEntry(int aLocationId, long aJournalId, Document aStorageDocument) throws AeMissingStorageDocumentException
   {
      super(JOURNAL_ENGINE_FAILURE, aLocationId, aJournalId, aStorageDocument);

      if (aStorageDocument == null)
      {
         throw new AeMissingStorageDocumentException();
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry#dispatchToProcess(org.activebpel.rt.bpel.IAeBusinessProcess)
    */
   public void dispatchToProcess(IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      // Do nothing.
   }
   
   /**
    * Returns the engine id.
    */
   public int getEngineId() throws AeBusinessProcessException
   {
      deserialize();
      return mEngineId;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.AeAbstractJournalEntry#internalDeserialize(org.w3c.dom.Document)
    */
   protected void internalDeserialize(Document aStorageDocument) throws AeBusinessProcessException
   {
      try
      {
         int engineId = Integer.parseInt(aStorageDocument.getDocumentElement().getAttribute(ENGINE_ID));
         setEngineId(engineId);
      }
      catch (Exception e)
      {
         throw new AeBusinessProcessException(AeMessages.format("AeEngineFailureJournalEntry.ERROR_InvalidEngineId", getJournalId()), e); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.AeAbstractJournalEntry#internalSerialize(org.activebpel.rt.xml.schema.AeTypeMapping)
    */
   protected AeFastDocument internalSerialize(AeTypeMapping aTypeMapping) throws AeBusinessProcessException
   {
      AeFastElement element = new AeFastElement(ENGINE);
      element.setAttribute(ENGINE_ID, String.valueOf(getEngineId()));
      return new AeFastDocument(element);
   }
   
   /**
    * Sets the engine id.
    */
   protected void setEngineId(int aGroupId)
   {
      mEngineId = aGroupId;
   }
}
