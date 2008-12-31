// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/IAeRecoveryQueueManager.java,v 1.3 2007/07/09 16:28:4
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
package org.activebpel.rt.bpel.server.engine.recovery;

import java.util.List;

import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.impl.IAeQueueManager;

/**
 * Extends {@link org.activebpel.rt.bpel.impl.IAeQueueManager} to define the
 * interface for a queue manager used by an instance of {@link
 * org.activebpel.rt.bpel.server.engine.recovery.AeRecoveryEngine} for
 * recovery.
 */
public interface IAeRecoveryQueueManager extends IAeQueueManager
{
   /**
    * Sets the list of invoke transmitted journal entries to be used to restore
    * invoke transmission ids.
    */
   public void setInvokeTransmittedEntries(List aInvokeTransmittedEntries);

   /**
    * Sets container for alarm and queue manager items generated during
    * recovery.
    */
   public void setRecoveredItemsSet(IAeRecoveredItemsSet aRecoveredItemsSet);
   
   /**
    * Gets container for alarm and queue manager items generated during 
    * recovery.
    */
   public IAeRecoveredItemsSet getRecoveredItemsSet();

   /**
    * Sets the process that is being recovered.
    */
   public void setRecoveryProcess(IAeBusinessProcess aRecoveryProcess);

   /**
    * Sets the sent replies.
    */
   public void setSentReplies(List aSentReplies);
}
