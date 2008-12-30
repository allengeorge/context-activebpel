//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/IAeCoordinationColumns.java,v 1.1 2005/11/16 16:48:1
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
package org.activebpel.rt.bpel.server.engine.storage.sql;

/**
 * SQL table column names.
 */
public interface IAeCoordinationColumns
{
   public static final String COORDINATION_PK   = "CoordinationPk"; //$NON-NLS-1$
   public static final String ENGINE_ID         = "EngineId"; //$NON-NLS-1$
   public static final String COORDINATION_TYPE = "CoordinationType"; //$NON-NLS-1$
   public static final String COORDINATION_ROLE = "CoordinationRole"; //$NON-NLS-1$
   public static final String COORDINATION_ID   = "CoordinationId"; //$NON-NLS-1$   
   public static final String STATE             = "State"; //$NON-NLS-1$
   public static final String PROCESS_ID        = "ProcessId"; //$NON-NLS-1$
   public static final String LOCATION_PATH     = "LocationPath"; //$NON-NLS-1$
   public static final String COORDINATION_DOC  = "CoordinationDocument"; //$NON-NLS-1$
   public static final String START_DATE        = "StartDate"; //$NON-NLS-1$   
   public static final String MODIFIED_DATE     = "ModifiedDate"; //$NON-NLS-1$

}
