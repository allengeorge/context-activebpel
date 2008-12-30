//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/queue/IAeQueueConfigKeys.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.queue;

/**
 * An interface that simply holds some static constants which are the names of keys in the XMLDB config
 * object.
 */
public interface IAeQueueConfigKeys
{
   /** Delete alarm query statment key. */
   public static final String DELETE_ALARM = "DeleteAlarm"; //$NON-NLS-1$
   /** Delete alarm query statment key. */
   public static final String DELETE_ALARMS_BY_GROUPID = "DeleteAlarmsByGroupId"; //$NON-NLS-1$
   /** Insert alarm query statment key. */
   public static final String INSERT_ALARM = "InsertAlarm"; //$NON-NLS-1$
   /** Get all alarms query statment key. */
   public static final String GET_ALARMS = "GetAlarms"; //$NON-NLS-1$
   /** Get alarms by filter criteria query statment key. */
   public static final String GET_ALARMS_FILTERED = "GetAlarmsFiltered"; //$NON-NLS-1$

   /** Insert queued receive query statment key. */
   public static final String INSERT_QUEUED_RECEIVE = "InsertQueuedReceive"; //$NON-NLS-1$
   /** Get a list of correlated receives query statement key. */
   public static final String GET_CORRELATED_RECEIVES = "GetCorrelatedReceives"; //$NON-NLS-1$
   /** Get a list of conflicting receives query statement key. */
   public static final String GET_CONFLICTING_RECEIVES = "GetConflictingReceives"; //$NON-NLS-1$
   /** Get a queued receive query statement key. */
   public static final String GET_QUEUED_RECEIVE = "GetQueuedReceive"; //$NON-NLS-1$
   /** Get a filtered list of queued receives query statement key. */
   public static final String GET_QUEUED_RECEIVES_FILTERED = "GetQueuedReceivesFiltered"; //$NON-NLS-1$
   /** Delete a single queued receive by id statement key. */
   public static final String DELETE_QUEUED_RECEIVE_BYID = "DeleteQueuedReceiveById"; //$NON-NLS-1$
   /** Delete all queued receives in group statement key. */
   public static final String DELETE_QUEUED_RECEIVES_BY_GROUP = "DeleteQueuedReceivesByGroup"; //$NON-NLS-1$
}
