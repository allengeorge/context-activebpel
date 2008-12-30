//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/sampledata/structure/AeComplexElement.java,v 1.3 2007/02/27 22:41:5
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
package org.activebpel.rt.xml.schema.sampledata.structure; 

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import org.activebpel.rt.xml.schema.sampledata.IAeSampleDataVisitor;

/**
 *  Model of a Schema complexType definition.
 */
public class AeComplexElement extends AeBaseElement
{
   /** List of Attributes for this complexType. */
   private List mAttributes = new LinkedList();

   /** QName for the xsi:type attribute. If set, then we should output the type with the element */
   private QName mXsiType;
   
   /** The mixed flag for this complexType. */
   private boolean mMixed;

   /** Flag indicating if this complexType has simpleContent of simple type. */
   private boolean mSimpleContentType;
   
   /** The data type if simpleContent. */
   private QName mDataType;

   
   /**
    * Constructor.
    */
   public AeComplexElement()
   {
   }

   /**
    * @see org.activebpel.rt.xml.schema.sampledata.structure.AeStructure#accept(org.activebpel.rt.xml.schema.sampledata.IAeSampleDataVisitor)
    */
   public void accept(IAeSampleDataVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @return List
    */
   public List getAttributes()
   {
      return mAttributes;
   }

   /**
    * @param aAttributes.
    */
   public void setAttributes(List aAttributes)
   {
      mAttributes.clear();
      
      for (Iterator iter = aAttributes.iterator(); iter.hasNext();)
      {
         AeBaseAttribute attrib = (AeBaseAttribute) iter.next();
         addAttribute(attrib);
      }
   }
   
   /**
    * Adds the attribute to the element
    * @param aAttribute
    */
   public void addAttribute(AeBaseAttribute aAttribute)
   {
      aAttribute.setParent(this);
      getAttributes().add(aAttribute);
   }

   /**
    * @return boolean
    */
   public boolean isMixed()
   {
      return mMixed;
   }

   /**
    * @param aIsMixed.
    */
   public void setMixed(boolean aIsMixed)
   {
      mMixed = aIsMixed;
   }

   /**
    * @return the type
    */
   public QName getXsiType()
   {
      return mXsiType;
   }

   /**
    * @param aType the type to set
    */
   public void setXsiType(QName aType)
   {
      mXsiType = aType;
   }

   /**
    * Gets the flag indicating if this complexType has simpleContents of simple type.
    * 
    * @return boolean
    */
   public boolean isSimpleContentType()
   {
      return mSimpleContentType;
   }

   /**
    * Sets the flag indicating if this complexType has simpleContents of simple type. 
    *
    * @param aSimpleContextType
    */
   public void setSimpleContentType(boolean aSimpleContextType)
   {
      mSimpleContentType = aSimpleContextType;
   }

   /**
    * Gets the data type if simpleContents
    * @return
    */
   public QName getDataType()
   {
      return mDataType;
   }

   /**
    * Sets the data type if simpleContents
    * @param aDataType
    */
   public void setDataType(QName aDataType)
   {
      mDataType = aDataType;
   }
   
}
 
