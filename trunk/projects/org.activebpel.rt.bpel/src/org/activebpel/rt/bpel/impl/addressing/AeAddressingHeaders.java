//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/addressing/AeAddressingHeaders.java,v 1.9 2007/05/11 15:20:4
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
package org.activebpel.rt.bpel.impl.addressing;

import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAePolicyConstants;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeEndpointReference;
import org.activebpel.rt.bpel.impl.AeEndpointReference;
import org.activebpel.rt.bpel.impl.endpoints.AeEndpointFactory;
import org.activebpel.rt.bpel.impl.endpoints.IAeEndpointFactory;
import org.activebpel.rt.util.AeSOAPElementUtil;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.wsio.AeWsAddressingException;
import org.activebpel.wsio.AeWsAddressingHeaders;
import org.activebpel.wsio.IAeWebServiceEndpointReference;
import org.activebpel.wsio.IAeWsAddressingConstants;
import org.activebpel.wsio.IAeWsAddressingHeaders;
import org.w3c.dom.Element;

/**
 *  Holder for the values from a set of WS-Addressing Headers
 */
public class AeAddressingHeaders extends AeWsAddressingHeaders implements IAeAddressingHeaders
{
   /** factory that gives us a means to parse endpoint references from xml */
   private static IAeEndpointFactory sEndpointFactory = new AeEndpointFactory();
   
   /**
    * Constructor 
    * @param aNamespace WS-Addressing namespace URI for this instance
    */
   public AeAddressingHeaders(String aNamespace)
   {
      super(aNamespace);
   }
   
   /**
    * Copy constructor
    * @param aHeaders
    */
   public AeAddressingHeaders(IAeWsAddressingHeaders aHeaders)
   {
      super(aHeaders.getSourceNamespace());
      this.setAction(aHeaders.getAction());
      this.setConversationId(aHeaders.getConversationId());
      this.setFaultTo(aHeaders.getFaultTo());
      this.setFrom(aHeaders.getFrom());
      this.setMessageId(aHeaders.getMessageId());
      this.setRecipient(aHeaders.getRecipient());
      this.setRelatesTo(aHeaders.getRelatesTo());
      this.setReplyTo(aHeaders.getReplyTo());
      this.setTo(aHeaders.getTo());
      try
      {
         this.setReferenceProperties(aHeaders.getReferenceProperties());
      }
      catch (AeWsAddressingException ex)
      {
         AeException.logError(ex);
      }
   }
   
   /**
    * Converts headers to an instance of this class
    * @param aHeaders
    * @return converted headers
    */
   public static AeAddressingHeaders convert(IAeWsAddressingHeaders aHeaders)
   {
      if (aHeaders instanceof AeAddressingHeaders)
      {
         return (AeAddressingHeaders) aHeaders;
      }
      else
      {
         return new AeAddressingHeaders(aHeaders);
      }
   }

   /**
    * Adds a WS-Addressing header element to this instance
    * If the element is a WS-Addressing Header, the element will set the appropriate member variable
    * Adding headers this way prevents accidentally duplicating an addressing header if someone 
    * adds, for example, a ReplyTo element to reference properties
    * 
    * @see org.activebpel.rt.bpel.impl.addressing.IAeAddressingHeaders#addHeaderElement(org.w3c.dom.Element)
    */
   public void addHeaderElement(Element aElement) throws AeWsAddressingException
   {
      try
      {
         if (IAePolicyConstants.CONVERSATION_ID_HEADER.getLocalPart().equals(aElement.getLocalName()))
         {
            if (!aElement.hasChildNodes())
               throw new AeWsAddressingException(AeMessages.format("AeWsAddressingHeaders.2", aElement.getLocalName())); //$NON-NLS-1$
            setConversationId(aElement.getFirstChild().getNodeValue());
         }
         else if (IAeAddressingHeaders.WSA_TO.equals(aElement.getLocalName()))
         {
            if (!aElement.hasChildNodes())
            {
               setTo(""); //$NON-NLS-1$
            }
            else
            {
               setTo(aElement.getFirstChild().getNodeValue());
            }
         }
         else if (IAeAddressingHeaders.WSA_FROM.equals(aElement.getLocalName()))
            setFrom(parseEndpoint(aElement));
         else if (IAeAddressingHeaders.WSA_RECIPIENT.equals(aElement.getLocalName()))
            setRecipient(parseEndpoint(aElement));
         else if (IAeAddressingHeaders.WSA_REPLY_TO.equals(aElement.getLocalName()))
            setReplyTo(parseEndpoint(aElement));
         else if (IAeAddressingHeaders.WSA_FAULT_TO.equals(aElement.getLocalName()))
            setFaultTo(parseEndpoint(aElement));
         else if (IAeAddressingHeaders.WSA_ACTION.equals(aElement.getLocalName()))
         {
            setAction(AeXmlUtil.getText(aElement));
         }
         else if (IAeAddressingHeaders.WSA_MESSAGE_ID.equals(aElement.getLocalName()))
         {
            setMessageId(AeXmlUtil.getText(aElement));
         }
         else if (IAeAddressingHeaders.WSA_RELATES_TO.equals(aElement.getLocalName()))
         {
            addRelatesTo(AeXmlUtil.getText(aElement), new QName(getSourceNamespace(), IAeWsAddressingConstants.WSA_REPLY_RELATION)); 
         }
         else
         {
            Element element = AeSOAPElementUtil.convertToDOM(aElement);
            getReferenceProperties().add(element);
         }
      }
      catch (AeBusinessProcessException bpe)
      {
         throw new AeWsAddressingException(AeMessages.getString("AeWsAddressingHeaders.0"), bpe);  //$NON-NLS-1$
      }
   }
   
