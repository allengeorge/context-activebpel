//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/xpath/AeBPWSXPathExpressionParser.java,v 1.1 2006/09/15 14:49:5
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

import org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;

/**
 * An implementation of an expression parser for the xpath language.
 */
public class AeBPWSXPathExpressionParser extends AeAbstractXPathExpressionParser
{
   /**
    * Constructs an xpath parser given the context.
    * 
    * @param aParserContext
    */
   public AeBPWSXPathExpressionParser(IAeExpressionParserContext aParserContext)
   {
      super(aParserContext);
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.xpath.AeAbstractXPathExpressionParser#createParseResult(java.lang.String, org.activebpel.rt.bpel.def.expr.xpath.AeXPathParseHandler)
    */
   protected IAeExpressionParseResult createParseResult(String aExpression, AeXPathParseHandler aHandler)
   {
      return new AeBPWSXPathParseResult(aExpression, aHandler.getAST(), aHandler.getErrors(), getParserContext());
   }
}
