// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeInMemoryProcessManager.java,v 1.39 2007/05/08 19:01:1
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
package org.activebpel.rt.bpel.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.IAePlanManager;
import org.activebpel.rt.bpel.def.visitors.AeDefToImplVisitor;
import org.activebpel.rt.bpel.impl.list.AeProcessFilter;
import org.activebpel.rt.bpel.impl.list.AeProcessFilterAdapter;
import org.activebpel.rt.bpel.impl.list.AeProcessInstanceDetail;
import org.activebpel.rt.bpel.impl.list.AeProcessListResult;
import org.activebpel.rt.bpel.impl.queue.AeInboundReceive;
import org.activebpel.rt.bpel.impl.queue.AeReply;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Implements a simple in-memory process manager.
 */
public class AeInMemoryProcessManager extends AeAbstractProcessManager implements IAeProcessManager
{
   public static final String CONFIG_COMPLETED_PROCESS_COUNT = "CompletedProcessCount"; //$NON-NLS-1$

   /** Default number of completed processes to leave in {@link #mProcesses}. */
   private static final int DEFAULT_COMPLETED_PROCESS_COUNT = 25;

   /** Next available process id */
   private long mNextProcessId = 1;

   /** Maps process ids to processes */
   private Hashtable mProcesses = new Hashtable();

   /** The number of completed process to leave temporarily in {@link #mProcesses}. */
   private int mCompletedProcessCount;

   /** Process ids for completed processes temporarily left in {@link #mProcesses}. */
   private List mCompletedProcessIds = new LinkedList();

   /** The next journal ID to use for journaling methods. */
   private long mNextJournalId = 1;

   /**
    * Default constructor.
    */
   public AeInMemoryProcessManager()
   {
      this(Collections.EMPTY_MAP);
   }

