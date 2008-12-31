// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeBusinessProcessEngineInternal.java,v 1.64 2007/07/31 00:48:3
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

import ece.uwaterloo.ca.aag.subscribe.IAagSubscribeResponse;
import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.*;
import org.activebpel.rt.bpel.impl.queue.AeMessageReceiver;
import org.activebpel.rt.bpel.impl.queue.AeReply;
import org.activebpel.rt.bpel.impl.reply.AeMissingReplyReceiverException;
import org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver;
import org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker;
import org.activebpel.rt.bpel.urn.IAeURNResolver;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.wsio.IAeMessageAcknowledgeCallback;
import org.activebpel.wsio.IAeWebServiceResponse;
import org.activebpel.wsio.receive.AeMessageContext;
import org.activebpel.wsio.receive.AeTimeoutException;
import org.activebpel.wsio.receive.IAeMessageContext;
import org.apache.muse.ws.addressing.EndpointReference;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Internal extensions to business process engine allows classes within
 * the implemenation to use the engine while still hiding the implementor.
 */
public interface IAeBusinessProcessEngineInternal extends IAeBusinessProcessEngine, IAeBusinessProcessFactory
{
   /**
    * Unassigned engine id, normally 0.
    */
   public static final int NULL_ENGINE_ID = 0;

   /**
    * Returns the engine ID.
    */
   public int getEngineId();

   /**
    * Gets the lock manager.
    */
   public IAeLockManager getLockManager();
   
   /**
    * Gets the attachment manager.
    */
   public IAeAttachmentManager getAttachmentManager();

   /**
    * Gets the queue manager that's used to queue activities and request data
    */
   public IAeQueueManager getQueueManager();

   /** Getter for the process manager. */
   public IAeProcessManager getProcessManager();

   /**
    * Gets a custom manager by name.
    * 
    * @param aManagerName
    */
   public IAeManager getCustomManager(String aManagerName);
   
   /**
    * Gets the names of all of the custom managers that were installed.
    */
   public Iterator getCustomManagerNames();

   /**
    * Gets the plan used to route the incoming message based on the information in the context.
    * @param aContext
    * @throws AeBusinessProcessException
    */
   public IAeProcessPlan getProcessPlan(AeMessageContext aContext) throws AeBusinessProcessException;   
   
   /**
    * Getter for the trasmit and receive id tracker.
    * @return Transmission id tracker.
    */
   public IAeTransmissionTracker getTransmissionTracker();

   /**
    * @return Returns the coordination manager.
    */
   public IAeCoordinationManagerInternal getCoordinationManager();
   
   /**
    * Returns true if message context indicates that invoke request should be coordinated.
    * @param aContext
    * @return returns true if the message context's business properties has a coordination context
    */
   public boolean isCoordinated(IAeMessageContext aContext);

    /**
    * Registers the given context for coordination with the coordination manager.
    * @param aContext message context
    * @param aProcess process
    * @return true if registered.
    * @throws AeBusinessProcessException
    */
   public boolean registerForCoordination(IAeMessageContext aContext, IAeBusinessProcess aProcess) throws AeBusinessProcessException;

   /**
    * Returns the interface to the engine callback.
    * @return engine call back interface.
    */
   public IAeBusinessProcessEngineCallback getEngineCallback();

   /**
    * Returns handler for responsible for process coordination operations.
    */
   public IAeProcessCoordination getProcessCoordination();

   /**
    * Fire an information event to report the results of an expression evaluation.
    * @param aPID The process ID.
    * @param aExpression The expression to evaluate.
    * @param aEventID The event ID for this evaluation.
    * @param aNodePath The node path for the object performing the evaluation.
    * @param aResult The result of the evaluation.
    */
   public void fireEvaluationEvent(long aPID, String aExpression, int aEventID, String aNodePath, String aResult);

   /**
    * Fires the information event passed to all process listeners.
    * @param aInfoEvent The information event to be fired.
    */
   public void fireInfoEvent(IAeProcessInfoEvent aInfoEvent);

   /**
    * Fires the event passed to all process listeners.
    * @param aEvent The event to be fired.
    */
   public void fireEvent(IAeProcessEvent aEvent);

   /**
    * Fires the event to all engine listeners.
    * @param aEvent The event to be fired.
    */
   public void fireEngineEvent(IAeEngineEvent aEvent);

   /**
    * Fires the alert to all engine listeners
    * @param aEvent
    */
   public void fireEngineAlert(IAeEngineAlert aEvent);

   /**
    * Fires a monitor event to all monitor listeners
    * @param aMonitorID The Id of the monitor event
    * @param aEventData The event data to be propagated 
    */
   public void fireMonitorEvent(int aMonitorID, long aEventData);
   
