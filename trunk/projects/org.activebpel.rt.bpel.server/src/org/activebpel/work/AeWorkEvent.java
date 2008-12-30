// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/AeWorkEvent.java,v 1.5 2005/02/10 23:00:4
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

import commonj.work.WorkEvent;
import commonj.work.WorkException;
import commonj.work.WorkItem;

import org.activebpel.rt.bpel.server.AeMessages;

/**
 * Event that gets fired to a WorkListener to report progress on a Work object. 
 */
public class AeWorkEvent implements WorkEvent
{
   /** type of event */
   private int mType;
   /** WorkItem that event relates to */
   private WorkItem mWorkItem;
   /** exception, if any, that was a result of the work's execution */
   private WorkException mException;
   
   /**
    * Creates a work event for the specified work object and type.  
    */
   public AeWorkEvent(WorkItem aWorkItem, int aType, WorkException aException)
   {
      mType = aType;
      mException = aException;

      if (aException != null && aType != WorkEvent.WORK_COMPLETED)
         throw new IllegalArgumentException(AeMessages.getString("AeWorkEvent.ERROR_0")); //$NON-NLS-1$
   }

   /**
    * @see commonj.work.WorkEvent#getType()
    */
   public int getType()
   {
      return mType;
   }

   /**
    * @see commonj.work.WorkEvent#getException()
    */
   public WorkException getException()
   {
      return mException;
   }

   /**
    * @see commonj.work.WorkEvent#getWorkItem()
    */
   public WorkItem getWorkItem()
   {
      return mWorkItem;
   }
}
