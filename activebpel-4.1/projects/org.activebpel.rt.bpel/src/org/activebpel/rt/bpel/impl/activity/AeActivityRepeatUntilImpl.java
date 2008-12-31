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
import org.activebpel.rt.bpel.IAeProcessInfoEvent;
import org.activebpel.rt.bpel.def.activity.AeActivityRepeatUntilDef;
import org.activebpel.rt.bpel.def.activity.support.AeConditionDef;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;

/**
 * Implementation of the bpel repeatUntil activity.
 */
public class AeActivityRepeatUntilImpl extends AeActivityWhileImpl
{
   /** Flag indicating if this is the first iteration. */
   private boolean mFirstIteration = true;

   /**
    * Constructs the repeatUntil impl object.
    *
    * @param aActivityDef
    * @param aParent
    */
   public AeActivityRepeatUntilImpl(AeActivityRepeatUntilDef aActivityDef, IAeActivityParent aParent)
   {
      super(aActivityDef, aParent);
   }


   /**
    * @see org.activebpel.rt.bpel.impl.activity.AeActivityWhileImpl#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      if (mFirstIteration)
      {
         mFirstIteration = false;

         // Queue the activity to execute
         getChild().setState(AeBpelState.INACTIVE);
         getProcess().queueObjectToExecute(getChild());
      }
      else
      {
         initAlarmIterations();

         AeConditionDef conditionDef = ((AeActivityRepeatUntilDef) getDefinition()).getConditionDef();

         boolean isConditionTrue = executeBooleanExpression(conditionDef);

         // Generate engine info event for debug.
         getProcess().getEngine().fireEvaluationEvent(getProcess().getProcessId(),
               conditionDef.getExpression(), IAeProcessInfoEvent.INFO_REPEAT_UNTIL, getLocationPath(),
               Boolean.toString(isConditionTrue));

         if(isConditionTrue)
         {
            // condition is true so we are done
            objectCompleted();
         }
         else
         {
            // queue the activity to execute
            getChild().setState(AeBpelState.INACTIVE);
            getProcess().queueObjectToExecute(getChild());
         }
      }
   }

   /**
    * @return Returns the firstIteration.
    */
   public boolean isFirstIteration()
   {
      return mFirstIteration;
   }

   /**
    * @param aFirstIteration The firstIteration to set.
    */
   public void setFirstIteration(boolean aFirstIteration)
   {
      mFirstIteration = aFirstIteration;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept(IAeImplVisitor aVisitor) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }
}
