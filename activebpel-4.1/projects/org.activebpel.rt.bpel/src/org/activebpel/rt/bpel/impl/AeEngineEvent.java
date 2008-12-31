// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeEngineEvent.java,v 1.6 2006/10/20 14:41:2
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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.IAeEngineEvent;

/**
 * Implementation of bpel engine event.
 */
public class AeEngineEvent extends AeEvent implements IAeEngineEvent
{
   /** The process ID of the event being triggered. */
   private long mProcessID; 
   /** The ID of the event being triggered as defined in the interface. */
   private int mEventID;   
   /** The namespace qualified name of the process this event represents. */
   private QName mProcessName;

   /**
    * Constructor for BPEL engine event.
    * @param aProcessID The process ID of the event.
    * @param aEventID The event id of the event.
    * @param aProcessName The name of the process
    */
   public AeEngineEvent(long aProcessID, int aEventID, QName aProcessName)
   {
      super();
      
      mProcessID   = aProcessID;
      mEventID     = aEventID;
      mProcessName = aProcessName;
   }
   
   /**
    * Constructor for BPEL engine event.
    * 
    * @param aProcessID The process ID of the event.
    * @param aEventID The event id of the event.
    * @param aProcessName The name of the process
    * @param aTimestamp The event timestamp
    */
   public AeEngineEvent(long aProcessID, int aEventID, QName aProcessName, Date aTimestamp)
   {
      super(aTimestamp);
      
      mProcessID   = aProcessID;
      mEventID     = aEventID;
      mProcessName = aProcessName;
   }
   
   /**
    * @see org.activebpel.rt.bpel.IAeEngineEvent#getEventID()
    */
   public int getEventID()
   {
      return mEventID;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeEngineEvent#getPID()
    */
   public long getPID()
   {
      return mProcessID;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeEngineEvent#getProcessName()
    */
   public QName getProcessName()
   {
      return mProcessName;
   }
}
