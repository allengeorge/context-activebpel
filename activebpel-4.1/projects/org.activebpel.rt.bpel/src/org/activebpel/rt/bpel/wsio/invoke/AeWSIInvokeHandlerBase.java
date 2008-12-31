//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/wsio/invoke/AeWSIInvokeHandlerBase.java,v 1.4 2007/09/07 13:40:5
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
package org.activebpel.rt.bpel.wsio.invoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.impl.AeDataConverter;
import org.activebpel.rt.bpel.impl.AeFaultFactory;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.wsio.AeWebServiceMessageData;
import org.activebpel.wsio.IAeWebServiceMessageData;
import org.activebpel.wsio.IAeWebServiceResponse;
import org.activebpel.wsio.invoke.AeInvokeResponse;
import org.activebpel.wsio.invoke.IAeInvoke;
import org.activebpel.wsio.invoke.IAeInvokeHandler;
import org.w3c.dom.Document;

/**
 * Base class for custom invoke handlers that handle invokes to WSI compliant
 * services. This base class expects a single part input message. The operation
 * name is used to look up a method that implements that operation (using
 * reflection).
 */
public abstract class AeWSIInvokeHandlerBase implements IAeInvokeHandler
{
   /**
    * Default c'tor.
    */
   public AeWSIInvokeHandlerBase()
   {
   }

   /**
    * @see org.activebpel.wsio.invoke.IAeInvokeHandler#handleInvoke(org.activebpel.wsio.invoke.IAeInvoke, java.lang.String)
    */
   public IAeWebServiceResponse handleInvoke(IAeInvoke aInvoke, String aQueryData)
   {
      try
      {
         IAeWebServiceMessageData msgData = aInvoke.getInputMessageData();
         String aOperation = aInvoke.getOperation();
         return invokeOperation(aOperation, msgData);
      }
      catch (Exception e)
      {
         AeException.logError(e);
         AeInvokeResponse response = new AeInvokeResponse();
         IAeFault fault = AeFaultFactory.getSystemErrorFault(e);
         response.setFaultData(fault.getFaultName(), AeDataConverter.convertFaultDataNoException(fault));
         return response;
      }
   }

   /**
    * Invokes the given web service operation.
    *
    * @param aOperation
    * @param aMessageData
    */
   protected IAeWebServiceResponse invokeOperation(String aOperation, IAeWebServiceMessageData aMessageData) throws UnsupportedOperationException
   {
      AeInvokeResponse response = new AeInvokeResponse();
      Throwable throwable = null;
      try
      {
         Method method = getClass().getMethod(aOperation,
               new Class[] { IAeWebServiceMessageData.class, AeInvokeResponse.class });
         method.invoke(this, new Object[] { aMessageData, response });
      }
      catch (NoSuchMethodException nsme)
      {
         AeException.logError(nsme);
         throwable = new UnsupportedOperationException(AeMessages.getString("AeWSIInvokeHandlerBase.OperationNotSupportedError")); //$NON-NLS-1$
      }
      catch (InvocationTargetException ite)
      {
         throwable = ite.getTargetException();
      }
      catch (Throwable t)
      {
         throwable = t;
      }
      finally
      {
         // TODO (KR) Close all attachment streams here to avoid leaking temporary files.
      }

      if (throwable != null)
      {
         IAeFault fault = mapThrowableAsFault(response, throwable);
         response.setFaultData(fault.getFaultName(), AeDataConverter.convertFaultDataNoException(fault));
      }
      return response;
   }

   /**
    * Convenience method to compare the message type and throw an exception
    * if the message type does not match the expected type.
    * 
    * @param aMessageData
    * @param aExpectedType
    * @param aOperationName
    */
   protected void compareExpectedMessageType(IAeWebServiceMessageData aMessageData,
            QName aExpectedType, String aOperationName) throws AeBusinessProcessException
   {
      if (!AeUtil.compareObjects(aMessageData.getMessageType(), aExpectedType))
      {
         throw new AeBusinessProcessException(AeMessages.format(
               "AeWSIInvokeHandler.MessageDataTypeError",  //$NON-NLS-1$
               new Object[] { aOperationName, aExpectedType, aMessageData.getMessageType() }));
      }
   }

   /**
    * Maps an error as a fault.
    * 
    * @param aThrowable
    */
   protected IAeFault mapThrowableAsFault(AeInvokeResponse aResponse, Throwable aThrowable)
   {
      return AeFaultFactory.getSystemErrorFault(aThrowable);
   }

   /**
    * Convenience method to create a AeWebServiceMessageData given the message type.
    * @param aOutputMessageType
    * @return AeWebServiceMessageData
    */
   protected AeWebServiceMessageData createWebServiceMessageData(QName aOutputMessageType)
   {
      Map data = new HashMap();
      AeWebServiceMessageData respMsgData = new AeWebServiceMessageData(aOutputMessageType, data);
      return respMsgData;
   }
   
   /**
    * Convenience method to serialize and set the output data in the response.
    * 
    * @param aResponse wsio response
    * @param aOutputMessageType output message type qname
    * @param aPartName message data part
    * @param aData the message data
    * @throws Exception Errors due to serialization.
    */
   protected void setResponseData(AeInvokeResponse aResponse, QName aOutputMessageType, String aPartName, 
            Document aData) throws Exception
   {
      AeWebServiceMessageData respMsgData = createWebServiceMessageData(aOutputMessageType);
      aResponse.setMessageData(respMsgData);
      respMsgData.getMessageData().put(aPartName, aData);
   }
}
