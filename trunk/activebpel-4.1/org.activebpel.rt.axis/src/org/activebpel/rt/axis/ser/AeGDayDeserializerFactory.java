//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/ser/AeGDayDeserializerFactory.java,v 1.1 2006/05/26 21:38:1
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

/**
 * Factory for creating deserializer for calendar field gDay 
 */
public class AeGDayDeserializerFactory extends AeBaseDeserializerFactory
{

   /**
    * Creates a deserializer factory with the given java type and xml type.
    */
   public AeGDayDeserializerFactory(Class javaType, QName xmlType)
   {
      super(AeGDayDeserializer.class, xmlType, javaType);
   }

}
 
