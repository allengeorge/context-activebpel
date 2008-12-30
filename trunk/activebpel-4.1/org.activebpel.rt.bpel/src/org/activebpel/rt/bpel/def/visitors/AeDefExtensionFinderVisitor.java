// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDefExtensionFinderVisitor.java,v 1.2 2007/06/06 20:20:3
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

import org.activebpel.rt.bpel.def.AeBaseDef;

/**
 * This visitor will look for extension elements/attributes.  If any are found
 */
public class AeDefExtensionFinderVisitor extends AeAbstractSearchVisitor
{
   /** Flag indicating whether an extension was found while visiting. */
   private boolean mExtensionFound;
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractSearchVisitor#isFound()
    */
   public boolean isFound()
   {
      return mExtensionFound;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#traverse(org.activebpel.rt.bpel.def.AeBaseDef)
    */
   protected void traverse(AeBaseDef aDef)
   {
      mExtensionFound = aDef.getExtensionAttributeDefs().hasNext() || aDef.getExtensionElementDefs().hasNext();

      super.traverse(aDef);
   }
}
