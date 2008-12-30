//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/subprocess/AeSpCoordinator.java,v 1.5 2007/01/25 21:38:1
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
package org.activebpel.rt.bpel.server.coord.subprocess;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.coord.AeCoordinationException;
import org.activebpel.rt.bpel.coord.IAeCoordinationContext;
import org.activebpel.rt.bpel.coord.IAeProtocolMessage;
import org.activebpel.rt.bpel.coord.IAeProtocolState;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.IAeCoordinationManagerInternal;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.coord.IAeProtocolStateTable;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Implementation of a AE subprocess protcol coordinator. The aesp protocol is loosely based on
 * Business Agreement protocol described in the BPEL-4WS 1.1, appendix C.
 */
public class AeSpCoordinator extends AeSpCoordinatingBase implements IAeSpCoordinator
{
   /**
    * Default constructor.
    */
   public AeSpCoordinator(IAeCoordinationContext aContext, IAeCoordinationManagerInternal aCoordinationManager)
   {
      super(aContext, aCoordinationManager);
   }

   /**
    * Overrides method to create and return an instance of AeSpProtocolTable.
    * @see org.activebpel.rt.bpel.server.coord.AeCoordinatingBase#createProtocolTable()
    */
   protected IAeProtocolStateTable createProtocolTable()
   {
      return new AeSpProtocolTable();
   }


   /**
    * Returns true if the message is a valid message to be dispatched in the current state.
    * @param aMessage
    * @return true if the message is valid for the given state.
    */
   protected boolean canDispatch(IAeProtocolMessage aMessage)
   {
      // cannot dispatch message when in ENDED state.
      return !( AeSpCoordinationStates.ENDED.equals(getState()) );
   }

   /**
    * Overrides method to handle incoming protocol messages.
    * @see org.activebpel.rt.bpel.coord.IAeCoordinating#queueReceiveMessage(org.activebpel.rt.bpel.coord.IAeProtocolMessage)
    */
   public void queueReceiveMessage(IAeProtocolMessage aMessage) throws AeCoordinationException
   {
      // coordinators view of the participant's current state
      IAeProtocolState currState = getState();
      // coordinators view of the participant's next state
      IAeProtocolState nextState = getTable().getNextState(currState, aMessage);

      if (nextState == null)
      {
         // invalid state change!
         Object params[] = { String.valueOf(currState), String.valueOf(aMessage) };
         AeException.logWarning(AeMessages.format("AeCoordinatingBase.INVALID_STATE_CHANGE",params)); //$NON-NLS-1$
         return;
      }
      else if (nextState.equals(currState))
      {
         // state has not changed - but we may want to handle this case.
         // for now, just return.
         return;
      }

      // if the participants were told to either cancel or compensate (equivalent to fire and forget),
      // then next expect messag is canceled, closed,  compensated faulted. Since the current
      // state COMPENSATING_OR_CANCELING is fire and forget, change state to ended and deregister
      // coordination.
      if (AeSpCoordinationStates.COMPENSATING_OR_CANCELING.equals( getState() ))
      {
         changeState(aMessage);
         deregister();
      }
      // Handle following messages sent by the participant (subprocess):
      // canceled, completed, closed, compensated, exited, faulted/active, faulted/compensating.
      else if (AeSpCoordinationMessages.CANCELED.equalsSignal(aMessage))
      {
         onMessageCanceled(aMessage);
      }
      else if (AeSpCoordinationMessages.COMPLETED.equalsSignal(aMessage))
      {
         onMessageCompleted(aMessage);
      }
      else if (AeSpCoordinationMessages.CLOSED.equalsSignal(aMessage))
      {
         onMessageClosed(aMessage);
      }
      else if (AeSpCoordinationMessages.COMPENSATED.equalsSignal(aMessage))
      {
         onMessageCompensated(aMessage);
      }
      else if (AeSpCoordinationMessages.EXITED.equalsSignal(aMessage))
      {
         onMessageExited(aMessage);
      }
      else if (AeSpCoordinationMessages.FAULTED_ACTIVE.equalsSignal(aMessage))
      {
         onMessageFaultedActive(aMessage);
      }
      else if (AeSpCoordinationMessages.FAULTED_COMPENSATING.equalsSignal(aMessage))
      {
         onMessageFaultedCompensating(aMessage);
      }
      else
      {
         // unknown message - ignore for now.
         AeException.logWarning(AeMessages.format("AeCoordinatingBase.UNKNOWN_MESSAGE",String.valueOf(aMessage))); //$NON-NLS-1$
      }
   }

