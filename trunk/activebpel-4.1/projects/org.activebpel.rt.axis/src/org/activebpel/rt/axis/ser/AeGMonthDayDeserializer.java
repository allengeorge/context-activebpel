//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/ser/AeGMonthDayDeserializer.java,v 1.2 2006/09/07 15:19:5
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
package org.activebpel.rt.axis.ser; 

import javax.xml.namespace.QName;

import org.activebpel.rt.xml.schema.AeSchemaMonthDay;
import org.apache.axis.encoding.ser.SimpleDeserializer;

/**
 * Deserializer for schema calendar field gMonthDay 
 */
public class AeGMonthDayDeserializer extends SimpleDeserializer
{
   /**
    * The Deserializer is constructed with the xmlType and javaType
    */
   public AeGMonthDayDeserializer(Class javaType, QName xmlType)
   {
      super(javaType, xmlType);
   }

   /**
    * @see org.activebpel.rt.axis.ser.AeAbstractSchemaTypeDeserializer#makeValueInternal(java.lang.String)
    */
   protected Object makeValueInternal(String aSource)
   {
      return new AeSchemaMonthDay(aSource);
   }
}
 
