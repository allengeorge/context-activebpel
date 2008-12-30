//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeWSIOActivityValidator.java,v 1.8 2007/03/10 01:53:5
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

import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AeActivityPartnerLinkBaseDef;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef;
import org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef;
import org.activebpel.rt.bpel.def.activity.IAePartnerLinkActivityDef;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.bpel.def.validation.IAePartnerLinkOperationUser;
import org.activebpel.rt.bpel.def.validation.activity.scope.AePartnerLinkValidator;
import org.activebpel.rt.bpel.def.validation.activity.wsio.AeCorrelationValidator;
import org.activebpel.rt.bpel.def.validation.activity.wsio.AeCorrelationsValidator;
import org.activebpel.rt.bpel.def.visitors.preprocess.strategies.wsio.IAeMessageDataStrategyNames;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.util.AeUtil;

/**
 * Base model for activities that involve sending or receiving data through some WSDL interface
 */
public abstract class AeWSIOActivityValidator extends AeActivityValidator implements IAePartnerLinkOperationUser, IAeCorrelationUser
{
   /** plink referenced by this wsio activity */
   private AePartnerLinkValidator mPartnerLinkValidator;
   
   /**
    * ctor
    * @param aDef
    */
   public AeWSIOActivityValidator(AeBaseDef aDef)
   {
      super(aDef);
   }

   /**
    * Validates that the partner link referenced exists and records our usage of the plink, role, and operation
    * @see org.activebpel.rt.bpel.def.validation.activity.AeActivityValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      recordPartnerLinkUsage();
      
      if (isUndefined(getOperation()))
      {
         getReporter().addError(ERROR_FIELD_MISSING,
               new String[] { AeActivityPartnerLinkBaseDef.TAG_OPERATION },
               getDefinition());
         return;
      }
   }

   /**
    * Reports an error that the message parts for the wsio activity weren't found.
    * If the missing message parts are due to an invalid portType, then no error
    * is reported since that is handled elsewhere.
    * 
    * @param aVariable
    * @param aOperationNotFoundMessage
    */
   protected void reportMessagePartsNotFound(AeVariableValidator aVariable, String aOperationNotFoundMessage)
   {
      QName portType = findPortType();
      // if the port type is null then we'll report an error on the plink validator
      if (portType != null)
      {
         if (aVariable != null)
         {
            getReporter().addError(aOperationNotFoundMessage,
                  new String[] { getOperation(), 
                                 getNSPrefix(aVariable.getType().getNamespaceURI()), 
                                 aVariable.getType().getLocalPart() },
                  getDefinition());
         }
         else
         {
            getReporter().addError(ERROR_OPERATION_NOT_FOUND,
                  new String[] { getOperation(), 
                                 getNSPrefix(portType.getNamespaceURI()), 
                                 portType.getLocalPart() },
                  getDefinition());
         }
      }
   }
   
   /**
    * Finds the port type through the activity or the plink def
    */
   protected QName findPortType()
   {
      QName portType = getPortType();
      if (portType == null && isPortTypeOptional())
      {
         if (getPartnerLinkValidator() != null)
         {
            if (isMyRole())
            {
               portType = getPartnerLinkValidator().getMyRolePortType();
            }
            else
            {
               portType = getPartnerLinkValidator().getPartnerRolePortType();
            }
         }
      }
      return portType;
   }
   
   /**
    * If the wsio activity is receiving message data then it must provide a strategy to consume that
    * message data. These strategies include copying the message data into a message variable,
    * element variable, executing fromParts, or doing nothing in the case of an empty message.
    * 
    * @param aMessagePartsMap
    * @param aVariableValidator
    */
   protected void validateMessageConsumerStrategy(AeMessagePartsMap aMessagePartsMap, AeVariableValidator aVariableValidator)
   {
      IAeMessageDataConsumerDef def = (IAeMessageDataConsumerDef) getDefinition();
      String strategy = def.getMessageDataConsumerStrategy();
      validateMessageStrategy(aMessagePartsMap, aVariableValidator, strategy, true);
   }

   /**
    * If the wsio activity is sending message data then it must provide a strategy to produce that
    * message data. These strategies include copying the message data into a message variable,
    * element variable, executing toParts, or doing nothing in the case of an empty message.
    * 
    * @param aMessagePartsMap
    * @param aVariableValidator
    */
   protected void validateMessageProducerStrategy(AeMessagePartsMap aMessagePartsMap, AeVariableValidator aVariableValidator)
   {
      IAeMessageDataProducerDef def = (IAeMessageDataProducerDef) getDefinition();
      String strategy = def.getMessageDataProducerStrategy();
      validateMessageStrategy(aMessagePartsMap, aVariableValidator, strategy, false);
   }

