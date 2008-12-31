//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/assign/AeFromValidator.java,v 1.9 2006/12/14 22:45:2
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

import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.IAeExpressionDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.bpel.def.validation.IAeValidationDefs;
import org.activebpel.rt.bpel.def.validation.activity.scope.AePartnerLinkValidator;
import org.activebpel.rt.bpel.def.validation.expressions.AeBaseExpressionValidator;
import org.activebpel.rt.util.AeUtil;

/**
 * model provides validation for the from part of a copy operaiton
 */
public class AeFromValidator extends AeBaseExpressionValidator
{
   /** variable referenced by the from */
   private AeVariableValidator mVariable;
   
   /**
    * ctor
    * @param aDef
    */
   public AeFromValidator(AeFromDef aDef)
   {
      super((IAeExpressionDef)aDef);
   }
   
   /**
    * Getter for the def
    */
   protected AeFromDef getDef()
   {
      return (AeFromDef) getDefinition();
   }
   
   /**
    * Getter for the variable
    */
   protected AeVariableValidator getVariable()
   {
      return mVariable;
   }
   
   /**
    * Setter for the variable
    * @param aVariable
    */
   protected void setVariable(AeVariableValidator aVariable)
   {
      mVariable = aVariable;
   }

   /**
    * Validates:
    * 1. from conforms to one of the prescribed variants
    * 2. if variable referenced, record variable usage (either message part, message part query, element query, property)
    * 3. if plink referenced, validate that it can be resolved and provides the role we're selecting
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      if (getDef().getStrategyKey() == null && !getDef().isOpaque())
      {
         AeVariableDef varDef = AeDefUtil.getVariableByName(getDef().getVariable(), getDef());

         // the most common error will be an unresolvable variable, check for that and give custom message
         if (AeUtil.notNullOrEmpty(getDef().getVariable()) && varDef == null)
         {
            getReporter().addError(IAeValidationDefs.ERROR_VAR_NOT_FOUND, new String[] {getDef().getVariable()}, getDefinition());
         }
         else
         {
            getReporter().addError(IAeValidationDefs.ERROR_UNSUPPORTED_COPYOP_FROM, null, getDefinition());
         }
         return;
      }
      
      if ( AeUtil.notNullOrEmpty(getDef().getVariable()) )
      {
         setVariable(getVariableValidator(getDef().getVariable(), null, true));
         if (getVariable() == null)
         {
            return;
         }
         
         // handles recording the usage based on the pattern of part, query, property
         getVariable().addUsage(this, getDef().getPart(), getDef().getQuery(), getDef().getProperty());
      }
      else if (AeUtil.notNullOrEmpty(getDef().getPartnerLink()))
      {
         AePartnerLinkValidator plinkModel = getPartnerLinkValidator(getDef().getPartnerLink(), true);
         if (plinkModel != null)
         {
            plinkModel.addReference();
            if (getDef().isPartnerRole() && AeUtil.isNullOrEmpty(plinkModel.getDef().getPartnerRole())) 
            {
               Object[] args = {plinkModel.getName()};
               getReporter().addError(IAeValidationDefs.ERROR_PLINK_ASSIGN_FROM_PARTNERROE, args, getDefinition());
            }
            else if (getDef().isMyRole() && AeUtil.isNullOrEmpty(plinkModel.getDef().getMyRole())) 
            {
               Object[] args = {plinkModel.getName()};
               getReporter().addError(IAeValidationDefs.ERROR_PLINK_ASSIGN_FROM_MYROE, args, getDefinition());
            }
         }
      }
      
      // Fix for Defect #357 - add check for opaque in non-abstract process.
      // 
      if ( getDef().isOpaque() && !getProcessDef().isAbstractProcess())
         getReporter().addError( ERROR_OPAQUE_NOT_ALLOWED, new String[0], getDefinition() );
      
      // super will validate the expression if present
      super.validate();
   }
}
 
