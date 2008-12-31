//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/timer/AeAbstractTimerWork.java,v 1.2 2005/02/24 23:31:1
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
package org.activebpel.timer;

import commonj.timers.Timer;
import commonj.timers.TimerListener;
import commonj.work.WorkException;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.work.AeAbstractWork;

/**
 * This abstract class is an adapter used to facilitate scheduling work using the timer facility.
 */
public abstract class AeAbstractTimerWork extends AeAbstractWork implements TimerListener 
{
   /**
    * Called when the timer we scheduled has expired so that we may perform the appropriate work. 
    * @see commonj.timers.TimerListener#timerExpired(commonj.timers.Timer)
    */
   public void timerExpired(Timer aTimer)
   {
      try
      {
         AeEngineFactory.getWorkManager().schedule(this);
      }
      catch (WorkException we)
      {
         AeException.logError(we, AeMessages.getString("AeTimerListenerAdapter.ERROR_ErrorFiringTimer")); //$NON-NLS-1$
      }
   }
   
   /** 
    * The actual timer work to be performed.  
    * Note this work is executed via the engine's work manager.
    */
   abstract public void run();
}
