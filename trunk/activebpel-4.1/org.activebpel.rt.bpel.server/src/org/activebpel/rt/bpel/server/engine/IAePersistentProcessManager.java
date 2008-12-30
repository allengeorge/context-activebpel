// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/IAePersistentProcessManager.java,v 1.9 2006/01/03 20:35:0
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

import java.io.Reader;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeProcessManager;
import org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry;
import org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateStorage;

/**
 * Persistent version of the process manager.
 */
public interface IAePersistentProcessManager extends IAeProcessManager
{
   /**
    * Gets a Reader to the process's log.
    * @param aProcessId
    * @throws AeBusinessProcessException
    */
   public Reader dumpLog(long aProcessId) throws AeBusinessProcessException;

   /**
    * Returns number of times to retry deadlocked transactions.
    */
   public int getDeadlockTryCount();

   /**
    * Returns the effective process limit, which is the greater of the
    * configured maximum process count or the number of processes required for
    * recovery.
    */
   public int getEffectiveProcessLimit();

   /**
    * Returns the journal entry with the given journal id.
    */
   public IAeJournalEntry getJournalEntry(long aJournalId) throws AeBusinessProcessException;

   /**
    * Returns the maximum number of processes allowed in memory.
    */
   public int getMaxProcessCount();

   /**
    * Gets the process log for the given pid.
    * @param aProcessId
    */
   public String getProcessLog(long aProcessId) throws AeBusinessProcessException;

   /**
    * Returns process state storage.
    */
   public IAeProcessStateStorage getStorage();

   /**
    * Returns <code>true</code> if and only if process is container-managed.
    */
   public boolean isContainerManaged(long aProcessId);

   /**
    * Returns <code>true</code> if and only if the given process id currently
    * resident in memory.
    *
    * @param aProcessId
    */
   public boolean isLoaded(long aProcessId);
   
   /**
    * Returns <code>true</code> if and only if process is persistent.
    */
   public boolean isPersistent(long aProcessId);
}
