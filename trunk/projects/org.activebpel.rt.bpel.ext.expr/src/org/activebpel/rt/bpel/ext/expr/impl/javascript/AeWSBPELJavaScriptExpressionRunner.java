// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/impl/javascript/AeWSBPELJavaScriptExpressionRunner.java,v 1.2 2006/09/18 20:08:5
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
package org.activebpel.rt.bpel.ext.expr.impl.javascript;

import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.mozilla.javascript.Scriptable;

/**
 * A BPEL 2.0 implementation of a JavaScript expression runner.
 */
public class AeWSBPELJavaScriptExpressionRunner extends AeAbstractJavaScriptExpressionRunner
{
   /**
    * Default c'tor.
    */
   public AeWSBPELJavaScriptExpressionRunner()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.ext.expr.impl.javascript.AeAbstractJavaScriptExpressionRunner#createFunctionContainer(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, org.mozilla.javascript.Scriptable, java.lang.String)
    */
   protected AeJavaScriptFunctionContainer createFunctionContainer(
         IAeFunctionExecutionContext aFuncExecContext, Scriptable aScope, String aFunctionNamespace)
   {
      if (!IAeBPELConstants.BPWS_NAMESPACE_URI.equals(aFunctionNamespace))
      {
         return new AeWSBPELJavaScriptFunctionContainer(aFunctionNamespace, aScope, aFuncExecContext);
      }
      else
      {
         return super.createFunctionContainer(aFuncExecContext, aScope, aFunctionNamespace);
      }
   }
}
