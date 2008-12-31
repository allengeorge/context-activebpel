// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/expr/xpath/AeAbstractXPathExpressionRunner.java,v 1.1 2006/09/15 14:49:5
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
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionRunner;
import org.activebpel.rt.bpel.impl.expr.AeExprFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.expr.AeExpressionException;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionTypeConverter;
import org.activebpel.rt.bpel.xpath.AeXPathHelper;
import org.jaxen.FunctionContext;
import org.jaxen.NamespaceContext;
import org.jaxen.VariableContext;

/**
 * A base class for implementations of xpath expression runners.
 */
public abstract class AeAbstractXPathExpressionRunner extends AeAbstractExpressionRunner
{
   /**
    * Default constructor.
    */
   public AeAbstractXPathExpressionRunner()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionRunner#doExecuteExpression(java.lang.String, org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext)
    */
   protected Object doExecuteExpression(String aExpression, IAeExpressionRunnerContext aContext)
         throws AeBpelException
   {
      try
      {
         IAeExpressionTypeConverter typeConverter = createExpressionTypeConverter(aContext);
         IAeFunctionExecutionContext funcExecContext = new AeExprFunctionExecutionContext(aContext, typeConverter);
         
         NamespaceContext xpathNSContext = new AeXPathNamespaceContext(aContext.getNamespaceContext());
         FunctionContext xpathFuncContext = new AeXPathFunctionContext(funcExecContext);
         VariableContext xpathVarContext = createVariableContext(funcExecContext);

         AeXPathHelper xpathHelper = AeXPathHelper.getInstance(aContext.getBpelNamespace());
         return xpathHelper.executeXPathExpression(aExpression, aContext.getEvaluationContext(),
               xpathFuncContext, xpathVarContext, xpathNSContext);
      }
      catch (AeBpelException ex)
      {
         throw ex;
      }
      catch (AeExpressionException ee)
      {
         throw ee.getWrappedException();
      }
      catch (Throwable t)
      {
         throwSubLangExecutionFault(aExpression, t, aContext);
         return null; // Will never get here - the above call will always throw.
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionRunner#doExecuteJoinConditionExpression(java.lang.String, org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext)
    */
   protected Object doExecuteJoinConditionExpression(String aExpression, IAeExpressionRunnerContext aContext) throws AeBpelException
   {
      try
      {
         IAeExpressionTypeConverter typeConverter = createExpressionTypeConverter(aContext);
         IAeFunctionExecutionContext funcExecContext = new AeExprFunctionExecutionContext(aContext, typeConverter);
         
         NamespaceContext xpathNSContext = new AeXPathNamespaceContext(aContext.getNamespaceContext());
         FunctionContext xpathFuncContext = new AeXPathFunctionContext(funcExecContext);
         VariableContext xpathVarContext = createJoinConditionVariableContext(funcExecContext);

         AeXPathHelper xpathHelper = AeXPathHelper.getInstance(aContext.getBpelNamespace());
         return xpathHelper.executeXPathExpression(aExpression, aContext.getEvaluationContext(),
               xpathFuncContext, xpathVarContext, xpathNSContext);
      }
      catch (AeBpelException ex)
      {
         throw ex;
      }
      catch (AeExpressionException ee)
      {
         throw ee.getWrappedException();
      }
      catch (Throwable t)
      {
         throwSubLangExecutionFault(aExpression, t, aContext);
         return null; // Will never get here - the above call will always throw.
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionRunner#createExpressionTypeConverter(org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext)
    */
   protected IAeExpressionTypeConverter createExpressionTypeConverter(IAeExpressionRunnerContext aContext)
   {
      AeXPathHelper xpathHelper = AeXPathHelper.getInstance(aContext.getBpelNamespace());
      return new AeXPathExpressionTypeConverter(xpathHelper);
   }

   /**
    * Base class does not supply a variable context (BPEL4WS version).
    * 
    * @param aContext
    */
   protected abstract VariableContext createVariableContext(IAeFunctionExecutionContext aContext);

   /**
    * Base class does not supply a variable context (BPEL4WS version).
    * 
    * @param aContext
    */
   protected abstract VariableContext createJoinConditionVariableContext(IAeFunctionExecutionContext aContext);
}
