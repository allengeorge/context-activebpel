// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/addressing/AePartnerAddressing.java,v 1.28 2007/05/22 00:19:1
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
package org.activebpel.rt.bpel.server.addressing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.IAePolicyConstants;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeWSDLDefHelper;
import org.activebpel.rt.bpel.IAeEndpointReference;
import org.activebpel.rt.bpel.IAePartnerLink;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.impl.AeEndpointReference;
import org.activebpel.rt.bpel.impl.addressing.AeAddressingHeaders;
import org.activebpel.rt.bpel.impl.addressing.AeWsAddressingFactory;
import org.activebpel.rt.bpel.impl.addressing.IAeAddressingDeserializer;
import org.activebpel.rt.bpel.impl.addressing.IAeAddressingHeaders;
import org.activebpel.rt.bpel.impl.addressing.IAeAddressingSerializer;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.IAeProcessDeployment;
import org.activebpel.rt.bpel.server.addressing.pdef.IAePartnerAddressBook;
import org.activebpel.rt.bpel.server.addressing.pdef.IAePartnerAddressingProvider;
import org.activebpel.rt.bpel.server.deploy.IAeServiceDeploymentInfo;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.bpel.urn.IAeURNResolver;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;
import org.activebpel.wsio.AeWsAddressingException;
import org.activebpel.wsio.IAeWebServiceEndpointReference;
import org.activebpel.wsio.IAeWsAddressingConstants;
import org.activebpel.wsio.IAeWsAddressingHeaders;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * The partner addressing layer is responsible for providing endpoint references
 * for each of the partners that the business process interacts with. The 
 * endpoint data either exists as part of the deployment descriptor, as part of 
 * the message context, or from a partner definition object.  
 */
public class AePartnerAddressing implements IAePartnerAddressing
{
   
   private IAePartnerAddressingProvider mProvider;
   
   /**
    * @see org.activebpel.rt.bpel.server.addressing.IAePartnerAddressing#getReplyAddressing(IAeAddressingHeaders, String)
    */
   public IAeAddressingHeaders getReplyAddressing(IAeWsAddressingHeaders aSource, String aAction) throws AeBusinessProcessException
   {
      AeAddressingHeaders outAddrHeaders = new AeAddressingHeaders(aSource.getSourceNamespace());

      // Get the address that was invoked and 
      // use it for the From address
      String incommingTo = aSource.getTo();
      String fromAddressURI = incommingTo;
      IAeEndpointReference from = new AeEndpointReference();
      from.setAddress(fromAddressURI);
      from.setSourceNamespace(aSource.getSourceNamespace());
      outAddrHeaders.setFrom(from);

      // Add RelatesTo
      String incommingMessageId = aSource.getMessageId();
      if (!AeUtil.isNullOrEmpty(incommingMessageId))
      {
         outAddrHeaders.addRelatesTo(incommingMessageId, getReplyRelation(aSource.getSourceNamespace()));
      }
      else
      {
         // WS-Addressing requires a RelatesTo header if a reply is expected
         if (aSource.getReplyTo() != null)
            throw new IllegalArgumentException(AeMessages.getString("AePartnerAddressing.0")); //$NON-NLS-1$
      }
      
      //Setting To endpoint
      if (getFaultAction(aSource.getSourceNamespace()).equals(aAction) && (aSource.getFaultTo() != null))
      {
         // For wsa fault action, use the FaultTo address, if any
         IAeEndpointReference endpoint = (IAeEndpointReference) aSource.getFaultTo();
         outAddrHeaders.setRecipient(endpoint);
      }
      else if (aSource.getReplyTo() != null) 
      {
         // Use reply to, if specified
         IAeEndpointReference endpoint = (IAeEndpointReference) aSource.getReplyTo();
         outAddrHeaders.setRecipient(endpoint);
      }
      else if (aSource.getFrom() != null) 
      {
         // otherwise, use from if available
         IAeEndpointReference endpoint = (IAeEndpointReference) aSource.getFrom();
         outAddrHeaders.setRecipient(endpoint);
      }
      else
      {
         // use anonymous role if no other choice
         outAddrHeaders.setTo(getAnonymousRole(aSource.getSourceNamespace()));
      }
      
      try
      {
         for (Iterator it = outAddrHeaders.getRecipient().getReferenceProperties().iterator(); it.hasNext();)
         {
            Element el = AeXmlUtil.cloneElement((Element) it.next());
            if (el.getNamespaceURI() == IAeConstants.WSA_NAMESPACE_URI_2005_08)
            {
               el.setAttributeNS(IAeConstants.WSA_NAMESPACE_URI_2005_08, "wsa:IsReferenceParameter", "true"); //$NON-NLS-1$ //$NON-NLS-2$
            }
            outAddrHeaders.addHeaderElement(el);
         }
      }
      catch (AeWsAddressingException wse)
      {
         throw new AeBusinessProcessException(AeMessages.getString("AePartnerAddressing.2"), wse); //$NON-NLS-1$
      }

      // Set the action to the one given
      outAddrHeaders.setAction(aAction);
      
      return outAddrHeaders;
   }     
   
