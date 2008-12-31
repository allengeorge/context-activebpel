// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/xpath/AeWSBPELXPathExpressionAnalyzer.java,v 1.5 2006/09/25 21:15:3
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

import org.activebpel.rt.bpel.def.expr.AePrefixedExpressionAnalyzer;
import org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParser;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;

/**
 * A concrete implementation of an expression analyzer for XPath 1.0 (for BPEL 2.0).  This 
 * class helps the Designer perform analysis and manipulation of expressions written in XPath 1.0
 * according to the BPEL 2.0 specification.
 */
public class AeWSBPELXPathExpressionAnalyzer extends AePrefixedExpressionAnalyzer
{
   /**
    * Overrides method to supply a BPEL 2.0 version of the expression parser.
    * 
    * @see org.activebpel.rt.bpel.def.expr.xpath.AeBPWSXPathExpressionAnalyzer#createExpressionParser(org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext)
    */
   protected IAeExpressionParser createExpressionParser(IAeExpressionParserContext aContext)
   {
      return new AeWSBPELXPathExpressionParser(aContext);
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
    * Renames variables within an expression.  This method renames variables with the syntactic form
    * of $varName within an XPath expression.
    * 
    * @param aContext
    * @param aExpression
    * @param aOldVarName
    * @param aNewVarName
    */
   public static String renameExpressionVariable(IAeExpressionAnalyzerContext aContext, String aExpression,
         String aOldVarName, String aNewVarName)
   {
      String expression = aExpression;

      // Note: $2 should refer to any character that could conceivably follow the variable reference
      // in an xpath.  Example syntax:  concat('Val:', $variableName)
      String pattern = "(\\$)" + aOldVarName + "([\\.[^" + AeXmlUtil.NCNAME_CHAR_PATTERN + "]])"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      String replacement = "$1" + aNewVarName + "$2"; //$NON-NLS-1$ //$NON-NLS-2$
      String newExpr = expression.replaceAll(pattern, replacement);
      
      // Note: need to check for $varName at the end of the expression because for some reason
      // the above regexp will not match on the end of the line (even using the $ construct)
      if (newExpr.endsWith("$" + aOldVarName)) //$NON-NLS-1$
      {
         newExpr = newExpr.substring(0, newExpr.length() - aOldVarName.length()) + aNewVarName;
      }
      
      return newExpr;
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#parseExpressionToSpec(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public AeExpressionToSpecDetails parseExpressionToSpec(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      return AeWSBPELXPathExpressionToSpecUtil.parseExpressionToSpec(aContext, aExpression);
   }
}
