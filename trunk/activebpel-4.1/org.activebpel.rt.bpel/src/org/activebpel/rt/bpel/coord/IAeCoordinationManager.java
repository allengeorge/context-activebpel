//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/coord/IAeCoordinationManager.java,v 1.4 2006/06/26 16:50:4
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

import java.util.List;

import org.activebpel.rt.bpel.impl.IAeManager;


/**
 * Interface for a coordination manager.
 */
public interface IAeCoordinationManager extends IAeManager
{
   
   /**
    * Creates a coordination context.
    * @param aCtxRequest
    * @return Create context response.
    * @throws AeCoordinationException
    */
   public IAeCreateContextResponse createCoordinationContext(IAeCreateContextRequest aCtxRequest) throws AeCoordinationException;
   
   /**
    * Registers the given context for coordination.
    * @param aRegRequest registration request.
    * @return registration response.
    * @throws AeCoordinationException
    */
   public IAeRegistrationResponse register(IAeRegistrationRequest aRegRequest) throws AeCoordinationException;
   
   /**
    * Returns the coordination context given the coordination id, or returns null if not found.
    * @param aCoordinationId
    * @return coordination context if found.
    * @throws AeCoordinationException
    */
   public IAeCoordinationContext getContext(String aCoordinationId) throws AeCoordinationNotFoundException;
   
   /**
    * Returns a coordinator matching the coordination id.
    * @param aCoordinationId
    * @return the coordinator if found or null otherwise.
    */
   public IAeCoordinator getCoordinator(String aCoordinationId) throws AeCoordinationNotFoundException;
   
   /**
    * Returns a participant matching the coordination id.
    * @param aCoordinationId
    * @return the participant if found or null otherwise.
    */
   public IAeParticipant getParticipant(String aCoordinationId) throws AeCoordinationNotFoundException;

   /**
    * Returns the parent (coordinator) coordination detail given a child (participant) process id.
    * @param aChildProcessId
    * @throws AeCoordinationNotFoundException
    */
   public AeCoordinationDetail getCoordinatorDetail(long aChildProcessId) throws AeCoordinationNotFoundException;
   
   /**
    * Returns a list of participants (children) given parent process (coordinator) id.
    * @param aParentProcessId
    * @return list containing AeCoordinationDetail objects
    * @throws AeCoordinationNotFoundException
    */
   public List getParticipantDetail(long aParentProcessId) throws AeCoordinationNotFoundException;
}
