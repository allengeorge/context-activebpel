//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/coord/IAeCoordinationConfigKeys.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.coord;

/**
 * Constants for the Coordination storage XMLDB keys.
 */
public interface IAeCoordinationConfigKeys
{
   public static final String INSERT_CONTEXT             = "InsertContext"; //$NON-NLS-1$
   public static final String UPDATE_CONTEXT             = "UpdateContext"; //$NON-NLS-1$   
   public static final String UPDATE_STATE               = "UpdateState"; //$NON-NLS-1$
   public static final String LIST_BY_COORDINATION_ID    = "ListByCoordinationId"; //$NON-NLS-1$
   public static final String LIST_BY_PROCESS_ID         = "ListByProcessId"; //$NON-NLS-1$
   public static final String LOOKUP_COORDINATION        = "LookupCoordination"; //$NON-NLS-1$
   public static final String DELETE_COORDINATION        = "DeleteCoordination"; //$NON-NLS-1$
   public static final String DELETE_BY_PROCESS_ID       = "DeleteByProcessId"; //$NON-NLS-1$
   public static final String DELETE_BY_COORDINATION_ID  = "DeleteByCoordinationId"; //$NON-NLS-1$     
   public static final String LIST_COORDINATORS_FOR_PID  = "ListCoordinatorsForProcessId"; //$NON-NLS-1$
   public static final String LIST_PARTICIPANTS_FOR_PID  = "ListParticipantsForProcessId"; //$NON-NLS-1$
}
