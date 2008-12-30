// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/endpoints/AeEndpointFactory.java,v 1.10 2007/02/28 16:45:2
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

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Element;

/**
 * Factory for returning the serializers and deserializers for endpoint references.
 * This is currently limited to working with WS-Addressing endpoints but could be
 * expanded to provide support for other formats. 
 */
public class AeEndpointFactory implements IAeEndpointFactory
{
   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeEndpointFactory#getDeserializer(java.lang.String)
    */
   public IAeEndpointDeserializer getDeserializer(String aNamespace)
   {
      if (AeUtil.isNullOrEmpty( aNamespace) || IAeConstants.WSA_NAMESPACE_URI.equals(aNamespace))
      {
         return AeWSAddressingEndpointDeserializer.getInstance();
      }
      else if (IAeConstants.WSA_NAMESPACE_URI_2004_08.equals(aNamespace))
      {
         return AeWSAddressingEndpointDeserializer.getInstance(aNamespace);         
      }
      else if (IAeConstants.WSA_NAMESPACE_URI_2004_03.equals(aNamespace))
      {
         return AeWSAddressingEndpointDeserializer.getInstance(aNamespace);         
      }
      else if (IAeConstants.WSA_NAMESPACE_URI_2005_08.equals(aNamespace))
      {
         return AeWSAddressingEndpointDeserializer.getInstance(aNamespace);         
      }
      throw new IllegalArgumentException(AeMessages.format("AeEndpointFactory.ERROR_0", aNamespace)); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeEndpointFactory#getSerializer(java.lang.String)
    */
   public IAeEndpointSerializer getSerializer(String aNamespace)
   {
      if (IAeBPELConstants.WSA_NAMESPACE_URI.equals(aNamespace) || AeUtil.isNullOrEmpty(aNamespace))
      {
         return AeWSAddressingEndpointSerializer.getInstance();
      }
      else if (IAeConstants.WSA_NAMESPACE_URI_2004_08.equals(aNamespace))
      {
         return AeWSAddressingEndpointSerializer.getInstance(aNamespace);         
      }
      else if (IAeConstants.WSA_NAMESPACE_URI_2004_03.equals(aNamespace))
      {
         return AeWSAddressingEndpointSerializer.getInstance(aNamespace);         
      }
      else if (IAeConstants.WSA_NAMESPACE_URI_2005_08.equals(aNamespace))
      {
         return AeWSAddressingEndpointSerializer.getInstance(aNamespace);         
      }
      throw new IllegalArgumentException(AeMessages.format("AeEndpointFactory.ERROR_1", aNamespace)); //$NON-NLS-1$
   }
   
   /**
    * Returns true if the given element is a wsa-epr element.
    * @param aElement
    * @return true if the element is a EndpointReference element.
    */
   public static boolean isEndpointReferenceElement(Element aElement)
   {
      return aElement != null
            && "EndpointReference".equals(aElement.getLocalName()) //$NON-NLS-1$
            && (   IAeConstants.WSA_NAMESPACE_URI.equals(aElement.getNamespaceURI())
                || IAeConstants.WSA_NAMESPACE_URI_2004_03.equals(aElement.getNamespaceURI())
                || IAeConstants.WSA_NAMESPACE_URI_2004_08.equals(aElement.getNamespaceURI())
                || IAeConstants.WSA_NAMESPACE_URI_2005_08.equals(aElement.getNamespaceURI())
               );
   }

}
