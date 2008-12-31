// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/xpath/ast/AeXPathAST.java,v 1.4 2006/09/27 19:58:4
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.expr.xpath.AeXPathParseHandler;
import org.activebpel.rt.bpel.xpath.ast.visitors.AeXPathDebugSerializeNodeVisitor;
import org.activebpel.rt.xml.IAeNamespaceContext;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.saxpath.XPathReader;
import org.jaxen.saxpath.helpers.XPathReaderFactory;

/**
 * A class that wraps the XPath abstract syntax tree.  The AST is basically a tree of AeXPathNode
 * instances.  This class provides some convenient access to that tree.
 */
public class AeXPathAST
{
   /** The root of the AST. */
   private AeAbstractXPathNode mRootNode;

   /**
    * Simple constructor.
    *
    * @param aRootNode
    */
   public AeXPathAST(AeAbstractXPathNode aRootNode)
   {
      setRootNode(aRootNode);
      getRootNode().setParent(null);

      normalize();
   }

   /**
    * Called internally to normalize the AST.
    */
   protected void normalize()
   {
      setRootNode(getRootNode().normalize());
   }

   /**
    * Visits the root node of the AST using the given node visitor.
    * 
    * @param aNodeVisitor
    */
   public void visit(IAeXPathNodeVisitor aNodeVisitor)
   {
      getRootNode().accept(aNodeVisitor);
   }
   
   /**
    * Use the visitor to visit all the nodes in the AST.
    *
    * @param aNodeVisitor
    */
   public void visitAll(IAeXPathNodeVisitor aNodeVisitor)
   {
      AeXPathTreeTraverser traverser = new AeXPathTreeTraverser(aNodeVisitor);
      traverser.traverse(getRootNode());
   }

   /**
    * @return Returns the rootNode.
    */
   public AeAbstractXPathNode getRootNode()
   {
      return mRootNode;
   }

   /**
    * @param aRootNode The rootNode to set.
    */
   protected void setRootNode(AeAbstractXPathNode aRootNode)
   {
      mRootNode = aRootNode;
   }

   /**
    * Returns a String representation of this AST for debug purposes.
    */
   public String toDebugString()
   {
      return AeXPathAST.toDebugString(getRootNode());
   }

   /**
    * Returns a String representation of the given node for debug purposes.
    *
    * @param aNode
    */
   public static String toDebugString(AeAbstractXPathNode aNode)
   {
      AeXPathDebugSerializeNodeVisitor visitor = new AeXPathDebugSerializeNodeVisitor();
      aNode.accept(visitor);
      return visitor.getString();
   }

   /**
    * Creates a XPath AST for a given XPath.
    * 
    * @param aExpression
    */
   public static AeXPathAST createXPathAST(String aExpression, IAeNamespaceContext aNamespaceContext)
         throws AeException
   {
      try
      {
         AeXPathParseHandler handler = new AeXPathParseHandler(aNamespaceContext);

         XPathReader reader = XPathReaderFactory.createReader();
         reader.setXPathHandler(handler);
         reader.parse(aExpression);
         
         return handler.getAST();
      }
      catch (SAXPathException ex)
      {
         throw new AeException(ex);
      }
      catch (NullPointerException npe)
      {
         throw new AeException(npe);
      }
   }
}
