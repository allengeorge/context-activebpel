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

import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Models the <code>to</code> element in a copy operation. Broke this out as its
 * own class since we want to visit it and to avoid any confusion with the <code>from</code>
 * portion of the copy.
 */
public class AeToDef extends AeVarDef
{
   /**
    * Default constructor
    */
   public AeToDef()
   {
      super();
   }

   /**
    * Returns true if this to is empty.  This is used during validation, since the to portion
    * of an assign's copy child should never be empty.
    */
   public boolean isEmpty()
   {
      return AeUtil.isNullOrEmpty(getVariable()) && AeUtil.isNullOrEmpty(getPartnerLink());
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeQueryDef#getBpelNamespace()
    */
   public String getBpelNamespace()
   {
      return AeDefUtil.getProcessDef(this).getNamespace();
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
