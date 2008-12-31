//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/from/AeFromExpression.java,v 1.3 2006/07/14 15:46:5
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
package org.activebpel.rt.bpel.impl.activity.assign.from; 

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.IAeExpressionDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;

/**
 * Handles executing an expression in order to determine the <from> value
 */
public class AeFromExpression extends AeFromBase
{
   /** expression to execute */
   private IAeExpressionDef mExpressionDef;
   
   /**
    * Ctor accepts def
    * 
    * @param aFromDef
    */
   public AeFromExpression(AeFromDef aFromDef)
   {
      this((IAeExpressionDef)aFromDef);
   }
   
   /**
    * Ctor accepts expression def
    * @param aExpressionDef
    */
   public AeFromExpression(IAeExpressionDef aExpressionDef)
   {
      setExpressionDef(aExpressionDef);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeFrom#getFromData()
    */
   public Object getFromData() throws AeBusinessProcessException
   {
      return getCopyOperation().getContext().executeExpression(getExpressionDef());
   }

   /**
    * @return Returns the expressionDef.
    */
   public IAeExpressionDef getExpressionDef()
   {
      return mExpressionDef;
   }

   /**
    * @param aExpressionDef The expressionDef to set.
    */
   public void setExpressionDef(IAeExpressionDef aExpressionDef)
   {
      mExpressionDef = aExpressionDef;
   }
}
 
