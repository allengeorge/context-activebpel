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
package org.activebpel.rt.bpel.def.validation.variable;

import org.activebpel.rt.bpel.def.AeBaseDefNamespaceContext;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.bpel.def.validation.IAeValidator;
import org.activebpel.rt.xml.IAeNamespaceContext;



/**
 * Base class for a variable usage, includes reference to the model
 * that is using the variable
 */
public abstract class AeVariableUsage
{
   /** The variable validator. */
   private AeVariableValidator mVariableValidator;
   /** Validator that is using the variable */
   private IAeValidator mValidator;

   /**
    * ctor
    * 
    * @param aVariableValidator
    * @param aValidator
    */
   protected AeVariableUsage(AeVariableValidator aVariableValidator, IAeValidator aValidator)
   {
      setVariableValidator(aVariableValidator);
      setValidator(aValidator);
   }

   /**
    * Getter for the validator
    */
   protected IAeValidator getValidator()
   {
      return mValidator;
   }
   
   /**
    * validates the usage
    */
   public boolean validate()
   {
      return true;
   }

   /**
    * @return Returns the varModel.
    */
   protected AeVariableValidator getVariableValidator()
   {
      return mVariableValidator;
   }

   /**
    * @param aVarModel The varModel to set.
    */
   protected void setVariableValidator(AeVariableValidator aVarModel)
   {
      mVariableValidator = aVarModel;
   }

   /**
    * @param aModel The model to set.
    */
   protected void setValidator(IAeValidator aModel)
   {
      mValidator = aModel;
   }
   
   /**
    * Returns a namespace context that uses the defs to resolve the namespace.
    * This should be overridden by any property alias based variable usage.
    */
   protected IAeNamespaceContext createNamespaceContextForQuery()
   {
      IAeNamespaceContext nsContext = new AeBaseDefNamespaceContext(getValidator().getDefinition());
      return nsContext;
   }
}
