//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/AeAbstractExpressionValidator.java,v 1.14 2007/06/29 22:24:1
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

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.AeBaseDefNamespaceContext;
import org.activebpel.rt.bpel.def.expr.AeExpressionLanguageUtil;
import org.activebpel.rt.bpel.def.expr.AeExpressionParserContext;
import org.activebpel.rt.bpel.def.expr.AeScriptFuncDef;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParser;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.bpel.def.validation.IAeValidationDefs;
import org.activebpel.rt.bpel.def.validation.expr.functions.IAeFunctionValidator;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.IAeNamespaceContext;

/**
 * Base class that expression validators may use to perform common functionality.
 */
public abstract class AeAbstractExpressionValidator implements IAeExpressionValidator, IAeValidationDefs
{
   /** ActiveBPEL function  namespace. */
   public final static String ACTIVE_BPEL_FUNCTION_NAMESPACE = "http://www.activebpel.org/bpel/extension"; //$NON-NLS-1$

   /** The set of functions allowed in join conditions. */
   public static Set sJoinConditionAllowedFunctions = new HashSet();
   /** The namespace that all built in functions are a part of. */
   public final static String BUILTIN_FUNCTION_NAMESPACE = null;

   /**
    * Populate the sJoinConditionAllowedFunctions map.
    */
   static
   {
      sJoinConditionAllowedFunctions.add(AeExpressionLanguageUtil.LINK_STATUS_FUNC);
      sJoinConditionAllowedFunctions.add(new QName(BUILTIN_FUNCTION_NAMESPACE, "boolean")); //$NON-NLS-1$
      sJoinConditionAllowedFunctions.add(new QName(BUILTIN_FUNCTION_NAMESPACE, "not")); //$NON-NLS-1$
      sJoinConditionAllowedFunctions.add(new QName(BUILTIN_FUNCTION_NAMESPACE, "true")); //$NON-NLS-1$
      sJoinConditionAllowedFunctions.add(new QName(BUILTIN_FUNCTION_NAMESPACE, "false")); //$NON-NLS-1$
   }

   /**
    * Constructs an expression validator.
    */
   protected AeAbstractExpressionValidator()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidator#validateExpression(org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext, java.lang.String)
    */
   public IAeExpressionValidationResult validateExpression(IAeExpressionValidationContext aContext, String aExpression) throws AeException
   {
      AeExpressionValidationResult result = new AeExpressionValidationResult();
      if (AeUtil.isNullOrEmpty(aExpression))
         return result;

      IAeExpressionParser parser = createExpressionParser(createParserContext(aContext));
      IAeExpressionParseResult parseResult = parser.parse(aExpression);
      
      result.setParseResult(parseResult);
      
      if (parseResult.hasErrors())
      {
         result.addErrors(parseResult.getParseErrors());
      }

      doCommonExpressionValidation(parseResult, result, aContext);
      doFunctionValidation(parseResult, result, aContext);
      doNonJoinConditionValidation(parseResult, result, aContext);

      return result;
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
    * @see org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidator#validateUnsignedIntExpression(org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext, java.lang.String)
    */
   public IAeExpressionValidationResult validateUnsignedIntExpression(IAeExpressionValidationContext aContext, String aExpression) throws AeException
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
      AeExpressionValidationResult result = new AeExpressionValidationResult();
      if (AeUtil.isNullOrEmpty(aExpression))
         return result;

      IAeExpressionParser parser = createExpressionParser(createParserContext(aContext));
      IAeExpressionParseResult parseResult = parser.parse(aExpression);
      
      result.setParseResult(parseResult);

      if (parseResult.hasErrors())
      {
         result.addErrors(parseResult.getParseErrors());
      }

      doCommonExpressionValidation(parseResult, result, aContext);
      doFunctionValidation(parseResult, result, aContext);
      doJoinConditionValidation(parseResult, result, aContext);

      return result;
   }
   
   /**
    * Validates all of the function calls within the expression.
    * 
    * @param aParseResult
    * @param aValidationResult
    * @param aContext
    */
   protected void doFunctionValidation(IAeExpressionParseResult aParseResult,
         AeExpressionValidationResult aValidationResult, IAeExpressionValidationContext aContext)
   {
      for (Iterator iter = aParseResult.getFunctions().iterator(); iter.hasNext();)
      {
         AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
         validateFunction(function, aValidationResult, aContext);
      }
   }

