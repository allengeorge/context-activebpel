//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/sampledata/structure/AeSimpleElement.java,v 1.1 2007/02/17 21:12:2
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

import java.util.List;

import javax.xml.namespace.QName;

import org.activebpel.rt.xml.schema.sampledata.IAeSampleDataVisitor;

/**
 * Model class for a Schema Simple Type.
 */
public class AeSimpleElement extends AeBaseElement
{
   /** The data type of this element. */
   private QName mDataType;
   
   // Note that a simpleType's "default" and "fixed" attributes are optional and mutually exclusive. 
   
   /** The default value for this element. */
   private String mDefaultValue;

   /** The  fixed value for this element. **/
   private String mFixed;

   /** List of enumerated restrictions. (String objects). */
   private List mEnumRestrictions;
   

   /**
    * Constructor.
    */
   public AeSimpleElement()
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
    * @return QName
    */
   public QName getDataType()
   {
      return mDataType;
   }

   /**
    * @param aDataType
    */
   public void setDataType(QName aDataType)
   {
      mDataType = aDataType;
   }

   /**
    * @return String
    */
   public String getDefaultValue()
   {
      return mDefaultValue;
   }

   /**
    * @param aDefaultValue
    */
   public void setDefaultValue(String aDefaultValue)
   {
      mDefaultValue = aDefaultValue;
   }
   /**
    * Gets the fixed value for this element. 
    * 
    * @return String.
    */
   public String getFixedValue()
   {
      return mFixed;
   }

   /**
    * Sets the fixed value for this element.
    * 
    * @param aFixed.
    */
   public void setFixedValue(String aFixed)
   {
      mFixed = aFixed;
   }

   /**
    * @return List
    */
   public List getEnumRestrictions()
   {
      return mEnumRestrictions;
   }

   /**
    * @param aEnumRestrictions.
    */
   public void setEnumRestrictions(List aEnumRestrictions)
   {
      mEnumRestrictions = aEnumRestrictions;
   }

}
 
