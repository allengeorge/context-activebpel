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

import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.ser.SimpleSerializer;
import org.apache.axis.utils.Messages;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

/**
 * A base class for AE schema-type serializers.
 */
public class AeBaseSerializer extends SimpleSerializer
{
   /**
    * C'tor.
    */
   public AeBaseSerializer(Class javaType, QName xmlType)
   {
      super(javaType, xmlType);
   }

   /**
    * Overrides the base implementation in order to bypass the call to getObjectAttributes().  We
    * know that our types do not have any additional JavaBean style attributes that we want to 
    * add, so we can skip that step.  Unfortunately, getObjectAttributes() is private, so we 
    * couldn't simply override it.
    * 
    * @see org.apache.axis.encoding.Serializer#serialize(javax.xml.namespace.QName, org.xml.sax.Attributes,
    *      java.lang.Object, org.apache.axis.encoding.SerializationContext)
    */
   public void serialize(QName name, Attributes attributes, Object value, SerializationContext context)
         throws IOException
   {
      if (value != null && value.getClass() == java.lang.Object.class)
      {
         throw new IOException(Messages.getMessage("cantSerialize02")); //$NON-NLS-1$
      }

      AttributesImpl attrs;
      if (attributes == null)
      {
         attrs = new AttributesImpl();
      }
      else if (attributes instanceof AttributesImpl)
      {
         attrs = (AttributesImpl) attributes;
      }
      else
      {
         attrs = new AttributesImpl(attributes);
      }

      String valueStr = null;
      if (value != null)
      {
         valueStr = getValueAsString(value, context);
      }
      context.startElement(name, attrs);
      if (valueStr != null)
      {
         context.writeSafeString(valueStr);
      }
      context.endElement();
   }

   /**
    * @see org.apache.axis.encoding.ser.SimpleSerializer#getValueAsString(java.lang.Object, org.apache.axis.encoding.SerializationContext)
    */
   public String getValueAsString(Object aValue, SerializationContext aContext)
   {
      return aValue.toString();
   }
}