   /**
    * @see org.activebpel.rt.bpel.server.addressing.IAePartnerAddressing#readFromDeployment(org.w3c.dom.Element)
    */
   public IAeEndpointReference readFromDeployment(Element aElement) throws AeBusinessProcessException
   {
      AeEndpointReference endpoint = null;

      DocumentFragment frag = aElement.getOwnerDocument().createDocumentFragment();
      for (Node child=aElement.getFirstChild(); child != null; child=child.getNextSibling())
      {
         if (child.getNodeType() == Node.ELEMENT_NODE)
            frag.appendChild(child.cloneNode(true));
      }
      endpoint = new AeEndpointReference();
      endpoint.setReferenceData(AeXmlUtil.getFirstSubElement(frag));
       
      return endpoint;
   }

   /**
    * @see org.activebpel.rt.bpel.server.addressing.IAePartnerAddressing#readFromContext(javax.xml.rpc.handler.soap.SOAPMessageContext)
    */
   public IAeEndpointReference readFromContext(SOAPMessageContext aContext) throws AeBusinessProcessException
   {
      IAeEndpointReference ref = null;
      try
      {
         // Get the reply addressing headers
         SOAPHeader header = aContext.getMessage().getSOAPHeader();
         IAeAddressingDeserializer deserializer = AeWsAddressingFactory.getInstance().getDeserializer(IAeConstants.WSA_NAMESPACE_URI);
         IAeAddressingHeaders headers = deserializer.deserializeHeaders(header);
         IAeAddressingHeaders replyAddressing = getReplyAddressing(headers, headers.getAction());
         
         // Create an endpoint ref
         ref = new AeEndpointReference();
         ref.setReferenceData(replyAddressing.getRecipient());
         
         return ref;
      }
      catch (SOAPException e)
      {
         AeException.logError(e);
      } 
      return ref;
   }

   /**
    * @see org.activebpel.rt.bpel.server.addressing.IAePartnerAddressing#updateFromPrincipal(org.activebpel.rt.bpel.IAePartnerLink, java.lang.String)
    */
   public void updateFromPrincipal(IAePartnerLink aLink, String aPrincipal) throws AeBusinessProcessException
   {
      IAePartnerAddressBook book = getProvider().getAddressBook( aPrincipal );
      aLink.setPrincipal(aPrincipal);

      if (book != null)
      {
         IAeEndpointReference partnerRef = book.getEndpointReference(aLink.getPartnerLinkType(), aLink.getPartnerRole());
         if (partnerRef != null)
         {
            aLink.getPartnerReference().setReferenceData(partnerRef);
         }
      
         IAeEndpointReference myRef = book.getEndpointReference(aLink.getPartnerLinkType(), aLink.getMyRole());
         if (myRef!= null)
         {
            aLink.getMyReference().setReferenceData(myRef);
         }
      }
   }

   /**
    * Getter for the provider
    */
   public IAePartnerAddressingProvider getProvider()
   {
      return mProvider;
   }

   /**
    * Setter for the provider
    * @param aProvider
    */
   public void setProvider(IAePartnerAddressingProvider aProvider)
   {
      mProvider = aProvider;
   }
   
   /**
    * Returns the fully qualified fault action uri
    * @param aNamespace
    * @return fault action uri
    */
   private String getFaultAction(String aNamespace)
   {
      return aNamespace.concat(IAeWsAddressingConstants.WSA_FAULT_ACTION_);
   }
   
