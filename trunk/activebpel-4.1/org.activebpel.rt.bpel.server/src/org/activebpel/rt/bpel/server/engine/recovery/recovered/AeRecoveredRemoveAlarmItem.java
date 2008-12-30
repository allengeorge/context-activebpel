// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/recovered/AeRecoveredRemoveAlarmItem.java,v 1.3 2006/09/18 17:59:5
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;

/**
 * Implements a recovered item to remove an alarm.
 */
public class AeRecoveredRemoveAlarmItem extends AeRecoveredLocationIdItem implements IAeRecoveredItem
{
   /** Alarm Id. */
   private int mAlarmId;
   /**
    * Constructs a recovered item to remove an alarm.
    */
   public AeRecoveredRemoveAlarmItem(long aProcessId, int aLocationId, int aAlarmId)
   {
      super(aProcessId, aLocationId);
      setAlarmId(aAlarmId);
   }

   /**
    * @return Returns the alarmId.
    */
   protected int getAlarmId()
   {
      return mAlarmId;
   }



   /**
    * @param aAlarmId The alarmId to set.
    */
   protected void setAlarmId(int aAlarmId)
   {
      mAlarmId = aAlarmId;
   }



   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#queueItem(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
    */
   public void queueItem(IAeBusinessProcessEngineInternal aTargetEngine) throws AeBusinessProcessException
   {
      aTargetEngine.getQueueManager().removeAlarm(getProcessId(), getLocationId(), getAlarmId());
   }
}
