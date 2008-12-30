//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/functions/AeGetLinkStatusFunctionValidator.java,v 1.1 2007/06/29 22:24:0
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
package org.activebpel.rt.bpel.def.validation.expr.functions; 

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.expr.AeScriptFuncDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext;

/**
 * Validates the bpws standard getLinkStatus function
 */
public class AeGetLinkStatusFunctionValidator extends AeAbstractFunctionValidator
{
   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.functions.IAeFunctionValidator#validate(org.activebpel.rt.bpel.def.expr.AeScriptFuncDef, org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult, org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext)
    */
   public void validate(AeScriptFuncDef aScriptFunction, 
         AeExpressionValidationResult aResult,
         IAeExpressionValidationContext aContext)
   {
      int numArgs = aScriptFunction.getArgs().size();
      if (numArgs != 1)
      {
         addError(aResult,
               AeMessages.getString("AeAbstractFunctionValidator.ERROR_INCORRECT_ARGS_NUMBER"), //$NON-NLS-1$
               new Object[] { aScriptFunction.getName(), new Integer(1), new Integer(numArgs), aResult.getParseResult().getExpression() });
      }
      else if ( !(aScriptFunction.getArgument(0) instanceof String) )
      {
         addError(aResult,
               AeMessages.getString("AeAbstractFunctionValidator.ERROR_INCORRECT_ARG_TYPE"), //$NON-NLS-1$
               new Object [] { aScriptFunction.getName(), "", "String", aResult.getParseResult().getExpression() });//$NON-NLS-1$//$NON-NLS-2$
      }
      else
      {
         AeActivityDef def = AeDefUtil.getActivityDef(aContext.getBaseDef());
         String linkName = aScriptFunction.getStringArgument(0);
         if (!def.getTargetLinkNames().contains(linkName))
         {
            addError(aResult,
                  AeMessages.getString("AeGetLinkStatusFunctionValidator.LINK_DOES_NOT_EXIST_ERROR"), //$NON-NLS-1$
                  new Object[] { linkName, aResult.getParseResult().getExpression() });
         }
      }
   }
}
 
