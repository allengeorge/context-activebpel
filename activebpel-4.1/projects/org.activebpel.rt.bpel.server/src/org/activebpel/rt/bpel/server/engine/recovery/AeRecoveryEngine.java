// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/AeRecoveryEngine.java,v 1.16 2007/07/13 22:17:4
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.config.IAeEngineConfiguration;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.IAeAlarmReceiver;
import org.activebpel.rt.bpel.impl.IAeAttachmentManager;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.IAeCoordinationManagerInternal;
import org.activebpel.rt.bpel.impl.IAeEnginePartnerLinkStrategy;
import org.activebpel.rt.bpel.impl.IAeLockManager;
import org.activebpel.rt.bpel.impl.activity.IAeMessageReceiverActivity;
import org.activebpel.rt.bpel.impl.queue.AeReply;
import org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.AeAbstractServerEngine;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeEngineFailureJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeInvokeTransmittedJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeSentReplyJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry;
import org.activebpel.rt.bpel.server.engine.recovery.recovered.AeRecoveredRemoveAlarmItem;
import org.activebpel.rt.bpel.server.engine.recovery.recovered.AeRecoveredRemoveReceiverItem;
import org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeLongSet;
import org.activebpel.wsio.IAeMessageAcknowledgeCallback;

/**
 * Implements a business process engine that recovers the state of a process
 * from a list of
 * {@link org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry}
 * instances.
 */
