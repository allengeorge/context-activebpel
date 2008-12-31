// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/xpath/ast/visitors/AeAbstractTraversingXPathNodeVisitor.java,v 1.1 2006/07/21 16:03:3
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
package org.activebpel.rt.bpel.xpath.ast.visitors;

import java.util.Iterator;
import java.util.Stack;

import org.activebpel.rt.bpel.xpath.ast.AeAbstractXPathNode;

/**
 * This class is a simple traversing xpath node visitor.  It simply adds some convenience 
 * methods to aid in traversing the nodes.
 */
public abstract class AeAbstractTraversingXPathNodeVisitor extends AeAbstractXPathNodeVisitor
{
   /** The node stac. */
   private Stack mNodeStack;

   /**
    * Default c'tor.
    */
   protected AeAbstractTraversingXPathNodeVisitor()
   {
      super();
      
      setNodeStack(new Stack());
   }

   /**
    * @return Returns the nodeStack.
    */
   protected Stack getNodeStack()
   {
      return mNodeStack;
   }

   /**
    * @param aNodeStack The nodeStack to set.
    */
   protected void setNodeStack(Stack aNodeStack)
   {
      mNodeStack = aNodeStack;
   }

   /**
    * Pushes a node onto the stack.
    * 
    * @param aNode
    */
   protected void pushNode(AeAbstractXPathNode aNode)
   {
      getNodeStack().push(aNode);
   }

   /**
    * Pops a node off the stack.
    */
   protected void popNode()
   {
      getNodeStack().pop();
   }

   /**
    * Traverse the node's children.
    * 
    * @param aNode
    */
   protected void traverse(AeAbstractXPathNode aNode)
   {
      pushNode(aNode);
      for (Iterator iter = aNode.getChildren().iterator(); iter.hasNext(); )
      {
         AeAbstractXPathNode child = (AeAbstractXPathNode) iter.next();
         child.accept(this);
      }
      popNode();
   }

   /**
    * @see org.activebpel.rt.bpel.xpath.ast.visitors.AeAbstractXPathNodeVisitor#visitBaseXPathNode(org.activebpel.rt.bpel.xpath.ast.AeAbstractXPathNode)
    */
   protected void visitBaseXPathNode(AeAbstractXPathNode aNode)
   {
      traverse(aNode);
   }
}
