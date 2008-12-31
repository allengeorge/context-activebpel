// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/scope/AeWSBPELPartnerLinkValidator.java,v 1.1 2007/06/08 20:17:5
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

import org.activebpel.rt.bpel.def.AePartnerLinkDef;

/**
 * Extends base class to check that import exists for the PartnerLinkType namespace. 
 */
public class AeWSBPELPartnerLinkValidator extends AePartnerLinkValidator
{
   /**
    * Constructs a validator for the passed def.
    * @param aDef
    */
   public AeWSBPELPartnerLinkValidator(AePartnerLinkDef aDef)
   {
      super(aDef);
   }

   /**
    * Extends method to check that the import exists for the partnerlinktype. 
    * @see org.activebpel.rt.bpel.def.validation.activity.scope.AePartnerLinkValidator#validate()
    */
   public void validate()
   {
      super.validate();

      // if we found the PartnerLinkType definition make sure it is a directly imported namespace
      if(getPartnerLinkType() != null)
      {
         // check that the namespace for the partnerlink type was imported if this is a WS-BPEL 2.0 process
         String namespaceURI = getDef().getPartnerLinkTypeName().getNamespaceURI();
         if (getProcessDef().findImportDef(namespaceURI) == null)
         {
            String name = getDef().getPartnerLinkTypeName().getLocalPart();
            getReporter().addWarning(WARNING_MISSING_IMPORT,
                                     new String[] { namespaceURI,
                                                    name },
                                     getDef() );
         }
         
      }
      
   }

}