public class AeRecoveryEngine extends AeAbstractServerEngine implements IAeRecoveryEngine
{
   private int mEngineId;
   /**
    * Constructs a recovery engine.
    *
    * @param aEngineConfiguration
    * @param aRecoveryQueueManager
    * @param aRecoveryProcessManager
    * @param aRecoveryLockManager
    * @param aRecoveryAttachmentManager
    * @param aPartnerLinkStrategy
    * @param aTransmissionTracker
    * @param aCustomManagersMap
    * @param aEngineId
    */
   public AeRecoveryEngine(
      IAeEngineConfiguration aEngineConfiguration,
      IAeRecoveryQueueManager aRecoveryQueueManager,
      IAeRecoveryProcessManager aRecoveryProcessManager,
      IAeLockManager aRecoveryLockManager,
      IAeAttachmentManager aRecoveryAttachmentManager,
      IAeEnginePartnerLinkStrategy aPartnerLinkStrategy,
      IAeCoordinationManagerInternal aCoordinationManager,
      IAeTransmissionTracker aTransmissionTracker,
      Map aCustomManagersMap,
      int aEngineId)
   {
      super(aEngineConfiguration, aRecoveryQueueManager, aRecoveryProcessManager, aRecoveryLockManager, aRecoveryAttachmentManager);

      setPartnerLinkStrategy(aPartnerLinkStrategy);
      setCoordinationManager(aCoordinationManager);
      setTransmissionTracker(aTransmissionTracker);
      setCustomManagers(aCustomManagersMap);
      // Since recovery engine extends BusiProcEngine, engine id will always be 1.
      // We need to get the engine id from the underlying engine.      
      mEngineId = aEngineId;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal#getEngineId()
    */
   public int getEngineId()
   {
      return mEngineId;
   }   
   
   /**
    * Adds recovered items to the given list that will remove queued requests
    * that are now stale (meaning that the corresponding activities are no
    * longer executing in the process).
    *
    * @param aRecoveredItems
    * @param aProcess
    * @param aStaleLocationIds
    */
   protected void addStaleRequestRemovalItems(List aRecoveredItems, IAeBusinessProcess aProcess, Set aStaleLocationIds)
   {
      long processId = aProcess.getProcessId();

      for (Iterator i = aStaleLocationIds.iterator(); i.hasNext(); )
      {
         int locationId = ((Number) i.next()).intValue();
         IAeBpelObject aImpl = aProcess.findBpelObject(locationId);
         
         if (aImpl instanceof IAeAlarmReceiver)
         {
            IAeAlarmReceiver alarmRec = (IAeAlarmReceiver) aImpl;
            IAeRecoveredItem removeAlarmItem = new AeRecoveredRemoveAlarmItem(processId, locationId, alarmRec.getAlarmId());
            aRecoveredItems.add(removeAlarmItem);
         }
         else if (aImpl instanceof IAeMessageReceiverActivity)
         {
            IAeRecoveredItem removeReceiverItem = new AeRecoveredRemoveReceiverItem(processId, locationId);
            aRecoveredItems.add(removeReceiverItem);
         }
      }
   }

   /**
    * Dispatches the journal entries to the given process.
    *
    * @param aJournalEntries
    * @param aProcess
    * @throws AeBusinessProcessException
    */
   protected void dispatchJournalEntries(List aJournalEntries, IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      // Loop until we dispatch all of the journal entries or the process
      // completes.
      Iterator i = aJournalEntries.iterator();

      while (i.hasNext() && !isProcessEnded(aProcess))
      {
         IAeJournalEntry entry = (IAeJournalEntry) i.next();
         
         // Dispatch the journal entry to the process.
         entry.dispatchToProcess(aProcess);
      }

      // If there are still journal entries to consume, then the process
      // probably faulted. Count the number of remaining entries and emit a
      // warning.
      if (i.hasNext())
      {
         int remaining = 0;

         while (i.hasNext())
         {
            IAeJournalEntry entry = (IAeJournalEntry) i.next();

            if (entry instanceof AeEngineFailureJournalEntry)
            {
               // This doesn't count as an entry that the process would consume.
            }
            else if (entry instanceof AeSentReplyJournalEntry)
            {
               // This doesn't count as an entry that the process would consume.
            }
            else
            {
               ++remaining;
            }
         }

         if (remaining > 0)
         {
            AeException.logWarning(AeMessages.format("AeRecoveryEngine.WARNING_ENTRIES_REMAINING", //$NON-NLS-1$
                                                     new Object[] { new Long(aProcess.getProcessId()), new Integer(remaining) }));
         }
      }
   }

   /**
    * Returns the location ids for activities that are currently executing in
    * the given process.
    */
   protected AeLongSet getExecutingLocationIds(IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      AeExecutingLocationIdsCollector collector = new AeExecutingLocationIdsCollector();
      return collector.getExecutingLocationIds(aProcess);
   }

   /**
    * Returns location ids of requests that are already queued for the given
    * process.
    *
    * @param aProcess
    * @param aJournalEntries
    */
   protected AeLongSet getQueuedLocationIds(IAeBusinessProcess aProcess, List aJournalEntries) throws AeBusinessProcessException
   {
      AeQueuedLocationIdsCollector collector = new AeQueuedLocationIdsCollector();
      return collector.getQueuedLocationIds(aProcess, aJournalEntries);
   }

   /**
    * Returns the process manager to use during recovery.
    */
   protected IAeRecoveryProcessManager getRecoveryProcessManager()
   {
      return (IAeRecoveryProcessManager) getProcessManager();
   }

   /**
    * Returns the queue manager to use during recovery.
    */
   protected IAeRecoveryQueueManager getRecoveryQueueManager()
   {
      return (IAeRecoveryQueueManager) getQueueManager();
   }

   /**
    * Returns location ids of requests that are currently queued but should no
    * longer be queued, because the corresponding activity is not executing.
    */
   protected Set getStaleLocationIds(Set aQueuedLocationIds, Set aExecutingLocationIds)
   {
      Set staleLocationIds = new HashSet(aQueuedLocationIds);
      staleLocationIds.removeAll(aExecutingLocationIds);

      return staleLocationIds;
   }

   /**
    * Returns <code>true</code> if and only if the process ended.
    */
   protected boolean isProcessEnded(IAeBusinessProcess aProcess)
   {
      switch (aProcess.getProcessState())
      {
         case IAeBusinessProcess.PROCESS_COMPLETE:
         case IAeBusinessProcess.PROCESS_FAULTED:
            return true;
         default:
            return false;
      }
   }

   /**
    * Queues the recovered items to the given engine.
    *
    * @param aRecoveredItems
    * @param aProcess
    */
   protected void queueRecoveredItems(List aRecoveredItems, IAeBusinessProcess aProcess)
   {
      for (Iterator i = aRecoveredItems.iterator(); i.hasNext(); )
      {
         IAeRecoveredItem recoveredItem = (IAeRecoveredItem) i.next();

         try
         {
            recoveredItem.queueItem(aProcess.getEngine());
         }
         catch (AeBusinessProcessException e)
         {
            // Report the name of the recovered item's class.
            String name = recoveredItem.getClass().getName();

            // Report just the name without the package.
            int n = name.lastIndexOf('.');
            if (n > 0)
            {
               name = name.substring(n + 1);
            }

            Object[] params = new Object[] { name, new Long(aProcess.getProcessId()), new Integer(recoveredItem.getLocationId()) };
            AeException.logError(e, AeMessages.format("AeRecoveryEngine.ERROR_QUEUE_RECOVERED_ITEM", params)); //$NON-NLS-1$
         }
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryEngine#recover(org.activebpel.rt.bpel.IAeBusinessProcess, java.util.List, boolean)
    */
   public List recover(IAeBusinessProcess aProcess, List aJournalEntries, boolean aQueueRecoveredItems) throws AeBusinessProcessException
   {
      // Extract sent reply objects.
      List sentReplies = getSentReplies(aJournalEntries);

      // Extract invoke transmitted journal entries.
      List invokeTransmittedEntries = getInvokeTransmittedEntries(aJournalEntries);

      // Recover the process state and generate a complete list of recovered
      // alarm and queue manager requests.
      List recoveredItems = recover(aProcess, aJournalEntries, sentReplies, invokeTransmittedEntries);

      // Get location ids for requests that are already queued.
      AeLongSet queuedLocationIds = getQueuedLocationIds(aProcess, aJournalEntries);

      // Remove recovered items that are already queued.
      removeQueuedItems(recoveredItems, queuedLocationIds);

      // Get location ids for executing activities.
      AeLongSet executingLocationIds = getExecutingLocationIds(aProcess);

      // Get location ids for queued requests that should be removed.
      Set staleLocationIds = getStaleLocationIds(queuedLocationIds, executingLocationIds);

      // Add recovered items to remove stale requests.
      addStaleRequestRemovalItems(recoveredItems, aProcess, staleLocationIds);

      // Queue the recovered items if requested.
      if (aQueueRecoveredItems)
      {
         queueRecoveredItems(recoveredItems, aProcess);
      }

      return recoveredItems;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.AeBusinessProcessEngine#queueInvokeData(long, java.lang.String, long, org.activebpel.rt.message.IAeMessageData, java.util.Map, org.activebpel.wsio.IAeMessageAcknowledgeCallback)
    */
   public void queueInvokeData(long aProcessId, String aLocationPath, long aTransmissionId, IAeMessageData aMessageData, Map aProperties, IAeMessageAcknowledgeCallback aAckCallback) throws AeBusinessProcessException
   {
      removeRecoveredInvoke(aProcessId, aLocationPath);
      super.queueInvokeData(aProcessId, aLocationPath, aTransmissionId, aMessageData,
            aProperties, aAckCallback);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeBusinessProcessEngine#queueInvokeFault(long, java.lang.String, long, org.activebpel.rt.bpel.IAeFault, java.util.Map, org.activebpel.wsio.IAeMessageAcknowledgeCallback)
    */
   public void queueInvokeFault(long aProcessId, String aLocationPath, long aTransmissionId, IAeFault aFault, Map aProperties, IAeMessageAcknowledgeCallback aAckCallback) throws AeBusinessProcessException
   {
      removeRecoveredInvoke(aProcessId, aLocationPath);
      super.queueInvokeFault(aProcessId, aLocationPath, aTransmissionId, aFault,
            aProperties, aAckCallback);
   }

   /**
    * Recovers the state of the specified process from the given list of {@link
    * org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry}
    * instances.
    *
    * @param aProcess
    * @param aJournalEntries
    * @param aSentReplies
    * @param aInvokeTransmittedEntries
    * @return A list of {@link org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem} instances.
    * @throws AeBusinessProcessException
    */
   protected synchronized List recover(IAeBusinessProcess aProcess, List aJournalEntries, List aSentReplies, List aInvokeTransmittedEntries) throws AeBusinessProcessException
   {
      // The whole method is synchronized, because we can only recover one
      // process at a time.
      setRecoveryProcess(aProcess);

      // Create a set to hold the recovered alarm and queue manager requests.
      IAeRecoveredItemsSet recoveredItemsSet = new AeRecoveredItemsSet();
      getRecoveryQueueManager().setRecoveredItemsSet(recoveredItemsSet);
      
      // Prepare the queue manager to match recovered send reply requests
      // against replies that were already sent by the process.
      getRecoveryQueueManager().setSentReplies(aSentReplies);

      // Prepare the queue manager to match invoke location ids to transmission
      // ids.
      getRecoveryQueueManager().setInvokeTransmittedEntries(aInvokeTransmittedEntries);
      
      // Switch the process from its current engine to this recovery engine.
      IAeBusinessProcessEngineInternal oldEngine = aProcess.getEngine();
      aProcess.setEngine(this);
      try
      {
         // Restore queued items so we can match correlated requests
         restoreQueuedItems(aProcess);

         // Dispatch the journal entries to the process.
         dispatchJournalEntries(aJournalEntries, aProcess);
      }
      finally
      {
         // Restore the original engine for the process.
         aProcess.setEngine(oldEngine);
      }

      // Return the list of recovered alarm and queue manager requests.
      return recoveredItemsSet.getRecoveredItems();
   }

   /**
    * Removes recovered items from the given list for requests that are already
    * queued.
    *
    * @param aRecoveredItems
    * @param aQueuedLocationIds
    * @throws AeBusinessProcessException
    */
   protected void removeQueuedItems(List aRecoveredItems, AeLongSet aQueuedLocationIds)
   {
      for (Iterator i = aRecoveredItems.iterator(); i.hasNext(); )
      {
         IAeRecoveredItem recoveredItem = (IAeRecoveredItem) i.next();
         int locationId = recoveredItem.getLocationId();

         // Note that some recovered items (namely, invokes and replies) report
         // 0 for location id to indicate that they don't have a location id.
         if ((locationId > 0) && aQueuedLocationIds.contains(locationId))
         {
            i.remove();
         }
      }
   }

   /**
    * Restores the executing items that are flagged as queued in the recovery queue manager
    * so we can match correlated requests
    *  
    * @param aQueuedLocationIds
    * @throws AeBusinessProcessException
    */
   protected void restoreQueuedItems(IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      // Get location ids for executing items 
      Set executingLocationIds = getExecutingLocationIds(aProcess);
      for (Iterator it = executingLocationIds.iterator(); it.hasNext();)
      {
         int locationId = ((Long) it.next()).intValue();
         IAeBpelObject item = aProcess.findBpelObject(locationId);
         
         // re-queue any previously queued message or alarm receivers
         // we reset the state before re-queue to suppress a state transition warning that is 
         // entirely appropriate in the normal engine, but not during recovery
         if (item instanceof IAeMessageReceiverActivity)
         {
            IAeMessageReceiverActivity receiver = (IAeMessageReceiverActivity) item;
            if (receiver.isQueued())
            {
               item.setState(AeBpelState.INACTIVE);
               aProcess.queueObjectToExecute(item);
            }
         }
         else if (item instanceof IAeAlarmReceiver)
         {
            IAeAlarmReceiver receiver = (IAeAlarmReceiver) item;
            if (receiver.isQueued())
            {  
               item.setState(AeBpelState.INACTIVE);
               aProcess.queueObjectToExecute(item);
            }
         }
      }
   }

   /**
    * Returns sent reply objects from the sent reply journal entries in the
    * given list of journal entries.
    */
   protected List getSentReplies(List aJournalEntries) throws AeBusinessProcessException
   {
      List sentReplies = new LinkedList();

      for (Iterator i = aJournalEntries.iterator(); i.hasNext(); )
      {
         IAeJournalEntry item = (IAeJournalEntry) i.next();

         if (item instanceof AeSentReplyJournalEntry)
         {
            // Unwrap the actual reply object.
            AeReply sentReply = ((AeSentReplyJournalEntry) item).getReply();
            sentReplies.add(sentReply);
         }
      }

      return sentReplies;
   }

   /**
    * Sets the process to recover on all the managers.
    *
    * @param aProcess
    */
   protected void setRecoveryProcess(IAeBusinessProcess aProcess)
   {
      getRecoveryProcessManager().setRecoveryProcess(aProcess);
      getRecoveryQueueManager().setRecoveryProcess(aProcess);
   }
   
   /**
    * Returns invoke transmitted journal entries from the given list of journal
    * entries.
    */
   protected List getInvokeTransmittedEntries(List aJournalEntries) throws AeBusinessProcessException
   {
      List transmittedInvokeEntries = new LinkedList();

      for (Iterator i = aJournalEntries.iterator(); i.hasNext(); )
      {
         IAeJournalEntry entry = (IAeJournalEntry) i.next();

         if (entry instanceof AeInvokeTransmittedJournalEntry)
         {
            transmittedInvokeEntries.add(entry);
         }
      }

      return transmittedInvokeEntries;
   }

   /**
    * Removes a previosly recovered invoke once its response data or fault
    * has been processed by the engine.
    * @param aProcessId
    * @param aLocationPath
    * @throws AeBusinessProcessException
    */
   private void removeRecoveredInvoke(long aProcessId, String aLocationPath) throws AeBusinessProcessException
   {
      int locationId = getProcessById(aProcessId).getLocationId(aLocationPath);
      getRecoveryQueueManager().getRecoveredItemsSet().removeRecoveredItem(locationId);
   }

}
