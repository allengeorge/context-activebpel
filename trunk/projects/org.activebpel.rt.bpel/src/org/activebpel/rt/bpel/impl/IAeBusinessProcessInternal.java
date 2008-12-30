// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeBusinessProcessInternal.java,v 1.8 2007/01/27 14:40:5
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.*;
import org.activebpel.rt.bpel.def.AePartnerLinkOpKey;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.activity.IAeMessageReceiverActivity;
import org.activebpel.rt.bpel.impl.lock.IAeVariableLocker;
import org.activebpel.rt.message.IAeMessageData;

import javax.xml.namespace.QName;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Internal extensions to business process allows classes within
 * the implemenation to use the process while still hiding the implementor.
 */
public interface IAeBusinessProcessInternal extends IAeBusinessProcess, IAeActivity
{
   /**
    * Returns true if the process is exiting. If a process is exiting then there is no 
    * fault handling, compensation, or termination handling. All activities must terminate
    * immediately.
    */
   public boolean isExiting();

   /**
    * Adds a reference for the variable at the given location path
    * @param aVar the process variable
    */
   public void addVariableMapping(IAeVariable aVar );
   
   /**
    * Adds a map to the partnerlink by its location.
    *
    * @param aPartnerLink
    */
   public void addPartnerLinkMapping(AePartnerLink aPartnerLink);

   /**
    * The process maintains unique values for each version of a variable and partner link.
    * Each time a variable's (or partner link's) data changes it gets a new id for the storage layer.
    */
   public int getNextVersionNumber();

   /**
    * Sets the next id to use when getNextVariableVersionNumber() is called.
    * @param aId
    */
   public void setNextVersionNumber(int aId);

   /**
    * Initializes the given partner link (based on deployment info).
    * 
    * @param aPartnerLink
    */
   public void initPartnerLink(AePartnerLink aPartnerLink) throws AeBusinessProcessException;
   
   /**
    * Adds a reference for the object at the given location path.
    * 
    * @param aLocationPath an XPath location string of the object
    * @param aObject the Bpel implementation object
    */
   public void addBpelObject(String aLocationPath, IAeBpelObject aObject);
   
   /** 
    * @return next invoke id.
    */
   public long getNextInvokeId();   
   
   /** 
    * @return next alarm id.
    */
   public int getNextAlarmId();   

   /**
    * Called by serializable scopes prior to execution to ensure that they have
    * all of the necessary locks on the variables being used by their enclosed
    * activities. If the locks are immediately obtained then the activity proceeds
    * with its normal execution path. If not, then execution is halted for the
    * activity until the lock can be acquired. At that time, the activity will
    * resume normal execution and release any of its locks after it is complete.
    * @param aSetOfVariablePaths
    * @param aOwnerXPath
    */
   public boolean addExclusiveLock(Set aSetOfVariablePaths, String aOwnerXPath);

   /**
    * Called by activities prior to execution to ensure that they have
    * all of the necessary locks on the variables being used by their enclosed
    * activities. If the locks are immediately obtained then the activity proceeds
    * with its normal execution path. If not, then execution is halted for the
    * activity until the lock can be acquired. At that time, the activity will
    * resume normal execution and release any of its locks after it is complete.
    * @param aSetOfVariablePaths
    * @param aOwnerXPath
    */
   public boolean addSharedLock(Set aSetOfVariablePaths, String aOwnerXPath);

   /**
    * Dequeues an alarm that has been queued.
    * @param aAlarm The alarm to dequeue.
    */
   public void dequeueAlarm(IAeAlarmReceiver aAlarm)
      throws AeBusinessProcessException;

   /**
    * Removes a message receiver from the list of message receivers which are active.
    * @param aMessageReceiver The receiver to remove.
    * @throws AeBusinessProcessException Thrown if error occurs removing the receiver.
    */
   public void dequeueMessageReceiver(IAeMessageReceiverActivity aMessageReceiver)
         throws AeBusinessProcessException;

   /**
    * Faults all orphaned open IMAs for the given scope, and removes those
    * orphaned IMAs from the list of open IMAs for the process.
    *
    * @param aScope
    * @param aFault
    */
   public void faultOrphanedIMAs(AeActivityScopeImpl aScope, IAeFault aFault);

   /**
    * Gets the expression language factory.
    */
   public IAeExpressionLanguageFactory getExpressionLanguageFactory() throws AeException;

   /**
    * Return the <code>List</code> of location paths for any activities that
    * are in the faulting state.  This <code>List</code> will be empty if there
    * are none.
    */
   public List getFaultingActivityLocationPaths();

   /**
    * Gets the def used to create this process.
    */
   public AeProcessDef getProcessDefinition();

   /**
    * Returns <code>true</code> if and only if there is at least one open IMA
    * not already marked orphaned that references a message exchange or partner
    * link defined within the given scope.
    *
    * @param aScope
    */
   public boolean hasNewOrphanedIMAs(AeActivityScopeImpl aScope);
   
   /**
    * Returns true if the process is configured to create target xpath.
    */
   public boolean isAllowCreateTargetXPath();

