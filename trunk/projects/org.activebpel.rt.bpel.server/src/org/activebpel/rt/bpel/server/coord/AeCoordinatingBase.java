//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/AeCoordinatingBase.java,v 1.2 2006/06/26 18:28:2
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
package org.activebpel.rt.bpel.server.coord;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.IAeBusinessProcessEngine;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.coord.AeCoordinationException;
import org.activebpel.rt.bpel.coord.AeCoordinationFaultException;
import org.activebpel.rt.bpel.coord.IAeCoordinating;
import org.activebpel.rt.bpel.coord.IAeCoordinationContext;
import org.activebpel.rt.bpel.coord.IAeProtocolMessage;
import org.activebpel.rt.bpel.coord.IAeProtocolState;
import org.activebpel.rt.bpel.impl.IAeCoordinationManagerInternal;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.util.AeUtil;

/**
 * Base class for any object that is participating in a coordinated activity.
 * This class provides a basic framework for managing protocol signals and state transitions.
 */
public abstract class AeCoordinatingBase implements IAeCoordinating
{
   /**
    * The coordination manager. 
    */
   private IAeCoordinationManagerInternal mCoordinationManager = null;
   
   /**
    * Current state.
    */
   private IAeProtocolState mState = null;
   
   /**
    * Look up table for state transitions.
    */
   private IAeProtocolStateTable mStateTable = null;
   
   /**
    * Process id of the activity participating in the coordination.
    */
   private long mProcessId = 0;
   
   /**
    * Location path.
    */
   private String mLocationPath;
   
   /** Coordiantion id */
   private String mCoordinationId = null;
   
   /** Coordination context. */
   private IAeCoordinationContext mContext = null;

   /**
    * Base constructor given the context and the manager.
    */
   public AeCoordinatingBase(IAeCoordinationContext aContext, IAeCoordinationManagerInternal aCoordinationManager)
   {
      mCoordinationManager = aCoordinationManager;
      mContext = aContext;
      setCoordinationId(mContext.getIdentifier());
      setLocationPath(aContext.getProperty(IAeCoordinating.AE_COORD_LOCATION_PATH));
      setProcessId(aContext.getProperty(IAeCoordinating.AE_COORD_PID));      
   }   
   
   /***
    * Convenience method that returns the engine instance.
    * @return engine instance.
    */
   protected IAeBusinessProcessEngine getEngine()
   {
      return AeEngineFactory.getEngine();
   }
   
   /**
    * @return the coordination manager instance.
    */
   protected IAeCoordinationManagerInternal getCoordinationManager()
   {
      return mCoordinationManager;
   }
   
   /**
    * Returns the transition table. If one has not been initialized, this method calls
    * createProtocolTable method to create one.
    * 
    * @return protocol table.
    */
   protected IAeProtocolStateTable getTable()
   {
      if (mStateTable == null)
      {
         mStateTable = createProtocolTable();
      }
      return mStateTable;
   }
   
   /**
    * Creates a protocol state table. The subclasses (protocol specific) are responsible for returning
    * a table.
    * @return protocol table. 
    */
   protected abstract IAeProtocolStateTable createProtocolTable();

   /**
    * Returns the current state. 
    * @see org.activebpel.rt.bpel.coord.IAeCoordinating#getState()
    */
   public IAeProtocolState getState()
   {
      if (mState == null)
      {
         mState = getTable().getInitialState();
      }
      return mState;
   }
   
   /**
    * Sets the current state.
    * @param aState current state.
    */
   public void setState(IAeProtocolState aState) throws AeCoordinationException
   {
      if (aState != null)
      {
         mState = aState;
      }
      else
      {
         // illegal state change! throw?
         throw new AeCoordinationFaultException(AeCoordinationFaultException.INVALID_STATE);
      }      
   }
   
