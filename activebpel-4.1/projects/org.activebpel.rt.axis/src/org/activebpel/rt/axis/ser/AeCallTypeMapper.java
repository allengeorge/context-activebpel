//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/ser/AeCallTypeMapper.java,v 1.1 2005/04/20 19:22:4
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
import javax.xml.rpc.encoding.DeserializerFactory;
import javax.xml.rpc.encoding.SerializerFactory;

import org.apache.axis.client.Call;

/**
 * Implements the type mapper interface for an Axis Call object. This object only
 * works with Axis specific Serializer and Deserializer factories.
 */
public class AeCallTypeMapper implements IAeTypeMapper
{
   /** Call that contains the type mapping info */
   private Call mCall;
   
   /**
    * Ctor creates the mapper with the call object.
    * 
    * @param aCall
    */
   public AeCallTypeMapper(Call aCall)
   {
      mCall = aCall;
   }
   
   /**
    * @see org.activebpel.rt.axis.ser.IAeTypeMapper#register(java.lang.Class, javax.xml.namespace.QName, javax.xml.rpc.encoding.SerializerFactory, javax.xml.rpc.encoding.DeserializerFactory)
    */
   public void register(Class aJavaType, QName aQName,
         SerializerFactory aSerializerFactory,
         DeserializerFactory aDeserializerFactory)
   {
      getCall().registerTypeMapping(aJavaType, aQName, 
            (org.apache.axis.encoding.SerializerFactory)aSerializerFactory, 
            (org.apache.axis.encoding.DeserializerFactory)aDeserializerFactory);
   }

   /**
    * Getter for the call
    */
   protected Call getCall()
   {
      return mCall;
   }

}
 