   /**
    * Sets the plan manager for the engine. This is used to resolve a
    * process QName to a process plan that provides hints in the form of
    * correlated properties and create instance.
    * @param aPlanManager
    */
   public void setPlanManager(IAePlanManager aPlanManager);

   /**
    * Starts the engine.
    */
   public void start() throws AeBusinessProcessException;

   /**
    * Stops the engine.
    */
   public void stop() throws AeBusinessProcessException;

   /**
    * Shuts the engine down.
    */
   public void shutDown() throws AeBusinessProcessException;

   /**
    * Dispatches alarm to a process by the location path of the alarm receiver.
    *
    * @param aProcessId The process id of the owner process.
    * @param aLocationPathId The location path id of the alarm receiver.
    * @param aGroupId The alarm's group id.
    * @param aAlarmId alarm id.
    */
   public void dispatchAlarm(long aProcessId, int aLocationPathId, int aGroupId, int aAlarmId) throws AeBusinessProcessException;

   /**
    * Returns the strategy for managing partner links.
    */
   public IAeEnginePartnerLinkStrategy getPartnerLinkStrategy();

   /**
    * Schedules an alarm for execution.
    *
    * @param aProcessId The ID of the BPEL process this alarm is associated with.
    * @param aLocationPathId The location path id of the alarm receiver.
    * @param aGroupId The alarm's group id.
    * @param aAlarmId The alarm instance execution id.
    * @param aDeadline The alarm deadline.
    */
   public void scheduleAlarm(long aProcessId, int aLocationPathId, int aGroupId, int aAlarmId, Date aDeadline) throws AeBusinessProcessException;

   /**
    * Removes an alarm.  The removal of an alarm requires it to be unscheduled (cancelled)
    * in addition to whatever other bookkeeping must be done by the alarm manager
    * implementation.  Note that <code>removeAlarm</code> will be called by the
    * engine when an alarm should be cancelled (not fired) as well as when the alarm
    * was fired and must now be removed from whatever internal storage the alarm manager
    * implementation is using.
    *
    * @param aProcessId The ID of the BPEL process this alarm is associated with.
    * @param aLocationPathId The location path id of the alarm receiver.
    * @param aAlarmId alarm id
    * @return True if the alarm was successfully removed.
    * @throws AeBusinessProcessException
    */
   public boolean removeAlarm(long aProcessId, int aLocationPathId, int aAlarmId) throws AeBusinessProcessException;

   /**
    * The invoke queue object contains all of the necessary information to perform
    * an invocation on a target endpoint. The actual invoke operation is handled
    * at a different layer asynchronously.
    * @param aPlan may contain additional information from the deployment layer like invokeHandler or addressing information
    * @param aInvokeQueueObject The entry to add.
    */
   public void addInvoke(IAeProcessPlan aPlan, IAeInvokeInternal aInvokeQueueObject) throws AeBusinessProcessException;

   /**
    * The subscribe invoke queue object contains all of the necessary information to perform
    * a subscription to topics on a stateful resource. The actual invoke operation is handled
    * at a different layer asynchronously.
    * @param aPlan may contain additional information from the deployment layer like invokeHandler or addressing information
    * @param aSubsInvokeQueueObject The entry to add.
    */
   public void addSubscribe(IAeProcessPlan aPlan, IAagSubscribeInvokeInternal aSubscribeInvokeQueueObject) throws AeBusinessProcessException;

   /**
    * The consumer factory invoke queue object contains all of the necessary information to call
    * the Muse BPELConsumerFactory to create a BPELConsumer for the process. The actual invoke
    * operation is handled at a different layer asynchronously.
    * @param aPlan may contain additional information from the deployment layer like invokeHandler or addressing information
    * @param aConsumerFactoryInvokeQueueObject The entry to add.
    */
   public void addCreateConsumer(IAeProcessPlan aPlan, IAagConsumerFactoryInvokeInternal aConsumerFactoryInvokeQueueObject) throws AeBusinessProcessException;

   /**
    * Queues a message receiver. This message receiver (e.g. receive, onMessage)
    * will stay queued until a message arrives for it from the outside world
    * or until it is explicity dequeued by the process.
    * @param aMessageReceiver The entry to add.
    */
   public void addMessageReceiver(AeMessageReceiver aMessageReceiver) throws AeBusinessProcessException;

   /**
    * Finds a matching message queue entry for the receiver and dequeues it.
    * @param aProcessId process Id for the message receiver
    * @param aMessageReceiverPathId The path id to the receiver
    * @return boolean True if the matching element was found and dequeued.
    * @throws AeBusinessProcessException
    */
   public boolean removeMessageReceiver(long aProcessId, int aMessageReceiverPathId) throws AeBusinessProcessException;

