//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeServerProcessCoordination.java,v 1.11 2007/06/19 15:28:3
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.coord.AeCoordinationException;
import org.activebpel.rt.bpel.coord.IAeCoordinator;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.activity.support.AeCompensationHandler;
import org.activebpel.rt.bpel.impl.activity.support.AeCoordinatorCompInfo;
import org.activebpel.rt.bpel.impl.activity.support.AeParticipantCompensator;
import org.activebpel.rt.bpel.impl.activity.support.IAeCompensationCallback;

/**
 * Handles the coordination related operations for a process. 
 */
public class AeServerProcessCoordination implements IAeProcessCoordination
{
   /**
    * Business process engine.
    */
   private IAeBusinessProcessEngineInternal mEngine;
   
   /**
    * Default ctor.
    */
   public AeServerProcessCoordination(IAeBusinessProcessEngineInternal aEngine)
   {
      setEngine(aEngine);
   }
     
   /**
    * @return Returns the engine.
    */
   protected IAeBusinessProcessEngineInternal getEngine()
   {
      return mEngine;
   }
         
   /**
    * @param aEngine The engine to set.
    */
   protected void setEngine(IAeBusinessProcessEngineInternal aEngine)
   {
      mEngine = aEngine;
   }
   
   /** 
    * @return Returns the coordination manager.
    */
   protected IAeCoordinationManagerInternal getCoordinationManager()
   {
      return getEngine().getCoordinationManager();
   }
   
   /** 
    * @return Returns the process manager.
    */
   protected IAeProcessManager getProcessManager()
   {
      return getEngine().getProcessManager();
   }
   
   /**
    * Finds the enclosing scope given the process and the locationPath
    * @param aProcess
    * @param aLocationPath 
    * @return enclosing scope or null if not found.
    * @throws Exception
    */
   protected AeActivityScopeImpl findEnclosingScope(IAeBusinessProcess aProcess, String aLocationPath) throws AeException
   {
      IAeBpelObject bpel = aProcess.findBpelObject(aLocationPath);
      if (bpel != null)
      {
         IAeBpelObject parent = bpel.getParent();
         while (parent != null)
         {
            if (parent instanceof AeActivityScopeImpl)
            {
               return (AeActivityScopeImpl)parent;
            }
            parent = parent.getParent();
         } // while                   
      }// if
      return null;
   }   

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#registerCoordinationId(long, java.lang.String, java.lang.String)
    */
   public void registerCoordinationId(long aProcessId, String aLocationPath, String aCoordinationId)
         throws AeCoordinationException
   {
      IAeBusinessProcess process = null;
      IAeProcessManager processManager = getProcessManager();
      try
      {
         process = processManager.getProcess(aProcessId);
         //  need to be synchronized (for in-memory process manager).
         synchronized (process)
         {
            AeActivityScopeImpl scope = findEnclosingScope(process, aLocationPath);
            // add the coordination id to the scope's coordination container activity.
            scope.getCoordinationContainer().registerCoordinationId( aCoordinationId );
         }
      }
      catch(AeCoordinationException ce)
      {
         throw ce;
      }
      catch(Throwable t)
      {
         throw new AeCoordinationException(t);
      }
      finally
      {
         processManager.releaseProcess(process);
      }      
   }

   /**
    * Overrides method to 
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#deregisterCoordinationId(long, java.lang.String, java.lang.String)
    */
   public void deregisterCoordinationId(long aProcessId, String aLocationPath, String aCoordinationId)
         throws AeCoordinationException
   {
      IAeBusinessProcess process = null;
      IAeProcessManager processManager = getProcessManager();
      try
      {
         process = processManager.getProcess(aProcessId);
         //  need to be synchronized (for in-memory process manager).
         synchronized( process )
         {         
            AeActivityScopeImpl scope = findEnclosingScope(process, aLocationPath);
            //  remove the coordination id from the scope's coordination container activity.
            scope.getCoordinationContainer().deregisterCoordinationId( aCoordinationId );
         }
      }
      catch(Exception e)
      {
         throw new AeCoordinationException(e);
      }
      finally
      {
         processManager.releaseProcess(process);
      }   
   }

