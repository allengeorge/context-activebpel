// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/AeRemoteWorkItem.java,v 1.3 2005/02/10 23:00:4
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

import commonj.work.RemoteWorkItem;
import commonj.work.Work;
import commonj.work.WorkManager;

/**
 * Remote work items are returned to the caller when the work that was scheduled
 * implements Serializable. The current work manager implementation doesn't support
 * scheduling work in other jvms but it's still required to return a remote
 * work item when something Serializable is scheduled.  
 */
public class AeRemoteWorkItem extends AeWorkItem implements RemoteWorkItem
{
   /** work manager that executed the work */
   private WorkManager mWorkManager;
   
   /**
    * Constructor accepts the work manager and the work to be done.
    * @param aWorkManager
    * @param aWork
    */
   public AeRemoteWorkItem(WorkManager aWorkManager, Work aWork)
   {
      super(aWork);
      mWorkManager = aWorkManager;
   }

   /**
    * @see commonj.work.RemoteWorkItem#release()
    */
   public void release()
   {
      getWork().release();
   }

   /**
    * @see commonj.work.RemoteWorkItem#getPinnedWorkManager()
    */
   public WorkManager getPinnedWorkManager()
   {
      return mWorkManager;
   }
}
