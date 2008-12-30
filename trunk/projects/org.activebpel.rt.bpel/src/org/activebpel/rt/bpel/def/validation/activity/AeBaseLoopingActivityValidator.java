//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeBaseLoopingActivityValidator.java,v 1.3 2006/12/14 22:43:1
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

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.validation.expressions.IAeExpressionModelValidator;

/**
 * Base class for validating looping activities like while and repeatUntil 
 */
public class AeBaseLoopingActivityValidator extends AeActivityValidator
{
   /**
    * Ctor
    * @param aDef
    */
   protected AeBaseLoopingActivityValidator(AeBaseDef aDef)
   {
      super(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.AeActivityValidator#validate()
    */
   public void validate()
   {
      super.validate();

      IAeExpressionModelValidator expression = (IAeExpressionModelValidator)getChild(IAeExpressionModelValidator.class);
      if (isNullOrEmpty(expression))
      {
         getReporter().addError( ERROR_FIELD_MISSING, new String[] { AeBaseDef.TAG_CONDITION }, getDefinition() ); 
      }
   }
}
 
