// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/convert/visitors/AeBPWSToWSBPELOnAlarmVisitor.java,v 1.1 2006/11/04 16:33:0
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
package org.activebpel.rt.bpel.def.convert.visitors;

import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.AeEventHandlersDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef;

/**
 * This is a visitor used by the BPEL 1.1 -> BPEL 2.0 converter.  It is responsible for adding 
 * a scope child to onAlarm constructs within event handlers.
 */
public class AeBPWSToWSBPELOnAlarmVisitor extends AeAbstractBPWSToWSBPELVisitor
{
   /**
    * Constructor.
    */
   public AeBPWSToWSBPELOnAlarmVisitor()
   {
      super();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef)
    */
   public void visit(AeOnAlarmDef aDef)
   {
      if (aDef.getParent() instanceof AeEventHandlersDef)
      {
         AeActivityDef oldChild = aDef.getActivityDef();
         if (!(oldChild instanceof AeActivityScopeDef))
         {
            AeActivityScopeDef newScopeChild = new AeActivityScopeDef();
            newScopeChild.setActivityDef(oldChild);
            newScopeChild.setParent(oldChild.getParent());
            oldChild.setParent(newScopeChild);
            aDef.setActivityDef(newScopeChild);
         }
      }

      super.visit(aDef);
   }
}
