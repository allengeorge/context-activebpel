//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/validation/xquery/AeAbstractXQueryExpressionValidator.java,v 1.2 2006/09/15 14:53:3
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
package org.activebpel.rt.bpel.ext.expr.def.validation.xquery;

import java.util.Iterator;

import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.FunctionCall;
import net.sf.saxon.value.AtomicValue;
import net.sf.saxon.value.BooleanValue;

import org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParser;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator;
import org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext;
import org.activebpel.rt.bpel.ext.expr.AeMessages;
import org.activebpel.rt.bpel.ext.expr.def.xquery.AeBPWSXQueryExpressionParser;
import org.activebpel.rt.bpel.ext.expr.def.xquery.AeAbstractXQueryParseResult;

/**
 * Implements an expression validator for the XQuery 1.0 expression language.
 */
public abstract class AeAbstractXQueryExpressionValidator extends AeAbstractExpressionValidator
{
   /**
    * Constructs an xquery expression validator.
    */
   public AeAbstractXQueryExpressionValidator()
   {
   }

   /**
    * Overrides method to do additional validation on the xquery parse tree for join conditions.  This
    * method delegates to the superclass to do most of the validation, but then does a little bit extra.
    * 
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#doJoinConditionValidation(org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult, org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult, org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext)
    */
   protected void doJoinConditionValidation(IAeExpressionParseResult aParseResult,
         AeExpressionValidationResult aValidationResult, IAeExpressionValidationContext aContext)
   {
      super.doJoinConditionValidation(aParseResult, aValidationResult, aContext);
      
      Expression node = ((AeAbstractXQueryParseResult) aParseResult).getXQueryExpression();
      validateXQueryNode(node, aParseResult, aValidationResult);
   }

   /**
    * Validates that the given node is ok for a join condition.  This specifically looks for 
    * literal nodes that are not contained within function calls.
    * 
    * @param aNode
    * @param aParseResult
    * @param aValidationResult
    */
   protected void validateXQueryNode(Expression aNode, IAeExpressionParseResult aParseResult, 
         AeExpressionValidationResult aValidationResult)
   {
      if (aNode instanceof FunctionCall)
      {
         return;
      }
      else if (aNode instanceof AtomicValue && !(aNode instanceof BooleanValue))
      {
         AtomicValue value = (AtomicValue) aNode;
         addError(aValidationResult, AeMessages.getString("AeXQueryExpressionValidator.INVALID_LITERAL_IN_JOINCONDITION_ERROR"),  //$NON-NLS-1$
               new Object [] { value.getStringValue(), aParseResult.getExpression() });
         return;
      }

      // Now process all of the children.
      for (Iterator iter = aNode.iterateSubExpressions(); iter.hasNext(); )
      {
         Expression child = (Expression) iter.next();
         validateXQueryNode(child, aParseResult, aValidationResult);
      }
   }

   /**
    * Overrides method to supply the xpath expression parser.
    * 
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#createExpressionParser(org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext)
    */
   protected IAeExpressionParser createExpressionParser(IAeExpressionParserContext aContext)
   {
      return new AeBPWSXQueryExpressionParser(aContext);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#handleNoFunctionsInJoinCondition(org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult, org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult)
    */
   protected void handleNoFunctionsInJoinCondition(IAeExpressionParseResult aParseResult, AeExpressionValidationResult aValidationResult)
   {
      // Do nothing, you can have a valid boolean XQuery expression without using functions.
   }
}
