//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/copy/AeEmptySelectionStrategy.java,v 1.1 2006/09/07 15:06:2
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
package org.activebpel.rt.bpel.impl.activity.assign.copy; 

import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.AeSelectionFailureException;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyStrategy;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;

/**
 * Selecting a null value in a <from> typically results in a bpel:selectionFailure.
 * This class is used to report that error UNLESS the emptyQuerySelection config
 * is enabled at which point it removes the target node.
 */
public class AeEmptySelectionStrategy implements IAeCopyStrategy
{
   /**
    * Either removes the target node if emptyQuerySelection is allowed or reports bpel:selectionFailure
    * 
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeCopyStrategy#copy(org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation, java.lang.Object, java.lang.Object)
    */
   public void copy(IAeCopyOperation aCopyOperation, Object aFromData, Object aToData) throws AeBpelException
   {
      boolean removed = false;
      if (aCopyOperation.getContext().isEmptyQuerySelectionAllowed())
      {
         if (aToData instanceof Attr)
         {
            Attr attr = (Attr) aToData;
            attr.getOwnerElement().removeAttributeNode(attr);
            removed = true;
         }
         else if (aToData instanceof Node)
         {
            Node node = (Node) aToData;
            node.getParentNode().removeChild(node);
            removed = true;
         }
      }
      
      if (!removed)
         throw new AeSelectionFailureException(aCopyOperation.getContext().getBPELNamespace(), 0);
   }
}
 
