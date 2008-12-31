//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/expr/xpath/AeXPathContext.java,v 1.5 2006/09/27 19:58:4
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

import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.function.IAeFunctionFactory;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.IAeFaultFactory;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionTypeConverter;
import org.activebpel.rt.xml.IAeNamespaceContext;
import org.jaxen.Context;

/**
 * Provides a runtime evaluation context for calling expression functions.  It wraps the jaxen 
 * runtime function context.
 */
public class AeXPathContext implements IAeFunctionExecutionContext
{
   /** The jaxen context. */
   private Context mContext;
   /** The proxied function exec context. */
   private IAeFunctionExecutionContext mFunctionExecContext;

   /**
    * Constructs an xpath context from a jaxen function context.
    * 
    * @param aContext
    */
   public AeXPathContext(Context aContext, IAeFunctionExecutionContext aFunctionExecContext)
   {
      setContext(aContext);
      setFunctionExecContext(aFunctionExecContext);
   }

   /**
    * @return Returns the context.
    */
   public Context getContext()
   {
      return mContext;
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionExecutionContext#getNamespaceContext()
    */
   public IAeNamespaceContext getNamespaceContext()
   {
      return getFunctionExecContext().getNamespaceContext();
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionExecutionContext#getFunctionContext()
    */
   public IAeFunctionFactory getFunctionContext()
   {
      return getFunctionExecContext().getFunctionContext();
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionExecutionContext#getEvaluationContext()
    */
   public Object getEvaluationContext()
   {
      return getFunctionExecContext().getEvaluationContext();
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionExecutionContext#getAbstractBpelObject()
    */
   public AeAbstractBpelObject getAbstractBpelObject()
   {
      return getFunctionExecContext().getAbstractBpelObject();
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionExecutionContext#getFaultFactory()
    */
   public IAeFaultFactory getFaultFactory()
   {
      return getAbstractBpelObject().getFaultFactory();
   }
   
   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionExecutionContext#getBpelNamespace()
    */
   public String getBpelNamespace()
   {
      return getFunctionExecContext().getBpelNamespace();
   }
   
   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionExecutionContext#getTypeConverter()
    */
   public IAeExpressionTypeConverter getTypeConverter()
   {
      return getFunctionExecContext().getTypeConverter();
   }

   /**
    * @param aContext The context to set.
    */
   protected void setContext(Context aContext)
   {
      mContext = aContext;
   }
   
   /**
    * @return Returns the functionExecContext.
    */
   protected IAeFunctionExecutionContext getFunctionExecContext()
   {
      return mFunctionExecContext;
   }
   
   /**
    * @param aFunctionExecContext The functionExecContext to set.
    */
   protected void setFunctionExecContext(IAeFunctionExecutionContext aFunctionExecContext)
   {
      mFunctionExecContext = aFunctionExecContext;
   }
}
