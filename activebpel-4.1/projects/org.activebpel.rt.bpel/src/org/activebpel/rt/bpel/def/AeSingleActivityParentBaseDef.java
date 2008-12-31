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


/**
 * A base class for any def that has a single activity as its child.
 */
public abstract class AeSingleActivityParentBaseDef extends AeBaseDef implements IAeSingleActivityContainerDef
{
   /** Activity to execute if this fault handler is called */
   private AeActivityDef mActivity;

   /**
    * Default c'tor.
    */
   public AeSingleActivityParentBaseDef()
   {
      super();
   }

   /**
    * Accessor method to obtain the activity to be executed upon for
    * the fault condition.
    * 
    * @return the activity associated with the fault condition
    */
   public AeActivityDef getActivityDef()
   {
      return mActivity;
   }

   /**
    * Mutator method to set the activity which will be executed for 
    * the fault condition.
    * 
    * @param aActivity the activity to be executed
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
}
