// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/addressing/AeWSAddressingSerializer.java,v 1.12 2007/05/11 15:20:4
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
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.impl.AeSOAPMessageFactory;
import org.activebpel.rt.bpel.impl.endpoints.AeEndpointFactory;
import org.activebpel.rt.bpel.impl.endpoints.IAeEndpointSerializer;
import org.activebpel.rt.util.AeSOAPElementUtil;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.wsio.IAeWebServiceEndpointReference;
import org.activebpel.wsio.IAeWsAddressingConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * WS-Addressing serializer to add addressing headers to a SOAPHeader element. 
 */
public class AeWSAddressingSerializer implements IAeAddressingSerializer
{
   /** singleton instance */
   private static final AeWSAddressingSerializer sSingleton = new AeWSAddressingSerializer();
   /** 2004 08 singleton instance */
   private static final AeWSAddressingSerializer sSingleton_2004_08 = new AeWSAddressingSerializer(IAeConstants.WSA_NAMESPACE_URI_2004_08);
   /** 2004 03 singleton instance */
   private static final AeWSAddressingSerializer sSingleton_2004_03 = new AeWSAddressingSerializer(IAeConstants.WSA_NAMESPACE_URI_2004_03);
   /** 2005 08 singleton instance */
   private static final AeWSAddressingSerializer sSingleton_2005_08 = new AeWSAddressingSerializer(IAeConstants.WSA_NAMESPACE_URI_2005_08);

   /** Factory for getting endpoint serializers */ 
   private static final AeEndpointFactory sFactory = new AeEndpointFactory();
   
   /** Namespace of this serializer */
   private String mNamespace = IAeConstants.WSA_NAMESPACE_URI;
   
   /**
    * Private ctor for singleton pattern using default namespace 
    */
   private AeWSAddressingSerializer()
   {
   }
   
   /**
    * Private ctor for singleton pattern for a specific namespace
    */
   private AeWSAddressingSerializer(String aNamespace)
   {
      mNamespace = aNamespace;
   }   
   
   /**
    * Getter for the default singleton
    */
   public static AeWSAddressingSerializer getInstance()
   {
      return sSingleton;
   }

