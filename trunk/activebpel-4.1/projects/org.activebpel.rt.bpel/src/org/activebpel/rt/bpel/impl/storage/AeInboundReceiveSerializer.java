// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/storage/AeInboundReceiveSerializer.java,v 1.12 2007/02/21 20:11:1
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
package org.activebpel.rt.bpel.impl.storage;

import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.impl.AeBusinessProcessPropertyIO;
import org.activebpel.rt.bpel.impl.AeMessageDataSerializer;
import org.activebpel.rt.bpel.impl.IAeImplStateNames;
import org.activebpel.rt.bpel.impl.addressing.AeAddressingHeaders;
import org.activebpel.rt.bpel.impl.addressing.AeWsAddressingFactory;
import org.activebpel.rt.bpel.impl.addressing.IAeAddressingSerializer;
import org.activebpel.rt.bpel.impl.endpoints.AeEndpointFactory;
import org.activebpel.rt.bpel.impl.endpoints.IAeEndpointSerializer;
import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.impl.fastdom.AeFastElement;
import org.activebpel.rt.bpel.impl.fastdom.AeFastNode;
import org.activebpel.rt.bpel.impl.fastdom.AeForeignNode;
import org.activebpel.rt.bpel.impl.queue.AeInboundReceive;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.activebpel.wsio.IAeWebServiceEndpointReference;
import org.activebpel.wsio.IAeWsAddressingHeaders;
import org.activebpel.wsio.receive.IAeMessageContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Serializes an inbound receive to an instance of {@link
 * org.activebpel.rt.bpel.impl.fastdom.AeFastElement} or {@link
 * org.activebpel.rt.bpel.impl.fastdom.AeFastDocument}.
 */
public class AeInboundReceiveSerializer implements IAeImplStateNames
{
   /** The inbound receive to serialize. */
   private AeInboundReceive mInboundReceive;

   /** The resulting serialization. */
   private AeFastElement mInboundReceiveElement;

   /** Serializer for message data. */
   private AeMessageDataSerializer mMessageDataSerializer;

   /** The type mapping to use to serialize simple types. */
   private AeTypeMapping mTypeMapping;

   /**
    * Appends optional attribute to the specified element.
    * 
    * @param aElement
    * @param aName
    * @param aValue
    */
   protected void appendAttribute(AeFastElement aElement, String aName, String aValue)
   {
      if (aValue != null)
      {
         aElement.setAttribute(aName, aValue);
      }
   }

   /**
    * Appends optional element as a child of the specified element.
    * 
    * @param aElement
    * @param aChildElement
    */
   protected void appendElement(AeFastElement aElement, AeFastElement aChildElement)
   {
      if (aChildElement != null)
      {
         aElement.appendChild(aChildElement);
      }
   }

   /**
    * Serializes an instance of {@link
    * org.activebpel.wsio.IAeWebServiceEndpointReference} to an instance of
    * {@link org.activebpel.rt.bpel.impl.fastdom.AeFastElement} with the
    * specified tag name.
    * 
    * @param aTagName
    * @param aEndpointReference
    */
   protected AeFastElement createEndpointReferenceElement(String aTagName, IAeWebServiceEndpointReference aEndpointReference)
   {
      AeFastElement result = null;

      if (aEndpointReference != null)
      {
         IAeEndpointSerializer writer = new AeEndpointFactory().getSerializer(aEndpointReference.getSourceNamespace());
         Document doc = writer.serializeEndpoint(aEndpointReference);
         AeFastNode node = new AeForeignNode(doc.getDocumentElement());

         result = new AeFastElement(aTagName);
         result.appendChild(node);
      }

      return result;
   }

