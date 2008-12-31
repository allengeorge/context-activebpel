//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/AeRegistrationRequest.java,v 1.1 2005/10/28 21:10:3
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

import org.activebpel.rt.bpel.coord.IAeCoordinating;
import org.activebpel.rt.bpel.coord.IAeCoordinationContext;
import org.activebpel.rt.bpel.coord.IAeRegistrationRequest;

/**
 * Simple implementation of the registration request.
 */
public class AeRegistrationRequest extends AeContextBase implements IAeRegistrationRequest
{

   /**
    * Coordination context.
    */
   private IAeCoordinationContext mCoordinationContext;
   
   /**
    * Default constructor.
    */
   public AeRegistrationRequest()
   {
      super();
   }

   /** 
    * Overrides method to 
    * @see org.activebpel.rt.bpel.coord.IAeRegistrationRequest#getCoordinationContext()
    */
   public IAeCoordinationContext getCoordinationContext()
   {
      return mCoordinationContext;
   }
   
   /**
    * Sets the coordination context.
    * @param aCoordinationContext
    */
   public void setCoordinationContext(IAeCoordinationContext aCoordinationContext)
   {
      mCoordinationContext = aCoordinationContext;
   }
   
   
   /**
    * Overrides method to 
    * @see org.activebpel.rt.bpel.coord.IAeRegistrationRequest#getProtocolIdentifier()
    */
   public String getProtocolIdentifier()
   {
      return getProperty(IAeCoordinating.WSCOORD_PROTOCOL);
   }

   /**
    * Overrides method to 
    * @see org.activebpel.rt.bpel.coord.IAeRegistrationRequest#setProtocolIdentifier(java.lang.String)
    */
   public void setProtocolIdentifier(String aProtocolId)
   {
      setProperty(IAeCoordinating.WSCOORD_PROTOCOL, aProtocolId);
   }

}
