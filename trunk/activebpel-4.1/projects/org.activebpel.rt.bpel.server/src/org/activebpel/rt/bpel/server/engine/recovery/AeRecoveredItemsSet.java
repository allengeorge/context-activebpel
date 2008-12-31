// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/AeRecoveredItemsSet.java,v 1.4 2007/07/09 16:28:4
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

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.server.engine.recovery.recovered.AeRecoveredLocationIdItem;
import org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem;

/**
 * Implements a set of recovered items representing alarm and queue manager
 * requests. Extends {@link java.util.LinkedHashMap} to preserve order of
 * insertion for {@link #getRecoveredItems()}.
 */
public class AeRecoveredItemsSet extends LinkedHashMap implements IAeRecoveredItemsSet
{
   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveredItemsSet#addRecoveredItem(org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem)
    */
   public void addRecoveredItem(IAeRecoveredItem aRecoveredItem) throws AeRecoveryConflictingRequestException
   {
      if (containsKey(aRecoveredItem))
      {
         throw new AeRecoveryConflictingRequestException();
      }

      put(aRecoveredItem, aRecoveredItem);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveredItemsSet#getRecoveredItems()
    */
   public List getRecoveredItems()
   {
      return new LinkedList(this.values());
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveredItemsSet#removeRecoveredItem(org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem)
    */
   public IAeRecoveredItem removeRecoveredItem(IAeRecoveredItem aItem)
   {
      return (IAeRecoveredItem) remove(aItem);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.IAeRecoveredItemsSet#removeRecoveredItem(int)
    */
   public IAeRecoveredItem removeRecoveredItem(final int aLocationId)
   {
      return removeRecoveredItem(new AeRecoveredLocationIdItem(0, aLocationId)
      {
         /**
          * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#queueItem(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
          */
         public void queueItem(IAeBusinessProcessEngineInternal aTargetEngine) throws AeBusinessProcessException
         {
            // will never be called since we're only using this as a key to 
            // remove the item with the same id
         }
      });
   }
   
}
