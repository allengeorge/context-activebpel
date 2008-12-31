//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/AeNoOpExpressionValidator.java,v 1.2 2006/06/26 16:50:2
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
package org.activebpel.rt.bpel.def.validation.expr;

import org.activebpel.rt.AeException;

/**
 * A no-op expression validator.  This implementation essentially returns "true" for any expression
 * passed to it.
 */
public class AeNoOpExpressionValidator implements IAeExpressionValidator
{
   /**
    * Overrides method to provide a no-op implementation.  This class can be used as the expression
    * language validator if the language can not be statically validated.  Typically, however, this
    * class will be used during development of support for a particular expression language before
    * a real expression validator implementation has been written.
    * 
    * @see org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidator#validateExpression(org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext, java.lang.String)
    */
   public IAeExpressionValidationResult validateExpression(IAeExpressionValidationContext aContext,
         String aExpression) throws AeException
   {
      return new AeExpressionValidationResult();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidator#validateBooleanExpression(org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext, java.lang.String)
    */
   public IAeExpressionValidationResult validateBooleanExpression(IAeExpressionValidationContext aContext, String aExpression)
         throws AeException
   {
      return validateExpression(aContext, aExpression);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidator#validateDeadlineExpression(org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext, java.lang.String)
    */
   public IAeExpressionValidationResult validateDeadlineExpression(IAeExpressionValidationContext aContext, String aExpression)
         throws AeException
   {
      return validateExpression(aContext, aExpression);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidator#validateDurationExpression(org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext, java.lang.String)
    */
   public IAeExpressionValidationResult validateDurationExpression(IAeExpressionValidationContext aContext, String aExpression)
         throws AeException
   {
      return validateExpression(aContext, aExpression);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidator#validateJoinConditionExpression(org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext, java.lang.String)
    */
   public IAeExpressionValidationResult validateJoinConditionExpression(IAeExpressionValidationContext aContext, String aExpression)
         throws AeException
   {
      return validateExpression(aContext, aExpression);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidator#validateUnsignedIntExpression(org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext, java.lang.String)
    */
   public IAeExpressionValidationResult validateUnsignedIntExpression(IAeExpressionValidationContext aContext, String aExpression) throws AeException
   {
      return validateExpression(aContext, aExpression);
   }
}
