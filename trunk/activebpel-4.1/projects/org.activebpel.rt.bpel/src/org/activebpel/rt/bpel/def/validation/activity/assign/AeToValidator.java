//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/assign/AeToValidator.java,v 1.7 2006/12/14 22:45:2
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
import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.bpel.def.validation.IAeValidationDefs;
import org.activebpel.rt.bpel.def.validation.activity.scope.AePartnerLinkValidator;
import org.activebpel.rt.bpel.def.validation.expressions.AeBaseExpressionValidator;
import org.activebpel.rt.util.AeUtil;

/**
 * model provides validation for the to def
 */
public class AeToValidator extends AeBaseExpressionValidator
{
   /** adapter interface used to validate the query */
   private IAeExpressionDef mDefAdapter;

   /**
    * ctor
    * @param aDef
    */
   public AeToValidator(AeToDef aDef)
   {
      super((IAeExpressionDef) aDef);
   }

   /**
    * Getter for the def
    */
   protected AeToDef getDef()
   {
      return (AeToDef) getDefinition();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expressions.AeBaseExpressionValidator#getExpressionDef()
    */
   public IAeExpressionDef getExpressionDef()
   {
      if (mDefAdapter == null && AeUtil.notNullOrEmpty(getDef().getQuery()))
      {
         mDefAdapter = new IAeExpressionDef()
         {
            /**
             * @see org.activebpel.rt.bpel.def.IAeExpressionDef#getExpression()
             */
            public String getExpression()
            {
               return getDef().getQuery();
            }

            /**
             * @see org.activebpel.rt.bpel.def.IAeExpressionDef#getExpressionLanguage()
             */
            public String getExpressionLanguage()
            {
               // TODO (EPW) should create a different path for executing/analyzing/validating queries to be used in the future.
               return getValidationContext().getExpressionLanguageFactory().getBpelDefaultLanguage(getBpelNamespace());
            }
            
            /**
             * @see org.activebpel.rt.bpel.def.IAeExpressionDef#getBpelNamespace()
             */
            public String getBpelNamespace()
            {
               return AeDefUtil.getProcessDef(getDef()).getNamespace();
            }

            /**
             * @see org.activebpel.rt.bpel.def.IAeExpressionDef#setExpression(java.lang.String)
             */
            public void setExpression(String aExpression)
            {
               // no-op
            }
            
            /**
             * @see org.activebpel.rt.bpel.def.IAeExpressionDef#setExpressionLanguage(java.lang.String)
             */
            public void setExpressionLanguage(String aLanguageURI)
            {
               // no-op
            }
         };
      }
      return mDefAdapter;
   }

   /**
    * Validates:
    * 1. to conforms to one of the prescribed variants
    * 2. if variable is provided, validate that it can be resolved and record usage (message part, message part query, element query, property)
    * @see org.activebpel.rt.bpel.def.validation.expressions.AeBaseExpressionValidator#validate()
    */
   public void validate()
   {
      if (getDef().getStrategyKey() == null)
      {
         AeVariableDef varDef = AeDefUtil.getVariableByName(getDef().getVariable(), getDef());

         // the most common error will be an unresolvable variable, check for that and give custom message
         if (AeUtil.notNullOrEmpty(getDef().getVariable()) && varDef == null)
         {
            getReporter().addError(IAeValidationDefs.ERROR_VAR_NOT_FOUND, new String[] {getDef().getVariable()}, getDefinition());
         }
         else
         {
            getReporter().addError(IAeValidationDefs.ERROR_UNSUPPORTED_COPYOP_TO, null, getDefinition());
         }
         return;
      }

      if (AeUtil.notNullOrEmpty(getDef().getVariable()))
      {
         AeVariableValidator variableValidator = getVariableValidator(getDef().getVariable(), null, true);
         if (variableValidator == null)
         {
            return;
         }

         // handles recording the usage based on the pattern of part, query, property
         variableValidator.addUsage(this, getDef().getPart(), getDef().getQuery(), getDef().getProperty());
      }
      // record partner link usage
      else if (AeUtil.notNullOrEmpty(getDef().getPartnerLink()))
      {
         AePartnerLinkValidator plink = getPartnerLinkValidator(getDef().getPartnerLink(), true);
         if (plink != null)
         {
            plink.addReference();
            
            if (AeUtil.isNullOrEmpty(plink.getDef().getPartnerRole()))
            {
               Object[] args = {plink.getName()};
               getReporter().addError(IAeValidationDefs.ERROR_PLINK_ASSIGN_TO, args, getDefinition());
            }
         }
      }

      // call to super will validate the query
      super.validate();
   }
}
