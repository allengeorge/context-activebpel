// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/AeQueuedWork.java,v 1.2 2004/07/08 13:10:0
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
import commonj.work.WorkListener;


/**
 * Holds work that is waiting for a thread to execute it.
 */   
public class AeQueuedWork
{
   /** Work that will be executed */
   private Work mWork;
   /** Optional listener to receive callbacks regarding work progress */
   private WorkListener mListener;
   /** WorkItem that reports the status of the work */
   private AeWorkItem mItem;
   
   /**
    * Creates an entry for work to be queued.
    * @param aWork
    * @param aWorkItem
    * @param aWorkListener
    */
   public AeQueuedWork(Work aWork, AeWorkItem aWorkItem, WorkListener aWorkListener)
   {
      setWork(aWork);
      setItem(aWorkItem);
      setListener(aWorkListener);
   }

   /**
    * Setter for the work
    * @param aWork
    */
   protected void setWork(Work aWork)
   {
      mWork = aWork;
   }

   /**
    * Getter for the work
    */
   protected Work getWork()
   {
      return mWork;
   }

   /**
    * Setter for the listener
    * @param aListener
    */
   protected void setListener(WorkListener aListener)
   {
      mListener = aListener;
   }

   /**
    * Getter for the listener
    */
   protected WorkListener getListener()
   {
      return mListener;
   }

   /**
    * Setter for the item
    * @param aItem
    */
   protected void setItem(AeWorkItem aItem)
   {
      mItem = aItem;
   }

   /**
    * Getter for the item
    */
   protected AeWorkItem getItem()
   {
      return mItem;
   }
}
