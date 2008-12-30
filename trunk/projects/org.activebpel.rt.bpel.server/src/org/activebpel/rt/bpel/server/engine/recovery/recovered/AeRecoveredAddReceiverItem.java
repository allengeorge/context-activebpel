// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/recovered/AeRecoveredAddReceiverItem.java,v 1.2 2006/06/16 00:24:1
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
import org.activebpel.rt.bpel.server.AeMessages;

/**
 * Implements a recovered item to add a message receiver.
 */
public class AeRecoveredAddReceiverItem extends AeRecoveredLocationIdItem implements IAeRecoveredItem
{
   /** The message receiver. */
   private final AeMessageReceiver mMessageReceiver;

   /**
    * Constructs a recovered item to add a message receiver.
    */
   public AeRecoveredAddReceiverItem(AeMessageReceiver aMessageReceiver)
   {
      super(aMessageReceiver.getProcessId(), aMessageReceiver.getMessageReceiverPathId());

      mMessageReceiver = aMessageReceiver;
   }

   /**
    * Constructs a recovered item to match another add message receiver item by
    * location id.
    */
   public AeRecoveredAddReceiverItem(int aLocationId)
   {
      super(0, aLocationId);

      mMessageReceiver = null;
   }

   /**
    * Returns the message receiver.
    */
   public AeMessageReceiver getMessageReceiver()
   {
      return mMessageReceiver;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#queueItem(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
    */
   public void queueItem(IAeBusinessProcessEngineInternal aTargetEngine) throws AeBusinessProcessException
   {
      if (getMessageReceiver() == null)
      {
         throw new IllegalStateException(AeMessages.getString("AeRecoveredAddReceiverItem.ERROR_0")); //$NON-NLS-1$
      }

      aTargetEngine.getQueueManager().addMessageReceiver(getMessageReceiver());
   }
}
