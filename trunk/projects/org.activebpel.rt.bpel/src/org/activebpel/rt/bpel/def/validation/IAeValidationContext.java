//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/IAeValidationContext.java,v 1.2 2007/06/29 22:24:1
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
 * This interface defines a context that will be used by the def validation visitor during
 * validation.  It provides access to various objects required during validation.
 */
public interface IAeValidationContext
{
   /**
    * Gets the base error reporter to use during validation.
    */
   public IAeBaseErrorReporter getErrorReporter();

   /**
    * Gets the context WSDL provider to use during validation.
    */
   public IAeContextWSDLProvider getContextWSDLProvider();
   
   /**
    * Gets the expression language factory to use during validation.
    */
   public IAeExpressionLanguageFactory getExpressionLanguageFactory();
   
   /**
    * Gets the function validator factory to use when validating function calls
    * found in expressions or queries.
    */
   public IAeFunctionValidatorFactory getFunctionValidatorFactory();
}
