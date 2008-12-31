// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDefAssignParentVisitor.java,v 1.3 2006/06/26 16:50:4
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
package org.activebpel.rt.bpel.def.visitors;

import org.activebpel.rt.bpel.def.AeBaseDef;

import java.util.Stack;

/**
 * Visitor responsible for assigning parent-to-child relationships among the def model.<p>
 *
 * Call AeDefAssigneParentVisitor.visit( AeProcessDef ) whenever the parent assignments
 * should be updated (but <i>after</i> the def model is completely built).
 */
public class AeDefAssignParentVisitor extends AeAbstractDefVisitor
{
   /** Stores the stack of objects that we're visiting */
   protected Stack mStack = new Stack();

   /**
    * Default c'tor.
    */
   public AeDefAssignParentVisitor()
   {
      setTraversalVisitor( new AeTraversalVisitor(new AeDefTraverser(), this));
   }

   /**
    * Pushes a def onto the stack, making it the current parent.
    * @param aDef
    */
   protected void push( AeBaseDef aDef )
   {
      mStack.push( aDef );
   }

   /**
    * Pops the current parent def from the stack.
    */
   protected void pop()
   {
      mStack.pop();
   }

   /**
    * Peeks at the current parent on the stack.
    */
   protected AeBaseDef peek()
   {
      if ( mStack.isEmpty())
         return null ;
      else
         return (AeBaseDef)mStack.peek();
   }

   /**
    * Set the current def's parent, then traverse any children.
    *
    * @param aDef The def to assign parent for, and then traverse.
    */
   protected void traverse( AeBaseDef aDef )
   {
      if ( peek() != null )
         aDef.setParent( peek());

      // Push the current def - if it has any children, this will be assigned as their parent.
      //
      push( aDef );
      super.traverse( aDef );
      pop();
   }
}
