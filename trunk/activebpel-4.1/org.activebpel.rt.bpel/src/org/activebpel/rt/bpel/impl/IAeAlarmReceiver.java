// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeAlarmReceiver.java,v 1.6 2006/09/18 17:55:3
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
package org.activebpel.rt.bpel.impl;

import org.activebpel.rt.bpel.AeBusinessProcessException;

/**
 * Defines an onAlarm callback.  An alarm receiver is used when an bpel 
 * executable object wishes to be called back when an alarm time has been reached.
 */
public interface IAeAlarmReceiver
{
   /**
    * Callback when an alarm time has been reached.
    * @throws AeBusinessProcessException Allows the receiver to throw an exception.
    */
   public void onAlarm() throws AeBusinessProcessException;

   /**
    * Returns the unique location path within the process for this receiver.
    */
   public String getLocationPath();

   /**
    * Returns the unique location id within the process for this receiver.
    */
   public int getLocationId();

   /**
    * Returns the location path of the group this alarm is a part of.  An alarm's group is
    * determined by the activity it is associated with.  This could be a Wait activity, While,
    * or Pick.
    */
   public int getGroupId();
   
   /** 
    * @return The alarm execution instance id.
    */
   public int getAlarmId();

   /** 
    * Sets the alarm execution instance id.
    * @param aAlarmId alarm id.
    */
   public void setAlarmId(int aAlarmId);
   
   /**
    * Returns <code>true</code> if and only if this object is expecting to
    * receive an alarm.
    */
   public boolean isQueued();
   
}
