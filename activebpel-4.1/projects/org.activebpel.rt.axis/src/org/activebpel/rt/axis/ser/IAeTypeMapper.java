//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/ser/IAeTypeMapper.java,v 1.1 2005/04/20 19:22:4
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

/**
 * Provides a single interface for registering types. In Axis, the MessageContext 
 * and Call object have a different way of registering types.
 */
public interface IAeTypeMapper
{
   /**
    * Registers the serializer/deserializer with Axis' type mapping registry.
    * 
    * @param aJavaType
    * @param aQName
    * @param aSerializerFactory
    * @param aDeserializerFactory
    */
   public void register(Class aJavaType, QName aQName, SerializerFactory aSerializerFactory, DeserializerFactory aDeserializerFactory);
}
 
