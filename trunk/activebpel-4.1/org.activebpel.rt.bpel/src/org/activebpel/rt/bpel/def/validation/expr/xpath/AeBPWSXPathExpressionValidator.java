// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/xpath/AeBPWSXPathExpressionValidator.java,v 1.3 2007/06/29 22:24:1
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
import java.util.Set;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.expr.AeScriptVarDef;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult;
import org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext;

/**
 * Implements an expression validator for the XPath 1.0 expression language.  This is the default languge used
 * for BPEL 1.1 (when no expression language is specified).
 */
public class AeBPWSXPathExpressionValidator extends AeAbstractXPathExpressionValidator
{
   /**
    * Default c'tor.
    */
   public AeBPWSXPathExpressionValidator()
   {
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#doCommonExpressionValidation(org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult, org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult, org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext)
    */
   protected void doCommonExpressionValidation(IAeExpressionParseResult aParseResult, AeExpressionValidationResult aValidationResult, IAeExpressionValidationContext aContext)
   {
      super.doCommonExpressionValidation(aParseResult, aValidationResult, aContext);
      
      checkExpressionVariableReferences(aParseResult, aValidationResult);
   }

   /**
    * Checks for variable references in the expression.
    * 
    * @param aParseResult
    * @param aValidationResult
    */
   protected void checkExpressionVariableReferences(IAeExpressionParseResult aParseResult, AeExpressionValidationResult aValidationResult)
   {
      Set variables = aParseResult.getVariableReferences();
      for (Iterator iter = variables.iterator(); iter.hasNext(); )
      {
         AeScriptVarDef varDef = (AeScriptVarDef) iter.next();
         addError(aValidationResult, AeMessages.getString("AeBPEL4WSXPathExpressionValidator.ERROR_RESOLVING_XPATH_VARIABLE"), //$NON-NLS-1$
               new Object[] { varDef.getName() });
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#handleNoFunctionsInJoinCondition(org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult, org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult)
    */
   protected void handleNoFunctionsInJoinCondition(IAeExpressionParseResult aParseResult, AeExpressionValidationResult aValidationResult)
   {
      addError(aValidationResult,
            AeMessages.getString("AeBPWSXPathExpressionValidator.INVALID_JOIN_CONDITION1_ERROR"),  //$NON-NLS-1$
            new Object[] { aParseResult.getExpression() });
   }
}
