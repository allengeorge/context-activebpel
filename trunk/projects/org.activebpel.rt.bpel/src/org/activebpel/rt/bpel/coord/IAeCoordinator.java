//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/coord/IAeCoordinator.java,v 1.3 2005/11/16 16:22:3
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
package org.activebpel.rt.bpel.coord;

/**
 * Defines the interface for object that acts as the coordinator.
 */
public interface IAeCoordinator extends IAeCoordinating
{
   /**
    * Registers with the process's enclosing scope. The scope or process is not completed
    * until all activities under coordination has completed and deregistered.
    * 
    * @throws AeCoordinationException
    */
   public void register() throws AeCoordinationException;
   
   /**
    * Deregisters with the process's scope. The scope should remove the coordination (id)
    * from its collection of coordinated activities.
    * 
    * @throws AeCoordinationException
    */
   public void deregister() throws AeCoordinationException;  
   
   /**
    * Initiates the compensation of the activity under coordination (ie - the participant)
    * by sending the participiant a COMPENSATE (or equivalent) message. This method is normally
    * invoked (indirectly) by process's (or scope's) compensation handler. For example, when the BPEL compensate
    * activity is executed.
    */
   public void compensate() throws AeCoordinationException;
   
   /**
    * Signals the activity under coordination (ie - the participant) to either compensate (if
    * it has completed) or cancel (it if is still running).
    * 
    * This method is normally send to participants in 'active' state during fault/comp handler execution.
    */
   public void compensateOrCancel() throws AeCoordinationException;   
   
   /**
    * Signals the activity under coordination (ie - the participant) to cancel /terminate (it if is still
    * running). If the participant is compensating, then the compensation handler will be terminated.
    * 
    * This method is normally send to participants in 'active' or 'compensating' state.
    */
   public void cancel() throws AeCoordinationException;     
   
}
