//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeLoopActivity.java,v 1.5 2006/06/26 16:50:3
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
import org.activebpel.rt.bpel.IAeActivity;
import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.support.IAeLoopActivity;

/**
 * Base class for the while and forEach activities. 
 */
public abstract class AeLoopActivity extends AeActivityImpl implements IAeLoopActivity
{
   /** Serves to tell us why we're terminating, either because of a break or a continue */
   private int mEarlyTerminationReason = IAeLoopActivity.REASON_NONE;
   
   /** Child activity */
   private IAeActivity mChild;
   
   /**
    * @param aActivityDef
    * @param aParent
    */
   public AeLoopActivity(AeActivityDef aActivityDef, IAeActivityParent aParent)
   {
      super(aActivityDef, aParent);
   }
   
   /**
    * Getter for the child
    */
   protected IAeActivity getChild()
   {
      return mChild;
   }
   
   /**
    * Setter for the child
    * @param aChild
    */
   protected void setChild(IAeActivity aChild)
   {
      mChild = aChild;
   }

   /**
    * Handles a normal child completion by re-executing itself, which will reschedule
    * the child activity if the while condition is still true. Any other kind of
    * child complete (like that from a dead path) is ignored.
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#childComplete(org.activebpel.rt.bpel.impl.IAeBpelObject)
    */
   public void childComplete(IAeBpelObject aChild) throws AeBusinessProcessException
   {
      int flag = getEarlyTerminationReason();
      clearEarlyTerminationReason();
      
      if (flag == IAeLoopActivity.REASON_BREAK)
      {
         objectCompleted();
      }
      else
      {
         handleLoopCompletion(aChild);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeLoopActivity#onBreak(org.activebpel.rt.bpel.impl.activity.IAeLoopControl)
    */
   public void onBreak(IAeLoopControl aControl) throws AeBusinessProcessException
   {
      onLoopTermination(IAeLoopActivity.REASON_BREAK);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeLoopActivity#onContinue(org.activebpel.rt.bpel.impl.activity.IAeLoopControl)
    */
   public void onContinue(IAeLoopControl aControl) throws AeBusinessProcessException
   {
      onLoopTermination(IAeLoopActivity.REASON_CONTINUE);
   }

   /**
    * Sets the termination flag and begins terminating the enclosed activities.
    * When complete, the while will either complete normally or evaluate its
    * expression again and possibly continue looping.
    * 
    * @param aControlFlag
    */
   protected void onLoopTermination(int aControlFlag) throws AeBusinessProcessException
   {
      setEarlyTerminationReason(aControlFlag);
      getChild().terminateEarly();
   }

   /**
    * Clears the early termination flag
    */
   protected void clearEarlyTerminationReason()
   {
      setEarlyTerminationReason(IAeLoopActivity.REASON_NONE);
   }

   /**
    * @return Returns the control flag
    */
   public int getEarlyTerminationReason()
   {
      return mEarlyTerminationReason;
   }

   /**
    * @param aReason The reason for early termination
    */
   public void setEarlyTerminationReason(int aReason)
   {
      mEarlyTerminationReason = aReason;
   }

   /**
    * Called from childComplete() and as a result of a continue activity executing.
    * @param aChild
    * @throws AeBusinessProcessException
    */
   protected abstract void handleLoopCompletion(IAeBpelObject aChild) throws AeBusinessProcessException;
}
 
