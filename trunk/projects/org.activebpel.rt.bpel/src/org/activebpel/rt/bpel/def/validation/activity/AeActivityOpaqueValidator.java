//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityOpaqueValidator.java,v 1.2 2006/11/07 00:25:2
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
package org.activebpel.rt.bpel.def.validation.activity;

import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.activity.AeActivityOpaqueDef;

/**
 * Model for validating ws-bpel 2.x abstract process's opaque activity.
 */
public class AeActivityOpaqueValidator extends AeActivityValidator
{

   /**
    * Ctor.
    * @param aDef
    */
   public AeActivityOpaqueValidator(AeActivityOpaqueDef aDef)
   {
      super(aDef);
   }
   
   /** 
    * Overrides method to report error if the opaque activity is not part of the abstract process namespace. 
    * @see org.activebpel.rt.bpel.def.validation.IAeValidator#validate()
    */
   public void validate()
   {
      super.validate();
      if (!IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI.equals(getBpelNamespace()) )
      {
         getReporter().addError( ERROR_OPAQUE_ACTIVITY_NOT_ALLOWED, null, (AeActivityOpaqueDef)getDefinition() );         
      }
   }

}
