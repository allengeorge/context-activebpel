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
import org.activebpel.rt.bpel.def.IAeExpressionDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;

/**
 * Base class for all defs that specify an expression, such as transitionCondition, joinCondition, etc...
 */
public abstract class AeExpressionBaseDef extends AeBaseDef implements IAeExpressionDef
{
   /** The join condition construct's 'expressionLanguage' attribute. */
   private String mExpressionLanguage;
   /** The value of the joinCondition, which is the boolean expression. */
   private String mExpression;

   /**
    * Default c'tor.
    */
   public AeExpressionBaseDef()
   {
      super();
   }
   
   /**
    * @return Returns the expressionLanguage.
    */
   public String getExpressionLanguage()
   {
      return mExpressionLanguage;
   }

   /**
    * @param aExpressionLanguage The expressionLanguage to set.
    */
   public void setExpressionLanguage(String aExpressionLanguage)
   {
      mExpressionLanguage = aExpressionLanguage;
   }

   /**
    * @return Returns the expression.
    */
   public String getExpression()
   {
      return mExpression;
   }

   /**
    * @param aExpression The expression to set.
    */
   public void setExpression(String aExpression)
   {
      mExpression = aExpression;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.IAeExpressionDef#getBpelNamespace()
    */
   public String getBpelNamespace()
   {
      return AeDefUtil.getProcessDef(this).getNamespace();
   }
}
