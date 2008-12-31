// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/xpath/AeWSBPELXPathExpressionValidator.java,v 1.4 2007/06/29 22:24:1
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

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParser;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.bpel.def.expr.xpath.AeWSBPELXPathExpressionParser;
import org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult;

/**
 * Implements an expression validator for the XPath 1.0 expression language.  This is the default languge used
 * for BPEL 2.0 (when no expression language is specified).
 */
public class AeWSBPELXPathExpressionValidator extends AeAbstractXPathExpressionValidator
{
   /**
    * Default c'tor.
    */
   public AeWSBPELXPathExpressionValidator()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#createExpressionParser(org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext)
    */
   protected IAeExpressionParser createExpressionParser(IAeExpressionParserContext aContext)
   {
      return new AeWSBPELXPathExpressionParser(aContext);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#handleNoFunctionsInJoinCondition(org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult, org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult)
    */
   protected void handleNoFunctionsInJoinCondition(IAeExpressionParseResult aParseResult, AeExpressionValidationResult aValidationResult)
   {
      if (aParseResult.getVarNames().size() == 0)
      {
         addError(aValidationResult,
               AeMessages.getString("AeWSBPELXPathExpressionValidator.InvalidJoinConditionError"),  //$NON-NLS-1$
               new Object[] { aParseResult.getExpression() });
      }
   }
}
