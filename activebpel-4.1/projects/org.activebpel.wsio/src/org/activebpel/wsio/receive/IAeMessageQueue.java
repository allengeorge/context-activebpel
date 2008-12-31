//$Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/receive/IAeMessageQueue.java,v 1.6 2006/07/25 17:54:1
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
package org.activebpel.wsio.receive; 

import java.rmi.RemoteException;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.wsio.IAeWebServiceMessageData;
import org.activebpel.wsio.IAeWebServiceResponse;
import org.w3c.dom.Document;

/**
 * Provides an interface for sending messages to the engine.
 */
public interface IAeMessageQueue
{
   /**
    * Delivers the message to the BPEL engine's message queue.
    * 
    * @param aData
    * @param aContext
    * @return IAeWebServiceResponse
    * @throws RemoteException
    * @throws AeRequestException
    */
   public IAeWebServiceResponse queueReceiveData(IAeWebServiceMessageData aData, IAeMessageContext aContext)
      throws RemoteException, AeRequestException;
   
   /**
    * Specialized version of queueReceiveData that accepts xml documents in lieu 
    * of our message container. The context must specify the full routing 
    * information of process qname and partner link OR specify only the service 
    * name. In either case, the missing information will be determined by 
    * introspecting the wsdl and matching the data to an operation.
    * 
    * @param aContext must contain the process qname & partner link OR the service name, never both
    * @param aDocArray array of xml documents that make up the message parts for the operaiton invoked
    * @throws RemoteException
    * @throws AeRequestException
    */
   public IAeWebServiceResponse queueReceiveData(IAeMessageContext aContext, Document[] aDocArray) 
      throws RemoteException, AeRequestException;
   
   /**
    * Allows an externally invoked operation data to dispatch to a queued invoke.
    * @param aProcessId The id of the process expecting the response from the invoke
    * @param aLocationPath The path to the location awaiting the response
    * @param aMessageData The data we have received from invoke.
    * @param aProcessProperties Any string name/value pairs we received back from the invoke.
    * @throws AeBusinessProcessException Thrown if error occurs setting the receiver.
    * @deprecated Use org.activebpel.wsio.receive.IAeMessageQueue#queueInvokeData(long, java.lang.String, long, org.activebpel.wsio.IAeWebServiceMessageData, java.util.Map)
    */
   public void queueInvokeData(long aProcessId, String aLocationPath, IAeWebServiceMessageData aMessageData, Map aProcessProperties )
         throws RemoteException, AeRequestException;

   /**
    * Allows an externally invoked operation data to dispatch to a queued invoke.
    * @param aProcessId The id of the process expecting the response from the invoke
    * @param aLocationPath The path to the location awaiting the response
    * @param aTransmissionId The invoke's execution instance transmission id.
    * @param aMessageData The data we have received from invoke.
    * @param aProcessProperties Any string name/value pairs we received back from the invoke.
    * @throws AeBusinessProcessException Thrown if error occurs setting the receiver.
    */
   public void queueInvokeData(long aProcessId, String aLocationPath, long aTransmissionId, IAeWebServiceMessageData aMessageData, Map aProcessProperties )
         throws RemoteException, AeRequestException;
   
   /**
    * Allows an externally invoked operation fault to dispatch to a queued invoke.
    * @param aProcessId The process that's expecting the invoke response
    * @param aLocationPath The path to the location awaiting the response
    * @param aFaultName The fault name we received from invoke or null if not available
    * @param aFaultData The fault data we have received from invoke or null if not available
    * @param aProcessProperties Any string name/value pairs we received back from the invoke.
    * @throws AeBusinessProcessException Thrown if error occurs setting the receiver.
    * @deprecated Use org.activebpel.wsio.receive.IAeMessageQueue#queueInvokeFault(long, java.lang.String, long, javax.xml.namespace.QName, org.activebpel.wsio.IAeWebServiceMessageData, java.util.Map)
    */
   public void queueInvokeFault(long aProcessId, String aLocationPath, QName aFaultName, IAeWebServiceMessageData aFaultData, Map aProcessProperties)
         throws RemoteException, AeRequestException;

   /**
    * Allows an externally invoked operation fault to dispatch to a queued invoke.
    * @param aProcessId The process that's expecting the invoke response
    * @param aLocationPath The path to the location awaiting the response
    * @param aTransmissionId The invoke's execution instance transmission id. 
    * @param aFaultName The fault name we received from invoke or null if not available
    * @param aFaultData The fault data we have received from invoke or null if not available
    * @param aProcessProperties Any string name/value pairs we received back from the invoke.
    * @throws AeBusinessProcessException Thrown if error occurs setting the receiver.
    */
   public void queueInvokeFault(long aProcessId, String aLocationPath, long aTransmissionId, QName aFaultName, IAeWebServiceMessageData aFaultData, Map aProcessProperties)
      throws RemoteException, AeRequestException;
   
}
 
