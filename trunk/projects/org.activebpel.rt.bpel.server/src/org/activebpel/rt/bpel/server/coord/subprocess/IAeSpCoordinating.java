//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/subprocess/IAeSpCoordinating.java,v 1.3 2006/05/24 23:16:3
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

import org.activebpel.rt.bpel.coord.IAeCoordinating;

/**
 * Interface indicating a AE subprocess protocol type coordinating activity.
 * The AE subprocess protocol is loosely based on Business Agreement protocol described in
 * the BPEL-4WS 1.1, appendix C. 
 */
public interface IAeSpCoordinating extends IAeCoordinating
{
   /**
    * Indicates to use AE subprocess Participant Completion coordination protocol.
    */
   public static final String AESP_PARTICIPANT_COMPLETION_PROTOCOL = "activebpel:coord:aesp:ParticipantCompletion"; //$NON-NLS-1$
   
   /**
    * Protocol message target process id .
    */
   public static final String PROTOCOL_DESTINATION_PROCESS_ID = "activebpel:coord:aesp:TargetProcessId"; //$NON-NLS-1$

   /**
    * Protocol message target location path .
    */
   public static final String PROTOCOL_DESTINATION_LOCATION_PATH = "activebpel:coord:aesp:TargetLocationPath"; //$NON-NLS-1$   

}
