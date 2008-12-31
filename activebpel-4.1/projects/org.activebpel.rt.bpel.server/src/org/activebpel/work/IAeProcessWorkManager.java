// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/IAeProcessWorkManager.java,v 1.1 2006/01/07 00:05:0
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

import org.activebpel.rt.bpel.AeBusinessProcessException;

import commonj.work.Work;

/**
 * Defines the interface for a work manager that limits the amount of work
 * scheduled per process.
 */
public interface IAeProcessWorkManager
{
   /**
    * Schedules the given work for the given process.
    *
    * @param aProcessId
    * @param aWork
    */
   public void schedule(long aProcessId, Work aWork) throws AeBusinessProcessException;
}
