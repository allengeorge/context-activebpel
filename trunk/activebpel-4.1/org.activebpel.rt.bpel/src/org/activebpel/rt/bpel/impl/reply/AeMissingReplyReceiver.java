//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/reply/AeMissingReplyReceiver.java,v 1.3 2006/06/08 19:30:5
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

import java.util.Map;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Implements the receiver for the reply to an inbound receive when the
 * system went down before the reply happened.
 */
public class AeMissingReplyReceiver implements IAeReplyReceiver
{
   /**
    * Reply id of the associated inbound receive.
    */
   private long mInboundReceiveReplyId;

   /**
    * Contructs a missing reply receiver given reply id.
    * @param aInboundReceiveReplyId
    */
   public AeMissingReplyReceiver(long aInboundReceiveReplyId)
   {
      mInboundReceiveReplyId = aInboundReceiveReplyId;
   }
   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver#onFault(org.activebpel.rt.bpel.IAeFault, java.util.Map)
    */
   public void onFault(IAeFault aFault, Map aProcessProperties) throws AeBusinessProcessException
   {
      // Throw an exception, because we no longer have the original receiver
      // for the reply to the inbound receive.
      throw new AeMissingReplyReceiverException(mInboundReceiveReplyId);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver#onMessage(org.activebpel.rt.message.IAeMessageData, java.util.Map)
    */
   public void onMessage(IAeMessageData aMessage, Map aProcessProperties) throws AeBusinessProcessException
   {
      // Throw an exception, because we no longer have the original receiver
      // for the reply to the inbound receive.
      throw new AeMissingReplyReceiverException(mInboundReceiveReplyId);
   }
   
   /** 
    * Overrides method to return <code>null</code> 
    * @see org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver#getDurableReplyInfo()
    */
   public IAeDurableReplyInfo getDurableReplyInfo()
   {
      return null;
   }
      
}


