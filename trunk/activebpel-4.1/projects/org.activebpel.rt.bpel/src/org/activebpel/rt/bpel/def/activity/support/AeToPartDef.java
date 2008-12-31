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
package org.activebpel.rt.bpel.def.activity.support;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Models the invoke|reply activity's 'toPart' bpel construct introduced in WS-BPEL 2.0.
 */
public class AeToPartDef extends AeBaseDef
{
   /** The toPart's 'part' attribute. */
   private String mPart;
   /** The toPart's 'fromVariable' attribute. */
   private String mFromVariable;

   /**
    * Default c'tor.
    */
   public AeToPartDef()
   {
      super();
   }

   /**
    * @return Returns the fromVariable.
    */
   public String getFromVariable()
   {
      return mFromVariable;
   }

   /**
    * @param aFromVariable The fromVariable to set.
    */
   public void setFromVariable(String aFromVariable)
   {
      mFromVariable = aFromVariable;
   }

   /**
    * @return Returns the part.
    */
   public String getPart()
   {
      return mPart;
   }

   /**
    * @param aPart The part to set.
    */
   public void setPart(String aPart)
   {
      mPart = aPart;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}