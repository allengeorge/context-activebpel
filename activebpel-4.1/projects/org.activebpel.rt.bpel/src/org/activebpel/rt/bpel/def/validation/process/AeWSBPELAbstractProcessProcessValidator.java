//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/process/AeWSBPELAbstractProcessProcessValidator.java,v 1.2 2006/12/14 22:49:4
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
import org.activebpel.rt.bpel.def.validation.IAeValidationContext;
import org.activebpel.rt.util.AeUtil;

/**
 * Process def validator for ws-bpel 2.x abstract processes.
 *
 */
public class AeWSBPELAbstractProcessProcessValidator extends AeWSBPELProcessValidator
{

   /**
    * Default ctor.
    * @param aContext
    * @param aDef
    */
   public AeWSBPELAbstractProcessProcessValidator(IAeValidationContext aContext, AeProcessDef aDef)
   {
      super(aContext, aDef);
   }

   /**
    * Overrides method to check for abstractProcessProfile attribute.
    * @see org.activebpel.rt.bpel.def.validation.process.AeWSBPELProcessValidator#validate()
    */
   public void validate()
   {
      super.validate();
      if ( AeUtil.isNullOrEmpty( getProcessDef().getAbstractProcessProfile() ) )
      {
         getReporter().addError(AeMessages.getString("AeWSBPELAbstractProcessProcessValidator.ProfileRequired"), null, //$NON-NLS-1$
               getDefinition());
      }
   }
}
