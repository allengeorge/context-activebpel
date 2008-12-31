// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityCompensateScopeValidator.java,v 1.4 2006/12/14 22:43:1
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

import org.activebpel.rt.bpel.def.AeScopeDef;
import org.activebpel.rt.bpel.def.activity.AeActivityCompensateScopeDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.validation.IAeValidationDefs;
import org.activebpel.rt.bpel.def.visitors.AeChildScopeByNameVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Model for validating the compensateScope activity.
 */
public class AeActivityCompensateScopeValidator extends AeActivityCompensateValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeActivityCompensateScopeValidator(AeActivityCompensateScopeDef aDef)
   {
      super(aDef);
   }

   /**
    * Getter for the def.
    */
   protected AeActivityCompensateScopeDef getCompensateScopeDef()
   {
      return (AeActivityCompensateScopeDef) getDefinition();
   }

   /**
    * Validates that the compensateScope activity has a valid 'target' attribute.
    * @see org.activebpel.rt.bpel.def.validation.activity.AeActivityValidator#validate()
    */
   public void validate()
   {
      super.validate();

      if (AeUtil.notNullOrEmpty(getCompensateScopeDef().getTarget()))
      {
         validateScopeTarget(getCompensateScopeDef().getTarget());
      }
      else
      {
         getReporter().addError(IAeValidationDefs.ERROR_COMPENSATE_SCOPE_EMPTY, null, getDefinition());
      }
   }
   
   /**
    * Validates that the scope target can be resolved
    * @param aScopeName
    */
   protected void validateScopeTarget(String aScopeName)
   {
      AeScopeDef rootScope = getDef().findRootScopeForCompensation();
      AeActivityScopeDef targetScope = AeChildScopeByNameVisitor.findChildScopeByName(rootScope, aScopeName);
      if (targetScope == null)
      {
         getReporter().addError( ERROR_SCOPE_NOT_FOUND,
               new String[] { aScopeName },
               getDefinition() );
      }
   }
}
