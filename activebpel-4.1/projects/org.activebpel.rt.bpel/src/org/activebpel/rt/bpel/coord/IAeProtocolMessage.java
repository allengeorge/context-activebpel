//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/coord/IAeProtocolMessage.java,v 1.1 2005/10/28 21:07:1
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

import org.activebpel.rt.bpel.IAeFault;

/**
 * Interface for a WS-Coordination protocol message that is exchanged between the 
 * coordinator and the participant. 
 * 
 * Note: This is an internal implementation tailored to be used with requirement 111. 
 * 
 */
public interface IAeProtocolMessage
{
   /** 
    * @return Returns protocol specific message signal.
    */
   public String getSignal();
   
   /** 
    * @return Returns the coordination id.
    */
   public String getCoordinationId();
   
   /**
    * @return Returns the fault if any or null otherwise.
    */
   public IAeFault getFault();
   
   /** 
    * Returns true if the signal of the IAeProtocolMessage being 
    * compared with equals this instance's signal.
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equalsSignal(IAeProtocolMessage aOther);   
}
