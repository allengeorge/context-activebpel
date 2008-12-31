// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/recovered/AeRecoveredReplyItem.java,v 1.1 2005/07/12 00:26:0
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

import org.activebpel.rt.bpel.impl.queue.AeReply;

/**
 * Base class for recovered reply items.
 */
public abstract class AeRecoveredReplyItem implements IAeRecoveredItem
{
   /** The reply object. */
   private final AeReply mReply;

   /**
    * Constructs a recovered reply item.
    */
   protected AeRecoveredReplyItem(AeReply aReply)
   {
      mReply = aReply;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#getLocationId()
    */
   public int getLocationId()
   {
      // Return 0, because location id not used for matching recovered replies.
      return 0;
   }

   /**
    * Returns the reply object.
    */
   public AeReply getReply()
   {
      return mReply;
   }
}
