//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityWaitValidator.java,v 1.3 2006/10/03 19:35:1
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

import org.activebpel.rt.bpel.def.activity.AeActivityWaitDef;
import org.activebpel.rt.bpel.def.validation.expressions.IAeExpressionModelValidator;

/**
 * model provides validation for the wait activity
 */
public class AeActivityWaitValidator extends AeActivityValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeActivityWaitValidator(AeActivityWaitDef aDef)
   {
      super(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.AeActivityValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      IAeExpressionModelValidator child = (IAeExpressionModelValidator) getChild(IAeExpressionModelValidator.class);
      if (isNullOrEmpty(child))
      {
         getReporter().addError( ERROR_FIELD_MISSING,
               new String[] { AeActivityWaitDef.TAG_FOR },
               getDefinition() );
      }
   }
}
 
