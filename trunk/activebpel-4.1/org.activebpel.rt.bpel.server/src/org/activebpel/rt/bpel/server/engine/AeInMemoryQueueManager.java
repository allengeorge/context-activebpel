// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/AeInMemoryQueueManager.java,v 1.28 2007/07/26 14:12:1
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
package org.activebpel.rt.bpel.server.engine;

import commonj.timers.Timer;
import commonj.timers.TimerListener;
import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.config.IAeEngineConfiguration;
import org.activebpel.rt.bpel.impl.*;
import org.activebpel.rt.bpel.impl.queue.AeAlarm;
import org.activebpel.rt.bpel.impl.queue.AeInboundReceive;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.IAeProcessDeployment;
import org.activebpel.rt.bpel.server.deploy.AeProcessDeploymentFactory;
import org.activebpel.rt.bpel.server.deploy.AeProcessTransactionType;
import org.activebpel.rt.bpel.server.engine.invoke.AeInvoker;
import ece.uwaterloo.ca.aag.server.engine.AagSubscriber;
import ece.uwaterloo.ca.aag.server.engine.AagConsumerFactoryInvoker;
import org.activebpel.timer.AeAbstractTimerWork;
import org.activebpel.work.AeAbstractWork;
import org.activebpel.wsio.IAeMessageAcknowledgeCallback;
import org.activebpel.wsio.invoke.AeNullInvokeHandler;
import org.activebpel.wsio.invoke.IAeInvoke;
import org.activebpel.wsio.invoke.IAeInvokeHandler;

import java.util.Date;
import java.util.Map;

import ece.uwaterloo.ca.aag.server.engine.AagSubscribeHandler;
import ece.uwaterloo.ca.aag.server.engine.AagConsumerFactoryInvokeHandler;

/**
 * Queue Mananger for server side operations.  Handles invokes through
 * an installed invoke handler.  This class can be used both as a concrete
 * implementation of a queue manager, as well as a base class for other queue
 * manager implementations (such as the persistent queue manager).
 */
public class AeInMemoryQueueManager extends AeBaseQueueManager
{
   /** factory for creating invoke handlers */
   protected IAeInvokeHandlerFactory mInvokeHandlerFactory;
   /** factory for creating receive handlers */
   protected IAeReceiveHandlerFactory mReceiveHandlerFactory;
   
   /**
    * Constructs a queue manager from the given configuration map.
    *
    * @param aConfig The configuration map for this manager.
    */
   public AeInMemoryQueueManager(Map aConfig) throws AeException
   {
      super(aConfig);
      init( aConfig );
   }

