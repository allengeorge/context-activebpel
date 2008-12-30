//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/invokers/AeSOAPInvoker.java,v 1.6 2007/06/08 03:35:3
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
package org.activebpel.rt.axis.bpel.invokers; 

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.wsdl.Part;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.activebpel.rt.AeException;
import org.activebpel.rt.axis.bpel.AeMessages;
import org.activebpel.rt.axis.bpel.handlers.AeAttachmentUtil;
import org.activebpel.rt.bpel.impl.AeSOAPMessageFactory;
import org.activebpel.rt.util.AeCloser;
import org.activebpel.wsio.AeWebServiceMessageData;
import org.activebpel.wsio.IAeWebServiceAttachment;
import org.activebpel.wsio.IAeWebServiceMessageData;
import org.apache.axis.attachments.AttachmentPart;
import org.apache.axis.message.SOAPHeaderElement;
import org.w3c.dom.Document;

/**
 * Base class for RPC and DOC invokers. 
 */
public abstract class AeSOAPInvoker implements IAeInvoker
{

   
   /**
    * Extracts message parts from the output message 
    * @param aContext
    * @param outputMsg
    * @throws AeException
    */
   protected void extractPartsFromHeader(AeInvokeContext aContext, AeWebServiceMessageData outputMsg) throws AeException
   {
      try
      {
         for (Iterator iter = aContext.getOutputHeaderParts().iterator(); iter.hasNext();)
         {
            String partName = (String) iter.next();
            Part part = aContext.getOperation().getOutput().getMessage().getPart(partName);
            QName elementQName = part.getElementName();
            if (elementQName != null)
            {
               for(Iterator it = aContext.getCall().getResponseMessage().getSOAPHeader().examineAllHeaderElements(); it.hasNext(); )
               {
                  SOAPHeaderElement headerElement = (SOAPHeaderElement) it.next();
                  if (headerElement.getQName().equals(elementQName))
                  {
                     Document doc = headerElement.getAsDOM().getOwnerDocument();
                     outputMsg.setData(part.getName(), doc);
                     break;
                  }
               }
            }
         }
      }
      catch (Exception e)
      {
         throw new AeException(e.getLocalizedMessage(), e);
      }
   }
   
   /**
    * Creates the container for the response or null if it's a one-way
    * @param aContext
    */
   protected AeWebServiceMessageData createOutputMessageData(AeInvokeContext aContext)
   {
      if (!aContext.getInvoke().isOneWay())
      {
         QName outMsgQName = aContext.getOperation().getOutput().getMessage().getQName();
         return new AeWebServiceMessageData(outMsgQName); 
      }
      return null;
   }
   
   /**
    * Returns the map of message parts
    * 
    * @param aContext
    * @return
    * @throws AeException
    */
   protected Map getMessageData(AeInvokeContext aContext) throws AeException
   {
      return aContext.getInvoke().getInputMessageData().getMessageData();
   }
   
   /**
    * Adds attachments to the invoke context soap message for delivery
    * 
    * @param aInvokeContext
    * @param call
    * @param attachments
    * @return List of attachments added
    * @throws AeException
    */
   protected List addAttachments(AeInvokeContext aInvokeContext) throws AeException
   {
      IAeWebServiceMessageData inputMessageData = aInvokeContext.getInvoke().getInputMessageData();

      List attachments = inputMessageData.getAttachments();
      
      
      if (attachments != null)
      {
         SOAPMessage msg;
         try
         {
            msg = AeSOAPMessageFactory.getSOAPMessageFactory().createMessage();
         }
         catch (SOAPException ex1)
         {
           throw new AeException(ex1);
         }
         
         for (Iterator itr = attachments.iterator();itr.hasNext();)
         {
            Object attachment = itr.next();
            if(!(attachment instanceof IAeWebServiceAttachment))
            {
               new AeException(AeMessages.getString("AeSOAPInvoker.ERROR_1")); //$NON-NLS-1$
            }
            DataHandler dh = new DataHandler(new AeAttachmentDataSource((IAeWebServiceAttachment)attachment));
            AttachmentPart ap = (AttachmentPart)msg.createAttachmentPart(dh);
            ap.setContentId(((IAeWebServiceAttachment)attachment).getContentId());
            aInvokeContext.getCall().addAttachmentPart(ap);
         }
      }
      
      return attachments;
   }
   
   /**
    * Close attachment streams
    * 
    * @throws AeException
    */
   protected void closeAttachmentStreams(List aAttachments)
   {
      if(aAttachments != null)
      {
         // close attachment streams of the message sent.
         for (Iterator itr = aAttachments.iterator();itr.hasNext();)
         {
            AeCloser.close(((IAeWebServiceAttachment)itr.next()).getContent()); 
         }  
      }
   }
   
   /**
    * Populates the response message with attachments received in the response soap message
    * 
    * @param aInvokeContext
    * @param responseMessage
    * @throws AeException
    */
   protected void receiveAttachments(AeInvokeContext aInvokeContext, AeWebServiceMessageData responseMessage) throws AeException
   {
      try
      {
         if(responseMessage == null) return;
         
         // get the attachments of the response message
         responseMessage.setAttachments(AeAttachmentUtil.soap2wsioAttachments(aInvokeContext.getCall().getResponseMessage()));
      }
      catch (Exception ex1)
      {
        throw new AeException(ex1);
      }
   }
}
 
