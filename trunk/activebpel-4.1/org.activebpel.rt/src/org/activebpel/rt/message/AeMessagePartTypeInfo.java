//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/message/AeMessagePartTypeInfo.java,v 1.2 2006/08/03 23:16:0
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
package org.activebpel.rt.message; 

import javax.wsdl.Part;
import javax.xml.namespace.QName;

import org.exolab.castor.xml.schema.XMLType;

/**
 * Lightweight version of a WSDL Message Part that extracts the basic 
 * information from a javax.wsdl.Part and allows it to be cached without
 * the overhead of keeping the entire WSDL in memory.
 */
public class AeMessagePartTypeInfo
{
   /** name of the part */
   private String mName;
   /** QName of the element (mutually exclusive w/ type)*/
   private QName mElementName;
   /** QName of the type (mutually exclusive w/ type)*/
   private QName mTypeName;
   /** XMLType for the part */
   private XMLType mXMLType;
   
   /**
    * Simple copy constructor
    * @param aPart
    */
   public AeMessagePartTypeInfo(Part aPart, XMLType aType)
   {
      setName(aPart.getName());
      setElementName(aPart.getElementName());
      setTypeName(aPart.getTypeName());
      setXMLType(aType);
   }
   
   /**
    * Returns true if the part is an element
    */
   public boolean isElement()
   {
      return getElementName() != null;
   }
   
   /**
    * @see javax.wsdl.Part#getElementName()
    */
   public QName getElementName()
   {
      return mElementName;
   }

   /**
    * @see javax.wsdl.Part#getName()
    */
   public String getName()
   {
      return mName;
   }

   /**
    * @see javax.wsdl.Part#getTypeName()
    */
   public QName getTypeName()
   {
      return mTypeName;
   }

   /**
    * @param aElementName The elementName to set.
    */
   protected void setElementName(QName aElementName)
   {
      mElementName = aElementName;
   }

   /**
    * @param aName The name to set.
    */
   protected void setName(String aName)
   {
      mName = aName;
   }

   /**
    * @param aTypeName The typeName to set.
    */
   protected void setTypeName(QName aTypeName)
   {
      mTypeName = aTypeName;
   }

   /**
    * @return Returns the xMLType.
    */
   public XMLType getXMLType()
   {
      return mXMLType;
   }

   /**
    * @param aType The xMLType to set.
    */
   protected void setXMLType(XMLType aType)
   {
      mXMLType = aType;
   }
   
   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      
      sb.append("AeMessagePartTypeInfo part: ").append(getName()); //$NON-NLS-1$
      if (isElement())
         sb.append(" element: ").append(getElementName()); //$NON-NLS-1$
      else
         sb.append(" type: ").append(getTypeName()); //$NON-NLS-1$
      sb.append(" XMLType: ").append(getXMLType()); //$NON-NLS-1$
      
      return sb.toString();
   }
}
 