   /**
    * Callback via the process manager when coordinator process is complete.
    *
    * @param aFaultObject fault object if the process completed with a fault.
    * @param aNormalCompletion indiciates that the process completed normally and is eligible fo compensation.
    */
   public void onProcessComplete(IAeFault aFaultObject, boolean aNormalCompletion)
   {
      // what to do do if the parent process (coordinator) completed or faulted.
      // for now terminate the sub process?

      // curr view of the participant.
      IAeProtocolState currState = getState();
      if (AeSpCoordinationStates.ENDED.equals(currState))
      {
         // participant/subprocess is already done!
         return;
      }
      // create a message to cancel or compensate the sub process.
      else if (AeSpCoordinationStates.ACTIVE.equals(currState))
      {
         // Defect 2268 race condition. Process A completes while Process B is still active
         // or before Process B gets a chance to reliabaly xmt the COMPLETED message to A.
         // For example, a participant (Proc_B) may have completed and indicated this (asynchronously)
         // to the coordinator (Proc_A). In the meantime, Proc_A completes before receiving the
         // COMPLETED msg from Proc_B. At this point Proc_A assumes Proc_B coord state is still ACTIVE.
         //
         // In this case,  we need to signal Proc_B to either CANCEL (if it is still running/ACTIVE)
         // or COMPENSATE (if Proc_B has COMPLETED).
         try
         {
            compensateOrCancel();
         }
         catch(Exception e)
         {
            AeException.logError(e,e.getMessage());
         }
         return;
      }

      IAeProtocolMessage msg = null;
      if (AeSpCoordinationStates.COMPLETED.equals(currState))
      {
         msg = createMessage(AeSpCoordinationMessages.CLOSE);
      }
      else if (AeSpCoordinationStates.FAULTED_ACTIVE.equals(currState)
            || AeSpCoordinationStates.FAULTED_COMPENSATING.equals(currState))
      {
         msg = createMessage(AeSpCoordinationMessages.FORGET);
      }

      // change to next state and send off message.
      if (msg != null)
      {
         try
         {
            changeStateAndDispatch(msg);
         }
         catch (AeCoordinationException e)
         {
            AeException.logError(e,e.getMessage());
         }
      }
   }

   /**
    * Registers the coordination id with process invoke activity's enclosing scope.
    *
    * @see org.activebpel.rt.bpel.coord.IAeCoordinator#register()
    */
   public void register() throws AeCoordinationException
   {
      // set the current state to active.
      setState(AeSpCoordinationStates.ACTIVE);

      // add coordination id to enclosing scope.
      IAeBusinessProcessEngineInternal engine = (IAeBusinessProcessEngineInternal)AeEngineFactory.getEngine();
      engine.getProcessCoordination().registerCoordinationId( getProcessId(), getLocationPath(), getCoordinationId());

   }

   /**
    * Deregisters the current coordination with the invoke activity's enclosing scope.
    * (The enclosing scope is not complete until all participants (subprocesses) have
    * ended and deregistered.)
    *
    * @see org.activebpel.rt.bpel.coord.IAeCoordinator#deregister()
    */
   public void deregister() throws AeCoordinationException
   {
      // remove coordination id from enclosing scope.
      IAeBusinessProcessEngineInternal engine = (IAeBusinessProcessEngineInternal)AeEngineFactory.getEngine();
      engine.getProcessCoordination().deregisterCoordinationId( getProcessId(), getLocationPath(), getCoordinationId());
   }

   /**
    * Overrides method to begin the compensation process by dispatching a COMPENSATE message
    * to the participant.
    * @see org.activebpel.rt.bpel.server.coord.subprocess.IAeSpCoordinator#compensate()
    */
   public void compensate() throws AeCoordinationException
   {
      // should a check be made to see if the participant is in COMPLETED state?
      IAeProtocolMessage msg = null;
      msg = createMessage(AeSpCoordinationMessages.COMPENSATE);
      try
      {
         changeStateAndDispatch(msg);
      }
      catch (AeCoordinationException e)
      {
         throw e;
      }
   }

   /**
    * Signals the activity under coordination (ie - the participant) to either compensate (if it has
    * completed) or cancel (it if is still running).
    *
    * This method is normally send to participants in 'active' state during fault/comp handler execution.
    */
   public void compensateOrCancel() throws AeCoordinationException
   {
      IAeProtocolMessage msg = null;
      msg = createMessage(AeSpCoordinationMessages.COMPENSATE_OR_CANCEL); // fire and forget
      try
      {
         // change state and dispatch via (sub) process's execution queue.
         changeState(msg, true, true);
      }
      catch (AeCoordinationException e)
      {
         throw e;
      }
   }

   /**
    * Overrides method to signal the participant to cancel.
    * @see org.activebpel.rt.bpel.coord.IAeCoordinator#cancel()
    */
   public void cancel() throws AeCoordinationException
   {
      IAeProtocolMessage msg = createMessage(AeSpCoordinationMessages.CANCEL);
      try
      {
         // change state and dispatch via (sub) process's execution queue.
         changeState(msg, true, true);
      }
      catch (AeCoordinationException e)
      {
         throw e;
      }
   }

