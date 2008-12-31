//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/AeValidationContext.java,v 1.2 2007/06/29 22:24:1
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
package org.activebpel.rt.bpel.def.validation;

import org.activebpel.rt.bpel.IAeContextWSDLProvider;
import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.validation.expr.functions.IAeFunctionValidatorFactory;

/**
 * A simple validation context impl.
 */
public class AeValidationContext implements IAeValidationContext
{
   /** An error reporter. */
   private IAeBaseErrorReporter mErrorReporter;
   /** A context WSDL provider. */
   private IAeContextWSDLProvider mContextWSDLProvider;
   /** An expression validator. */
   private IAeExpressionLanguageFactory mExpressionFactory;
   /** factory for validating functions */
   private IAeFunctionValidatorFactory mFunctionFactory;

   /**
    * Constructs a simple validation context.
    * 
    * @param aErrorReporter
    * @param aContextWSDLProvider
    * @param aExpressionFactory
    */
   public AeValidationContext(IAeBaseErrorReporter aErrorReporter, IAeContextWSDLProvider aContextWSDLProvider, 
         IAeExpressionLanguageFactory aExpressionFactory, IAeFunctionValidatorFactory aFunctionFactory)
   {
      setErrorReporter(aErrorReporter);
      setContextWSDLProvider(aContextWSDLProvider);
      setExpressionFactory(aExpressionFactory);
      setFunctionValidatorFactory(aFunctionFactory);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeValidationContext#getFunctionValidatorFactory()
    */
   public IAeFunctionValidatorFactory getFunctionValidatorFactory()
   {
      return mFunctionFactory;
   }
   
   /**
    * Setter for function validator factory
    * @param aFactory
    */
   protected void setFunctionValidatorFactory(IAeFunctionValidatorFactory aFactory)
   {
      mFunctionFactory = aFactory;
   }

   /**
    * Gets the base error reporter to use during validation.
    */
   public IAeBaseErrorReporter getErrorReporter()
   {
      return mErrorReporter;
   }

   /**
    * Gets the context WSDL provider to use during validation.
    */
   public IAeContextWSDLProvider getContextWSDLProvider()
   {
      return mContextWSDLProvider;
   }

   /**
    * Gets the expression language factory to use during validation.
    */
   public IAeExpressionLanguageFactory getExpressionLanguageFactory()
   {
      return mExpressionFactory;
   }

   /**
    * @param aContextWSDLProvider The contextWSDLProvider to set.
    */
   protected void setContextWSDLProvider(IAeContextWSDLProvider aContextWSDLProvider)
   {
      mContextWSDLProvider = aContextWSDLProvider;
   }

   /**
    * @param aErrorReporter The errorReporter to set.
    */
   protected void setErrorReporter(IAeBaseErrorReporter aErrorReporter)
   {
      mErrorReporter = aErrorReporter;
   }

   /**
    * @param aExpressionValidator The expressionValidator to set.
    */
   protected void setExpressionFactory(IAeExpressionLanguageFactory aExpressionValidator)
   {
      mExpressionFactory = aExpressionValidator;
   }

}
