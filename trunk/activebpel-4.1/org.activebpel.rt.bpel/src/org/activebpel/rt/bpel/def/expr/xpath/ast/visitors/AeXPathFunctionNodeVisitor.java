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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.activebpel.rt.bpel.def.expr.AeScriptFuncDef;
import org.activebpel.rt.bpel.xpath.ast.AeAbstractXPathNode;
import org.activebpel.rt.bpel.xpath.ast.AeXPathFunctionNode;
import org.activebpel.rt.bpel.xpath.ast.AeXPathLiteralNode;
import org.activebpel.rt.bpel.xpath.ast.visitors.AeAbstractXPathNodeVisitor;

/**
 * This visitor will visit the xpath AST looking for functions.
 */
public class AeXPathFunctionNodeVisitor extends AeAbstractXPathNodeVisitor
{
   /** The functions founds by the visitor. */
   private Set mFunctions;

   /**
    * Default c'tor.
    */
   public AeXPathFunctionNodeVisitor()
   {
      setFunctions(new LinkedHashSet());
   }

   /**
    * @see org.activebpel.rt.bpel.xpath.ast.IAeXPathNodeVisitor#visit(org.activebpel.rt.bpel.xpath.ast.AeXPathFunctionNode)
    */
   public void visit(AeXPathFunctionNode aNode)
   {
      AeScriptFuncDef funcDef = new AeScriptFuncDef(aNode.getFunctionQName());
      
      List arguments = new ArrayList();
      for (Iterator iter = aNode.getChildren().iterator(); iter.hasNext(); )
      {
         AeAbstractXPathNode child = (AeAbstractXPathNode) iter.next();
         if (child instanceof AeXPathLiteralNode)
            arguments.add( ((AeXPathLiteralNode) child).getValue() );
         else
            arguments.add(AeScriptFuncDef.__EXPRESSION__);
      }
      funcDef.setArgs(arguments);
      
      getFunctions().add(funcDef);
   }

   /**
    * @return Returns the functions.
    */
   public Set getFunctions()
   {
      return mFunctions;
   }

   /**
    * @param aFunctions The functions to set.
    */
   protected void setFunctions(Set aFunctions)
   {
      mFunctions = aFunctions;
   }
}
