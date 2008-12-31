// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeWSBPELOnAlarmValidator.java,v 1.4 2006/12/14 22:45:2
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
package org.activebpel.rt.bpel.def.validation.activity;

import java.util.List;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.AeEventHandlersDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef;
import org.activebpel.rt.bpel.def.validation.activity.scope.AeRepeatEveryValidator;
import org.activebpel.rt.bpel.def.validation.expressions.AeForValidator;
import org.activebpel.rt.bpel.def.validation.expressions.AeUntilValidator;
import org.activebpel.rt.bpel.def.validation.expressions.IAeExpressionModelValidator;

/**
 * Extends the base class in order to provide some slightly altered validation logic for
 * WS-BPEL 2.0 compatible OnAlarms.
 */
public class AeWSBPELOnAlarmValidator extends AeOnAlarmValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeWSBPELOnAlarmValidator(AeOnAlarmDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Check for required Scope child if this onAlarm is defined within an Event Handler.
    * 
    * @see org.activebpel.rt.bpel.def.validation.activity.AeOnAlarmValidator#validate()
    */
   public void validate()
   {
      if ( getDef().getParent() instanceof AeEventHandlersDef )
      {
         // onAlarm activities within an event handler requires a child Scope.         
         AeActivityScopeValidator scope = (AeActivityScopeValidator) getChild(AeActivityScopeValidator.class);
         if ( scope == null )
         {
            getReporter().addError( ERROR_REQUIRES_SCOPE_CHILD, new String[] { getDefinition().getLocationPath() },
                                    getDefinition() );
         }
      }
      
      super.validate();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.AeOnAlarmValidator#validateAlarmChildren()
    */
   protected void validateAlarmChildren()
   {
      List list = getChildren(IAeExpressionModelValidator.class);
      // Two children, the first child must be a for or an until, the 2nd child must be a repeatEvery
      if (list.size() == 2)
      {
         IAeExpressionModelValidator child1 = (IAeExpressionModelValidator) list.get(0);
         IAeExpressionModelValidator child2 = (IAeExpressionModelValidator) list.get(1);
         if (!(child1 instanceof AeForValidator) && !(child1 instanceof AeUntilValidator))
         {
            getReporter().addError(AeMessages.getString("AeWSBPEL20OnAlarmModel.InvalidChild1Error"), null, //$NON-NLS-1$
                  getDefinition());
         }
         else
         {
            validateAlarmChild(child1);
         }
         
         if (!(child2 instanceof AeRepeatEveryValidator))
         {
            getReporter().addError(AeMessages.getString("AeWSBPEL20OnAlarmModel.InvalidChild2Error"), null, //$NON-NLS-1$
                  getDefinition());
         }
         else
         {
            validateAlarmChild(child2);
         }
      }
      else
      {
         super.validateAlarmChildren();
      }
   }
}
