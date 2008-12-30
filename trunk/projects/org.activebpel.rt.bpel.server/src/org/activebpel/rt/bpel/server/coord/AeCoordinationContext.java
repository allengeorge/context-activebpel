//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/AeCoordinationContext.java,v 1.3 2006/05/24 23:16:3
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

import java.util.Iterator;
import java.util.Properties;

import org.activebpel.rt.bpel.coord.IAeCoordinating;
import org.activebpel.rt.bpel.coord.IAeCoordinationContext;

/**
 * Simple imlementation of a coordination context.
 */
public class AeCoordinationContext extends AeContextBase implements IAeCoordinationContext
{

   /**
    * Coordination instance id.
    */
   private IAeCoordinationId mCoordinationId;
   
   /**
    * Constructs a coordination context given the id.
    */
   public AeCoordinationContext(IAeCoordinationId aCoordinationId)
   {
      super();
      mCoordinationId = aCoordinationId;
   }
   
   /**
    * Sets given properties by copying the argument content to an internal Properties object.
    * @param aProperties
    */
   public void setProperties(Properties aProperties)
   {
      if (aProperties != null)
      {
         Iterator keyIter = aProperties.keySet().iterator();
         String key = null;
         while (keyIter.hasNext())
         {
            key = (String) keyIter.next();
            setProperty(key, aProperties.getProperty(key));
         }
      }
   }

   /**
    * Returns the type of coordination this context has been activated for.
    * @return coordination type. 
    */
   public String getCoordinationType()
   {
      return getProperty(IAeCoordinating.WSCOORD_TYPE);
   }   
   
   /**
    * Returns the coordination identifier.
    * @return coordination id.
    */
   public String getIdentifier()
   {
      return getCoordinationId().getIdentifier();
   }

   /** 
    * @return the coordination identifier wrapper.
    */
   public IAeCoordinationId getCoordinationId()
   {
      return mCoordinationId;
   }
}
