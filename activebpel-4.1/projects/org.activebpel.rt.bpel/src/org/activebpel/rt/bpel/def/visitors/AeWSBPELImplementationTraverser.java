//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeWSBPELImplementationTraverser.java,v 1.7 2006/11/06 23:35:4
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

import org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef;

/**
 * WS-BPEL 2.0 traverser
 */
public class AeWSBPELImplementationTraverser extends AeImplementationTraverser
{
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeOnAlarmDef aDef, IAeDefVisitor aVisitor)
   {
      if (aDef.getRepeatEveryDef() == null)
      {
         super.traverse(aDef, aVisitor);
      }
      else
      {
         traverseDocumentationDef(aDef, aVisitor);
         traverseForAndUntilDefs(aDef, aVisitor);
         callAccept(aDef.getRepeatEveryDef(), aVisitor);
         // avoid traversing the scope def since we defer the creation of the
         // scope impl until the alarm fires.
         // callAccept(aDef.getActivityDef(), aVisitor);
         traverseExtensionDefs(aDef, aVisitor);
      }
   }
}
 
