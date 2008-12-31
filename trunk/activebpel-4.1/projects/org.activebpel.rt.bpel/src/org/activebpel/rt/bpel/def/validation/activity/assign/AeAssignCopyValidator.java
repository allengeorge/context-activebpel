//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/assign/AeAssignCopyValidator.java,v 1.3 2006/12/14 22:45:2
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
package org.activebpel.rt.bpel.def.validation.activity.assign; 

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.activity.support.AeAssignCopyDef;
import org.activebpel.rt.bpel.def.io.readers.def.IAeFromStrategyNames;
import org.activebpel.rt.bpel.def.io.readers.def.IAeToStrategyNames;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;

/**
 * model provides validation for a copy operation within an assign
 */
public class AeAssignCopyValidator extends AeBaseValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeAssignCopyValidator(AeAssignCopyDef aDef)
   {
      super(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      AeFromValidator from = (AeFromValidator) getChild(AeFromValidator.class);
      AeToValidator to = (AeToValidator) getChild(AeToValidator.class);

      if (from == null)
      {
         getReporter().addError(AeMessages.getString("AeDefValidationVisitor.ASSIGNCOPY_WITH_MISSING_FROM"), null, getDefinition()); //$NON-NLS-1$
      }
      if (to == null)
      {
         getReporter().addError(AeMessages.getString("AeDefValidationVisitor.ASSIGNCOPY_WITH_MISSING_TO"), null, getDefinition()); //$NON-NLS-1$
      }
      
      if (from != null && to != null && from.getDef().getStrategyKey() != null && to.getDef().getStrategyKey() != null)
      {
         // validate that the strategies make sense
         String fromStrategyName = from.getDef().getStrategyKey().getStrategyName();
         String toStrategyName = to.getDef().getStrategyKey().getStrategyName();
         if ((fromStrategyName.equals(IAeFromStrategyNames.FROM_VARIABLE_MESSAGE) && !toStrategyName.equals(IAeToStrategyNames.TO_VARIABLE_MESSAGE))
               || (!fromStrategyName.equals(IAeFromStrategyNames.FROM_VARIABLE_MESSAGE) && toStrategyName.equals(IAeToStrategyNames.TO_VARIABLE_MESSAGE)))
         {
            getReporter().addWarning(ERROR_MISMATCHED_ASSIGNMENT_FAILURE, null, getDefinition());
         }
      }
   }
}
 
