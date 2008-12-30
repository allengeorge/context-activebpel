// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/AeActivityCompensateDef.java,v 1.8 2006/12/14 22:39:1
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
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeScopeDef;
import org.activebpel.rt.bpel.def.IAeFCTHandlerDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Definition for bpel compensate activity.
 */
public class AeActivityCompensateDef extends AeActivityDef
{
   /**
    * Default constructor
    */
   public AeActivityCompensateDef()
   {
      super();
   }

   /**
    * Walks up the parent hierarchy until it comes across a catch/catchAll or compensationHandler
    * and then it returns that object's enclosing scope.
    */
   public AeScopeDef findRootScopeForCompensation()
   {
      boolean foundFCTDef = false;
      for ( AeBaseDef parent = getParent(); parent != null; parent = parent.getParent())
      {
         // skip over scopes til u find a catch/catchAll or compensation handler
         if (foundFCTDef && parent instanceof AeScopeDef )
         {
            return (AeScopeDef) parent;
         }
         // we found a catch/catchAll
         else if (parent instanceof IAeFCTHandlerDef)
         {
            foundFCTDef = true;
         }
      }

      // should never get here unless the process is invalid, at which point we
      // should have caught it during static analysis.
      return null ;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeActivityDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
