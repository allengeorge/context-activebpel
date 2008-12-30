//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/scope/AeWSBPELTerminationHandlerValidator.java,v 1.2 2006/10/30 22:47:2
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

import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.AeTerminationHandlerDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.validation.extensions.AeExtensionValidator;

/**
 * Model provides validation for the terminationHandler def.
 */
public class AeWSBPELTerminationHandlerValidator extends AeTerminationHandlerValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeWSBPELTerminationHandlerValidator(AeTerminationHandlerDef aDef)
   {
      super(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      // Is this an extension termination handler?  (process level termination handler)
      if (getDef().getParent() instanceof AeProcessDef)
      {
         AeExtensionValidator extensionValidator = findExtensionValidator(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ALLOW_PROCESS_COORDINATION);
         processExtensionValidator(extensionValidator, true, IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ALLOW_PROCESS_COORDINATION);
      }
   }
}
 
