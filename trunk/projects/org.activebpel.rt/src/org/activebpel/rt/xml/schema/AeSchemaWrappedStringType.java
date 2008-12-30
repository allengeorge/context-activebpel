//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/AeSchemaWrappedStringType.java,v 1.2 2006/09/07 14:41:1
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


/**
 * Base class for schema types that have little or no parsing and simply wrap a string.
 * No validation is done on the string method. 
 */
public abstract class AeSchemaWrappedStringType implements IAeSchemaType
{
   /** uri that we're wrapping */
   protected String mValue;
   
   /**
    * Ctor takes its wrapped value
    * @param aValue
    */
   public AeSchemaWrappedStringType(String aValue)
   {
      mValue = aValue;
   }

   /**
    * Return the URI
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return mValue;
   }

   /**
    * @return Returns the value.
    */
   protected String getValue()
   {
      return mValue;
   }

   /**
    * @param aValue The value to set.
    */
   protected void setValue(String aValue)
   {
      mValue = aValue;
   }
}
 