   /**
    * Validates the strategy against the variable or fromParts/toParts
    * @param aMessagePartsMap
    * @param aVariableValidator
    * @param aStrategy
    * @param aConsumer
    */
   protected void validateMessageStrategy(AeMessagePartsMap aMessagePartsMap, AeVariableValidator aVariableValidator, 
                           String aStrategy, boolean aConsumer)
   {
      if (AeUtil.isNullOrEmpty(aStrategy))
      {
         // most likely scenario is a missing or wrong variable
         if ((aVariableValidator == null || !aVariableValidator.getDef().isMessageType()) && !isWSBPEL())
         {
            getReporter().addError(ERROR_MESSAGE_VARIABLE_REQUIRED,
                  new String[] { getOperation()},
                  getDefinition());
         }
         else
         {
            getReporter().addError(aConsumer ? ERROR_MESSAGE_CONSUMER_STRATEGY : ERROR_MESSAGE_PRODUCER_STRATEGY,
                  new String[] { getOperation()},
                  getDefinition());
         }
      }
      else if (aMessagePartsMap == null)
      {
         reportMessagePartsNotFound(aVariableValidator, aConsumer ? ERROR_OPERATION_IN_NOT_FOUND : ERROR_OPERATION_OUT_NOT_FOUND);
      }
      else if (IAeMessageDataStrategyNames.EMPTY_MESSAGE.equals(aStrategy))
      {
         if (aMessagePartsMap.getPartsCount() != 0)
         {
            getReporter().addError(aConsumer ? ERROR_EMPTY_MESSAGE_CONSUMER_STRATEGY_NOT_ALLOWED : ERROR_EMPTY_MESSAGE_PRODUCER_STRATEGY_NOT_ALLOWED,
                  new String[] { getNSPrefix(aMessagePartsMap.getMessageType().getNamespaceURI()), aMessagePartsMap.getMessageType().getLocalPart(), getOperation()},
                  getDefinition());
         }
      }
      else if (IAeMessageDataStrategyNames.ELEMENT_VARIABLE.equals(aStrategy))
      {
         QName singleElementPart = aMessagePartsMap.getSingleElementPart();

         if (singleElementPart == null)
         {
            getReporter().addError(ERROR_ELEMENT_VARIABLE_NOT_ALLOWED,
                  new String[] { getOperation()},
                  getDefinition());
         }
         else if (!AeUtil.compareObjects(singleElementPart, aVariableValidator.getType()) &&
            aVariableValidator.getWsdlDef() != null && !aVariableValidator.getWsdlDef().isCompatibleSGElement(singleElementPart, aVariableValidator.getType()))
         {
            QName actualQName = aVariableValidator.getType();
            QName expectedMessageQName = aMessagePartsMap.getMessageType();
            getReporter().addError(ERROR_WRONG_ELEMENT_VARIABLE,
                  new String[] { getOperation(), 
                        getNSPrefix(actualQName.getNamespaceURI()), actualQName.getLocalPart(),
                        getNSPrefix(expectedMessageQName.getNamespaceURI()), expectedMessageQName.getLocalPart(),
                        getNSPrefix(singleElementPart.getNamespaceURI()), singleElementPart.getLocalPart()},
                  getDefinition());         
         }
      }
      else if (IAeMessageDataStrategyNames.MESSAGE_VARIABLE.equals(aStrategy))
      {
         if (!AeUtil.compareObjects(aMessagePartsMap.getMessageType(), aVariableValidator.getType()))
         {
            getReporter().addError(ERROR_WRONG_MESSAGE_VARIABLE,
                  new String[] { getOperation(), 
                                 getNSPrefix(aVariableValidator.getType().getNamespaceURI()), 
                                 aVariableValidator.getType().getLocalPart(),
                                 getNSPrefix(aMessagePartsMap.getMessageType().getNamespaceURI()), 
                                 aMessagePartsMap.getMessageType().getLocalPart()},
                  getDefinition());
         }
      }
   }
   
   /**
    * Validates that the partner link referenced exists and records our usage of the plink, role, and operation
    */
   protected void recordPartnerLinkUsage()
   {
      String plinkName = getPartnerLink();
      AePartnerLinkValidator plinkModel = getPartnerLinkValidator(plinkName, true);
      if (plinkModel != null)
      {
         recordPartnerLinkModel(plinkModel);
         setPartnerLinkValidator(plinkModel);
      }
   }

   /**
    * Records our usage of the plink. Broken out as a separate method to allow the reply
    * subclass to record its fault behaviour if it's replying w/ a fault.
    * @param plinkModel
    */
   protected void recordPartnerLinkModel(AePartnerLinkValidator plinkModel)
   {
      plinkModel.addPartnerLinkUser(this);
   }
   
   /**
    * Getter for the partner link name
    */
   protected String getPartnerLink()
   {
      return ((IAePartnerLinkActivityDef)getDefinition()).getPartnerLink();
   }
   
   /**
    * Resolves the variable for an enclosed fromPart
    * @param aVariableName
    */
   public AeVariableValidator resolveFromPartVariable(String aVariableName)
   {
      return getVariableValidator(aVariableName, null, true);
   }
   
   /**
    * Gets the list of correlations used for this operation.
    */
   public List getCorrelations()
   {
      AeCorrelationsValidator corrModel = (AeCorrelationsValidator) getChild(AeCorrelationsValidator.class);
      if (corrModel == null)
         return Collections.EMPTY_LIST;
      return corrModel.getChildren(AeCorrelationValidator.class);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.IAeCorrelationUser#isPatternRequired()
    */
   public boolean isPatternRequired()
   {
      return false;
   }

   /**
    * @return Returns the partnerLinkValidator.
    */
   protected AePartnerLinkValidator getPartnerLinkValidator()
   {
      return mPartnerLinkValidator;
   }

   /**
    * @param aPartnerLinkValidator The partnerLinkValidator to set.
    */
   protected void setPartnerLinkValidator(
         AePartnerLinkValidator aPartnerLinkValidator)
   {
      mPartnerLinkValidator = aPartnerLinkValidator;
   }

   /**
    * Gets the request map from the def
    */
   public AeMessagePartsMap getConsumerMessagePartsMap()
   {
      if (getDefinition() instanceof IAeMessageDataConsumerDef)
      {
         return ((IAeMessageDataConsumerDef)getDefinition()).getConsumerMessagePartsMap();
      }
      return null;
   }
   
   /**
    * Gets the response map from the def
    */
   public AeMessagePartsMap getProducerMessagePartsMap()
   {
      if (getDefinition() instanceof IAeMessageDataProducerDef)
      {
         return ((IAeMessageDataProducerDef)getDefinition()).getProducerMessagePartsMap();
      }
      return null;
   }
}
 
