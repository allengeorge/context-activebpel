// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/xpath/ast/AeXPathTreeTraverser.java,v 1.1 2006/07/21 16:03:3
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

import java.util.Iterator;

/**
 * Used to traverse the XPath AST.
 */
public class AeXPathTreeTraverser
{
   /** The xpath node visitor. */
   private IAeXPathNodeVisitor mVisitor;

   /**
    * Simple constructor.
    * 
    * @param aVisitor
    */
   public AeXPathTreeTraverser(IAeXPathNodeVisitor aVisitor)
   {
      setVisitor(aVisitor);
   }

   /**
    * Traverse the entire tree using the given visitor.
    * 
    * @param aXPathNode
    */
   public void traverse(AeAbstractXPathNode aXPathNode)
   {
      // Traverse the given node.
      aXPathNode.accept(getVisitor());

      // Traverse all of that node's children.
      for (Iterator iter = aXPathNode.getChildren().iterator(); iter.hasNext(); )
      {
         AeAbstractXPathNode child = (AeAbstractXPathNode) iter.next();
         traverse(child);
      }
   }

   /**
    * @return Returns the visitor.
    */
   protected IAeXPathNodeVisitor getVisitor()
   {
      return mVisitor;
   }

   /**
    * @param aVisitor The visitor to set.
    */
   protected void setVisitor(IAeXPathNodeVisitor aVisitor)
   {
      mVisitor = aVisitor;
   }
}
