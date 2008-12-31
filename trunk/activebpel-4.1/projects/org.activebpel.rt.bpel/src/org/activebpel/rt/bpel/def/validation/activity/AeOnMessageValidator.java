//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeOnMessageValidator.java,v 1.3 2006/12/14 22:44:5
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
import org.activebpel.rt.bpel.def.IAeActivityCreateInstanceDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.util.AeUtil;

/**
 * model provides validation for the onMessage part of a pick
 */
public class AeOnMessageValidator extends AeOnMessageBaseValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeOnMessageValidator(AeOnMessageDef aDef)
   {
      super(aDef);
   }
   
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.AeOnMessageBaseValidator#resolveVariable()
    */
   protected AeVariableValidator resolveVariable()
   {
      // Avoid calling getVariableValidator() if there is no variable specified
      // since variable can be null for ws-bpel if it's an empty message. 
      if (AeUtil.notNullOrEmpty(getDef().getVariable()))
         return getVariableValidator(getDef().getVariable(), AeOnMessageDef.TAG_VARIABLE, true); 

      return null;
   }

   /**
    * Getter for the def
    */
   protected AeOnMessageDef getDef()
   {
      return (AeOnMessageDef) getDefinition();
   }

   /**
    * Validates:
    * 1. variable exists
    * 2. warns if not create instance and no correlation
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      // calling getVariable() will report an error if the variable is not found
      getVariable();
      
      // check for corr set if parent is a Pick and not a create instance
      //
      boolean isCreateInstance = false;
      AeBaseDef parent = getDef().getParent();
      if ( parent instanceof IAeActivityCreateInstanceDef )
      {
         isCreateInstance = ((IAeActivityCreateInstanceDef)getDef().getParent()).isCreateInstance();
      }

      if ( !isCreateInstance && !getDef().getCorrelationDefs().hasNext())
      {
         getReporter().addWarning( WARNING_NO_CORR_SET_NO_CREATE,
                                    new String[] { AeOnMessageDef.TAG_ON_MESSAGE },
                                    getDef() );
      }
   }
}
 
