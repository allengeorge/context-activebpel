// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeChildScopesVisitor.java,v 1.1 2006/10/18 23:12:4
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

import java.util.LinkedHashSet;
import java.util.Set;

import org.activebpel.rt.bpel.def.AeScopeDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;

/**
 * Visitor that locates child scopes.
 */
public class AeChildScopesVisitor extends AeAbstractDefVisitor
{
   /** the set of child scopes */
   private Set mChildScopeDefs = new LinkedHashSet();
   
   /**
    * ctor
    * @param aScopeName
    */
   protected AeChildScopesVisitor()
   {
      setTraversalVisitor(new AeTraversalVisitor(new AeDefTraverser(), this)); 
   }

   /**
    * Returns child scopes of the given scope.
    *
    * @param aRootScopeDef
    */
   public static Set findChildScopes(AeScopeDef aRootScopeDef)
   {
      AeChildScopesVisitor visitor = new AeChildScopesVisitor();
      aRootScopeDef.getActivityDef().accept(visitor);
      return visitor.getChildScopeDefs();
   }

   /**
    * Returns the set of child scopes.
    */
   protected Set getChildScopeDefs()
   {
      return mChildScopeDefs;
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityScopeDef)
    */
   public void visit(AeActivityScopeDef aDef)
   {
      // Found a child scope.
      getChildScopeDefs().add(aDef);
      
      // No traversing into the def since we can only reference scopes 1 level deep.
   }
}