   /**
    * Initialize the <code>IAeInvokeHandlerFactory</code> instance.
    * @param aConfig
    * @throws AeException
    */
   protected void init( Map aConfig ) throws AeException
   {
      Map invokeHandlerFactoryParams = (Map)aConfig.get( IAeEngineConfiguration.INVOKE_HANDLER_FACTORY );
      mInvokeHandlerFactory = (IAeInvokeHandlerFactory) AeEngineFactory.createConfigSpecificClass(invokeHandlerFactoryParams);
      Map receiveHandlerFactoryParams = (Map)aConfig.get( IAeEngineConfiguration.RECEIVE_HANDLER_FACTORY );
      mReceiveHandlerFactory = (IAeReceiveHandlerFactory) AeEngineFactory.createConfigSpecificClass(receiveHandlerFactoryParams);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeQueueManager#addInvoke(org.activebpel.rt.bpel.impl.IAeProcessPlan, org.activebpel.rt.bpel.impl.IAeInvokeInternal)
    */
   public void addInvoke(IAeProcessPlan aPlan, IAeInvokeInternal aInvokeQueueObject) throws AeBusinessProcessException
   {
      IAeProcessDeployment deployment = AeProcessDeploymentFactory.getDeploymentForPlan(aPlan);
      boolean isSynchronous = deployment.getTransactionType() == AeProcessTransactionType.CONTAINER;
      aInvokeQueueObject.setInvokeHandler(deployment.getInvokeHandler(aInvokeQueueObject.getPartnerLink()));       
      IAeInvokeHandler invokeHandler = getInvokeHandler( aInvokeQueueObject );
      String queryData = getInvokeHandlerFactory().getQueryData( aInvokeQueueObject );

      final AeInvoker invoker = new AeInvoker( aInvokeQueueObject, invokeHandler, queryData );
      boolean alreadyTransmitted = invoker.isTransmitted();
      boolean shouldHandle = true;

      // If the invoke is a one-way, and it was already transmitted,
      // then swap out the current invoke handler for a null/one-way
      // invoke handler.  This handler will not do anything - it will
      // simply return null as the response, which is what a one-way
      // invoke would normally get.  This should only happen during
      // recovery - i.e. the one-way invoke was already trasmitted
      // but is being re-executed.
      if (alreadyTransmitted && aInvokeQueueObject.isOneWay())
      {
         isSynchronous = true;
         invoker.setInvokeHandler(new AeNullInvokeHandler());
      }
      // If the invoke has already been transmitted, but it is NOT
      // one-way, then do a short return.
      else if (alreadyTransmitted)
      {
         shouldHandle = false;
      }
      // If the invoke has not yet been transmitted, then prepare to invoke.
      else
      {
         shouldHandle = invoker.prepareInvoke();
      }

      // clean up/deference response receiver from invoke queue object (see defect 2340).
      aInvokeQueueObject.dereferenceInvokeActivity();

      // If we shouldn't handle the invoke for some reason, do a short return.
      if (!shouldHandle)
         return;
      
      if (isSynchronous)
      {
         // run handler on current thread
         invoker.handleInvoke();
      }
      else
      { 
         // run handler on a worker thread.
         AeEngineFactory.schedule(aInvokeQueueObject.getProcessId(), 
            new AeAbstractWork() {
               public void run()
               {
                  // handle invoke (via worker thread)
                  invoker.handleInvoke();                  
               }
            } 
         );
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeQueueManager#addSubscribe(org.activebpel.rt.bpel.impl.IAeProcessPlan, org.activebpel.rt.bpel.impl.IAeInvokeInternal)
    */
   public void addSubscribe(IAeProcessPlan aPlan, IAagSubscribeInvokeInternal aSubsInvokeQueueObject) throws AeBusinessProcessException
   {
      // FIXME This implementation is _way_ half-baked
      IAeProcessDeployment deployment = AeProcessDeploymentFactory.getDeploymentForPlan(aPlan);
      boolean isSynchronous = deployment.getTransactionType() == AeProcessTransactionType.CONTAINER;
      // Unlike the invoke case, I know exactly what handler I need to use
      AagSubscribeHandler subscribeHandler = new AagSubscribeHandler();
      final AagSubscriber subscriber = new AagSubscriber(aSubsInvokeQueueObject, subscribeHandler);

      // FIXME, support only the "not transmitted" case
      // Clean up/deference response receiver from invoke queue object (see defect 2340).
      aSubsInvokeQueueObject.dereferenceInvokeActivity();

      if (isSynchronous)
      {
         // run handler on current thread
         subscriber.handleSubscribe();
      }
      else
      {
         // run handler on a worker thread.
         AeEngineFactory.schedule(aSubsInvokeQueueObject.getProcessId(),
            new AeAbstractWork() {
               public void run()
               {
                  // handle subscribe (via worker thread)
                  subscriber.handleSubscribe();                  
               }
            }
         );
      }
   }

   public void addConsumerFactoryInvoke(IAeProcessPlan aPlan, IAagConsumerFactoryInvokeInternal aCreateConsumerQueueObject) throws AeBusinessProcessException
   {
      // FIXME This implementation is _way_ half-baked
      IAeProcessDeployment deployment = AeProcessDeploymentFactory.getDeploymentForPlan(aPlan);
      boolean isSynchronous = deployment.getTransactionType() == AeProcessTransactionType.CONTAINER;
      // Unlike the invoke case, I know exactly what handler I need to use
      AagConsumerFactoryInvokeHandler consumerFactoryInvokeHandler = new AagConsumerFactoryInvokeHandler();
      final AagConsumerFactoryInvoker createConsumerFactoryInvoker = new AagConsumerFactoryInvoker(aCreateConsumerQueueObject, consumerFactoryInvokeHandler);

      // FIXME, support only the "not transmitted" case
      // Clean up/deference response receiver from invoke queue object (see defect 2340).
      aCreateConsumerQueueObject.dereferenceProcess();

      if (isSynchronous)
      {
         // run handler on current thread
         createConsumerFactoryInvoker.handleCreateConsumer();
      }
      else
      {
         // run handler on a worker thread.
         AeEngineFactory.schedule(aCreateConsumerQueueObject.getProcessId(),
            new AeAbstractWork() {
               public void run()
               {
                  // handle subscribe (via worker thread)
                  createConsumerFactoryInvoker.handleCreateConsumer();
               }
            }
         );
      }
   }

   /**
    * Return the appropriate <code>IAeInvokeHandler</code> impl to handle the invoke.
    * @param aInvokeQueueObject
    */
   protected IAeInvokeHandler getInvokeHandler( IAeInvoke aInvokeQueueObject ) throws AeBusinessProcessException
   {
      return getInvokeHandlerFactory().createInvokeHandler( aInvokeQueueObject );
   }

   /**
    * Return the appropriate <code>IAeReceiveHandler</code> impl to handle the service request.
    * @param aMessageContext
    */
   public IAeReceiveHandler getReceiveHandler( String aProtocol ) throws AeBusinessProcessException
   {
      return getReceiveHandlerFactory().createReceiveHandler( aProtocol );
   }
   
   /**
    * Accessor for invoke handler factory.
    */
   protected IAeInvokeHandlerFactory getInvokeHandlerFactory()
   {
      return mInvokeHandlerFactory;
   }

   /**
    * @return the receive handler factory
    */
   protected IAeReceiveHandlerFactory getReceiveHandlerFactory()
   {
      return mReceiveHandlerFactory;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.AeBaseQueueManager#addUnmatchedReceive(org.activebpel.rt.bpel.impl.queue.AeInboundReceive, org.activebpel.wsio.IAeMessageAcknowledgeCallback)
    */
   protected void addUnmatchedReceive(AeInboundReceive aInboundReceive, IAeMessageAcknowledgeCallback aAckCallback) throws AeCorrelationViolationException
   {
      super.addUnmatchedReceive(aInboundReceive, aAckCallback);

      // install a timer that will time the receive out if it still exists w/in the queue
      // make sure to use the timeout date that is already configured if one exists
      Date timeoutDate = aInboundReceive.getTimeoutDate();
      if (timeoutDate == null)
      {
         long timeout = getEngine().getEngineConfiguration().getUpdatableEngineConfig().getUnmatchedCorrelatedReceiveTimeout() * 1000;
         timeoutDate = new Date(System.currentTimeMillis() + timeout);
         aInboundReceive.setTimeoutDate(timeoutDate);
      }
      scheduleUnmatchedReceiveTimer(aInboundReceive, timeoutDate);
   }

   /**
    * Schedules an unmatched receive timer.  The timer is responsible for timing out (faulting) an unmatched 
    * receive that has been in the queue too long.
    * 
    * @param aInboundReceive
    */
   protected void scheduleUnmatchedReceiveTimer(AeInboundReceive aInboundReceive, Date aTimeoutDate)
   {
      Timer timer = aInboundReceive.getTimeoutTimer();
      if (timer != null)
      {
         timer.cancel();
      }
      timer = AeEngineFactory.getTimerManager().schedule(new AeUnmatchedReceiveTimerHandler(aInboundReceive.getQueueId()), aTimeoutDate);
      aInboundReceive.setTimeoutTimer(timer);
   }
   
   /**
    * Handler gets executed when the timeout period runs out for the unmatched
    * receive. The handler will remove the receive from the queue if it still
    * exists and then notify the waiting reply of the rejection (if there was
    * a reply)
    */
   class AeUnmatchedReceiveTimerHandler extends AeAbstractTimerWork
   {
      /** The queue id of the unmatched receive */
      private String mQueueId;

      /**
       * Constructor which requires a queue id of the unmatched receive
       * @param aQueueId The queue id of the unmatched receive
       */
      public AeUnmatchedReceiveTimerHandler(String aQueueId)
      {
         mQueueId = aQueueId;
      }

      /**
       * @see org.activebpel.timer.AeAbstractTimerWork#run()
       */
      public void run()
      {
         removeUnmatchedReceive(mQueueId);
      }
   }

   /**
    * Implement the schedule method for the base alarm manager to schedule
    * the alarm through the TimerManager configured in the EngineFactory.
    * @param aListener the listener to be scheduled
    * @param aDeadline the date this alarm should execute
    * @return Timer object created by the scheduler or null if the manager is stopped
    */
   protected Timer schedule(TimerListener aListener, Date aDeadline)
   {
      Timer timer = null;
      if (isStarted())
      {
         try
         {
            timer = AeEngineFactory.getTimerManager().schedule(aListener, aDeadline);
         }
         catch (IllegalStateException e)
         {
            // It is unlikely that this scenario will arrive but anything is possible
            // under load so here goes...
            //
            // We checked before calling schedule that the alarm manager is started
            // so it should be safe to schedule the alarm with the underlying timer
            // service. The managers are stopped before the timer service so if the 
            // manager is running then the timer service should be running.
            // However, there is still a possibility that the engine could be stopped
            // during this call. Therefore, I have the extra check below. If the 
            // manager is stopped then we'll simply log the exception and return null.
            // If the manager is running AND the timer service is stopped, then something
            // is definitely wrong and we should throw.
            //          -- MF
            if (!isStarted())
            {
               AeException.logError(e, e.getMessage());
            }
            else
            {
               throw e;
            }
         }
      }
      
      return timer;
   }

   /**
    * Creates the alarm listener which encapsulates the work to be performed when the alarm fires. 
    * 
    * @see org.activebpel.rt.bpel.impl.AeBaseQueueManager#createAlarmListener(org.activebpel.rt.bpel.impl.queue.AeAlarm)
    */
   protected TimerListener createAlarmListener(AeAlarm aAlarm)
   {
      return new AeAlarmListener(aAlarm);
   }
   
   /**
    * Inner class used to encapsulate the alarm work in its own thread.
    */
   static class AeAlarmListener extends AeAbstractWork implements TimerListener
   {
      /** Alarm definition */
      private AeAlarm mAlarm;
      
      /**
       * Constructor which takes as input the alarm to be processed when the timer expires.
       * @param aAlarm The Alarm definition
       */
      protected AeAlarmListener(AeAlarm aAlarm)
      {
         mAlarm = aAlarm;
      }
      
      /**
       * Work to be performed when alarm is fired. 
       * @see org.activebpel.timer.AeAbstractTimerWork#run()
       */
      public void run()
      {
         try
         {
            AeEngineFactory.getEngine().dispatchAlarm(mAlarm.getProcessId(), mAlarm.getPathId(), mAlarm.getGroupId(), mAlarm.getAlarmId());
         }
         catch(Throwable t)
         {
            AeException.logError(t, AeMessages.getString("AeInMemoryQueueManager.ERROR_DispatchingAlarm")); //$NON-NLS-1$
         }
      }

      /**
       * @see commonj.timers.TimerListener#timerExpired(commonj.timers.Timer)
       */
      public void timerExpired(Timer aTimer)
      {
         try
         {
            AeEngineFactory.scheduleAlarmWork(this);
         }
         catch (AeBusinessProcessException e)
         {
            AeException.logError(e, AeMessages.getString("AeInMemoryQueueManager.ERROR_ScheduleAlarmWork")); //$NON-NLS-1$
            
            // Do the work here on the timer thread.
            run();
         }
      }
   }
}
