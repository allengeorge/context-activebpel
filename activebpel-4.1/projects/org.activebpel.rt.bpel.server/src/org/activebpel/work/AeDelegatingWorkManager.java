// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/AeDelegatingWorkManager.java,v 1.1 2006/01/07 00:05:3
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

import java.util.Collection;

import commonj.work.Work;
import commonj.work.WorkException;
import commonj.work.WorkItem;
import commonj.work.WorkListener;
import commonj.work.WorkManager;

/**
 * Implements a <code>WorkManager</code> that delegates to another
 * <code>WorkManager</code>.
 */
public class AeDelegatingWorkManager implements WorkManager
{
   /** The delegate <code>WorkManager</code>. */
   private final WorkManager mDelegateWorkManager;

   /**
    * Constructor.
    *
    * @param aDelegateWorkManager
    */
   public AeDelegatingWorkManager(WorkManager aDelegateWorkManager)
   {
      mDelegateWorkManager = aDelegateWorkManager;
   }

   /**
    * Returns the delegate <code>WorkManager</code>.
    */
   protected WorkManager getDelegateWorkManager()
   {
      return mDelegateWorkManager;
   }

   /**
    * @see commonj.work.WorkManager#schedule(commonj.work.Work)
    */
   public WorkItem schedule(Work aWork) throws WorkException, IllegalArgumentException
   {
      return getDelegateWorkManager().schedule(aWork);
   }

   /**
    * @see commonj.work.WorkManager#schedule(commonj.work.Work, commonj.work.WorkListener)
    */
   public WorkItem schedule(Work aWork, WorkListener aWorkListener) throws WorkException, IllegalArgumentException
   {
      return getDelegateWorkManager().schedule(aWork, aWorkListener);
   }

   /**
    * @see commonj.work.WorkManager#waitForAll(java.util.Collection, long)
    */
   public boolean waitForAll(Collection aWorkItems, long aTimeoutMillis) throws InterruptedException, IllegalArgumentException
   {
      return getDelegateWorkManager().waitForAll(aWorkItems, aTimeoutMillis);
   }

   /**
    * @see commonj.work.WorkManager#waitForAny(java.util.Collection, long)
    */
   public Collection waitForAny(Collection aWorkItems, long aTimeoutMillis) throws InterruptedException, IllegalArgumentException
   {
      return getDelegateWorkManager().waitForAny(aWorkItems, aTimeoutMillis);
   }
}