   /**
    * Sends the data to the response receiver that's waiting for the reply.
    * @param aReplyObject Identifies the reply object
    * @param aData data to send, if not <code>null</code> then the reply is a message
    * @param aFault fault to send, if not <code>null</code> then the reply is a fault
    * @param aProcessProperties The business process properties.
    * @throws AeMissingReplyReceiverException if no reply was found to be waiting
    * @throws AeBusinessProcessException if there is some communication error executing the reply
    */
   public void sendReply(AeReply aReplyObject, IAeMessageData aData, IAeFault aFault, Map aProcessProperties) throws AeBusinessProcessException;

   /**
    * Specialized version of queue receive data that accepts the process plan.
    * This method is intended to be used by our internal web service layer to
    * route messages to the engine. In that layer, we need to load the deployment
    * plan for the process in order to deserialize the SOAP message into our
    * message data container.
    *
    * @param aPlan Process plan for routing the message
    * @param aContext provides contextual information for message like partner link being targeted
    * @param aData contains the data extracted from the inbound message
    * @throws AeBusinessProcessException
    * @throws AeTimeoutException
    */
   public IAeWebServiceResponse queueReceiveData(IAeProcessPlan aPlan, IAeMessageContext aContext, IAeMessageData aData)
         throws AeBusinessProcessException, AeTimeoutException;

   /**
    * Accepts an inbound message which will either create a new process instance or get dispatched to
    * an existing message receiver from an already executing process.
    * If a new process was created, then this method returns the newly created process id
    * otherwise, returns <code>IAeBusinessProcess.NULL_PROCESS_ID</code>.
    *
    * If the <code>aQueueForExecution</code> was false, then the newly created process is not
    * queued for execution.
    *
    * @param aMessageData message data
    * @param aReply message reply receiver
    * @param aContext message context
    * @param aAckCallback durable messaging callback
    * @param aQueueForExecution if true and a process was created, it will be queued for execution.
    * @return If a process was created, then the process id is returned, else returns <code>IAeBusinessProcess.NULL_PROCESS_ID</code>.
    */
   public long queueReceiveData(IAeMessageData aMessageData, IAeReplyReceiver aReply, IAeMessageContext aContext,
            IAeMessageAcknowledgeCallback aAckCallback, boolean aQueueForExecution)
      throws AeCorrelationViolationException, AeConflictingRequestException, AeBusinessProcessException;

   /**
    *
    * @param processID
    * @param processQName
    * @param processWSNConsumerEPR
    */
   public void queueCreateConsumerData(long processID, QName processQName, EndpointReference processWSNConsumerEPR);

   public void queueCreateConsumerFault();

   public void queueSubscribeData(long processId, QName processName, String locationPath, IAagSubscribeResponse subsResponse);

   public void queueSubscribeFault();

   /**
    * Queues a previously created (sub)process for execution.
    *
    * @param aProcessId child process id.
    * @throws AeBusinessProcessException
    */
   public void executeProcess(long aProcessId) throws AeBusinessProcessException;

   /**
    * Returns the locationPath string given the locationId and the processId
    * @param aProcessId process id
    * @param aLocationId location id of the BPEL object.
    * @throws AeBusinessProcessException
    */
   public String getLocationPathById(long aProcessId, int aLocationId) throws AeBusinessProcessException;

   /**
    * Retrieve an object for the passed URI, note that this object may be cached.  The 
    * returned object is dependent on the passed type, currently wsdl types return AeBPELExtendedWSDLDef
    * and all others return InputSource.
    * @param aLocation 
    * @param aTypeURI 
    * @throws AeException wraps any type of expeption thrown while getting the resource
    */
   public Object loadResource( String aLocation, String aTypeURI ) throws AeException;

   /**
    * Removes the waiting reply object.
    * @param aReplyObject Identifies the reply object
    */
   public void removeReply(AeReply aReplyObject) throws AeBusinessProcessException;

   /**
    * Returns <code>true</code> if and only if the engine is running.
    */
   public boolean isRunning();
   
   /**
    * Returns the URN resolver associated with the engine.
    */
   public IAeURNResolver getURNResolver();

   /**
    * Returns the URL (including webapp and Axis service part) of the Active-BPEL SOAP stack
    * @return
    */
   public String getBaseInvocationURL();

   /**
    * Set the URL (including webapp and Axis service part) of the Active-BPEL SOAP stack 
    * @param baseURL
    */
   public void setBaseInvocationURL(String baseURL);

   /**
    * Updates a stateful variable in a process with the new value contained in this element
    */
   public void updateStatefulVariable(long processID, EndpointReference subscriptionReference, Element newValueElement);
}
