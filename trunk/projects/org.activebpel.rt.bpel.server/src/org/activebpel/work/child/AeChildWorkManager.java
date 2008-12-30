// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/child/AeChildWorkManager.java,v 1.2 2007/06/20 19:40:0
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
package org.activebpel.work.child;

import commonj.work.Work;
import commonj.work.WorkException;
import commonj.work.WorkItem;
import commonj.work.WorkListener;
import commonj.work.WorkManager;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Implements a work manager that limits the amount of work scheduled to a
 * parent work manager.
 */
public class AeChildWorkManager implements WorkManager, IAeChildWorkCompletedListener
{
   /** Name that identifies this work manager in log messages. */
   private final String mName;

   /** The parent work manager. */
   private final WorkManager mParentWorkManager;

   /** <code>WorkItem</code>s waiting to be scheduled to the parent work manager. */
   private final Collection mWaitingQueue = new LinkedList();

   /** The maximum number of work items to schedule from this work manager to its parent. */
   private int mMaxWorkCount; // initialized by constructor

   /** The number of work items currently scheduled in the parent work manager from this work manager. */
   private int mScheduledCount = 0;

   /**
    * Constructor.
    *
    * @param aName
    */
   public AeChildWorkManager(String aName, int aMaxWorkCount)
   {
      this(aName, aMaxWorkCount, AeEngineFactory.getWorkManager());
   }

   /**
    * Constructor.
    *
    * @param aName
    * @param aParentWorkManager
    */
   public AeChildWorkManager(String aName, int aMaxWorkCount, WorkManager aParentWorkManager)
   {
      mName = aName;
      mMaxWorkCount = aMaxWorkCount;
      mParentWorkManager = aParentWorkManager;
   }
   
   /**
    * Decrements the count of work items currently scheduled in the parent work
    * manager.
    */
   protected void decrementScheduledCount()
   {
      if (--mScheduledCount < 0)
      {
         // Reset it to a sane value.
         mScheduledCount = 0;

         throw new IllegalStateException(AeMessages.format("AeChildWorkManager.ERROR_NegativeScheduledCount", getName())); //$NON-NLS-1$
      }
   }

   /**
    * @return the maximum number of work items to schedule from this work manager to its parent
    */
   protected int getMaxWorkCount()
   {
      return mMaxWorkCount;
   }

   /**
    * @return the name of this work manager
    */
   protected String getName()
   {
      return mName;
   }

   /**
    * @return the count of work items currently scheduled in the parent work manager from this work manager
    */
   protected int getScheduledCount()
   {
      return mScheduledCount;
   }

   /**
    * @return the <code>Collection</code> of work items waiting to be scheduled to the parent work manager
    */
   protected Collection getWaitingQueue()
   {
      return mWaitingQueue;
   }

   /**
    * @return this work manager's parent
    */
   protected WorkManager getParentWorkManager()
   {
      return mParentWorkManager;
   }

   /**
    * Increments the count of work items currently scheduled in the parent work
    * manager.
    */
   protected void incrementScheduledCount()
   {
      ++mScheduledCount;
   }
   
   /**
    * @see commonj.work.WorkManager#schedule(commonj.work.Work)
    */
   public WorkItem schedule(Work aWork) throws WorkException
   {
      return schedule(aWork, null);
   }

   /**
    * @see commonj.work.WorkManager#schedule(commonj.work.Work, commonj.work.WorkListener)
    */
   public synchronized WorkItem schedule(Work aWork, WorkListener aWorkListener) throws WorkException
   {
      AeChildWorkItem item = new AeChildWorkItem(aWork, aWorkListener, this);
      item.addChildWorkCompletedListener(this);

      getWaitingQueue().add(item);
      scheduleWaiting();

      return item;
   }

   /**
    * Schedules waiting work to the parent work manager.
    */
   protected synchronized void scheduleWaiting() throws WorkException
   {
      // Iterate through the waiting work items until we have scheduled the
      // maximum possible number of work items.
      for (Iterator i = getWaitingQueue().iterator(); i.hasNext() && (getScheduledCount() < getMaxWorkCount()); )
      {
         AeChildWorkItem item = (AeChildWorkItem) i.next();

         // Schedule the work with its listener to the parent work manager.
         WorkItem scheduledItem = getParentWorkManager().schedule(item.getWork(), item.getWorkListener());

         // Save the actual WorkItem returned by the parent work manager.
         item.setScheduledWorkItem(scheduledItem);

         // And we're off! Remove the work item from the queue and increment the
         // scheduled count.
         i.remove();
         incrementScheduledCount();
      }
   }

   /**
    * Convenience method that calls {@link #scheduleWaiting()} and handles any
    * <code>WorkException</code>s.
    */
   protected void scheduleWaitingNoException()
   {
      try
      {
         scheduleWaiting();
      }
      catch (WorkException e)
      {
         AeException.logError(e, AeMessages.format("AeChildWorkManager.ERROR_ScheduleWaiting", getName())); //$NON-NLS-1$
      }
   }

   /**
    * Sets the maximum number of work items to schedule from this child work
    * manager to its parent.
    *
    * @param aMaxWorkCount
    */
   protected void setMaxWorkCount(int aMaxWorkCount)
   {
      if (aMaxWorkCount > 0)
      {
         mMaxWorkCount = aMaxWorkCount;
      }
      else
      {
         // Specifying 0 or less means no limit.
         mMaxWorkCount = Integer.MAX_VALUE;
      }

      // The work count may have increased, allowing us to schedule more work.
      scheduleWaitingNoException();
   }

   /**
    * @see commonj.work.WorkManager#waitForAll(java.util.Collection, long)
    */
   public boolean waitForAll(Collection aWorkItems, long aTimeoutMillis) throws InterruptedException, IllegalArgumentException
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see commonj.work.WorkManager#waitForAny(java.util.Collection, long)
    */
   public Collection waitForAny(Collection aWorkItems, long aTimeoutMillis) throws InterruptedException, IllegalArgumentException
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.work.child.IAeChildWorkCompletedListener#workCompleted(commonj.work.WorkItem)
    */
   public synchronized void workCompleted(WorkItem aWorkItem)
   {
      decrementScheduledCount();

      scheduleWaitingNoException();
   }
}
