// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/AeWSBPELVariableValidator.java,v 1.1 2007/06/08 20:17:5
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
package org.activebpel.rt.bpel.def.validation;

import org.activebpel.rt.bpel.def.AeVariableDef;

/**
 * Extens base validtor to check that import exists for message type variables.
 */
public class AeWSBPELVariableValidator extends AeVariableValidator
{

   /**
    * Constructs validator for passed def.
    * @param aDef
    */
   public AeWSBPELVariableValidator(AeVariableDef aDef)
   {
      super(aDef);
   }

   /**
    * Extends method to check that the import exists for the namespace. 
    * @see org.activebpel.rt.bpel.def.validation.AeVariableValidator#validateMessageType()
    */
   protected void validateMessageType()
   {
      super.validateMessageType();
      
      // if the wsdl was found make sure it is in the imports
      if ( getDef().getMessageType() != null && getWsdlDef() != null )
      {
         // check that the namespace for the partnerlink type was imported if this is a WS-BPEL 2.0 process
         String namespaceURI = getDef().getMessageType().getNamespaceURI();
         if (getProcessDef().findImportDef(namespaceURI) == null)
         {
            String name = getDef().getMessageType().getLocalPart();
            getReporter().addWarning(WARNING_MISSING_IMPORT,
                                     new String[] { namespaceURI,
                                                    name },
                                     getDef() );
         }
         
      }
   }

}
