// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/AeDelegatingWork.java,v 1.2 2007/06/20 19:40:2
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
 * Implements a <code>Work</code> object that delegates to another
 * <code>Work</code> object.
 */
public class AeDelegatingWork implements Work
{
   /** The delegate <code>Work</code> object. */
   private final Work mDelegateWork;

   /**
    * Constructor.
    *
    * @param aDelegateWork
    */
   public AeDelegatingWork(Work aDelegateWork)
   {
      mDelegateWork = aDelegateWork;
   }

   /**
    * Returns the delegate <code>Work</code> object.
    */
   public Work getDelegateWork()
   {
      return mDelegateWork;
   }

   /**
    * @see commonj.work.Work#isDaemon()
    */
   public boolean isDaemon()
   {
      return getDelegateWork().isDaemon();
   }

   /**
    * @see commonj.work.Work#release()
    */
   public void release()
   {
      getDelegateWork().release();
   }

   /**
    * @see java.lang.Runnable#run()
    */
   public void run()
   {
      getDelegateWork().run();
   }
}
