// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeEvent.java,v 1.1 2006/10/20 14:41:2
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

import java.util.Date;

import org.activebpel.rt.bpel.IAeEvent;

/**
 * A base class for all AE Events.
 */
public abstract class AeEvent implements IAeEvent
{
   /** The event's timestamp. */
   private Date mTimestamp;
   
   /**
    * Default c'tor.
    */
   public AeEvent()
   {
      setTimestamp(new Date());
   }
   
   /**
    * Creates the event with the given event timestamp.
    * 
    * @param aTimestamp
    */
   public AeEvent(Date aTimestamp)
   {
      setTimestamp(aTimestamp);
   }
   
   /**
    * @see org.activebpel.rt.bpel.IAeEvent#getTimestamp()
    */
   public Date getTimestamp()
   {
      return mTimestamp;
   }
   
   /**
    * @param aTimestamp The timestamp to set.
    */
   protected void setTimestamp(Date aTimestamp)
   {
      mTimestamp = aTimestamp;
   }
}
