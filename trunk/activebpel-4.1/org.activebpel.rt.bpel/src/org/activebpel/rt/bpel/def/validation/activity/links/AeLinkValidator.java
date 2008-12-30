//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/links/AeLinkValidator.java,v 1.2 2006/09/25 01:34:3
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
package org.activebpel.rt.bpel.def.validation.activity.links; 

import org.activebpel.rt.bpel.def.activity.support.AeLinkDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;

/**
 * model provides validation for the link
 */
public class AeLinkValidator extends AeBaseValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeLinkValidator(AeLinkDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Getter for the link def
    */
   protected AeLinkDef getDef()
   {
      return (AeLinkDef) getDefinition();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      validateNCName(true);
      getProcessValidator().getLinkValidator().addLink(getDef());
      
      // fixme 2.0 only, warning if link not used
   }
}
 
