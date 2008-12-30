//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/journal/AeInvokePendingJournalEntry.java,v 1.1 2006/11/08 18:18:5
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
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.AeActivityInvokeImpl;
import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Document;

/**
 * Implements journal entry for pending invokes.
 */
public class AeInvokePendingJournalEntry extends AeAbstractJournalEntry implements IAeJournalEntry
{
   /**
    * Constructs journal entry to persist to storage.
    */
   public AeInvokePendingJournalEntry(int aLocationId)
   {
      super(JOURNAL_INVOKE_PENDING, aLocationId);
   }

   /**
    * Constructs journal entry to restore from storage.
    */
   public AeInvokePendingJournalEntry(int aLocationId, long aJournalId, Document aStorageDocument)
   {
      super(JOURNAL_INVOKE_PENDING, aLocationId, aJournalId, aStorageDocument);
   }
   
   /** 
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.AeAbstractJournalEntry#internalDeserialize(org.w3c.dom.Document)
    */
   protected void internalDeserialize(Document aStorageDocument) throws AeBusinessProcessException
   {
      // Nothing to deserialize.
   }

   /** 
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.AeAbstractJournalEntry#internalSerialize(org.activebpel.rt.xml.schema.AeTypeMapping)
    */
   protected AeFastDocument internalSerialize(AeTypeMapping aTypeMapping) throws AeBusinessProcessException
   {
      // Nothing to serialize.
      return null;
   }

   /** 
    * Overrides method to set the journal id in the invoke activity implementation.
    * 
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry#dispatchToProcess(org.activebpel.rt.bpel.IAeBusinessProcess)
    */
   public void dispatchToProcess(IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      if (aProcess == null)
      {
         throw new IllegalStateException(AeMessages.getString("AeInvokeQueuedJournalEntry.MISSING_PROCESS")); //$NON-NLS-1$
      }

      IAeBpelObject object = aProcess.findBpelObject(getLocationId());

      if (!(object instanceof AeActivityInvokeImpl))
      {            
         throw new AeBusinessProcessException(AeMessages.format("AeInvokeQueuedJournalEntry.NO_MATCHING_INVOKE", getLocationId())); //$NON-NLS-1$
      }

      ((AeActivityInvokeImpl) object).setJournalId(getJournalId());
   }
}
