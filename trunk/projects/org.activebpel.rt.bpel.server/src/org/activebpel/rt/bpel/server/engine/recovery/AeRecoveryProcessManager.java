// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/AeRecoveryProcessManager.java,v 1.15 2007/05/08 19:13:4
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

import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.IAePlanManager;
import org.activebpel.rt.bpel.impl.AeManagerAdapter;
import org.activebpel.rt.bpel.impl.IAeProcessPlan;
import org.activebpel.rt.bpel.impl.IAeProcessPurgedListener;
import org.activebpel.rt.bpel.impl.list.AeProcessFilter;
import org.activebpel.rt.bpel.impl.list.AeProcessInstanceDetail;
import org.activebpel.rt.bpel.impl.list.AeProcessListResult;
import org.activebpel.rt.bpel.impl.queue.AeInboundReceive;
import org.activebpel.rt.bpel.impl.queue.AeReply;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Implements a process manager for recovery.
 */
public class AeRecoveryProcessManager extends AeManagerAdapter implements IAeRecoveryProcessManager
{
   /** The process that is currently being recovered. */
   private IAeBusinessProcess mRecoveryProcess;

   /**
    * Constructs a process manager for recovery.
    */
   public AeRecoveryProcessManager()
   {
   }

   /**
    * Verifies that the specified process id matches the process id of the
    * process being recovered. Throws {@link java.lang.IllegalStateException}
    * otherwise.
    *
    * @param aProcessId
    */
   protected void checkProcessId(long aProcessId)
   {
      if (aProcessId != getRecoveryProcess().getProcessId())
      {
         throw new IllegalStateException(AeMessages.format("AeRecoveryProcessManager.ERROR_0", //$NON-NLS-1$
                                                           new Object[] { new Long(aProcessId), new Long(getRecoveryProcess().getProcessId()) }));
      }
   }

   /**
    * Returns the process that is being recovered.
    */
   protected IAeBusinessProcess getRecoveryProcess()
   {
      return mRecoveryProcess;
   }

   /*======================================================================
    * org.activebpel.rt.bpel.impl.IAeProcessManager methods
    *======================================================================
    */

   /**
    * Overrides method to return recovery process.
    *
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#createBusinessProcess(org.activebpel.rt.bpel.impl.IAeProcessPlan)
    */
   public IAeBusinessProcess createBusinessProcess(IAeProcessPlan aProcessPlan) throws AeBusinessProcessException
   {
      return getRecoveryProcess();
   }

