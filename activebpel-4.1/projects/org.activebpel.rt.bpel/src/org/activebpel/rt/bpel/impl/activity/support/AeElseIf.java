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
import org.activebpel.rt.bpel.IAeProcessInfoEvent;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.activity.support.AeElseIfDef;
import org.activebpel.rt.bpel.impl.activity.AeActivityIfImpl;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;

/**
 * Implements the 'elseif' child of the 'if' activity.
 */
public class AeElseIf extends AeElse
{
   /**
    * Constructs the else if object with the given def and parent.
    * 
    * @param aDef
    * @param aParent
    */
   public AeElseIf(AeElseIfDef aDef, AeActivityIfImpl aParent)
   {
      super(aDef, aParent);
   }

   /**
    * Returns true if our expression evaluates to true. Always true for otherwise.
    */
   public boolean isEvalTrue() throws AeBusinessProcessException
   {
      AeElseIfDef def = (AeElseIfDef) getDefinition();
      boolean result = executeBooleanExpression(def.getConditionDef());

      // Generate engine info event for debug.
      fireEvalEvent(def, result);

      return result;
   }

   /**
    * Fire the evaluation event.
    * 
    * @param aDef
    * @param aResult
    */
   protected void fireEvalEvent(AeElseIfDef aDef, boolean aResult)
   {
      int eventId = IAeProcessInfoEvent.INFO_ELSE_IF;
      if (IAeBPELConstants.BPWS_NAMESPACE_URI.equals(getProcess().getBPELNamespace()))
         eventId = IAeProcessInfoEvent.INFO_CASE;

      getProcess().getEngine().fireEvaluationEvent(getProcess().getProcessId(),
            aDef.getConditionDef().getExpression(), eventId, getLocationPath(),
            Boolean.toString(aResult));
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept(IAeImplVisitor aVisitor) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }
}
