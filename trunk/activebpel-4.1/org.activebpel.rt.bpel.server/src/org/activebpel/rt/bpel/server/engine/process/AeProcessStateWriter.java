// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/process/AeProcessStateWriter.java,v 1.23 2007/04/23 23:40:3
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
package org.activebpel.rt.bpel.server.engine.process;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.IAeProcessManager;
import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.impl.queue.AeInboundReceive;
import org.activebpel.rt.bpel.impl.queue.AeReply;
import org.activebpel.rt.bpel.impl.storage.IAeProcessSnapshot;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.bpel.server.engine.AePersistentProcessManager;
import org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager;
import org.activebpel.rt.bpel.server.engine.IAeProcessLogger;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeAlarmJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeCompensateSubprocessJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeEngineFailureJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeInboundReceiveJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeInvokeDataJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeInvokeFaultJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeInvokePendingJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeInvokeTransmittedJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeSentReplyJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry;
import org.activebpel.rt.bpel.server.engine.storage.AeLocationVersionSet;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.IAeLocationVersionSet;
import org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateConnection;
import org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateStorage;
import org.activebpel.rt.bpel.server.engine.transaction.AeTransactionException;
import org.activebpel.rt.bpel.server.engine.transaction.AeTransactionManager;
import org.activebpel.rt.bpel.server.logging.IAePersistentLogger;
import org.activebpel.rt.bpel.server.logging.IAeProcessLogEntry;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeLongSet;

/**
 * Writes process state to persistent storage.
 */
public class AeProcessStateWriter implements IAeProcessStateWriter
{
   /** The process manager that owns this process state writer. */
   private final IAePersistentProcessManager mProcessManager;

   /**
    * Constructs the process state writer for the given process manager.
    *
    * @param aProcessManager
    */
   public AeProcessStateWriter(IAePersistentProcessManager aProcessManager)
   {
      mProcessManager = aProcessManager;
   }

   /**
    * Writes debugging output.
    */
   public static void debug(String aMessage)
   {
      if (isDebug())
      {
         System.out.println(aMessage);
      }
   }

   /**
    * Writes formatted debugging output.
    */
   public static void debug(String aPattern, Object[] aArguments)
   {
      if (isDebug()) // test for debugging before formatting
      {
         debug(MessageFormat.format(aPattern, aArguments));
      }
   }

   /**
    * Returns the engine for this process state writer.
    */
   protected IAeBusinessProcessEngineInternal getEngine()
   {
      return getProcessManager().getEngine();
   }

   /**
    * Returns log entry for the current portion of the process log.
    *
    * @param aProcessId
    */
   protected IAeProcessLogEntry getProcessLogEntry(long aProcessId)
   {
      IAeProcessLogEntry entry = null;
      IAeProcessLogger logger = AeEngineFactory.getLogger();

      if (logger instanceof IAePersistentLogger)
      {
         entry = ((IAePersistentLogger) logger).getLogEntry(aProcessId);
      }

      return entry;
   }

