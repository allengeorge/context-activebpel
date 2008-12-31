//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityValidateValidator.java,v 1.4 2006/12/14 22:43:1
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

import java.util.Iterator;

import org.activebpel.rt.bpel.def.activity.AeActivityValidateDef;

/**
 * model provides validation for the validate activity
 */
public class AeActivityValidateValidator extends AeActivityValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeActivityValidateValidator(AeActivityValidateDef aDef)
   {
      super(aDef);
   }

   /**
    * Getter for the def
    */
   protected AeActivityValidateDef getDef()
   {
      return (AeActivityValidateDef) getDefinition();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      if (getDef().getVariablesCount() == 0)
      {
         getReporter().addError(ERROR_EMPTY_VALIDATE, null, getDefinition());
      }
      else
      {
         // TODO (MF) Add test case to make sure variables are in scope
         // Validate that all of the variables referenced can be resolved
         for (Iterator iter=getDef().getVariables(); iter.hasNext(); )
            getVariableValidator((String)iter.next(), "variable", true);  //$NON-NLS-1$
      }
      
   }
} 
