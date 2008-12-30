// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeMessageReceiver.java,v 1.8 2006/05/24 23:07:0
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
import org.activebpel.rt.message.IAeMessageData;

/**
 * Defines a callback interface for objects that expect to receive messages. 
 */
public interface IAeMessageReceiver
{
   /**
    * Callback when a message arrives for a message receiver.
    * @param aMessage The message which has been received.
    * @throws AeBusinessProcessException Allows the receiver to throw an exception.
    */
   public void onMessage(IAeMessageData aMessage) throws AeBusinessProcessException;

   /**
    * Callback when a fault arrives instead of the expected message.
    * @param aFault The fault which has been received.
    * @throws AeBusinessProcessException Allows the receiver to throw an exception.
    */
   public void onFault(IAeFault aFault) throws AeBusinessProcessException;

   /**
    * Returns the unique location path within the process for this receiver.
    */
   public String getLocationPath();
   
   /**
    * Returns the unique location id within the process for this receiver.
    */
   public int getLocationId();
   
}
