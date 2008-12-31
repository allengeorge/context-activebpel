//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/AeInMemoryCoordinationId.java,v 1.2 2005/11/16 16:48:1
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


/**
 * Class to construct a coordination id based on the process id and 
 * the invoke acitivity's location path.
 */
public class AeInMemoryCoordinationId implements IAeCoordinationId
{
   /**
    * context id.
    */
   private String mIdentifier = null;
   
   /**
    * Constructs a coordination id given the process id and the location path.
    * @param aProcessId
    * @param aLocationPath
    */
   public AeInMemoryCoordinationId(String aProcessId, String aLocationPath)
   {
      mIdentifier = "activebpel:coord-id:" + aProcessId + ":" + aLocationPath;  //$NON-NLS-1$  //$NON-NLS-2$
   }
   
   /**
    * Returns the coordination context id.
    */
   public String getIdentifier()
   {
      return mIdentifier;
   }
   
}
