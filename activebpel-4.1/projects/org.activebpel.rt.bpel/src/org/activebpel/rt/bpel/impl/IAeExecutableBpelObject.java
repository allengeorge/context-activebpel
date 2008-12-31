// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeExecutableBpelObject.java,v 1.11 2006/10/04 16:19:3
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;

/**
 * Interface for Executable Bpel object implementations. 
 */
public interface IAeExecutableBpelObject extends IAeBpelObject, IAeExecutableQueueItem
{
   /**
    * Callback method that indicates a child object has completed. It is important
    * to note that the child's state will be either FINISHED or DEAD_PATH.
    * @param aChild The child completing its execution.
    */
   public void childComplete(IAeBpelObject aChild) throws AeBusinessProcessException;
   
   /**
    * Callback method that indicates a child object has been terminated. The parent 
    * receives this callback when the child's state changes to terminated.
    * @param aChild
    */
   public void childTerminated(IAeBpelObject aChild) throws AeBusinessProcessException;
   
   /**
    * All implementation must be able to accept execution notifications from its children.
    * Note if an implementation has no children it can have a stub method here. 
    * @param aChild The child completing its execution.
    * @param aFaultObject the chidl is completing with a fault.
    */
   public void childCompleteWithFault(IAeBpelObject aChild, IAeFault aFaultObject) throws AeBusinessProcessException;

   /**
    * Notification to parent that a child is faulting. This exists to give the parent
    * a chance to record the fault in the event that there is another fault raised
    * due to the link processing.
    * @param aChild
    * @param aFaultObject
    */
   public void childIsFaulting(IAeBpelObject aChild, IAeFault aFaultObject);
   
   /**
    * If the process or enclosing scope terminates executable objects are 
    * expected to allow termination.
    */
   public void terminate() throws AeBusinessProcessException;
   
   /**
    * Process Exception Management - complete activity
    */
   public void exceptionManagementCompleteActivity() throws AeBusinessProcessException;
   
   /**
    * Process Exception Management - resumes the uncaught fault.
    */
   public void exceptionManagementResumeUncaughtFault(IAeFault aUncaughtFault) throws AeBusinessProcessException;
   
}
