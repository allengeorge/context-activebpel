//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/AeAbstractExpressionParser.java,v 1.1 2005/06/08 12:50:2
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

/**
 * An abstract expression parser that language impls can extend.
 */
public abstract class AeAbstractExpressionParser implements IAeExpressionParser
{
   /** The context to use for parsing. */
   private IAeExpressionParserContext mParserContext;

   /**
    * Constructor.
    * 
    * @param aParserContext
    */
   public AeAbstractExpressionParser(IAeExpressionParserContext aParserContext)
   {
      setParserContext(aParserContext);
   }


   /**
    * @return Returns the parserContext.
    */
   protected IAeExpressionParserContext getParserContext()
   {
      return mParserContext;
   }

   /**
    * @param aParserContext The parserContext to set.
    */
   protected void setParserContext(IAeExpressionParserContext aParserContext)
   {
      mParserContext = aParserContext;
   }
}