   /**
    * Handle the COMPLETED message sent by the participant. On receiving the COMPLETED message,
    * a compensation handler on behalf of the participant is installed into the invoke activity's
    * enclosing scope.
    * @param aMessage
    * @throws AeCoordinationException
    */
   protected void onMessageCompleted(IAeProtocolMessage aMessage) throws AeCoordinationException
   {
      // participant has completed and is eligible for compensation.
      // update coordinator's view of the participant state to COMPLETED.
      changeState(aMessage);

      // install compensation handler.
      IAeBusinessProcessEngineInternal engine = (IAeBusinessProcessEngineInternal)AeEngineFactory.getEngine();
      engine.getProcessCoordination().installCompensationHandler( getProcessId(), getLocationPath(), getCoordinationId(), this);

   }

   /**
    * Handle the EXITED message sent by the participant.  The exited message indicates that
    * the participant have transitioned to the ENDED state, hence this coordination will be
    * removed (deregistered) from the invoke activity's enclosing scope.
    * @param aMessage
    * @throws AeCoordinationException
    */
   protected void onMessageExited(IAeProtocolMessage aMessage) throws AeCoordinationException
   {
      // participant has exited the process.
      // update coordinator's view of the participant state to ENDED.
      changeState(aMessage);
      deregister();
   }

   /**
    * Handle the CANCLED message sent by the participant.  The cancled message indicates that
    * the participant have transitioned to the ENDED state, hence this coordination will be
    * removed (deregistered) from the invoke activity's enclosing scope.
    * @param aMessage
    * @throws AeCoordinationException
    */
   protected void onMessageCanceled(IAeProtocolMessage aMessage) throws AeCoordinationException
   {
      // participant has completed the cancel request.
      // update coordinator's view of the participant state to ENDED.
      changeState(aMessage);
      deregister();
   }

   /**
    * Handle the CLOSED message sent by the participant.  The closed message indicates that
    * the participant have transitioned to the ENDED state, hence this coordination will be
    * removed (deregistered) from the invoke activity's enclosing scope.
    * @param aMessage
    * @throws AeCoordinationException
    */
   protected void onMessageClosed(IAeProtocolMessage aMessage) throws AeCoordinationException
   {
      // participant has completed the close request.
      // update coordinator's view of the participant state to ENDED.
      changeState(aMessage);
      deregister();
   }

   /**
    * Handle the COMPENSATED message sent by the participant.  The compensated message indicates that
    * the participant have successfully compensated itself and have transitioned to the ENDED state.
    * The installed compensation handler will be signaled that it completed.
    * This coordination will be removed (deregistered) from the invoke activity's enclosing scope.
    * @param aMessage
    * @throws AeCoordinationException
    */
   protected void onMessageCompensated(IAeProtocolMessage aMessage) throws AeCoordinationException
   {
      // participant has completed the compensate request.
      // update coordinator's view of the participant state to ENDED.
      changeState(aMessage);

      // signal the compHandler that its done.
      IAeBusinessProcessEngineInternal engine = (IAeBusinessProcessEngineInternal)AeEngineFactory.getEngine();
      engine.getProcessCoordination().compensationCompletedCallback( getProcessId(), getLocationPath(), getCoordinationId());

      // deregister.
      deregister();
   }

   /**
    * Handle the FAULTED_COMPENSATED message sent by the participant.  This message indicates that
    * the participant faulted while compensating. This method signals the installed compensationHandler
    * that it completedWithFault.
    * @param aMessage
    * @throws AeCoordinationException
    */
   protected void onMessageFaultedCompensating(IAeProtocolMessage aMessage) throws AeCoordinationException
   {
      // participant has faulted during compensation.
      // update coordinator's view of the participant state to FAULTED_COMPENSATING.
      changeState(aMessage);

      // signal the compHandler that it 'completed with fault'.
      IAeBusinessProcessEngineInternal engine = (IAeBusinessProcessEngineInternal)AeEngineFactory.getEngine();
      engine.getProcessCoordination().compensationCompletedWithFaultCallback( getProcessId(), getLocationPath(), getCoordinationId(), aMessage.getFault() );
   }

   /**
    * Handle the FAULTED_ACTIVE message sent by the participant.  This message indicates that
    * the participant have faulted.
    * @param aMessage
    * @throws AeCoordinationException
    */
   protected void onMessageFaultedActive(IAeProtocolMessage aMessage) throws AeCoordinationException
   {
      // participant has faulted during normal activity. It is not eligible for compensation!
      // update coordinator's view of the participant state to FAULTED_ACTIVE.
      changeState(aMessage);

      //Fault the enclosing scope
      IAeBusinessProcessEngineInternal engine = (IAeBusinessProcessEngineInternal)AeEngineFactory.getEngine();
      engine.getProcessCoordination().activityFaulted( getProcessId(), getLocationPath(), getCoordinationId(), aMessage.getFault() );
   }

}
