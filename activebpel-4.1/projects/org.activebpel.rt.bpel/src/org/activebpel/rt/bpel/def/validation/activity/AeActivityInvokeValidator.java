//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityInvokeValidator.java,v 1.5 2007/04/05 01:39:3
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

import org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.util.AeUtil;

/**
 * model provides validation for the invoke activity
 */
public class AeActivityInvokeValidator extends AeWSIOActivityValidator
{
   /** variable used for the invoke's request data */
   private AeVariableValidator mInputVariable;
   /** variable used for the invoke's response data */
   private AeVariableValidator mOutputVariable;

   /**
    * ctor
    * @param aDef
    */
   public AeActivityInvokeValidator(AeActivityInvokeDef aDef)
   {
      super(aDef);
   }
   
   /**
    * validates:
    * 1. input variable exists
    * 2. output variable, if defined, exists
    * @see org.activebpel.rt.bpel.def.validation.activity.AeWSIOActivityValidator#validate()
    */
   public void validate()
   {
      mInputVariable = getVariableValidator(getDef().getInputVariable(), null, true);
      mOutputVariable = getVariableValidator(getDef().getOutputVariable(), null, true);
      
      super.validate();

      AeMessagePartsMap producerMap = getProducerMessagePartsMap();
      if (producerMap == null)
      {
         // if the invoke is missing its parts map, then there are a two things that 
         // could be wrong
         // 1. bpws 1.1 and missing input variable or output variable, both of which are used
         //    to identify the operation being invoked
         // 2. problem in resolving plink, port type, or operation
         //
         // 
         // This checks for case 1, an empty producer strategy means invalid mix of input/output/fromPart/toPart
         if (AeUtil.isNullOrEmpty(getDef().getMessageDataProducerStrategy()))
         {
            // use this call to report the error (missing input or output variable)
            validateMessageProducerStrategy(producerMap, mInputVariable);
         }
         else
         {
            // if the port type is null then we'll report an error on the plink validator
            // otherwise, report an error that we couldn't find an operation with the given input and output data
            if (findPortType() != null)
            {
               QName inputType = mInputVariable != null ? mInputVariable.getType() : EMPTY_QNAME;
               QName outputType = mOutputVariable != null ? mOutputVariable.getType() : EMPTY_QNAME;
               getReporter().addError( ERROR_OPERATION_INOUT_NOT_FOUND,
                     new String[] { getOperation(),
                                    inputType == null ? "" : getNSPrefix( inputType.getNamespaceURI()), //$NON-NLS-1$
                                    inputType == null ? "" : inputType.getLocalPart(), //$NON-NLS-1$
                                    outputType == null ? "" : getNSPrefix( outputType.getNamespaceURI()), //$NON-NLS-1$
                                    outputType == null ? "" : outputType.getLocalPart() //$NON-NLS-1$
                                    },
                     getDefinition());
            }
         }
      }
      else
      {
         validateMessageProducerStrategy(producerMap, mInputVariable);
         
         AeMessagePartsMap consumerMap = getConsumerMessagePartsMap();
         if (consumerMap != null)
         {
            validateMessageConsumerStrategy(consumerMap, mOutputVariable);
         }
      }
   }
   
   /**
    * Getter for the input variable
    */
   public AeVariableValidator getInputVariableModel()
   {
      return mInputVariable;
   }
   
   /**
    * Getter for the output variable
    */
   public AeVariableValidator getOutputVariableModel()
   {
      return mOutputVariable;
   }

   /**
    * Getter for the def
    */
   protected AeActivityInvokeDef getDef()
   {
      return (AeActivityInvokeDef) getDefinition();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAePartnerLinkOperationUser#isMyRole()
    */
   public boolean isMyRole()
   {
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAePartnerLinkOperationUser#isPartnerRole()
    */
   public boolean isPartnerRole()
   {
      return true;
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
    * @see org.activebpel.rt.bpel.def.validation.activity.IAeCorrelationUser#isPatternRequired()
    */
   public boolean isPatternRequired()
   {
      return true;
   }
}
 
