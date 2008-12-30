//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/AeWSBPELAbstractProcessDefToValidationVisitor.java,v 1.3 2006/11/03 22:48:0
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

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.activity.AeActivityOpaqueDef;
import org.activebpel.rt.bpel.def.validation.process.AeWSBPELAbstractProcessProcessValidator;

/**
 * WSBPEL 2.0 Abstract process validation visitor.O
 */
public class AeWSBPELAbstractProcessDefToValidationVisitor extends AeWSBPELDefToValidationVisitor
{

   /**
    * Ctor
    * @param aContext
    */
   public AeWSBPELAbstractProcessDefToValidationVisitor(IAeValidationContext aContext)
   {
      super(aContext);
   }

   /**
    * Initializes def to model map.
    */
   protected void initMap()
   {
      super.initMap();
      getDefToValidatorMap().put(AeProcessDef.class, AeWSBPELAbstractProcessProcessValidator.class);
      // todo (PJ) add Def.class-to-ValidatorImpl mappings such that:
      // 1) createInstance receive activity is optional
      // 2) if/while/repeat condition can have value of  ##opaque (or empty).
      // 3) plink and operation is optional i.e. attr value can be ##opaque or empty in receives/invoke/onmessage
   }

   /**
    * Overrides method to avoid traversing.
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#traverse(org.activebpel.rt.bpel.def.AeBaseDef)
    */
   protected void traverse(AeBaseDef aDef)
   {
      super.traverse(aDef);
   }

   /**
    * Overrides method to ignore validation.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityOpaqueDef)
    */
   public void visit(AeActivityOpaqueDef aDef)
   {
      // no need to validate opaque activities.
      super.traverse(aDef);
   }
}
