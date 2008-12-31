// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/storage/AeReplyDeserializer.java,v 1.5 2006/06/09 22:58:0
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.impl.IAeImplStateNames;
import org.activebpel.rt.bpel.impl.queue.AeReply;
import org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Element;

/**
 * Deserializes a reply from its serialization.
 */
public class AeReplyDeserializer implements IAeImplStateNames
{
   /** The reply once deserialized. */
   private AeReply mReply;

   /** The reply's serialization. */
   private Element mReplyElement;

   /**
    * Creates an instance of {@link org.activebpel.rt.bpel.impl.queue.AeReply} from its
    * serialization.
    * 
    * @param aReplyElement
    * @throws AeBusinessProcessException
    */
   protected AeReply createReply(Element aReplyElement) throws AeBusinessProcessException
   {      
      long processId = Long.parseLong(aReplyElement.getAttribute(STATE_PID));
      long replyId = IAeReplyReceiver.NULL_REPLY_ID;
      // check if not empty (for pre 2.1 compatibility)
      if ( AeUtil.notNullOrEmpty( aReplyElement.getAttribute(STATE_REPLY_ID) ) )
      {
         replyId = Long.parseLong(aReplyElement.getAttribute(STATE_REPLY_ID));
      }
      return new AeReply(processId, replyId);
   }

   /**
    * Returns the reply deserialized from the serialization that was set with
    * {@link #setReplyElement}.
    */
   public AeReply getReply() throws AeBusinessProcessException
   {
      if (mReply == null)
      {
         if (getReplyElement() == null)
         {
            throw new IllegalStateException(AeMessages.getString("AeReplyDeserializer.ERROR_0")); //$NON-NLS-1$
         }

         mReply = createReply(getReplyElement());
      }

      return mReply;
   }

   /**
    * Returns the reply serialization to use.
    */
   protected Element getReplyElement()
   {
      return mReplyElement;
   }

   /**
    * Resets all output variables.
    */
   protected void reset()
   {
      mReply = null;
   }

   /**
    * Sets the reply serialization to use.
    *
    * @param aReplyElement
    */
   public void setReplyElement(Element aReplyElement)
   {
      reset();

      mReplyElement = aReplyElement;
   }
}
