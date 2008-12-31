//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expressions/AeBaseExpressionValidator.java,v 1.5 2007/06/29 22:24:1
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
package org.activebpel.rt.bpel.def.validation.expressions; 

import java.util.Iterator;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.IAeExpressionDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.util.AeVariableData;
import org.activebpel.rt.bpel.def.util.AeVariableProperty;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.bpel.def.validation.expr.AeExpressionValidationContext;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationContext;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationResult;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidator;
import org.activebpel.rt.util.AeUtil;

/**
 * Base class for models that contain expressions that need to be validated.
 */
public abstract class AeBaseExpressionValidator extends AeBaseValidator implements IAeExpressionModelValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeBaseExpressionValidator(IAeExpressionDef aDef)
   {
      super((AeBaseDef) aDef);
   }
   
   /**
    * Ctor for subclasses
    * 
    * @param aDef
    */
   protected AeBaseExpressionValidator(AeBaseDef aDef)
   {
      super(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.expressions.IAeExpressionModelValidator#getExpressionDef()
    */
   public IAeExpressionDef getExpressionDef()
   {
      return (IAeExpressionDef) getDefinition();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();

      if (getExpressionDef() != null)
         validateExpression(getExpressionDef().getExpression(), getExpressionLanguage());
   }

   /**
    * Validates the given expression and language
    * @param aExpression
    * @param aExpressionLanguage
    */
   protected void validateExpression(String aExpression, String aExpressionLanguage)
   {
      if (AeUtil.isNullOrEmpty(aExpression))
         return;

      try
      {
         IAeExpressionValidator validator = getValidationContext().getExpressionLanguageFactory().createExpressionValidator(getBpelNamespace(), aExpressionLanguage);
         IAeExpressionValidationContext context = new AeExpressionValidationContext(getDefinition(), getValidationContext().getFunctionValidatorFactory());
         IAeExpressionValidationResult vResult = validateExpression(aExpression, validator, context);
         processExpressionValidationResult(vResult);
      }
      catch (Throwable t)
      {
         getReporter().addError(ERROR_INVALID_EXPRESSION,
               new String[] { getExpressionDef().getExpression(), t.getLocalizedMessage() }, getDefinition());
      }
   }

   /**
    * Gets the expression language to use when validating the given expression def.
    */
   protected String getExpressionLanguage()
   {
      return AeDefUtil.getExpressionLanguage(getExpressionDef(), getProcessDef());
   }

   /**
    * Process the expression validation result.  This amounts to extracting any errors or warnings from
    * the result object, as well as asking it for lists of AeVariableData and AeVariableProperty objects
    * in order to do various additional validation tasks.
    *
    * @param aResult
    */
   protected void processExpressionValidationResult(IAeExpressionValidationResult aResult)
   {
      // Process any info messages found by the validator.
      for ( Iterator iter = aResult.getInfoList().iterator() ; iter.hasNext() ; )
      {
         String info = (String) iter.next();
         getReporter().addInfo(info, null, getDefinition());
      }

      // Process any errors found by the validator.
      for ( Iterator iter = aResult.getErrors().iterator() ; iter.hasNext() ; )
      {
         String error = (String) iter.next();
         getReporter().addError(error, null, getDefinition());
      }

      // Process any warnings found by the validator.
      for ( Iterator iter = aResult.getWarnings().iterator() ; iter.hasNext() ; )
      {
         String warning = (String) iter.next();
         getReporter().addWarning(warning, null, getDefinition());
      }
      
      if (aResult.getParseResult() != null)
      {
         recordVariablesInExpression(aResult);
      }
   }

   /**
    * Resolves all of the variables found in the expression.
    * @param aResult
    */
   protected void recordVariablesInExpression(IAeExpressionValidationResult aResult)
   {
      // walk all of the variables found and add a reference
      for(Iterator it = aResult.getParseResult().getVarNames().iterator(); it.hasNext(); )
      {
         String name = (String) it.next();
         // Getting the variable will record the reference
         /*AeVariableValidator varModel =*/ getVariableValidator(name, null, true);
      }

      // Check variable data list.
      for (Iterator iter = aResult.getParseResult().getVarDataList().iterator(); iter.hasNext();)
      {
         AeVariableData varData = (AeVariableData) iter.next();
         AeVariableValidator varModel = getVariableValidator(varData.getVarName(), null, true);
         if (varModel != null)
         {
            if (varModel.getDef().isElement())
            {
               // There is a legacy issue here with getVariableData(). Our prev versions
               // allowed for a variant of getVariableData that selected an element variable
               // along with a query expression. 
               // In this format, the AeVariableData class will store the query as the
               // part name since it wasn't aware of this variation from the spec.
               varModel.addUsage(this, null, varData.getPart(), null);
            }
            else
            {
               varModel.addUsage(this, varData.getPart(), varData.getQuery(), null);
            }
            if (varModel.getDef().isMessageType() && AeUtil.isNullOrEmpty(varData.getPart()))
            {
               getReporter().addError(
                     AeMessages.getString("AeBaseExpressionModel.MissingPartInExpressionError"),  //$NON-NLS-1$
                     new Object[] { varModel.getDef().getName() }, 
                     getDefinition());
            }
         }
      }

      // Check variable property combos.
      for (Iterator iter = aResult.getParseResult().getVarPropertyList().iterator(); iter.hasNext();)
      {
         AeVariableProperty varProp = (AeVariableProperty) iter.next();
         AeVariableValidator varModel = getVariableValidator(varProp.getVarName(), null, true);
         if (varModel != null && varProp.getProperty() != null)
         {
            varModel.addUsage(this, null, null, varProp.getProperty());
         }
      }
}

   /**
    * Validates the expression. Subclasses will validate the specific type of the expression by calling different
    * methods on the validator like validateBoolean, validateDuration ...etc.
    * @param aExpression
    * @param aValidator
    * @param aContext
    * @throws AeException
    */
   protected IAeExpressionValidationResult validateExpression(String aExpression, IAeExpressionValidator aValidator, IAeExpressionValidationContext aContext) throws AeException
   {
      return aValidator.validateExpression(aContext, aExpression);
   }
}
 
