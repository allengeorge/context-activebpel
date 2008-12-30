// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/xpath/AeAbstractXPathParseResult.java,v 1.1 2006/09/15 14:49:5
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
import java.util.Set;

import org.activebpel.rt.bpel.def.expr.AeAbstractExpressionParseResult;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.bpel.def.expr.xpath.ast.visitors.AeXPathFunctionNodeVisitor;
import org.activebpel.rt.bpel.def.expr.xpath.ast.visitors.AeXPathVariableNodeVisitor;
import org.activebpel.rt.bpel.xpath.ast.AeXPathAST;

/**
 * A base implementation for XPath parse results.
 */
public abstract class AeAbstractXPathParseResult extends AeAbstractExpressionParseResult
{
   /** The AST representation of the parsed XPath. */
   private AeXPathAST mXPathAST;
   /** Cached list of functions. */
   private Set mFunctions;
   /** Cached list of variables. */
   private Set mVariableReferences;

   /**
    * Creates the xpath parse result.
    * 
    * @param aExpression
    * @param aXPathAST
    * @param aErrors
    * @param aParserContext
    */
   public AeAbstractXPathParseResult(String aExpression, AeXPathAST aXPathAST, List aErrors,
         IAeExpressionParserContext aParserContext)
   {
      super(aExpression, aParserContext);
      setXPathAST(aXPathAST);
      setErrors(aErrors);
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getFunctions()
    */
   public Set getFunctions()
   {
      if (mFunctions == null)
         setFunctions(findFunctions());
      return mFunctions;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getVariableReferences()
    */
   public Set getVariableReferences()
   {
      if (mVariableReferences == null)
         setVariableReferences(findVariableReferences());
      return mVariableReferences;
   }
   
   /**
    * Visits the xpath AST in order to find the functions.
    */
   protected Set findFunctions()
   {
      AeXPathFunctionNodeVisitor visitor = new AeXPathFunctionNodeVisitor();
      getXPathAST().visitAll(visitor);
      return visitor.getFunctions();
   }
   
   /**
    * Visits the xpath AST in order to find the variable references.
    */
   protected Set findVariableReferences()
   {
      AeXPathVariableNodeVisitor visitor = new AeXPathVariableNodeVisitor();
      getXPathAST().visitAll(visitor);
      return visitor.getVariableReferences();
   }

   /**
    * @return Returns the xPathAST.
    */
   public AeXPathAST getXPathAST()
   {
      return mXPathAST;
   }

   /**
    * @param aPathAST The xPathAST to set.
    */
   protected void setXPathAST(AeXPathAST aPathAST)
   {
      mXPathAST = aPathAST;
   }

   /**
    * @param aVariableReferences The variableReferences to set.
    */
   protected void setVariableReferences(Set aVariableReferences)
   {
      mVariableReferences = aVariableReferences;
   }

   /**
    * @param aFunctions The functions to set.
    */
   protected void setFunctions(Set aFunctions)
   {
      mFunctions = aFunctions;
   }
}
