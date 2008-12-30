// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeBPELMessageDataValidator.java,v 1.2 2006/10/18 23:22:4
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
package org.activebpel.rt.bpel.impl; 

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.AeWSDLDefHelper;
import org.activebpel.rt.message.AeMessageDataValidator;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;

/**
 * Subclass of AeMessageDataValidator that validates the message iff the engine config has message
 * validation enabled. Any faults during validation are rethrown as the standard bpel validation fault. 
 */
public class AeBPELMessageDataValidator implements IAeMessageValidator
{
   /** if true, the validator allows for empty parts or extra parts */
   private boolean mRelaxedValidation;
   
   /**
    * Ctor
    * @param aRelaxedValidationFlag - controls whether we'll fault if there are parts missing or extra parts
    */
   public AeBPELMessageDataValidator(boolean aRelaxedValidationFlag)
   {
      setRelaxedValidation(aRelaxedValidationFlag);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeMessageValidator#validate(org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal, org.activebpel.rt.message.IAeMessageData, org.activebpel.rt.message.AeMessagePartsMap)
    */
   public void validate(IAeBusinessProcessInternal aProcess, IAeMessageData aMessageData, AeMessagePartsMap aMessagePartsMap) throws AeBpelException
   {
      if (!aProcess.getEngine().getEngineConfiguration().validateServiceMessages())
      {
         return;
      }
      
      Exception cause = null;
      String errorMessage = null;
      try
      {
         AeBPELExtendedWSDLDef def = AeWSDLDefHelper.getWSDLDefinitionForMsg(aProcess.getProcessPlan(), aMessageData.getMessageType());
         AeMessageDataValidator messageValidator = new AeMessageDataValidator(isRelaxedValidation(), aMessagePartsMap);
         errorMessage = messageValidator.validate(aMessageData, def, aProcess.getEngine().getTypeMapping());
      }
      catch(AeException e)
      {
         cause = e;
      }
      
      if (errorMessage != null)
      {
         String msg = AeMessages.format("AeBPELMessageDataValidator.InvalidMessage", new Object[] {aMessageData.getMessageType(), errorMessage}); //$NON-NLS-1$

         IAeFaultFactory factory = AeFaultFactory.getFactory(aProcess.getBPELNamespace());
         throw new AeBpelException(msg, factory.getInvalidVariables(), cause);    
      }
   }

   /**
    * @return Returns the relaxedValidation.
    */
   public boolean isRelaxedValidation()
   {
      return mRelaxedValidation;
   }

   /**
    * @param aRelaxedValidation The relaxedValidation to set.
    */
   public void setRelaxedValidation(boolean aRelaxedValidation)
   {
      mRelaxedValidation = aRelaxedValidation;
   }
}
 
