//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/IAeSchedulerSQLKeys.java,v 1.2 2007/02/05 17:10:0
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
 * Scheduler SQL query keys.
 */
public interface IAeSchedulerSQLKeys
{
   public static final String INSERT_SCHEDULE           = "InsertSchedule"; //$NON-NLS-1$
   public static final String UPDATE_SCHEDULE           = "UpdateSchedule"; //$NON-NLS-1$
   public static final String GET_ENTRY                 = "GetEntry"; //$NON-NLS-1$
   public static final String GET_ENABLED_ENTRY         = "GetEnabledEntry"; //$NON-NLS-1$
   public static final String LIST_ENABLED_ENTRIES      = "ListEnabledEntries"; //$NON-NLS-1$
   public static final String LIST_UNSCHEDULED_ENTRIES  = "ListUnscheduledEntries"; //$NON-NLS-1$
   public static final String LIST_LOCKED_ENTRIES       = "ListLockedEntries"; //$NON-NLS-1$
   public static final String UPDATE_DEADLINE           = "UpdateDeadline"; //$NON-NLS-1$
   public static final String UPDATE_ENDDATE            = "UpdateEnddate"; //$NON-NLS-1$
   public static final String CANCEL_ENTRY              = "CancelEntry"; //$NON-NLS-1$
   public static final String LOCK_ENTRY                = "LockEntry"; //$NON-NLS-1$
   public static final String UNLOCK_ENTRY              = "UnLockEntry"; //$NON-NLS-1$
   public static final String LOCK_ENTRY_FOR_SCHEDULED_EXECUTION  = "LockEntryForScheduledExecution"; //$NON-NLS-1$
   public static final String TRANSFER_LOCK             = "TransferLock"; //$NON-NLS-1$   
   public static final String LIST_LOCKED_ENTRIES_BY_ENGINE = "ListLockedEntriesByEngineId"; //$NON-NLS-1$   
}
