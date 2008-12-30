//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/copy/AeSelectionFailureStrategy.java,v 1.4 2006/09/07 15:06:2
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

import java.util.List;

import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.AeSelectionFailureException;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyStrategy;

/**
 * This strategy is used whenever a bpel:selectionFailure is guaranteed, such as when multiple nodes
 * are selected.
 */
public class AeSelectionFailureStrategy implements IAeCopyStrategy
{
   /**
    * Reports bpel:selectionFailure.
    * 
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeCopyStrategy#copy(org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation, java.lang.Object, java.lang.Object)
    */
   public void copy(IAeCopyOperation aCopyOperation, Object aFromData, Object aToData) throws AeBpelException
   {
      // The problem might be with the from data OR the to data.
      int selectionCount = 0;
      if (isMultiSelection(aFromData))
         selectionCount = getSelectionCount(aFromData);
      else if (isMultiSelection(aToData))
         selectionCount = getSelectionCount(aToData);
      throw new AeSelectionFailureException(aCopyOperation.getContext().getBPELNamespace(), selectionCount);
   }
   
   /**
    * Returns true if the given data is a sized object (List or NodeList) with size greater than 1.
    * 
    * @param aData
    */
   protected boolean isMultiSelection(Object aData)
   {
      if (aData instanceof List)
      {
         return ((List) aData).size() > 1;
      }
      else
      {
         return false;
      }
   }
   
   /**
    * Returns the selection count of the given sized object (either a List or a NodeList).
    * 
    * @param aData
    */
   protected int getSelectionCount(Object aData)
   {
      if (aData instanceof List)
      {
         return ((List) aData).size();
      }
      else
      {
         return 0;
      }
   }
}
 
