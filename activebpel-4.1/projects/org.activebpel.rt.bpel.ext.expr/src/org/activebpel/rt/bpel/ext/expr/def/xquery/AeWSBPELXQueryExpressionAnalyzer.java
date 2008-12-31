// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/xquery/AeWSBPELXQueryExpressionAnalyzer.java,v 1.4 2006/09/25 21:18:0
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

import org.activebpel.rt.bpel.def.expr.AePrefixedExpressionAnalyzer;
import org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParser;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.bpel.def.expr.xpath.AeWSBPELXPathExpressionAnalyzer;
import org.activebpel.rt.bpel.def.expr.xpath.AeWSBPELXPathExpressionToSpecUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * A WS-BPEL implementation of a XQuery expression analyser.
 */
public class AeWSBPELXQueryExpressionAnalyzer extends AePrefixedExpressionAnalyzer
{
   /**
    * Default c'tor.
    */
   public AeWSBPELXQueryExpressionAnalyzer()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.ext.expr.def.xquery.AeBPWSXQueryExpressionAnalyzer#createExpressionParser(org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext)
    */
   protected IAeExpressionParser createExpressionParser(IAeExpressionParserContext aContext)
   {
      return new AeWSBPELXQueryExpressionParser(aContext);
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.AeAbstractExpressionAnalyzer#renameVariable(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String, java.lang.String, java.lang.String)
    */
   public String renameVariable(IAeExpressionAnalyzerContext aContext, String aExpression, String aOldVarName, String aNewVarName)
   {
      if (AeUtil.isNullOrEmpty(aExpression))
         return null;
      
      String expression = super.renameVariable(aContext, aExpression, aOldVarName, aNewVarName);
      if (expression == null)
         expression = aExpression;
      
      // Use the same logic as the XPath impl for renaming $varName variables.
      String newExpr = AeWSBPELXPathExpressionAnalyzer.renameExpressionVariable(aContext, expression, aOldVarName, aNewVarName);
      
      if (aExpression.equals(newExpr))
      {
         return null;
      }
      else
      {
         return newExpr;
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#parseExpressionToSpec(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public AeExpressionToSpecDetails parseExpressionToSpec(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      return AeWSBPELXPathExpressionToSpecUtil.parseExpressionToSpec(aContext, aExpression);
   }
}
