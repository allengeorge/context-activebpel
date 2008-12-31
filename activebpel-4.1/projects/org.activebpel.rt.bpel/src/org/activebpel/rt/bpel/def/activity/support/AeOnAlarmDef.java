// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/support/AeOnAlarmDef.java,v 1.9 2006/07/20 20:45:0
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

import org.activebpel.rt.bpel.def.AeSingleActivityParentBaseDef;
import org.activebpel.rt.bpel.def.IAeForUntilParentDef;
import org.activebpel.rt.bpel.def.IAeSingleActivityContainerDef;
import org.activebpel.rt.bpel.def.activity.IAeTimedDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;


/** The onAlarm element used within the Pick activity */
public class AeOnAlarmDef extends AeSingleActivityParentBaseDef implements IAeSingleActivityContainerDef, IAeTimedDef, IAeForUntilParentDef
{
   /** The 'for' child construct. */
   private AeForDef mForDef;
   /** The 'until' child construct. */
   private AeUntilDef mUntilDef;
   /** The 'repeatEvery' child construct. */
   private AeRepeatEveryDef mRepeatEvery;

   /**
    * Default constructor
    */
   public AeOnAlarmDef()
   {
      super();
   }

   /**
    * Accessor method to obtain the For attribute.
    * 
    * @return name of For attribute
    */
   public String getFor()
   {
      if (mForDef != null)
      {
         return mForDef.getExpression();
      }
      else
      {
         return null;
      }
   }

   /**
    * Accessor method to obtain the Until attribute.
    * 
    * @return name of Until attribute
    */
   public String getUntil()
   {
      if (mUntilDef != null)
      {
         return mUntilDef.getExpression();
      }
      else
      {
         return null;
      }
   }

   /**
    * @return Returns the forDef.
    */
   public AeForDef getForDef()
   {
      return mForDef;
   }

   /**
    * @param aForDef The forDef to set.
    */
   public void setForDef(AeForDef aForDef)
   {
      mForDef = aForDef;
   }

   /**
    * @return Returns the untilDef.
    */
   public AeUntilDef getUntilDef()
   {
      return mUntilDef;
   }

   /**
    * @param aUntilDef The untilDef to set.
    */
   public void setUntilDef(AeUntilDef aUntilDef)
   {
      mUntilDef = aUntilDef;
   }

   /**
    * @return Returns the repeatEvery.
    */
   public AeRepeatEveryDef getRepeatEveryDef()
   {
      return mRepeatEvery;
   }

   /**
    * @param aRepeatEvery The repeatEvery to set.
    */
   public void setRepeatEveryDef(AeRepeatEveryDef aRepeatEvery)
   {
      mRepeatEvery = aRepeatEvery;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
