// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDefOnEventVariableTypeVisitor.java,v 1.3 2006/09/22 19:52:3
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

import javax.wsdl.Message;
import javax.wsdl.Part;

import org.activebpel.rt.bpel.AeWSDLDefHelper;
import org.activebpel.rt.bpel.IAeContextWSDLProvider;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromPartDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnEventDef;

/**
 * Assigns variable types to implicit variables created for <code>onEvent</code>
 * <code>fromPart</code> variable references by
 * {@link org.activebpel.rt.bpel.def.visitors.AeWSBPELImplicitVariableVisitor}.
 */
public class AeDefOnEventVariableTypeVisitor extends AeAbstractDefVisitor
{
   /** The WSDL provider set during visitor creation. */
   private final IAeContextWSDLProvider mWSDLProvider;

   /**
    * Constructs the visitor with the given WSDL provider.
    *
    * @param aWSDLProvider
    */
   public AeDefOnEventVariableTypeVisitor(IAeContextWSDLProvider aWSDLProvider)
   {
      mWSDLProvider = aWSDLProvider;

      setTraversalVisitor(new AeTraversalVisitor(new AeDefTraverser(), this));
   }

   /**
    * Returns the WSDL provider.
    */
   protected IAeContextWSDLProvider getWSDLProvider()
   {
      return mWSDLProvider;
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnEventDef)
    */
   public void visit(AeOnEventDef aDef)
   {
      if (aDef.getFromPartsDef() != null)
      {
         AeActivityScopeDef scopeDef = aDef.getChildScope();
         if (scopeDef != null)
         {
            Message message = AeWSDLDefHelper.getInputMessage(getWSDLProvider(), aDef.getPortType(), aDef.getOperation());
            if (message != null)
            {
               for (Iterator i = aDef.getFromPartDefs(); i.hasNext(); )
               {
                  AeFromPartDef fromPartDef = (AeFromPartDef) i.next();
                  String variableName = fromPartDef.getToVariable();
                  String partName = fromPartDef.getPart();

                  if ((variableName != null) && (partName != null))
                  {
                     AeVariableDef variableDef = scopeDef.getVariableDef(variableName);
                     Part part = message.getPart(partName);
   
                     if ((variableDef != null) && (part != null))
                     {
                        variableDef.setElement(part.getElementName());
                        variableDef.setType(part.getTypeName());
                     }
                  }
               }
            }
         }
      }

      super.visit(aDef);
   }
}
