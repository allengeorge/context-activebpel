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
package org.activebpel.rt.bpel.def;

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Models the 'extensionActivity' bpel construct introduced in WS-BPEL 2.0.
 */
public class AeExtensionActivityDef extends AeActivityDef implements IAeSingleActivityContainerDef
{
   /** The extensionActivity construct must have a single activity child. */
   private AeActivityDef mActivity;

   /**
    * Default c'tor.
    */
   public AeExtensionActivityDef()
   {
      super();
   }

   /**
    * @return Returns the activity.
    */
   public AeActivityDef getActivityDef()
   {
      return mActivity;
   }

   /**
    * @param aActivity The activity to set.
    */
   public void setActivityDef(AeActivityDef aActivity)
   {
      mActivity = aActivity;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.IAeActivityContainerDef#replaceActivityDef(org.activebpel.rt.bpel.def.AeActivityDef, org.activebpel.rt.bpel.def.AeActivityDef)
    */
   public void replaceActivityDef(AeActivityDef aOldActivityDef, AeActivityDef aNewActivityDef)
   {
      setActivityDef(aNewActivityDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
