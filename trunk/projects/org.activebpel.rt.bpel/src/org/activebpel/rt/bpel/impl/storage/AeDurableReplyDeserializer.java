//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/storage/AeDurableReplyDeserializer.java,v 1.2 2006/07/10 16:32:4
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
package org.activebpel.rt.bpel.impl.storage;

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.impl.IAeImplStateNames;
import org.activebpel.rt.bpel.impl.reply.AeDurableReplyInfo;
import org.activebpel.rt.bpel.impl.reply.IAeDurableReplyInfo;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AeDurableReplyDeserializer implements IAeImplStateNames
{
   /**
    * Durable reply information
    */
   private IAeDurableReplyInfo mDurableReplyInfo;
   
   /**
    * Element representing the serialized contents of the durable reply. 
    */
   private Element mDurableReplyInfoElement;

   /**
    * Deserializes the durable reply information.
    * 
    * @param aDurableReplyInfoElement
    * @throws AeBusinessProcessException
    */
   protected IAeDurableReplyInfo createDurableReplyInfo(Element aDurableReplyInfoElement) throws AeBusinessProcessException
   {
      String type = aDurableReplyInfoElement.getAttribute(STATE_DURABLE_REPLY_TYPE);
      if (AeUtil.isNullOrEmpty(type))
      {
         throw new IllegalStateException(AeMessages.getString("AeDurableReplyDeserializer.MISSING_DURABLE_REPLY_TYPE")); //$NON-NLS-1$         
      }
      // get the list of child property elements for the durable reply.
      Map properties = new HashMap();
      NodeList nl = aDurableReplyInfoElement.getElementsByTagName(STATE_PROPERTY);
      for (int i = 0; i < nl.getLength(); i++)
      {
         Element element = (Element) nl.item(i);
         String name = element.getAttribute(STATE_NAME);
         String value = AeXmlUtil.getText( element );
         properties.put(name, value);
      }
      return new AeDurableReplyInfo(type, properties);      
   }   
   
   protected void deserialize() throws AeBusinessProcessException
   {
      if (mDurableReplyInfo == null)
      {
         Element root = getDurableReplyInfoElement();
         if (root == null)
         {
            throw new IllegalStateException(AeMessages.getString("AeDurableReplyDeserializer.MISSING_DATA")); //$NON-NLS-1$
         }

         mDurableReplyInfo = createDurableReplyInfo(root);
      }
   }  
   
   /**
    * @return Returns the durableReplyInfo.
    */
   public IAeDurableReplyInfo getDurableReplyInfo() throws AeBusinessProcessException
   {
      deserialize();
      return mDurableReplyInfo;
   }

   /**
    * @param aDurableReplyInfo The durableReplyInfo to set.
    */
   protected void setDurableReplyInfo(IAeDurableReplyInfo aDurableReplyInfo)
   {
      mDurableReplyInfo = aDurableReplyInfo;
   }

   /**
    * @return Returns the durableReplyInfoElement.
    */
   protected Element getDurableReplyInfoElement()
   {
      return mDurableReplyInfoElement;
   }

   /**
    * @param aDurableReplyInfoElement The durableReplyInfoElement to set.
    */
   public void setDurableReplyInfoElement(Element aDurableReplyInfoElement)
   {
      mDurableReplyInfoElement = aDurableReplyInfoElement;
   }

}