   /**
    * Validates the function using the available function 
    * @param function
    * @param aValidationResult
    * @param aContext
    */
   protected void validateFunction(AeScriptFuncDef function, AeExpressionValidationResult aValidationResult, IAeExpressionValidationContext aContext)
   {
      IAeFunctionValidator validator = aContext.getFunctionFactory().getValidator(function);
      if (validator != null)
      {
         validator.validate(function, aValidationResult, aContext);
      }
   }

   /**
    * Does some common validation steps.  
    *
    * @param aParseResult
    * @param aValidationResult
    * @param aContext
    */
   protected void doCommonExpressionValidation(IAeExpressionParseResult aParseResult,
         AeExpressionValidationResult aValidationResult, IAeExpressionValidationContext aContext)
   {
      // no-op here
   }

   /**
    * Do validation for 'normal' expressions.  This basically means all expressions that aren't
    * joinConditions.
    *
    * @param aParseResult
    * @param aValidationResult
    * @param aContext
    */
   protected void doNonJoinConditionValidation(IAeExpressionParseResult aParseResult,
         AeExpressionValidationResult aValidationResult, IAeExpressionValidationContext aContext)
   {
      // getLinkStatus can only be used in joinCondition expressions.
      if (aParseResult.getLinkStatusFunctionList().size() > 0)
      {
         addError(aValidationResult,
               AeMessages.getString("AeAbstractExpressionValidator.INVALID_USE_OF_LINKSTATUS"), //$NON-NLS-1$
               new Object[] { aParseResult, aParseResult.getExpression() });
      }
   }

   /**
    * There are additional restrictions imposed on join condition expressions.  This method will check
    * for those restrictions.
    *
    * @param aParseResult
    * @param aValidationResult
    * @param aContext
    */
   protected void doJoinConditionValidation(IAeExpressionParseResult aParseResult,
         AeExpressionValidationResult aValidationResult, IAeExpressionValidationContext aContext)
   {
      Collection functionList = aParseResult.getFunctions();
      if (functionList.size() == 0)
      {
         // If there are no functions found, then we MIGHT have an error.  It depends on the
         // language and the version of BPEL.
         handleNoFunctionsInJoinCondition(aParseResult, aValidationResult);
         return;
      }
      for (Iterator iter = functionList.iterator(); iter.hasNext(); )
      {
         AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
         if (!getJoinConditionAllowedFunctions().contains(function.getQName()))
         {
            addError(aValidationResult,
                  AeMessages.getString("AeAbstractExpressionValidator.ILLEGAL_CALL_IN_JOIN_CONDITION_ERROR"), //$NON-NLS-1$
                  new Object[] { function.getQName(), aParseResult.getExpression() });
         }
         else
         {
            validateFunction(function, aValidationResult, aContext);
         }
      }
   }

   /**
    * Called if there are no functions found in the join condition.
    * 
    * @param aParseResult
    * @param aValidationResult
    */
   protected abstract void handleNoFunctionsInJoinCondition(IAeExpressionParseResult aParseResult, AeExpressionValidationResult aValidationResult);

   /**
    * Gets the set of functions allowed in join condition expressions.
    */
   protected Set getJoinConditionAllowedFunctions()
   {
      return sJoinConditionAllowedFunctions;
   }

   /**
    * Adds an info to the validation result.
    *
    * @param aMessage
    * @param aArgs
    */
   protected void addInfo(AeExpressionValidationResult aResult, String aMessage, Object [] aArgs)
   {
      String msg = MessageFormat.format(aMessage, aArgs);
      aResult.addInfo(msg);
   }

   /**
    * Adds an error to the validation result.
    *
    * @param aMessage
    * @param aArgs
    */
   protected void addError(AeExpressionValidationResult aResult, String aMessage, Object [] aArgs)
   {
      String msg = MessageFormat.format(aMessage, aArgs);
      aResult.addError(msg);
   }

   /**
    * Adds a warning to the validation result.
    *
    * @param aMessage
    * @param aArgs
    */
   protected void addWarning(AeExpressionValidationResult aResult, String aMessage, Object [] aArgs)
   {
      String msg = MessageFormat.format(aMessage, aArgs);
      aResult.addWarning(msg);
   }

   /**
    * Creates the expression parser context from the validation context.
    *
    * @param aValidationContext
    */
   protected IAeExpressionParserContext createParserContext(IAeExpressionValidationContext aValidationContext)
   {
      IAeNamespaceContext nsCtx = new AeBaseDefNamespaceContext(aValidationContext.getBaseDef());
      return new AeExpressionParserContext(nsCtx);
   }

   /**
    * Creates the parser to use to parse the expression.
    *
    * @param aContext
    */
   abstract protected IAeExpressionParser createExpressionParser(IAeExpressionParserContext aContext);
}
