//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr.bsf/src/org/activebpel/rt/bpel/ext/expr/bsf/impl/AeBSFAbstractExtensionFunctionBean.java,v 1.2 2006/07/10 16:42:2
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
package org.activebpel.rt.bpel.ext.expr.bsf.impl;

import java.util.List;

import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;

/**
 * This is a base class for all of the BSF specific extension function beans.  Each extension
 * function bean provides a way to execute BPEL extension functions, such as bpws:getVariableData()
 * or custom extension functions such as myprfx:myFunction().  This class provides a set of
 * common methods needed by the concrete beans.
 */
public abstract class AeBSFAbstractExtensionFunctionBean
{
   /** The function execution context to use. */
   private IAeFunctionExecutionContext mFunctionExecutionContext;

   /**
    * Constructs the bean with the given function context.
    * 
    * @param aFunctionExecutionContext
    */
   public AeBSFAbstractExtensionFunctionBean(IAeFunctionExecutionContext aFunctionExecutionContext)
   {
      setFunctionExecutionContext(aFunctionExecutionContext);
   }

   /**
    * Returns the namespace used for the bean - must be implemented by children.
    */
   protected abstract String getNamespace();

   /**
    * Calls the given function.
    * 
    * @param aFunctionName
    * @param aArgs
    */
   protected Object callFunction(String aFunctionName, List aArgs)
   {
      String namespace = getNamespace();
      return callFunction(namespace, aFunctionName, aArgs);
   }
   
   /**
    * Calls the given function with the given arguments.  The function is identified by its
    * namespace, and name.
    * 
    * @param aNamespace
    * @param aFunctionName
    * @param aArgs
    */
   protected Object callFunction(String aNamespace, String aFunctionName, List aArgs)
   {
      try
      {
         IAeFunction function = getFunctionExecutionContext().getFunctionContext().getFunction(aNamespace, aFunctionName);
         Object rval = function.call(getFunctionExecutionContext(), aArgs);
         rval = getFunctionExecutionContext().getTypeConverter().convertToExpressionType(rval);
         return rval;
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
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