   /** 
    * Dispatches the protocol message to either the coordinator or the participant.
    * i.e. delivers the message to the target's objects onMessage(...) method.
    * @param aMessage
    */
   protected void dispatchMessage(IAeProtocolMessage aMessage, boolean aViaProcessExeQueue)
   {      
      // dispatch message to coordinator or participant.
      getCoordinationManager().dispatch(aMessage, aViaProcessExeQueue);
   }
   
   /**
    * Method to transition to the next state and dispatch the given message.
    * @param aMessage message sent.
    */
   protected void changeStateAndDispatch(IAeProtocolMessage aMessage) throws AeCoordinationException
   {
      changeState(aMessage, true, false);
   }   
   
   /**
    * Method to transition to the next state but does not dispatch any messages.
    * @param aMessage message sent.
    */
   protected void changeState(IAeProtocolMessage aMessage) throws AeCoordinationException
   {
      changeState(aMessage, false, false);
   }
   
   
   /**
    * Method to transition to the next state and dispatch the given message.
    * @param aMessage message sent.
    * @param aDispatch if true, dispatches the message.
    */
   protected void changeState(IAeProtocolMessage aMessage, boolean aDispatch, boolean aViaProcessExeQueue) throws AeCoordinationException
   {
      // get the current state
      IAeProtocolState currState = getState();
      // determine the next state that should be transitioned to for the sent/received message.
      IAeProtocolState nextState = getTable().getNextState(currState, aMessage);
      // set the next state
      setState(nextState);
      
      // persist current state info
      getCoordinationManager().persistState(this);
      
      // dispatch message
      if (aDispatch)
      {
         dispatchMessage(aMessage, aViaProcessExeQueue);
      }
   }   
   
   /**
    * Returns true if the message is a valid message to be dispatched in the current state.
    * @param aMessage
    * @return true if the message is valid for the given state.
    */
   protected abstract boolean canDispatch(IAeProtocolMessage aMessage);


   /**
    * Handles protocol messages and transitions to the appropriate state.
    * @param aMessage protocol message.
    */
   public abstract void queueReceiveMessage(IAeProtocolMessage aMessage) throws AeCoordinationException;
       
   /**
    * Callback when a process is complete.
    * 
    * @param aFaultObject fault object if the process completed with a fault.
    * @param aNormalCompletion indiciates that the process completed normally and is eligible fo compensation. 
    */   
   public abstract void onProcessComplete(IAeFault aFaultObject, boolean aNormalCompletion);
   
   /**
    * @see org.activebpel.rt.bpel.coord.IAeCoordinating#getCoordinationContext()
    */
   public IAeCoordinationContext getCoordinationContext()
   {
      return mContext;
   }  
   
   /**
    * @return Returns the coordinationId.
    */
   public String getCoordinationId()
   {
      return mCoordinationId;
   }
   
   /**
    * @param aCoordinationId The coordinationId to set.
    */
   public void setCoordinationId(String aCoordinationId)
   {
      mCoordinationId = aCoordinationId;
   }
   
   /**
    * @return Returns the locationPath.
    */
   public String getLocationPath()
   {
      return mLocationPath;
   }
   
   /**
    * @param aLocationPath The locationPath to set.
    */
   public void setLocationPath(String aLocationPath)
   {
      mLocationPath = aLocationPath;
   }
   
   /**
    * @return Returns the processId.
    */
   public long getProcessId()
   {
      return mProcessId;
   }
   
   /**
    * @param aProcessId The processId to set.
    */
   public void setProcessId(long aProcessId)
   {
      mProcessId = aProcessId;
   }
   
   /**
    * Sets the process id.
    * @param aPidStr
    */
   protected void setProcessId(String aPidStr)
   {
      if (AeUtil.notNullOrEmpty(aPidStr))
      {
         try
         {
            setProcessId(Long.parseLong(aPidStr));
         }
         catch (Exception e)
         {
            //ignore
            AeException.logError(e, e.getMessage());
         }
      }      
   }
}
