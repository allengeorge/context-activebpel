//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AeWSBPELPropertyAliasIO.java,v 1.3 2006/08/01 17:47:4
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
package org.activebpel.rt.wsdl.def; 

import javax.wsdl.Definition;

import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Reads and writes WS BPEL property alias elements
 */
public class AeWSBPELPropertyAliasIO extends AePropertyAliasIO
{
   /**
    * @see org.activebpel.rt.wsdl.def.AePropertyAliasIO#writePropertyAlias(org.activebpel.rt.wsdl.def.IAePropertyAlias, org.w3c.dom.Element, javax.wsdl.Definition)
    */
   protected void writePropertyAlias(IAePropertyAlias aPropAlias, Element aElement, Definition aDefinition)
   {
      /*
        <bpel:propertyAlias propertyName="QName" 
                  messageType="QName"? part="NCName"? 
                  type="QName"? element="QName"?> 
              <bpel:query queryLanguage="anyURI"?>? 
              ... queryString ... 
              </bpel:query> 
        </bpel:propertyAlias>
       */
      addPropertyName(aPropAlias, aElement, aDefinition);
      if (aPropAlias.getType() == IAePropertyAlias.MESSAGE_TYPE)
      {
         addMessageData(aPropAlias, aElement, aDefinition);
      }
      else if (aPropAlias.getType() == IAePropertyAlias.TYPE)
      {
         String type = toString(aDefinition, aPropAlias.getTypeName());
         aElement.setAttribute(COMPLEX_TYPE_ATTRIB, type);
      }
      else
      {
         String element = toString(aDefinition, aPropAlias.getElementName());
         aElement.setAttribute(ELEMENT_TYPE_ATTRIB, element);
      }
      addQuery(aPropAlias, aElement);
   }
   
   /**
    * @see org.activebpel.rt.wsdl.def.AePropertyAliasIO#addQuery(org.activebpel.rt.wsdl.def.IAePropertyAlias, org.w3c.dom.Element)
    */
   protected void addQuery(IAePropertyAlias aPropAlias, Element aElement)
   {
      if (AeUtil.notNullOrEmpty(aPropAlias.getQuery()))
      {
         Element queryElement = aElement.getOwnerDocument().createElementNS(aElement.getNamespaceURI(), aElement.getPrefix() + ":" + QUERY_ATTRIB); //$NON-NLS-1$
         aElement.appendChild(queryElement);
         if(AeUtil.notNullOrEmpty(aPropAlias.getQueryLanguage()))
         {
            queryElement.setAttribute(QUERY_LANGUAGE, aPropAlias.getQueryLanguage());
         }
         queryElement.appendChild(queryElement.getOwnerDocument().createTextNode(aPropAlias.getQuery()));
      }
   }

   /**
    * @see org.activebpel.rt.wsdl.def.AePropertyAliasIO#updatePropAliasData(org.activebpel.rt.wsdl.def.AePropertyAliasImpl, org.w3c.dom.Element)
    */
   protected void updatePropAliasData(AePropertyAliasImpl aPropAlias, Element aPropertyElem)
   {
      aPropAlias.setPropertyName( AeXmlUtil.createQName(aPropertyElem, aPropertyElem.getAttribute(PROPERTY_NAME_ATTRIB)));

      if (aPropertyElem.hasAttribute(MESSAGE_TYPE_ATTRIB)) 
      {
         aPropAlias.setMessageName(AeXmlUtil.createQName(aPropertyElem, aPropertyElem.getAttribute(MESSAGE_TYPE_ATTRIB)));
         aPropAlias.setPart(aPropertyElem.getAttribute(PART_ATTRIB));
      }
      else if (aPropertyElem.hasAttribute(ELEMENT_TYPE_ATTRIB)) 
      {
         aPropAlias.setElementName(AeXmlUtil.createQName(aPropertyElem, aPropertyElem.getAttribute(ELEMENT_TYPE_ATTRIB)));
      }
      else if (aPropertyElem.hasAttribute(COMPLEX_TYPE_ATTRIB)) 
      {
         aPropAlias.setTypeName(AeXmlUtil.createQName(aPropertyElem, aPropertyElem.getAttribute(COMPLEX_TYPE_ATTRIB)));
      }
      
      NodeList children = aPropertyElem.getElementsByTagNameNS(aPropertyElem.getNamespaceURI(), QUERY_ATTRIB);
      Element queryElement = (Element) children.item(0);
      if (queryElement != null && queryElement.getParentNode() == aPropertyElem)
      {
         aPropAlias.setQueryLanguage(queryElement.getAttribute(QUERY_LANGUAGE));
         aPropAlias.setQuery(AeXmlUtil.getText(queryElement));
      }
   }
}
 
