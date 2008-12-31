// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/IAeRecoverableProcessManager.java,v 1.4 2006/11/08 18:17:4
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
package org.activebpel.rt.bpel.server.engine;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeProcessManager;
import org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateStorage;
import org.activebpel.rt.util.AeLongSet;

/**
 * Recoverable version of the process manager.
 */
public interface IAeRecoverableProcessManager extends IAeProcessManager
{
   /**
    * Acquires the process mutex for the given process.
    *
    * @param aProcessId
    */
   public void acquireProcessMutex(long aProcessId);

   /**
    * Returns synchronization object that enforces mutually exclusive execution
    * of recovery and the {@link AePersistentProcessManager#stop()} method.
    */
   public Object getRecoveryAndStopMutex();

   /**
    * Returns process state storage.
    */
   public IAeProcessStateStorage getStorage();

   /**
    * Adds the given journal entry ids to the set of journal entries to delete
    * when the process state is next saved.
    */
   public void journalEntriesDone(long aProcessId, AeLongSet aJournalIds);

   /**
    * Creates journal entry to recover an engine failure in the event that the
    * current recovery engine itself fails. 
    *
    * @param aProcessId
    * @param aDeadEngineId
    */
   public void journalEngineFailure(long aProcessId, int aDeadEngineId) throws AeBusinessProcessException;

   /**
    * Releases the process mutex for the given process.
    *
    * @param aProcessId
    */
   public void releaseProcessMutex(long aProcessId);

   /**
    * Sets the number of processes allowed in memory for recovery.
    *
    * @param aRecoveryProcessCount
    */
   public void setRecoveryProcessCount(int aRecoveryProcessCount);
}
