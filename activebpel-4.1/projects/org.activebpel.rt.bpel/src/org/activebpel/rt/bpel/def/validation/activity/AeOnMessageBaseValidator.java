//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeOnMessageBaseValidator.java,v 1.4 2006/09/27 00:36:2
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

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.activity.IAeReceiveActivityDef;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.message.AeMessagePartsMap;

public abstract class AeOnMessageBaseValidator extends AeWSIOActivityValidator
{
   /** variable that is being received */
   protected AeVariableValidator mVariable;

   /**
    * Ctor accepts the def
    * @param aDef
    */
   public AeOnMessageBaseValidator(AeBaseDef aDef)
   {
      super(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.AeWSIOActivityValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      AeMessagePartsMap consumerMap = getConsumerMessagePartsMap();
      AeVariableValidator variable = getVariable();
      validateMessageConsumerStrategy(consumerMap, variable);
   }

   /**
    * Gets the variable for the onMessage
    */
   protected AeVariableValidator getVariable()
   {
      if (mVariable == null)
      {
         mVariable = resolveVariable(); 
      }
      return mVariable;
   }
   
   /**
    * Resolves the variable
    */
   protected abstract AeVariableValidator resolveVariable();
   
   
   /**
    * Getter for the def
    */
   private IAeReceiveActivityDef getDef()
   {
      return (IAeReceiveActivityDef) getDefinition();
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

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validateNCName(boolean)
    */
   protected void validateNCName(boolean aRequired)
   {
      // side effect of extending wsio activity, onMessage doesn't have a name
   }
}
 
