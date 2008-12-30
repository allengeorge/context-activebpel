// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/recovered/AeRecoveredSendReplyItem.java,v 1.2 2006/10/18 23:33:1
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
package org.activebpel.rt.bpel.server.engine.recovery.recovered;

import java.util.Map;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.queue.AeReply;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Implements a recovered item to send a reply.
 */
public class AeRecoveredSendReplyItem extends AeRecoveredReplyItem implements IAeRecoveredItem
{
   /** The message data to send. */
   private final IAeMessageData mMessageData;

   /** The fault to send. */
   private final IAeFault mFault;

   /** The associated process properties. */
   private final Map mProcessProperties;

   /**
    * Constructs a recovered item to send a reply.
    */
   public AeRecoveredSendReplyItem(AeReply aReply, IAeMessageData aMessageData, IAeFault aFault, Map aProcessProperties)
   {
      super(aReply);

      mMessageData = aMessageData;
      mFault = aFault;
      mProcessProperties = aProcessProperties;
   }

   /**
    * Returns the fault to send.
    */
   protected IAeFault getFault()
   {
      return mFault;
   }

   /**
    * Returns the message data to send.
    */
   protected IAeMessageData getMessageData()
   {
      return mMessageData;
   }

   /**
    * Returns the associated process properties.
    */
   protected Map getProcessProperties()
   {
      return mProcessProperties;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#queueItem(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
    */
   public void queueItem(IAeBusinessProcessEngineInternal aTargetEngine) throws AeBusinessProcessException
   {
      aTargetEngine.getQueueManager().sendReply(getReply(), getMessageData(), getFault(), getProcessProperties());
   }
}
