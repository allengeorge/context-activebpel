//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/subprocess/AeSpCoordinationStates.java,v 1.1 2005/10/28 21:10:3
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

import org.activebpel.rt.bpel.coord.IAeProtocolState;
import org.activebpel.rt.bpel.server.coord.AeProtocolState;

/**
 * AE subprocess protocol coordination states loosely based on Business Agreement protocol described in
 * the BPEL-4WS 1.1, appendix C. 
 */
public class AeSpCoordinationStates
{
   /** Initial state. */
   public static final IAeProtocolState NONE = new AeProtocolState("aesp:None"); //$NON-NLS-1$

   /** Active state */
   public static final IAeProtocolState ACTIVE = new AeProtocolState("aesp:Active"); //$NON-NLS-1$
   
   /** Canceling state.*/
   public static final IAeProtocolState CANCELING = new AeProtocolState("aesp:Canceling"); //$NON-NLS-1$
   
   /** Completed state.*/
   public static final IAeProtocolState COMPLETED = new AeProtocolState("aesp:Completed"); //$NON-NLS-1$
   
   /** Closing state. */
   public static final IAeProtocolState CLOSING = new AeProtocolState("aesp:Closing"); //$NON-NLS-1$
   
   /** Compensating state. */
   public static final IAeProtocolState COMPENSATING = new AeProtocolState("aesp:Compensating"); //$NON-NLS-1$
   
   ///** Faulted state. */
   //public static final IAeProtocolState FAULTED = new AeProtocolState("aesp:Faulted"); //$NON-NLS-1$
   
   /** Ended state. */
   public static final IAeProtocolState ENDED = new AeProtocolState("aesp:Ended"); //$NON-NLS-1$
   
   /** Faulted while active state. */
   public static final IAeProtocolState FAULTED_ACTIVE = new AeProtocolState("aesp:FaultedActive"); //$NON-NLS-1$
   
   /** Faulted while compensating state. */
   public static final IAeProtocolState FAULTED_COMPENSATING = new AeProtocolState("aesp:FaultedCompensating"); //$NON-NLS-1$
   
   /** Compensating or canceling state. */
   public static final IAeProtocolState COMPENSATING_OR_CANCELING = new AeProtocolState("aesp:CompensatingOrCanceling"); //$NON-NLS-1$
   
}
