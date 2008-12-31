//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/scope/AeVariablesValidator.java,v 1.3 2006/12/14 22:47:3
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
package org.activebpel.rt.bpel.def.validation.activity.scope; 

import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.def.AeVariablesDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;

/**
 * model provides validation for variables def
 */
public class AeVariablesValidator extends AeBaseValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeVariablesValidator(AeVariablesDef aDef)
   {
      super(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      // TODO (MF) check for variable type overrides for BPWS (old code produced warnings for overridden variables of diff type)
   }

   /**
    * Gets the given variable model by its name or null if not defined here
    * @param aName
    */
   public AeVariableValidator getVariableValidator(String aName)
   {
      List vars = getChildren(AeVariableValidator.class);
      for (Iterator iter = vars.iterator(); iter.hasNext();)
      {
         AeVariableValidator variableModel = (AeVariableValidator) iter.next();
         if (variableModel.getName().equals(aName))
            return variableModel;
      }
      return null;
   }
}
 
