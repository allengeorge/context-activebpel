// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/xpath/AeWSBPELXPathParseResult.java,v 1.2 2006/09/15 14:49:5
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
package org.activebpel.rt.bpel.def.expr.xpath;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.activebpel.rt.bpel.def.expr.AeScriptVarDef;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.bpel.def.util.AeVariableData;
import org.activebpel.rt.bpel.xpath.ast.AeXPathAST;
import org.activebpel.rt.util.AeUtil;

/**
 * A concrete implementation of a parse result for the xpath language (for BPEL 2.0).
 */
public class AeWSBPELXPathParseResult extends AeAbstractXPathParseResult
{
   /**
    * Creates the xpath parse result.
    * 
    * @param aExpression
    * @param aXPathAST
    * @param aErrors
    * @param aParserContext
    */
   public AeWSBPELXPathParseResult(String aExpression, AeXPathAST aXPathAST, List aErrors, IAeExpressionParserContext aParserContext)
   {
      super(aExpression, aXPathAST, aErrors, aParserContext);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.AeAbstractExpressionParseResult#getVarDataList()
    */
   public List getVarDataList()
   {
      List varData = super.getVarDataList();
      varData.addAll(getVarDataFromXPathVariables());
      return varData;
   }

   /**
    * Gets a list of AeVariableData objects built from the 
    */
   protected Collection getVarDataFromXPathVariables()
   {
      List list = new LinkedList();
      for (Iterator iter = getVariableReferences().iterator(); iter.hasNext(); )
      {
         AeScriptVarDef varDef = (AeScriptVarDef) iter.next();
         // BPEL 2.0 variables are referenced using an unqualified XPath 1.0 variable reference.
         if (AeUtil.isNullOrEmpty(varDef.getNamespace()))
         {
            AeXPathVariableReference varRef = new AeXPathVariableReference(varDef.getName());
            list.add(new AeVariableData(varRef.getVariableName(), varRef.getPartName(), varDef.getQuery()));
         }
      }
      return list;
   }
}
