//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeTerminationHandler.java,v 1.2 2006/06/26 16:50:3
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
package org.activebpel.rt.bpel.impl.activity.support; 

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.def.AeTerminationHandlerDef;
import org.activebpel.rt.bpel.impl.IAeFCTHandler;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;

/**
 * TerminationHandler provides explicit way of providing behavior for a scope
 * that is being terminated. This replaces the 1.1 special fault: bpws:forcedTermination
 */
public class AeTerminationHandler extends AeFCTHandler implements IAeFCTHandler
{
   /**
    * Ctor accepts the def and the parent scope
    * @param aHandlerDef
    * @param aParent
    */
   public AeTerminationHandler(AeTerminationHandlerDef aHandlerDef, AeActivityScopeImpl aParent)
   {
      super(aHandlerDef, aParent);
   }

   /**
    * After running all of the activities within this handler,
    * this method returns true so that the coordinated activities are also compensated.
    *
    * @return Returns true if the coordinated activities (invokes)
    * should also be compensated after executing the normal list of CompInfo object.
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFCTHandler#isEnableCoordinatedActivityCompensation()
    */
   protected boolean isEnableCoordinatedActivityCompensation()
   {
      return true;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeAbstractBpelObject#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept(IAeImplVisitor aVisitor) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeExecutableQueueItem#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      getProcess().queueObjectToExecute(getActivity());
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFCTHandler#isAllowedToRethrow()
    */
   public boolean isAllowedToRethrow()
   {
      return false;
   }

   /**
    * notifies the scope that the termination handler has completed.
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFCTHandler#notifyScopeOfCompletion()
    */
   protected void notifyScopeOfCompletion() throws AeBusinessProcessException
   {
      AeActivityScopeImpl scope = (AeActivityScopeImpl) getParent();
      scope.terminationHandlerComplete();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFCTHandler#notifyScopeOfFaultedCompletion(org.activebpel.rt.bpel.IAeFault)
    */
   protected void notifyScopeOfFaultedCompletion(IAeFault aFault) throws AeBusinessProcessException
   {
      // termination handlers aren't allowed to rethrow so we should never get here
      notifyScopeOfCompletion();
   }
}
 
