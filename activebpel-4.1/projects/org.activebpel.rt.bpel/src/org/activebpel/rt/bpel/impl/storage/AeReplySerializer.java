// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/storage/AeReplySerializer.java,v 1.3 2006/06/05 20:40:2
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
import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.impl.fastdom.AeFastElement;
import org.activebpel.rt.bpel.impl.queue.AeReply;

/**
 * Serializes a reply to an instance of {@link
 * org.activebpel.rt.bpel.impl.fastdom.AeFastElement} or {@link
 * org.activebpel.rt.bpel.impl.fastdom.AeFastDocument}.
 */
public class AeReplySerializer implements IAeImplStateNames
{
   /** The reply to serialize. */
   private AeReply mReply;

   /** The resulting serialization. */
   private AeFastElement mReplyElement;

   /**
    * Serializes the specified reply to an instance of {@link
    * org.activebpel.rt.bpel.impl.fastdom.AeFastElement}.
    *
    * @param aReply
    */
   protected AeFastElement createReplyElement(AeReply aReply) throws AeBusinessProcessException
   {
      AeFastElement replyElement = new AeFastElement(STATE_REPLY);
      replyElement.setAttribute(STATE_PID      , String.valueOf(aReply.getProcessId()));
      replyElement.setAttribute(STATE_REPLY_ID , String.valueOf(aReply.getReplyId()) );
      return replyElement;
   }

   /**
    * Returns the reply to serialize.
    */
   protected AeReply getReply()
   {
      return mReply;
   }

   /**
    * Returns an instance of {@link
    * org.activebpel.rt.bpel.impl.fastdom.AeFastDocument} representing the
    * reply.
    */
   public AeFastDocument getReplyDocument() throws AeBusinessProcessException
   {
      return new AeFastDocument(getReplyElement());
   }

   /**
    * Returns an instance of {@link
    * org.activebpel.rt.bpel.impl.fastdom.AeFastElement} representing the reply.
    */
   public AeFastElement getReplyElement() throws AeBusinessProcessException
   {
      if (mReplyElement == null)
      {
         if (getReply() == null)
         {
            throw new IllegalStateException(AeMessages.getString("AeReplySerializer.ERROR_0")); //$NON-NLS-1$
         }

         mReplyElement = createReplyElement(getReply());
      }

      return mReplyElement;
   }

   /**
    * Resets all output variables.
    */
   protected void reset()
   {
      mReplyElement = null;
   }

   /**
    * Sets the reply to serialize.
    *
    * @param aReply
    */
   public void setReply(AeReply aReply)
   {
      reset();

      mReply = aReply;
   }
}