   /**
    * Returns the WSA anonymous role URI
    * @param aNamespace
    * @return anonymous role uri
    */
   private String getAnonymousRole(String aNamespace)
   {
      return aNamespace.concat(IAeWsAddressingConstants.WSA_ANONYMOUS_ROLE);
   }
   
   /**
    * Gets the Reply relationship type QName
    * 
    * @param aNamespace
    * @return relationship type attribute
    */
   private QName getReplyRelation(String aNamespace)
   {
      if (IAeConstants.WSA_NAMESPACE_URI.equals(aNamespace))
      {
         return new QName(aNamespace, IAeWsAddressingConstants.WSA_RESPONSE_RELATION);
      }
      else
      {
         return new QName(aNamespace, IAeWsAddressingConstants.WSA_REPLY_RELATION);
      }
   }
   
   /**
    * Merges 2 endpoints to update the target with information from the source 
    * @param aSource endpoint to merge
    * @param aTarget existing partner endpoint.  If this endpoint is not null, 
    *        the address and service info is taken from here.
    * @return merged endpoint
    */
   public IAeEndpointReference mergeReplyEndpoint(IAeWebServiceEndpointReference aSource, IAeWebServiceEndpointReference aTarget) throws AeBusinessProcessException 
   {
      if (aSource == null && aTarget == null)
         return null;
      
      // Use target (if it exists), or brand new from the source
      IAeEndpointReference newReplyTo = new AeEndpointReference();
      if (aTarget != null)
      {
         newReplyTo.setReferenceData(aTarget);
         if (aSource != null)
         {
            if (AeUtil.notNullOrEmpty(aSource.getReferenceProperties()) || 
                AeUtil.notNullOrEmpty(aSource.getReferenceProperties()) )
            {
               // add reference properties from both endpoints
               AeAddressingHeaders wsa = new AeAddressingHeaders(newReplyTo.getSourceNamespace());
               try
               {
                  wsa.setReferenceProperties(aSource.getReferenceProperties());
                  wsa.setReferenceProperties(aTarget.getReferenceProperties());
                  // calling the update here will have us recursively handle embedded wsa headers buried deeper in the epr
                  newReplyTo = updateEndpointHeaders(wsa, newReplyTo);
               }
               catch (AeWsAddressingException wse)
               {
                  throw new AeBusinessProcessException(wse.getLocalizedMessage(), wse);
               }
            }
         }
      }
      else
      {
         newReplyTo.setReferenceData(aSource);
      }
      
      // Resolve the URN for the address
      IAeURNResolver resolver = AeEngineFactory.getURNResolver();
      String url = newReplyTo.getAddress();
      newReplyTo.setAddress(resolver.getURL(url));
      
      return newReplyTo;
   }

   /**
    * @see org.activebpel.rt.bpel.server.addressing.IAePartnerAddressing#updateEndpointHeaders(org.activebpel.rt.bpel.impl.addressing.IAeAddressingHeaders, org.activebpel.wsio.IAeWebServiceEndpointReference)
    */
   public IAeEndpointReference updateEndpointHeaders(IAeAddressingHeaders aWsaHeaders, IAeWebServiceEndpointReference aEndpoint) throws AeBusinessProcessException
   {
      AeEndpointReference epr = new AeEndpointReference();
      epr.setReferenceData(aEndpoint);

      // resolve URN mappings for To address
      String toAddress = AeEngineFactory.getURNResolver().getURL(epr.getAddress());
      epr.setAddress(toAddress);      
      // Set To addressing with the endpoint
      aWsaHeaders.setRecipient(epr);
      
      // pull the replyTo from the wsa headers 
      IAeWebServiceEndpointReference wsaReplyTo = aWsaHeaders.getReplyTo();
      // pull the faultTo from the wsa headers 
      IAeWebServiceEndpointReference wsaFaultTo = aWsaHeaders.getFaultTo();
            
      // Setting the reference properties on the addressing holder
      // ensures that wsa headers embedded in the partner endpoint are handled properly
      // replacing the values in the wsa headers with any info embedded in reference properties
      try
      {
         aWsaHeaders.setReferenceProperties(epr.getReferenceProperties());
      }
      catch (AeWsAddressingException wse)
      {
         throw new AeBusinessProcessException(AeMessages.getString("AePartnerAddressing.1"), wse); //$NON-NLS-1$
      }
      // Clear existing reference properties on our endpoint holder so they don't get added again
      epr.setReferenceProperties(new ArrayList());
      
      // merge the replyto
      aWsaHeaders.setReplyTo(mergeReplyEndpoint(wsaReplyTo, aWsaHeaders.getReplyTo()));
      // merge the faultto
      aWsaHeaders.setFaultTo(mergeReplyEndpoint(wsaFaultTo, aWsaHeaders.getFaultTo()));      
      
      // Create the addressing headers
      IAeAddressingSerializer ser = AeWsAddressingFactory.getInstance().getSerializer(aWsaHeaders.getSourceNamespace());
      SOAPEnvelope env;
      try
      {
         env = AeEngineFactory.getSOAPMessageFactory().createMessage().getSOAPPart().getEnvelope();
      }
      catch (SOAPException ex)
      {
         throw new AeBusinessProcessException(ex.getMessage(), ex);
      }
      SOAPHeader header = ser.serializeHeaders(aWsaHeaders, env);
      // Add all headers as reference properties on partner link
      if (header != null)
      {
         for (Iterator it = header.getChildElements(); it.hasNext();)
         {
            Element element = (Element) it.next();
            epr.addReferenceProperty(element); 
         }
      }
      return epr;
   }

