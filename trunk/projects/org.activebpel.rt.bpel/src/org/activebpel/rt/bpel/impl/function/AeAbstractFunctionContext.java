//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/AeAbstractFunctionContext.java,v 1.1 2005/06/08 12:50:3
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
package org.activebpel.rt.bpel.impl.function;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.function.IAeFunctionContext;



/**
 * Base class for implementing <code>FunctionContext</code> impls that 
 * implement the <code>IAeFunctionContext</code> interface.
 */
public abstract class AeAbstractFunctionContext implements IAeFunctionContext
{
   // common error messages
   public static final String NO_FUNCTION_FOUND_ERROR = AeMessages.getString("AeAbstractFunctionContext.0"); //$NON-NLS-1$

   /**
    * Convenience method for no function found error message.
    * @param aLocalName
    */
   protected String formatFunctionNotFoundErrorMsg( String aLocalName )
   {
      return org.activebpel.rt.bpel.AeMessages.format(NO_FUNCTION_FOUND_ERROR, aLocalName );      
   }
}
