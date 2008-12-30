// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/wsio/AeFromPartsValidator.java,v 1.1 2006/08/16 22:07:2
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
package org.activebpel.rt.bpel.def.validation.activity.wsio;

import org.activebpel.rt.bpel.def.activity.support.AeFromPartsDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;

/**
 * Model that provides validation of the fromParts.
 */
public class AeFromPartsValidator extends AeBaseValidator
{

   /**
    * Constructor.
    */
   public AeFromPartsValidator(AeFromPartsDef aDef)
   {
      super(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      AeFromPartsDef fromPartsDef = (AeFromPartsDef) getDefinition();
      
      // Must have at least one fromPart
      if (!fromPartsDef.getFromPartDefs().hasNext())
         getReporter().addError( ERROR_EMPTY_CONTAINER, new String[]{ fromPartsDef.getLocationPath() }, getDefinition() );
   }
}
