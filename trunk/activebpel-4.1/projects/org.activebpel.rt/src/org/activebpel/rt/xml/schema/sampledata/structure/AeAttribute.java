//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/sampledata/structure/AeAttribute.java,v 1.2 2007/02/20 21:57:1
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
 *  Model of a Schema attribute.
 */
public class AeAttribute extends AeBaseAttribute
{
   /** This attributes simpleType. */
   private QName mDataType;

   // Note that an attribute's "default", "prohibited" and "fixed" attributes are
   // mutually exclusive. The default value is "optional".
   
   /** Flag indicating if this attribute is optional. (default value if not specified. */
   private boolean mOptional = true;

   /** Flag indicating if this attribute is required. */
   private boolean mRequired;
   
   /** The default value for this attribute. */
   private String mDefault;

   /** The  fixed value for this attribute. **/
   private String mFixed;

   /** List of enumerated restrictions. (String objects). */
   private List mEnumRestrictions;
   
   /** The name of this attribute. Note: namespaceURI will be empty if this attribute is unqualified. */
   private QName mName;

   /**
    * Constructor.
    */
   public AeAttribute()
   {
   }

   /**
    * Gets this attributes simple type.
    * 
    * @return QName
    */
   public QName getDataType()
   {
      return mDataType;
   }
   
   /**
    * Sets this attributes simple type.
    * 
    * @param aDataType
    */
   public void setDataType(QName aDataType)
   {
      mDataType = aDataType;
   }
   
   /**
    * @return boolean
    */
   public boolean isRequired()
   {
      return mRequired;
   }
   
   /**
    * @param aRequired
    */
   public void setRequired(boolean aRequired)
   {
      mRequired = aRequired;
   }

   /**
    * Gets the default value for this attribute.  
    * Returns null if no default value was specified or the attribute is
    * required.
    * 
    * @return String
    */
   public String getDefaultValue()
   {
      return mDefault;
   }

   /**
    * Sets the default value for this attribute.
    * 
    * @param aDefault
    */
   public void setDefaultValue(String aDefault)
   {
      mDefault = aDefault;
   }

   /**
    * Returns true if the use of this attribute is optional.
    *
    * @return boolean true if attribute optional, otherwise it's required.
    */
   public boolean isOptional()
   {
      return mOptional;
   }

   /**
    * Sets the flag indicating that this attribute is optional.
    * 
    * @param aOptional true if optional, false if required.
    */
   public void setOptional(boolean aOptional)
   {
      mOptional = aOptional;
   }

   /**
    * Gets the fixed value for this attribute. Returns null if the 
    * attribute is required.
    * 
    * @return String.
    */
   public String getFixedValue()
   {
      return mFixed;
   }

   /**
    * Sets the fixed value for this attribute.
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

   /**
    * @see org.activebpel.rt.xml.schema.sampledata.structure.AeStructure#accept(org.activebpel.rt.xml.schema.sampledata.IAeSampleDataVisitor)
    */
   public void accept(IAeSampleDataVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.xml.schema.sampledata.structure.AeStructure#getType()
    */
   public int getType()
   {
      return AeStructure.ATTRIBUTE_TYPE;
   }

   /**
    * Gets the name of this attribute.
    * 
    * @return QName
    */
   public QName getName()
   {
      return mName;
   }
   
   /**
    * Sets the name of this attribute.
    * 
    * @param aName
    */
   public void setName(QName aName)
   {
      mName = aName;
   }
}
 