   /**
    * Returns the process manager that owns this process state writer.
    */
   protected IAePersistentProcessManager getProcessManager()
   {
      return mProcessManager;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAePersistentProcessManager#getStorage()
    */
   public IAeProcessStateStorage getStorage()
   {
      return getProcessManager().getStorage();
   }

   /**
    * @return <code>true</code> if and only if the process manager is in debug
    * mode.
    */
   public static boolean isDebug()
   {
      return AePersistentProcessManager.isDebug();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.process.IAeProcessStateWriter#journalAlarm(long, int, int, int)
    */
   public long journalAlarm(long aProcessId, int aLocationId, int aGroupId, int aAlarmId)
   {
      debug(
         "Process {0,number,0}: received alarm for location {1,number,0} with alarmId {2,number,0}", //$NON-NLS-1$
         new Object[] { new Long(aProcessId), new Integer(aLocationId), new Integer(aAlarmId) }
      );
      return writeJournalEntry(aProcessId, new AeAlarmJournalEntry(aLocationId, aGroupId, aAlarmId));
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.process.IAeProcessStateWriter#journalInboundReceive(long, int, org.activebpel.rt.bpel.impl.queue.AeInboundReceive, boolean)
    */
   public long journalInboundReceive(long aProcessId, int aLocationId, AeInboundReceive aInboundReceive)
   {
      debug(
         "Process {0,number,0}: received message for location {1,number,0}", //$NON-NLS-1$
         new Object[] { new Long(aProcessId), new Integer(aLocationId) }
      );
      return writeJournalEntry(aProcessId, new AeInboundReceiveJournalEntry(aLocationId, aInboundReceive));
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.process.IAeProcessStateWriter#journalInvokeData(long, int, int, org.activebpel.rt.message.IAeMessageData, java.util.Map)
    */
   public long journalInvokeData(long aProcessId, int aLocationId, long aTransmissionId, IAeMessageData aMessageData, Map aProcessProperties)
   {
      debug(
         "Process {0,number,0}: received data for invoke at location {1,number,0}", //$NON-NLS-1$
         new Object[] { new Long(aProcessId), new Integer(aLocationId) }
      );
      return writeJournalEntry(aProcessId, new AeInvokeDataJournalEntry(aLocationId, aTransmissionId, aMessageData, aProcessProperties));
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.process.IAeProcessStateWriter#journalInvokeFault(long, int, int, org.activebpel.rt.bpel.IAeFault, java.util.Map)
    */
   public long journalInvokeFault(long aProcessId, int aLocationId, long aTransmissionId, IAeFault aFault, Map aProcessProperties)
   {
      debug(
         "Process {0,number,0}: received fault for invoke at location {1,number,0}", //$NON-NLS-1$
         new Object[] { new Long(aProcessId), new Integer(aLocationId) }
      );
      return writeJournalEntry(aProcessId, new AeInvokeFaultJournalEntry(aLocationId, aTransmissionId, aFault, aProcessProperties));
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.process.IAeProcessStateWriter#journalSentReply(long, org.activebpel.rt.bpel.impl.queue.AeReply, java.util.Map)
    */
   public long journalSentReply(long aProcessId, AeReply aSentReply, Map aProcessProperties) throws AeBusinessProcessException
   {
      debug(
         "Process {0,number,0}: sent reply id {1,number,0}", //$NON-NLS-1$
         new Object[] { new Long(aProcessId), new Long( aSentReply.getReplyId()) }
      );
      return writeJournalEntry(aProcessId, new AeSentReplyJournalEntry(aSentReply, aProcessProperties));
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.process.IAeProcessStateWriter#journalInvokeTransmitted(long, int, long, long)
    */
   public long journalInvokeTransmitted(long aProcessId, int aLocationId, long aTransmissionId) throws AeBusinessProcessException
   {
      debug(
         "Process {0,number,0}: transmitted invoke at location {1,number,0} with txid {2,number,0}", //$NON-NLS-1$
         new Object[] { new Long(aProcessId), new Integer(aLocationId), new Long(aTransmissionId) }
      );
      return writeJournalEntry(aProcessId, new AeInvokeTransmittedJournalEntry(aLocationId, aTransmissionId));
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.process.IAeProcessStateWriter#journalCompensateSubprocess(long, java.lang.String)
    */
   public long journalCompensateSubprocess(long aProcessId, String aCoordinationId) throws AeBusinessProcessException
   {
      debug(
         "Process {0,number,0}: subprocess compensate for coordination id {1}", //$NON-NLS-1$
         new Object[] { new Long(aProcessId), aCoordinationId }
      );
      return writeJournalEntry( aProcessId, new AeCompensateSubprocessJournalEntry(aCoordinationId) );     
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.process.IAeProcessStateWriter#journalInvokePending(long, int)
    */
   public long journalInvokePending(long aProcessId, int aLocationId) throws AeBusinessProcessException
   {
      debug(
         "Process {0,number,0}: invoke pending at location {1,number,0}", //$NON-NLS-1$
         new Object[] { new Long(aProcessId), new Integer(aLocationId) }
      );
      return writeJournalEntry(aProcessId, new AeInvokePendingJournalEntry(aLocationId));
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.process.IAeProcessStateWriter#journalEngineFailure(long, int)
    */
   public long journalEngineFailure(long aProcessId, int aEngineId)
   {
      debug(
         "Process {0,number,0}: recovery from engine {1,number,0}", //$NON-NLS-1$
         new Object[] { new Long(aProcessId), new Integer(aEngineId) }
      );
      return writeJournalEntry(aProcessId, new AeEngineFailureJournalEntry(aEngineId));
   }

   /**
    * Rolls back the active transaction manager transaction, consuming any
    * exceptions.
    */
   protected void rollbackTransaction(long aProcessId)
   {
      try
      {
         AeTransactionManager.getInstance().rollback();
      }
      catch (AeTransactionException e)
      {
         AeException.logError(e, AeMessages.format("AeProcessStateWriter.ERROR_RollbackTransaction", aProcessId)); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.process.IAeProcessStateWriter#writeProcess(org.activebpel.rt.bpel.IAeBusinessProcess, org.activebpel.rt.util.AeLongSet, org.activebpel.rt.util.AeLongSet)
    */
   public int writeProcess(IAeBusinessProcess aProcess, AeLongSet aCompletedJournalIds, AeLongSet aCompletedTransmissionIds) throws AeBusinessProcessException
   {
      long processId = aProcess.getProcessId();
      int n; // pending invokes count

      // Get log entry for the current portion of the process log.
      IAeProcessLogEntry logEntry = getProcessLogEntry(processId);

      if (getProcessManager().isContainerManaged(processId))
      {
         IAeProcessStateConnection connection = getStorage().getConnection(processId, true);

         try
         {
            n = writeProcess(connection, aProcess, aCompletedJournalIds, aCompletedTransmissionIds, logEntry);

            if (n > 0)
            {
               debug(
                  "Process {0,number,0}: *** service flow has {1,choice,1#1 invoke|1<{1,number,0} invokes} pending ***", //$NON-NLS-1$
                  new Object[] { new Long(processId), new Integer(n) }
               );
            }
         }
         finally
         {
            getStorage().releaseConnection(connection);
         }
      }
      else
      {
         int tryCount = getProcessManager().getDeadlockTryCount();
         AeStorageException firstException = null;

         for (int tries = 0; true; )
         {
            // begin transaction.
            AeTransactionManager.getInstance().begin();
            IAeProcessStateConnection connection = getStorage().getConnection(processId, false);
            try
            {
               n = writeProcess(connection, aProcess, aCompletedJournalIds, aCompletedTransmissionIds, logEntry);

               // Commit all changes and stop looping.
               AeTransactionManager.getInstance().commit();
               break;
            }
            catch (AeStorageException e)
            {
               // Rollback all changes.
               rollbackTransaction(processId);

               // Retry if this is a SQL exception and we haven't exhausted the
               // try count.  
               // Note(Eric): Tamino will throw a SQLException specifically when a dead lock is detected (i.e. throws a common/well-known exception).               
               // TODO (EPW) We need to have a method isRetryableException on the provider factory...
               if ((e.getCause() instanceof SQLException) && (++tries < tryCount))
               {
                  if (firstException == null)
                  {
                     firstException = e;
                  }

                  AeException.logError(null, AeMessages.format("AeProcessStateWriter.ERROR_0", processId)); //$NON-NLS-1$
               }
               // Otherwise, we're done.
               else
               {
                  if (firstException != null)
                  {
                     AeException.logError(firstException.getCause(), AeMessages.format("AeProcessStateWriter.ERROR_SaveProcessFirstException", processId)); //$NON-NLS-1$
                  }

                  throw e;
               }
            }
            catch (AeTransactionException e)
            {
               rollbackTransaction(processId);

               throw e;
            }
         } // end for loop
      }

      // If we get to this point, then we can safely clear this log entry's
      // portion from the process log.
      if (logEntry != null)
      {
         logEntry.clearFromLog();
      }

      return n;
   }

   /**
    * Saves given {@link IAeJournalEntry} instance for possible recovery in the
    * event of engine failure.
    */
   protected long writeJournalEntry(long aProcessId, IAeJournalEntry aJournalEntry)
   {
      if (getProcessManager().isPersistent(aProcessId))
      {
         int tryCount = getProcessManager().getDeadlockTryCount();
         AeStorageException firstException = null;

         for (int tries = 0; true; )
         {
            try
            {
               // Successful insertion of the journal entry breaks the loop.
               return getStorage().writeJournalEntry(aProcessId, aJournalEntry);
            }
            catch (AeStorageException e)
            {
               // Retry if this is a SQL exception and we haven't exhausted the
               // try count.  
               // TODO (EPW) We need to have a method isRetryableException on the provider factory...
               if ((e.getCause() instanceof SQLException) && (++tries < tryCount))
               {
                  if (firstException == null)
                  {
                     firstException = e;
                  }

                  AeException.logError(null, AeMessages.format("AeProcessStateWriter.ERROR_3", aProcessId)); //$NON-NLS-1$
               }
               // Otherwise, we're done.
               else
               {
                  if (firstException != null)
                  {
                     AeException.logError(firstException.getCause(), AeMessages.format("AeProcessStateWriter.ERROR_WriteJournalEntryFirstException", aProcessId)); //$NON-NLS-1$
                  }

                  AeException.logError(e, AeMessages.format("AeProcessStateWriter.ERROR_WriteJournalEntryLastException", aProcessId)); //$NON-NLS-1$

                  // TODO (KR) Should this throw the storage exception rather than break?
                  break;
               }
            }
         }
      }

      return IAeProcessManager.NULL_JOURNAL_ID;
   }

   /**
    * Saves process state and variables to the given storage connection.
    *
    * @param aConnection
    * @param aProcess
    * @param aCompletedJournalIds
    * @param aCompletedTransmissionIds
    * @param aLogEntry
    * @return The number of pending invoke activities (for debugging output).
    * @throws AeBusinessProcessException
    */
   protected int writeProcess(IAeProcessStateConnection aConnection, IAeBusinessProcess aProcess,
         AeLongSet aCompletedJournalIds, AeLongSet aCompletedTransmissionIds, IAeProcessLogEntry aLogEntry)
         throws AeBusinessProcessException
   {
      // Get a process snapshot.
      IAeProcessSnapshot snapshot = aProcess.getProcessSnapshot();

      // Save the process state.
      AeFastDocument processDocument = snapshot.serializeProcess(true);
      int processState = aProcess.getProcessState();
      int processStateReason = aProcess.getProcessStateReason();
      Date startDate = aProcess.getStartDate();
      Date endDate = aProcess.getEndDate();
      int pendingInvokesCount = snapshot.getPendingInvokes().size();

      aConnection.saveProcess(processDocument, processState, processStateReason, startDate, endDate, pendingInvokesCount);

      // Build the set of live variables for
      // IAeProcessStateConnection#trimStoredVariables.
      IAeLocationVersionSet liveSet = new AeLocationVersionSet();

      // Iterate through all live variable location paths.
      for (Iterator i = snapshot.getVariableLocationPaths().iterator(); i.hasNext(); )
      {
         String locationPath = (String) i.next();
         int locationId = aProcess.getLocationId(locationPath);
         Set versionNumbers = snapshot.getVariableVersionNumbers(locationPath);

         // Iterate through all version numbers for this location path.
         for (Iterator j = versionNumbers.iterator(); j.hasNext(); )
         {
            int versionNumber = ((Number) j.next()).intValue();
            liveSet.add(locationId, versionNumber);
            
            IAeVariable variable = snapshot.getVariable(locationPath, versionNumber);
            if ((variable.hasData() || variable.hasAttachments()) && !aConnection.isStoredVariable(locationId, versionNumber))
            {
               AeFastDocument variableDocument = snapshot.serializeVariable(variable);
               aConnection.saveVariable(aProcess, variable, variableDocument);
            }
         }
      }

      // Trim the set of stored variables to those that are live now.
      aConnection.trimStoredVariables(liveSet);

      // Save new persistent log data.
      aConnection.saveLog(aLogEntry);

      // Remove journal entries that have been incorporated into the process
      // state.
      aConnection.removeJournalEntries(aCompletedJournalIds);
      
      // Remove completed transmission ids.      
      try
      {
         AeEngineFactory.getTransmissionTracker().remove(aCompletedTransmissionIds);
      }
      catch(Exception e)
      {
         throw new AeBusinessProcessException(e.getMessage(), e);
      }

      return pendingInvokesCount;
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.process.IAeProcessStateWriter#getNextJournalId()
    */
   public long getNextJournalId() throws AeBusinessProcessException
   {
      try
      {
         return getStorage().getNextJournalId();
      }
      catch(Throwable t)
      {
         throw new AeBusinessProcessException(t.getMessage(), t);
      }
   }
}
