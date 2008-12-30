// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/javascript/AeAbstractJavaScriptExpressionParser.java,v 1.2 2006/09/25 21:18:0
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
package org.activebpel.rt.bpel.ext.expr.def.javascript;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.expr.AeAbstractExpressionParser;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ScriptOrFnNode;

/**
 * Base class for JavaScript implementations of an Expression Parser.
 */
public abstract class AeAbstractJavaScriptExpressionParser extends AeAbstractExpressionParser
{
   /**
    * Constructs a javascript parser given the context.
    * 
    * @param aParserContext
    */
   public AeAbstractJavaScriptExpressionParser(IAeExpressionParserContext aParserContext)
   {
      super(aParserContext);
   }

   /**
    * This implementation uses Rhino to parse the expression into a parse tree.  The resulting parse
    * tree is then walked by the AeJavaScriptParseResult object.
    * 
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParser#parse(java.lang.String)
    */
   public IAeExpressionParseResult parse(String aExpression) throws AeException
   {
      try
      {
         Context ctx = Context.enter();
         ctx.setGeneratingDebug(true);
         ctx.setGeneratingSource(true);
         CompilerEnvirons compilerEnv = new CompilerEnvirons();
         compilerEnv.initFromContext(ctx);
         ErrorReporter compilationErrorReporter = compilerEnv.getErrorReporter();

         Parser p = new Parser(compilerEnv, compilationErrorReporter);
         ScriptOrFnNode tree = p.parse(aExpression, "<java>", 0); //$NON-NLS-1$
         return createParseResult(aExpression, tree);
      }
      catch (Exception e)
      {
         throw new AeException(e);
      }
      finally
      {
         Context.exit();
      }
   }

   /**
    * Creates the JavaScript parse result object using the given information.
    * 
    * @param aExpression
    * @param aTree
    */
   protected abstract IAeExpressionParseResult createParseResult(String aExpression, ScriptOrFnNode aTree);
}
