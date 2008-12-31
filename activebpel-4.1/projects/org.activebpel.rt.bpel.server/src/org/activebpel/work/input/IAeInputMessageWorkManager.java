// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/input/IAeInputMessageWorkManager.java,v 1.1 2007/07/31 20:53:4
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
package org.activebpel.work.input;

import org.activebpel.rt.bpel.AeBusinessProcessException;

/**
 * Defines the interface for a work manager that schedules instances of
 * {@link IAeInputMessageWork}.
 */
public interface IAeInputMessageWorkManager
{
   /**
    * Schedules input message work for the given process id.
    *
    * @param aProcessId
    * @param aInputMessageWork
    */
   public void schedule(long aProcessId, IAeInputMessageWork aInputMessageWork) throws AeBusinessProcessException;

   /**
    * Signals the input message work manager to stop. 
    */
   public void stop();
}
