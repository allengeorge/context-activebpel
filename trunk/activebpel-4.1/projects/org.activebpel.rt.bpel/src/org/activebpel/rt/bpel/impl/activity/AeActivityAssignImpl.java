// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeActivityAssignImpl.java,v 1.60 2006/07/17 21:58:4
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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeProcessInfoEvent;
import org.activebpel.rt.bpel.def.activity.AeActivityAssignDef;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.AeFaultFactory;
import org.activebpel.rt.bpel.impl.AeProcessInfoEvent;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.activity.assign.AeAtomicCopyOperationContext;
import org.activebpel.rt.bpel.impl.activity.assign.IAeAssignOperation;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperationContext;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;

/**
 * Implementation of the BPEL assign activity.
 */
public class AeActivityAssignImpl extends AeActivityImpl
{
   /** list of copy operations to get executed */
   private List mCopyOperations = new LinkedList();
   
   /** Copy operation context used by assign activity */
   private IAeCopyOperationContext mCopyOperationContext;
   
   /**
    * Ctor accepts the def and parent
    * 
    * @param aAssign
    * @param aParent
    */
   public AeActivityAssignImpl(AeActivityAssignDef aAssign, IAeActivityParent aParent)
   {
      super(aAssign, aParent);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeAbstractBpelObject#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept(IAeImplVisitor aVisitor) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }
   
   /**
    * Returns a copy operation context for the assign activity.
    */
   public IAeCopyOperationContext getCopyOperationContext()
   {
      if (mCopyOperationContext == null)
         mCopyOperationContext = new AeAtomicCopyOperationContext(this);
      
      return mCopyOperationContext;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeExecutableQueueItem#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      boolean success = false;
      AeAtomicCopyOperationContext copyContext = (AeAtomicCopyOperationContext)getCopyOperationContext();
      
      try
      {
         executeOperations();
         copyContext.clearRollback();
         success = true;
      }
      catch(Throwable t)
      {
         // Restore data to the initial state and signal a fault
         copyContext.rollback();
         if (t instanceof AeBpelException)
         {
            objectCompletedWithFault(((AeBpelException)t).getFault());
         }
         else
         {
            objectCompletedWithFault(AeFaultFactory.getSystemErrorFault(t));
         }
      }
      
      if (success)
         objectCompleted();
   }

   /**
    * Executes all of the copy operations as well as any extensible operations
    * in the order that they appeared in the def. If there are any errors during
    * the execution then we'll throw and the assign will rollback any modified 
    * variables.
    * 
    * @throws AeBusinessProcessException
    */
   protected void executeOperations() throws AeBusinessProcessException
   {
      int index = 0;
      try
      {
         for (Iterator iter = getCopyOperations().iterator(); iter.hasNext(); index++)
         {
            IAeAssignOperation operation = (IAeAssignOperation) iter.next();
            operation.execute();
         }
      }
      catch(Throwable t)
      {
         // Log info error message to give user clue as to which operation failed.
         // Note we are sending the index of the copy operation which is translated in the msg formatter
         AeProcessInfoEvent evt = new AeProcessInfoEvent(getProcess().getProcessId(),
                                                 getLocationPath(),
                                                 IAeProcessInfoEvent.ERROR_ASSIGN_ACTIVITY,
                                                 "", //$NON-NLS-1$
                                                 Integer.toString(index));
         getProcess().getEngine().fireInfoEvent(evt);
         
         if (t instanceof AeBusinessProcessException)
            throw (AeBusinessProcessException)t;
         else
            throw new AeBusinessProcessException(t.getMessage(), t);
      }
   }
   
   /**
    * Adds the copy operation to our list
    * 
    * @param aCopyOperation
    */
   public void addCopyOperation(IAeAssignOperation aCopyOperation)
   {
      getCopyOperations().add(aCopyOperation);
   }

   /**
    * @return Returns the copyOperations.
    */
   protected List getCopyOperations()
   {
      return mCopyOperations;
   }

   /**
    * @param aCopyOperations The copyOperations to set.
    */
   protected void setCopyOperations(List aCopyOperations)
   {
      mCopyOperations = aCopyOperations;
   }
}
