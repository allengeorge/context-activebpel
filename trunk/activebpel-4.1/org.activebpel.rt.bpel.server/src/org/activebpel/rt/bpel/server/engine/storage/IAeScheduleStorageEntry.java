//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/IAeScheduleStorageEntry.java,v 1.1 2007/01/25 21:38:1
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

public interface IAeScheduleStorageEntry
{
   /**
    * Schedule entry id
    * @return
    */
   public String getId();
   
   /**
    * Schedule trigger.
    * @return
    */
   public String getTrigger();
   
   /**
    * True if the schedule is enabled.
    * @return
    */
   public boolean isEnabled();
   
   /**
    * Engine id that has currently locked the entry.
    * @return engine id if locked or 0 otherwise.
    */
   public int getLocked();
   
   /**
    * Deadline in milli seconds.
    * @return deadline or 0 if not scheduled.
    */
   public long getDeadlineMillis();
   
   /**
    * Last known start date.
    * @return start date in ms or 0 if not started.
    */
   public long getStartDateMillis();

   /**
    * Last known end date.
    * @return end date in ms or 0 if not started.
    */   
   public long getEndDateMillis();
   
   /** 
    * @return Classname of implementation.
    */
   public String getClassname();
}
