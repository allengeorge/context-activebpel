//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/decision/AeElseIfValidator.java,v 1.2 2006/10/25 16:08:0
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
package org.activebpel.rt.bpel.def.validation.activity.decision; 

import org.activebpel.rt.bpel.def.activity.support.AeElseIfDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;

/**
 * model provides validation for the else if condition of a an if activity
 */
public class AeElseIfValidator extends AeBaseValidator
{

   /**
    * ctor
    * @param aDef
    */
   public AeElseIfValidator(AeElseIfDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Gets the def
    */
   protected AeElseIfDef getDef()
   {
      return (AeElseIfDef) getDefinition();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      // No empty cases allowed.
      //
      if ( getDef().getActivityDef() == null )
         getReporter().addError( ERROR_ACTIVITY_MISSING,
                              new String[] { getDef().getLocationPath() },
                              getDef() );

      // Needs a condition expression.
      //
      if ( getDef().getConditionDef() == null )
      {
         getReporter().addError( ERROR_FIELD_MISSING, new String[] { AeElseIfDef.TAG_CONDITION }, getDef() ); 
      }
      super.validate();
   }
} 
