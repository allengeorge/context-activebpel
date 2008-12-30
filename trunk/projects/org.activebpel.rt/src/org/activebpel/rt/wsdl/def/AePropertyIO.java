// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AePropertyIO.java,v 1.9 2006/10/25 16:07:0
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

import java.io.PrintWriter;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.xml.namespace.QName;

import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * This class serializes and deserializes a Message Property extension
 * implementation. 
 */
public class AePropertyIO extends AeWSDLExtensionIO implements IAeBPELExtendedWSDLConst
{
   /**
    * Serialize a Message Property extension element into a the PrintWriter.
    * 
    * @param aParentType a class object indicating where in the WSDL document
    * this extensibility element was encountered. 
    * @param aQname the QName of this extension element.
    * @param aExtElement the extensibility element to be serialized.
    * @param aWriter the PrintWriter where we're serializing to.
    * @param aDefinition the Definition that this extensibility element was
    * encountered in.
    * @param aExtReg the associated ExtensionRegistry.
    */
   public void marshall(
      Class aParentType,
      QName aQname,
      ExtensibilityElement aExtElement,
      PrintWriter aWriter,
      Definition aDefinition,
      ExtensionRegistry aExtReg)
      throws WSDLException
   {
      IAeProperty lProperty = (IAeProperty)aExtElement;

      Element lPropDomElem = createElement(aQname);

      // Determine the Namespace prefix of the Property extension.
      String preferredPrefix = AeWSDLPrefixes.getPropertyPrefix(aQname.getNamespaceURI());
      String lPrefix = getPrefixOrCreateLocally(aQname.getNamespaceURI(), aDefinition, lPropDomElem, preferredPrefix); 
      lPropDomElem.setPrefix(lPrefix);
      lPropDomElem.setAttribute(NAME_ATTRIB, lProperty.getQName().getLocalPart());

      writePropertyData(lProperty, aDefinition, lPropDomElem);
      
      writeElement(lPropDomElem, aWriter);
   }

   /**
    * Writes the property's type to the element
    * @param aProperty
    * @param aDefinition
    * @param aPropElement
    */
   protected void writePropertyData(IAeProperty aProperty, Definition aDefinition, Element aPropElement)
   {
      String lTypeQName = toString(aDefinition, aProperty.getTypeName());
      aPropElement.setAttribute(TYPE_ATTRIB, lTypeQName);
   }

   /**
    * Deserialize a Message Property extension element into a
    * AePropertyImpl implementation object.
    * 
    * @param aParentType a class object indicating where in the WSDL document
    * this extensibility element was encountered. 
    * @param aQName the QName of this extension element.
    * @param aPropertyElem the extensibility DOM element to be deserialized.
    * @param aDefinition the Definition that this extensibility element was
    * encountered in.
    * @param aExtReg the associated ExtensionRegistry.
    * 
    * @return ExtensibilyElement the implementation mapping class for this
    * extension.
    */
   public ExtensibilityElement unmarshall(
      Class aParentType,
      QName aQName,
      Element aPropertyElem,
      Definition aDefinition,
      ExtensionRegistry aExtReg)
      throws WSDLException
   {
      // Create a new Property implementation object.
      AePropertyImpl prop = new AePropertyImpl();
      prop.setQName( new QName(aDefinition.getTargetNamespace(), aPropertyElem.getAttribute(NAME_ATTRIB)) );
      readPropertyData(aPropertyElem, prop);
      prop.setRequired(Boolean.TRUE);
      prop.setElementType(aQName);
      return prop;
   }

   /**
    * Reads the type QName
    * @param aPropertyElem
    * @param prop
    */
   protected void readPropertyData(Element aPropertyElem, AePropertyImpl prop)
   {
      prop.setTypeName( AeXmlUtil.createQName(aPropertyElem, aPropertyElem.getAttribute(TYPE_ATTRIB) ));
   }
}
