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

import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.ser.BaseDeserializerFactory;

import javax.xml.namespace.QName;
import javax.xml.rpc.JAXRPCException;

/**
 * A base class for AE deserializer factories. This class causes the general purpose and specialized cases to
 * be skipped (since they are never used).
 */
public class AeBaseDeserializerFactory extends BaseDeserializerFactory
{
   /**
    * Creates a deserializer factory with the given java type and xml type.
    */
   public AeBaseDeserializerFactory(Class aClass, QName xmlType, Class javaType)
   {
      super(aClass, xmlType, javaType);
   }

   /**
    * @see org.apache.axis.encoding.ser.BaseDeserializerFactory#getDeserializerAs(java.lang.String)
    */
   public javax.xml.rpc.encoding.Deserializer getDeserializerAs(String aMechanismType) throws JAXRPCException
   {
      return super.getDeserializerAs(aMechanismType);
   }

   /**
    * Overrides method to return null, since we know that none of our schema types have
    * specialized deserializers.  This was found when profiling on .NET - Axis will look for
    * the specialized deserializer using introspection, and will throw a NoSuchMethodException
    * when it's invariably not found.  Exceptions are expensive in the .NET port, so this 
    * change was made to reduce the number of them.
    * 
    * @see org.apache.axis.encoding.ser.BaseDeserializerFactory#getSpecialized(java.lang.String)
    */
   protected Deserializer getSpecialized(String aMechanismType)
   {
      return null;
   }
}
