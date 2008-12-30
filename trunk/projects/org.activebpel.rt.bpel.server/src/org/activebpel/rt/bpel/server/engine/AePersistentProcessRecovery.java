// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/AePersistentProcessRecovery.java,v 1.15 2006/11/08 18:17:4
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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.activity.AeActivityInvokeImpl;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.recovery.AeRecoveryEngineFactory;
import org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryEngine;
import org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateStorage;
import org.activebpel.rt.util.AeLongSet;

/**
 * Implements recovering processes from persistent process storage.
 */
public class AePersistentProcessRecovery implements IAeProcessRecovery
{
   /** Mutex object for critical section in <code>prepareToRecover()</code>. */
   private static final Object sPrepareMutex = new Object();

   /** The recoverable process manager. */
   private final IAeRecoverableProcessManager mProcessManager;

   /** <code>true</code> if and only if <code>prepareToRecover</code> has been called. */
   private boolean mPrepared;

   /** The set of ids of processes that have been prepared for recovery. */
   private AeLongSet mPreparedProcessIds;

   /** The recovery engine. */
   private IAeRecoveryEngine mRecoveryEngine;

   /**
    * Constructs recovery implementation for the specified process manager.
    *
    * @param aProcessManager
    */
   public AePersistentProcessRecovery(IAeRecoverableProcessManager aProcessManager)
   {
      mProcessManager = aProcessManager;
   }

   /**
    * Writes debugging output.
    */
   protected void debug(String aMessage)
   {
      if (getProcessManager() instanceof AePersistentProcessManager)
      {
         ((AePersistentProcessManager) getProcessManager()).debug(aMessage);
      }
   }

   /**
    * Returns the business process engine.
    */
   protected IAeBusinessProcessEngineInternal getEngine()
   {
      return getProcessManager().getEngine();
   }

   /**
    * Returns uncleared journal entries for the given process as a list of
    * {@link IAeJournalEntry} instances in the order that they were saved.
    *
    * @param aProcessId
    */
   protected List getJournalEntries(long aProcessId)
   {
      try
      {
         return getStorage().getJournalEntries(aProcessId);
      }
      catch (AeStorageException e)
      {
         AeException.logError(e, AeMessages.format("AePersistentProcessRecovery.ERROR_0", aProcessId)); //$NON-NLS-1$
         return Collections.EMPTY_LIST;
      }
   }

   /**
    * Returns the journal entry ids from the given journal entries.
    *
    * @param aJournalEntries
    */
   protected AeLongSet getJournalIds(List aJournalEntries)
   {
      AeLongSet result = new AeLongSet();

      for (Iterator i = aJournalEntries.iterator(); i.hasNext(); )
      {
         IAeJournalEntry entry = (IAeJournalEntry) i.next();
         result.add(entry.getJournalId());
      }

      return result;
   }

   /**
    * Returns invokes pending for the specified process.
    *
    * @param aProcess
    */
   protected Set getPendingInvokes(IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      return aProcess.getProcessSnapshot().getPendingInvokes();
   }

   /**
    * Returns the set of ids of processes that have been prepared for recovery.
    */
   protected AeLongSet getPreparedProcessIds()
   {
      if (mPreparedProcessIds == null)
      {
         mPreparedProcessIds = new AeLongSet(new TreeSet());
      }

      return mPreparedProcessIds;
   }

   /**
    * Returns the process with the specified process id.
    *
    * @param aProcessId
    */
   protected IAeBusinessProcess getProcess(long aProcessId) throws AeBusinessProcessException
   {
      return getProcessManager().getProcess(aProcessId);
   }

