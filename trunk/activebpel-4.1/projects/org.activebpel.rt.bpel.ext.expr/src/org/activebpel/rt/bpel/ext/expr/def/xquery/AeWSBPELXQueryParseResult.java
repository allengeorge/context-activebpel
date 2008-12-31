// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/xquery/AeWSBPELXQueryParseResult.java,v 1.2 2006/09/15 14:53:3
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
package org.activebpel.rt.bpel.ext.expr.def.xquery;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.sf.saxon.Configuration;
import net.sf.saxon.expr.Expression;

import org.activebpel.rt.bpel.def.expr.AeScriptVarDef;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.bpel.def.expr.xpath.AeXPathVariableReference;
import org.activebpel.rt.bpel.def.util.AeVariableData;
import org.activebpel.rt.util.AeUtil;

/**
 * A WS-BPEL version of a XQuery parse result.
 */
public class AeWSBPELXQueryParseResult extends AeAbstractXQueryParseResult
{
   /**
    * Constructs a WS-BPEL XQuery Parse Result.
    * 
    * @param aExpression
    * @param aXQueryExpression
    * @param aConfiguration
    * @param aParserContext
    */
   public AeWSBPELXQueryParseResult(String aExpression, Expression aXQueryExpression, Configuration aConfiguration,
         IAeExpressionParserContext aParserContext)
   {
      super(aExpression, aXQueryExpression, aConfiguration, aParserContext);
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.AeAbstractExpressionParseResult#getVarDataList()
    */
   public List getVarDataList()
   {
      List varData = super.getVarDataList();
      varData.addAll(getVarDataFromXQueryVariables());
      return varData;
   }

   /**
    * Gets a list of AeVariableData objects built from the 
    */
   protected Collection getVarDataFromXQueryVariables()
   {
      List list = new LinkedList();
      for (Iterator iter = getVariableReferences().iterator(); iter.hasNext(); )
      {
         AeScriptVarDef varDef = (AeScriptVarDef) iter.next();
         // BPEL 2.0 variables are referenced using an unqualified XPath 1.0 variable reference.
         if (AeUtil.isNullOrEmpty(varDef.getNamespace()))
         {
            AeXPathVariableReference varRef = new AeXPathVariableReference(varDef.getName());
            // Note that, at the moment, the query portion of the var def will be null for any
            // $varName syntax based XQuery var reference.
            list.add(new AeVariableData(varRef.getVariableName(), varRef.getPartName(), varDef.getQuery()));
         }
      }
      return list;
   }
}