   /**
    * @see org.activebpel.rt.bpel.server.addressing.IAePartnerAddressing#getMyRoleEndpoint(org.activebpel.rt.bpel.server.IAeProcessDeployment, org.activebpel.rt.bpel.def.AePartnerLinkDef, javax.xml.namespace.QName, java.lang.String)
    */
   public IAeEndpointReference getMyRoleEndpoint(IAeProcessDeployment aProcessDeployment, AePartnerLinkDef aPartnerLink, QName aProcessName, String aConversationId) throws AeBusinessProcessException
   {
      // Find the service definition for this partner link
      IAeServiceDeploymentInfo service = aProcessDeployment.getServiceInfo(aPartnerLink.getLocationPath());
      
      if (service == null)
      {
         // no myrole on partner link, so no implicit replyTo
         return null;
      }
      
      AeEndpointReference myRoleRef = new AeEndpointReference();
      String url = service.getServiceName();
      myRoleRef.setAddress(url); 
      
      AeBPELExtendedWSDLDef cachedDef = AeWSDLDefHelper.getWSDLDefinitionForPLT(aProcessDeployment, aPartnerLink.getPartnerLinkTypeName());

      if (cachedDef != null)
      {
         QName serviceName = new QName(cachedDef.getTargetNamespace(), service.getServiceName());
         myRoleRef.setServiceName(serviceName);
         QName portType = aPartnerLink.getMyRolePortType();
         myRoleRef.setPortType(portType);
         
         Service wsdlService = (Service) cachedDef.getServices().get(serviceName);
         if (wsdlService != null)
         {
            Map ports = wsdlService.getPorts();
            for (Iterator it = ports.values().iterator(); it.hasNext();)
            {
               Port port = (Port) it.next();
               if (port.getBinding().getPortType().getQName().equals(portType))
               {
                  myRoleRef.setServicePort(port.getName());
                  break;
               }
            }
         }
      }
      List policies = service.getPolicies();
      if (!AeUtil.isNullOrEmpty(policies))
      {
         try
         {
            for (Iterator it = policies.iterator(); it.hasNext();)
            {
               myRoleRef.addPolicyElement((Element) it.next());
            }
            
            // create a correlation header
            if (aConversationId != null)
            {
               // get the correlationId property from the mapper
               Map props = AeEngineFactory.getPolicyMapper().getCallProperties(policies);
               QName correlationProperty = (QName) props.get(IAePolicyConstants.TAG_ASSERT_MANAGED_CORRELATION);
               if (correlationProperty != null)
               {
                  myRoleRef.addProperty(correlationProperty, aConversationId );
                  //  create a header element for the conversation id
                  QName headerName = IAePolicyConstants.CONVERSATION_ID_HEADER;               
                  Document doc = AeXmlUtil.newDocument();
                  Element convHeader = doc.createElementNS(headerName.getNamespaceURI(), headerName.getLocalPart());
                  convHeader.appendChild(doc.createTextNode(aConversationId));
                  myRoleRef.addReferenceProperty(convHeader);
               }
            }            
         }
         catch (AeException ae)
         {
            throw new AeBusinessProcessException(ae.getLocalizedMessage(), ae);
         }
      }
      return myRoleRef;
   }
}
