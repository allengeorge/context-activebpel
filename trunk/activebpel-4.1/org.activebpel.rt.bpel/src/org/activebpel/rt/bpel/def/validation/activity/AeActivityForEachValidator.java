//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityForEachValidator.java,v 1.5 2006/12/14 22:43:1
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

import org.activebpel.rt.bpel.def.activity.AeActivityForEachDef;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.util.AeUtil;

/**
 * model for validating a forEach activity
 */
public class AeActivityForEachValidator extends AeActivityValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeActivityForEachValidator(AeActivityForEachDef aDef)
   {
      super(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.AeActivityValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      if ( isUndefined(getDef().getCounterName()))
      {
         getReporter().addError( ERROR_FIELD_MISSING, new String[] { AeActivityForEachDef.TAG_FOREACH_COUNTERNAME }, getDefinition() );
      }
      else
      {
         // no need to check for null since if there was a counterName then we would have created a variable for the 
         // counter, unless one was already there, at which point we'll produce an error message since it's illegal to 
         // have an explicit def for an implicit variable
         AeVariableValidator variableModel = getCounterVariableModel();
         if (variableModel != null && !variableModel.getDef().isImplicit())
         {
            getReporter().addError( ERROR_IMPLICIT_VARIABLE_EXPLICITLY_DEFINED,
                  new String[] { getDefinition().getLocationPath(), variableModel.getDef().getName(), variableModel.getDef().getLocationPath() },
                  getDefinition() );
         }
      }
      
      if (isNullOrEmpty(getDef().getStartDef()))
      {
         getReporter().addError( ERROR_EMPTY_START_EXPRESSION, null, getDefinition() ); 
      }
      
      if (isNullOrEmpty(getDef().getFinalDef()))
      {
         getReporter().addError( ERROR_EMPTY_FINAL_EXPRESSION, null, getDefinition() ); 
      }
      
      if (getDef().getCompletionCondition() != null && AeUtil.isNullOrEmpty(getDef().getCompletionCondition().getExpression()))
      {
         getReporter().addError( ERROR_EMPTY_COMPLETION_CONDITION_EXPRESSION, null, getDefinition() ); 
      }
   }

   /**
    * Gets the implicit variable model in the child scope 
    */
   protected AeVariableValidator getCounterVariableModel()
   {
      AeActivityScopeValidator scope = (AeActivityScopeValidator) getChild(AeActivityScopeValidator.class);
      if (scope != null)
      {
         return scope.getVariableValidator(getDef().getCounterName());
      }
      return null;
   }
   
   /**
    * Getter for the def
    */
   protected AeActivityForEachDef getDef()
   {
      return (AeActivityForEachDef) getDefinition();
   }

}
 
