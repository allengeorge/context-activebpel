//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/receive/IAeBPELReceiveHandler.java,v 1.1 2007/01/26 22:54:0
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
package org.activebpel.rt.bpel.server.engine.receive;


import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeProcessPlan;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.wsio.IAeWebServiceMessageData;
import org.activebpel.wsio.IAeWebServiceResponse;
import org.activebpel.wsio.receive.AeRequestException;
import org.w3c.dom.Document;

/**
 * Interface detailing the abstract methods required of pluggable receive handlers
 */
public interface IAeBPELReceiveHandler
{
   /**
    * Maps element data from a request into a AeMessageData object using
    * information in the process plan and message context. 
    * 
    * @param aPlan process plan for service
    * @param aContext MessageContext
    * @param aData Raw Input data
    * @return input converted to AeMessageData 
    * @throws AeRequestException
    */
   public IAeMessageData mapInputData(IAeProcessPlan aPlan, AeExtendedMessageContext aContext, IAeWebServiceMessageData aData) throws AeBusinessProcessException;
   
   /**
    * Maps element data from a request into a AeMessageData object using
    * information in the process plan and message context. 
    * 
    * @param aPlan process plan for service
    * @param aContext MessageContext
    * @param aDocArray array of documents containing raw request data
    * @return AeMessageData mapped from input documents
    * @throws AeRequestException
    */
   public IAeMessageData mapInputData(IAeProcessPlan aPlan, AeExtendedMessageContext aContext, Document[] aDocArray) throws AeBusinessProcessException;
   
   /**
    * Maps the output data from the BPEL engine to a protocol-specific response
    * 
    * @param aContext the message context which contains the RPC objects
    * @param aOutputMsg the output message from the BPEL engine
    * @throws AeBusinessProcessException
    */
   public IAeWebServiceResponse mapOutputData(IAeExtendedMessageContext aContext, IAeWebServiceResponse aResponse) throws AeBusinessProcessException; 
   
   /**
    * Maps fault data from the BPEL engine to a protocol-specific response
    * 
    * @param aContext
    * @param aResponse
    */
   public IAeWebServiceResponse mapFaultData(IAeExtendedMessageContext aContext, IAeWebServiceResponse aResponse) throws AeBusinessProcessException;
   
   /**
    * Performs upfront validation of input data according to the expectations of the receive handler
    * Method should throw an AeBusinessProcessException if it finds a problem with the input
    * 
    * @param aPlan
    * @param aContext
    * @param aDocArray
    * @throws AeBusinessProcessException
    */
   public void validateInputData(IAeProcessPlan aPlan, IAeExtendedMessageContext aContext, Document[] aDocArray) throws AeBusinessProcessException;

   /**
    * Performs upfront validation of input data according to the expectations of the receive handler
    * Method should throw an AeBusinessProcessException if it finds a problem with the input
    * 
    * @param aPlan
    * @param aContext
    * @param aData
    * @throws AeBusinessProcessException
    */
   public void validateInputData(IAeProcessPlan aPlan, IAeExtendedMessageContext aContext, IAeWebServiceMessageData aData) throws AeBusinessProcessException;
}
