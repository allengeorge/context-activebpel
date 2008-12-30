//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/endpoints/AeWsa2005EndpointSerializer.java,v 1.1 2006/12/20 23:36:3
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
package org.activebpel.rt.bpel.impl.endpoints;

import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.wsio.IAeWebServiceEndpointReference;
import org.w3c.dom.Element;

/**
 * Endpoint serializer for WS-Addressing endpoints in the 2005 namespace. 
 */
public class AeWsa2005EndpointSerializer extends AeWSAddressingEndpointSerializer
{
   /**
    * ctor with namespace
    */
   protected AeWsa2005EndpointSerializer(String aNamespace)
   {
      super(aNamespace);
   }   

   /**
    * Overrides method to create a wsa:ReferenceParameters element instead of a wsa:ReferenceProperties element
    * @see org.activebpel.rt.bpel.impl.endpoints.AeWSAddressingEndpointSerializer#addReferenceProps(org.activebpel.wsio.IAeWebServiceEndpointReference, org.w3c.dom.Element)
    */
   protected void serializeReferenceProps(IAeWebServiceEndpointReference aRef, Element aElement)
   {
      if (!aRef.getReferenceProperties().isEmpty())
      {
         Element props = AeXmlUtil.addElementNS(aElement, aElement.getNamespaceURI(), "wsa:ReferenceParameters", null); //$NON-NLS-1$
         addReferenceProps(aRef, props);
      }
   }
   
   /**
    * Overrides method to put policy and extensibility elements in a wsa:Metadata element as defined in the 2005 spec 
    * @see org.activebpel.rt.bpel.impl.endpoints.AeWSAddressingEndpointSerializer#addMetadata(org.activebpel.wsio.IAeWebServiceEndpointReference, org.w3c.dom.Element)
    */
   protected void addMetadata(IAeWebServiceEndpointReference aRef, Element aElement)
   {
      if (aRef.getExtensibilityElements().hasNext() || !aRef.getPolicies().isEmpty())
      {
         Element meta = AeXmlUtil.addElementNS(aElement, aElement.getNamespaceURI(), "wsa:Metadata", null); //$NON-NLS-1$
         // Add policies if any were defined
         addPolicies(aRef, meta);
         
         // Add extensibility elements if any
         addExtElements(aRef, meta);
      }
   }
}
