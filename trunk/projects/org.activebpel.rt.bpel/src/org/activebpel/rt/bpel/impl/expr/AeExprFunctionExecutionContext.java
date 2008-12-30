//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/expr/AeExprFunctionExecutionContext.java,v 1.5 2006/09/27 19:58:4
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
package org.activebpel.rt.bpel.impl.expr;

import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.function.IAeFunctionFactory;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.IAeFaultFactory;
import org.activebpel.rt.xml.IAeNamespaceContext;

/**
 * Implements an IAeExpressionContext.  This implementation basically wraps the current expression 
 * <b>runner</b> context.
 */
public class AeExprFunctionExecutionContext implements IAeFunctionExecutionContext
{
   /** The expression runner context. */
   private IAeExpressionRunnerContext mRunnerContext;
   /** The type converter. */
   private IAeExpressionTypeConverter mExpressionTypeConverter;

   /**
    * Constructs an expression context from the given expression runner context.
    * 
    * @param aRunnerContext
    * @param aExpressionTypeConverter
    */
   public AeExprFunctionExecutionContext(IAeExpressionRunnerContext aRunnerContext,
         IAeExpressionTypeConverter aExpressionTypeConverter)
   {
      setRunnerContext(aRunnerContext);
      setExpressionTypeConverter(aExpressionTypeConverter);
   }

   /**
    * @return Returns the runnerContext.
    */
   protected IAeExpressionRunnerContext getRunnerContext()
   {
      return mRunnerContext;
   }

   /**
    * @param aRunnerContext The runnerContext to set.
    */
   protected void setRunnerContext(IAeExpressionRunnerContext aRunnerContext)
   {
      mRunnerContext = aRunnerContext;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext#getAbstractBpelObject()
    */
   public AeAbstractBpelObject getAbstractBpelObject()
   {
      return getRunnerContext().getAbstractBpelObject();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext#getEvaluationContext()
    */
   public Object getEvaluationContext()
   {
      return getRunnerContext().getEvaluationContext();
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext#getFunctionContext()
    */
   public IAeFunctionFactory getFunctionContext()
   {
      return getRunnerContext().getFunctionContext();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext#getNamespaceContext()
    */
   public IAeNamespaceContext getNamespaceContext()
   {
      return getRunnerContext().getNamespaceContext();
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionExecutionContext#getFaultFactory()
    */
   public IAeFaultFactory getFaultFactory()
   {
      return getRunnerContext().getFaultFactory();
   }
   
   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionExecutionContext#getBpelNamespace()
    */
   public String getBpelNamespace()
   {
      return getFaultFactory().getNamespace();
   }
   
   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionExecutionContext#getTypeConverter()
    */
   public IAeExpressionTypeConverter getTypeConverter()
   {
      return getExpressionTypeConverter();
   }

   /**
    * @return Returns the expressionTypeConverter.
    */
   protected IAeExpressionTypeConverter getExpressionTypeConverter()
   {
      return mExpressionTypeConverter;
   }

   /**
    * @param aExpressionTypeConverter The expressionTypeConverter to set.
    */
   protected void setExpressionTypeConverter(IAeExpressionTypeConverter aExpressionTypeConverter)
   {
      mExpressionTypeConverter = aExpressionTypeConverter;
   }
}
