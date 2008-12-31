// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/AeWorkItem.java,v 1.4 2005/02/10 23:00:4
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
import commonj.work.WorkEvent;
import commonj.work.WorkException;
import commonj.work.WorkItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Reports the status of work that has been scheduled with the work manager.
 */
public class AeWorkItem implements WorkItem
{
   /** work that was scheduled */
   protected Work mWork;
   /** exception thrown during execution of work */
   protected WorkException mException;
   /** stores the status of the work that was scheduled */
   protected int mStatus;
   /** list of work done listeners */
   protected List mListeners;
   
   /**
    * Constructor which takes as input a Work object.
    * @param aWork the work object this WorkItem represents
    */
   public AeWorkItem(Work aWork)
   {
      mWork = aWork;
   }   
   
   /**
    * Adds the listener which is to be notified when work has completed.
    * @param aListener the listener to be added
    */
   public synchronized void addWorkDoneListener(IAeWorkDoneListener aListener)
   {
      if (mListeners == null)
         mListeners = new ArrayList();
      
      mListeners.add(aListener);
   }
   
   /**
    * Notifies the listeners that the work is done. This method gets called by
    * the work manager for synchronization reasons.
    */
   public synchronized void notifyListeners()
   {
      for (Iterator iter=getListeners().iterator(); iter.hasNext();)
         ((IAeWorkDoneListener)iter.next()).workDone();
   }
   
   /** 
    * Returns the Work object created as part of this WorkItem.
    */
   protected Work getWork()
   {
      return mWork;
   }
   
   /**
    * Getter for the list of listeners with lazy instantiation.
    */
   protected List getListeners()
   {
      return (mListeners != null ? mListeners : Collections.EMPTY_LIST);
   }
   
   /**
    * @see commonj.work.WorkItem#getStatus()
    */
   public int getStatus()
   {
      return mStatus;
   }
   
   /**
    * Setter for the status
    * @param aStatus
    */
   public void setStatus(int aStatus)
   {
      mStatus = aStatus;
   }
   
   /**
    * Sets the exception that was thrown during the execution of the work.
    * @param aWorkException
    */
   public void setException(WorkException aWorkException)
   {
      mException = aWorkException;
   }

   /**
    * @see commonj.work.RemoteWorkItem#getResult()
    */
   public Work getResult() throws WorkException
   {
      Work work = null;
      if (getStatus() == WorkEvent.WORK_COMPLETED)
      {
         if (mException != null)
            throw mException;

         work = getWork();
      }
      return work;
   }

   /**
    * Note: this class has a natural ordering that is inconsistent with equals.
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(Object aOther)
   {
      if (getStatus() == ((WorkItem)aOther).getStatus())
         return 0;
      else if (getStatus() > ((WorkItem)aOther).getStatus())
         return -1;
      else
         return 1;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      if (aOther instanceof AeWorkItem)
         return (((AeWorkItem)aOther).getWork()).equals(getWork());

      return super.equals(aOther);
   }
   
   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return getWork().hashCode();
   }
}
