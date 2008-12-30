//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/functions/AeCreateAttachmentFunctionValidator.java,v 1.3 2007/09/04 15:48:4
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
import org.activebpel.rt.bpel.def.expr.AeScriptFuncDef;
import org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext;

/**
 * Validates the ActiveBPEL extension function createAttachment(variableName, contentType, content, [contentId])
 */
public class AeCreateAttachmentFunctionValidator extends AeAbstractActiveBpelExtensionFunctionValidator
{

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.functions.IAeFunctionValidator#validate(org.activebpel.rt.bpel.def.expr.AeScriptFuncDef,
    *      org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult,
    *      org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext)
    */
   public void validate(AeScriptFuncDef aScriptFunction, AeExpressionValidationResult aResult, IAeExpressionValidationContext aContext)
   {
      super.validate(aScriptFunction, aResult, aContext);
      
      int numArgs = aScriptFunction.getArgs().size();
      if ( numArgs < 3 || numArgs > 4 )
      {
         addError(aResult, AeMessages.getString("AeAbstractActiveBpelExtensionFunctionValidator.ERROR_INCORRECT_ARGS_NUMBER"), //$NON-NLS-1$
               new Object[] { aScriptFunction.getName(), "3 or 4" , new Integer(numArgs), aResult.getParseResult().getExpression() }); //$NON-NLS-1$
      }
      else
      {
         if ( !(aScriptFunction.isStringArgument(0)) )
            addError(aResult, AeMessages.getString("AeAbstractActiveBpelExtensionFunctionValidator.ERROR_INCORRECT_ARG_TYPE"), //$NON-NLS-1$
                  new Object[] { aScriptFunction.getName(), "1", "String", aResult.getParseResult().getExpression() });//$NON-NLS-1$//$NON-NLS-2$
         
         if ( !(aScriptFunction.isStringArgument(1) || aScriptFunction.isExpressionArgument(1)) )
            addError(aResult,
               AeMessages.getString("AeAbstractActiveBpelExtensionFunctionValidator.ERROR_INCORRECT_ARG_TYPE"), //$NON-NLS-1$
               new Object [] { aScriptFunction.getName(), "2", "String", aResult.getParseResult().getExpression() });//$NON-NLS-1$//$NON-NLS-2$
                           
         if ( !(aScriptFunction.isStringArgument(2) || aScriptFunction.isExpressionArgument(2) ))
            addError(aResult,
                  AeMessages.getString("AeAbstractActiveBpelExtensionFunctionValidator.ERROR_INCORRECT_ARG_TYPE"), //$NON-NLS-1$
                  new Object [] { aScriptFunction.getName(), "3", "expression or String", aResult.getParseResult().getExpression() });//$NON-NLS-1$//$NON-NLS-2$
         
         if (numArgs == 4 && !(aScriptFunction.isStringArgument(3)|| aScriptFunction.isExpressionArgument(3) ))
         {
            addError(aResult,
                  AeMessages.getString("AeAbstractActiveBpelExtensionFunctionValidator.ERROR_INCORRECT_ARG_TYPE"), //$NON-NLS-1$
                  new Object [] { aScriptFunction.getName(), "4", "expression or String", aResult.getParseResult().getExpression() });//$NON-NLS-1$//$NON-NLS-2$
         }
      }
   }

}
