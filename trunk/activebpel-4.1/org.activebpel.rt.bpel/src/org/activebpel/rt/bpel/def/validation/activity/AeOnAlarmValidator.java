//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeOnAlarmValidator.java,v 1.2 2006/10/03 19:25:4
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

import org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;
import org.activebpel.rt.bpel.def.validation.expressions.IAeExpressionModelValidator;

/**
 * provides validation for the onAlarm part of an event handler or pick
 */
public class AeOnAlarmValidator extends AeBaseValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeOnAlarmValidator(AeOnAlarmDef aDef)
   {
      super(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.expressions.AeBaseExpressionValidator#validate()
    */
   public void validate()
   {
      super.validate();

      validateAlarmChildren();
   }

   /**
    * Validates the OnAlarm's children.  In this case, the OnAlarm must have a single child that
    * implements the IAeExpressionModel interface.
    */
   protected void validateAlarmChildren()
   {
      IAeExpressionModelValidator child = (IAeExpressionModelValidator) getChild(IAeExpressionModelValidator.class);
      validateAlarmChild(child);
   }

   /**
    * Validates that the child is not null and contains a non-empty expression. The actual expression (if present)
    * is validated elsewhere.
    * @param aChild
    */
   protected void validateAlarmChild(IAeExpressionModelValidator aChild)
   {
      if (isNullOrEmpty(aChild))
      {
         getReporter().addError( ERROR_FIELD_MISSING,
               new String[] { AeOnAlarmDef.TAG_ON_ALARM },
               getDefinition() );
      }
   }

   /**
    * Getter for the def
    */
   protected AeOnAlarmDef getDef()
   {
      return (AeOnAlarmDef) getDefinition();
   }
}
 
