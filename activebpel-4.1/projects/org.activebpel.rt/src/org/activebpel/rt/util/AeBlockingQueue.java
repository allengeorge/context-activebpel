// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeBlockingQueue.java,v 1.4 2004/08/03 21:09:5
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
package org.activebpel.rt.util;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of a blocking queue.  
 */
public class AeBlockingQueue
{
   /** list of objects for our queue */
   private List mQueue = new LinkedList();
   
   /**
    * Adds a new object to the queue, calling notify() on this object.
    * @param aObject
    */
   public synchronized void add(Object aObject)
   {
      mQueue.add(aObject);
      notify();
   }
   
   /**
    * Waits for the queue to not be empty. 
    */
   public synchronized void waitForObject()
   {
      while(mQueue.isEmpty())
      {
         try
         {
            wait();
         }
         catch (InterruptedException e)
         {
            // interrupted means we are shutting down
            break;
         }
      }
   }
   
   /**
    * Gets the first object from the queue
    */
   public synchronized Object getNextObjectOrWait()
   {
      waitForObject();
      if (!mQueue.isEmpty())
      {
         return mQueue.remove(0);
      }
      return null;
   }
}
