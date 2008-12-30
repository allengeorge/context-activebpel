//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/invokers/AeDocumentStyleInvoker.java,v 1.6 2007/05/01 17:33:3
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.axis.bpel.AeMessages;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.wsio.AeWebServiceMessageData;
import org.apache.axis.message.SOAPBodyElement;
import org.apache.axis.message.SOAPHeaderElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import javax.wsdl.Part;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Calls a document style endpoint.
 */
public class AeDocumentStyleInvoker extends AeSOAPInvoker
{

   
   /**
    * @see org.activebpel.rt.axis.bpel.invokers.IAeInvoker#invoke(org.activebpel.rt.axis.bpel.invokers.AeInvokeContext)
    */
   public void invoke( AeInvokeContext aContext ) throws AeException, RemoteException
   {
      invokeDocumentCall( aContext );
   }
   
   /**
    * Calls a document style endpoint on behalf of passed invoke context.
    * 
    * @param aInvokeContext
    * @throws AeException
    * @throws RemoteException
    */
   protected void invokeDocumentCall( AeInvokeContext aInvokeContext ) throws AeException, RemoteException
   {
      // this document gets used to create element placeholders for the arguments
      // if they're rpc style and not already documents
      Document simpleTypeDoc = null;
      
      List orderedParts = aInvokeContext.getOperation().getInput().getMessage().getOrderedParts(null);
      
      ArrayList list = new ArrayList();
      Map messageData = getMessageData(aInvokeContext);
      List outboundAttachments = addAttachments(aInvokeContext);
      AeWebServiceMessageData outputMsg;
      Vector elems;
      try
      {
         for (Iterator iter = orderedParts.iterator(); iter.hasNext();)
         {
            Part part = (Part) iter.next();
            Object obj = messageData.get(part.getName());
            
            // don't add the part to the body if it is supposed to be a header
            if (aInvokeContext.isInputHeader(part.getName()) && obj instanceof Document)
            {
                Document doc = (Document) obj;
                aInvokeContext.getCall().addHeader(new SOAPHeaderElement(doc.getDocumentElement()));
            }
            else
            {
               if (obj instanceof Document)
               {
                  Element root = ((Document) obj).getDocumentElement();
                  list.add(new SOAPBodyElement(root));
               }
               else
               {
                  if (simpleTypeDoc == null)
                  {
                     simpleTypeDoc = AeXmlUtil.newDocument();
                  }
                  Element e = simpleTypeDoc.createElement(part.getName());
                  e.appendChild(simpleTypeDoc.createTextNode(obj.toString()));
                  SOAPBodyElement body = new SOAPBodyElement(e);
                  list.add(body);
               }
            }
         }
         
         // for document style we receive a vector of body elements from return
         elems = (Vector) aInvokeContext.getCall().invoke(list.toArray());
         
         // outputMsg will be created for request/response only
         outputMsg = createOutputMessageData(aInvokeContext);
      }
      finally
      {
         closeAttachmentStreams(outboundAttachments);
      }
      
      receiveAttachments(aInvokeContext,outputMsg);
      
   
      // if we got a return and expect one then process the body elements
      if (!aInvokeContext.getInvoke().isOneWay())
      {
         if (elems != null)
         {
            int i = 0;               
            for(Iterator iter = aInvokeContext.getOperation().getOutput().getMessage().getOrderedParts(null).iterator(); iter.hasNext(); )
            {
               Part part = (Part)iter.next();
               if (!aInvokeContext.isOutputHeader(part.getName()))
               {
                  SOAPBodyElement elem = (SOAPBodyElement) elems.get(i++);
                  Document doc;
                  try
                  {
                     doc = elem.getAsDocument();
                  }
                  catch (Exception ex)
                  {
                     throw new AeBusinessProcessException(AeMessages.getString("AeInvokeHandler.ERROR_1"), ex); //$NON-NLS-1$
                  }
                  Element root = doc.getDocumentElement();
                  if(root != null)
                  {
                     if(isSimpleType(root))
                        outputMsg.setData(part.getName(), AeXmlUtil.getText(root));
                     else
                        /**
                         * Since I'm dealing with doc-lit wrapped, there will only be a single part: the wrapper element
                         */
                        outputMsg.setData(part.getName(), doc);
                  }
                  else
                  {
                     outputMsg.setData(part.getName(), null);
                  }
               }
            }
         }
         
         extractPartsFromHeader(aInvokeContext, outputMsg);
      }
      

      // Return the message to the awaiting callback 
      aInvokeContext.getResponse().setMessageData( outputMsg );
   }
   
   /**
    * Helper method that checks to see if the passed elements data is a simple type
    * or complex type.
    * @param aElement The element to check the contents of.
    */
   protected boolean isSimpleType(Element aElement)
   {
      boolean simple = false;
      // TODO Simple check for now, a complex type will have attributes and/or child elements.
      if(AeUtil.isNullOrEmpty(aElement.getNamespaceURI()) && AeXmlUtil.getFirstSubElement(aElement) == null)
      {
         simple = true;
         if(aElement.hasAttributes())
         {
            NamedNodeMap attrs = aElement.getAttributes();
            for(int i=0; i < attrs.getLength(); ++i)
            {
               String nsURI = attrs.item(i).getNamespaceURI();
               if(! IAeBPELConstants.W3C_XMLNS.equals(nsURI) &&
                  ! IAeBPELConstants.W3C_XML_SCHEMA_INSTANCE.equals(nsURI))
               {
                  simple = false;
                  break;
               }
            }
         }
      }
      return simple;
   }
}
