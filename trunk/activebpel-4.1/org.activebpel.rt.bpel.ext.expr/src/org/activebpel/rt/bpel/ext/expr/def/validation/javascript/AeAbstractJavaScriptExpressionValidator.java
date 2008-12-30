//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/validation/javascript/AeAbstractJavaScriptExpressionValidator.java,v 1.2 2006/09/18 20:08:5
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
package org.activebpel.rt.bpel.ext.expr.def.validation.javascript;

import org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParser;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator;
import org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext;
import org.activebpel.rt.bpel.ext.expr.AeMessages;
import org.activebpel.rt.bpel.ext.expr.def.javascript.AeAbstractJavaScriptParseResult;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;

/**
 * This is a base class for JavaScript expression validators.
 */
public abstract class AeAbstractJavaScriptExpressionValidator extends AeAbstractExpressionValidator
{
   /**
    * Default c'tor.
    */
   public AeAbstractJavaScriptExpressionValidator()
   {
      super();
   }
   
   /**
    * Overrides method to do additional validation on the javascript parse tree for join conditions.  This
    * method delegates to the superclass to do most of the validation, but then does a little bit extra.
    * 
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#doJoinConditionValidation(org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult, org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult, org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext)
    */
   protected void doJoinConditionValidation(IAeExpressionParseResult aParseResult,
         AeExpressionValidationResult aValidationResult, IAeExpressionValidationContext aContext)
   {
      super.doJoinConditionValidation(aParseResult, aValidationResult, aContext);
      
      validateJSNodeForJoinCondition(((AeAbstractJavaScriptParseResult) aParseResult).getRootNode(), aParseResult,
            aValidationResult);
   }

   /**
    * Validates that the given node is ok for a join condition.  This specifically looks for 
    * literal nodes that are not contained within function calls.
    * 
    * @param aNode
    * @param aParseResult
    * @param aValidationResult
    */
   protected void validateJSNodeForJoinCondition(Node aNode, IAeExpressionParseResult aParseResult,
         AeExpressionValidationResult aValidationResult)
   {
      if (aNode.getType() == Token.CALL)
      {
         return;
      }
      else if (aNode.getType() == Token.STRING)
      {
         addError(aValidationResult,
               AeMessages.getString("AeJavaScriptExpressionValidator.INVALID_LITERAL_IN_JOINCONDITION_ERROR"),  //$NON-NLS-1$
               new Object [] { aNode.getString(), aParseResult.getExpression() });
         return;
      }
      else if (aNode.getType() == Token.NUMBER)
      {
         addError(aValidationResult,
               AeMessages.getString("AeJavaScriptExpressionValidator.INVALID_LITERAL_IN_JOINCONDITION_ERROR"),  //$NON-NLS-1$
               new Object [] { new Double(aNode.getDouble()), aParseResult.getExpression() });
         return;
      }

      Node child = aNode.getFirstChild();
      while ( child != null)
      {
         validateJSNodeForJoinCondition(child, aParseResult, aValidationResult);
         child = child.getNext();
      }
   }

   /**
    * Overrides method to supply a javascript version of the expression parser.
    * 
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#createExpressionParser(org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext)
    */
   protected abstract IAeExpressionParser createExpressionParser(IAeExpressionParserContext aContext);

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#handleNoFunctionsInJoinCondition(org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult, org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult)
    */
   protected void handleNoFunctionsInJoinCondition(IAeExpressionParseResult aParseResult, AeExpressionValidationResult aValidationResult)
   {
      // Do nothing - you can have a valid boolean expression in Javascript without function calls.
   }
}