   /**
    * Overrides method to 
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#activityFaulted(long, java.lang.String, java.lang.String, org.activebpel.rt.bpel.IAeFault)
    */
   public void activityFaulted(long aProcessId, String aLocationPath, String aCoordinationId, IAeFault aFault)
         throws AeCoordinationException
   {
      IAeBusinessProcess process = null;
      IAeProcessManager processManager = getProcessManager();
      try
      {
         process = processManager.getProcess(aProcessId);
         //  need to be synchronized (for in-memory process manager).
         synchronized(process)
         {
            AeActivityScopeImpl scope = findEnclosingScope(process, aLocationPath);
            // defect #1367
            // if subprocess was terminated or faulted before it replied to the invoke,
            // then the Invoke would have faulted and gone thru the standard error handling
            // code. For example, the invoke would have faulted and hence then enclosing scope
            // would have also faulted (via normal fault handling code in the engine).
            // Therefore, the coordination framework should not fault the enclosing scope (i.e. second time)
            
            // Also defect: #2579 - coordination framework should not double
            // fault the enclosing scope(which could be the process) if the scope/process is already running its fault handlers.
            
            if (!scope.getState().isFinal() && !scope.isExecutingFaultHandler())
            {
               scope.triggerFaultHandling(aFault);
            }
         }
      }
      catch(Exception e)
      {
         throw new AeCoordinationException(e);
      }
      finally
      {
         processManager.releaseProcess(process);
      }   
   }
   
