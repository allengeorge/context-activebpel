//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/functions/AeDelegatingFunctionValidator.java,v 1.1 2007/06/29 22:24:1
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

import org.activebpel.rt.bpel.def.expr.AeScriptFuncDef;
import org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext;

/**
 * Delegating function validator. Passes validate call through to a delegate
 * validator. 
 */
public class AeDelegatingFunctionValidator extends AeAbstractFunctionValidator
{
   /** delegate that does actual validation */
   private IAeFunctionValidator mDelegate;
   
   /**
    * Ctor
    * @param aDelegate
    */
   public AeDelegatingFunctionValidator(IAeFunctionValidator aDelegate)
   {
      setDelegate(aDelegate);
   }

   /**
    * Pass through to delegate
    * 
    * @see org.activebpel.rt.bpel.def.validation.expr.functions.IAeFunctionValidator#validate(org.activebpel.rt.bpel.def.expr.AeScriptFuncDef, org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationResult, org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext)
    */
   public void validate(AeScriptFuncDef aScriptFunction,
         AeExpressionValidationResult aResult, IAeExpressionValidationContext aContext)
   {
      getDelegate().validate(aScriptFunction, aResult, aContext);
   }

   /**
    * @return the delegate
    */
   public IAeFunctionValidator getDelegate()
   {
      return mDelegate;
   }

   /**
    * @param aDelegate the delegate to set
    */
   public void setDelegate(IAeFunctionValidator aDelegate)
   {
      mDelegate = aDelegate;
   }

}
 
