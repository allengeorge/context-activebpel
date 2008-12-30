// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/test/AeRepeatingProcessManager.java,v 1.3 2006/10/18 23:32:5
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

import java.util.List;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.def.visitors.AeDefToImplVisitor;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.IAeProcessDeployment;
import org.activebpel.rt.bpel.server.engine.AePersistentProcessManager;
import org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager;
import org.activebpel.rt.bpel.server.engine.process.AeProcessStateWriter;
import org.activebpel.rt.bpel.server.engine.process.IAeProcessStateReader;
import org.activebpel.rt.bpel.server.engine.recovery.AeRecoveryEngineFactory;
import org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryEngine;
import org.activebpel.rt.util.AeLongSet;

/**
 * Implements a process manager that repeats the execution of a process through
 * its journal entries each time the process is saved.
 */
public class AeRepeatingProcessManager extends AeDelegatingPersistentProcessManager implements IAePersistentProcessManager
{
   /** The process state reader from the underlying persistent process manager. */
   private IAeProcessStateReader mProcessStateReader;

   /** The recovery engine. */
   private IAeRecoveryEngine mRecoveryEngine;

   /**
    * Constructs a repeating process manager from the given configuration map.
    */
   public AeRepeatingProcessManager(Map aConfig) throws Exception
   {
      super(aConfig);

      // Replace default process writer with one that repeats process
      // execution.
      AePersistentProcessManager base = (AePersistentProcessManager) getBasePersistentProcessManager();
      base.setProcessStateWriter(new AeRepeatingProcessStateWriter(base));
   }

   /**
    * Returns the process state reader from the underlying persistent process
    * manager.
    */
   protected IAeProcessStateReader getProcessStateReader()
   {
      if (mProcessStateReader == null)
      {
         AePersistentProcessManager base = (AePersistentProcessManager) getBasePersistentProcessManager();
         mProcessStateReader = base.getProcessStateReader(); 
      }

      return mProcessStateReader;
   }

   /**
    * Returns the recovery engine.
    */
   protected IAeRecoveryEngine getRecoveryEngine()
   {
      if (mRecoveryEngine == null)
      {
         mRecoveryEngine = AeRecoveryEngineFactory.getInstance().newRecoveryEngine();
      }

      return mRecoveryEngine;
   }

   /**
    * Repeats the most recent execution of the given process by replaying its
    * journal entries.
    */
   protected void repeatProcess(IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      long processId = aProcess.getProcessId();
      IAeProcessDeployment deployment = (IAeProcessDeployment) aProcess.getProcessPlan();

      // Create a new instance of the process.
      IAeBusinessProcess clone = AeDefToImplVisitor.createProcess(processId, getEngine(), deployment);

      // Restore its state from storage.
      getProcessStateReader().readProcess(clone);

      // Get the journal entries from storage.
      List journalEntries = getStorage().getJournalEntries(processId);

      // Replay the journal entries to the clone.
      getRecoveryEngine().recover(clone, journalEntries, false);
   }

   /**
    * Implements a process state writer that repeats process execution before
    * saving its state.
    */
   protected class AeRepeatingProcessStateWriter extends AeProcessStateWriter
   {
      /**
       * Constructs a process state writer that repeats process execution.
       *
       * @param aProcessManager
       */
      public AeRepeatingProcessStateWriter(IAePersistentProcessManager aProcessManager)
      {
         super(aProcessManager);
      }

      /**
       * @see org.activebpel.rt.bpel.server.engine.process.IAeProcessStateWriter#writeProcess(org.activebpel.rt.bpel.IAeBusinessProcess, org.activebpel.rt.util.AeLongSet, org.activebpel.rt.util.AeLongSet)
       */
      public int writeProcess(IAeBusinessProcess aProcess, AeLongSet aCompletedJournalIds, AeLongSet aCompletedTransmissionIds) throws AeBusinessProcessException
      {
         try
         {
            repeatProcess(aProcess);
         }
         catch (Throwable t)
         {
            AeException.logError(t, AeMessages.format("AeRepeatingProcessManager.ERROR_REPEAT", aProcess.getProcessId())); //$NON-NLS-1$
         }

         // Now save the process state.
         return super.writeProcess(aProcess, aCompletedJournalIds, aCompletedTransmissionIds);
      }
   }
}
