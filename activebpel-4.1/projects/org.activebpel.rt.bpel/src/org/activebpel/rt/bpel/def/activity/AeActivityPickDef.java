// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/AeActivityPickDef.java,v 1.9 2006/09/11 23:06:2
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
package org.activebpel.rt.bpel.def.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.IAeActivityCreateInstanceDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Definition for bpel pick activity.
 */
public class AeActivityPickDef extends AeActivityDef implements IAeMessageContainerDef, IAeActivityCreateInstanceDef
{
   /**
    * createInstance attribute.
    */
   private boolean mCreateInstance;
   
   /** The list of onMessage children. */
   private List mOnMessageList = new ArrayList();  // Will always be at least one
   /** The list of onAlarm children. */
   private List mOnAlarmList;                      // May have zero or more elements

   /**
    * Default constructor
    */
   public AeActivityPickDef()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageContainerDef#addOnMessageDef(org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef)
    */
   public void addOnMessageDef(AeOnMessageDef aMessage)
   {
      mOnMessageList.add(aMessage);
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageContainerDef#getOnMessageDefs()
    */
   public Iterator getOnMessageDefs()
   {
      return mOnMessageList.iterator();
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeAlarmParentDef#addAlarmDef(org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef)
    */
   public void addAlarmDef(AeOnAlarmDef aAlarm)
   {
      if (mOnAlarmList == null)
         mOnAlarmList = new ArrayList();
         
      mOnAlarmList.add(aAlarm);
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeAlarmParentDef#getAlarmDefs()
    */
   public Iterator getAlarmDefs()
   {
      if (mOnAlarmList == null || mOnAlarmList.size() == 0)
         return Collections.EMPTY_LIST.iterator();
      else
         return mOnAlarmList.iterator();
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeActivityDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * Accessor method to obtain the create instance flag.
    */
   public final boolean isCreateInstance()
   {
      return mCreateInstance;
   }

   /**
    * Mutator method to set the create instance flag for the activity.
    * 
    * @param aCreateInstance boolean flag indicating if instance should be 
    *        created for activity
    */
   public final void setCreateInstance(boolean aCreateInstance)
   {
      mCreateInstance = aCreateInstance;
   }   
}
