// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/IAeMessageReceiverActivity.java,v 1.10 2006/10/26 13:57:2
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
import org.activebpel.rt.bpel.impl.AePartnerLinkOpImplKey;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal;
import org.activebpel.rt.bpel.impl.activity.support.AeCorrelationSet;
import org.activebpel.rt.bpel.impl.activity.support.IAeCorrelationListener;
import org.activebpel.rt.bpel.impl.activity.support.IAeIMACorrelations;
import org.activebpel.wsio.receive.IAeMessageContext;

/**
 * Provides an interface for an activity (receive, onMessage, or onEvent) that
 * gets queued and receives messages or faults. The interface provides 
 * information that is required to queue the activity so it can receive a 
 * message. It also provides an accessor for a separate interface that handles 
 * the actual dispatch of the message data or fault data to the receiver.
 */
public interface IAeMessageReceiverActivity extends IAeCorrelationListener
{
   /**
    * Returns the xpath location of the activity
    */
   public String getLocationPath();

   /**
    * Gets the correlation def objects for the activity
    */
   public IAeIMACorrelations getCorrelations();

   /**
    * Getter for the process (pass through to AeAbstractBpelObject's impl)
    */
   public IAeBusinessProcessInternal getProcess();
   
   /**
    * Gets the implementation of the correlation set (pass through to 
    * AeAbstractBpelObject's impl)
    * @param aName
    */
   public AeCorrelationSet findCorrelationSet(String aName) throws AeBusinessProcessException;

   /**
    * Returns true if the def's createInstance attribute is 'yes'
    */
   public boolean canCreateInstance();
   
   /**
    * Called prior to dispatching a message or fault to the receive. This gives 
    * the activity a chance to prepare to receive the data. 
    * @throws AeBusinessProcessException
    * @return IAeMessageDispatcher used to dispatch the data to the message 
    *         receiver 
    */
   public IAeMessageDispatcher createDispatcher(IAeMessageContext aMessageContext);
   
   /**
    * Getter for the partner link op impl key. This key is used to track 
    * openIMA's and thereby detect conflicting requests and bind replies to 
    * their original reply receiver.
    */
   public AePartnerLinkOpImplKey getPartnerLinkOperationImplKey();
   
   /**
    * True if the message receiver allows concurrent messages to be processed.
    */
   public boolean isConcurrent();
   
   /**
    * Returns true if the receiver is currently queued and waiting for a 
    * message.
    */
   public boolean isQueued();
}
