// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/convert/visitors/AeBPWSToWSBPELOnEventVisitor.java,v 1.3 2007/08/13 17:51:1
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
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.AeVariablesDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnEventDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;

/**
 * This is a visitor used by the BPEL 1.1 -> BPEL 2.0 converter.  It is responsible for adding 
 * a scope child to all onEvent constructs.
 */
public class AeBPWSToWSBPELOnEventVisitor extends AeAbstractBPWSToWSBPELVisitor
{
   /**
    * Constructor.
    */
   public AeBPWSToWSBPELOnEventVisitor()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnEventDef)
    */
   public void visit(AeOnEventDef aDef)
   {
      String variable = aDef.getVariable();
      AeVariableDef varDef = AeDefUtil.getVariableByName(variable, aDef);
      if (varDef != null)
      {
         aDef.setMessageType(varDef.getMessageType());
      }

      AeActivityDef oldChild = aDef.getActivityDef();
      AeActivityScopeDef childScope = null;
      if (!(oldChild instanceof AeActivityScopeDef))
      {
         AeActivityScopeDef newScopeChild = new AeActivityScopeDef();
         newScopeChild.setActivityDef(oldChild);
         newScopeChild.setParent(oldChild.getParent());
         oldChild.setParent(newScopeChild);
         aDef.setActivityDef(newScopeChild);

         childScope = newScopeChild;
      }
      else
      {
         childScope = (AeActivityScopeDef) oldChild;
      }
      
      // Now create the variable and mark it as implicit.
      AeVariableDef newVariable = new AeVariableDef();
      newVariable.setName(variable);
      newVariable.setMessageType(varDef.getMessageType());
      newVariable.setImplicit(true);

      AeVariablesDef variablesDef = childScope.getVariablesDef();
      if (variablesDef == null)
      {
         variablesDef = new AeVariablesDef();
         childScope.setVariablesDef(variablesDef);
      }
      
      variablesDef.addVariableDef(newVariable);

      super.visit(aDef);
   }
}
