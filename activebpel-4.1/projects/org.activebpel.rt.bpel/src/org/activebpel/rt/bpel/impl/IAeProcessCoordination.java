//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeProcessCoordination.java,v 1.6 2006/06/26 16:50:2
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

import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.coord.AeCoordinationException;
import org.activebpel.rt.bpel.coord.IAeCoordinator;

/**
 * Interface that is responsible for handling coordination operations related to the process.
 */
public interface IAeProcessCoordination
{
   /**
    * Registers the coordination id with process invoke activity's enclosing scope.
    * @param aProcessId
    * @param aLocationPath
    * @param aCoordinationId
    * @throws AeCoordinationException
    */
   public void registerCoordinationId(long aProcessId, String aLocationPath, String aCoordinationId) throws AeCoordinationException;

   /**
    * Deregisters the coordination id from process invoke activity's enclosing scope.
    * @param aProcessId
    * @param aLocationPath
    * @param aCoordinationId
    * @throws AeCoordinationException
    */
   public void deregisterCoordinationId(long aProcessId, String aLocationPath, String aCoordinationId) throws AeCoordinationException;

   /**
    * Indicates the coordinated activity faulted.
    * @param aProcessId
    * @param aLocationPath
    * @param aCoordinationId
    * @param aFault
    * @throws AeCoordinationException
    */
   public void activityFaulted(long aProcessId, String aLocationPath, String aCoordinationId, IAeFault aFault) throws AeCoordinationException;

   /**
    * Installs a AeCoordinatorCompInfo object into the enclosing scope.
    * @param aProcessId
    * @param aLocationPath
    * @param aCoordinationId
    * @param aCoordinator
    * @throws AeCoordinationException
    */
   public void installCompensationHandler(long aProcessId, String aLocationPath, String aCoordinationId, IAeCoordinator aCoordinator) throws AeCoordinationException;

   /**
    * Handles the callback when a coordinated compensation has completed.
    * @param aProcessId
    * @param aLocationPath
    * @param aCoordinationId
    * @throws AeCoordinationException
    */
   public void compensationCompletedCallback(long aProcessId, String aLocationPath, String aCoordinationId) throws AeCoordinationException;

   /**
    * Handles the callback when a coordinated compensation has faulted.
    * @param aProcessId
    * @param aLocationPath
    * @param aCoordinationId
    * @param aFault
    * @throws AeCoordinationException
    */
   public void compensationCompletedWithFaultCallback(long aProcessId, String aLocationPath, String aCoordinationId, IAeFault aFault) throws AeCoordinationException;

   /**
    * Compensates a sub process (participant).
    * @param aProcessId sub process id
    * @param aCoordinationId
    * @param aJournalId journalid if the compensate acitivity was journaled.
    * @throws AeCoordinationException
    */ 
   public void compensateSubProcess(long aProcessId, String aCoordinationId, long aJournalId) throws AeCoordinationException;

   /**
    * Terminates the subprocess's compensation handler if it is currently executing.
    * @param aProcessId sub process id
    * @throws AeCoordinationException
    */
   public void cancelSubProcessCompensation(long aProcessId) throws AeCoordinationException;

   /**
    * Cancels (terminates) process.
    * @param aProcessId
    * @throws AeCoordinationException
    */
   public void cancelProcess(long aProcessId) throws AeCoordinationException;

   /**
    * Signals that the sub process coordination has ended. The process identified by the
    * process id will not be compensated.
    * @param aProcessId
    */
   public void subprocessCoordinationEnded(long aProcessId) throws AeCoordinationException;

}
