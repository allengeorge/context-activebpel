// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/xpath/AeAbstractXPathExpressionParser.java,v 1.1 2006/09/15 14:49:5
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.expr.AeAbstractExpressionParser;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.jaxen.saxpath.XPathReader;
import org.jaxen.saxpath.helpers.XPathReaderFactory;

/**
 * A base implementation of an XPath expression parser.
 */
public abstract class AeAbstractXPathExpressionParser extends AeAbstractExpressionParser
{
   /**
    * Constructs an xpath parser given the context.
    * 
    * @param aParserContext
    */
   public AeAbstractXPathExpressionParser(IAeExpressionParserContext aParserContext)
   {
      super(aParserContext);
   }

   /**
    * Uses Jaxen to parse the XPath expression.  This implementation installs a custom Jaxen XPath 
    * parser event handler which will build a parse tree of the expression.  This parse tree is then
    * walked by the AeXPathParseResult object.
    * 
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParser#parse(java.lang.String)
    */
   public IAeExpressionParseResult parse(String aExpression) throws AeException
   {
      AeXPathParseHandler handler = new AeXPathParseHandler(getParserContext().getNamespaceContext());
      try
      {
         // parse the passed xpath and validate through our handler implementation
         XPathReader reader = XPathReaderFactory.createReader();
         reader.setXPathHandler(handler);
         reader.parse(aExpression);

         return createParseResult(aExpression, handler);
      }
      catch (Exception e)
      {
         throw new AeException(e.getMessage(), e);
      }
   }

   /**
    * Creates the parse result object from the expression and xpath parse handler.
    * 
    * @param aExpression
    * @param aHandler
    */
   protected abstract IAeExpressionParseResult createParseResult(String aExpression, AeXPathParseHandler aHandler);
}
