//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeBPWSActivityScopeValidator.java,v 1.3 2006/12/14 22:43:1
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
package org.activebpel.rt.bpel.def.validation.activity; 

import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.validation.IAeValidationDefs;
import org.activebpel.rt.bpel.def.visitors.AeChildScopeByNameVisitor;

/**
 * Validator for a BPWS scope 
 */
public class AeBPWSActivityScopeValidator extends AeActivityScopeValidator
{

   /**
    * Ctor
    * @param aDef
    */
   public AeBPWSActivityScopeValidator(AeActivityScopeDef aDef)
   {
      super(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.AeActivityScopeValidator#validateIsolatedScope()
    */
   protected void validateIsolatedScope()
   {
      // MUST not contain any child scopes
      AeNoNestedScopes visitor = new AeNoNestedScopes(getDef());
      getDef().accept(visitor);
      if (visitor.isFound())
      {
         getReporter().addWarning(IAeValidationDefs.WARNING_BPWS_SERIALIZABLE_LEAF, null, getDefinition());
      }
   }

   /**
    * Visits the def and reports if there is a nested scope
    */
   protected static class AeNoNestedScopes extends AeChildScopeByNameVisitor
   {
      /** scoep that we start visiting from */
      private AeActivityScopeDef mRoot;
      
      /**
       * Ctor
       * @param aDef
       */
      protected AeNoNestedScopes(AeActivityScopeDef aDef)
      {
         super(null);
         mRoot = aDef;
      }
      
      /**
       * @see org.activebpel.rt.bpel.def.visitors.AeChildScopeByNameVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityScopeDef)
       */
      public void visit(AeActivityScopeDef aDef)
      {
         if (aDef == mRoot)
         {
            traverse(aDef);
         }
         else
         {
            setScopeDef(aDef);
         }
      }
   }
}
 
