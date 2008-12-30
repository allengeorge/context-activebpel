// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/AeMessageQueue.java,v 1.4 2007/05/01 17:37:1
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
package org.activebpel.rt.bpel.server.engine; 

import java.rmi.RemoteException;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.AeDataConverter;
import org.activebpel.rt.bpel.impl.activity.support.AeFault;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.wsio.IAeWebServiceMessageData;
import org.activebpel.wsio.IAeWebServiceResponse;
import org.activebpel.wsio.receive.AeRequestException;
import org.activebpel.wsio.receive.IAeMessageContext;
import org.activebpel.wsio.receive.IAeMessageQueue;
import org.w3c.dom.Document;

/**
 * Provides a method for dispatching messages into the engine. 
 */
public class AeMessageQueue implements IAeMessageQueue
{
   private static final AeMessageQueue INSTANCE = new AeMessageQueue();
   
   /**
    * getter for the singleton
    */
   public static IAeMessageQueue getInstance()
   {
      return INSTANCE;
   }
   
   /**
    * private ctor to force singleton usage 
    */
   private AeMessageQueue()
   {
   }

   /**
    * @see org.activebpel.wsio.receive.IAeMessageQueue#queueInvokeData(long, java.lang.String, org.activebpel.wsio.IAeWebServiceMessageData, java.util.Map)
    * @deprecated use org.activebpel.wsio.receive.IAeMessageQueue#queueInvokeData(long, java.lang.String, long, org.activebpel.wsio.IAeWebServiceMessageData, java.util.Map)
    */
   public void queueInvokeData(long aProcessId, String aLocationPath,
         IAeWebServiceMessageData aMessageData, Map aProcessProperties)
         throws RemoteException, AeRequestException
   {
      queueInvokeData(aProcessId, aLocationPath, 0, aMessageData, aProcessProperties);
   }
   
   /**
    * @see org.activebpel.wsio.receive.IAeMessageQueue#queueInvokeData(long, java.lang.String, long, org.activebpel.wsio.IAeWebServiceMessageData, java.util.Map)
    */
   public void queueInvokeData(long aProcessId, String aLocationPath, long aTransmissionId,
         IAeWebServiceMessageData aMessageData, Map aProcessProperties)
         throws RemoteException, AeRequestException
   {   
      try
      {        
         IAeMessageData data = AeDataConverter.convert(aMessageData);
         AeEngineFactory.getEngine().queueInvokeData(aProcessId, aLocationPath, aTransmissionId, data, aProcessProperties);
      }
      catch (AeBusinessProcessException e)
      {
         throw new AeRequestException(e.getMessage());
      }
   }
   
   /**
    * @see org.activebpel.wsio.receive.IAeMessageQueue#queueInvokeFault(long, java.lang.String, javax.xml.namespace.QName, org.activebpel.wsio.IAeWebServiceMessageData, java.util.Map)
    * @deprecated use org.activebpel.wsio.receive.IAeMessageQueue#queueInvokeFault(long, java.lang.String, long, javax.xml.namespace.QName, org.activebpel.wsio.IAeWebServiceMessageData, java.util.Map) 
    */ 
   public void queueInvokeFault(long aProcessId, String aLocationPath,
         QName aFaultName, IAeWebServiceMessageData aFaultData,
         Map aProcessProperties) throws RemoteException, AeRequestException
   {
      queueInvokeFault(aProcessId, aLocationPath, 0, aFaultName, aFaultData, aProcessProperties);
   }
   
   /**
    * @see org.activebpel.wsio.receive.IAeMessageQueue#queueInvokeFault(long, java.lang.String, long, javax.xml.namespace.QName, org.activebpel.wsio.IAeWebServiceMessageData, java.util.Map)
    */
   public void queueInvokeFault(long aProcessId, String aLocationPath, long aTransmissionId,
         QName aFaultName, IAeWebServiceMessageData aFaultData,
         Map aProcessProperties) throws RemoteException, AeRequestException
   {   
      try
      {
//       AeDataConverter.convert handles nulls so no need to check here
         IAeMessageData data = AeDataConverter.convert(aFaultData);
         AeFault fault = new AeFault( aFaultName, data );
         
         AeEngineFactory.getEngine().queueInvokeFault(aProcessId, aLocationPath,  aTransmissionId, fault, aProcessProperties);
      }
      catch (AeBusinessProcessException e)
      {
         throw new AeRequestException(e.getMessage());
      }
   }

   /**
    * @see org.activebpel.wsio.receive.IAeMessageQueue#queueReceiveData(org.activebpel.wsio.receive.IAeMessageContext, org.w3c.dom.Document[])
    */
   public IAeWebServiceResponse queueReceiveData(IAeMessageContext aContext,
         Document[] aDocArray) throws RemoteException, AeRequestException
   {
      try
      {
         return AeEngineFactory.getEngine().queueReceiveData(aContext, aDocArray);         
      }
      catch(AeException e)
      {
         AeException.logError(e, e.getMessage());
         throw new AeRequestException(e.getMessage());
      }
   }
   
   /**
    * @see org.activebpel.wsio.receive.IAeMessageQueue#queueReceiveData(org.activebpel.wsio.IAeWebServiceMessageData, org.activebpel.wsio.receive.IAeMessageContext)
    */
   public IAeWebServiceResponse queueReceiveData(
         IAeWebServiceMessageData aData, IAeMessageContext aContext)
         throws RemoteException, AeRequestException
   {
      try
      {
         return AeEngineFactory.getEngine().queueReceiveData(aContext, aData);         
      }
      catch(AeBusinessProcessException e)
      {
         AeException.logError(e, e.getMessage());
         throw new AeRequestException(e.getMessage());
      }
   }
}
 