   /**
    * Sets the wsa:ReplyTo endpoint. Mandatory wsa:MessageID for this request is generated if not already set. 
    * @see org.activebpel.wsio.IAeWsAddressingHeaders#setReplyTo(org.activebpel.wsio.IAeWebServiceEndpointReference)
    */
   public void setReplyTo(IAeWebServiceEndpointReference aReplyTo)
   {
      super.setReplyTo(aReplyTo);
      // MessageID is mandatory with a ReplyTo
      if (aReplyTo != null)
      {
         if (getMessageId() == null)
         {
            setMessageId(AeUtil.getNewUUID());
         }
      }
   }
   
   /**
    * Parses an endpoint from the element passed in. 
    * @param aElement
    */
   private IAeEndpointReference parseEndpoint(Element aElement) throws AeBusinessProcessException
   {
      // use endpoint factory to deserialize from element
      IAeEndpointReference ref = null;
      // copy all of the child nodes over to the new endpoint ref element
      try
      {
         ref = sEndpointFactory.getDeserializer(aElement.getNamespaceURI()).deserializeEndpoint(aElement);
      }
      catch (AeException e)
      {
         throw new AeBusinessProcessException(AeMessages.getString("AeWsAddressingHeaders.1"), e); //$NON-NLS-1$
      }
      return ref;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.addressing.IAeAddressingHeaders#getTo()
    */
   public String getTo()
   {
      IAeWebServiceEndpointReference epr = getRecipient();
      if (epr != null)
         return epr.getAddress();
      else
         return null;
   }

   /**
    * Sets the address of the intended recipient.
    * @see org.activebpel.wsio.IAeWsAddressingHeaders#setTo(java.lang.String)
    */
   public void setTo(String aTo)
   {
      AeEndpointReference epr = new AeEndpointReference();
      if (getRecipient() != null)
         epr.setReferenceData(getRecipient());
      epr.setAddress(aTo);   
      setRecipient(epr);
   }

   /**
    * Sets the list of additional headers to be included when serializing to a SOAP Envelope
    * If the list contains any embedded WS-Addressing headers, these will be used to set the 
    * appropriate member value of this instance 
    *  
    * @see org.activebpel.rt.bpel.impl.addressing.IAeAddressingHeaders#setReferenceProperties(java.util.List)
    */
   public void setReferenceProperties(List aElementList) throws AeWsAddressingException
   {
      // clear old stuff
      List props = getReferenceProperties();
      props.clear();

      // Just clearing out old properties
      if (AeUtil.isNullOrEmpty(aElementList))
         return;

      try
      {
         // Add all the properties
         for (Iterator it = aElementList.iterator(); it.hasNext();)
         {
            // This method figures out if an element is an 
            // embedded WSA header
            addHeaderElement((Element) it.next());
         }
      }
      catch (Exception e)
      {
         throw new AeWsAddressingException(AeMessages.getString("AeAddressingHeaders.0"), e); //$NON-NLS-1$
      }
   }
}
