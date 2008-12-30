// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/recovered/AeRecoveredScheduleAlarmItem.java,v 1.4 2006/09/18 17:59:5
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
package org.activebpel.rt.bpel.server.engine.recovery.recovered;

import java.util.Date;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.server.AeMessages;

/**
 * Implements a recovered item to schedule an alarm.
 */
public class AeRecoveredScheduleAlarmItem extends AeRecoveredLocationIdItem implements IAeRecoveredItem
{
   /** The alarm's group id. */
   private final int mGroupId;
   /** The alarm deadline. */
   private final Date mDeadline;
   /** The alarm execution id. */
   private final int mAlarmId;

   /**
    * Constructs a recovered item to schedule an alarm.
    */
   public AeRecoveredScheduleAlarmItem(long aProcessId, int aLocationId, int aGroupId, int aAlarmId, Date aDeadline)
   {
      super(aProcessId, aLocationId);
      mGroupId = aGroupId;
      mDeadline = aDeadline;
      mAlarmId = aAlarmId;
   }

   /**
    * Constructs a recovered item to match another schedule alarm item by
    * location id.
    */
   public AeRecoveredScheduleAlarmItem(int aLocationId, int aAlarmId)
   {
      this(0, aLocationId, 0, aAlarmId, null);
   }

   /**
    * Returns the alarm deadline;
    */
   protected Date getDeadline()
   {
      return mDeadline;
   }

   /**
    * Returns the alarm's group id.
    */
   protected int getGroupId()
   {
      return mGroupId;
   }
   
   /**
    * Returns the alarm id.
    */
   protected int getAlarmId()
   {
      return mAlarmId;
   }   
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#queueItem(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
    */
   public void queueItem(IAeBusinessProcessEngineInternal aTargetEngine) throws AeBusinessProcessException
   {
      if (getDeadline() == null)
      {
         throw new IllegalStateException(AeMessages.getString("AeRecoveredScheduleAlarmItem.ERROR_0")); //$NON-NLS-1$
      }
      aTargetEngine.scheduleAlarm(getProcessId(), getLocationId(), getGroupId(), getAlarmId(), getDeadline());
   }
}
