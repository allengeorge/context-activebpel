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
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.IAeConditionParentDef;
import org.activebpel.rt.bpel.def.IAeSingleActivityContainerDef;
import org.activebpel.rt.bpel.def.activity.support.AeConditionDef;
import org.activebpel.rt.bpel.def.activity.support.AeElseDef;
import org.activebpel.rt.bpel.def.activity.support.AeElseIfDef;
import org.activebpel.rt.bpel.def.activity.support.AeIfDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Definition for bpel if/switch activity.
 */
public class AeActivityIfDef extends AeActivityDef implements IAeSingleActivityContainerDef, IAeConditionParentDef
{
   /** A container for the condition and activity children of the if activity. */
   private AeIfDef mIfDef;
   /** The list of 'elseif' constructs in this if. */
   private List mElseIfs = new ArrayList();
   /** The final else child. */
   private AeElseDef mElse;

   /**
    * Default constructor
    */
   public AeActivityIfDef()
   {
      super();
   }

   /**
    * @return Returns the activity.
    */
   public AeActivityDef getActivityDef()
   {
      if (getIfDef() == null)
         return null;
      return getIfDef().getActivityDef();
   }

   /**
    * @param aActivity The activity to set.
    */
   public void setActivityDef(AeActivityDef aActivity)
   {
      if (getIfDef() == null)
         setIfDef(new AeIfDef());
      getIfDef().setActivityDef(aActivity);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.IAeActivityContainerDef#replaceActivityDef(org.activebpel.rt.bpel.def.AeActivityDef, org.activebpel.rt.bpel.def.AeActivityDef)
    */
   public void replaceActivityDef(AeActivityDef aOldActivityDef, AeActivityDef aNewActivityDef)
   {
      setActivityDef(aNewActivityDef);
   }

   /**
    * @return Returns the condition.
    */
   public AeConditionDef getConditionDef()
   {
      if (getIfDef() == null)
         return null;
      return getIfDef().getConditionDef();
   }

   /**
    * @param aCondition The condition to set.
    */
   public void setConditionDef(AeConditionDef aCondition)
   {
      if (getIfDef() == null)
         setIfDef(new AeIfDef());
      getIfDef().setConditionDef(aCondition);
   }

   /**
    * @return Returns the else.
    */
   public AeElseDef getElseDef()
   {
      return mElse;
   }

   /**
    * @param aElse The else to set.
    */
   public void setElseDef(AeElseDef aElse)
   {
      mElse = aElse;
   }
   
   /**
    * Adds an elseif construct to the def.
    * 
    * @param aElseIf
    */
   public void addElseIfDef(AeElseIfDef aElseIf)
   {
      mElseIfs.add(aElseIf);
   }
   
   /**
    * Gets an iterator over all the AeElseIfDefs.
    */
   public Iterator getElseIfDefs()
   {
      return mElseIfs.iterator();
   }

   /**
    * @return Returns the ifDef.
    */
   public AeIfDef getIfDef()
   {
      return mIfDef;
   }

   /**
    * @param aIfDef The ifDef to set.
    */
   public void setIfDef(AeIfDef aIfDef)
   {
      mIfDef = aIfDef;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeActivityDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
