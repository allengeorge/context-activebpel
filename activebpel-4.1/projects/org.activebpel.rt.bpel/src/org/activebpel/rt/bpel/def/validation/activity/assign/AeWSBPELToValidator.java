// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/assign/AeWSBPELToValidator.java,v 1.6 2006/12/14 22:45:2
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

import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.def.io.readers.def.AeExpressionSpecStrategyKey;
import org.activebpel.rt.bpel.def.io.readers.def.IAeToStrategyKeys;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.bpel.def.validation.IAeValidationDefs;

/**
 * A WS-BPEL 2.0 implementation of the ToModel.  This class overrides the base class in 
 * order to do some additional testing for the Query to-spec variant introduced in BPEL 2.0.
 */
public class AeWSBPELToValidator extends AeToValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeWSBPELToValidator(AeToDef aDef)
   {
      super(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.assign.AeToValidator#validate()
    */
   public void validate()
   {
      if (getDef().getStrategyKey() == IAeToStrategyKeys.KEY_TO_EXPRESSION)
      {
         getReporter().addError(IAeValidationDefs.ERROR_TO_EXPR_FORMAT_INVALID, 
               new Object[] { getDef().getExpression() }, 
               getDefinition());
         return;
      }
      else if (getDef().getStrategyKey() instanceof AeExpressionSpecStrategyKey)
      {
         AeExpressionSpecStrategyKey expressionStrategy = (AeExpressionSpecStrategyKey) getDef().getStrategyKey();
         String varName = expressionStrategy.getVariableName();
         String partName = expressionStrategy.getPartName();
         String query = expressionStrategy.getQuery();
         AeVariableValidator variableValidator = getVariableValidator(varName, null, true);
         if (variableValidator != null)
         {
            variableValidator.addUsage(this, partName, query, null);
         }
      }
      else
      {
         // Just do super.validate() - this will cause the Query to be validated as-is, which will give us
         // error messages like "could not parse query" etc...
         super.validate();
      }
   }
}
