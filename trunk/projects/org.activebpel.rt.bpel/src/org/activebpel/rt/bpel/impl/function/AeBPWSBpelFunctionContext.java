//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/AeBPWSBpelFunctionContext.java,v 1.1 2006/08/17 19:59:2
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

import org.activebpel.rt.bpel.function.AeUnresolvableException;
import org.activebpel.rt.bpel.function.IAeFunction;

/**
 * A <code>IAeFunctionContext</code> implementation that handles returning
 * standard BPEL 1.1 functions.
 * 
 * Currently supported function are:
 * 
 * <pre>getVariableData</pre> 
 * <pre>getVariableProperty</pre> 
 * <pre>getLinkStatus</pre> 
 */
public class AeBPWSBpelFunctionContext extends AeAbstractBpelFunctionContext
{
   // Constant for getVariableData bpel function. */
   public static final String GET_VARIABLE_DATA     = "getVariableData"; //$NON-NLS-1$
   // Constant for getLinkStatus bpel function. */
   public static final String GET_LINK_STATUS       = "getLinkStatus"; //$NON-NLS-1$

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionContext#getFunction(java.lang.String)
    */
   public IAeFunction getFunction(String aLocalName) throws AeUnresolvableException
   {
      if (GET_VARIABLE_DATA.equals(aLocalName))
      {
         return new AeGetVariableDataFunction();
      }
      else if (GET_LINK_STATUS.equals(aLocalName))
      {
         return new AeGetLinkStatusFunction();
      }
      else
      {
         return super.getFunction(aLocalName);
      }
   }
}
