// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/convert/visitors/AeBPWSToWSBPELCorrelationInitiateVisitor.java,v 1.3 2006/09/27 00:36:2
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
package org.activebpel.rt.bpel.def.convert.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.def.AeCorrelationSetDef;
import org.activebpel.rt.bpel.def.AeCorrelationsDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.IAeCorrelationsParentDef;
import org.activebpel.rt.bpel.def.activity.IAeReceiveActivityDef;
import org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.visitors.AeAbstractEntryPointVisitor;

/**
 * This is a visitor used by the BPEL 1.1 -> BPEL 2.0 converter.  It is responsible for changing
 * the value of the 'initiate' flag of multi-start activities from 'yes' to 'join'.
 */
public class AeBPWSToWSBPELCorrelationInitiateVisitor extends AeAbstractEntryPointVisitor
{
   /** A map of create instance activities.  The key is a correlation set def, the value is a list of correlation defs from create instance activities. */
   private Map mCreateInstances = new HashMap();

   /**
    * Constructor.
    */
   public AeBPWSToWSBPELCorrelationInitiateVisitor()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      super.visit(aDef);
      
      processCorrelationMap();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractEntryPointVisitor#processEntryPoint(org.activebpel.rt.bpel.def.activity.IAeReceiveActivityDef)
    */
   protected void processEntryPoint(IAeReceiveActivityDef aDef)
   {
      IAeCorrelationsParentDef def = (IAeCorrelationsParentDef) aDef;
      AeCorrelationsDef correlations = def.getCorrelationsDef();
      if (correlations != null)
      {
         for (Iterator iter = correlations.getValues(); iter.hasNext(); )
         {
            AeCorrelationDef corrDef = (AeCorrelationDef) iter.next();
            if (AeCorrelationDef.INITIATE_YES.equals(corrDef.getInitiate()))
            {
               AeCorrelationSetDef corrSetDef = AeDefUtil.findCorrSetByName(corrDef.getCorrelationSetName(), aDef.getContext());
               addCorrelationMapping(corrSetDef, corrDef);
            }
         }
      }
   }
   
   /**
    * Adds a mapping from correlation set def to correlation def.
    * 
    * @param aCorrelationSetDef
    * @param aCorrelationDef
    */
   protected void addCorrelationMapping(AeCorrelationSetDef aCorrelationSetDef, AeCorrelationDef aCorrelationDef)
   {
      List list = (List) getCreateInstances().get(aCorrelationSetDef);
      if (list == null)
      {
         list = new ArrayList();
         getCreateInstances().put(aCorrelationSetDef, list);
      }
      list.add(aCorrelationDef);
   }

   /**
    * Process the map of correlation sets to correlation defs.  Each entry in the map
    * will be a map from a correlation set def to a list of correlation defs.  If the
    * list has more than one correlation def, then all of the items in that list must
    * be changed to JOIN from YES (only YES correlation defs will be in the list).
    */
   protected void processCorrelationMap()
   {
      for (Iterator iter = getCreateInstances().values().iterator(); iter.hasNext(); )
      {
         List corrDefs = (List) iter.next();
         if (corrDefs.size() > 1)
         {
            for (Iterator corrDefIter = corrDefs.iterator(); corrDefIter.hasNext(); )
            {
               AeCorrelationDef corrDef = (AeCorrelationDef) corrDefIter.next();
               corrDef.setInitiate(AeCorrelationDef.INITIATE_JOIN);
            }
         }
      }
   }

   /**
    * @return Returns the createInstances.
    */
   public Map getCreateInstances()
   {
      return mCreateInstances;
   }
}
