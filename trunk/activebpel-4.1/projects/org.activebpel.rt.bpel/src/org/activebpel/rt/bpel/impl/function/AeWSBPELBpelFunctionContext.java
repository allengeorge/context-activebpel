// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/AeWSBPELBpelFunctionContext.java,v 1.2 2006/08/17 19:59:2
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
 * A BPEL 2.0 version of the standard bpel function context.
 */
public class AeWSBPELBpelFunctionContext extends AeAbstractBpelFunctionContext
{
   /** Constant for doXslTransform bpel function. */
   public static final String DO_XSL_TRANSFORM = "doXslTransform"; //$NON-NLS-1$

   /**
    * Overrides method to disallow the getVariableData() function.
    * 
    * @see org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunctionContext#getFunction(java.lang.String)
    */
   public IAeFunction getFunction(String aLocalName) throws AeUnresolvableException
   {
      if (DO_XSL_TRANSFORM.equals(aLocalName))
      {
         return new AeDoXslTransformFunction();
      }
      else
      {
         return super.getFunction(aLocalName);
      }
   }
}
