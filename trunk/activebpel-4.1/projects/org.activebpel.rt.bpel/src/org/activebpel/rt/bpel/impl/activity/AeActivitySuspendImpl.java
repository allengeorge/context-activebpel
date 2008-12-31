//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeActivitySuspendImpl.java,v 1.5 2006/09/22 19:52:3
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
package org.activebpel.rt.bpel.impl.activity; 

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.AeActivitySuspendDef;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.AeSuspendReason;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Implementation of the suspend activity. Immediately suspends the process and
 * issues an alert with the optional variable to the alerting system.
 */
public class AeActivitySuspendImpl extends AeActivityImpl
{
   /**
    * Ctor takes the def
    * 
    * @param aDef
    * @param aParent
    */
   public AeActivitySuspendImpl(AeActivitySuspendDef aDef, IAeActivityParent aParent)
   {
      super(aDef, aParent);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept(IAeImplVisitor aVisitor)
         throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }
   
   /**
    * Extends base to suspend the process when we are ready to execute.
    * @see org.activebpel.rt.bpel.impl.IAeExecutableQueueItem#setState(org.activebpel.rt.bpel.impl.AeBpelState)
    */
   public void setState(AeBpelState aNewState)
         throws AeBusinessProcessException
   {
      super.setState(aNewState);
      
      // suspend the process if we are ready to execute
      if(aNewState.equals(AeBpelState.READY_TO_EXECUTE))
      {
         IAeVariable variable = null;
         String varName = getDef().getVariable();
         if (AeUtil.notNullOrEmpty(varName))
         {
            variable = findVariable(varName);
            if (!variable.hasData())
            {
               variable = null;
            }
         }
         
         getProcess().suspend(new AeSuspendReason(AeSuspendReason.SUSPEND_CODE_LOGICAL, getLocationPath(), variable));
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeExecutableQueueItem#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      objectCompleted();
   }
   
   /**
    * Getter for the def
    */
   protected AeActivitySuspendDef getDef()
   {
      return (AeActivitySuspendDef) getDefinition();
   }
}
 
