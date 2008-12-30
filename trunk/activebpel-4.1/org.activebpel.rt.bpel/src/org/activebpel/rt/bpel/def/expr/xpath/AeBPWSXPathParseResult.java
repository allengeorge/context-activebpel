//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/xpath/AeBPWSXPathParseResult.java,v 1.1 2006/09/15 14:49:5
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

import java.util.List;

import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.bpel.xpath.ast.AeXPathAST;

/**
 * A concrete implementation of a parse result for the xpath language for BPEL 1.1 processes.
 */
public class AeBPWSXPathParseResult extends AeAbstractXPathParseResult
{
   /**
    * Creates the xpath parse result.
    * 
    * @param aExpression
    * @param aXPathAST
    * @param aErrors
    * @param aParserContext
    */
   public AeBPWSXPathParseResult(String aExpression, AeXPathAST aXPathAST, List aErrors,
         IAeExpressionParserContext aParserContext)
   {
      super(aExpression, aXPathAST, aErrors, aParserContext);
   }
}
