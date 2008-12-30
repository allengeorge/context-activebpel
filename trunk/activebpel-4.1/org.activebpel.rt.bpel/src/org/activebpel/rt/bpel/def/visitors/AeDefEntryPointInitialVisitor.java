// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDefEntryPointInitialVisitor.java,v 1.8 2006/06/26 16:50:4
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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.activebpel.rt.bpel.def.activity.AeActivityPickDef;
import org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef;
import org.activebpel.rt.bpel.def.activity.IAeReceiveActivityDef;


/**
 * Builds a list of keys (partnerLinkName:PortQName:operationName)
 * for initial entry points into the BPEL process.
 * <br />
 * Specifically - it looks for all receive and pick activities
 * with a createInstance attribute whose value is set to true.
 * <br />
 * If the activity is a pick activity, all of its onMessage
 * children are added to the list. 
 */
public class AeDefEntryPointInitialVisitor extends AeAbstractEntryPointVisitor
{
   /** number of individual activities that are marked as create instances */
   private int mCount = 0;
   
   /** list of keys for initial entry points into process */
   private Set mCreateInstance = new HashSet();

   /**
    * Default constructor.
    */
   public AeDefEntryPointInitialVisitor()
   {
   }

   /**
    * Accessor for key list.
    * @return list of keys for initial entry points into the process
    */
   public Collection getCreateInstanceCollection()
   {
      return mCreateInstance;
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractEntryPointVisitor#accept(org.activebpel.rt.bpel.def.activity.AeActivityPickDef)
    */
   protected boolean accept(AeActivityPickDef aDef)
   {
      boolean isCreateInstance = super.accept(aDef);
      if (isCreateInstance)
         mCount++;
      return isCreateInstance;
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractEntryPointVisitor#accept(org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef)
    */
   protected boolean accept(AeActivityReceiveDef aDef)
   {
      boolean isCreateInstance = super.accept(aDef);
      if (isCreateInstance)
         mCount++;
      return isCreateInstance;
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractEntryPointVisitor#processEntryPoint(org.activebpel.rt.bpel.def.activity.IAeReceiveActivityDef)
    */
   protected void processEntryPoint(IAeReceiveActivityDef aDef)
   {
      mCreateInstance.add(aDef.getPartnerLinkOperationKey());
   }

   /**
    * Returns the number of unique activities that can create an instance. May differ
    * from getCreateInstanceList().size() since this method only counts pick's once
    * instead of counting them for each onMessage occurence. 
    */
   public int getCreateInstanceActivityCount()
   {
      return mCount;
   }
}
