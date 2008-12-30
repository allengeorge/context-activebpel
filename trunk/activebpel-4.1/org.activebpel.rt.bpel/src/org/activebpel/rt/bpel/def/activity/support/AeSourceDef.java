// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/support/AeSourceDef.java,v 1.6 2006/06/26 16:50:3
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

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Definition base for all named definition elements
 */
public class AeSourceDef extends AeBaseDef
{
   /** The 'linkName' attribute. */
   private String mLinkName;
   /** The 'transitionCondition' child construct. */
   private AeTransitionConditionDef mTransitionConditionDef;

   /**
    * Default constructor
    */
   public AeSourceDef()
   {
      super();
   }

   /**
    * Accessor method to obtain link name of this object.
    * 
    * @return name of link
    */
   public String getLinkName()
   {
      return mLinkName;
   }

   /**
    * Mutator method to set the link name of this object.
    * 
    * @param aLinkName name of the link
    */
   public void setLinkName(String aLinkName)
   {
      mLinkName = aLinkName;
   }
   
   /**
    * Accessor method to obtain transitionCondition property of this object.
    * 
    * @return transitionCondition expression
    */
   public String getTransitionCondition()
   {
      if (getTransitionConditionDef() != null)
      {
         return getTransitionConditionDef().getExpression();
      }
      else
      {
         return null;
      }
   }

   /**
    * @return Returns the transitionConditionDef.
    */
   public AeTransitionConditionDef getTransitionConditionDef()
   {
      return mTransitionConditionDef;
   }

   /**
    * @param aTransitionConditionDef The transitionConditionDef to set.
    */
   public void setTransitionConditionDef(AeTransitionConditionDef aTransitionConditionDef)
   {
      mTransitionConditionDef = aTransitionConditionDef;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
