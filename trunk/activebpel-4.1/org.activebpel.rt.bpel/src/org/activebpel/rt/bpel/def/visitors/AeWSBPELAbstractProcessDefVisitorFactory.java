//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeWSBPELAbstractProcessDefVisitorFactory.java,v 1.1 2006/09/27 18:23:4
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
package org.activebpel.rt.bpel.def.visitors;

import org.activebpel.rt.bpel.def.validation.AeWSBPELAbstractProcessDefToValidationVisitor;
import org.activebpel.rt.bpel.def.validation.IAeValidationContext;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal;
import org.activebpel.rt.bpel.impl.IAeProcessPlan;

/**
 * Factory for creating def visitors for WS-BPEL 2.0 abstract process defs.
 */
public class AeWSBPELAbstractProcessDefVisitorFactory extends AeWSBPELDefVisitorFactory
{ 
   /**
    * Default ctor.
    */
   public AeWSBPELAbstractProcessDefVisitorFactory()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createDefPathSegmentVisitor()
    */
   public IAeDefPathSegmentVisitor createDefPathSegmentVisitor()
   {
      return new AeWSBPELDefPathSegmentVisitor();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createImplVisitor(org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal, org.activebpel.rt.bpel.impl.IAeBpelObject)
    */
   public IAeDefToImplVisitor createImplVisitor(IAeBusinessProcessInternal aProcess, IAeBpelObject aParent)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createImplVisitor(long, org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal, org.activebpel.rt.bpel.impl.IAeProcessPlan)
    */
   public IAeDefToImplVisitor createImplVisitor(long aPid, IAeBusinessProcessEngineInternal aEngine, IAeProcessPlan aPlan)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createValidationVisitor(org.activebpel.rt.bpel.def.validation.IAeValidationContext)
    */
   public IAeDefVisitor createValidationVisitor(IAeValidationContext aContext)
   {
      return new AeWSBPELAbstractProcessDefToValidationVisitor(aContext);
   }
}
