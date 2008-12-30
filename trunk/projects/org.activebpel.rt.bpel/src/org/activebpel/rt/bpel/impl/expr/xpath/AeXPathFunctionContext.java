//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/expr/xpath/AeXPathFunctionContext.java,v 1.2 2006/06/26 16:50:4
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
package org.activebpel.rt.bpel.impl.expr.xpath;

import org.activebpel.rt.bpel.function.AeUnresolvableException;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.jaxen.Function;
import org.jaxen.FunctionContext;
import org.jaxen.UnresolvableException;
import org.jaxen.XPathFunctionContext;

/**
 * This class implements a jaxen function context from an ActiveBPEL function context.  This class
 * basically wraps a generic ActiveBPEL function context and delegates calls to it.
 */
public class AeXPathFunctionContext implements FunctionContext
{
   /** The expression runner context. */
   private IAeFunctionExecutionContext mFunctionExecContext;
   /** The default Jaxen function context to use. */
   private FunctionContext mDefaultContext;

   /**
    * Constructs an xpath function context from the given the expression runner context.
    * 
    * @param aFuncExecContext
    */
   public AeXPathFunctionContext(IAeFunctionExecutionContext aFuncExecContext)
   {
      setFunctionExecContext(aFuncExecContext);
      setDefaultContext(XPathFunctionContext.getInstance());
   }

   /**
    * @see org.jaxen.FunctionContext#getFunction(java.lang.String, java.lang.String, java.lang.String)
    */
   public Function getFunction(String aNamespaceURI, String aPrefix, String aLocalName)
         throws UnresolvableException
   {
      try
      {
         IAeFunction function = getFunctionExecContext().getFunctionContext().getFunction(aNamespaceURI, aLocalName);
         if (function != null)
         {
            return new AeXPathFunction(function, getFunctionExecContext());
         }
      }
      catch (AeUnresolvableException aException)
      {
         aException.logError();
         throw new UnresolvableException(aException.getLocalizedMessage());
      }

      return getDefaultContext().getFunction(aNamespaceURI, aPrefix, aLocalName);
   }

   /**
    * @return Returns the defaultContext.
    */
   protected FunctionContext getDefaultContext()
   {
      return mDefaultContext;
   }

   /**
    * @param aDefaultContext The defaultContext to set.
    */
   protected void setDefaultContext(FunctionContext aDefaultContext)
   {
      mDefaultContext = aDefaultContext;
   }

   /**
    * @return Returns the runnerContext.
    */
   protected IAeFunctionExecutionContext getFunctionExecContext()
   {
      return mFunctionExecContext;
   }

   /**
    * @param aFuncExecContext The function execution context to set.
    */
   protected void setFunctionExecContext(IAeFunctionExecutionContext aFuncExecContext)
   {
      mFunctionExecContext = aFuncExecContext;
   }
}
