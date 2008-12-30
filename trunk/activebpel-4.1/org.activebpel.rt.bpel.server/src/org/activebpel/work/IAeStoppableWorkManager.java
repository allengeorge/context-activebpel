// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/IAeStoppableWorkManager.java,v 1.1 2006/01/12 21:52:1
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

import commonj.work.WorkManager;

/**
 * Defines interface for work managers that can be stopped automatically when
 * the engine shuts down.
 */
public interface IAeStoppableWorkManager extends WorkManager
{
   /**
    * Signals the work manager to stop. If the dispatch thread is currently running
    * it'll be interrupted. 
    */
   public void stop();
}
