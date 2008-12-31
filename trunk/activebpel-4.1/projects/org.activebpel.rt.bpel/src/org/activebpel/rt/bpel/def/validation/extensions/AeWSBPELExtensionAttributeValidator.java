// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/extensions/AeWSBPELExtensionAttributeValidator.java,v 1.2 2006/12/14 22:49:0
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
package org.activebpel.rt.bpel.def.validation.extensions;

import org.activebpel.rt.bpel.def.AeExtensionAttributeDef;
import org.activebpel.rt.util.AeUtil;

/**
 * Class that validates an extension attribute def.
 */
public class AeWSBPELExtensionAttributeValidator extends AeAbstractExtensionAttributeValidator
{
   /**
    * Constructor.
    * 
    * @param aDef
    */
   public AeWSBPELExtensionAttributeValidator(AeExtensionAttributeDef aDef)
   {
      super(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      String extNamespace = getDef().getNamespace();
      if (AeUtil.isNullOrEmpty(extNamespace))
      {
         getReporter().addError(ERROR_UNEXPECTED_ATTRIBUTE, new String[] { getDef().getQualifiedName() },
               getDefinition());
      }
      else
      {
         AeExtensionValidator extensionValidator = findExtensionValidator(extNamespace);
         processExtensionValidator(extensionValidator, false, extNamespace);
      }
   }
}
