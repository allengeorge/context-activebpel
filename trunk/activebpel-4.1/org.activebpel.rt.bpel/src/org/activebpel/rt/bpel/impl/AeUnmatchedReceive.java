//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeUnmatchedReceive.java,v 1.1 2006/05/24 23:07:0
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
package org.activebpel.rt.bpel.impl;

import org.activebpel.rt.bpel.impl.queue.AeInboundReceive;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.wsio.IAeMessageAcknowledgeCallback;

/**
 * Umatched Receive collections object - which is a tuple for holding
 * the unmatched <code>AeInboundReceive</code> and its assoicated <code>IAeMessageAcknowledgeCallback</code>.
 */
public class AeUnmatchedReceive
{
   /** The unmatched inbound receive. */ 
   private AeInboundReceive mInboundReceive;
   /** Durable message acknowlege callback. */
   private IAeMessageAcknowledgeCallback mAckCallback;
   
   /**
    * Default ctor.
    * @param aInboundReceive
    * @param aAckCallback
    */
   public AeUnmatchedReceive(AeInboundReceive aInboundReceive, IAeMessageAcknowledgeCallback aAckCallback)
   {
      mInboundReceive = aInboundReceive;
      mAckCallback = aAckCallback;
   }
   
   /** 
    * @return Returns the unmatched inbound receive.
    */
   public AeInboundReceive getInboundReceive()
   {
      return mInboundReceive;
   }
   
   /** 
    * @return Returns the unmatched receive's associated durable callback.
    */
   public IAeMessageAcknowledgeCallback getAckCallback()
   {
      return mAckCallback;
   }
   
   /**
    * Returns the  queue id assigned to the inbound receive..
    */
   public String getQueueId()
   {
      return getInboundReceive().getQueueId();
   }   
   
   /**
    * Sets the inbound receive's queue id.
    * @param aQueueId
    */
   public void setQueueId(String aQueueId)
   {
      getInboundReceive().setQueueId(aQueueId);
   }
   
   /** 
    * Overrides method to compare the inbound receives. 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object other)
   {
      return AeUtil.compareObjects( getInboundReceive(), other);
   }
}
