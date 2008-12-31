//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/recovered/AeRecoveredAddReplyItem.java,v 1.4 2006/06/05 20:45:4
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.queue.AeMessageReceiver;
import org.activebpel.rt.bpel.impl.queue.AeReply;
import org.activebpel.rt.util.AeUtil;

/**
 * Implements a recovered item to add a reply.
 */
public class AeRecoveredAddReplyItem extends AeRecoveredReplyItem implements IAeRecoveredItem
{
   /** The previously queued message receiver that goes with the reply. */
   private final AeMessageReceiver mMessageReceiver;

   /**
    * Constructs a recovered item to add a reply.
    */
   public AeRecoveredAddReplyItem(AeReply aReply, AeMessageReceiver aMessageReceiver)
   {
      super(aReply);

      mMessageReceiver = aMessageReceiver;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      return (aOther instanceof AeRecoveredAddReplyItem)
         && AeUtil.compareObjects(((AeRecoveredAddReplyItem) aOther).getReply(), getReply());
   }

   /**
    * Returns the previously queued message receiver that goes with the reply.
    */
   protected AeMessageReceiver getMessageReceiver()
   {
      return mMessageReceiver;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return getReply().hashCode();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#queueItem(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
    */
   public void queueItem(IAeBusinessProcessEngineInternal aTargetEngine) throws AeBusinessProcessException
   {
      aTargetEngine.getQueueManager().addNonDurableReply(getReply(), getMessageReceiver());
   }
}
