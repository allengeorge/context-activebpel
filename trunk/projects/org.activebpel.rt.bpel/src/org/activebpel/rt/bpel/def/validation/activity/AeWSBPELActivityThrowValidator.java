//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeWSBPELActivityThrowValidator.java,v 1.1 2006/08/16 22:07:2
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

import org.activebpel.rt.bpel.def.activity.AeActivityThrowDef;

/**
 * Validates the throw activity for WS-BPEL 2.0
 */
public class AeWSBPELActivityThrowValidator extends AeActivityThrowValidator
{

   /**
    * Ctor accepts def
    * @param aDef
    */
   public AeWSBPELActivityThrowValidator(AeActivityThrowDef aDef)
   {
      super(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.AeActivityThrowValidator#validateVariable()
    */
   protected void validateVariable()
   {
      if (getVariable().getDef().isType())
      {
         getReporter().addError( ERROR_FAULT_TYPE,
               new String[] { getVariable().getDef().getName() },
               getDef() );
      }
   }
}
 
