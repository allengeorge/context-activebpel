//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/impl/xquery/AeXQueryFunctionLibrary.java,v 1.2 2006/07/10 16:41:1
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
package org.activebpel.rt.bpel.ext.expr.impl.xquery;

import net.sf.saxon.expr.Expression;
import net.sf.saxon.functions.FunctionLibrary;
import net.sf.saxon.trans.XPathException;

import org.activebpel.rt.bpel.function.AeUnresolvableException;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.function.IAeFunctionFactory;

/**
 * This class implements a Saxon XQuery function library from a generic ActiveBPEL expression
 * function context and a default Saxon expression context.
 */
public class AeXQueryFunctionLibrary implements FunctionLibrary
{
   /** The expression context to use during function execution. */
   private IAeFunctionExecutionContext mFunctionExecutionContext;

   /**
    * Constructs a function library for use with Saxon 8.x.  This function library is used to
    * bind xquery function calls in the expression to java implementations of those functions.
    * 
    * @param aFunctionExecutionContext
    */
   public AeXQueryFunctionLibrary(IAeFunctionExecutionContext aFunctionExecutionContext)
   {
      setFunctionExecutionContext(aFunctionExecutionContext);
   }
   
   /**
    * @see net.sf.saxon.functions.FunctionLibrary#bind(int, java.lang.String, java.lang.String,
    *      net.sf.saxon.expr.Expression[])
    */
   public Expression bind(int aNameCode, String aUri, String aLocal, Expression[] aStaticArgs)
         throws XPathException
   {
      try
      {
         IAeFunctionFactory funcContext = getFunctionExecutionContext().getFunctionContext();
         IAeFunction function = funcContext.getFunction(aUri, aLocal);
         if (function != null)
         {
            AeXQueryFunction func = new AeXQueryFunction(function, getFunctionExecutionContext());
            func.setArguments(aStaticArgs);
            return func;
         }
      }
      catch (AeUnresolvableException e)
      {
         // Eat this - we want to return null if the function cannot be found.
      }
      return null;
   }

   /**
    * @see net.sf.saxon.functions.FunctionLibrary#isAvailable(int, java.lang.String, java.lang.String, int)
    */
   public boolean isAvailable(int aFingerprint, String aUri, String aLocal, int arity)
   {
      try
      {
         IAeFunctionFactory funcContext = getFunctionExecutionContext().getFunctionContext();
         IAeFunction function = funcContext.getFunction(aUri, aLocal);
         if (function != null)
         {
            return true;
         }
      }
      catch (AeUnresolvableException e)
      {
         // Eat this - we want to return false if the function cannot be found.
      }
      return false;
   }

   /**
    * @see net.sf.saxon.functions.FunctionLibrary#copy()
    */
   public FunctionLibrary copy()
   {
      return new AeXQueryFunctionLibrary(getFunctionExecutionContext());
   }

   /**
    * @return Returns the functionExecutionContext.
    */
   protected IAeFunctionExecutionContext getFunctionExecutionContext()
   {
      return mFunctionExecutionContext;
   }

   /**
    * @param aFunctionExecutionContext The functionExecutionContext to set.
    */
   protected void setFunctionExecutionContext(IAeFunctionExecutionContext aFunctionExecutionContext)
   {
      mFunctionExecutionContext = aFunctionExecutionContext;
   }
}
