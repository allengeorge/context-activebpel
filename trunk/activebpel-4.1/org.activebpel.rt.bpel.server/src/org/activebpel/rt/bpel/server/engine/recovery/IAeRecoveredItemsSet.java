// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/IAeRecoveredItemsSet.java,v 1.4 2007/07/09 16:28:4
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

import org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem;

/**
 * Defines the interface for a set of alarm and queue manager items generated
 * during recoverys.
 */
public interface IAeRecoveredItemsSet
{
   /**
    * Adds the given recovered item to the set and verifies that the set did
    * not already contain a matching item.
    */
   // fixme The exception here only applies to message receivers.
   // It seems possible that we could have multiple instances of a one-way invoke recovered if it were in a loop.
   public void addRecoveredItem(IAeRecoveredItem aRecoveredItem) throws AeRecoveryConflictingRequestException;

   /**
    * Returns recovered items as a list.
    */
   public List getRecoveredItems();

   /**
    * Removes and returns the recovered item that matches the given item;
    * returns <code>null</code> if there is no matching item.
    * 
    * @param aItem
    */
   public IAeRecoveredItem removeRecoveredItem(IAeRecoveredItem aItem);
   
   /**
    * Removes and returns the recovered item that matches the given location id;
    * returns <code>null</code> if there is no matching item.
    * 
    * @param aLocationId
    */
   public IAeRecoveredItem removeRecoveredItem(int aLocationId);
}
