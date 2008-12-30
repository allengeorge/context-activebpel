//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeWSBPELImplicitVariableVisitor.java,v 1.3 2006/09/22 19:52:3
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

import java.util.Iterator;

import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromPartDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnEventDef;
import org.activebpel.rt.util.AeUtil;

/**
 * Adds the implicit variable declaration where needed in 2.0
 */
public class AeWSBPELImplicitVariableVisitor extends AeImplicitVariableVisitor
{
   /**
    * protected ctor to force usage of visitor factory 
    */
   protected AeWSBPELImplicitVariableVisitor()
   {
      super();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnEventDef)
    */
   public void visit(AeOnEventDef aDef)
   {
      AeActivityScopeDef scopeDef = aDef.getChildScope();
      if (scopeDef != null)
      {
         String varName = aDef.getVariable();
         if (AeUtil.notNullOrEmpty(varName))
         {
            AeVariableDef varDef = addVariableToScope(varName, scopeDef);
            if (varDef != null)
            {
               if (aDef.getElement() != null)
               {
                  varDef.setElement(aDef.getElement());
               }
               
               if (aDef.getMessageType() != null)
               {
                  varDef.setMessageType(aDef.getMessageType());
               }
            }
         }
         else if (aDef.getFromPartsDef() != null)
         {
            // Create untyped implicit variables for the fromPart variables. The
            // variable types will be added later by a separate visitor that has
            // access to a WSDL provider (see AeDefOnEventVariableTypeVisitor,
            // which is called by AeProcessDef's preProcessForExecution()).
            for (Iterator i = aDef.getFromPartDefs(); i.hasNext(); )
            {
               AeFromPartDef fromPartDef = (AeFromPartDef) i.next();
               varName = fromPartDef.getToVariable();
               addVariableToScope(varName, scopeDef);
            }
         }
      }
      super.visit(aDef);
   }
}
 
