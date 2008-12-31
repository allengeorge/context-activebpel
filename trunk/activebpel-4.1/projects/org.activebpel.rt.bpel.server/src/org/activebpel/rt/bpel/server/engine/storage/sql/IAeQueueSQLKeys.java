//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/IAeQueueSQLKeys.java,v 1.5 2006/01/03 20:34:5
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
 * Constants for the Queue storage SQL keys (keys into the SQLConfig object).
 */
public interface IAeQueueSQLKeys
{
   /** The SQL statement key for deleting an alarm. */
   public static final String DELETE_ALARM = "DeleteAlarm"; //$NON-NLS-1$
   /** The SQL statement key for deleting an alarm by its group id. */
   public static final String DELETE_ALARMS_IN_GROUP = "DeleteAlarmsByGroup"; //$NON-NLS-1$
   /** The SQL statement key for inserting an alarm. */
   public static final String INSERT_ALARM = "InsertAlarm"; //$NON-NLS-1$
   /** The SQL statement key for getting the list of alarms. */
   public static final String GET_ALARMS = "GetAlarms"; //$NON-NLS-1$

   /** The SQL statement key for getting queued receives. */
   public static final String GET_QUEUED_RECEIVE = "GetQueuedReceive"; //$NON-NLS-1$
   /** The SQL statement key for deleting queued receives by queued receive ID. */
   public static final String DELETE_QUEUED_RECEIVE_BYID = "DeleteQueuedReceiveById"; //$NON-NLS-1$
   /** The SQL statement key for deleting queued receives by location ID. */
   public static final String DELETE_QUEUED_RECEIVES_BY_LOCID = "DeleteQueuedReceiveByLocId"; //$NON-NLS-1$
   /** The SQL statement key for deleting queued receives. */
   public static final String DELETE_QUEUED_RECEIVE = "DeleteQueuedReceive"; //$NON-NLS-1$
   /** The SQL statement key for deleting queued receives by queued receive ID. */
   public static final String DELETE_QUEUED_RECEIVES_BY_GROUP = "DeleteQueuedReceivesByGroup"; //$NON-NLS-1$
   /** The SQL statement key for inserting queued receives. */
   public static final String INSERT_QUEUED_RECEIVE = "InsertQueuedReceive"; //$NON-NLS-1$
   /** The SQL statement key for getting correlated receives. */
   public static final String GET_CORRELATED_RECEIVES = "GetCorrelatedReceives"; //$NON-NLS-1$
}
