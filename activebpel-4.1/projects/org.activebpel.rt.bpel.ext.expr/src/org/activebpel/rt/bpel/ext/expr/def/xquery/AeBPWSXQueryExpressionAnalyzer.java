//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/xquery/AeBPWSXQueryExpressionAnalyzer.java,v 1.2 2006/09/20 17:07:2
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

/**
 * A concrete implementation of the expression util interface for XQuery 1.0.  This class helps the 
 * Designer perform analysis and manipulation of expressions written in XQuery 1.0.
 */
public class AeBPWSXQueryExpressionAnalyzer extends AePrefixedExpressionAnalyzer
{
   /**
    * Default c'tor.
    */
   public AeBPWSXQueryExpressionAnalyzer()
   {
      super();
   }

   /**
    * Overrides method to supply an xquery impl for the expression parser.
    * 
    * @see org.activebpel.rt.bpel.def.expr.AeAbstractExpressionAnalyzer#createExpressionParser(org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext)
    */
   protected IAeExpressionParser createExpressionParser(IAeExpressionParserContext aContext)
   {
      return new AeBPWSXQueryExpressionParser(aContext);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#parseExpressionToSpec(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public AeExpressionToSpecDetails parseExpressionToSpec(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      throw new UnsupportedOperationException();
   }
}
