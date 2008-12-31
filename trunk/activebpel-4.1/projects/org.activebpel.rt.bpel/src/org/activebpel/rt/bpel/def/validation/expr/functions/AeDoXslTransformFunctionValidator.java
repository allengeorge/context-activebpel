//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/functions/AeDoXslTransformFunctionValidator.java,v 1.1 2007/06/29 22:24:0
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
 * Validates the bpel:doXslTransform() function
 * 
 * doXslTransform('string', $var/my/xpath, 'name', 'value', 'n2', $x, 'n3', count($y/some/path) )
 * 
 */
public class AeDoXslTransformFunctionValidator extends AeAbstractFunctionValidator
{
   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.functions.IAeFunctionValidator#validate(org.activebpel.rt.bpel.def.expr.AeScriptFuncDef, org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult, org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext)
    */
   public void validate(AeScriptFuncDef aScriptFunction,
         AeExpressionValidationResult aResult,
         IAeExpressionValidationContext aContext)
   {
      int numArgs = aScriptFunction.getArgs().size();
      if (numArgs < 2 || (numArgs % 2) != 0)
      {
         addError(aResult, AeMessages.getString("AeDoXslTransformFunctionValidator.INCORRECT_NUM_ARGS_TO_DOXSLTRANSFORM_ERROR"), //$NON-NLS-1$
               new Object[] { new Integer(numArgs) });
      }
      else
      {
         if ( !aScriptFunction.isStringArgument(0) )
         {
            addError(aResult, AeMessages.getString("AeDoXslTransformFunctionValidator.INVALID_ARG0_IN_DOXSLTRANSFORM_ERROR"), null); //$NON-NLS-1$
         }
         if (!(aScriptFunction.isExpressionArgument(1)))
         {
            addError(aResult, AeMessages.getString("AeDoXslTransformFunctionValidator.INVALID_ARG1_IN_DOXSLTRANSFORM_ERROR"), null); //$NON-NLS-1$
         }
         // Check the optional (key, value) arguments - each key must be a string literal
         for (int i = 2; i < aScriptFunction.getArgs().size(); i += 2)
         {
            if ( !aScriptFunction.isStringArgument(i) )
            {
               addError(
                     aResult,
                     AeMessages.getString("AeDoXslTransformFunctionValidator.INVALID_PARAM_NAME_IN_DOXSLTRANSFORM_ERROR"), new Object[] { new Integer(i + 1) }); //$NON-NLS-1$
            }
         }
      }
   }
 
}
