//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AeMessageMatcher.java,v 1.2 2006/02/01 22:29:1
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
package org.activebpel.rt.wsdl.def; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.Message;
import javax.wsdl.Part;
import javax.xml.namespace.QName;

import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides a routine for matching a Document[] against wsdl messages to see if 
 * the message parts match the array. This is used when matching the operation
 * being invoked when receiving a Document style message since that style omits
 * the operation from the SOAP message.
 */
public class AeMessageMatcher
{
   /** array of documents for our operation's input message */
   private Document[] mData;
   
   /** The message that was found or null if not found */
   private Message mMessage;
   
   /**
    * Constructor requries the doc array to match against
    * @param aDocArray
    */
   public AeMessageMatcher(Document[] aDocArray)
   {
      mData = aDocArray;
   }

   /**
    * Returns true if the input message matches the doc[]
    * 
    * @param aInputMessage
    */
   public boolean isMatch(Message aInputMessage)
   {
      setMessage(null);
      
      List partsList = aInputMessage.getOrderedParts(null);
      
      boolean signatureMatch = partsList.size() == getData().length; 
   
      if (signatureMatch)
      {
         int i=0;
         for (Iterator it = partsList.iterator(); signatureMatch && it.hasNext(); i++)
         {
            Part part = (Part) it.next();
            
            Element docElement = getData()[i].getDocumentElement();
            
            if(part.getElementName() == null)
            {
               // it's a part, see if it has an xsi:type on it
               QName xsiType = AeXmlUtil.getXSIType(docElement);
               if (xsiType != null)
               {
                  signatureMatch = xsiType.equals(part.getTypeName());
               }
               else if (!AeXmlUtil.getLocalName(docElement).equals(part.getName()))
               {
                  signatureMatch = false;
               }
            }
            else
            {
               if (!AeUtil.compareObjects(docElement.getLocalName(),    part.getElementName().getLocalPart())
               ||  !AeUtil.compareObjects(docElement.getNamespaceURI(), part.getElementName().getNamespaceURI()) )
               {
                  signatureMatch = false;
               }
            }
            
         }
      }
      
      if (signatureMatch)
         setMessage(aInputMessage);
      
      return signatureMatch;
   }
   
   /**
    * Returns true if we were able to find a match
    */
   public boolean foundMatch()
   {
      return getMessage() != null;
   }

   /**
    * Gets the parts map which produces a map of the part names to the data
    */
   public Map getPartsMap()
   {
      if (!foundMatch())
      {
         return null;
      }
      
      Map map = new HashMap();
      List list = getMessage().getOrderedParts(null);
      if (list != null)
      {
         int i=0;
         for (Iterator iter = list.iterator(); iter.hasNext(); i++)
         {
            Part part = (Part) iter.next();
            map.put(part, getData()[i]);
         }
      }
      return map;
   }

   /**
    * Getter for the data
    */
   protected Document[] getData()
   {
      return mData;
   }
   
   /**
    * Getter for the message. Will be null if the last match wasn't successful
    */
   protected Message getMessage()
   {
      return mMessage;
   }
   
   /**
    * Setter for the message.
    * @param aMessage
    */
   protected void setMessage(Message aMessage)
   {
      mMessage = aMessage;
   }
} 
