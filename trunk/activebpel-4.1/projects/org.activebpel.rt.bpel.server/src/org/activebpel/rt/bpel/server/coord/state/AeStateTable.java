//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/state/AeStateTable.java,v 1.2 2005/11/16 16:48:1
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
package org.activebpel.rt.bpel.server.coord.state;

import java.util.Hashtable;
import java.util.Map;

import org.activebpel.rt.bpel.coord.IAeProtocolState;

/**
 * Class which implements state lookup table.
 */
public class AeStateTable
{
   /**
    * Map containing table row entry for a given state.
    */
   private Map mStateMap = new Hashtable();
   
   /**
    * Default constructor.
    */
   public AeStateTable()
   {     
   }
   
   /**
    * Adds the given table entry and associates it with the state.
    * @param aState
    * @param aEntry
    */
   public void add(IAeProtocolState aState, AeStateTableEntry aEntry)
   {
      mStateMap.put(aState.getState(), aEntry);
   }
   
   /**
    * Returns the table entry for the given state.
    * @param aState
    */
   public AeStateTableEntry get(IAeProtocolState aState)
   {
     return (AeStateTableEntry) mStateMap.get(aState.getState());  
   }   

}
