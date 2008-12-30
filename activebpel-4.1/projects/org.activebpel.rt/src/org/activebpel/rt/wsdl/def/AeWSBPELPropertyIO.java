//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AeWSBPELPropertyIO.java,v 1.2 2006/06/26 16:46:4
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

import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Reads and writes WS BPEL property elements
 */
public class AeWSBPELPropertyIO extends AePropertyIO
{
   /**
    * @see org.activebpel.rt.wsdl.def.AePropertyIO#readPropertyData(org.w3c.dom.Element, org.activebpel.rt.wsdl.def.AePropertyImpl)
    */
   protected void readPropertyData(Element aPropertyElem, AePropertyImpl aProp)
   {
      if (aPropertyElem.hasAttribute(ELEMENT_TYPE_ATTRIB))
      {
         aProp.setElementName(AeXmlUtil.createQName(aPropertyElem, aPropertyElem.getAttribute(ELEMENT_TYPE_ATTRIB)));
      }
      else
      {
         super.readPropertyData(aPropertyElem, aProp);
      }
   }

   /**
    * Writes the property's type or element QName to 
    * @see org.activebpel.rt.wsdl.def.AePropertyIO#writePropertyData(org.activebpel.rt.wsdl.def.IAeProperty, javax.wsdl.Definition, org.w3c.dom.Element)
    */
   protected void writePropertyData(IAeProperty aProperty, Definition aDefinition, Element aPropElement)
   {
      if (aProperty.getElementName() != null)
      {
         aPropElement.setAttribute(ELEMENT_TYPE_ATTRIB, toString(aDefinition, aProperty.getElementName()));
      }
      else
      {
         super.writePropertyData(aProperty, aDefinition, aPropElement);
      }
   }
}
 
