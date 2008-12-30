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
package org.activebpel.rt.xml.schema;

import org.exolab.castor.xml.schema.ComplexType;

/**
 * Accepts compatibles types. A type is deemed to be compatible if any of the 
 * following are true:
 * - type is the same as the target's ComplexType member data
 * - type is derived from the target's ComplexType
 * 
 * @return List of compatible ComplexType objects.
 */
public class AeAcceptAllCompatibleComplexTypes implements IAeComplexTypeFilter
{
   /** provides context for determining compatibility */
   private ComplexType mComplexType;
   
   /**
    * Ctor
    * @param aComplexType
    */
   public AeAcceptAllCompatibleComplexTypes(ComplexType aComplexType)
   {
      setComplexType(aComplexType);
   }
   
   /**
    * @see org.activebpel.rt.xml.schema.IAeComplexTypeFilter#accept(org.exolab.castor.xml.schema.ComplexType)
    */
   public boolean accept(ComplexType aComplexType)
   {
      // rule one: type is the same as the target's ComplexType member data
      if (aComplexType == getComplexType())
         return true;
      
      // rule two: type is derived from the target's ComplexType
      if (AeSchemaUtil.isTypeDerivedFromType(aComplexType, getComplexType()))
      {
         return true;
      }
      
      return false;
   }

   /**
    * @return the complexType
    */
   public ComplexType getComplexType()
   {
      return mComplexType;
   }

   /**
    * @param aComplexType the complexType to set
    */
   public void setComplexType(ComplexType aComplexType)
   {
      mComplexType = aComplexType;
   }
}
