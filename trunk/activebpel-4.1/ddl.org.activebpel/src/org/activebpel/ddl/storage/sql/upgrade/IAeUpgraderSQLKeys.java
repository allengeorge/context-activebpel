//$Header: /Development/AEDevelopment/projects/ddl.org.activebpel/src/org/activebpel/ddl/storage/sql/upgrade/IAeUpgraderSQLKeys.java,v 1.3 2005/10/17 20:43:0
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
package org.activebpel.ddl.storage.sql.upgrade;

/**
 * Constants for the storage upgrader SQL keys (keys into the SQLConfig object).
 */
public interface IAeUpgraderSQLKeys
{
   /** SQL statement key to get all alarms with bad deadlinemillis data (set to 0 after an upgrade). */
   public static final String GET_ALARMS_WITHBADDEADLINEMILLIS = "GetAlarmsWithBadDeadlineMillis"; //$NON-NLS-1$
   /** SQL statement key to update the DeadlineMillis column. */
   public static final String UPDATE_DEADLINEMILLIS = "UpdateDeadlineMillis"; //$NON-NLS-1$

   /** SQL statement key to get all queued receives. */
   public static final String GET_QUEUED_RECEIVES = "GetQueuedReceives"; //$NON-NLS-1$
   /** SQL statement key to update the DeadlineMillis column. */
   public static final String UPDATE_HASH_VALUES = "UpdateHashValues"; //$NON-NLS-1$

   /** SQL statement key to get all queued receives. */
   public static final String GET_DUPE_QUEUED_RECEIVES = "GetDupeQueuedReceives"; //$NON-NLS-1$
}
