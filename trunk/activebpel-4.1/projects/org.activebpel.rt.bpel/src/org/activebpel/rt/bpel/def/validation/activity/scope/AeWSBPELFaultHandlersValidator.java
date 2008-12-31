//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/scope/AeWSBPELFaultHandlersValidator.java,v 1.2 2006/12/14 22:47:3
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

import org.activebpel.rt.bpel.def.AeFaultHandlersDef;

/**
 * model provides validation for the WSBPEL faultHandlers def
 */
public class AeWSBPELFaultHandlersValidator extends AeFaultHandlersValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeWSBPELFaultHandlersValidator(AeFaultHandlersDef aDef)
   {
      super(aDef);
   }
      
/**
 * 
 * Overrides method to 
 * @see org.activebpel.rt.bpel.def.validation.activity.scope.AeFaultHandlersValidator#reportDuplicateCatch(org.activebpel.rt.bpel.def.validation.activity.scope.AeBaseCatchValidator)
 */
   protected void reportDuplicateCatch(AeBaseCatchValidator baseCatch)
   {
      getReporter().addError(ERROR_ILLEGAL_FH_CONSTRUCTS, null, baseCatch.getDefinition());
   }
}
 
