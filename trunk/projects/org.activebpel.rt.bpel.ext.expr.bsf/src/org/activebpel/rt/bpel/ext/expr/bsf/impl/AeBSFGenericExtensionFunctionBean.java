//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr.bsf/src/org/activebpel/rt/bpel/ext/expr/bsf/impl/AeBSFGenericExtensionFunctionBean.java,v 1.2 2006/07/10 16:42:2
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

import java.util.ArrayList;

import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.util.AeXmlUtil;

/**
 * This is a class that allows BSF compatible scripting languages access to the installed extension
 * functions.  Scripting languages will have access to a global variable called "bpel".  This 
 * class implements the functionality for that global variable.  Note that, for convenience, 
 * we also provide separate access to the bpel specific extension functions (in other words
 * the bpws:* functions).  This class provides several versions of a "call" method that can
 * be used to execute any extension function (including custom extension functions).  The
 * symantics are simple.<br>
 * <br>
 * <em>For example:</em><br>
 * <pre>
 * bpel.call('bpws:getVariableData', 'requestVar', '/requestVar/node1')
 * bpel.call('prfx:myFunction', 'arg1', 'arg2')
 * </pre>
 * <br>
 * This mechanism currently supports any function with six or fewer arguments.
 */
public class AeBSFGenericExtensionFunctionBean extends AeBSFAbstractExtensionFunctionBean
{
   /**
    * Constructs a BSF bpel context given the expression runtime context.
    * 
    * @param aFunctionExecutionContext
    */
   protected AeBSFGenericExtensionFunctionBean(IAeFunctionExecutionContext aFunctionExecutionContext)
   {
      super(aFunctionExecutionContext);
   }

   /**
    * @see org.activebpel.rt.bpel.ext.expr.bsf.impl.AeBSFAbstractExtensionFunctionBean#getNamespace()
    */
   protected String getNamespace()
   {
      // Return null - this bean handles multiple namespaces/prefixes.
      return null;
   }

   /**
    * Calls the given 0 argument function spec.
    * 
    * @param aFunctionSpec
    * @throws Exception
    */
   public Object call(String aFunctionSpec) throws Exception
   {
      return callWithArgs(aFunctionSpec, new Object[] {});
   }

   /**
    * Calls the given 1 argument function spec.
    * 
    * @param aFunctionSpec
    * @param aArg1
    * @throws Exception
    */
   public Object call(String aFunctionSpec, Object aArg1) throws Exception
   {
      return callWithArgs(aFunctionSpec, new Object[] { aArg1 });
   }

   /**
    * Calls the given 2 argument function spec.
    * 
    * @param aFunctionSpec
    * @param aArg1
    * @param aArg2
    * @throws Exception
    */
   public Object call(String aFunctionSpec, Object aArg1, Object aArg2) throws Exception
   {
      return callWithArgs(aFunctionSpec, new Object[] { aArg1, aArg2 });
   }

   /**
    * Calls the given 3 argument function spec.
    * 
    * @param aFunctionSpec
    * @param aArg1
    * @param aArg2
    * @param aArg3
    * @throws Exception
    */
   public Object call(String aFunctionSpec, Object aArg1, Object aArg2, Object aArg3) throws Exception
   {
      return callWithArgs(aFunctionSpec, new Object[] { aArg1, aArg2, aArg3 });
   }

   /**
    * Calls the given 4 argument function spec.
    * 
    * @param aFunctionSpec
    * @param aArg1
    * @param aArg2
    * @param aArg3
    * @param aArg4
    * @throws Exception
    */
   public Object call(String aFunctionSpec, Object aArg1, Object aArg2, Object aArg3, Object aArg4) throws Exception
   {
      return callWithArgs(aFunctionSpec, new Object[] { aArg1, aArg2, aArg3, aArg4 });
   }

   /**
    * Calls the given 5 argument function spec.
    * 
    * @param aFunctionSpec
    * @param aArg1
    * @param aArg2
    * @param aArg3
    * @param aArg4
    * @param aArg5
    * @throws Exception
    */
   public Object call(String aFunctionSpec, Object aArg1, Object aArg2, Object aArg3, Object aArg4,
         Object aArg5) throws Exception
   {
      return callWithArgs(aFunctionSpec, new Object[] { aArg1, aArg2, aArg3, aArg4, aArg5 });
   }

   /**
    * Calls the given 6 argument function spec.
    * 
    * @param aFunctionSpec
    * @param aArg1
    * @param aArg2
    * @param aArg3
    * @param aArg4
    * @param aArg5
    * @param aArg6
    * @throws Exception
    */
   public Object call(String aFunctionSpec, Object aArg1, Object aArg2, Object aArg3, Object aArg4,
         Object aArg5, Object aArg6) throws Exception
   {
      return callWithArgs(aFunctionSpec, new Object[] { aArg1, aArg2, aArg3, aArg4, aArg5, aArg6 });
   }

   /**
    * Does the actual work of looking up the function and calling it.
    * 
    * @param aFunctionSpec
    * @throws Exception
    */
   protected Object callWithArgs(String aFunctionSpec, Object [] aArgs) throws Exception
   {
      String prefix = AeXmlUtil.extractPrefix(aFunctionSpec);
      String funcName = AeXmlUtil.extractLocalPart(aFunctionSpec);
      String ns = getFunctionExecutionContext().getNamespaceContext().resolvePrefixToNamespace(prefix);

      // Create the argument list.
      ArrayList list = new ArrayList();
      for (int i = 0; i < aArgs.length; i++)
      {
         list.add(aArgs[i]);
      }

      return callFunction(ns, funcName, list);
   }
}