   /**
    * Returns true if the process is configured to disable the selection failure fault.
    */
   public boolean isDisableSelectionFailure();
   
   /**
    * Returns true if there is data available for this receive. This is used in
    * handling multi-start processes.
    * @param aPartnerLinkOpKey
    */
   public boolean isReceiveDataQueued(AePartnerLinkOpKey aPartnerLinkOpKey);
   
   /**
    * Returns true if the start message is available for the process.
    */
   public boolean isStartMessageAvailable();

   /**
    * This method allows a callback for links, which allows for either deadpath
    * elimination or allowing a queued activity to execute.
    * @param aLink The link that has changed its status.
    */
   public void linkStatusChanged(IAeLink aLink);

   /**
    * This method is a callback for objects to notify the process that it is done.
    * An object's completion is a two step process. The first step is reaching the
    * completion state which is done by calling this method. The second step is
    * the evaluation of an object's links. The parent should be notified of the child's
    * completion after the links have been evaluated in order to catch any faults during link
    * evaluation.
    * @param aObject The object which has completed.
    */
   public void objectCompleted(IAeBpelObject aObject) throws AeBusinessProcessException;

   /**
    * This method is a callback for objects to notify the process that it is done,
    * but that an error has occured as indicated by the passed fault object.
    * The process will then notify the parent and fire appropriate events.
    *
    * @param aObject The object which has completed.
    * @param aFaultObject The fault which caused the object to complete.
    */
   public void objectCompletedWithFault(IAeBpelObject aObject, IAeFault aFaultObject) throws AeBusinessProcessException;

   /**
    * This method is a callback for objects to notify the process that they're
    * state has changed.
    * @param aObject
    * @param aOldState
    * @param aDetailsObject  - object used to report any additional information along with the state change,
    *                          most notably the fault that caused the object to
    *                          become faulted.
    */
   public void objectStateChanged(IAeBpelObject aObject, AeBpelState aOldState, IAeStateChangeDetail aDetailsObject) throws AeBusinessProcessException;

   /**
    * Queue an alarm to callback when the passed dealine has been reached.
    * @param aAlarm The alarm to callback when the dealine is reached.
    * @param aDeadline The deadline for the alarm.
    */
   public void queueAlarm(IAeAlarmReceiver aAlarm, Date aDeadline)
      throws AeBusinessProcessException;

   /**
    * Will queue an invoke to take place.  A framework in the engine
    * will typically perform the actual invoke and then call back
    * the invoke response receiver.
    * 
    * @param aInvoke The object to receive the callback when the invoke response is ready.
    * @param aInputMessage The input message for the invoke, null if none.
    * @param aPartnerLink The partner link
    * @param aPartnerLinkOpImplKey The partner link key
    * @throws AeBusinessProcessException Thrown if error occurs setting the receiver.
    */
   public void queueInvoke(IAeInvokeActivity aInvoke,
                            IAeMessageData aInputMessage,
                            IAePartnerLink aPartnerLink,
                            AePartnerLinkOpImplKey aPartnerLinkOpImplKey)
         throws AeBusinessProcessException;

   /**
    * Sets a message receiver for the given correlated business operation
    * from the specified partner link.
    * @param aMessageReceiver The receiver to call back when the message is received.
    * @param aGroupId
    * @throws AeBusinessProcessException Thrown if error occurs setting the receiver.
    */
   public void queueMessageReceiver(IAeMessageReceiverActivity aMessageReceiver, int aGroupId) throws AeBusinessProcessException;

   /**
    * Queues the reply.
    * 
    * @param aInputMessage can be null
    * @param aFaultName can be null
    * @param aPartnerLinkOpImplKey
    * @param aMessageExchange can be null
    * @throws AeBusinessProcessException
    */
   public void queueReply(IAeMessageData aInputMessage,
                          QName aFaultName,
                          AePartnerLinkOpImplKey aPartnerLinkOpImplKey,
                          String aMessageExchange)
         throws AeBusinessProcessException;

   /**
    * Removes the receiver from the process's collection of receive activities used
    * to track bpws:conflictingReceives. This gets called by the receive or onMessage
    * when it receives its data. In the case of an onEvent within a scope, the
    * onMessage will not remove itself until it has been dequeued since events
    * remain queued in BPEL 2.0.
    * @param aMessageReceiver
    */
   public void removeReceiverKeyForConflictingReceives(IAeMessageReceiverActivity aMessageReceiver);

   /**
    * Removes the waiting reply object.
    *
    * @param aPartnerLinkOpImplKey
    * @param aMessageExchange
    * @throws AeBusinessProcessException
    */
   public void removeReply(AePartnerLinkOpImplKey aPartnerLinkOpImplKey, String aMessageExchange) throws AeBusinessProcessException;

   /**
    * Sets the flag to that determines if this is a coordinator.
    * @param aCoordinator
    */
   public void setCoordinator(boolean aCoordinator);

   /**
    * Sets the state of the process.
    */
   public void setProcessState(int aState);
   
   /**
    * Getter for the variable locker
    */
   public IAeVariableLocker getVariableLocker();
}
