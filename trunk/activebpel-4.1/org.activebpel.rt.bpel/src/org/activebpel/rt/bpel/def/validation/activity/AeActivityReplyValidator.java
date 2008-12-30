//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityReplyValidator.java,v 1.4 2006/12/14 22:43:1
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

import org.activebpel.rt.bpel.def.activity.AeActivityReplyDef;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * model provides validation for the reply activity
 */
public class AeActivityReplyValidator extends AeWSIOActivityValidator
{
   /** variable that we're replying with */
   private AeVariableValidator mVariableModel;

   /**
    * ctor
    * @param aDef
    */
   public AeActivityReplyValidator(AeActivityReplyDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Getter for the variable
    */
   public AeVariableValidator getVariable()
   {
      return mVariableModel;
   }
   
   /**
    * Validates:
    * 1. variable reference
    * @see org.activebpel.rt.bpel.def.validation.activity.AeWSIOActivityValidator#validate()
    */
   public void validate()
   {
      super.validate();

      // TODO (MF) Validate the variable or toParts against the outgoing message type.
      if (getDef().getToPartsDef() == null)
         mVariableModel = getVariableValidator(getDef().getVariable(), null, true);
      
      AeMessagePartsMap producerMap = getProducerMessagePartsMap();
      if (producerMap == null)
      {
         if (getDef().getFaultName() != null)
         {
            addTypeNotFoundError(ERROR_FAULT_NAME_NOT_FOUND, getDef().getFaultName(), getDefinition());
         }
         else
         {
            reportMessagePartsNotFound(mVariableModel, ERROR_OPERATION_OUT_NOT_FOUND);
         }
      }
      else
      {
         validateMessageProducerStrategy(producerMap, mVariableModel);
      }
   }

   /**
    * Getter for the def
    */
   public AeActivityReplyDef getDef()
   {
      return (AeActivityReplyDef) getDefinition();
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
 
