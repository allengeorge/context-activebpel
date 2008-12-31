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
package org.activebpel.rt.bpel.def.visitors;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.activity.IAeScopeTerminationStrategy;

/**
 * Strategy used to terminate an executing scope for WS-BPEL 2.0. If the scope is already executing a fault handler
 * then the termination handler is unavailable and the fault handler is allowed to continue. Otherwise, queue
 * the termination handler to execute.
 */
public class AeWSBPELScopeTerminationStrategy implements IAeScopeTerminationStrategy
{
   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeScopeTerminationStrategy#onHandleTermination(org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl)
    */
   public void onHandleTermination(AeActivityScopeImpl aImpl) throws AeBusinessProcessException
   {
      aImpl.getProcess().queueObjectToExecute(aImpl.getTerminationHandler());
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeScopeTerminationStrategy#onStartTermination(org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl)
    */
   public void onStartTermination(AeActivityScopeImpl aImpl) throws AeBusinessProcessException
   {
      if (aImpl.isExecutingFaultHandler())
      {
         return;
      }
      else
      {
         aImpl.startTermination();
      }
   }
}
