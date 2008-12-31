// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/test/AeDelegatingPersistentProcessManager.java,v 1.4 2006/01/03 20:34:5
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
package org.activebpel.rt.bpel.server.engine.recovery.test;

import java.io.Reader;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager;
import org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry;
import org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateStorage;

/**
 * Implements a persistent process manager that delegates all method calls to
 * an underlying delegate persistent process manager.
 */
public class AeDelegatingPersistentProcessManager extends AeDelegatingProcessManager implements IAePersistentProcessManager
{
   /**
    * Constructs a persistent process manager that delegates all method calls
    * to a delegate process manager constructed from the given configuration.
    *
    * @param aConfig
    */
   public AeDelegatingPersistentProcessManager(Map aConfig) throws AeException
   {
      super(aConfig);
   }

   /**
    * Returns the base persistent process manager.
    */
   protected IAePersistentProcessManager getBasePersistentProcessManager()
   {
      return (IAePersistentProcessManager) getBaseProcessManager();
   }

   /*=========================================================================
    * org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager methods
    *=========================================================================
    */

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager#dumpLog(long)
    */
   public Reader dumpLog(long aProcessId) throws AeBusinessProcessException
   {
      return getBasePersistentProcessManager().dumpLog(aProcessId);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager#getDeadlockTryCount()
    */
   public int getDeadlockTryCount()
   {
      return getBasePersistentProcessManager().getDeadlockTryCount();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager#getEffectiveProcessLimit()
    */
   public int getEffectiveProcessLimit()
   {
      return getBasePersistentProcessManager().getEffectiveProcessLimit();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager#getJournalEntry(long)
    */
   public IAeJournalEntry getJournalEntry(long aJournalId) throws AeBusinessProcessException
   {
      return getBasePersistentProcessManager().getJournalEntry(aJournalId);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager#getMaxProcessCount()
    */
   public int getMaxProcessCount()
   {
      return getBasePersistentProcessManager().getMaxProcessCount();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager#getProcessLog(long)
    */
   public String getProcessLog(long aProcessId) throws AeBusinessProcessException
   {
      return getBasePersistentProcessManager().getProcessLog(aProcessId);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager#getStorage()
    */
   public IAeProcessStateStorage getStorage()
   {
      return getBasePersistentProcessManager().getStorage();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager#isContainerManaged(long)
    */
   public boolean isContainerManaged(long aProcessId)
   {
      return getBasePersistentProcessManager().isContainerManaged(aProcessId);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager#isLoaded(long)
    */
   public boolean isLoaded(long aProcessId)
   {
      return getBasePersistentProcessManager().isLoaded(aProcessId);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager#isPersistent(long)
    */
   public boolean isPersistent(long aProcessId)
   {
      return getBasePersistentProcessManager().isPersistent(aProcessId);
   }
}
