// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/xpath/ast/visitors/AeXPathVariableNodeVisitor.java,v 1.1 2006/07/21 16:03:3
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
package org.activebpel.rt.bpel.def.expr.xpath.ast.visitors;

import java.util.LinkedHashSet;
import java.util.Set;

import org.activebpel.rt.bpel.def.expr.AeScriptVarDef;
import org.activebpel.rt.bpel.xpath.ast.AeAbstractXPathNode;
import org.activebpel.rt.bpel.xpath.ast.AeXPathPathExprNode;
import org.activebpel.rt.bpel.xpath.ast.AeXPathVariableNode;
import org.activebpel.rt.bpel.xpath.ast.visitors.AeAbstractXPathNodeVisitor;

/**
 * This visitor will visit the xpath AST looking for variable references.
 */
public class AeXPathVariableNodeVisitor extends AeAbstractXPathNodeVisitor
{
   /** The variable references founds by the visitor. */
   private Set mVariableReferences;

   /**
    * Default c'tor.
    */
   public AeXPathVariableNodeVisitor()
   {
      setVariableReferences(new LinkedHashSet());
   }

   /**
    * @see org.activebpel.rt.bpel.xpath.ast.IAeXPathNodeVisitor#visit(org.activebpel.rt.bpel.xpath.ast.AeXPathVariableNode)
    */
   public void visit(AeXPathVariableNode aNode)
   {
      getVariableReferences().add(new AeScriptVarDef(aNode.getVariableQName(), getQueryForVariable(aNode)));
   }
   
   /**
    * Tries to determine the relative path/query used with this variable.  If there is a relative
    * path associated with the variable, the var node will have a parent of type AeXPathPathExprNode
    * and a single sibling (which will be the path).
    * 
    * @param aNode
    */
   protected String getQueryForVariable(AeXPathVariableNode aNode)
   {
      // Note: to get the query portion of a variable reference, examine the parent:
      //     - the parent should be a PathExpr with two children - the 2nd child will be the query (relative location path node)
      AeAbstractXPathNode parent = aNode.getParent();
      if (parent instanceof AeXPathPathExprNode)
      {
         AeAbstractXPathNode sibling = (AeAbstractXPathNode) parent.getChildren().get(1);
         return sibling.serialize();
      }
      return null;
   }

   /**
    * @return Returns the variableReferences.
    */
   public Set getVariableReferences()
   {
      return mVariableReferences;
   }

   /**
    * @param aVariableReferences The variableReferences to set.
    */
   protected void setVariableReferences(Set aVariableReferences)
   {
      mVariableReferences = aVariableReferences;
   }
}
