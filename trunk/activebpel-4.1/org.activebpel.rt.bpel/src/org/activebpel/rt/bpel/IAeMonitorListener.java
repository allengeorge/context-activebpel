// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAeMonitorListener.java,v 1.1 2007/07/31 00:48:3
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
package org.activebpel.rt.bpel;

/**
 * Interface for engine listeners.
 */
public interface IAeMonitorListener
{
   /** Monitor event sent when a process faults or is suspended because of a fault */
   public static final int MONITOR_PROCESS_FAULT = 100;
   /** Monitor event sent when a process is loaded */
   public static final int MONITOR_PROCESS_LOADED = 101;
   /** Monitor event sent when a process is loaded */
   public static final int MONITOR_PROCESS_LOAD_TIME = 102;
   /** Monitor event sent when work manager scheduled item has started */
   public static final int MONITOR_WM_START_TIME = 103;
   /** Monitor event sent with time to acquire database connection */
   public static final int MONITOR_DB_CONN_TIME = 104;
   /** Monitor event sent when correlated message is discarded */
   public static final int MONITOR_CORR_MSG_DISCARD = 105;

   /** EventData code used in MONITOR_PROCESS_FAULT to signal process ended in fault state */
   public static final long EVENT_DATA_PROCESS_FAULTED = 0;
   /** EventData code used in MONITOR_PROCESS_FAULT to signal process suspended because it was about to fault */
   public static final long EVENT_DATA_PROCESS_FAULTING = 1;

   /** EventData code used in MONITOR_PROCESS_LOADED to signal process loaded from cache */
   public static final long EVENT_DATA_PROCESS_LOAD_FROM_CACHE = 0;
   /** EventData code used in MONITOR_PROCESS_LOADED to signal process loaded from database */
   public static final long EVENT_DATA_PROCESS_LOAD_FROM_DB = 1;
   
   /**
    * Method called to notify listeners a monitor event has occurred.
    * @param aMonitorID The Id of the monitor event 
    * @param aMonitorData The data which is specific to the event id
    */
   public void handleMonitorEvent(int aMonitorID, long aMonitorData);
}
