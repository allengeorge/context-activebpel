//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/AeNoOpExpressionAnalyzer.java,v 1.5 2006/10/12 20:15:2
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
package org.activebpel.rt.bpel.def.expr;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Implements a no-op version of the expression analyzer.  This analyzer is typically used during 
 * development of other pieces of functionality needed for supporting a new expression language.
 */
public class AeNoOpExpressionAnalyzer implements IAeExpressionAnalyzer
{
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#getNamespaces(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public Set getNamespaces(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      return Collections.EMPTY_SET;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#getVarDataList(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public List getVarDataList(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      return Collections.EMPTY_LIST;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#getVariables(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public Set getVariables(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      return Collections.EMPTY_SET;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#getVarPropertyList(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public List getVarPropertyList(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      return Collections.EMPTY_LIST;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#getStylesheetURIs(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public Set getStylesheetURIs(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      return Collections.EMPTY_SET;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#renameNamespacePrefix(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String, java.lang.String, java.lang.String)
    */
   public String renameNamespacePrefix(IAeExpressionAnalyzerContext aContext, String aExpression, String aOldPrefix,
         String aNewPrefix)
   {
      return null;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#renameVariable(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String, java.lang.String, java.lang.String)
    */
   public String renameVariable(IAeExpressionAnalyzerContext aContext, String aExpression, String aOldVarName,
         String aNewVarName)
   {
      return null;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#parseExpressionToSpec(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public AeExpressionToSpecDetails parseExpressionToSpec(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      return null;
   }
}
