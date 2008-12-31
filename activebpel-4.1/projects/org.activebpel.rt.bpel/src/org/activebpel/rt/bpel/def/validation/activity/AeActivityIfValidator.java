//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityIfValidator.java,v 1.2 2006/10/03 19:25:4
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

import org.activebpel.rt.bpel.def.activity.AeActivityIfDef;
import org.activebpel.rt.bpel.def.validation.activity.decision.AeIfValidator;

/**
 * model provides validation for the 1.1 switch and 2.0 if activities
 */
public class AeActivityIfValidator extends AeActivityValidator
{
   /** error for reporting missing condition */
   private String mMissingConditionError;
   
   /**
    * ctor
    * @param aDef
    */
   public AeActivityIfValidator(AeActivityIfDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Validates that the if contains at least one conditional check
    * @see org.activebpel.rt.bpel.def.validation.IAeValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      AeIfValidator child = (AeIfValidator)getChild(AeIfValidator.class);
      if(child == null)
      {
         getReporter().addError( getMissingConditionError(), null, getDefinition() );
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.AeActivityValidator#checkForMissingChildActivity()
    */
   protected void checkForMissingChildActivity()
   {
      // no-op here, the child activity will be nested within an if def
   }

   /**
    * Error message for missing condition
    */
   public String getMissingConditionError()
   {
      return mMissingConditionError;
   }

   /**
    * Setter for missing condition error message
    * @param aMissingConditionError
    */
   public void setMissingConditionError(String aMissingConditionError)
   {
      mMissingConditionError = aMissingConditionError;
   }
}
 
