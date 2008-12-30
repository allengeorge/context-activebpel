// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeIsolatedScopeVisitor.java,v 1.1 2006/10/18 23:12:2
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

import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;

/**
 * Visitor that sets {@link AeActivityDef#setIsolatedScope(AeActivityScopeDef)}
 * for all activities.
 */
public class AeIsolatedScopeVisitor extends AeAbstractDefVisitor
{
   /**
    * The isolated scope that we are currently traversing, or
    * <code>null</code> if we are not within an isolated scope.
    */
   private AeActivityScopeDef mIsolatedScope;

   /**
    * Constructs the visitor with the default traversal implementation. 
    */
   public AeIsolatedScopeVisitor()
   {
      setTraversalVisitor(new AeTraversalVisitor(new AeDefTraverser(), this));
   }
   
   /**
    * Returns the isolated scope that we are currently traversing or
    * <code>null</code> if we are not within an isolated scope.
    */
   protected AeActivityScopeDef getIsolatedScope()
   {
      return mIsolatedScope;
   }

   /**
    * Sets the isolated scope that we are currently traversing.
    *
    * @param aIsolatedScope
    */
   protected void setIsolatedScope(AeActivityScopeDef aIsolatedScope)
   {
      mIsolatedScope = aIsolatedScope;
   }

   /**
    * Overrides method to save the current isolated scope to activities.
    * 
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#traverse(org.activebpel.rt.bpel.def.AeBaseDef)
    */
   protected void traverse(AeBaseDef aDef)
   {
      if (aDef instanceof AeActivityDef)
      {
         ((AeActivityDef) aDef).setIsolatedScope(getIsolatedScope());
      }

      super.traverse(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityScopeDef)
    */
   public void visit(AeActivityScopeDef aDef)
   {
      if (aDef.isIsolated())
      {
         // Save (and restore) the current isolated scope in case we have an
         // nested isolated scope (which should never happen).
         AeActivityScopeDef oldIsolatedScope = getIsolatedScope();
         
         // Set the current isolated scope.
         setIsolatedScope(aDef);

         super.visit(aDef);

         setIsolatedScope(oldIsolatedScope);
      }
      else
      {
         super.visit(aDef);
      }
   }
}
