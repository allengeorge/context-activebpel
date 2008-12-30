// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/AeExceptionReportingWorkManager.java,v 1.2 2006/01/12 21:52:1
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
import commonj.work.WorkException;
import commonj.work.WorkItem;
import commonj.work.WorkListener;
import commonj.work.WorkManager;

/**
 * Extends {@link org.activebpel.work.AeDelegatingWorkManager} to wrap scheduled
 * work in instances of {@link org.activebpel.work.AeExceptionReportingWork}.
 */
public class AeExceptionReportingWorkManager extends AeDelegatingWorkManager implements IAeStoppableWorkManager
{
   /**
    * Constructor.
    *
    * @param aDelegateWorkManager
    */
   public AeExceptionReportingWorkManager(WorkManager aDelegateWorkManager)
   {
      super(aDelegateWorkManager);
   }

   /**
    * @see commonj.work.WorkManager#schedule(commonj.work.Work)
    */
   public WorkItem schedule(Work aWork) throws WorkException, IllegalArgumentException
   {
      return super.schedule(new AeExceptionReportingWork(aWork));
   }

   /**
    * @see commonj.work.WorkManager#schedule(commonj.work.Work, commonj.work.WorkListener)
    */
   public WorkItem schedule(Work aWork, WorkListener aWorkListener) throws WorkException, IllegalArgumentException
   {
      return super.schedule(new AeExceptionReportingWork(aWork), aWorkListener);
   }

   /**
    * @see org.activebpel.work.IAeStoppableWorkManager#stop() 
    */
   public void stop()
   {
      if (getDelegateWorkManager() instanceof IAeStoppableWorkManager)
      {
         ((IAeStoppableWorkManager) getDelegateWorkManager()).stop();
      }
   }
}
