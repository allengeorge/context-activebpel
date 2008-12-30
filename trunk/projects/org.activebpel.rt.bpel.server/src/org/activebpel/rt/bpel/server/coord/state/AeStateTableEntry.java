//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/state/AeStateTableEntry.java,v 1.2 2005/11/16 16:48:1
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

import org.activebpel.rt.bpel.coord.IAeProtocolMessage;
import org.activebpel.rt.bpel.coord.IAeProtocolState;

/**
 * This class contains a map associating various coordination signals (messages) with states.
 * This object is used as a entry of each of the entries in the AeStateTable.
 */
public class AeStateTableEntry
{
   /**
    * Map containing various states, keyed by message.
    */
   private Map mMessageStateMap = new Hashtable();
   
   /**
    * Default construct.
    */
   public AeStateTableEntry()
   {     
   }
   
   /**
    * Adds and associates the given message with the state (that should be transitioned to).
    * @param aMessage
    * @param aState
    */
   public void add(IAeProtocolMessage aMessage, IAeProtocolState aState)
   {
      mMessageStateMap.put(aMessage.getSignal(), aState);
   }
   
   /**
    * Returns the state (which should be transitioned to), given the message signal.
    * @param aMessage
    */
   public IAeProtocolState get(IAeProtocolMessage aMessage)
   {
     return (IAeProtocolState) mMessageStateMap.get(aMessage.getSignal());  
   }    
}