   /**
    * Constructs an in-memory process manager.
    *
    * @param aConfig The configuration map for this manager.
    */
   public AeInMemoryProcessManager(Map aConfig)
   {
      super(aConfig);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getNextJournalId()
    */
   public synchronized long getNextJournalId()
   {
      return mNextJournalId++;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#createBusinessProcess(org.activebpel.rt.bpel.impl.IAeProcessPlan)
    */
   public IAeBusinessProcess createBusinessProcess(IAeProcessPlan aPlan) throws AeBusinessProcessException
   {
      long pid = getNextProcessId();
      IAeBusinessProcess process = AeDefToImplVisitor.createProcess(pid, getEngine(), aPlan);
      putProcess(pid, process);
      return process;
   }

   /**
    * Returns process id from a key generated by {@link #getKey(long)}.
    */
   protected long fromKey(Object aKey)
   {
      return ((Number) aKey).longValue();
   }

   /**
    * Returns a key compatible with a Java <code>Map</code> for the specified
    * process id.
    */
   protected Object getKey(long aProcessId)
   {
      return new Long(aProcessId);
   }

   /**
    * Returns next available process id.
    */
   protected long getNextProcessId()
   {
      synchronized (mProcesses)
      {
      	return mNextProcessId++;
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcess(long)
    */
   public IAeBusinessProcess getProcess(long aProcessId)
   {
      return (IAeBusinessProcess) getProcessMap().get(getKey(aProcessId));
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcessNoUpdate(long)
    */
   public IAeBusinessProcess getProcessNoUpdate(long aProcessId)
   {
      return getProcess(aProcessId);
   }

   /**
    * Returns process map.
    */
   protected Map getProcessMap()
   {
      return mProcesses;
   }

   /**
    * Creates a process instance detail for the given process
    *
    * @param aProcess
    */
   private AeProcessInstanceDetail createProcessInstanceDetail(IAeBusinessProcess aProcess)
   {
      AeProcessInstanceDetail detail = new AeProcessInstanceDetail();
      detail.setName(aProcess.getName());
      detail.setProcessId(aProcess.getProcessId());
      detail.setState(aProcess.getProcessState());
      detail.setStateReason(aProcess.getProcessStateReason());
      detail.setStarted(aProcess.getStartDate());
      detail.setEnded(aProcess.getEndDate());
      return detail;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcessInstanceDetails(long)
    */
   public AeProcessInstanceDetail getProcessInstanceDetails(long aProcessId)
   {
      IAeBusinessProcess process = getProcess(aProcessId);
      return (process == null) ? null : createProcessInstanceDetail(process);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcesses(org.activebpel.rt.bpel.impl.list.AeProcessFilter)
    */
   public AeProcessListResult getProcesses(AeProcessFilter aFilter)
   {
      ArrayList results = new ArrayList();
      int totalCount = aFilter == null ? 0 : aFilter.getListStart();
      int matches = 0;

      synchronized(getProcessMap())
      {
         AeProcessFilterAdapter filter = new AeProcessFilterAdapter( aFilter );

         SortedMap map = new TreeMap(new AeReverseComparator());
         map.putAll(getProcessMap());

         for (Iterator iter=map.keySet().iterator(); iter.hasNext();)
         {
            IAeBusinessProcess process = (IAeBusinessProcess)map.get(iter.next());
            if( filter.accept(process) )
            {
               totalCount++;
               if( aFilter.isWithinRange(totalCount) )
               {
                  results.add(createProcessInstanceDetail(process));
               }
               matches++;
            }
         }
      }

      // Convert the list into an array as expected
      AeProcessInstanceDetail[] details = new AeProcessInstanceDetail[results.size()];
      results.toArray(details);
      return new AeProcessListResult(totalCount, details);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcessCount(org.activebpel.rt.bpel.impl.list.AeProcessFilter)
    */
   public int getProcessCount(AeProcessFilter aFilter) throws AeBusinessProcessException
   {
      return getProcesses(aFilter).getTotalRowCount();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcessIds(org.activebpel.rt.bpel.impl.list.AeProcessFilter)
    */
   public long[] getProcessIds(AeProcessFilter aFilter) throws AeBusinessProcessException
   {
      throw new UnsupportedOperationException();
   }

   /**
    * Puts process into process map.
    *
    * @param aProcessId
    * @param aProcess
    */
   protected void putProcess(long aProcessId, IAeBusinessProcess aProcess)
   {
      getProcessMap().put(getKey(aProcessId), aProcess);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#processEnded(long)
    */
   public void processEnded(long aProcessId)
   {
      removeProcess(aProcessId);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#releaseProcess(org.activebpel.rt.bpel.IAeBusinessProcess)
    */
   public void releaseProcess(IAeBusinessProcess aProcess)
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#removeProcess(long)
    */
   public void removeProcess(long aProcessId)
   {
      List purgedProcessIds = new ArrayList();

      if (getCompletedProcessCount() <= 0)
      {
         // If we're not caching completed processes, then just remove the
         // process from the process map.
         Object key = getKey(aProcessId);
         getProcessMap().remove(key);
         
         purgedProcessIds.add(key);
      }
      else
      {
         synchronized (mCompletedProcessIds)
         {
            // Otherwise, pare the list of cached processes to the cache size.
            while (mCompletedProcessIds.size() >= getCompletedProcessCount())
            {
               Object key = mCompletedProcessIds.remove(0);
               getProcessMap().remove(key);

               purgedProcessIds.add(key);
            }

            // And then add the specified process to the cache.
            mCompletedProcessIds.add(getKey(aProcessId));
         }
      }

      // Notify listeners of the purged processes.
      for (Iterator i = purgedProcessIds.iterator(); i.hasNext(); )
      {
         long processId = fromKey(i.next());
         fireProcessPurged(processId);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#removeProcesses(org.activebpel.rt.bpel.impl.list.AeProcessFilter)
    */
   public int removeProcesses(AeProcessFilter aFilter)
   {
      synchronized (getProcessMap())
      {
         List processes = new ArrayList(getProcessMap().values());
         AeProcessFilterAdapter filter = new AeProcessFilterAdapter(aFilter);
         int removed = 0;

         for (Iterator i = processes.iterator(); i.hasNext(); )
         {
            IAeBusinessProcess process = (IAeBusinessProcess) i.next();

            if (filter.accept(process))
            {
               removeProcess(process.getProcessId());
               ++removed;
            }
         }

         return removed;
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#setPlanManager(org.activebpel.rt.bpel.IAePlanManager)
    */
   public void setPlanManager(IAePlanManager aPlanManager)
   {
      // Don't need plan manager for in-memory process manager.
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcessQName(long)
    */
   public QName getProcessQName(long aProcessId)
   {
      QName processName = null;
      Object key = getKey( aProcessId );
      IAeBusinessProcess process = (IAeBusinessProcess)getProcessMap().get( key );
      if( process != null )
      {
         processName = process.getName();
      }
      return processName;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalEntryDone(long, long)
    */
   public void journalEntryDone(long aProcessId, long aJournalId)
   {
      // Nothing to do for in-memory process manager.
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalInvokeData(long, int, long, org.activebpel.rt.message.IAeMessageData, java.util.Map)
    */
   public long journalInvokeData(long aProcessId, int aLocationId, long aTransmissionId, IAeMessageData aMessageData, Map aProcessProperties)
   {
      // Don't need to save received invoke data for in-memory process manager.
      return getNextJournalId();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalInvokeFault(long, int, long, org.activebpel.rt.bpel.IAeFault, java.util.Map)
    */
   public long journalInvokeFault(long aProcessId, int aLocationId, long aTransmissionId, IAeFault aFault, Map aProcessProperties)
   {
      // Don't need to save received invoke fault for in-memory process manager.
      return getNextJournalId();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalInboundReceive(long, int, org.activebpel.rt.bpel.impl.queue.AeInboundReceive)
    */
   public long journalInboundReceive(long aProcessId, int aLocationId, AeInboundReceive aInboundReceive)
   {
      // Don't need to save received message for in-memory process manager.
      return getNextJournalId();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalSentReply(long, org.activebpel.rt.bpel.impl.queue.AeReply, java.util.Map)
    */
   public void journalSentReply(long aProcessId, AeReply aSentReply, Map aProcessProperties)
   {
      // Don't need to save sent reply for in-memory process manager.
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalInvokeTransmitted(long, int, long)
    */
   public void journalInvokeTransmitted(long aProcessId, int aLocationId, long aTransmissionId) throws AeBusinessProcessException
   {
      // Don't need to save invoke's transmission id for in-memory process manager.
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalCompensateSubprocess(long, java.lang.String)
    */
   public long journalCompensateSubprocess(long aProcessId, String aCoordinationId) throws AeBusinessProcessException
   {
      // In memory proc. manager does not save journal data.
      return getNextJournalId();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalInvokePending(long, int)
    */
   public long journalInvokePending(long aProcessId, int aLocationId)
   {
      // Don't need to save pending invoke data for in-memory process manager.
      return getNextJournalId();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#transmissionIdDone(long, long)
    */
   public void transmissionIdDone(long aProcessId, long aTransmissionId)
   {
      // For in-memory impl., remove from tx tracker.
      try
      {
         getEngine().getTransmissionTracker().remove(aTransmissionId);
      }
      catch(Exception e)
      {
         AeException.logError(e);
      }
   }

   /**
    * Returns the number of completed process to leave temporarily in the
    * process map.
    */
   protected int getCompletedProcessCount()
   {
      return mCompletedProcessCount;
   }

   /**
    * Sets the number of completed process to leave temporarily in the process
    * map.
    */
   protected void setCompletedProcessCount(int aCompletedProcessCount)
   {
      mCompletedProcessCount = aCompletedProcessCount;
   }

   /**
    * Sets configuration.
    */
   protected void setConfig(Map aConfig)
   {
      super.setConfig(aConfig);
      
      setCompletedProcessCount(getConfigInt(CONFIG_COMPLETED_PROCESS_COUNT, DEFAULT_COMPLETED_PROCESS_COUNT));
   }

   /**
    * Implements a <code>Comparator</code> that reverses the natural order of
    * <code>Comparable</code> objects.
    */
   protected static class AeReverseComparator implements Comparator
   {
      /**
       * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
       */
      public int compare(Object o1, Object o2)
      {
         return -((Comparable) o1).compareTo(o2);
      }
   }
}