   /**
    * Returns the recoverable process manager.
    */
   protected IAeRecoverableProcessManager getProcessManager()
   {
      return mProcessManager;
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
    * Returns the set of ids for processes with work to be recovered.
    */
   protected Set getRecoveryProcessIds() throws AeStorageException
   {
      return getStorage().getRecoveryProcessIds();
   }

   /**
    * Convenience method that returns the process state storage.
    */
   protected IAeProcessStateStorage getStorage()
   {
      return getProcessManager().getStorage();
   }

   /**
    * Returns <code>true</code> if and only if the engine is running.
    */
   protected static boolean isEngineRunning()
   {
      return AeEngineFactory.getEngine().isRunning();
   }

   /**
    * Returns <code>true</code> if and only if <code>prepareToRecover</code>
    * has been called.
    */
   protected boolean isPrepared()
   {
      return mPrepared;
   }

   /**
    * Overrides method to load processes that require recovery.
    *
    * @see org.activebpel.rt.bpel.server.engine.IAeProcessRecovery#prepareToRecover()
    */
   public void prepareToRecover()
   {
      mPrepared = true;

      Set ids;

      try
      {
         ids = getRecoveryProcessIds();
      }
      catch (AeStorageException e)
      {
         AeException.logError(e, AeMessages.getString("AePersistentProcessRecovery.ERROR_2")); //$NON-NLS-1$
         ids = Collections.EMPTY_SET;
      }

      // This is synchronized globally to prevent one recovery from clobbering
      // the process count required for another concurrent recovery.
      synchronized (sPrepareMutex)
      {
         // Make sure that the process manager has enough room to load the
         // process wrappers.
         getProcessManager().setRecoveryProcessCount(ids.size());

         try
         {
            // Grab mutexes for the processes that require recovery. We release
            // the mutexes in recover() as we recover individual processes.
            for (Iterator i = ids.iterator(); i.hasNext(); )
            {
               long processId = ((Number) i.next()).longValue();

               getProcessManager().acquireProcessMutex(processId);
               getPreparedProcessIds().add(processId);
            }
         }
         finally
         {
            getProcessManager().setRecoveryProcessCount(0);
         }
      }
   }

   /**
    * Overrides method to call {@link #recoverUnderMutex()} after synchronizing
    * on the process manager's
    * {@link IAeRecoverableProcessManager#getRecoveryAndStopMutex()} object.
    *  
    * @see org.activebpel.rt.bpel.server.engine.IAeProcessRecovery#recover()
    */
   public void recover()
   {
      // To fix defect 1527, "Getting an IllegalArgumentException when
      // recovery is attempting to be done but the server is in the middle of
      // shutting down," we need to keep the engine from stopping the timer
      // manager while in the middle of recovery. AeBpelEngine stops the timer
      // manager after stopping all the managers, so by synchronizing on
      // AePersistentProcessManager#getRecoveryStopMutex() here we prevent
      // AePersistentProcessManager#stop() from returning until we return.
      synchronized (getProcessManager().getRecoveryAndStopMutex())
      {
         recoverUnderMutex();
      }
   }

   /**
    * Recovers pending work. Calls <code>prepareToRecover()</code> if not yet prepared.
    */
   protected void recoverUnderMutex()
   {
      if (!isPrepared())
      {
         prepareToRecover();
      }

      AeLongSet ids = getPreparedProcessIds();
      long millis = System.currentTimeMillis();

      int n = ids.size();
      debug("Recovering " + n + " process" + ((n == 1) ? "" : "es")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

      // Iterate over process ids that have been prepared for recovery.
      for (Iterator i = ids.iterator(); i.hasNext(); )
      {
         long processId = ((Number) i.next()).longValue();

         try
         {
            IAeBusinessProcess process;

            try
            {
               // Fix defect 1527, "Getting an IllegalArgumentException when
               // recovery is attempting to be done but the server is in the
               // middle of shutting down," by performing recovery only while
               // the engine is running. When someone tries to shut down the
               // engine, AeBpelEngine transitions first to the state
               // IAeEngineAdministration.STOPPING, which is not a running
               // state. This gives us a chance to get out of recovery before
               // the engine stops the timer manager (because we are running
               // under a mutex that keeps the process manager from stopping
               // while we are in this loop).
               //
               // This test also addresses defect 1475, "You cannot stop the
               // ActiveBPEL engine while it is in the middle of recovery,"
               // because recovery stops as soon as the engine starts to shut
               // down.
               if (isEngineRunning())
               {
                  process = getProcess(processId);
               }
               else
               {
                  process = null;
               }
            }
            finally
            {
               // Make sure to release the process mutex that we acquired in
               // prepareToRecover(), but remember that releaseProcess() has to
               // be the last call in order to persist the recovered state.
               getProcessManager().releaseProcessMutex(processId);
            }

            if (process != null)
            {
               try
               {
                  recover(process);
               }
               finally
               {
                  releaseProcess(process);
               }
            }
         }
         // Catch even null pointer and other runtime exceptions here to
         // prevent problems recovering one process from affecting other
         // processes or engine startup.
         catch (Throwable t)
         {
            AeException.logError(t, AeMessages.format("AePersistentProcessRecovery.ERROR_12", processId)); //$NON-NLS-1$
         }
      }

      long elapsed = System.currentTimeMillis() - millis;
      debug("Recovery done (" + elapsed + " millis)"); //$NON-NLS-1$ //$NON-NLS-2$
   }

   /**
    * Recovers pending work for the specified process.
    *
    * @param aProcess
    */
   protected void recover(IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      // Get pending invokes now for a before-and-after comparison.
      Set pendingInvokes = getPendingInvokes(aProcess);

      // Dispatch journal entries anew.
      //
      // Note: Dispatch journal entries before restarting pending invokes,
      // because some of the journal entries may be received data or faults for
      // pending invokes (in which case the invokes will no longer be pending
      // after recovery).
      long processId = aProcess.getProcessId();
      List journalEntries = getJournalEntries(processId);

      getRecoveryEngine().recover(aProcess, journalEntries, true);

      // TODO (KR) Administrator should have a way to purge process and its journal entries if recovery fails.
      getProcessManager().journalEntriesDone(processId, getJournalIds(journalEntries));

      // Trim the set of pending invokes that we have to restart to those that
      // were pending both before and after we recovered journal entries. In
      // other words,
      // (1) if an invoke was pending before recovery but is not pending after
      //     recovery, then the recovery engine must have matched the invoke
      //     to a journal entry for the invoke result,
      // (2) if an invoke is pending after recovery but was not pending before
      //     recovery, then the recovery engine must have queued the invoke.
      pendingInvokes.retainAll(getPendingInvokes(aProcess));

      // Restart pending invokes.
      restartPendingInvokes(aProcess, pendingInvokes);
   }

   /**
    * Releases a process locked into memory by <code>getProcess</code>.
    *
    * @param aProcess The process to release.
    */
   protected void releaseProcess(IAeBusinessProcess aProcess)
   {
      getProcessManager().releaseProcess(aProcess);
   }

   /**
    * Restarts the pending invokes for the given process.
    *
    * @param aProcess
    * @param aPendingInvokes
    * @throws AeBusinessProcessException
    */
   protected void restartPendingInvokes(IAeBusinessProcess aProcess, Set aPendingInvokes) throws AeBusinessProcessException
   {
      for (Iterator i = aPendingInvokes.iterator(); i.hasNext(); )
      {
         AeActivityInvokeImpl invoke = (AeActivityInvokeImpl) i.next();
         // TODO (KR) Decide if isqueued is appropriate here
         if (invoke.getState() == AeBpelState.EXECUTING && (! invoke.isQueued()) )
         {
            invoke.setRawState(AeBpelState.INACTIVE);
            aProcess.queueObjectToExecute(invoke);
         }
      }
   }
}
