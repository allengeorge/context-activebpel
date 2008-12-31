// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/child/IAeChildWorkCompletedListener.java,v 1.2 2007/06/20 19:40:0
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

import commonj.work.WorkItem;

/**
 * Defines interface for listeners to be notified when a work item completes.
 */
public interface IAeChildWorkCompletedListener
{
   /**
    * Called when the <code>Work</code> associated with the given
    * <code>WorkItem</code> completes.
    *
    * @param aWorkItem
    */
   public void workCompleted(WorkItem aWorkItem);
}
