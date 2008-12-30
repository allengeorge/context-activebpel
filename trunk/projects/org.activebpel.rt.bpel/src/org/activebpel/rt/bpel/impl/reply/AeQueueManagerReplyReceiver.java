//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/reply/AeQueueManagerReplyReceiver.java,v 1.3 2006/06/08 19:30:5
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
package org.activebpel.rt.bpel.impl.reply;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.impl.IAeQueueManager;
import org.activebpel.rt.bpel.impl.queue.AeReply;
import org.activebpel.rt.message.IAeMessageData;

import java.util.Map;

/**
 * A reply receiver that delegates to a matching non-durable reply receiver
 * maintained by the queue manager. 
 *
 */
public class AeQueueManagerReplyReceiver implements IAeReplyReceiver
{
   /** Reply receiver prototype. */;
   private IAeReplyReceiver mDelegate;
   
   public AeQueueManagerReplyReceiver(IAeQueueManager aQueueManager, AeReply aReplyPrototype)
   {      
      // find the waiting reply from the queue manager. if a waiting reply is not found,
      // then use a missing reply receiver.
      AeReply waitingReply  = aQueueManager.removeReply(aReplyPrototype);
      if (waitingReply != null)
      {
         setDelegate( waitingReply.getReplyReceiver() );  
      }      
      else
      {
         setDelegate ( new AeMissingReplyReceiver(aReplyPrototype.getReplyId()) );
      }
   }
   
   /**
    * @param aDelegate The delegate to set.
    */
   protected void setDelegate(IAeReplyReceiver aDelegate)
   {
      mDelegate = aDelegate;
   }
   
   /**
    * @return Returns the delegate.
    */
   public IAeReplyReceiver getDelegate()
   {
      return mDelegate;
   }


   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver#onMessage(org.activebpel.rt.message.IAeMessageData, java.util.Map)
    */
   public void onMessage(IAeMessageData aMessage, Map aProcessProperties) throws AeBusinessProcessException
   {
      getDelegate().onMessage(aMessage, aProcessProperties);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver#onFault(org.activebpel.rt.bpel.IAeFault, java.util.Map)
    */
   public void onFault(IAeFault aFault, Map aProcessProperties) throws AeBusinessProcessException
   {
      getDelegate().onFault(aFault, aProcessProperties);
   }

   /** 
    * Overrides method to return null. 
    * @see org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver#getDurableReplyInfo()
    */
   public IAeDurableReplyInfo getDurableReplyInfo()
   {
      return null;
   }

}
