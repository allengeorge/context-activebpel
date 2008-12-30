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
package org.activebpel.rt.bpel.def;

import java.util.Iterator;

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;


/**
 * Models the <code>variables</code> element from BPEL.  
 */
public class AeVariablesDef extends AeBaseContainer
{
   /**
    * Default c'tor.
    */
   public AeVariablesDef()
   {
      super();
   }

   /**
    * Add a variable
    * @param aVariableDef
    */
   public void addVariableDef(AeVariableDef aVariableDef)
   {
      add(aVariableDef.getName(), aVariableDef);
   }
   
   /**
    * Gets a variable by name.
    * @param aName
    */
   public AeVariableDef getVariableDef(String aName)
   {
      return (AeVariableDef) get(aName);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.AeBaseContainer#isEmpty()
    */
   public boolean isEmpty()
   {
      boolean empty = super.isEmpty();
      if (!empty)
      {
         boolean foundExplicit = false;
         // we might still be empty if the container only has dynamic variables
         for (Iterator it = getValues(); it.hasNext(); )
         {
            AeVariableDef def = (AeVariableDef) it.next();
            if (!def.isImplicit())
            {
               foundExplicit = true;
               break;
            }
         }
         
         // TODO (MF) should use a different means of testing for explicit only

         // if the variable container only had implicit variables then we consider
         // it empty in terms of writing xml since these defs will get recreated
         // during the read.
         empty = !foundExplicit;
      }
      return empty;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);      
   }
}
