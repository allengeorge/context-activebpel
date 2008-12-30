// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/input/AeDefaultInputMessageWorkManager.java,v 1.1 2007/07/31 20:53:4
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
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Implements an {@link IAeInputMessageWorkManager} that delegates to
 * {@link AeEngineFactory#schedule(long, commonj.work.Work)}.
 */
public class AeDefaultInputMessageWorkManager implements IAeInputMessageWorkManager
{
   /**
    * Overrides method to delegate to
    * {@link AeEngineFactory#schedule(long, commonj.work.Work)} thus treating
    * the given input message work just like any other work scheduled for the
    * given process.
    *
    * @see org.activebpel.work.input.IAeInputMessageWorkManager#schedule(long, org.activebpel.work.input.IAeInputMessageWork)
    */
   public void schedule(long aProcessId, IAeInputMessageWork aInputMessageWork) throws AeBusinessProcessException
   {
      AeEngineFactory.schedule(aProcessId, aInputMessageWork);
   }

   /**
    * Overrides method to ignore stop request.
    *  
    * @see org.activebpel.work.input.IAeInputMessageWorkManager#stop()
    */
   public void stop()
   {
      // Nothing to do.
   }
}
