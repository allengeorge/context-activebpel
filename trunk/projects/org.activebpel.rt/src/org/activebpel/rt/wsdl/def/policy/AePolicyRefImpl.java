//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/policy/AePolicyRefImpl.java,v 1.1 2007/07/27 18:08:5
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

import javax.xml.namespace.QName;

import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Implementation representing a wsp:PolicyReference extensibility element in a WSDL document
 */
public class AePolicyRefImpl implements IAePolicyReference
{
   private static final String ID_SEPARATOR = "#"; //$NON-NLS-1$
   
   private String mBaseURI;
   private String mReferenceId;
   private Boolean mRequired;
   private QName mElementType = POLICY_REF_QNAME;
   private String mTargetNamespace;

   /**
    * No-arg constructor
    */
   public AePolicyRefImpl()
   {
      
   }
   
   /**
    * Create a new instance from an element
    * @param aElement
    */
   public AePolicyRefImpl(Element aElement)
   {
      setReferenceURI(aElement.getAttribute(IAePolicyReference.URI_ATTRIBUTE)); 
      Document owner = aElement.getOwnerDocument();
      if (owner != null)
      {
         // get the targetNamespace attribute from the owner document in case
         // we need to lookup the wsdl for a local reference 
         setTargetNamespace(owner.getDocumentElement().getAttribute("targetNamespace")); //$NON-NLS-1$
      }
   }
   
   /**
    * @see org.activebpel.rt.wsdl.def.policy.IAePolicyReference#getNamespaceURI()
    */
   public String getNamespaceURI()
   {
      return mBaseURI;
   }

   /**
    * @see org.activebpel.rt.wsdl.def.policy.IAePolicyReference#getReferenceId()
    */
   public String getReferenceId()
   {
      return mReferenceId;
   }

   /**
    * @see org.activebpel.rt.wsdl.def.policy.IAePolicyReference#getReferenceURI()
    */
   public String getReferenceURI()
   {
      return getNamespaceURI() + ID_SEPARATOR + getReferenceId();
   }

   /**
    * @see org.activebpel.rt.wsdl.def.policy.IAePolicyReference#setNamespaceURI(java.lang.String)
    */
   public void setNamespaceURI(String aBaseURI)
   {
      mBaseURI = aBaseURI;
   }

   /**
    * @see org.activebpel.rt.wsdl.def.policy.IAePolicyReference#setReferenceId(java.lang.String)
    */
   public void setReferenceId(String aId)
   {
      mReferenceId = aId;
   }

   /**
    * @see org.activebpel.rt.wsdl.def.policy.IAePolicyReference#setReferenceURI(java.lang.String)
    */
   public void setReferenceURI(String aURI)
   {
      if (aURI == null)
      {
         setNamespaceURI(null);
         setReferenceId(null);
         return;
      }
      
      int index = aURI.indexOf(ID_SEPARATOR);
      if (index == 0)
      {
         setNamespaceURI(null);
         setReferenceId(aURI.substring(index + 1));
      }
      if (index == -1)
      {
         setNamespaceURI(null);
         setReferenceId(aURI);
      }
      else
      {
         setNamespaceURI(aURI.substring(0, index));
         setReferenceId(aURI.substring(index + 1));
      }
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
    * @see org.activebpel.rt.wsdl.def.policy.IAePolicyReference#isLocalReference()
    */
   public boolean isLocalReference()
   {
      return AeUtil.isNullOrEmpty(getNamespaceURI());
   }

   /**
    * @see org.activebpel.rt.wsdl.def.policy.IAePolicyReference#getTargetNamespace()
    */
   public String getTargetNamespace()
   {
      return mTargetNamespace;
   }
   
   /**
    * @return the target namespace of the reference's owner document
    */
   public void setTargetNamespace(String aNamespace)
   {
      mTargetNamespace = aNamespace;
   }
}
