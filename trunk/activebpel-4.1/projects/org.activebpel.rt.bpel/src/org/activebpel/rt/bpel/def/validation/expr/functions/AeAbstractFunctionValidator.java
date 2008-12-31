//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/functions/AeAbstractFunctionValidator.java,v 1.1 2007/06/29 22:24:0
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

import java.text.MessageFormat;

import org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult;

/**
 * Base class for function validators
 */
public abstract class AeAbstractFunctionValidator implements IAeFunctionValidator
{
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
}
 
