//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/IAeProcessColumns.java,v 1.3 2007/01/26 15:05:5
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
 * Defines constants for process column names.
 */
public interface IAeProcessColumns
{
   public static final String END_DATE          = "EndDate"; //$NON-NLS-1$
   public static final String PLAN_ID           = "PlanId"; //$NON-NLS-1$
   public static final String PROCESS_ID        = "ProcessId"; //$NON-NLS-1$
   public static final String PROCESS_NAME      = "ProcessName"; //$NON-NLS-1$
   public static final String PROCESS_NAMESPACE = "ProcessNamespace"; //$NON-NLS-1$
   public static final String PROCESS_STATE     = "ProcessState"; //$NON-NLS-1$
   public static final String PROCESS_STATE_REASON = "ProcessStateReason"; //$NON-NLS-1$
   public static final String START_DATE        = "StartDate"; //$NON-NLS-1$
}
