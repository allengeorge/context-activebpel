//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/AeRPCLiteralDeserializer.java,v 1.3 2006/03/14 18:16:3
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

import org.activebpel.rt.AeException;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.message.MessageElement;
import org.xml.sax.SAXException;

/**
 * Custom deserializer for handling complex types over RPC literal. These types will be
 * deserialized into their literal xml.
 * 
 * RPC Literal deserialization is simpler than Encoded since the inbound message
 * should be complete with namespaces which identify the types. There is also
 * no flattening of the document structure through multiRef encoding. As such,
 * it's a pretty straightforward implementation of copying the SOAP MessageElements
 * into a new DOM.
 * 
 * One might think that this would be as simple as importing the MessageElement
 * into a new Document but this unfortunately does not copy all of the namespace
 * declarations over. As such, we walk the MessageElements recursively and copy
 * their attributes and namespace declarations manually, relying on some facilities
 * in the base class for copying attributes and such.
 * 
 */
public class AeRPCLiteralDeserializer extends AeRPCEncodedDeserializer
{
   // TODO (MF) This class no longer needs to extend AeRPCEncodedDeserializer
   /**
    * Constructor for deserializer.
    */
   public AeRPCLiteralDeserializer(IAeTypesContext aTypesContext)
   {
      super(aTypesContext);
   }
   
   /**
    * @see org.apache.axis.encoding.Deserializer#onEndElement(java.lang.String, java.lang.String, org.apache.axis.encoding.DeserializationContext)
    */
   public void onEndElement(String aNamespace, String aLocalName,
                                  DeserializationContext aContext)
       throws SAXException
   {
      MessageElement msgElem = aContext.getCurElement();
      try
      {
         value = msgElem.getAsDOM().getOwnerDocument();
      }
      catch (Throwable t)
      {
         AeException.logError(t, AeMessages.getString("AeLiteralDeserializer.ERROR_10")); //$NON-NLS-1$
         if (t instanceof Exception)
            throw new SAXException((Exception)t);
         throw new SAXException(t.getLocalizedMessage());
      }
   }
}
