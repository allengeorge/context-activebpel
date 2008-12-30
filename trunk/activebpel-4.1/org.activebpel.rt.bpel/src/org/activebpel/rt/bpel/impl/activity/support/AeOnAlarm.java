// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeOnAlarm.java,v 1.18 2006/11/06 23:39:0
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
package org.activebpel.rt.bpel.impl.activity.support;

import java.util.Date;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeProcessInfoEvent;
import org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef;
import org.activebpel.rt.bpel.impl.IAeAlarmReceiver;
import org.activebpel.rt.bpel.impl.activity.AeActivityPickImpl;
import org.activebpel.rt.bpel.impl.activity.IAeEventParent;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;

/**
 * Models the <code>onAlarm</code> in a <code>pick</code> or in the 
 * <code>eventHandlers</code>.
 */
public class AeOnAlarm extends AeBaseEvent implements IAeAlarmReceiver
{

   /** Alarm execution instance reference id. */
   private int mAlarmId;
   
   /**
    * Requires the alarm def and parent to create.
    * @param aDef
    * @param aParent
    */
   public AeOnAlarm(AeOnAlarmDef aDef, IAeEventParent aParent)
   {
      super(aDef, aParent);
      setAlarmId(-1);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeAlarmReceiver#getGroupId()
    */
   public int getGroupId()
   {
      if (getParent() instanceof AeActivityPickImpl)
      {
         return getParent().getLocationId();
      }
      else
      {
         return getLocationId();
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept( IAeImplVisitor aVisitor ) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }

   /**
    * Implements the alarm execution logic.  The alarm queues a deadline
    * alarm with the engine.  The engine will then call back the alarm
    * when the deadline is reached.
    *
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      Date deadline = AeAlarmCalculator.calculateDeadline(this, getDef(), IAeProcessInfoEvent.INFO_ON_ALARM);
      queueAlarm(deadline);
   }

   /**
    * @throws AeBusinessProcessException
    */
   public void reschedule() throws AeBusinessProcessException
   {
      // no-op here
   }
   
   /**
    * Getter for the def
    */
   public AeOnAlarmDef getDef()
   {
      return (AeOnAlarmDef) getDefinition();
   }

   /**
    * Marks the alarm as queued and then queues it with the process
    * @param deadline
    * @throws AeBusinessProcessException
    */
   protected void queueAlarm(Date deadline) throws AeBusinessProcessException
   {
      setQueued(true);
      getProcess().queueAlarm(this, deadline);
   }

   /**
    * Handle the recipt of our alarm going off.
    */
   public void onAlarm() throws AeBusinessProcessException
   {
      // TODO (MF) should be able to remove the check for isQueued since we check prior to dispatch of alarm
      if (isQueued())
      {
         // set that we are no longer queued since we have been called
         setQueued(false);
   
         // queue our child activity
         executeChild();
      }
   }

   /**
    * Dequeue our alarm.
    * @see org.activebpel.rt.bpel.impl.activity.support.AeBaseEvent#dequeue()
    */
   protected void dequeue() throws AeBusinessProcessException
   {
      if (isQueued())
      {
         getProcess().dequeueAlarm(this);
         setQueued(false);
      }
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
   
   /**
    * Always returns false. 
    */
   public boolean isConcurrent()
   {
      return false;
   }
}
