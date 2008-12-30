// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDefCorrelatedReceiveVisitor.java,v 1.9 2006/09/27 00:36:2
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.activebpel.rt.bpel.def.AeCorrelationCombinations;
import org.activebpel.rt.bpel.def.AeCorrelationSetDef;
import org.activebpel.rt.bpel.def.AePartnerLinkOpKey;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef;
import org.activebpel.rt.bpel.def.activity.IAeReceiveActivityDef;
import org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnEventDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;

/**
 * This visitor walks the process def collecting information about correlated receives.
 * It is possible to have multiple activities receiving data for the same partner link,
 * operation but with different correlation sets.
 *
 * In order to ensure that the engine routes the inbound receive to the correct
 * activity, we need to know about all of the possible correlation sets in play
 * for the given partner link and operation. The matching algorithm will pass
 * in the partner link and operation being invoked and get back a collection
 * of properties to match against. This collection will be in descending order
 * by size since we want to attempt to match against the correlation sets with
 * the most specific information - this being determined by the number of properties
 * within the set.
 *
 */
public class AeDefCorrelatedReceiveVisitor extends AeAbstractDefVisitor
{
   /** The process def we're visiting */
   private AeProcessDef mProcessDef;
   /** an internal map that uses the concat of the partner link and operation as its
    *  and maps to a Set of correlation sets. */
   private Map mPartnerLinkOperationToCorrSets = new HashMap();

   /**
    * Constructs the visitor with the given WSDL provider.
    */
   public AeDefCorrelatedReceiveVisitor()
   {
      setTraversalVisitor(new AeTraversalVisitor(new AeDefTraverser(), this));
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      mProcessDef = aDef;
      aDef.accept(new AeJoinVisitor());
      super.visit(aDef);

      // walk the map and set all of the plink/operation sets on the def
      for (Iterator iter = mPartnerLinkOperationToCorrSets.entrySet().iterator(); iter.hasNext();)
      {
         Map.Entry entry = (Map.Entry) iter.next();
         AePartnerLinkOpKey key = (AePartnerLinkOpKey) entry.getKey();
         AeCorrelationCombinations combos = (AeCorrelationCombinations) entry.getValue();
         mProcessDef.setCorrelationProperties(key, combos);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef)
    */
   public void visit(AeActivityReceiveDef aDef)
   {
      addCorrProps(aDef, aDef.getCorrelationList());
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef)
    */
   public void visit(AeOnMessageDef aDef)
   {
      addCorrProps(aDef, aDef.getCorrelationDefs());
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnEventDef)
    */
   public void visit(AeOnEventDef aDef)
   {
      visit((AeOnMessageDef) aDef);
   }

   /**
    * Adds all of the correlation properties used for the given def object
    *
    * @param aDef
    * @param aCorrelationIterator
    */
   protected void addCorrProps(IAeReceiveActivityDef aDef, Iterator aCorrelationIterator)
   {
      Set corrSets = null;
      for (Iterator it = aCorrelationIterator; it.hasNext();)
      {
         AeCorrelationDef def = (AeCorrelationDef) it.next();
         AeCorrelationSetDef corrSetDef = AeDefUtil.findCorrSetByName(def.getCorrelationSetName(), aDef.getContext());

         // if the correlation is initiate="no" or if it's a "join" style correlation, then we should include the properties
         if (!def.isInitiate() || corrSetDef.isJoinStyle())
         {
            if (corrSets == null)
               corrSets = new HashSet();

            corrSets.add(corrSetDef);
      }
      }

      if (corrSets != null)
      {
         AePartnerLinkOpKey key = aDef.getPartnerLinkOperationKey();
         AeCorrelationCombinations combos = (AeCorrelationCombinations) mPartnerLinkOperationToCorrSets.get(key);
         if (combos == null)
         {
            combos = new AeCorrelationCombinations();
            mPartnerLinkOperationToCorrSets.put(key, combos);
         }
         combos.add(corrSets);
      }
   }
}
