// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeActivityWaitImpl.java,v 1.21 2006/09/18 17:55:3
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
package org.activebpel.rt.bpel.impl.activity;

import java.util.Date;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeProcessInfoEvent;
import org.activebpel.rt.bpel.def.activity.AeActivityWaitDef;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.IAeAlarmReceiver;
import org.activebpel.rt.bpel.impl.activity.support.AeAlarmCalculator;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;

/**
 * Implementation of the bpel wait activity.
 */
public class AeActivityWaitImpl extends AeActivityImpl implements IAeAlarmReceiver
{
   /** True while the wait activity is queued */
   private boolean mQueued;
   
   /** Alarm execution instance reference id. */
   private int mAlarmId;
   
   
   /** default constructor for activity */
   public AeActivityWaitImpl(AeActivityWaitDef aActivityDef, IAeActivityParent aParent)
   {
      super(aActivityDef, aParent);
      setAlarmId(-1);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept( IAeImplVisitor aVisitor ) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeAlarmReceiver#getGroupId()
    */
   public int getGroupId()
   {
      return getLocationId();
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      Date deadline = AeAlarmCalculator.calculateDeadline(this, getDef(), IAeProcessInfoEvent.INFO_WAIT);
      setQueued(true);
      getProcess().queueAlarm(this, deadline);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeAlarmReceiver#onAlarm()
    */
   public void onAlarm() throws AeBusinessProcessException
   {
      if (isQueued())
      {
         setQueued(false);
         objectCompleted();
      }
   }
   
   /**
    * Overrides method to extend base in order to dequeue any queued alarm. So if we are 
    * terminating or completing with queued alarms they should be dequeued. 
    * @see org.activebpel.rt.bpel.impl.IAeExecutableQueueItem#setState(org.activebpel.rt.bpel.impl.AeBpelState)
    */
   public void setState(AeBpelState aNewState) throws AeBusinessProcessException
   {
      dequeue();
      super.setState(aNewState);
   }
   
   /**
    * Dequeues the wait from the process.
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
    * Setter for the queued flag
    * @param aBool
    */
   public void setQueued(boolean aBool)
   {
      mQueued = aBool;
   }

   /**
    * Getter for the queued flag
    */
   public boolean isQueued()
   {
      return mQueued;
   }
   
   /**
    * Getter for the def
    */
   protected AeActivityWaitDef getDef()
   {
      return (AeActivityWaitDef) getDefinition();
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
