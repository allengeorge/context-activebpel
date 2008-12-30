//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/storage/AeDurableReplySerializer.java,v 1.2 2006/07/10 16:32:4
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

import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.impl.IAeImplStateNames;
import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.impl.fastdom.AeFastElement;
import org.activebpel.rt.bpel.impl.fastdom.AeFastText;
import org.activebpel.rt.bpel.impl.reply.IAeDurableReplyInfo;
import org.activebpel.rt.util.AeUtil;

/**
 * Serialiazes an instance of IAeDurableReplyInfo class.
 *
 */
public class AeDurableReplySerializer implements IAeImplStateNames
{
   // LocationPath maybe needed at a higher level e.g. AeDurableQueingReplyReceiver.
   /**
    * Durable reply information
    */
   private IAeDurableReplyInfo mDurableReplyInfo;
   
   /**
    * Element representing the serialized contents of the durable reply. 
    */
   private AeFastElement mDurableReplyInfoElement;
   
   /**
    * Returns an instance of {@link
    * org.activebpel.rt.bpel.impl.fastdom.AeFastDocument} representing the
    * durable reply information.
    */
   public AeFastDocument getDurableReplyInfoDocument() throws AeBusinessProcessException
   {
      return new AeFastDocument(getDurableReplyInfoElement());
   }   

   /**
    * Returns an instance of {@link
    * org.activebpel.rt.bpel.impl.fastdom.AeFastElement} representing the
    * durable reply information.
    */
   public AeFastElement getDurableReplyInfoElement() throws AeBusinessProcessException
   {
      if (mDurableReplyInfoElement == null)
      {
         if (getDurableReplyInfo() == null)
         {
            throw new IllegalStateException(AeMessages.getString("AeDurableReplySerializer.MISSING_DATA")); //$NON-NLS-1$
         }

         mDurableReplyInfoElement = createDurableReplyInfoElement( getDurableReplyInfo() );
      }

      return mDurableReplyInfoElement;
   }

   /**
    * Serializes the inbound receive's durable reply data.
    * 
    * @param aReply
    */
   protected AeFastElement createDurableReplyInfoElement(IAeDurableReplyInfo aReply)
   { 
      AeFastElement result = new AeFastElement(STATE_DURABLE_REPLY);
      result.setAttribute(STATE_DURABLE_REPLY_TYPE, aReply.getType());
      // Serialize durable reply properties.
      Map properties = aReply.getProperties();
      if (properties != null)
      {
         for (Iterator i = properties.entrySet().iterator(); i.hasNext(); )
         {
            Map.Entry entry = (Map.Entry) i.next();
            String name = (String) entry.getKey();
            String value = AeUtil.getSafeString((String) entry.getValue());
            AeFastElement propertyElement = new AeFastElement( STATE_PROPERTY );
            propertyElement.setAttribute( STATE_NAME, name );
            AeFastText valueNode = new AeFastText( value );
            propertyElement.appendChild( valueNode );            
            result.appendChild(propertyElement);
         }
      }      
      return result;
   }  
   
   /**
    * @return Returns the durableReplyInfo.
    */
   protected IAeDurableReplyInfo getDurableReplyInfo()
   {
      return mDurableReplyInfo;
   }

   /**
    * @param aDurableReplyInfo The durableReplyInfo to set.
    */
   public void setDurableReplyInfo(IAeDurableReplyInfo aDurableReplyInfo)
   {
      mDurableReplyInfo = aDurableReplyInfo;
   }   
   
}
