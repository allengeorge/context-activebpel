// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/AeProcessWorkQueue.java,v 1.1 2006/01/07 00:05:0
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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.activebpel.rt.bpel.server.AeMessages;

/**
 * Holds the queue of <code>Work</code> objects waiting to be scheduled for
 * a process and the count of <code>Work</code> objects currently scheduled.
 */
public class AeProcessWorkQueue
{
   /** Process id for logging purposes. */
   private final long mProcessId;

   /** <code>Work</code> objects waiting to be scheduled with the "real" work manager. */
   private final Collection mWaitingQueue = new LinkedList();

   /** The number of <code>Work</code> objects currently scheduled for the process. */
   private int mScheduledCount = 0;

   /**
    * Constructor.
    *
    * @param aProcessId
    */
   public AeProcessWorkQueue(long aProcessId)
   {
      mProcessId = aProcessId;
   }

   /**
    * Adds a <code>Work</code> object to the queue.
    *
    * @param aWork
    */
   public void addWaiting(Work aWork)
   {
      getWaitingQueue().add(aWork);
   }

   /**
    * Decrements the count of <code>Work</code> objects currently scheduled
    * for the process.
    */
   public void decrementScheduledCount()
   {
      if (--mScheduledCount < 0)
      {
         // Reset it to a sane value.
         mScheduledCount = 0;

         throw new IllegalStateException(AeMessages.format("AeProcessWorkManager.ERROR_NegativeScheduledCount", getProcessId())); //$NON-NLS-1$
      }
   }

   /**
    * Returns the associated process id.
    */
   public long getProcessId()
   {
      return mProcessId;
   }

   /**
    * Returns the count of <code>Work</code> objects currently scheduled for
    * the process.
    */
   public int getScheduledCount()
   {
      return mScheduledCount;
   }

   /**
    * Returns the <code>Work</code> objects waiting to be scheduled.
    */
   protected Collection getWaitingQueue()
   {
      return mWaitingQueue;
   }

   /**
    * Increments the count of <code>Work</code> objects currently scheduled
    * for the process.
    */
   public void incrementScheduledCount()
   {
      ++mScheduledCount;
   }

   /**
    * Returns <code>true</code> if and only if the queue is empty and
    * nothing is scheduled.
    */
   public boolean isEmpty()
   {
      return getWaitingQueue().isEmpty() && (getScheduledCount() == 0);
   }

   /**
    * Returns an <code>Iterator</code> over the <code>Work</code> objects
    * waiting to be scheduled.
    */
   public Iterator waitingIterator()
   {
      return getWaitingQueue().iterator();
   }
}
