//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeCoordinationManagerInternal.java,v 1.3 2007/06/19 15:28:3
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
import org.activebpel.rt.bpel.coord.IAeCoordinating;
import org.activebpel.rt.bpel.coord.IAeCoordinationManager;
import org.activebpel.rt.bpel.coord.IAeProtocolMessage;

/**
 * Extension to the coordination manager implementation.
 */
public interface IAeCoordinationManagerInternal extends IAeCoordinationManager
{
   /**
    * Callback from the process manager when a process completes.
    * @param aProcessId
    * @param aFaultObject fault object if the process completed with a fault.
    * @param aNormalCompletion indiciates that the process completed normally and is eligible fo compensation.
    */
   public void onProcessCompleted(long aProcessId, IAeFault aFaultObject, boolean aNormalCompletion);

   /**
    * Dispatches the message to the destination.
    * @param aMessage
    */
   public void dispatch(IAeProtocolMessage aMessage, boolean aViaProcessExeQueue);

   /**
    * Save the current state information.
    * @param aCoordinating
    */
   public void persistState(IAeCoordinating aCoordinating) throws AeCoordinationException;

   /**
    * Sends a COMPENSATE_OR_COMPENSATE signal to participants.
    * @param aCoordinationId id
    */
   public void compensateOrCancel(String aCoordinationId);

   /**
    * Notifies the coordinators that a subprocess particpant has closed and 
    * therefore the coordinators are no longer reachable through compensation.
    * @param aProcessId
    */
   public void notifyCoordinatorsParticipantClosed(long aProcessId);

}
