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

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Definition for the 'compensateScope' activity.
 */
public class AeActivityCompensateScopeDef extends AeActivityCompensateDef
{
   /** The name of the scope to compensate (the value of the 'target' attribute). */
   private String mTarget;  // Note: also models the BPEL 1.1 'scope' attribute of the 'compensate' activity.

   /**
    * Default constructor
    */
   public AeActivityCompensateScopeDef()
   {
      super();
   }

   /**
    * @return Returns the target.
    */
   public String getTarget()
   {
      return mTarget;
   }

   /**
    * @param aTarget The target to set.
    */
   public void setTarget(String aTarget)
   {
      mTarget = aTarget;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeActivityDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
