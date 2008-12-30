// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/recovered/AeRecoveredAddInvokeItem.java,v 1.7 2007/08/14 12:41:0
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
package org.activebpel.rt.bpel.server.engine.recovery.recovered;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.IAeInvokeInternal;
import org.activebpel.rt.bpel.impl.IAeProcessPlan;
import org.activebpel.rt.bpel.server.AeMessages;

/**
 * Implements a recovered item to add an invoke.
 */
public class AeRecoveredAddInvokeItem extends AeRecoveredLocationIdItem implements IAeRecoveredItem
{
   /** The invoke object. */
   private final IAeInvokeInternal mInvokeQueueObject;
   /** The process plan. */
   private final IAeProcessPlan mProcessPlan;

   /**
    * Constructs a recovered item to add an invoke.
    */
   public AeRecoveredAddInvokeItem(IAeInvokeInternal aInvokeQueueObject, IAeProcessPlan aProcessPlan)
   {
      super(aInvokeQueueObject.getProcessId(), aInvokeQueueObject.getLocationId());

      mInvokeQueueObject = aInvokeQueueObject;
      mProcessPlan = aProcessPlan;
   }

   /**
    * Constructs a recovered item to match another add invoke item by location
    * id.
    */
   public AeRecoveredAddInvokeItem(IAeInvokeInternal aInvokeQueueObject)
   {
      this(aInvokeQueueObject, null);
   }

   /**
    * Returns the invoke object.
    */
   public IAeInvokeInternal getInvokeQueueObject()
   {
      return mInvokeQueueObject;
   }

   /**
    * Returns the process plan.
    */
   protected IAeProcessPlan getProcessPlan()
   {
      return mProcessPlan;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#queueItem(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
    */
   public void queueItem(IAeBusinessProcessEngineInternal aTargetEngine) throws AeBusinessProcessException
   {
      if (getProcessPlan() == null)
      {
         throw new IllegalStateException(AeMessages.getString("AeRecoveredAddInvokeItem.ERROR_0")); //$NON-NLS-1$
      }

      aTargetEngine.getQueueManager().addInvoke(getProcessPlan(), getInvokeQueueObject());
   }
}
