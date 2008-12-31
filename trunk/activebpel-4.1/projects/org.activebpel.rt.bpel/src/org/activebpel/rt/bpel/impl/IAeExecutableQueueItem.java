// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeExecutableQueueItem.java,v 1.3 2005/01/28 20:22:1
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;

/**
 * Interface for items that can be executed by <code>AeExecutableQueue</code>.
 */
public interface IAeExecutableQueueItem
{
   /**
    * All implementations provide an execute method, which performs actual work.
    */
   public void execute() throws AeBusinessProcessException;

   /**
    * Returns the item's location path.
    */
   public String getLocationPath();

   /**
    * Returns the current state
    */
   public AeBpelState getState();

   /**
    * Indicates that the object has completed with a fault. Sets the objects state
    * to faulted and propagates the fault through the process.
    * @param aFault
    * @throws AeBusinessProcessException
    */
   public void objectCompletedWithFault(IAeFault aFault) throws AeBusinessProcessException;

   /**
    * Setter for the state.
    * @param aState
    */
   public void setState(AeBpelState aState) throws AeBusinessProcessException;
}
