//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/process/AePartnerValidator.java,v 1.2 2006/09/11 23:06:2
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

import java.util.Iterator;

import org.activebpel.rt.bpel.def.AePartnerDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;

/**
 * Validates the partner def
 */
public class AePartnerValidator extends AeBaseValidator
{

   /**
    * ctor takes the partner def
    * @param aDef
    */
   public AePartnerValidator(AePartnerDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Getter for the def
    */
   protected AePartnerDef getDef()
   {
      return (AePartnerDef) getDefinition();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      // check the partner links listed by this partner.
      //
      for ( Iterator iter = getDef().getPartnerLinks() ; iter.hasNext() ; )
      {
         String name = (String)iter.next();
         // validates that the plink exists and records a reference to it
         getPartnerLinkValidator(name, true);
      }
   }
}
 
