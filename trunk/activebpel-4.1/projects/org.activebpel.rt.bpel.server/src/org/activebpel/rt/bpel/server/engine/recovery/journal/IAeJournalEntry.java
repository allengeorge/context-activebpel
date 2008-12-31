// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/journal/IAeJournalEntry.java,v 1.5 2006/11/08 18:18:5
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
import org.activebpel.rt.xml.schema.AeTypeMapping;

/**
 * Defines interface for process journal entries.
 */
public interface IAeJournalEntry
{
   public static final int JOURNAL_ALARM                 = 1;
   public static final int JOURNAL_INBOUND_RECEIVE       = 2;
   public static final int JOURNAL_INVOKE_DATA           = 3;
   public static final int JOURNAL_INVOKE_FAULT          = 4;
   public static final int JOURNAL_SENT_REPLY            = 5;
   public static final int JOURNAL_INVOKE_TRANSMITTED    = 6;
   public static final int JOURNAL_COMPENSATE_SUBPROCESS = 7;
   public static final int JOURNAL_ENGINE_FAILURE        = 8;
   public static final int JOURNAL_INVOKE_PENDING        = 9;

   /**
    * Dispatches the journal entry's data to the specified process.
    */
   public void dispatchToProcess(IAeBusinessProcess aProcess) throws AeBusinessProcessException;

   /**
    * Returns one of {@link #JOURNAL_ALARM}, {@link #JOURNAL_INBOUND_RECEIVE},
    * {@link #JOURNAL_INVOKE_DATA}, {@link #JOURNAL_INVOKE_FAULT},
    * {@link #JOURNAL_SENT_REPLY}, {@link #JOURNAL_INVOKE_TRANSMITTED},
    * {@link #JOURNAL_COMPENSATE_SUBPROCESS}, {@link #JOURNAL_ENGINE_FAILURE},
    * or {@link #JOURNAL_INVOKE_PENDING}.
    */
   public int getEntryType();

   /**
    * Returns entry's id from storage.
    */
   public long getJournalId();

   /**
    * Returns the location id of the activity that received the entry's data.
    */
   public int getLocationId();

   /**
    * Serializes entry for persistent storage; may return <code>null</code>.
    */
   public AeFastDocument serialize(AeTypeMapping aTypeMapping) throws AeBusinessProcessException;
}
