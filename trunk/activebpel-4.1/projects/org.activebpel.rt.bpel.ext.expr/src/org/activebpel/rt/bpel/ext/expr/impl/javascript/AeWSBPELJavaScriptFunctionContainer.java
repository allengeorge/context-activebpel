// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/impl/javascript/AeWSBPELJavaScriptFunctionContainer.java,v 1.1 2006/09/18 20:08:5
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

import org.activebpel.rt.bpel.def.expr.AeExpressionLanguageUtil;
import org.activebpel.rt.bpel.function.AeUnresolvableException;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.function.AeGetLinkStatusFunction;
import org.activebpel.rt.bpel.impl.function.AeGetVariableDataFunction;
import org.mozilla.javascript.Scriptable;

/**
 * This is an extension to the basic JavaScript function container specifically for the WSBPEL 
 * namespace.  BPEL 2.0 removed getVariableData and getLinkStatus in favor of a shorter $varName
 * and $linkName syntax.  However, since BPEL variables cannot be manifested directly as 
 * JavaScript variable, the getVariableData and getLinkStatus functions are still used for
 * JavaScript.  This class makes these two functions explicitely available, even though they
 * don't exist in the standard BPEL 2.0 function namespace.
 */
public class AeWSBPELJavaScriptFunctionContainer extends AeJavaScriptFunctionContainer
{
   /**
    * Constructs the WSBPEL function container with the given parent scope.
    * 
    * @param aNamespace
    * @param aParentScope
    * @param aFunctionExecutionContext
    */
   public AeWSBPELJavaScriptFunctionContainer(String aNamespace, Scriptable aParentScope,
         IAeFunctionExecutionContext aFunctionExecutionContext)
   {
      super(aNamespace, aParentScope, aFunctionExecutionContext);
   }

   /**
    * @see org.activebpel.rt.bpel.ext.expr.impl.javascript.AeJavaScriptFunctionContainer#getClassName()
    */
   public String getClassName()
   {
      return AeWSBPELJavaScriptFunctionContainer.class.getName();
   }

   /**
    * @see org.activebpel.rt.bpel.ext.expr.impl.javascript.AeJavaScriptFunctionContainer#findFunction(java.lang.String)
    */
   protected IAeFunction findFunction(String aName) throws AeUnresolvableException
   {
      if (AeExpressionLanguageUtil.VAR_DATA_FUNC_NAME.equals(aName))
      {
         return new AeGetVariableDataFunction();
      }
      else if (AeExpressionLanguageUtil.LINK_STATUS_FUNC_NAME.equals(aName))
      {
         return new AeGetLinkStatusFunction();
      }
      else
      {
         return super.findFunction(aName);
      }
   }
}
