//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/to/AeToQueryRunner.java,v 1.3 2006/12/14 22:59:3
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
package org.activebpel.rt.bpel.impl.activity.assign.to; 

import java.util.List;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.AeFaultFactory;
import org.activebpel.rt.bpel.impl.AeSelectionFailureException;
import org.activebpel.rt.bpel.impl.activity.assign.AeCreateXPathUtil;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Helper class that selects a node from an element. This element will either be a message part
 * or an element variable. This behavior exists in BPEL 1.1 only since the 2.0 spec dropped the 
 * syntax for a query on a <to> in favor of using the new expression syntax. 
 */
public class AeToQueryRunner
{
   /**
    * Selects a node from an element within a message part or element variable using a query.
    * If the query fails and createXPath is turned on then we'll create the nodes required to
    * fulfill the query.
    * 
    * @param aCopyOperation
    * @param aQuery
    * @param targetDocElement
    * @throws AeBusinessProcessException
    */
   public static Node selectValue(IAeCopyOperation aCopyOperation, String aQuery, Element targetDocElement) throws AeBpelException
   {
      try
      {
         Node targetNode = null;
         
         // if we have a document element then search for the path
         if (targetDocElement != null)
         {
            // Locate the proper node from the query
            Object selection = aCopyOperation.getContext().executeQuery(aQuery, targetDocElement);
            if (selection instanceof List && ((List) selection).size() == 1)
               targetNode = (Node) ((List) selection).get(0);
            else if (selection instanceof List && ((List) selection).size() > 1)
               throw new AeSelectionFailureException(aCopyOperation.getContext().getBPELNamespace(),((List) selection).size());
         }
   
         // if we didn't find the node and the config says create then create it
         // TODO (EPW) can't assume XPath 1.0 for BPEL 2.0
         if (targetNode == null && aCopyOperation.getContext().isCreateXPathAllowed())
            targetNode = AeCreateXPathUtil.findOrCreateXPath(aQuery, targetDocElement.getOwnerDocument(), aCopyOperation.getContext(), aCopyOperation.getContext());
         return targetNode;
      }
      catch(AeBpelException e)
      {
         throw e;
      }
      catch(AeBusinessProcessException e)
      {
         throw new AeBpelException(e.getMessage(), AeFaultFactory.getSystemErrorFault(e));
      }
   }
}
 
