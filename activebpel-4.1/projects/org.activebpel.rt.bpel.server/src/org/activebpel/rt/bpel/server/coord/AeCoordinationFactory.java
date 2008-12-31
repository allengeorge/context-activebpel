//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/AeCoordinationFactory.java,v 1.3 2007/09/07 20:52:1
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
package org.activebpel.rt.bpel.server.coord;

import org.activebpel.rt.bpel.coord.AeCoordinationException;
import org.activebpel.rt.bpel.coord.IAeCoordinating;
import org.activebpel.rt.bpel.coord.IAeCoordinationContext;
import org.activebpel.rt.bpel.coord.IAeProtocolState;
import org.activebpel.rt.bpel.impl.IAeCoordinationManagerInternal;
import org.activebpel.rt.bpel.server.coord.subprocess.AeSpCoordinator;
import org.activebpel.rt.bpel.server.coord.subprocess.AeSpParticipant;
import org.activebpel.rt.util.AeUtil;


/**
 * Factory to create objects needed for the coordination framework.
 */
public class AeCoordinationFactory
{
   /** Singleton instance. */
   private static AeCoordinationFactory sInstance = null;
   
   /** Static accessor. */
   public static synchronized AeCoordinationFactory getInstance()
   {
      if (sInstance == null)
      {
         sInstance = new AeCoordinationFactory();
      }
      return sInstance;
   }

   /**
    * Creates and returns a Coordination object.
    * @param aManager coordination manager.
    * @param aContext coordination context.
    * @param aRole role.
    * @return coordinating object.
    * @throws AeCoordinationException
    */
   public IAeCoordinating createCoordination(IAeCoordinationManagerInternal aManager, 
         IAeCoordinationContext aContext, int aRole) throws AeCoordinationException
   {
      IAeProtocolState protocolState = null;
      return createCoordination(aManager, aContext, protocolState, aRole);      
   }   
   
   /**
    * Creates and returns a Coordination object.
    * @param aManager coordination manager.
    * @param aContext coordination context.
    * @param aState state.
    * @param aRole role.
    * @return coordinating object.
    * @throws AeCoordinationException
    */
   public IAeCoordinating createCoordination(IAeCoordinationManagerInternal aManager, 
         IAeCoordinationContext aContext, String aState, int aRole) throws AeCoordinationException
   {
      IAeProtocolState protocolState = null;
      if (AeUtil.notNullOrEmpty(aState))
      {
         protocolState = new AeProtocolState(aState);
      }
      return createCoordination(aManager, aContext, protocolState, aRole);      
   }
   
   /**
    * Creates and returns a Coordination object.
    * @param aManager coordination manager.
    * @param aContext coordination context.
    * @param aState state.
    * @param aRole role.
    * @return coordinating object.
    * @throws AeCoordinationException
    */
   public IAeCoordinating createCoordination(IAeCoordinationManagerInternal aManager, 
         IAeCoordinationContext aContext, IAeProtocolState aState, int aRole) throws AeCoordinationException
   {
      AeCoordinatingBase coordinating = null;
      if (aRole == IAeCoordinating.COORDINATOR_ROLE)
      {
         coordinating = new AeSpCoordinator(aContext, aManager);            
      }
      else
      {
         coordinating = new AeSpParticipant(aContext, aManager);            
      }
      if (aState != null)
      {
         coordinating.setState(aState);
      }
      return coordinating;
   }
   
}
