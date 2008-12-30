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

import org.activebpel.rt.bpel.def.AeDocumentationDef;
import org.activebpel.rt.bpel.def.activity.AeActivityForEachDef;

/**
 * Customized traverser that skips over the child scope for parallel forEach
 * activities. These definitions will be visited during the activity's execution
 * or during its state restoration.
 */
public class AeImplementationTraverser extends AeDefTraverser
{
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityForEachDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityForEachDef aDef, IAeDefVisitor aVisitor)
   {
      if (!aDef.isParallel())
      {
         super.traverse(aDef, aVisitor);
      }
      else
      {
         traverseSourceAndTargetLinks(aDef, aVisitor);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeDocumentationDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeDocumentationDef aDef, IAeDefVisitor aVisitor)
   {
   }
}
