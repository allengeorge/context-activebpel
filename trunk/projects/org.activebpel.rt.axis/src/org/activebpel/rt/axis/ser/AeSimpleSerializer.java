//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/ser/AeSimpleSerializer.java,v 1.1 2005/04/15 13:44:0
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

import java.io.IOException;

import javax.xml.namespace.QName;

import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.ser.SimpleSerializer;
import org.xml.sax.Attributes;

/**
 * Subclass of Axis's SimpleSerializer that unwraps the value it was passed before
 * calling super.serialize(). This class also overcomes a bug in Axis's implementation
 * where it was not clearing the attributes it set and would pollute the attributes
 * object for the next Serializer invoked. 
 */
public class AeSimpleSerializer extends SimpleSerializer
{

   /**
    * Ctor
    * 
    * @param javaType
    * @param xmlType
    */
   public AeSimpleSerializer(Class javaType, QName xmlType)
   {
      super(javaType, xmlType);
   }

   /**
    * Ctor 
    * 
    * @param aJavaType
    * @param aXmlType
    * @param aTypeDesc
    */
   public AeSimpleSerializer(Class aJavaType, QName aXmlType, TypeDesc aTypeDesc)
   {
      super(aJavaType, aXmlType, aTypeDesc);
   }

   /**
    * The value should be an AeSimpleValueWrapper.
    * 
    * @see org.apache.axis.encoding.Serializer#serialize(javax.xml.namespace.QName, org.xml.sax.Attributes, java.lang.Object, org.apache.axis.encoding.SerializationContext)
    */
   public void serialize(QName aName, Attributes attributes, Object aValue,
         SerializationContext aContext) throws IOException
   {
      Object value = ((AeSimpleValueWrapper) aValue).getDelegate();
      super.serialize(aName, attributes, value, aContext);
   }
}
