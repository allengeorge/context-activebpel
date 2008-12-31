//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityPickValidator.java,v 1.2 2006/09/25 01:34:4
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

import org.activebpel.rt.bpel.def.activity.AeActivityPickDef;

/**
 * model provides validation for pick model
 */
public class AeActivityPickValidator extends AeActivityValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeActivityPickValidator(AeActivityPickDef aDef)
   {
      super(aDef);
   }

   /**
    * Validates:
    * 1. if create instance, then no alarm allowed
    * 2. if create instance, record on process
    * @see org.activebpel.rt.bpel.def.validation.activity.AeActivityValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      // there must be at least 1 onMessage
      if (getChildren(AeOnMessageValidator.class).size() == 0)
      {
         getReporter().addError( ERROR_NO_ONMESSAGE, null, getDefinition() );
      }
      
      // no onAlarm if its create instance
      if ( getDef().isCreateInstance() && getChildren(AeOnAlarmValidator.class).size() > 0 )
      {
         getReporter().addError( ERROR_ALARM_ON_CREATEINSTANCE, null, getDefinition() );
      }
      
      if (getDef().isCreateInstance())
      {
         getProcessValidator().addCreateInstance(this);
      }
   }
   
   /**
    * Getter for the pick def
    */
   protected AeActivityPickDef getDef()
   {
      return (AeActivityPickDef) getDefinition();
   }
}
 