   /**
    * Getter for the singleton for a specific namespace
    */
   public static AeWSAddressingSerializer getInstance(String aNamespace)
   {
      if (IAeConstants.WSA_NAMESPACE_URI_2004_08.equals(aNamespace))
      {
         return sSingleton_2004_08;
      }
      else if (IAeConstants.WSA_NAMESPACE_URI_2004_03.equals(aNamespace))
      {
         return sSingleton_2004_03;
      }
      else if (IAeConstants.WSA_NAMESPACE_URI_2005_08.equals(aNamespace))
      {
         return sSingleton_2005_08;
      }
      else
      {
         throw new IllegalArgumentException(AeMessages.getString("AeWSAddressingDeserializer.1") + aNamespace); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.addressing.IAeAddressingSerializer#serializeToDocument(org.activebpel.rt.bpel.impl.addressing.IAeAddressingHeaders)
    */
   public Document serializeToDocument(IAeAddressingHeaders aRef) throws AeBusinessProcessException
   {
      try
      {
         MessageFactory soapFactory = AeSOAPMessageFactory.getSOAPMessageFactory();
         // The only time the soapFactory will be null is if we're running
         // within the simulation engine. All other times the soapFactory will
         // have been set during the initialization of the engine.
         // TODO (KP) revisit this code to not use the SOAPFactory, instead have this layer work with a DOM instead of SOAP
         if (soapFactory != null) 
         {
            SOAPMessage msg = soapFactory.createMessage();
            SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
            env.addNamespaceDeclaration(IAeWsAddressingConstants.WSA_NS_PREFIX, mNamespace);
            env.addNamespaceDeclaration("wsp", IAeConstants.WSP_NAMESPACE_URI); //$NON-NLS-1$
            env.addNamespaceDeclaration("abp", IAeConstants.ABP_NAMESPACE_URI); //$NON-NLS-1$
            Element headers = AeSOAPElementUtil.convertToDOM((Element) serializeHeadersInternal(aRef, env, true));
            return headers.getOwnerDocument(); 
         }
         else
         {
            return null;
         }
      }
      catch (SOAPException se)
      {
         throw new AeBusinessProcessException(AeMessages.getString("AeWSAddressingSerializer.0"), se); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.addressing.IAeAddressingSerializer#serializeHeaders(org.activebpel.rt.bpel.impl.addressing.IAeAddressingHeaders, javax.xml.soap.SOAPEnvelope)
    */
   public SOAPHeader serializeHeaders(IAeAddressingHeaders aReference, SOAPEnvelope aEnv) throws AeBusinessProcessException
   {
      return serializeHeadersInternal(aReference, aEnv, false);
   }

   /**
    * Internal method that performs the serialization of addressing headers to a SOAPHeader element
    * on behalf of the various overloaded methods.
    * 
    * If the RecipientAsEndpointFlag is true, we serialize the intended recipient as an endpoint reference
    * Otherwise, we serialize the address URI into a wsa:To element.  
    * If using a WSA namespace later than 2003, the flag should be false when transmitting to a partner.
    * 
    * @param aRef 
    * @param aEnv
    * @param aRecipientAsEndpointFlag 
    * @return Serialized headers as a SOAPHeader element
    * @throws AeBusinessProcessException
    */
   protected SOAPHeader serializeHeadersInternal(IAeAddressingHeaders aRef, SOAPEnvelope aEnv, boolean aRecipientAsEndpointFlag) throws AeBusinessProcessException
   {
      
      try
      {
         if (aRef == null)
         {
            return aEnv.getHeader();
         }
         if (!AeUtil.isNullOrEmpty(aRef.getConversationId()))
            addTextHeader(aEnv, IAeAddressingHeaders.ABX_CONVERSATION_ID, IAeConstants.ABX_NAMESPACE_URI, aRef.getConversationId());
         if (aRef.getAction() != null)
            addTextHeader(aEnv, IAeAddressingHeaders.WSA_ACTION, aRef.getAction());
         if (aRef.getMessageId() != null)
            addTextHeader(aEnv, IAeAddressingHeaders.WSA_MESSAGE_ID, aRef.getMessageId());
         if (aRef.getTo() != null && !aRecipientAsEndpointFlag)
         {
            addTextHeader(aEnv, IAeAddressingHeaders.WSA_TO, aRef.getTo());
         }
         if ((aRef.getRecipient() != null) && aRecipientAsEndpointFlag)
         {
            addEndpointHeader(aEnv, IAeAddressingHeaders.WSA_RECIPIENT, aRef.getRecipient());
         }
         if (aRef.getFrom() != null)
            addEndpointHeader(aEnv, IAeAddressingHeaders.WSA_FROM, aRef.getFrom());
         if (aRef.getReplyTo() != null)
            addEndpointHeader(aEnv, IAeAddressingHeaders.WSA_REPLY_TO, aRef.getReplyTo());
         if (aRef.getFaultTo() != null)
            addEndpointHeader(aEnv, IAeAddressingHeaders.WSA_FAULT_TO, aRef.getFaultTo());
         if (!AeUtil.isNullOrEmpty(aRef.getRelatesTo()))
         {
             Map relatesTo = aRef.getRelatesTo();
             for (Iterator it = relatesTo.keySet().iterator(); it.hasNext();)
             {
                QName name = (QName) it.next();
                addRelatesToHeader(aEnv, IAeWsAddressingConstants.WSA_NS_PREFIX + ":" + name.getLocalPart(), (String) relatesTo.get(name));  //$NON-NLS-1$
             }
         }
         if (!AeUtil.isNullOrEmpty(aRef.getReferenceProperties()))
         {
            for (Iterator it = aRef.getReferenceProperties().iterator(); it.hasNext();)
            {
               addHeaderElement(aEnv, (Element) it.next());
            }
         }
         return aEnv.getHeader();
      }
      catch (SOAPException se)
      {
         throw new AeBusinessProcessException(AeMessages.getString("AeWSAddressingSerializer.1"), se); //$NON-NLS-1$
      }
   }
   
   /**
    * Adds adds a DOM element to a SOAPEnvelope as a header
    * 
    * @param aEnv the target SOAPEnvelope
    * @param aValue the Header to add
    * @return the SOAPHeaderElement added
    * @throws SOAPException
    */
   private SOAPHeaderElement addHeaderElement(SOAPEnvelope aEnv, Element aValue) throws SOAPException
   {
      Name name = aEnv.createName(aValue.getLocalName(), aValue.getPrefix(), aValue.getNamespaceURI());
      SOAPHeaderElement wsaHeader = aEnv.getHeader().addHeaderElement(name);
      AeSOAPElementUtil.copyToSOAP(aValue, wsaHeader);
      return wsaHeader;
   }

   /**
    * Adds adds a header element to a SOAPEnvelope for a name/value pair 
    * @param aEnv the target SOAPEnvelope
    * @param aName the header element name
    * @param aValue the header element value
    * @return the new SOAPHeaderElement
    * @throws SOAPException
    */
   private SOAPHeaderElement addTextHeader(SOAPEnvelope aEnv, String aName, String aValue) throws SOAPException
   {
      Name name = aEnv.createName(aName, IAeWsAddressingConstants.WSA_NS_PREFIX, mNamespace);
      SOAPHeaderElement wsaHeader = aEnv.getHeader().addHeaderElement(name);
      wsaHeader.addTextNode(aValue);
      return wsaHeader;
   }

   /**
    * Adds adds a header element to a SOAPEnvelope for a name/value pair 
    * @param aEnv the target SOAPEnvelope
    * @param aName the header element name
    * @param aNamespace a specific namespace to use
    * @param aValue the header element value
    * @return the new SOAPHeaderElement
    * @throws SOAPException
    */
   private SOAPHeaderElement addTextHeader(SOAPEnvelope aEnv, String aName, String aNamespace, String aValue) throws SOAPException
   {
      Name name = aEnv.createName(aName, IAeWsAddressingConstants.WSA_NS_PREFIX, aNamespace);
      SOAPHeaderElement wsaHeader = aEnv.getHeader().addHeaderElement(name);
      wsaHeader.addTextNode(aValue);
      return wsaHeader;
   }
   
   /**
    * Adds a wsa:RelatesTo header to a SOAPEnvelope
    * @param aEnv the target SOAPEnvelope
    * @param aRelation the local part of the RelationshipType attribute
    * @param aValue the message id this header is relating to
    * @return the SOAPHeader added
    * @throws SOAPException
    */
   private SOAPHeaderElement addRelatesToHeader(SOAPEnvelope aEnv, String aRelation, String aValue) throws SOAPException
   {
      Name name = aEnv.createName(IAeAddressingHeaders.WSA_RELATES_TO, IAeWsAddressingConstants.WSA_NS_PREFIX, mNamespace);
      SOAPHeaderElement wsaHeader = aEnv.getHeader().addHeaderElement(name);
      Name relName = aEnv.createName(IAeWsAddressingConstants.WSA_RELATIONSHIP_TYPE, IAeWsAddressingConstants.WSA_NS_PREFIX, mNamespace);
      wsaHeader.addAttribute(relName, aRelation);
      wsaHeader.addTextNode(aValue);
      return wsaHeader;
   }   

   /**
    * Serializes an Endpoint Reference object to a SOAPHeader 
    * @param aEnv the SOAPEnvelope
    * @param aName the endpoint header element name
    * @param aEndpoint the endpoint reference
    * @return the new SOAPHeaderElement
    * @throws SOAPException
    */
   private SOAPHeaderElement addEndpointHeader(SOAPEnvelope aEnv, String aName, IAeWebServiceEndpointReference aEndpoint) throws SOAPException
   {
      Name name = aEnv.createName(aName, IAeWsAddressingConstants.WSA_NS_PREFIX, mNamespace);
      SOAPHeaderElement wsaHeader = aEnv.getHeader().addHeaderElement(name);
      IAeEndpointSerializer ser = sFactory.getSerializer(mNamespace);
      Document doc = ser.serializeEndpoint(aEndpoint);

      AeSOAPElementUtil.copyToSOAP(doc.getDocumentElement(), wsaHeader);
      
      return wsaHeader;
   }
}
