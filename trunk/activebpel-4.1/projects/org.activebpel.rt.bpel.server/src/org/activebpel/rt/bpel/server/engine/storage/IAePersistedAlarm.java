// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/IAePersistedAlarm.java,v 1.4 2006/09/18 17:59:5
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
package org.activebpel.rt.bpel.server.engine.storage;

import java.util.Date;

/**
 * Represents an alarm that was stored in the persistent store.  This class
 * provides access to the details about the alarm that were persisted.
 */
public interface IAePersistedAlarm
{
   /**
    * Gets the process ID associated with this alarm.
    * 
    * @return The process ID.
    */
   public long getProcessId();

   /**
    * Gets the location path ID associated with this alarm.
    * 
    * @return The location path ID.
    */
   public int getLocationPathId();

   /**
    * Gets the alarm's deadline.
    * 
    * @return The alarm's deadline.
    */
   public Date getDeadline();
   
   /**
    * Gets the alarm's group id.
    * 
    * @return The alarm's group id.
    */
   public int getGroupId();
   
   /** 
    * @return The alarm id.
    */
   public int getAlarmId();
}