   /**
    * Serializes an instance of {@link
    * org.activebpel.wsio.IAeWebServiceEndpointReference} to an instance of
    * {@link org.activebpel.rt.bpel.impl.fastdom.AeFastElement} with the
    * specified tag name.
    * 
    * @param aTagName
    * @param aEndpointReference
    */
   protected AeFastElement createWsaHeaderElement(String aTagName, IAeWsAddressingHeaders aWsaHeader) throws AeBusinessProcessException
   {
      AeFastElement result = null;

      if (aWsaHeader != null)
      {
         IAeAddressingSerializer writer = AeWsAddressingFactory.getInstance().getSerializer(aWsaHeader.getSourceNamespace());
         Document doc = writer.serializeToDocument(AeAddressingHeaders.convert(aWsaHeader));
         // added null check since there could have been problems serializing the
         // headers. This only happens in BPEP and is related to some class loading
         // issue with the SOAP factory. There's a note there to remove our 
         // dependency on the SOAP classes and instead just work with DOM's.
         if (doc != null)
         {
            AeFastNode node = new AeForeignNode(doc.getDocumentElement());

            result = new AeFastElement(aTagName);
            result.appendChild(node);
         }
      }

      return result;
   }
   
   /**
    * Serializes a list of message context reference property elements to an instance of
    * {@link org.activebpel.rt.bpel.impl.fastdom.AeFastElement} with the
    * specified tag name.
    * 
    * @param aTagName
    * @param aPropIterator
    */
   protected AeFastElement createReferencePropertiesElement(String aTagName, Iterator aPropIterator)
   {
      AeFastElement result = null;

      if (aPropIterator != null)
      {
         result = new AeFastElement(aTagName);
         while (aPropIterator.hasNext())
         {
            AeFastNode node = new AeForeignNode((Element) aPropIterator.next());
            result.appendChild(node);
         }
      }

      return result;
   }
   
   /**
    * Serializes the specified inbound receive to an instance of {@link
    * org.activebpel.rt.bpel.impl.fastdom.AeFastElement}.
    *
    * @param aInboundReceive
    */
   protected AeFastElement createInboundReceiveElement(AeInboundReceive aInboundReceive) throws AeBusinessProcessException
   {
      AeFastElement result = new AeFastElement(STATE_INBOUNDRECEIVE);
      appendAttribute(result, STATE_REPLYWAITING, String.valueOf(!aInboundReceive.isOneway()));
      appendElement(result, createMessageContextElement(aInboundReceive.getContext()));
      appendElement(result, createMessageDataElement(aInboundReceive.getMessageData()));
      if (aInboundReceive.getReplyReceiver() != null && aInboundReceive.getReplyReceiver().getDurableReplyInfo() != null ) 
      {
         // Create durable reply data if inboundRec is durable.
         appendElement(result, createDurableReplyElement(aInboundReceive));
      }
      return result;
   }
   
   /**
    * Serializes the inbound receive's durable reply data.
    * 
    * @param aInboundReceive
    */
   protected AeFastElement createDurableReplyElement(AeInboundReceive aInboundReceive) throws AeBusinessProcessException
   { 
      AeDurableReplySerializer replySerializer = new AeDurableReplySerializer();         
      replySerializer.setDurableReplyInfo(aInboundReceive.getReplyReceiver().getDurableReplyInfo());
      return replySerializer.getDurableReplyInfoElement();
   }

   /**
    * Serializes the inbound receive's message context.
    * 
    * @param aMessageContext
    * @throws AeBusinessProcessException 
    */
   protected AeFastElement createMessageContextElement(IAeMessageContext aMessageContext) throws AeBusinessProcessException
   {
      AeFastElement result = null;

      if (aMessageContext != null)
      {
         result = new AeFastElement(STATE_MESSAGECONTEXT);
         appendAttribute(result, STATE_OPERATION, aMessageContext.getOperation());
         appendAttribute(result, STATE_PLINK    , aMessageContext.getPartnerLink());
         appendAttribute(result, STATE_PRINCIPAL, aMessageContext.getPrincipal());
         appendAttribute(result, STATE_VERSION  , aMessageContext.getProcessVersion());

         IAeWsAddressingHeaders wsaHeaders = aMessageContext.getWsAddressingHeaders();
         appendElement(result, createWsaHeaderElement(STATE_WSAHEADERS, wsaHeaders));
         appendElement(result, createReferencePropertiesElement(STATE_REFPROPS, aMessageContext.getReferenceProperties()));

         // Serialize process properties.
         Map properties = aMessageContext.getBusinessProcessProperties();
         if (properties != null)
         {
            for (Iterator i = properties.entrySet().iterator(); i.hasNext(); )
            {
               Map.Entry entry = (Map.Entry) i.next();
               String name = (String) entry.getKey();
               String value = AeUtil.getSafeString((String) entry.getValue());

               appendElement(result, AeBusinessProcessPropertyIO.getBusinessProcessPropertyElement(name, value));
            }
         }
      }

      return result;
   }
   

