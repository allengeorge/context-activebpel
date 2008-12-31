// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeDefaultFaultHandler.java,v 1.17 2006/10/18 23:24:3
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
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeCatchAllDef;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.IAeFaultHandler;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;

/**
 * Implementation of CatchAll fault handler.
 */
public class AeDefaultFaultHandler extends AeFCTHandler implements IAeFaultHandler
{
   /** The fault that caused this handler to be executed. */
   private IAeFault mHandledFault;

   /**
    * Accepts the base def and scope as parent.
    * @param aDef
    * @param aParent
    */
   public AeDefaultFaultHandler(AeCatchAllDef aDef, AeActivityScopeImpl aParent)
   {
      super(aDef, aParent);
   }

   /**
    * Protected ctor for use by sub class. This allows the other fault handlers.
    * @param aDef
    * @param aParent
    */
   protected AeDefaultFaultHandler(AeBaseDef aDef, IAeBpelObject aParent)
   {
      super(aDef, aParent);
   }

   /**
    * Returns true since the coordinated activities (invokes) should also be compensated
    * after executing the normal list of CompInfo objects.
    * @return this method returns true
    */
   protected boolean isEnableCoordinatedActivityCompensation()
   {
      return true;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept( IAeImplVisitor aVisitor ) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      getProcess().queueObjectToExecute(getActivity());
   }

   /**
    * notifes the scope that its faultHandler has completed
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFCTHandler#notifyScopeOfCompletion()
    */
   protected void notifyScopeOfCompletion() throws AeBusinessProcessException
   {
      setFault(null);
      AeActivityScopeImpl scope = (AeActivityScopeImpl) getParent();
      scope.faultHandlerComplete(getHandledFault());
   }

   /**
    * notifies the scope that its faultHandler has completed with a fault
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFCTHandler#notifyScopeOfFaultedCompletion(org.activebpel.rt.bpel.IAeFault)
    */
   protected void notifyScopeOfFaultedCompletion(IAeFault aFault) throws AeBusinessProcessException
   {
      AeActivityScopeImpl scope = (AeActivityScopeImpl) getParent();
      scope.faultHandlerCompleteWithFault(aFault);
   }

   /**
    * Getter for the rethrow flag.
    */
   public boolean isAllowedToRethrow()
   {
      // the fault should not be null, null check just in case
      return getFault() == null || getFault().isRethrowable();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeAbstractBpelObject#noMoreChildrenToTerminate()
    */
   protected void noMoreChildrenToTerminate() throws AeBusinessProcessException
   {
      super.noMoreChildrenToTerminate();
      if (getState() == AeBpelState.DEAD_PATH)
      {
         ((AeActivityScopeImpl)getParent()).faultHandlerComplete(null);
      }
   }

   /**
    * Overrides to do nothing. Fault handlers are allowed to complete once they
    * start executing. If their parent scope is terminating, then the termination
    * of the scope will complete once the fault handler is done executing.
    *
    * @see org.activebpel.rt.bpel.impl.AeAbstractBpelObject#terminate()
    */
   public void terminate() throws AeBusinessProcessException
   {
      if (getProcess().isExiting())
      {
         super.terminate();
      }
      else
      {
         setTerminating(true);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultHandler#getHandledFault()
    */
   public IAeFault getHandledFault()
   {
      return mHandledFault;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultHandler#setHandledFault(org.activebpel.rt.bpel.IAeFault)
    */
   public void setHandledFault(IAeFault aHandledFault)
   {
      mHandledFault = aHandledFault;
   }
}
