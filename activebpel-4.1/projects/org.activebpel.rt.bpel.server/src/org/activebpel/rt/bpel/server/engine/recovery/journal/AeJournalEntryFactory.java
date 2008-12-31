// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/journal/AeJournalEntryFactory.java,v 1.4 2006/11/08 18:18:5
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.AeMessages;
import org.w3c.dom.Document;

/**
 * Implements factory to construct instances of {@link IAeJournalEntry} from
 * the entry's persisted data: entry type, location id, journal entry id, and
 * storage document.
 */
public class AeJournalEntryFactory implements IAeJournalEntryFactory
{
   /** Singleton instance. */
   private static IAeJournalEntryFactory sInstance = new AeJournalEntryFactory();

   /**
    * Private constructor for singleton instance.
    */
   private AeJournalEntryFactory()
   {
   }

   /**
    * Returns singleton instance.
    */
   public static IAeJournalEntryFactory getInstance()
   {
      return sInstance;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntryFactory#newJournalEntry(int, int, long, org.w3c.dom.Document)
    */
   public IAeJournalEntry newJournalEntry(int aEntryType, int aLocationId, long aJournalId, Document aStorageDocument) throws AeException
   {
      IAeJournalEntry entry = null;

      switch (aEntryType)
      {
         case IAeJournalEntry.JOURNAL_ALARM:
            entry = new AeAlarmJournalEntry(aLocationId, aJournalId, aStorageDocument);
            break;

         case IAeJournalEntry.JOURNAL_INVOKE_DATA:
            entry = new AeInvokeDataJournalEntry(aLocationId, aJournalId, aStorageDocument);
            break;

         case IAeJournalEntry.JOURNAL_INVOKE_FAULT:
            entry = new AeInvokeFaultJournalEntry(aLocationId, aJournalId, aStorageDocument);
            break;

         case IAeJournalEntry.JOURNAL_INBOUND_RECEIVE:
            entry = new AeInboundReceiveJournalEntry(aLocationId, aJournalId, aStorageDocument);
            break;

         case IAeJournalEntry.JOURNAL_SENT_REPLY:
            entry = new AeSentReplyJournalEntry(aLocationId, aJournalId, aStorageDocument);
            break;
            
         case IAeJournalEntry.JOURNAL_INVOKE_TRANSMITTED:
            entry = new AeInvokeTransmittedJournalEntry(aLocationId, aJournalId, aStorageDocument);
            break;
            
         case IAeJournalEntry.JOURNAL_COMPENSATE_SUBPROCESS:
            entry = new AeCompensateSubprocessJournalEntry(aLocationId, aJournalId, aStorageDocument);
            break;

         case IAeJournalEntry.JOURNAL_ENGINE_FAILURE:
            entry = new AeEngineFailureJournalEntry(aLocationId, aJournalId, aStorageDocument);
            break;

         case IAeJournalEntry.JOURNAL_INVOKE_PENDING:
            entry = new AeInvokePendingJournalEntry(aLocationId, aJournalId, aStorageDocument);
            break;

         default:
            throw new AeUnknownEntryTypeException(aEntryType, aLocationId);
      }

      return entry;
   }

   /**
    * Implements an exception that reports an unknown entry type.
    */
   protected static class AeUnknownEntryTypeException extends AeException
   {
      /**
       * Constructs an exception that reports an unknown entry type.
       */
      public AeUnknownEntryTypeException(int aEntryType, int aLocationId)
      {
         super(AeMessages.format("AeUnknownEntryTypeException.ERROR_UNKNOWN_TYPE", new Object[] { new Integer(aEntryType), new Integer(aLocationId) })); //$NON-NLS-1$
      }
   }
}
