//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/function/AeFunctionContextWrapper.java,v 1.1 2005/06/08 12:50:2
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
package org.activebpel.rt.bpel.function;

import java.util.List;

import org.activebpel.rt.bpel.impl.expr.xpath.AeXPathContext;
import org.activebpel.rt.bpel.impl.function.AeAbstractFunctionContext;
import org.jaxen.Context;
import org.jaxen.ContextSupport;
import org.jaxen.Function;
import org.jaxen.FunctionContext;
import org.jaxen.UnresolvableException;

/**
 * Hide the <code>FunctionContext</code> from internal storage.  This will also
 * handle legacy configurations.
 */
public class AeFunctionContextWrapper extends AeAbstractFunctionContext
{
   /** delegate <code>org.jaxen.FunctionContext</code> impl */
   protected FunctionContext mDelegate;
   /** the function context's namespace */
   private String mNamespace;

   /**
    * Constructor.
    * @param aFunctionContext The delegate function context instance.
    */
   public AeFunctionContextWrapper( String aNamespace, FunctionContext aFunctionContext )
   {
      setNamespace(aNamespace);
      mDelegate = aFunctionContext;
   }

   /**
    * If a <code>Function</code> is returned from the delegate, wrap it in an <code>IAeFunction</code>
    * impl that simply performs a pass-through.
    * 
    * @see org.activebpel.rt.bpel.function.IAeFunctionContext#getFunction(java.lang.String)
    */
   public IAeFunction getFunction(String aFunctionName) throws AeUnresolvableException
   {
      try
      {
         Function function = getDelegate().getFunction( getNamespace(), null, aFunctionName );
         if (function != null)
         {
            return wrapFunction(function);
         }
         else
         {
            throw new AeUnresolvableException(formatFunctionNotFoundErrorMsg(aFunctionName));
         }
      }
      catch( UnresolvableException ure )
      {
         throw new AeUnresolvableException( ure );
      }
   }

   /**
    * Accessor for the delegate.
    */
   protected FunctionContext getDelegate()
   {
      return mDelegate;
   }
   
   /**
    * Wrap the <code>Function</code> for interface compliance.
    * 
    * @param aFunction
    */
   protected IAeFunction wrapFunction( final Function aFunction )
   {
      return new AeFunctionWrapper( aFunction );
   }
   
   /**
    * Simple wrapper class for <code>Function</code> impls.
    */
   protected class AeFunctionWrapper implements IAeFunction
   {
      /** delegate function */
      protected Function mDelegate;
      
      /**
       * Constructor.
       * @param aDelegate
       */
      protected AeFunctionWrapper( Function aDelegate )
      {
         mDelegate = aDelegate;
      }

      /**
       * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
       */
      public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
      {
         try
         {
            // Note: for now we will pass through the jaxen context if jaxen is actually
            // executing the expression.  If not, we pass in an empty context.  This should
            // be improved to instead create a valid jaxen context from our generic expression
            // context.  This will probably be relatively difficult.  However, support for
            // Jaxen impl extension functions should be considered deprecated.
            Context ctx = null;
            if (aContext instanceof AeXPathContext)
            {
               ctx = ((AeXPathContext) aContext).getContext();
            }
            else
            {
               ctx = new AeExpressionContextWrapper();
            }
            return getDelegate().call( ctx, aArgs );
         }
         catch (Throwable t)
         {
            throw new AeFunctionCallException(t);
         }
      }
      
      /**
       * Accessor for delegate function.
       */
      protected Function getDelegate()
      {
         return mDelegate;
      }
   }
   
   /**
    * Simple wrapper class for the jaxen runtime function context.
    */
   protected class AeExpressionContextWrapper extends Context
   {
      /**
       * Very simple constructor.
       */
      public AeExpressionContextWrapper()
      {
         super(new ContextSupport());
      }
   }
   
   /**
    * @return Returns the namespace.
    */
   protected String getNamespace()
   {
      return mNamespace;
   }
   
   /**
    * @param aNamespace The namespace to set.
    */
   protected void setNamespace(String aNamespace)
   {
      mNamespace = aNamespace;
   }
}
