// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/IAeRecoveryEngine.java,v 1.1 2005/07/12 00:27:0
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

import java.util.List;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;

/**
 * Extends {@link org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal}
 * to define the interface for a business process engine that can recover the
 * state of a process from a list of
 * {@link org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry}
 * instances.
 */
public interface IAeRecoveryEngine extends IAeBusinessProcessEngineInternal
{
   /**
    * Recovers the state of the specified process from the given list of {@link
    * org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry}
    * instances and returns a list of {@link
    * org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem}
    * instances representing recovered requests. Queues the recovered items if
    * <code>aQueueRecoveredItems</code> is <code>true</code>.
    *
    * @param aProcess
    * @param aJournalEntries
    * @param aQueueRecoveredItems
    * @return A list of {@link org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem} instances.
    * @throws AeBusinessProcessException
    */
   public List recover(IAeBusinessProcess aProcess, List aJournalEntries, boolean aQueueRecoveredItems) throws AeBusinessProcessException;
}
