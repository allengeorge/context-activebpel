//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expressions/AeJoinConditionValidator.java,v 1.2 2007/06/22 17:48:1
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
package org.activebpel.rt.bpel.def.validation.expressions; 

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.IAeExpressionDef;
import org.activebpel.rt.bpel.def.activity.support.AeJoinConditionDef;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationResult;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidator;

/**
 * model provides validation for the joinCondition def
 */
public class AeJoinConditionValidator extends AeBaseExpressionValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeJoinConditionValidator(AeJoinConditionDef aDef)
   {
      super((IAeExpressionDef)aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expressions.AeBaseExpressionValidator#validateExpression(java.lang.String, org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidator, org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext)
    */
   protected IAeExpressionValidationResult validateExpression(String aExpression, IAeExpressionValidator aValidator, IAeExpressionValidationContext aContext) throws AeException
   {
      return aValidator.validateJoinConditionExpression(aContext, aExpression);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expressions.AeBaseExpressionValidator#recordVariablesInExpression(org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationResult)
    */
   protected void recordVariablesInExpression(IAeExpressionValidationResult aResult)
   {
      // fixme (MF) no-op for now since the variables here are actually links
   }
}
 
