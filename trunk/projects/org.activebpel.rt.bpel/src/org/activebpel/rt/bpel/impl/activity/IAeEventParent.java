// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/IAeEventParent.java,v 1.3 2004/07/08 13:09:5
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.support.AeOnAlarm;
import org.activebpel.rt.bpel.impl.activity.support.AeOnMessage;

/**
 * Interface for <code>pick</code>s and <code>eventHandlers</code>. Provides methods
 * for adding messages and alarms into the parent.
 */
public interface IAeEventParent extends IAeBpelObject
{
   /**
    * Adds the alarm to the <code>pick</code> or <code>eventHandlers</code>
    * @param aAlarm
    */
   public void addAlarm(AeOnAlarm aAlarm);

   /**
    * Adds the message to the <code>pick</code> or <code>eventHandlers</code>
    * @param aMessage
    */
   public void addMessage(AeOnMessage aMessage);
   
   /**
    * Callback from a child when it becomes active.
    * @param aChild the child becoming active.
    */
   public void childActive(IAeBpelObject aChild) throws AeBusinessProcessException;
}
