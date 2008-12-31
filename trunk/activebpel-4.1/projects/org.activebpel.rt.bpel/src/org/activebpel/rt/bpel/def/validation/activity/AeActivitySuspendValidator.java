//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivitySuspendValidator.java,v 1.2 2006/09/11 23:06:2
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

import org.activebpel.rt.bpel.def.activity.AeActivitySuspendDef;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;

/**
 * model provides validation for the suspend activity
 */
public class AeActivitySuspendValidator extends AeActivityValidator
{
   /** variable referenced by the variable */
   private AeVariableValidator mVariable;

   /**
    * ctor
    * @param aDef
    */
   public AeActivitySuspendValidator(AeActivitySuspendDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Getter for the def
    */
   protected AeActivitySuspendDef getDef()
   {
      return (AeActivitySuspendDef) getDefinition();
   }
   
   /**
    * Getter for the variable
    */
   public AeVariableValidator getVariable()
   {
      return mVariable;
   }

   /**
    * Validates:
    * 1. variable reference if provided
    * @see org.activebpel.rt.bpel.def.validation.activity.AeActivityValidator#validate()
    */
   public void validate()
   {
      super.validate();

      // Validate optional variable.
      if ( !isUndefined( getDef().getVariable()) )
      {
         mVariable = getVariableValidator( getDef().getVariable(), AeActivitySuspendDef.TAG_VARIABLE, true );
      }
   }
}
 
