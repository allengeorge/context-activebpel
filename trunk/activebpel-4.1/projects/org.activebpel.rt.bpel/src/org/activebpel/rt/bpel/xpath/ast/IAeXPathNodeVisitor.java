// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/xpath/ast/IAeXPathNodeVisitor.java,v 1.1 2006/07/21 16:03:3
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
package org.activebpel.rt.bpel.xpath.ast;

/**
 * This interface is implemented by visitors of the XPath AST.  Each node in the AST will be
 * visited by the visitor.
 */
public interface IAeXPathNodeVisitor
{
   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathFunctionNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathVariableNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathLiteralNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathAbsLocPathNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathAdditiveExprNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathAllNodeStepNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathAndExprNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathCommentNodeStepNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathEqualityExprNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathFilterExprNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathMultiplicativeExprNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathNameStepNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathOrExprNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathPathExprNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathPredicateNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathProcessingInstructionNodeStepNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathRelationalExprNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathRelativeLocPathNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathRootXpathNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathTextNodeStepNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathUnaryExprNode aNode);

   /**
    * Visit the given xpath AST node.
    *
    * @param aNode
    */
   public void visit(AeXPathUnionExprNode aNode);
}
