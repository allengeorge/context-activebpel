//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/subprocess/AeSpProtocolMessage.java,v 1.2 2007/06/19 15:35:3
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
package org.activebpel.rt.bpel.server.coord.subprocess;

import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.server.coord.AeProtocolMessage;

/**
 * 
 */
public class AeSpProtocolMessage extends AeProtocolMessage implements IAeSpProtocolMessage
{
   /**
    * Target process id.
    */
   private long mProcessId = 0;
   
   /**
    * Message target location path.
    */
   private String mLocationPath = null;

   /**
    * Constructs given signal, coordination id and target pid and location path.
    * @param aSignal
    * @param aCoordinationId
    * @param aPid
    * @param aLocationPath
    */
   public AeSpProtocolMessage(String aSignal, String aCoordinationId, long aPid, String aLocationPath)
   {      
      this(aSignal, aCoordinationId, null, aPid, aLocationPath);
   }
   
   /**
    * Construts given signal, coordination id, fault, and target pid and location path. 
    * @param aSignal
    * @param aCoordinationId
    * @param aFault
    * @param aPid
    * @param aLocationPath
    */
   public AeSpProtocolMessage(String aSignal, String aCoordinationId, IAeFault aFault, 
            long aPid, String aLocationPath)
   {
      super(aSignal, aCoordinationId, aFault);
      setProcessId(aPid);
      setLocationPath(aLocationPath);
   }
   
   /**
    * Overrides method to 
    * @see org.activebpel.rt.bpel.server.coord.subprocess.IAeSpProtocolMessage#getProcessId()
    */
   public long getProcessId()
   {
      return mProcessId;
   }

   /**
    * Sets the process id.
    * @param aProcessId
    */
   protected void setProcessId(long aProcessId)
   {
      mProcessId = aProcessId;
   }
   
   /**
    * Overrides method to 
    * @see org.activebpel.rt.bpel.server.coord.subprocess.IAeSpProtocolMessage#getLocationPath()
    */
   public String getLocationPath()
   {
      return mLocationPath;
   }

   /**
    * Sets the location path.
    * @param aLocationPath
    */
   protected void setLocationPath(String aLocationPath)
   {
      mLocationPath = aLocationPath;
   }
}
