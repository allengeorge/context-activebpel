//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/process/AeWSBPELProcessValidator.java,v 1.4 2006/12/14 22:49:4
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
package org.activebpel.rt.bpel.def.validation.process;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.validation.IAeValidationContext;
import org.activebpel.rt.bpel.def.validation.extensions.AeExtensionValidator;

/**
 * WS-BPEL 2.x version of the process def validator.
 */
public class AeWSBPELProcessValidator extends AeProcessValidator
{
   /**
    * Ctor.
    * @param aContext
    * @param aDef
    */
   public AeWSBPELProcessValidator(IAeValidationContext aContext, AeProcessDef aDef)
   {
      super(aContext, aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeValidator#validate()
    */
   public void validate()
   {
      if (getProcessDef().isCreateTargetXPath() || getProcessDef().isDisableSelectionFailure())
      {
         AeExtensionValidator extValidator = findExtensionValidator(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_QUERY_HANDLING);
         processExtensionValidator(extValidator, true, IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_QUERY_HANDLING);
      }

     if (getProcessDef().isStatefulProcess())
      {
         AeExtensionValidator extValidator = findExtensionValidator(IAeBPELConstants.SBPEL_EXTENSION_NAMESPACE_URI_STATEFUL_PROCESS);
         processExtensionValidator(extValidator, true, IAeBPELConstants.SBPEL_EXTENSION_NAMESPACE_URI_STATEFUL_PROCESS);
      }

      super.validate();
      
      if (isExtensionInUse())
      {
         getReporter().addInfo(AeMessages.getString("AeWSBPELProcessValidator.ExtensionActivitiesInUse"), null, //$NON-NLS-1$
               getDefinition());
      }
   }

   /**
    * Returns true if there are any extensions in use in the process.
    */
   protected boolean isExtensionInUse()
   {
      return getProcessDef().getExtensionDefs().hasNext();
   }
}
