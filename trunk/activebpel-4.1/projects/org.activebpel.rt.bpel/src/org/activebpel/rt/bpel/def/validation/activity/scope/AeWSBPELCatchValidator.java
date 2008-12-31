//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/scope/AeWSBPELCatchValidator.java,v 1.3 2006/09/11 23:06:2
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

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.def.AeCatchDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.bpel.impl.activity.support.AeFault;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeUtil;

/**
 * Adds the validation to handle faultVariable resolution rules
 */
public class AeWSBPELCatchValidator extends AeBaseCatchValidator
{
   /** valid catch def patterns for WS-BPEL */
   private static final Set WSBPEL_PATTERNS = new HashSet();

   static 
   {
      // catch w/ name only
      AeCatchSpec spec = new AeCatchSpec();
      spec.setFaultName();
      WSBPEL_PATTERNS.add(spec);
      
      // catch w/ variable + message 
      spec = new AeCatchSpec();
      spec.setFaultVariable();
      spec.setMessageType();
      WSBPEL_PATTERNS.add(spec);
      
      // catch w/ variable + message + name 
      spec = new AeCatchSpec();
      spec.setFaultName();
      spec.setFaultVariable();
      spec.setMessageType();
      WSBPEL_PATTERNS.add(spec);

      // catch w/ variable + element 
      spec = new AeCatchSpec();
      spec.setFaultVariable();
      spec.setElementType();
      WSBPEL_PATTERNS.add(spec);

      // catch w/ variable + element + name 
      spec = new AeCatchSpec();
      spec.setFaultName();
      spec.setFaultVariable();
      spec.setElementType();
      WSBPEL_PATTERNS.add(spec);
   };

   /**
    * Ctor accepts def
    * @param aDef
    */
   public AeWSBPELCatchValidator(AeCatchDef aDef)
   {
      super(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.scope.AeBaseCatchValidator#getPatterns()
    */
   protected Set getPatterns()
   {
      return WSBPEL_PATTERNS;
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#add(org.activebpel.rt.bpel.def.validation.AeBaseValidator)
    */
   public void add(AeBaseValidator aModel)
   {
      if (aModel instanceof AeVariableValidator)
      {
         aModel.setParent(this);
         setVariable((AeVariableValidator) aModel);
      }
      else
      {
         super.add(aModel);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.scope.AeBPWSCatchValidator#getPatternErrorMessage()
    */
   protected String getPatternErrorMessage()
   {
      return ERROR_WSBPEL_CATCH_PATTERN;
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.scope.AeBPWSCatchValidator#validate()
    */
   public void validate()
   {
      super.validate();
      if (getVariable() != null)
      {
         getVariable().validate();
      }
      //Validate illegal catch when exitOnStandardFault is set to yes
      QName faultName = getDef().getFaultName();
      if(faultName != null) 
      {
         IAeFault aFault =  new AeFault(faultName, (IAeMessageData) null);
         if ( getFaultFactory().isStandardFaultForExit(aFault)
               && AeDefUtil.isExitOnStandardFaultEnabled(getDefinition()) )
         {
            getReporter().addError(ERROR_ILLEGAL_CATCH_FOR_EXIT_ON_STD_FAULT, null, getDef());
         }
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#getVariableValidator(java.lang.String, java.lang.String, boolean)
    */
   protected AeVariableValidator getVariableValidator(String aName, String aFieldName, boolean aRecordReference)
   {
      if (AeUtil.compareObjects(aName, getDef().getFaultVariable()))
      {
         return (AeVariableValidator) getVariable();
      }
      else
      {
         return super.getVariableValidator(aName, aFieldName, aRecordReference);
      }
   }
}
 
