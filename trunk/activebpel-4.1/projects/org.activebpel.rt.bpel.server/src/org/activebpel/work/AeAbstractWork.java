// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/AeAbstractWork.java,v 1.2 2004/07/08 13:10:0
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

/**
 * Base class to help create a Work instance.
 */
abstract public class AeAbstractWork implements Work
{
   /** true if this work */
   boolean mDaemon = false;

   /** no-arg constructor */   
   public AeAbstractWork()
   {
   }
   
   /** accepts the daemon flag */
   public AeAbstractWork(boolean aDaemon)
   {
      mDaemon = aDaemon;
   }

   /**
    * @see commonj.work.Work#release()
    */
   public void release()
   {
   }

   /**
    * A hint to the work manager as to whether the thread for this Work should
    * come from a pool or not.
    * @see commonj.work.Work#isDaemon()
    */
   public boolean isDaemon()
   {
      return mDaemon;
   }
}
