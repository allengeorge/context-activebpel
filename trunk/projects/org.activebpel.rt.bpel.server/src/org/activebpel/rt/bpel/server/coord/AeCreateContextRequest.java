//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/AeCreateContextRequest.java,v 1.1 2005/10/28 21:10:3
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.coord.IAeCoordinating;
import org.activebpel.rt.bpel.coord.IAeCreateContextRequest;
import org.activebpel.rt.util.AeUtil;

/**
 * Basic implementation of the create context request.
 */
public class AeCreateContextRequest extends AeContextBase implements IAeCreateContextRequest
{
   /**
    * Default constructor.
    */
   public AeCreateContextRequest()
   {
      super();
   }

   /**
    * @return coordination type.
    */
   public String getCoordinationType()
   {
      return getProperty(IAeCoordinating.WSCOORD_TYPE);
   }
   
   /**
    * Sets the type of coordination.
    * @param aCoordinationType the type of coordination.
    */
   public void setCoordinationType(String aCoordinationType)
   {
      setProperty(IAeCoordinating.WSCOORD_TYPE, aCoordinationType);
   }
   
   /** 
    * @return returns the location path
    */
   public String getLocationPath()
   {
      return getProperty(IAeCoordinating.AE_COORD_LOCATION_PATH);
   }
   
   /** 
    * @return the process id or -1 if not available.
    */
   public long getProcessId()
   {
      long pid = -1;
      if (AeUtil.notNullOrEmpty( getProperty(IAeCoordinating.AE_COORD_PID) ))
      {
         try
         {
            pid = Long.parseLong( getProperty(IAeCoordinating.AE_COORD_PID) );
         }
         catch (Exception e)
         {
            //ignore
            pid = -1;
            AeException.logError(e,e.getMessage());
         }
      }       
      return pid;
   }
   
}
