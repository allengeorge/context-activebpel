//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityReceiveValidator.java,v 1.4 2006/09/27 00:36:2
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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * model for validating the receive activity
 */
public class AeActivityReceiveValidator extends AeWSIOActivityValidator
{
   /** variable that we're receiving */
   private AeVariableValidator mVariableModel;
   
   /**
    * ctor
    * @param aDef
    */
   public AeActivityReceiveValidator(AeActivityReceiveDef aDef)
   {
      super(aDef);
   }

   /**
    * Validates:
    * 1. if create instance, record with process
    * 2. if not create instance, issue warning if no correlation
    * @see org.activebpel.rt.bpel.def.validation.activity.AeWSIOActivityValidator#validate()
    */
   public void validate()
   {
      mVariableModel = getVariableValidator(getDef().getVariable(), null, true);

      super.validate();

      if (getDef().isCreateInstance())
      {
         getProcessValidator().addCreateInstance(this);
      }
      else if (!getDef().getCorrelationList().hasNext())
      {
         getReporter().addWarning( WARNING_NO_CORR_SET_NO_CREATE,
               new String[] { AeActivityReceiveDef.TAG_RECEIVE },
               getDefinition());
      }

      AeMessagePartsMap consumerMap = getConsumerMessagePartsMap();
      validateMessageConsumerStrategy(consumerMap, mVariableModel);
   }
   
   /**
    * Getter for the variable
    */
   public AeVariableValidator getVariable()
   {
      return mVariableModel;
   }
   
   /**
    * Getter for the def
    */
   protected AeActivityReceiveDef getDef()
   {
      return (AeActivityReceiveDef) getDefinition();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAePartnerLinkOperationUser#getOperation()
    */
   public String getOperation()
   {
      return getDef().getOperation();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAePartnerLinkOperationUser#getPortType()
    */
   public QName getPortType()
   {
      return getDef().getPortType();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAePartnerLinkOperationUser#isMyRole()
    */
   public boolean isMyRole()
   {
      return true;
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAePartnerLinkOperationUser#isPartnerRole()
    */
   public boolean isPartnerRole()
   {
      return false;
   }
}
 
