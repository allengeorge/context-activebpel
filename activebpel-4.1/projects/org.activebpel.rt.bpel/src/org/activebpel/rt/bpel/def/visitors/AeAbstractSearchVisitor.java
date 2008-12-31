//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeAbstractSearchVisitor.java,v 1.2 2007/06/06 20:20:3
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
 * Base class for visitors that are searching for a particular def and want to
 * stop traversing after its found. 
 */
public abstract class AeAbstractSearchVisitor extends AeAbstractDefVisitor
{
   /**
    * No arg ctor 
    */
   public AeAbstractSearchVisitor()
   {
      setTraversalVisitor(new AeTraversalVisitor(new AeTraverseWhileNotFound(), this)); 
   }
   
   /**
    * Return true to stop searching
    */
   public abstract boolean isFound();

   /**
    * keeps traversing the def until we find what we're looking for.
    */
   protected class AeTraverseWhileNotFound extends AeDefTraverser
   {
      /**
       * @see org.activebpel.rt.bpel.def.visitors.AeDefTraverser#callAccept(org.activebpel.rt.bpel.def.AeBaseDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
       */
      protected void callAccept(AeBaseDef aDef, IAeDefVisitor aVisitor)
      {
         if (!isFound())
            super.callAccept(aDef, aVisitor);
      }
   }
}
 
