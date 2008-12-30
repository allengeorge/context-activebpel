// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/javascript/AeWSBPELJavaScriptParseResult.java,v 1.3 2006/09/25 21:18:0
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
package org.activebpel.rt.bpel.ext.expr.def.javascript;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.expr.AeExpressionLanguageUtil;
import org.activebpel.rt.bpel.def.expr.AeScriptFuncDef;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.mozilla.javascript.ScriptOrFnNode;

/**
 * A concrete BPEL 2.0 implementation of a parse result for the javascript language.
 */
public class AeWSBPELJavaScriptParseResult extends AeAbstractJavaScriptParseResult
{
   /** The getVariableData QName (for BPEL 2.0 JavaScript expressions). */
   public static final QName GET_VARIABLE_DATA_FUNC_NAME = new QName(
         IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI, AeExpressionLanguageUtil.VAR_DATA_FUNC_NAME);
   /** The getLinkStatus QName (for BPEL 2.0 JavaScript expressions). */
   private static final QName GET_LINK_STATUS_FUNC_NAME = new QName(
         IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI, AeExpressionLanguageUtil.LINK_STATUS_FUNC_NAME);

   /**
    * Constructor.
    * 
    * @param aExpression
    * @param aRootNode
    * @param aParserContext
    */
   public AeWSBPELJavaScriptParseResult(String aExpression, ScriptOrFnNode aRootNode,
         IAeExpressionParserContext aParserContext)
   {
      super(aExpression, aRootNode, aParserContext);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.AeAbstractExpressionParseResult#isGetLinkStatusFunction(org.activebpel.rt.bpel.def.expr.AeScriptFuncDef)
    */
   protected boolean isGetLinkStatusFunction(AeScriptFuncDef aFunction)
   {
      return GET_LINK_STATUS_FUNC_NAME.equals(aFunction.getQName()) || super.isGetLinkStatusFunction(aFunction);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.AeAbstractExpressionParseResult#isGetVariableDataFunction(org.activebpel.rt.bpel.def.expr.AeScriptFuncDef)
    */
   protected boolean isGetVariableDataFunction(AeScriptFuncDef aFunction)
   {
      return GET_VARIABLE_DATA_FUNC_NAME.equals(aFunction.getQName()) || super.isGetVariableDataFunction(aFunction);
   }
}
