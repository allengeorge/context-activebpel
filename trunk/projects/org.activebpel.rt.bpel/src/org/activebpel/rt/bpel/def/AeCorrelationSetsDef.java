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

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Container for <code>correlationSets</code>. 
 */
public class AeCorrelationSetsDef extends AeBaseContainer
{
   /**
    * Default c'tor.
    */
   public AeCorrelationSetsDef()
   {
      super();
   }

   /**
    * Adds a correlation set to the collection.
    * @param aCorrelationSetDef
    */
   public void addCorrelationSetDef(AeCorrelationSetDef aCorrelationSetDef)
   {
      add(aCorrelationSetDef.getName(), aCorrelationSetDef);
   }

   /**
    * Gets the correlation set by name
    * @param aName
    */
   public AeCorrelationSetDef getCorrelationSetDef(String aName)
   {
      return (AeCorrelationSetDef) super.get(aName);
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);      
   }
}
