//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/reply/AeQueuingReplyReceiver.java,v 1.7 2006/11/20 22:45:5
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcessEngine;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.impl.IAeInvokeInternal;
import org.activebpel.rt.bpel.impl.reply.AeReplyReceiverBase;
import org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.wsio.IAeMessageAcknowledgeCallback;

import java.util.Map;

/**
 * Provides a way of passing the outcome of the invocation back into the engine
 * by queuing the message data or the fault (instead of calling notifyAll).
 */
public class AeQueuingReplyReceiver extends AeReplyReceiverBase
{
   /**
    * Process Id of invoke.
    */
   private long mProcessId;
   /**
    * LocationPath of invoke.
    */
   private String mLocationPath;

   /** invoke transmission id. */
   private long mTransmissionId = IAeTransmissionTracker.NULL_TRANSREC_ID;

   /**
    * Creates a reply receiver given the queued Invoke object's process id and location path.
    * @param aProcessId process id of the Invoke object.
    * @param aLocationPath location path of the Invoke object.
    * @param aTransmissionId invoke activity transmission id.
    */
   public AeQueuingReplyReceiver(long aProcessId, String aLocationPath, long aTransmissionId)
   {
      mProcessId = aProcessId;
      mLocationPath = aLocationPath;
      setTransmissionId(aTransmissionId);
   }

   /**
    * Creates the receiver using the process id and location path from the invoke object
    * @param aInvoke invoke that is queued in the engine.
    */
   public AeQueuingReplyReceiver(IAeInvokeInternal aInvoke)
   {
      this(aInvoke.getProcessId(), aInvoke.getLocationPath(), aInvoke.getTransmissionId());
   }

   /**
    * @return process id of invoke activity.
    */
   protected long getProcessId()
   {
      return mProcessId;
   }

   /**
    * @return location path of invoke object.
    */
   protected String getLocationPath()
   {
      return mLocationPath;
   }

   /**
    * @return Returns the transmission d.
    */
   protected long getTransmissionId()
   {
      return mTransmissionId;
   }

   /**
    * @param aTransmissionId
    */
   public void setTransmissionId(long aTransmissionId)
   {
      mTransmissionId = aTransmissionId;
   }

   /**
    * Queues the fault into the engine.
    * @param aFault fault
    * @param aProcessProperties business process properties.
    * @see org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver#onFault(org.activebpel.rt.bpel.IAeFault, java.util.Map)
    */
   public void onFault(IAeFault aFault, Map aProcessProperties) throws AeBusinessProcessException
   {
      queueInvokeFault(aFault, aProcessProperties, null);
   }

   /**
    * Queues the message data into the engine.
    * @param aMessageData message data.
    * @param aProcessProperties business process properties.
    * @see org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver#onMessage(org.activebpel.rt.message.IAeMessageData, java.util.Map)
    */
   public void onMessage(IAeMessageData aMessageData, Map aProcessProperties) throws AeBusinessProcessException
   {
      queueInvokeData(aMessageData, aProcessProperties, null);
   }

   /**
    * Queue's the invoke's fault back into the engine.
    * @param aFault
    * @param aAckCallback optional message acknoledge callback.
    * @throws AeBusinessProcessException
    */
   protected void queueInvokeFault(IAeFault aFault, Map aProcessProperties, IAeMessageAcknowledgeCallback aAckCallback) throws AeBusinessProcessException
   {
      setFault(aFault);
      setBusinessProcessProperties(aProcessProperties);
      getEngine().queueInvokeFault(getProcessId(), getLocationPath(), getTransmissionId(), getFault(), getBusinessProcessProperties(), aAckCallback );
   }

   /**
    * Queue's the invoke's data back into the engine.
    * @param aMessageData
    * @param aAckCallback
    * @throws AeBusinessProcessException
    */
   protected void queueInvokeData(IAeMessageData aMessageData, Map aProcessProperties, IAeMessageAcknowledgeCallback aAckCallback) throws AeBusinessProcessException
   {
      setMessageData(aMessageData);
      setBusinessProcessProperties(aProcessProperties);
      getEngine().queueInvokeData(getProcessId(), getLocationPath(), getTransmissionId(), aMessageData, getBusinessProcessProperties(), aAckCallback );
   }

   /***
    * Returns the engine instance.
    * @return engine instance.
    */
   protected IAeBusinessProcessEngine getEngine()
   {
      return AeEngineFactory.getEngine();
   }
}
