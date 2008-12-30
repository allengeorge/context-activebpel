// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/AeActivityFlowDef.java,v 1.5 2006/06/26 16:50:3
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
import java.util.ListIterator;

import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.IAeMultipleActivityContainerDef;
import org.activebpel.rt.bpel.def.activity.support.AeLinksDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Definition for bpel flow activity.
 */
public class AeActivityFlowDef extends AeActivityDef implements IAeMultipleActivityContainerDef
{
   /** The list of activities in the flow. */
   private List mActivities = new ArrayList();  // Will always be at least one
   /** Container used to store links for Flow. */
   private AeLinksDef mLinks;

   /**
    * Default constructor
    */
   public AeActivityFlowDef()
   {
      super();
   }

   /**
    * Getter for the links def.
    */
   public AeLinksDef getLinksDef()
   {
      return mLinks;
   }

   /**
    * Sets the links def.
    * 
    * @param aDef
    */
   public void setLinksDef(AeLinksDef aDef)
   {
      mLinks = aDef;
   }

   /**
    * Provide a list of the Link objects for the user to iterate .
    * 
    * @return iterator of AeLinkDef objects 
    */
   public Iterator getLinkDefs()
   {
      if (mLinks == null)
         return Collections.EMPTY_LIST.iterator();
      else
         return mLinks.getLinkDefs();
   }

   /**
    * Adds an activity definition to the list of activities to execute.
    * @param aActivity the link to be added.
    */
   public void addActivityDef(AeActivityDef aActivity)
   {
      mActivities.add(aActivity);
   }

   /**
    * Provide a list of the activity elements for the user to iterate .
    * @return iterator of AeActivityDef objects
    */
   public Iterator getActivityDefs()
   {
      return mActivities.iterator();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.IAeActivityContainerDef#replaceActivityDef(org.activebpel.rt.bpel.def.AeActivityDef, org.activebpel.rt.bpel.def.AeActivityDef)
    */
   public void replaceActivityDef(AeActivityDef aOldActivityDef, AeActivityDef aNewActivityDef)
   {
      for (ListIterator liter = mActivities.listIterator(); liter.hasNext(); )
      {
         AeActivityDef activityDef = (AeActivityDef) liter.next();
         if (activityDef == aOldActivityDef)
         {
            liter.set(aNewActivityDef);
         }
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeActivityDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
