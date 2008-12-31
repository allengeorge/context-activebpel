//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/AeExpressionValidationContext.java,v 1.2 2007/06/29 22:24:1
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
package org.activebpel.rt.bpel.def.validation.expr;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.validation.expr.functions.IAeFunctionValidatorFactory;

/**
 * A simple implementation of an expression validation context.
 */
public class AeExpressionValidationContext implements IAeExpressionValidationContext
{
   /** The base def. */
   private AeBaseDef mDef;
   /** Factory used to create validators for functions found in the expression */
   private IAeFunctionValidatorFactory mFunctionFactory;

   /**
    * Simple constructor.
    * 
    * @param aDef
    */
   public AeExpressionValidationContext(AeBaseDef aDef, IAeFunctionValidatorFactory aFactory)
   {
      setDef(aDef);
      setFunctionFactory(aFactory);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext#getBaseDef()
    */
   public AeBaseDef getBaseDef()
   {
      return getDef();
   }
   
   /**
    * @return Returns the def.
    */
   protected AeBaseDef getDef()
   {
      return mDef;
   }
   
   /**
    * @param aDef The def to set.
    */
   protected void setDef(AeBaseDef aDef)
   {
      mDef = aDef;
   }

   /**
    * @return the functionFactory
    */
   public IAeFunctionValidatorFactory getFunctionFactory()
   {
      return mFunctionFactory;
   }

   /**
    * @param aFunctionFactory the functionFactory to set
    */
   protected void setFunctionFactory(IAeFunctionValidatorFactory aFunctionFactory)
   {
      mFunctionFactory = aFunctionFactory;
   }

}
