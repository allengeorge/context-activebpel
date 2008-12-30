//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/IAeSchedulerColumns.java,v 1.2 2007/01/29 15:28:2
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
 * SQL table column names for scheduler table.
 */
public interface IAeSchedulerColumns
{
   public static final String SCHEDULER_ID   = "ScheduleId"; //$NON-NLS-1$
   public static final String STATE          = "State"; //$NON-NLS-1$
   public static final String TRIGGER        = "ScheduleTrigger"; //$NON-NLS-1$
   public static final String DEADLINE_MS    = "DeadlineMillis"; //$NON-NLS-1$
   public static final String STARTDATE_MS   = "StartDateMillis"; //$NON-NLS-1$
   public static final String ENDDATE_MS     = "EndDateMillis"; //$NON-NLS-1$
   public static final String LOCKED         = "Locked"; //$NON-NLS-1$
   public static final String CLASSNAME      = "Classname"; //$NON-NLS-1$

   // todo (PJ) create indices for state,locked and trigger cols - when if neccessary
   // todo (PJ) add new column to store clob to store dom instead of className
   // todo (PJ) scheduleId column should be autogen from storage layer.

}
