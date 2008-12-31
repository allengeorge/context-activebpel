//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/AeActivitySuspendDef.java,v 1.2 2006/09/25 01:34:3
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
package org.activebpel.rt.bpel.def.activity; 

import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Suspend activity suspends the process and passes the optional variable to the
 * notification layer. 
 */
public class AeActivitySuspendDef extends AeActivityDef implements IAeExtensionActivityDef
{
   /** name of the optional variable */
   private String mVariable;

   /**
    * No-arg ctor
    */
   public AeActivitySuspendDef()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeExtensionActivityDef#isUnderstood()
    */
   public boolean isUnderstood()
   {
      return true;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeExtensionActivityDef#getNamespace()
    */
   public String getNamespace()
   {
      return IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
   
   /**
    * @return Returns the variable.
    */
   public String getVariable()
   {
      return mVariable;
   }
   
   /**
    * @param aVariable The variable to set.
    */
   public void setVariable(String aVariable)
   {
      mVariable = aVariable;
   }
}
 
