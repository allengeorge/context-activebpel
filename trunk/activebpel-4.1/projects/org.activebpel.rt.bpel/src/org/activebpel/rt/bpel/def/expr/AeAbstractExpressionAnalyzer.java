//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/AeAbstractExpressionAnalyzer.java,v 1.3 2006/10/12 20:15:2
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeUtil;

/**
 * This is a base class for any concrete, language specific implementation of an expression analyzer. Any
 * class that extends this must implement the <code>createExpressionParser</code> abstract method, which
 * will be used to in the implementation of the <code>getVarDataList</code>, <code>getVarPropertyList</code>, 
 * <code>renameVariable</code>, and <code>getVariables</code> methods. The other methods declared in the 
 * expression analyzer interface must, of course, also be implemented by extending classes.
 */
public abstract class AeAbstractExpressionAnalyzer implements IAeExpressionAnalyzer
{
   /**
    * Simple constructor.
    */
   public AeAbstractExpressionAnalyzer()
   {
   }

   /**
    * Creates an expression parser.
    * 
    * @param aContext
    */
   protected abstract IAeExpressionParser createExpressionParser(IAeExpressionParserContext aContext);

   /**
    * Creates an expression parser context from the analyzer context.
    * 
    * @param aContext
    */
   protected IAeExpressionParserContext createParserContext(IAeExpressionAnalyzerContext aContext)
   {
      return new AeExpressionParserContext(aContext.getNamespaceContext());
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#getVarDataList(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public List getVarDataList(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      if (AeUtil.isNullOrEmpty(aExpression))
         return Collections.EMPTY_LIST;

      try
      {
         IAeExpressionParserContext parserCtx = createParserContext(aContext);
         IAeExpressionParser parser = createExpressionParser(parserCtx);
         IAeExpressionParseResult parseResult = parser.parse(aExpression);
         return parseResult.getVarDataList();
      }
      catch (Exception e)
      {
         AeException.logError(e, e.getLocalizedMessage());
         return Collections.EMPTY_LIST;
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#getVarPropertyList(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public List getVarPropertyList(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      if (AeUtil.isNullOrEmpty(aExpression))
         return Collections.EMPTY_LIST;

      try
      {
         IAeExpressionParserContext parserCtx = createParserContext(aContext);
         IAeExpressionParser parser = createExpressionParser(parserCtx);
         IAeExpressionParseResult parseResult = parser.parse(aExpression);
         return parseResult.getVarPropertyList();
      }
      catch (Exception e)
      {
         AeException.logError(e, e.getLocalizedMessage());
         return Collections.EMPTY_LIST;
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#getStylesheetURIs(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public Set getStylesheetURIs(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      if (AeUtil.isNullOrEmpty(aExpression))
         return Collections.EMPTY_SET;

      try
      {
         IAeExpressionParserContext parserCtx = createParserContext(aContext);
         IAeExpressionParser parser = createExpressionParser(parserCtx);
         IAeExpressionParseResult parseResult = parser.parse(aExpression);
         List stylesheetURIs = parseResult.getStylesheetURIList();
         Set set = new HashSet();
         set.addAll(stylesheetURIs);
         return set;
      }
      catch (Exception e)
      {
         AeException.logError(e, e.getLocalizedMessage());
         return Collections.EMPTY_SET;
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#getVariables(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public Set getVariables(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      if (AeUtil.isNullOrEmpty(aExpression))
         return Collections.EMPTY_SET;

      try
      {
         IAeExpressionParserContext parserCtx = createParserContext(aContext);
         IAeExpressionParser parser = createExpressionParser(parserCtx);
         IAeExpressionParseResult parseResult = parser.parse(aExpression);
         return parseResult.getVarNames();
      }
      catch (Exception e)
      {
         AeException.logError(e, e.getLocalizedMessage());
         return Collections.EMPTY_SET;
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#renameVariable(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String, java.lang.String, java.lang.String)
    * 
    * todo I'm a little worried about unicode chars in var names here...
    */
   public String renameVariable(IAeExpressionAnalyzerContext aContext, String aExpression, String aOldVarName,
         String aNewVarName)
   {
      if (AeUtil.isNullOrEmpty(aExpression))
         return null;

      String pattern = "(getVariableData|getVariableProperty)([^'^\"]*)(['\"])" + aOldVarName + "(['\"])"; //$NON-NLS-1$ //$NON-NLS-2$
      String replacement = "$1$2$3" + aNewVarName + "$4"; //$NON-NLS-1$ //$NON-NLS-2$
      String newExpr = aExpression.replaceAll(pattern, replacement);
      if (aExpression.equals(newExpr))
      {
         return null;
      }
      else
      {
         return newExpr;
      }
   }
}
