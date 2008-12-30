//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/AeTypeMappingTuple.java,v 1.2 2005/06/22 17:10:1
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
package org.activebpel.rt.axis.bpel; 

import javax.xml.namespace.QName;

import org.activebpel.rt.util.AeUtil;

/**
 * Stores the basic info necessary in order to register a custom serializer or deserializer
 * for a message part.
 */
public class AeTypeMappingTuple
{
   /** true if the type is a derived simple type, false if it's a complex type or schema element */
   private boolean mDerivedSimpleType;
   /** QName for the schema type (whether its a complex type, derived simple type or element) */
   private QName mType;
   
   /**
    * Creates a tuple with the qname only
    * 
    * @param aType
    */
   public AeTypeMappingTuple(QName aType)
   {
      setType(aType);
   }
   
   /**
    * Creates a tuple with the specified values
    * 
    * @param aType
    * @param aDerivedSimpleType
    */
   public AeTypeMappingTuple(QName aType, boolean aDerivedSimpleType)
   {
      setType(aType);
      setDerivedSimpleType(aDerivedSimpleType);
   }
   
   /**
    * @return Returns the derivedSimpleType.
    */
   protected boolean isDerivedSimpleType()
   {
      return mDerivedSimpleType;
   }

   /**
    * @param aDerivedSimpleType The derivedSimpleType to set.
    */
   protected void setDerivedSimpleType(boolean aDerivedSimpleType)
   {
      mDerivedSimpleType = aDerivedSimpleType;
   }
   
   /**
    * @return Returns the type.
    */
   protected QName getType()
   {
      return mType;
   }
   
   /**
    * @param aType The type to set.
    */
   protected void setType(QName aType)
   {
      if (aType == null)
         throw new IllegalArgumentException(AeMessages.getString("AeTypeMappingTuple.NULL")); //$NON-NLS-1$
      mType = aType;
   }
   
   /**
    * Compares that member data to see if the two tuples match
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObject)
   {
      if (aObject instanceof AeTypeMappingTuple)
      {
         AeTypeMappingTuple other = (AeTypeMappingTuple) aObject;
         return AeUtil.compareObjects(getType(), other.getType()) &&
            isDerivedSimpleType() == other.isDerivedSimpleType();
      }
      return super.equals(aObject);
   }
   
   /**
    * Uses the hashcode for the type
    * 
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return getType().hashCode();
   }
}
 
