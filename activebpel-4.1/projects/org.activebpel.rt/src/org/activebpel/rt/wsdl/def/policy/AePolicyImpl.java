//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/policy/AePolicyImpl.java,v 1.1 2007/07/27 18:08:5
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
package org.activebpel.rt.wsdl.def.policy;

import java.util.ArrayList;
import java.util.List;

import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.xml.namespace.QName;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.IAePolicyConstants;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Implementation representing a policy extension defintion within a WSDL file
 */
public class AePolicyImpl implements IAePolicy, IAePolicyConstants
{
   private Element mPolicyElement;
   private String mId;
   private Boolean mRequired;
   private QName mElementType = POLICY_QNAME;
   private List mExtElements;

   /**
    * No-arg constructor
    */
   public AePolicyImpl()
   {
      
   }

   /**
    * Constructs an instance from an element
    * 
    * @param aElement
    */
   public AePolicyImpl(Element aElement)
   {
      setPolicyElement(aElement);
   }

   /**
    * @see org.activebpel.rt.wsdl.def.policy.IAePolicy#getPolicyElement()
    */
   public Element getPolicyElement()
   {
      return mPolicyElement;
   }

   /**
    * @see org.activebpel.rt.wsdl.def.policy.IAePolicy#getReferenceId()
    */
   public String getReferenceId()
   {
      return mId;
   }

   /**
    * @see org.activebpel.rt.wsdl.def.policy.IAePolicy#setPolicyElement(org.w3c.dom.Element)
    */
   public void setPolicyElement(Element aPolicy)
   {
      mPolicyElement = AeXmlUtil.cloneElement(aPolicy);
      setReferenceId(aPolicy.getAttributeNS(IAeConstants.WSU_NAMESPACE_URI, WSU_ID_ATTRIBUTE));
      
      getExtensibilityElements().clear();
      NodeList nodes = aPolicy.getChildNodes();     
      for (int i = 0; i < nodes.getLength(); i++)
      {
         Node node = nodes.item(i);
         if (node.getNodeType() == Node.ELEMENT_NODE)
         {
            ExtensibilityElement ext = null;
            if (node.getLocalName().equals(IAePolicyReference.POLICY_REFERENCE_ELEMENT))
            {
               ext = new AePolicyRefImpl((Element) node);
            }
            else
            {
               UnknownExtensibilityElement unk = new UnknownExtensibilityElement();
               unk.setElementType(new QName(node.getNamespaceURI(), node.getLocalName()));
               unk.setElement((Element) node);
               ext = unk;
            }
            
            addExtensibilityElement(ext);
         }
      }
      
   }

   /**
    * @see org.activebpel.rt.wsdl.def.policy.IAePolicy#setReferenceId(java.lang.String)
    */
   public void setReferenceId(String aId)
   {
      mId = aId;
   }

   /**
    * @see javax.wsdl.extensions.ExtensibilityElement#getElementType()
    */
   public QName getElementType()
   {
      return mElementType;
   }

   /**
    * @see javax.wsdl.extensions.ExtensibilityElement#getRequired()
    */
   public Boolean getRequired()
   {
      return mRequired;
   }

   /**
    * @see javax.wsdl.extensions.ExtensibilityElement#setElementType(javax.xml.namespace.QName)
    */
   public void setElementType(QName aElementType)
   {
      mElementType = aElementType;
   }

   /**
    * @see javax.wsdl.extensions.ExtensibilityElement#setRequired(java.lang.Boolean)
    */
   public void setRequired(Boolean aRequired)
   {
      mRequired = aRequired;
   }

   /**
    * @see javax.wsdl.extensions.ElementExtensible#addExtensibilityElement(javax.wsdl.extensions.ExtensibilityElement)
    */
   public void addExtensibilityElement(ExtensibilityElement aExtElement)
   {
      getExtensibilityElements().add(aExtElement);
   }

   /**
    * @see javax.wsdl.extensions.ElementExtensible#getExtensibilityElements()
    */
   public List getExtensibilityElements()
   {
      if (mExtElements == null)
      {
         mExtElements = new ArrayList();
      }
      return mExtElements;
   }
}
