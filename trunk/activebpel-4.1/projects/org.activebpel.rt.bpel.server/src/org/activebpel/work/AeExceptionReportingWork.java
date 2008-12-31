// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/AeExceptionReportingWork.java,v 1.3 2007/08/16 15:38:4
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
package org.activebpel.work;

import commonj.work.Work;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.IAeMonitorListener;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Extends {@link org.activebpel.work.AeDelegatingWork} to report any
 * exceptions thrown when the <code>Work</code> runs.  Additionally
 * it interacts with the engine monitor system to report on the time it 
 * took from construction to running (i.e. long times indicate that
 * the work manager thread pool is not large enough).
 */
public class AeExceptionReportingWork extends AeDelegatingWork implements Work
{
   /** The time the work was constructed. */
   private long mStartTime = System.currentTimeMillis(); 

   /**
    * Constructor.
    *
    * @param aDelegateWork
    */
   public AeExceptionReportingWork(Work aDelegateWork)
   {
      super(aDelegateWork);
   }

   /**
    * @see org.activebpel.work.AeDelegatingWork#doRun()
    */
   public void run()
   {
      try
      {
         // If engine has not yet started, do not try and send monitor events 
         if (getEngine() != null)
         {
            // Notify monitor listeners that the work took current - start amount of time to execute
            long delay = System.currentTimeMillis() - mStartTime;
            getEngine().fireMonitorEvent(IAeMonitorListener.MONITOR_WM_START_TIME, delay);
         }
         
         // now run the original work
         super.run();
      }
      catch (Throwable t)
      {
         AeException.logError(t, AeMessages.getString("AeExceptionReportingWork.ERROR_UnhandledException")); //$NON-NLS-1$
      }
   }
   
   /** 
    * Convenince method for accessing the engine.
    */
   protected IAeBusinessProcessEngineInternal getEngine()
   {
      return AeEngineFactory.getEngine();
   }
}
