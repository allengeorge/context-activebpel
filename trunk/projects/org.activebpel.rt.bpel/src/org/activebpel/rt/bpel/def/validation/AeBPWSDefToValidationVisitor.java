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
package org.activebpel.rt.bpel.def.validation;

import org.activebpel.rt.bpel.def.AeCatchDef;
import org.activebpel.rt.bpel.def.AeExtensionAttributeDef;
import org.activebpel.rt.bpel.def.AeFaultHandlersDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.activity.AeActivityIfDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.activity.AeActivityThrowDef;
import org.activebpel.rt.bpel.def.activity.support.AeElseDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnEventDef;
import org.activebpel.rt.bpel.def.io.IAeBpelLegacyConstants;
import org.activebpel.rt.bpel.def.io.ext.AeExtensionElementDef;
import org.activebpel.rt.bpel.def.validation.activity.AeActivityIfValidator;
import org.activebpel.rt.bpel.def.validation.activity.AeBPWSActivityScopeValidator;
import org.activebpel.rt.bpel.def.validation.activity.AeBPWSActivityThrowValidator;
import org.activebpel.rt.bpel.def.validation.activity.AeBPWSOnEventValidator;
import org.activebpel.rt.bpel.def.validation.activity.decision.AeElseValidator;
import org.activebpel.rt.bpel.def.validation.activity.scope.AeBPWSCatchValidator;
import org.activebpel.rt.bpel.def.validation.activity.scope.AeBPWSFaultHandlersValidator;
import org.activebpel.rt.bpel.def.validation.extensions.AeBPWSExtensionAttributeValidator;
import org.activebpel.rt.bpel.def.validation.extensions.AeBPWSExtensionElementValidator;
import org.activebpel.rt.bpel.def.validation.process.AeBPWSProcessValidator;

/**
 *  BPEL4WS validation visitor
 */
public class AeBPWSDefToValidationVisitor extends AeDefToValidationVisitor
{
   /**
    * Ctor
    * @param aContext
    */
   public AeBPWSDefToValidationVisitor(IAeValidationContext aContext)
   {
      super(aContext);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeDefToValidationVisitor#initMap()
    */
   protected void initMap()
   {
      super.initMap();
      getDefToValidatorMap().put(AeProcessDef.class, AeBPWSProcessValidator.class);
      getDefToValidatorMap().put(AeOnEventDef.class, AeBPWSOnEventValidator.class);
      getDefToValidatorMap().put(AeCatchDef.class, AeBPWSCatchValidator.class);
      getDefToValidatorMap().put(AeActivityThrowDef.class, AeBPWSActivityThrowValidator.class);
      getDefToValidatorMap().put(AeFaultHandlersDef.class, AeBPWSFaultHandlersValidator.class);
      getDefToValidatorMap().put(AeActivityScopeDef.class, AeBPWSActivityScopeValidator.class);
      getDefToValidatorMap().put(AeExtensionAttributeDef.class, AeBPWSExtensionAttributeValidator.class);
      getDefToValidatorMap().put(AeExtensionElementDef.class, AeBPWSExtensionElementValidator.class);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeElseDef)
    */
   public void visit(AeElseDef aDef)
   {
      AeElseValidator elseModel = new AeElseValidator(aDef);
      elseModel.setTagName(IAeBpelLegacyConstants.TAG_OTHERWISE);
      traverse(aDef, elseModel);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityIfDef)
    */
   public void visit(AeActivityIfDef aDef)
   {
      AeActivityIfValidator ifModel = new AeActivityIfValidator(aDef);
      ifModel.setMissingConditionError(IAeValidationDefs.SWITCH_MISSING_CASE);
      traverse(aDef, ifModel);
   }
}
