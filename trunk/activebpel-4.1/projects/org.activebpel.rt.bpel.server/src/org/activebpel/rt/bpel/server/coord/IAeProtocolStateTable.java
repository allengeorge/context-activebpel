//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/IAeProtocolStateTable.java,v 1.2 2005/11/16 16:48:1
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

import org.activebpel.rt.bpel.coord.IAeProtocolMessage;
import org.activebpel.rt.bpel.coord.IAeProtocolState;

/**
 * Interface for a protocol state transition table.
 */
public interface IAeProtocolStateTable
{   
   /**
    * Returns the state that should be transitioned to given the received message or the message about to be sent.
    * @param aCurrentState current state
    * @param aMessage message received or about to be sent.
    * @return the next state.
    */
   public IAeProtocolState getNextState(IAeProtocolState aCurrentState, IAeProtocolMessage aMessage);
   
   /**
    * Returns the initial state.
    */
   public IAeProtocolState getInitialState();
}
