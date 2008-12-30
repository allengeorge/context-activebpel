//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/reply/AeDurableQueuingReplyReceiverFactory.java,v 1.1 2006/05/24 23:16:3
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
package org.activebpel.rt.bpel.server.engine.reply;

import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver;
import org.activebpel.rt.bpel.impl.reply.IAeReplyReceiverFactory;
import org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.util.AeUtil;

/**
 * ReplyReceiver factory that is responsible for recreating QueuingReplyReceivers.
 */
public class AeDurableQueuingReplyReceiverFactory implements IAeReplyReceiverFactory
{   
   /**
    * Default ctor.
    */
   public AeDurableQueuingReplyReceiverFactory(Map aConfig) throws AeException
   {      
   }
      
   /** 
    * Overrides method to return a <code>AeDurableQueuingReplyReceiver</code>. 
    * @see org.activebpel.rt.bpel.impl.reply.IAeReplyReceiverFactory#createReplyReceiver(java.util.Map)
    */
   public IAeReplyReceiver createReplyReceiver(Map aProperties) throws AeException
   {
      // get process id.
      String processIdString = (String) aProperties.get(AeDurableQueuingReplyReceiver.PROCESS_ID);
      if (AeUtil.isNullOrEmpty(processIdString))
      {
         throw new AeException(AeMessages.getString("AeDurableQueuingReplyReceiverFactory.MISSING_PID") ); //$NON-NLS-1$
      }
      long processId = -1;
      try
      {
         processId = Integer.parseInt( processIdString );
      }
      catch(Exception e)
      {
         AeException.logError(e);
         processId = -1;
      }
      if (processId < 1)
      {
         throw new AeException(AeMessages.format("AeDurableQueuingReplyReceiverFactory.INVALID_PID", processIdString) );          //$NON-NLS-1$
      }
      // get location path
      String locationPath = (String) aProperties.get(AeDurableQueuingReplyReceiver.LOCATION_PATH);
      if (AeUtil.isNullOrEmpty(locationPath))
      {
         throw new AeException(AeMessages.getString("AeDurableQueuingReplyReceiverFactory.MISSING_LOCATION_PATH") );  //$NON-NLS-1$
      }
      
      // get tx id
      String txIdString = (String) aProperties.get(AeDurableQueuingReplyReceiver.TRANSMISSION_ID ); 
      long txId = IAeTransmissionTracker.NULL_TRANSREC_ID;
      if ( AeUtil.notNullOrEmpty(txIdString) )
      {
         try
         {
            txId = Long.parseLong( txIdString );
         }
         catch(Exception e)
         {
            AeException.logError(e);
            // ignore
         }         
      }      
      // create and return a queuing reply receiver instance.
      return new AeDurableQueuingReplyReceiver(processId, locationPath, txId);
   }
}
