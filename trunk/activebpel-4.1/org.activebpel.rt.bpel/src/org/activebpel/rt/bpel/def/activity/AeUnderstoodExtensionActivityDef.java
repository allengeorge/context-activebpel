//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/AeUnderstoodExtensionActivityDef.java,v 1.1 2007/09/12 02:48:1
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
import org.activebpel.rt.bpel.def.IAeMultipleActivityContainerDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Def used to model the contents of an extension activity that is understood
 * by the engine. These extension activities will have been registered with the
 * engine prior to loading the process into a def. 
 * 
 * An extension activity can be a basic activity or a structured activity. As a
 * structured activity the def may contain other activities as children, including
 * other extension activities.
 */
public class AeUnderstoodExtensionActivityDef extends AeAbstractExtensionActivityDef
implements IAeMultipleActivityContainerDef
{
   /** List of child activities */
   private List mActivityList;

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      // fixme (MF) add this to the visitor
//      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeExtensionActivityDef#isUnderstood()
    */
   public boolean isUnderstood()
   {
      return true;
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeActivityContainerDef#replaceActivityDef(org.activebpel.rt.bpel.def.AeActivityDef, org.activebpel.rt.bpel.def.AeActivityDef)
    */
   public void replaceActivityDef(AeActivityDef aOldActivityDef, AeActivityDef aNewActivityDef)
   {
      AeDefUtil.replaceActivityDef(getActivityList(), aOldActivityDef, aNewActivityDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeMultipleActivityContainerDef#addActivityDef(org.activebpel.rt.bpel.def.AeActivityDef)
    */
   public void addActivityDef(AeActivityDef aActivity)
   {
      getActivityList().add(aActivity);
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeMultipleActivityContainerDef#getActivityDefs()
    */
   public Iterator getActivityDefs()
   {
      if (hasChildActivities())
      {
         return getActivityList().iterator();
      }
      else
      {
         return Collections.EMPTY_LIST.iterator();
      }
   }
   
   /**
    * Returns true if the def has child activities
    */
   public boolean hasChildActivities()
   {
      return AeUtil.notNullOrEmpty(mActivityList);
   }

   /**
    * @return the activityList
    */
   protected List getActivityList()
   {
      if (mActivityList == null)
      {
         mActivityList = new ArrayList();
      }
      return mActivityList;
   }

   /**
    * @param aActivityList the activityList to set
    */
   protected void setActivityList(List aActivityList)
   {
      mActivityList = aActivityList;
   }
}
