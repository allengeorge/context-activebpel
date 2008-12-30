// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/AeNotUnderstoodExtensionActivityDef.java,v 1.1 2007/09/12 02:48:1
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



import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * When the I/O layer reads an extensionActivity, the child must be an Activity.  If the I/O layer
 * does not know how to read that child, a AeUnknownActivityExtensionDef is created.
 */
public class AeNotUnderstoodExtensionActivityDef extends AeAbstractExtensionActivityDef
{
   /**
    * Default c'tor.
    */
   public AeNotUnderstoodExtensionActivityDef()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeExtensionActivityDef#isUnderstood()
    */
   public boolean isUnderstood()
   {
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
