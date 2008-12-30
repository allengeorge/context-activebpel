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
package org.activebpel.rt.bpel.def.activity.support;

import org.activebpel.rt.bpel.def.IAeConditionParentDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Models the 'elseIf' construct in WS-BPEL 2.0.  Note that for bpel 1.1 processes, this class
 * models the switch's case child.
 */
public class AeElseIfDef extends AeElseDef implements IAeConditionParentDef
{
   /** The elseif's condition. */
   private AeConditionDef mCondition;

   /**
    * Default c'tor.
    */
   public AeElseIfDef()
   {
      super();
   }

   /**
    * @return Returns the condition.
    */
   public AeConditionDef getConditionDef()
   {
      return mCondition;
   }

   /**
    * @param aCondition The condition to set.
    */
   public void setConditionDef(AeConditionDef aCondition)
   {
      mCondition = aCondition;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.support.AeElseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
