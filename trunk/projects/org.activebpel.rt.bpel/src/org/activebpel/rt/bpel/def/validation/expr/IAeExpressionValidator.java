//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/IAeExpressionValidator.java,v 1.2 2006/06/26 16:50:2
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
 * This interface defines a common API for classes that validate expressions. The BPEL 1.0 default expression
 * language is XPath 1.0, so there will be at least one class that implements this interface
 * (AeXPathExpressionValidator).
 */
public interface IAeExpressionValidator
{
   /**
    * This method is called to validate a generic expression. It returns a
    * <code>IAeExpressionValidationResult</code> that will be interrogated for the result of the validation.
    * This method should throw an exception only for errors that it did not expect to find. It should,
    * however, internally handle the case where the expression syntax is invalid.
    * @param aContext The expression validation context
    * @param aExpression The expression to validate.
    * 
    * @throws AeException
    */
   public IAeExpressionValidationResult validateExpression(IAeExpressionValidationContext aContext,
         String aExpression) throws AeException;

   /**
    * This method is called to validate an expression that is expected to return a Boolean. The class
    * implementing this interface (which will be expression language specific) can then provide additional
    * logic (when possible) to determine if the expression is a valid boolean expression. It is expected that
    * some languages may not be able to do any type checking at validation (untyped or dynamically typed
    * languages, for example). Such languages should simply do generic expression validation in that case.
    * @param aContext The expression validation context
    * @param aExpression
    * 
    * @throws AeException
    */
   public IAeExpressionValidationResult validateBooleanExpression(IAeExpressionValidationContext aContext,
         String aExpression) throws AeException;

   /**
    * This method is called to validate an expression that is expected to return a deadline. The class
    * implementing this interface (which will be expression language specific) can then provide additional
    * logic (when possible) to determine if the expression is a valid deadline expression. It is expected that
    * some languages may not be able to do any type checking at validation (untyped or dynamically typed
    * languages, for example). Such languages should simply do generic expression validation in that case.
    * @param aContext The expression validation context
    * @param aExpression
    * 
    * @throws AeException
    */
   public IAeExpressionValidationResult validateDeadlineExpression(IAeExpressionValidationContext aContext,
         String aExpression) throws AeException;

   /**
    * This method is called to validate an expression that is expected to return a duration. The class
    * implementing this interface (which will be expression language specific) can then provide additional
    * logic (when possible) to determine if the expression is a valid duration expression. It is expected that
    * some languages may not be able to do any type checking at validation (untyped or dynamically typed
    * languages, for example). Such languages should simply do generic expression validation in that case.
    * @param aContext The expression validation context
    * @param aExpression
    * 
    * @throws AeException
    */
   public IAeExpressionValidationResult validateDurationExpression(IAeExpressionValidationContext aContext,
         String aExpression) throws AeException;

   /**
    * This method is called to validate a join condition expression. Join conditions are subject to a set of
    * relatively strict rules. For example, only boolean operators and the bpws:getLinkStatus() function may
    * be used in the expression. In addition, the result must be a Boolean. Please refer to the BPEL
    * specification for more details.
    * @param aContext The expression validation context
    * @param aExpression
    * 
    * @throws AeException
    */
   public IAeExpressionValidationResult validateJoinConditionExpression(IAeExpressionValidationContext aContext,
         String aExpression) throws AeException;
   
   /**
    * This method is called to validate an expression that is expected to return an unsignedInt. The class
    * implementing this interface (which will be expression language specific) can then provide additional
    * logic (when possible) to determine if the expression is a valid unsignedInt expression. It is expected that
    * some languages may not be able to do any type checking at validation (untyped or dynamically typed
    * languages, for example). Such languages should simply do generic expression validation in that case.
    * @param aContext The expression validation context
    * @param aExpression
    * @throws AeException 
    */
   public IAeExpressionValidationResult validateUnsignedIntExpression(IAeExpressionValidationContext aContext, String aExpression) throws AeException;

}
