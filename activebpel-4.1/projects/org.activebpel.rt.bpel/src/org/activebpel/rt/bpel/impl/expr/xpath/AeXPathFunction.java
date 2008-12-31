//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/expr/xpath/AeXPathFunction.java,v 1.4 2006/09/07 15:06:2
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;

/**
 * Implements an xpath function given the generic ActiveBPEL <code>IAeExpressionFunction</code>.
 */
public class AeXPathFunction implements Function
{
   /** The generic function to delegate to. */
   private IAeFunction mDelegate;
   /** The expression runner context. */
   private IAeFunctionExecutionContext mFunctionExecContext;

   /**
    * Constructs this xpath function given the generic delegate function.
    * 
    * @param aFunction
    * @param aFunctionExecContext
    */
   public AeXPathFunction(IAeFunction aFunction, IAeFunctionExecutionContext aFunctionExecContext)
   {
      setDelegate(aFunction);
      setFunctionExecContext(aFunctionExecContext);
   }

   /**
    * @see org.jaxen.Function#call(org.jaxen.Context, java.util.List)
    */
   public Object call(Context aContext, List aArgs) throws FunctionCallException
   {
      try
      {
         List args = new ArrayList();
         // Convert the arguments (which were created by Jaxen) to Engine Types (Java Types) so that
         // the custom function only needs to deal with standard Java/Ae types.
         for (Iterator iter = aArgs.iterator(); iter.hasNext(); )
            args.add(getFunctionExecContext().getTypeConverter().convertToEngineType(iter.next()));

         Object rval = getDelegate().call(new AeXPathContext(aContext, getFunctionExecContext()), args);
         return getFunctionExecContext().getTypeConverter().convertToExpressionType(rval);
      }
      catch (AeFunctionCallException aef)
      {
         throw new FunctionCallException(aef);
      }
   }

   /**
    * @return Returns the delegate.
    */
   protected IAeFunction getDelegate()
   {
      return mDelegate;
   }
   
   /**
    * @param aDelegate The delegate to set.
    */
   protected void setDelegate(IAeFunction aDelegate)
   {
      mDelegate = aDelegate;
   }

   /**
    * @return Returns the function exec context.
    */
   protected IAeFunctionExecutionContext getFunctionExecContext()
   {
      return mFunctionExecContext;
   }

   /**
    * @param aFunctionExecContext The function exec context to set.
    */
   protected void setFunctionExecContext(IAeFunctionExecutionContext aFunctionExecContext)
   {
      mFunctionExecContext = aFunctionExecContext;
   }
}
