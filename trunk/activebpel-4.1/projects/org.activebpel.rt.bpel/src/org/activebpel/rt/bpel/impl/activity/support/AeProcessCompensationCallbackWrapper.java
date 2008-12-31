// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeProcessCompensationCallbackWrapper.java,v 1.4 2006/11/09 16:28:5
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
package org.activebpel.rt.bpel.impl.activity.support; 

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.IAeProcessInfoEvent;
import org.activebpel.rt.bpel.impl.AeProcessInfoEvent;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal;

/**
 * wrapper used to ensure that the process transitions from running to completed
 * when the process level compensation is complete.
 */
public class AeProcessCompensationCallbackWrapper implements
      IAeCompensationCallback
{
   /** our delegate */
   private IAeCompensationCallback mDelegate;
   
   /**
    * ctor accepts our delegate callback reference
    * @param aCallback
    */
   public AeProcessCompensationCallbackWrapper(IAeCompensationCallback aCallback)
   {
      setDelegate(aCallback);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeCompensationCallback#compensationComplete(org.activebpel.rt.bpel.impl.activity.support.AeCompensationHandler)
    */
   public void compensationComplete(AeCompensationHandler aCompHandler) throws AeBusinessProcessException
   {
      getDelegate().compensationComplete(aCompHandler); 
      setProcessState(aCompHandler, IAeBusinessProcess.PROCESS_COMPLETE, IAeProcessInfoEvent.INFO_PROCESS_COMPENSATION_FINISHED);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeCompensationCallback#compensationCompleteWithFault(org.activebpel.rt.bpel.impl.activity.support.AeCompensationHandler, org.activebpel.rt.bpel.IAeFault)
    */
   public void compensationCompleteWithFault(AeCompensationHandler aCompHandler, IAeFault aFault) throws AeBusinessProcessException
   {
      getDelegate().compensationCompleteWithFault(aCompHandler, aFault);
      setProcessState(aCompHandler, IAeBusinessProcess.PROCESS_FAULTED, IAeProcessInfoEvent.INFO_PROCESS_COMPENSATION_FAULTED);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeCompensationCallback#compensationTerminated(org.activebpel.rt.bpel.impl.activity.support.AeCompensationHandler)
    */
   public void compensationTerminated(AeCompensationHandler aCompHandler) throws AeBusinessProcessException
   {
      getDelegate().compensationTerminated(aCompHandler);
      // As per CK/Defect1558 - change state to Faulted instead of Completed.
      setProcessState(aCompHandler, IAeBusinessProcess.PROCESS_FAULTED, IAeProcessInfoEvent.INFO_PROCESS_COMPENSATION_TERMINATED);
   }
   
   /**
    * Fires an event to report the state change for the process level compensation handler and then
    * sets the process state to complete once the compensation is done.
    * @param aCompHandler
    * @param aProcessState
    * @param aProcessInfoState
    */
   protected void setProcessState(AeCompensationHandler aCompHandler, int aProcessState, int aProcessInfoState)
   {
      IAeBusinessProcessInternal process = aCompHandler.getProcess();
      AeProcessInfoEvent event = new AeProcessInfoEvent(process.getProcessId(), process.getLocationPath(), aProcessInfoState);
      process.getEngine().fireInfoEvent(event);
      process.setProcessState(aProcessState);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeCompensationCallback#getLocationPath()
    */
   public String getLocationPath()
   {
      return getDelegate().getLocationPath();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeCompensationCallback#isCoordinated()
    */
   public boolean isCoordinated()
   {
      return getDelegate().isCoordinated();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeCompensationCallback#getCoordinationId()
    */
   public String getCoordinationId()
   {
      return getDelegate().getCoordinationId();
   }

   /**
    * @return Returns the delegate.
    */
   protected IAeCompensationCallback getDelegate()
   {
      return mDelegate;
   }

   /**
    * @param aDelegate The delegate to set.
    */
   protected void setDelegate(IAeCompensationCallback aDelegate)
   {
      mDelegate = aDelegate;
   }
}
 
