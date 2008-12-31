// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeEventHandlers.java,v 1.3 2004/07/08 13:09:5
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Models the events that may occur within a <code>scope</code> or <code>pick</code>
 */
public class AeEventHandlers
{
   /**
    * list of messages
    */
   private List mMessages;
   
   /**
    * list of alarms
    */
   private List mAlarms;

   /**
    * Adds the alarm to the list.
    * @see org.activebpel.rt.bpel.impl.activity.IAeEventParent#addAlarm(org.activebpel.rt.bpel.impl.activity.support.AeOnAlarm)
    */
   public void addAlarm(AeOnAlarm aAlarm)
   {
      getAlarmsList().add(aAlarm);
   }

   /**
    * Adds the message to the list.
    * @see org.activebpel.rt.bpel.impl.activity.IAeEventParent#addMessage(org.activebpel.rt.bpel.impl.activity.support.AeOnMessage)
    */
   public void addMessage(AeOnMessage aMessage)
   {
      getMessagesList().add(aMessage);
   }

   /**
    * Getter for the alarms, lazy load here. 
    */
   protected List getAlarmsList()
   {
      if (mAlarms == null)
      {
         mAlarms = new ArrayList();
      }
      return mAlarms;
   }

   /**
    * Getter for the messages, lazy load here.
    */
   protected List getMessagesList()
   {
      if (mMessages == null)
      {
         mMessages = new ArrayList();
      }
      return mMessages;
   }
   
   /**
    * Gets the alarms or empty iterator if there are none.
    */
   public Iterator getAlarms()
   {
      if (mAlarms == null)
      {
         return Collections.EMPTY_LIST.iterator();
      }
      return getAlarmsList().iterator();
   }
   
   /**
    * Gets the messages or empty iterator if there are none.
    */
   public Iterator getMessages()
   {
      if (mMessages == null)
      {
         return Collections.EMPTY_LIST.iterator();
      }
      return getMessagesList().iterator();
   }
}
