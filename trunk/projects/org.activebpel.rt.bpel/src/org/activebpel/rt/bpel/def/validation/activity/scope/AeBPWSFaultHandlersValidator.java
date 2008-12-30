//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/scope/AeBPWSFaultHandlersValidator.java,v 1.2 2006/12/14 22:47:3
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

import org.activebpel.rt.bpel.def.AeCatchDef;
import org.activebpel.rt.bpel.def.AeFaultHandlersDef;
import org.activebpel.rt.util.AeUtil;

/**
 * model provides validation for the BPWS faultHandlers def
 */
public class AeBPWSFaultHandlersValidator extends AeFaultHandlersValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeBPWSFaultHandlersValidator(AeFaultHandlersDef aDef)
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
      getReporter().addWarning(ERROR_ILLEGAL_FH_CONSTRUCTS, null, baseCatch.getDefinition());
   }
   
   /**
    * Overrides method to
    * @see org.activebpel.rt.bpel.def.validation.activity.scope.AeFaultHandlersValidator#createSpecValidator(org.activebpel.rt.bpel.def.validation.activity.scope.AeBaseCatchValidator)
    */
   protected IAeCatchValidatorSpec createSpecValidator(AeBaseCatchValidator baseCatch)
   {
      return new AeBPWSCatchValidatorSpec(baseCatch.getDef());
   }
   
   /**
    * Nested class to create a object of AeBPWSCatchValidatorSpec for comparing the two 
    * catch element. Extend AeBaseCatchValidatorSpec to handle BPWS specific faultVaraible. 
    */
   protected static class AeBPWSCatchValidatorSpec extends AeBaseCatchValidatorSpec
   {

      /**
       * ctor
       * @param aDef
       */
      public AeBPWSCatchValidatorSpec(AeCatchDef aDef)
      {
         super(aDef);
      }
      
      /**
       * Equality is determined by comparing the AeCatchSpec of the fault handler and if the 
       * spec is equal then compare the faultName, faultVariable, falutElementName and faultMessageType.
       * @see java.lang.Object#equals(java.lang.Object)
       */    
      public boolean equals(Object aObject)
      {
         if ( aObject == null || !(aObject instanceof AeBPWSCatchValidatorSpec))
            return false;

         AeBPWSCatchValidatorSpec otherSpec = (AeBPWSCatchValidatorSpec)aObject;         
         return super.equals(aObject) && AeUtil.compareObjects(getCatchDef().getFaultVariable(), otherSpec.getCatchDef().getFaultVariable());
      }

      /**
       * @see java.lang.Object#hashCode()
       */
      public int hashCode()
      {
         return super.hashCode() + AeUtil.getSafeString(getCatchDef().getFaultVariable()).hashCode();  
      }
   }
      
}
 
