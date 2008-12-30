// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/AeRPCLiteralSerializer.java,v 1.4 2006/11/07 20:14:5
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

import java.io.IOException;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.ser.ElementSerializer;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;

/**
 * Custom serializer for axis that handles complex types over rpc literal.
 */
public class AeRPCLiteralSerializer extends ElementSerializer
{
   /**
    * Constructor
    */
   public AeRPCLiteralSerializer()
   {
   }

   /**
    * @see org.apache.axis.encoding.Serializer#serialize(javax.xml.namespace.QName, org.xml.sax.Attributes, java.lang.Object, org.apache.axis.encoding.SerializationContext)
    */
   public void serialize(QName aName, Attributes aAttributes, Object aValue, SerializationContext aContext)
      throws IOException
   {
      try
      {
         Document document = (Document) aValue;
         aContext.setWriteXMLType(null);
         aContext.writeDOMElement(document.getDocumentElement());
      }
      catch(Throwable t)
      {
         AeException.logError(t, t.getLocalizedMessage());
         if (t instanceof IOException)
            throw (IOException)t;
         else
            throw new IOException(t.getLocalizedMessage());
      }
   }
}
