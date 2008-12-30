// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AePropertyImpl.java,v 1.6 2006/06/26 16:46:4
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

import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;

import org.activebpel.rt.util.AeUtil;

/**
 * This class represents an implementation mapping to a Message Property
 * extension within a WSDL document. It contains information about operations
 * associated with this Message Property element.
 */
public class AePropertyImpl
   implements ExtensibilityElement, IAeProperty, IAeBPELExtendedWSDLConst
{
   /** The QName of this extension element. */
   private QName mElementType;

   /** Indicates if the semantics of this extension are required. */
   private Boolean mRequired;

   /** The name of this Property. */
   private QName mName;

   /** The type of this property or null if its an element. */
   private QName mType;
   
   /** The name of the element property or null if its a type */
   private QName mElementName;

   /**
    * Constructor.  This contructor is generally used for modeling a new
    * Message Property extension element. I.e. this extension implementation is
    * not being generated via a deserializer.
    */
   public AePropertyImpl()
   {
      setElementType(new QName(PROPERTY_1_1_NAMESPACE, PROPERTY_TAG));
   }

   /**
    * Set whether or not the semantics of this extension are required.
    * 
    * @param aRequired
    */
   public void setRequired(Boolean aRequired)
   {
      mRequired = aRequired;
   }

   /**
    * Get whether or not the semantics of this extension are required.
    * 
    * @return Boolean
    */
   public Boolean getRequired()
   {
      return mRequired;
   }
   
   /**
    * Set the QName of this Property element.
    * 
    * @param aElementType
    */
   public void setElementType(QName aElementType)
   {
      mElementType = aElementType;
   }
   
   /**
    * Get the QName of this Property element.
    * 
    * @return QName
    */
   public QName getElementType()
   {
      return mElementType;
   }

   /**
    * Get this Message Property's name.
    * 
    * @return QName
    */
   public QName getQName()
   {
      return mName;
   }

   /**
    * Set this Message Property name.
    * 
    * @param aName
    */
   public void setQName(QName aName)
   {
      mName = aName;
   }
   
   /**
    * Get this Message Property Type QName.
    * 
    * @return QName
    */
   public QName getTypeName()
   {
      return mType;
   }

   /**
    * Set this Message Property Type QName.
    * 
    * @param aType
    */
   public void setTypeName(QName aType)
   {
      mType = aType;
   }
   
   /**
    * @see org.activebpel.rt.wsdl.def.IAeProperty#getElementName()
    */
   public QName getElementName()
   {
      return mElementName;
   }

   /**
    * @see org.activebpel.rt.wsdl.def.IAeProperty#setElementName(javax.xml.namespace.QName)
    */
   public void setElementName(QName aQName)
   {
      mElementName = aQName;
   }

   /**
    * Override the comparison operator to determine if objects are equal.  
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObj)
   {
      // Make sure we have the proper object for comparison
      if (! (aObj instanceof AePropertyImpl))
         return false;
      
      AePropertyImpl target = (AePropertyImpl)aObj;
      
      return AeUtil.compareObjects(mElementType, target.getElementType()) &&
             AeUtil.compareObjects(mType, target.getTypeName()) &&
             AeUtil.compareObjects(mName, target.getQName()) &&
             AeUtil.compareObjects(mElementName, target.getElementName());
   }
}
