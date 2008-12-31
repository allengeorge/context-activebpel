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
import org.activebpel.rt.bpel.def.IAeActivityContainerDef;
import org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;

/**
 * A def visitor that will find any Invokes that have implicit scopes and make those scopes
 * explicit in the Def tree.
 */
public class AeDefCreateInvokeScopeVisitor extends AeAbstractDefVisitor
{
   /**
    * Default c'tor.
    */
   public AeDefCreateInvokeScopeVisitor()
   {
      setTraversalVisitor( new AeTraversalVisitor(new AeDefTraverser(), this));
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef)
    */
   public void visit(AeActivityInvokeDef aDef)
   {
      AeBaseDef parentDef = aDef.getParent();

      if (aDef.hasImplicitScopeDef())
      {
         AeActivityScopeDef scopeDef = aDef.removeImplicitScopeDef();
         scopeDef.setName(aDef.getName());
         scopeDef.setSourcesDef(aDef.getSourcesDef());
         scopeDef.setTargetsDef(aDef.getTargetsDef());
         scopeDef.getScopeDef().setParent(scopeDef);

         // ************************************************************************************
         // Note here that the name of the invoke activity does not need to be voided - but we 
         // used to do that, so we continue to do it for location-path legacy reasons.
         // ************************************************************************************
         aDef.setName(""); //$NON-NLS-1$
         aDef.setSourcesDef(null);
         aDef.setTargetsDef(null);
         
         // Now do the following:
         // 1) make the invoke a child of the scope
         // 2) tell the invoke's old parent to replace the invoke with the scope
         // 3) make the scope the parent of the invoke
         // 4) make the invoke's old 'parent' the parent of the scope.
         scopeDef.setActivityDef(aDef);
         ((IAeActivityContainerDef) parentDef).replaceActivityDef(aDef, scopeDef);
         aDef.setParent(scopeDef);
         scopeDef.setParent(parentDef);
      }
      super.visit(aDef);
   }
}
