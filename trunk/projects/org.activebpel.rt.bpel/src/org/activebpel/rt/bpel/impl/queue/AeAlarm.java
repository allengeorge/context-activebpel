// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/queue/AeAlarm.java,v 1.5 2006/09/18 17:55:3
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
package org.activebpel.rt.bpel.impl.queue;

import java.util.Date;

/**
 * Represents an in memory alarm queue object.
 */
public class AeAlarm
{
   /** The deadline for the event to be triggered. */
   private Date mDeadline;
   /** The path the deadline is associated with. */
   private int mPathId;
   /** The process id the deadline is associated with. */
   private long mProcessId;
   /** The group id this alarm is associated with. */
   private int mGroupId;
   /** Alarm execution reference id. */
   private int mAlarmId;

   /** 
    * Create entry for alarm queue. 
    * @param aPID process id
    * @param aPathId location id of alarm receiver.
    * @param aAlarmId alarm execution reference id.
    * @param aDeadline alarm end time. 
    * */
   public AeAlarm(long aPID, int aPathId, int aGroupId, int aAlarmId, Date aDeadline)
   {
      setProcessId(aPID);
      setPathId(aPathId);
      setGroupId(aGroupId);
      setAlarmId(aAlarmId);
      setDeadline(aDeadline);
   }

   /** 
    * Create entry for alarm queue. 
    * @param aPID process id
    * @param aPathId location id of alarm receiver.
    * @param aAlarmId alarm execution reference id.
    * */
   public AeAlarm(long aPID, int aPathId, int aAlarmId)
   {
      setProcessId(aPID);
      setPathId(aPathId);
      setAlarmId(aAlarmId);
   }

   /**
    * Overides the base to check for specific equality of alarms.
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObject)
   {
      if (aObject instanceof AeAlarm)
      {
         AeAlarm other = (AeAlarm) aObject;
         return getProcessId() == other.getProcessId() && getPathId() == other.getPathId()
            && getAlarmId() == other.getAlarmId();
      }
      return super.equals(aObject);
   }

   /**
    * @return The deadline for the event.
    */
   public Date getDeadline()
   {
      return mDeadline;
   }

   /**
    * @return The location path id of the receiver.
    */
   public int getPathId()
   {
      return mPathId;
   }

   /**
    * @return The process id of the receiver.
    */
   public long getProcessId()
   {
      return mProcessId;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return new Long(getProcessId()).hashCode() + new Integer(getPathId()).hashCode()
         + new Integer(getAlarmId()).hashCode();
   }

   /**
    * @return Returns the group id.
    */
   public int getGroupId()
   {
      return mGroupId;
   }

   /**
    * @param aGroupId The group id to set.
    */
   protected void setGroupId(int aGroupId)
   {
      mGroupId = aGroupId;
   }

   /**
    * @param aDeadline The deadline to set.
    */
   protected void setDeadline(Date aDeadline)
   {
      mDeadline = aDeadline;
   }

   /**
    * @param aPathId The pathId to set.
    */
   protected void setPathId(int aPathId)
   {
      mPathId = aPathId;
   }

   /**
    * @param aProcessId The processId to set.
    */
   protected void setProcessId(long aProcessId)
   {
      mProcessId = aProcessId;
   }

   /**
    * @return Returns the alarmId.
    */
   public int getAlarmId()
   {
      return mAlarmId;
   }

   /**
    * @param aAlarmId The alarmId to set.
    */
   public void setAlarmId(int aAlarmId)
   {
      mAlarmId = aAlarmId;
   }
   
   
}
