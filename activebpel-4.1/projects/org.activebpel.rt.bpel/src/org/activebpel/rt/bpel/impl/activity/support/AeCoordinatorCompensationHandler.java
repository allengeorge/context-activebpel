//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeCoordinatorCompensationHandler.java,v 1.6 2006/10/12 20:15:2
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

import java.util.Collections;
import java.util.Iterator;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.coord.IAeCoordinationManager;
import org.activebpel.rt.bpel.coord.IAeCoordinator;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.AePartnerLink;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;

/**
 * Compensation handler for a coordinated invoke activity.
 */
public class AeCoordinatorCompensationHandler extends AeCompensationHandler implements IAeCompensationCallback
{
   /**
    * Coordination id.
    */
   private String mCoordinationId = null;

   /**
    * Default ctor.
    */
   public AeCoordinatorCompensationHandler(AeActivityScopeImpl aParent, String aCoordinationId)
   {
      super(null,aParent);
      mCoordinationId = aCoordinationId;
   }

   /**
    * Returns true if the coordinated activities (invokes) should also be compensated after executing
    * the normal list of CompInfo object.
    * @return false since this is the handler for coordinated activities.
    */
   protected boolean isEnableCoordinatedActivityCompensation()
   {
      // Return false since this is the handler for the coordinated activities.
      return false;
   }

   /**
    * @return Returns the coordinationId.
    */
   public String getCoordinationId()
   {
      return mCoordinationId;
   }

   /**
    * @return the coordinator for this compensator.
    */
   protected IAeCoordinator getCoordinator() throws AeException
   {
      // get the coordination manager.
      IAeCoordinationManager manager = getProcess().getEngine().getCoordinationManager();
      IAeCoordinator coordinator = manager.getCoordinator(getCoordinationId());
      return coordinator;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept(IAeImplVisitor aVisitor ) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }

   /**
    * Returns null since the variable information does not apply for coordination compensation handler.
    * @see org.activebpel.rt.bpel.impl.IAeBpelObject#findVariable(java.lang.String)
    */
   public IAeVariable findVariable(String aName)
   {
      return null;
   }

   /**
    * Returns null since the correlation set does not apply for coordination compensation handler.
    * @see org.activebpel.rt.bpel.impl.IAeBpelObject#findCorrelationSet(java.lang.String)
    */
   public AeCorrelationSet findCorrelationSet(String aName)
   {
      return null;
   }
   
   /**
    * Returns null since the partner link does not apply for coordination compensation handler.
    * @see org.activebpel.rt.bpel.impl.AeAbstractBpelObject#findPartnerLink(java.lang.String)
    */
   public AePartnerLink findPartnerLink(String aName)
   {
      return null;
   }

   /**
    * Called by the scope when its compensation is complete.
    * @param aCompHandler
    */
   public void compensationComplete(AeCompensationHandler aCompHandler) throws AeBusinessProcessException
   {
      // this method is invoked by coordination manager via IAeProcessCoordination when the
      // participant's (subprocess) compensation completed (COMPENSATED message).
      setState(AeBpelState.FINISHED);
      getCallback().compensationComplete(this);

   }

   /**
    * Called by the scope when its compensation was interrupted by a fault.
    * @param aCompHandler
    * @param aFault
    */
   public void compensationCompleteWithFault(AeCompensationHandler aCompHandler, IAeFault aFault) throws AeBusinessProcessException
   {
      // this method is invoked by coordination manager via IAeProcessCoordination when the
      // participant's (subprocess) compensation faulted (FAULT_COMPENSATING message).
      setFaultedState(aFault);
      getCallback().compensationCompleteWithFault(this, aFault);
   }

   /**
    * Callback method that indicates the compensationHandler has been terminated.
    * @param aCompHandler
    */
   public void compensationTerminated(AeCompensationHandler aCompHandler) throws AeBusinessProcessException
   {
      // this method is invoked by coordination manager via IAeProcessCoordination when the
      // participant's (subprocess) compensation handler has been terminated.
      //
      getCallback().compensationTerminated(this);
   }

   /**
    * Overrides method to return true.
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeCompensationCallback#isCoordinated()
    */
   public boolean isCoordinated()
   {
      return true;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeExecutableQueueItem#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      try
      {
         // get the coordinator and call it's compensate method.
         // (by proxy, Coordinator::compensate() invokes the participant's process instance compensation.
        getCoordinator().compensate();
      }
      catch(Exception e)
      {
         AeBusinessProcessException bpe =  new AeBusinessProcessException(e.getMessage(), e);
         throw bpe;
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeBpelObject#getChildrenForStateChange()
    */
   public Iterator getChildrenForStateChange()
   {
      return Collections.EMPTY_SET.iterator();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeExecutableQueueItem#getLocationPath()
    */
   public String getLocationPath()
   {
      IAeBpelObject parent = getParent();
      StringBuffer buffer = new StringBuffer(parent.getLocationPath());
      return buffer.append(AeSupportActivityLocationPathSuffix.COORDINATION_COMPENSATION_HANDLER).toString();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#terminate()
    */
   public void terminate() throws AeBusinessProcessException
   {
      try
      {
         // get the coordinator and ask it to cancel the participating process.
        getCoordinator().cancel();
      }
      catch(Exception e)
      {
         AeBusinessProcessException bpe =  new AeBusinessProcessException(e.getMessage(), e);
         throw bpe;
      }
      super.terminate();
   }

   /**
    * This object gets for a coordinated invoke activity.
    * As such, there is no definition object and calling this method results in an exception.
    * @see org.activebpel.rt.bpel.impl.AeAbstractBpelObject#getDefinition()
    */
   public AeBaseDef getDefinition()
   {
      throw new UnsupportedOperationException(AeMessages.getString("AeCoordinatorCompensationHandler.DEF_NOT_AVAILABLE")); //$NON-NLS-1$
   }

   /**
    * Overrides method to return <code>false</code>, since {@link
    * #getDefinition()} will fail.
    *
    * @see org.activebpel.rt.bpel.impl.IAeBpelObject#hasLocationId()
    */
   public boolean hasLocationId()
   {
      return false;
   }
}