   /**
    * Overrides method to return recovery process.
    *
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcess(long)
    */
   public IAeBusinessProcess getProcess(long aProcessId)
   {
      return getRecoveryProcess();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcessNoUpdate(long)
    */
   public IAeBusinessProcess getProcessNoUpdate(long aProcessId)
   {
      return getProcess(aProcessId);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcesses(org.activebpel.rt.bpel.impl.list.AeProcessFilter)
    */
   public AeProcessListResult getProcesses(AeProcessFilter aFilter) throws AeBusinessProcessException
   {
      throw new UnsupportedOperationException();
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcessCount(org.activebpel.rt.bpel.impl.list.AeProcessFilter)
    */
   public int getProcessCount(AeProcessFilter aFilter) throws AeBusinessProcessException
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcessIds(org.activebpel.rt.bpel.impl.list.AeProcessFilter)
    */
   public long[] getProcessIds(AeProcessFilter aFilter) throws AeBusinessProcessException
   {
      throw new UnsupportedOperationException();
   }
    
   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcessInstanceDetails(long)
    */
   public AeProcessInstanceDetail getProcessInstanceDetails(long aProcessId)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getProcessQName(long)
    */
   public QName getProcessQName(long aProcessId) throws AeBusinessProcessException
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#processEnded(long)
    */
   public void processEnded(long aProcessId)
   {
      checkProcessId(aProcessId);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalEntryDone(long, long)
    */
   public void journalEntryDone(long aProcessId, long aJournalId)
   {
      checkProcessId(aProcessId);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#releaseProcess(org.activebpel.rt.bpel.IAeBusinessProcess)
    */
   public void releaseProcess(IAeBusinessProcess aProcess)
   {
      if (aProcess != null)
      {
         checkProcessId(aProcess.getProcessId());
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#removeProcess(long)
    */
   public void removeProcess(long aProcessId) throws AeBusinessProcessException
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#removeProcesses(org.activebpel.rt.bpel.impl.list.AeProcessFilter)
    */
   public int removeProcesses(AeProcessFilter aFilter) throws AeBusinessProcessException
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalInvokeData(long, int, long,  org.activebpel.rt.message.IAeMessageData, java.util.Map)
    */
   public long journalInvokeData(long aProcessId, int aLocationId, long aTransmissionId, IAeMessageData aMessageData, Map aProcessProperties) throws AeBusinessProcessException
   {
      checkProcessId(aProcessId);
      return NULL_JOURNAL_ID + 1;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalInvokeFault(long, int, long, org.activebpel.rt.bpel.IAeFault, java.util.Map)
    */
   public long journalInvokeFault(long aProcessId, int aLocationId, long aTransmissionId, IAeFault aFault, Map aProcessProperties) throws AeBusinessProcessException
   {
      checkProcessId(aProcessId);
      return NULL_JOURNAL_ID + 1;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalInboundReceive(long, int, org.activebpel.rt.bpel.impl.queue.AeInboundReceive)
    */
   public long journalInboundReceive(long aProcessId, int aLocationId, AeInboundReceive aInboundReceive) throws AeBusinessProcessException
   {
      checkProcessId(aProcessId);

      // Return the reply id, because AeBaseQueueManager#matchInboundReceive and 
      // AeBusinessProcessEngine#internalCreateProcessWithMessage use the
      // journal id for the reply id.
      return aInboundReceive.getReplyId();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalSentReply(long, org.activebpel.rt.bpel.impl.queue.AeReply, java.util.Map)
    */
   public void journalSentReply(long aProcessId, AeReply aSentReply, Map aProcessProperties) throws AeBusinessProcessException
   {
      checkProcessId(aProcessId);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalInvokeTransmitted(long, int, long)
    */
   public void journalInvokeTransmitted(long aProcessId, int aLocationId, long aTransmissionId) throws AeBusinessProcessException
   {
      checkProcessId(aProcessId);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalCompensateSubprocess(long, java.lang.String)
    */
   public long journalCompensateSubprocess(long aProcessId, String aCoordinationId) throws AeBusinessProcessException
   {
      checkProcessId(aProcessId);
      return NULL_JOURNAL_ID + 1;
   }      

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#journalInvokePending(long, int)
    */
   public long journalInvokePending(long aProcessId, int aLocationId)
   {
      checkProcessId(aProcessId);
      return NULL_JOURNAL_ID + 1;
   }      

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#setPlanManager(org.activebpel.rt.bpel.IAePlanManager)
    */
   public void setPlanManager(IAePlanManager aPlanManager) throws AeBusinessProcessException
   {
      throw new UnsupportedOperationException();
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#getNextJournalId()
    */
   public long getNextJournalId() throws AeBusinessProcessException
   {
      return NULL_JOURNAL_ID + 1;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#transmissionIdDone(long, long)
    */
   public void transmissionIdDone(long aProcessId, long aTransmissionId)
   {
      checkProcessId(aProcessId);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#addProcessPurgedListener(org.activebpel.rt.bpel.impl.IAeProcessPurgedListener)
    */
   public void addProcessPurgedListener(IAeProcessPurgedListener aListener)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#removeProcessPurgedListener(org.activebpel.rt.bpel.impl.IAeProcessPurgedListener)
    */
   public void removeProcessPurgedListener(IAeProcessPurgedListener aListener)
   {
      throw new UnsupportedOperationException();
   }

   /*================================================================================
    * org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryProcessManager methods
    *================================================================================
    */

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveryProcessManager#setRecoveryProcess(org.activebpel.rt.bpel.IAeBusinessProcess)
    */
   public void setRecoveryProcess(IAeBusinessProcess aRecoveryProcess)
   {
      mRecoveryProcess = aRecoveryProcess;
   }
}
