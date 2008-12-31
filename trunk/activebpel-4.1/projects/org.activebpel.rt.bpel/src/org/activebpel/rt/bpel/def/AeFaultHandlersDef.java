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
 * Container class for fault handlers (catch or catchAll) attached to a scope. 
 */
public class AeFaultHandlersDef extends AeBaseContainer 
   implements IAeCatchParentDef, IAeCompensateParentDef, IAeUncrossableLinkBoundary
{
   /** Default fault handler - <code>catchAll</code> */
   private AeCatchAllDef mCatchAllDef;

   /**
    * Default c'tor.
    */
   public AeFaultHandlersDef()
   {
      super();
   }
   
   /**
    * Adds the fault handler to the collection
    * @param aDef
    */
   public void addCatchDef(AeCatchDef aDef)
   {
      super.add(aDef);
   }

   /**
    * Gets an iterator for the faulthandlers
    */
   public Iterator getCatchDefs()
   {
      return getValues();
   }

   /**
    * Getter for the default fault handler
    */
   public AeCatchAllDef getCatchAllDef()
   {
      return mCatchAllDef;
   }

   /**
    * Setter for the default fault handler
    * @param aDef
    */
   public void setCatchAllDef(AeCatchAllDef aDef)
   {
      mCatchAllDef = aDef;
   }

   /**
    * Tests for existence of a fault handler
    * @return true if there is one or if the default is not null
    */
   public boolean hasCatchOrCatchAll()
   {
      return (getSize() > 0 || getCatchAllDef() != null);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.AeBaseContainer#isEmpty()
    */
   public boolean isEmpty()
   {
      boolean empty = super.isEmpty();
      if (getCatchAllDef() != null)
      {
         empty = false;
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

   /**
    * @see org.activebpel.rt.bpel.def.IAeUncrossableLinkBoundary#canCrossInbound()
    */
   public boolean canCrossInbound()
   {
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeUncrossableLinkBoundary#canCrossOutbound()
    */
   public boolean canCrossOutbound()
   {
      return true;
   }
}