   /**
    * Serializes the inbound receive's message data.
    * 
    * @param aMessageData
    * @throws AeBusinessProcessException
    */
   protected AeFastElement createMessageDataElement(IAeMessageData aMessageData) throws AeBusinessProcessException
   {
      AeFastElement result = null;

      if (aMessageData != null)
      {
         AeMessageDataSerializer serializer = getMessageDataSerializer();
         serializer.setMessageData(aMessageData);

         result = serializer.getMessageDataElement();
      }

      return result;
   }

   /**
    * Serializes a {@link javax.xml.namespace.QName} to an instance of {@link
    * org.activebpel.rt.bpel.impl.fastdom.AeFastElement} with the specified tag
    * name.
    * 
    * @param aTagName
    * @param aQName
    */
   protected AeFastElement createQNameElement(String aTagName, QName aQName)
   {
      AeFastElement result = null;

      if (aQName != null)
      {
         result = new AeFastElement(aTagName);
         result.setAttribute(STATE_NAME        , aQName.getLocalPart());
         result.setAttribute(STATE_NAMESPACEURI, aQName.getNamespaceURI());
      }

      return result;
   }

   /**
    * Returns the inbound receive to serialize.
    */
   protected AeInboundReceive getInboundReceive()
   {
      return mInboundReceive;
   }

   /**
    * Returns an instance of {@link
    * org.activebpel.rt.bpel.impl.fastdom.AeFastDocument} representing the
    * inbound receive.
    */
   public AeFastDocument getInboundReceiveDocument() throws AeBusinessProcessException
   {
      return new AeFastDocument(getInboundReceiveElement());
   }

   /**
    * Returns an instance of {@link
    * org.activebpel.rt.bpel.impl.fastdom.AeFastElement} representing the
    * inbound receive.
    */
   public AeFastElement getInboundReceiveElement() throws AeBusinessProcessException
   {
      if (mInboundReceiveElement == null)
      {
         if (getInboundReceive() == null)
         {
            throw new IllegalStateException(AeMessages.getString("AeInboundReceiveSerializer.ERROR_0")); //$NON-NLS-1$
         }

         mInboundReceiveElement = createInboundReceiveElement(getInboundReceive());
      }

      return mInboundReceiveElement;
   }

   /**
    * Returns serializer for message data.
    */
   protected AeMessageDataSerializer getMessageDataSerializer()
   {
      if (mMessageDataSerializer == null)
      {
         if (getTypeMapping() == null)
         {
            throw new IllegalStateException(AeMessages.getString("AeInboundReceiveSerializer.ERROR_1")); //$NON-NLS-1$
         }

         mMessageDataSerializer = new AeMessageDataSerializer(getTypeMapping());
      }

      return mMessageDataSerializer;
   }

   /**
    * Returns type mapping to use to serialize simple types.
    */
   protected AeTypeMapping getTypeMapping()
   {
      return mTypeMapping;
   }

   /**
    * Resets all output variables.
    */
   protected void reset()
   {
      mInboundReceiveElement = null;
      mMessageDataSerializer = null;
   }

   /**
    * Sets the inbound receive to serialize.
    *
    * @param aInboundReceive
    */
   public void setInboundReceive(AeInboundReceive aInboundReceive)
   {
      reset();

      mInboundReceive = aInboundReceive;
   }

   /**
    * Sets the type mapping to use to serialize simple types.
    *
    * @param aTypeMapping
    */
   public void setTypeMapping(AeTypeMapping aTypeMapping)
   {
      reset();

      mTypeMapping = aTypeMapping;
   }
}