   /**
    * Overrides method to 
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#installCompensationHandler(long, java.lang.String, java.lang.String, org.activebpel.rt.bpel.coord.IAeCoordinator)
    */
   public void installCompensationHandler(long aProcessId, String aLocationPath, String aCoordinationId,
         IAeCoordinator aCoordinator) throws AeCoordinationException
   {
      IAeBusinessProcess process = null;
      IAeProcessManager processManager = getProcessManager();
      try
      {
         process = processManager.getProcess(aProcessId);
         //  need to be synchronized (for in-memory process manager).
         synchronized(process)
         {
            AeActivityScopeImpl scope = findEnclosingScope(process, aLocationPath);
            // install compensation handler!
            scope.coordinatedActivityCompleted( aCoordinationId, new AeCoordinatorCompInfo(scope, aCoordinator) );
         }
      }
      catch(Exception e)
      {
         throw new AeCoordinationException(e);
      }
      finally
      {
         processManager.releaseProcess(process);
      }   
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#compensationCompletedCallback(long, java.lang.String, java.lang.String)
    */
   public void compensationCompletedCallback(long aProcessId, String aLocationPath, String aCoordinationId) throws AeCoordinationException
   {
      internalCompensationCompletedCallback(aProcessId, aLocationPath, aCoordinationId, null);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#compensationCompletedWithFaultCallback(long, java.lang.String, java.lang.String, org.activebpel.rt.bpel.IAeFault)
    */
   public void compensationCompletedWithFaultCallback(long aProcessId, String aLocationPath,
         String aCoordinationId, IAeFault aFault) throws AeCoordinationException
   {
      internalCompensationCompletedCallback(aProcessId, aLocationPath, aCoordinationId, aFault);
   }

   /**
    * Handles the compensation callback either completed normally or with faulted (if aFault is not null).
    * 
    * @param aProcessId
    * @param aLocationPath
    * @param aCoordinationId
    * @param aFault
    * @throws AeCoordinationException
    */
   protected void internalCompensationCompletedCallback(long aProcessId, String aLocationPath, String aCoordinationId, IAeFault aFault) throws AeCoordinationException
   {
      IAeBusinessProcess process = null;
      IAeProcessManager processManager = getProcessManager();
      try
      {
         process = processManager.getProcess(aProcessId);
         synchronized (process)
         {
            AeActivityScopeImpl scope = findEnclosingScope(process, aLocationPath);
            // get the compensation handler for the coord-id.
            AeCompensationHandler compHandler = scope.getCoordinationContainer().getCompensationHandler(aCoordinationId);
            if (compHandler != null && compHandler instanceof IAeCompensationCallback)
            {
               // Note: AeCoordinatorCompensationHandler also implements IAeCompensationCallback
               IAeCompensationCallback compCallback = (IAeCompensationCallback)compHandler;
               if (aFault == null)
               {
                  compCallback.compensationComplete(compHandler);
               }
               else
               {
                  compCallback.compensationCompleteWithFault(compHandler, aFault);
               }
            }
            else
            {
               // error - should get not here.
               AeException.logWarning(AeMessages.format("AeServerProcessCoordination.UNSUPPORTED_COMP_HANDLER",String.valueOf(compHandler))); //$NON-NLS-1$
            }
         }
      }
      catch(Exception e)
      {
         throw new AeCoordinationException(e);
      }
      finally
      {
         processManager.releaseProcess(process);
      }   
   }   

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#compensateSubProcess(long, java.lang.String, long)
    */
   public void compensateSubProcess(long aProcessId, String aCoordinationId, long aJournalId) throws AeCoordinationException
   {            
      IAeBusinessProcess process = null;
      IAeProcessManager processManager = getProcessManager();
      long journalId = aJournalId;
      try
      {
         // journal entry if needed.
         if (journalId == IAeProcessManager.NULL_JOURNAL_ID)
         {
            journalId = processManager.journalCompensateSubprocess(aProcessId, aCoordinationId);
         }         
         AeParticipantCompensator comp = new AeParticipantCompensator(aCoordinationId, getEngine());
         process = processManager.getProcess(aProcessId);
         process.compensate(comp);
         processManager.journalEntryDone(aProcessId, journalId);
      }
      catch(Throwable t)
      {
         AeCoordinationException ce = new AeCoordinationException(t.getMessage(), t);
         throw ce;
      }
      finally
      {
         processManager.releaseProcess(process);
      }   
   }

   /** 
    * Overrides method to cancel subprocess's compensation handler if it is currently executing. 
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#cancelSubProcessCompensation(long)
    */
   public void cancelSubProcessCompensation(long aProcessId) throws AeCoordinationException   
   {
      IAeBusinessProcess process = null;
      IAeProcessManager processManager = getProcessManager();
      try
      {
         process = processManager.getProcess(aProcessId);
         IAeCompensatableActivity compensatableActivity = (IAeCompensatableActivity) process;
         compensatableActivity.terminateCompensationHandler();
      }
      catch(Throwable t)
      {
         AeCoordinationException ce = new AeCoordinationException(t.getMessage(), t);
         throw ce;
      }
      finally
      {
         processManager.releaseProcess(process);
      }      
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#cancelProcess(long)
    */
   public void cancelProcess(long aProcessId) throws AeCoordinationException
   {
      IAeBusinessProcess process = null;
      IAeProcessManager processManager = getProcessManager();
      try
      {
         process = processManager.getProcess(aProcessId);
         process.cancelProcess();
      }
      catch(AeBusinessProcessException bpe)
      {
         AeException.logError(bpe,bpe.getMessage());
      }
      finally
      {
         processManager.releaseProcess(process);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#subprocessCoordinationEnded(long)
    */
   public void subprocessCoordinationEnded(long aProcessId) throws AeCoordinationException
   {
      IAeBusinessProcess process = null;
      IAeProcessManager processManager = getProcessManager();
      try
      {
         process = processManager.getProcess(aProcessId);
         process.releaseCompensationResources();
      }
      catch(AeBusinessProcessException bpe)
      {
         AeException.logError(bpe,bpe.getMessage());
      }
      finally
      {
         processManager.releaseProcess(process);
      }
   }
}
