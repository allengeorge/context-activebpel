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
package org.activebpel.rt.bpel.def.validation.expr.xpath;

import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParser;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.bpel.def.expr.xpath.AeAbstractXPathParseResult;
import org.activebpel.rt.bpel.def.expr.xpath.AeBPWSXPathExpressionParser;
import org.activebpel.rt.bpel.def.expr.xpath.ast.visitors.AeXPathInvalidLiteralNodeVisitor;
import org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator;
import org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext;
import org.activebpel.rt.bpel.xpath.ast.AeXPathLiteralNode;

/**
 * Implements an expression validator for the XPath 1.0 expression language.  This class must be
 * extended by the BPEL 1.1 and BPEL 2.0 specific implementations.
 */
public abstract class AeAbstractXPathExpressionValidator extends AeAbstractExpressionValidator
{
   /**
    * Constructs an xpath expression validator.
    */
   public AeAbstractXPathExpressionValidator()
   {
   }

   /**
    * Overrides method to do additional validation on the xpath parse tree for join conditions.  This
    * method delegates to the superclass to do most of the validation, but then does a little bit extra.
    * 
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#doJoinConditionValidation(org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult, org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult, org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext)
    */
   protected void doJoinConditionValidation(IAeExpressionParseResult aParseResult,
         AeExpressionValidationResult aValidationResult, IAeExpressionValidationContext aContext)
   {
      super.doJoinConditionValidation(aParseResult, aValidationResult, aContext);

      validateXPathASTForJoinCondition(aParseResult, aValidationResult);
   }
   
   /**
    * Validates the XPath parse result (using the xpath-only AST structure) for additional problems
    * that might exist in the join condition, specifically invalid literal usage.
    * 
    * @param aParseResult
    * @param aValidationResult
    */
   protected void validateXPathASTForJoinCondition(IAeExpressionParseResult aParseResult,
         AeExpressionValidationResult aValidationResult)
   {
      AeXPathInvalidLiteralNodeVisitor visitor = new AeXPathInvalidLiteralNodeVisitor();
      ((AeAbstractXPathParseResult) aParseResult).getXPathAST().visitAll(visitor);
      List invalidLiterals = visitor.getLiterals();
      
      for (Iterator iter = invalidLiterals.iterator(); iter.hasNext(); )
      {
         AeXPathLiteralNode literal = (AeXPathLiteralNode) iter.next();
         addError(aValidationResult,
               AeMessages.getString("AeXPathExpressionValidator.INVALID_LITERAL_IN_JOINCONDITION_ERROR"),  //$NON-NLS-1$
               new Object [] { literal.getValue(), aParseResult.getExpression() });
         
      }
   }

   /**
    * Overrides method to supply the xpath expression parser.
    * 
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#createExpressionParser(org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext)
    */
   protected IAeExpressionParser createExpressionParser(IAeExpressionParserContext aContext)
   {
      return new AeBPWSXPathExpressionParser(aContext);
   }
}
