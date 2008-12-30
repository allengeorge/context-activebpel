//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityValidator.java,v 1.1 2006/08/16 22:07:2
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
import org.activebpel.rt.bpel.def.IAeMultipleActivityContainerDef;
import org.activebpel.rt.bpel.def.IAeSingleActivityContainerDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;

/**
 * Base model for all activity subclasses
 */
public class AeActivityValidator extends AeBaseValidator
{
   /**
    * ctor
    * @param aDef
    */
   protected AeActivityValidator(AeBaseDef aDef)
   {
      super(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      validateNCName(false);

      checkForMissingChildActivity();
   }

   /**
    * checks for a missing child activity
    */
   protected void checkForMissingChildActivity()
   {
      if (getDefinition() instanceof IAeSingleActivityContainerDef || getDefinition() instanceof IAeMultipleActivityContainerDef)
      {
         if (getChildren(AeActivityValidator.class).isEmpty())
         {
               getReporter().addError( ERROR_ACTIVITY_MISSING,
                                    new String[] { getDefinition().getLocationPath() },
                                    getDefinition() );
         }
      }
   }
}
 
