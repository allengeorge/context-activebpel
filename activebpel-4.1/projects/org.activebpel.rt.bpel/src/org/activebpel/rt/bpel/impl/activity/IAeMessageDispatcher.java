//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/IAeMessageDispatcher.java,v 1.1 2006/10/26 13:57:2
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
package org.activebpel.rt.bpel.impl.activity; 

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.impl.AePartnerLinkOpImplKey;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Interface created by message receivers in order to handle the dispatching of message or faults to
 * the message receiver. This interface enables the message receivers to provide a different implementation
 * of the dispatch logic. The only notable implementation is the <code>onEvent</code> which uses this
 * interface in order to construct a dynamic scope which handles the processing of the message. 
 */
public interface IAeMessageDispatcher
{
   /**
    * Notifies the activity that its data has arrived.
    * @throws AeBusinessProcessException
    */
   public void onMessage(IAeMessageData aMessage) throws AeBusinessProcessException;

   /**
    * Used to report a fault back to the message receive activity. At this point,
    * the only fault that will get reported will be a bpws:conflictingRequest
    *
    * @param aFault
    * @throws AeBusinessProcessException
    */
   public void onFault(IAeFault aFault) throws AeBusinessProcessException;

   /**
    * Called when a message arrives for this message receiver and we need to return a messageExchange
    * path so we can bind the openIMA to the proper messageExchange path.  
    * @return Returns the message exchange path.
    */
   public String getMessageExchangePathForOpenIMA() throws AeBusinessProcessException; 

   /**
    * Getter for the partner link op impl key. This key is used to track openIMA's and thereby
    * detect conflicting requests and bind replies to their original reply receiver.
    */
   public AePartnerLinkOpImplKey getPartnerLinkOperationImplKey();
   
   /**
    * Returns true if the process can update the plink when the message arrives or whether it will
    * be handled by the dispatcher. 
    */
   public boolean isPartnerLinkReadyForUpdate();

   /**
    * Returns the xpath location of the activity
    */
   public String getLocationPath();
   
   /**
    * Gets the bpel object that will be receiving the message
    */
   public IAeBpelObject getTarget();
}
 
