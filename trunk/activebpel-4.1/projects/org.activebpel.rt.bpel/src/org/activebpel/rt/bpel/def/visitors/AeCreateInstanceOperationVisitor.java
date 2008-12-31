//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeCreateInstanceOperationVisitor.java,v 1.3 2006/12/22 12:57:5
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
import java.util.Map;

import org.activebpel.rt.bpel.def.activity.AeActivityPickDef;
import org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef;
import org.activebpel.rt.bpel.def.activity.IAeReceiveActivityDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnEventDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef;

/**
 * Walks the process def looking to see if the createInstance operation is 
 * used by more than one IMA. If not, then an inbound receive routing optimization
 * is possible since we'll never route an inbound receive on this operation 
 * since it should always result in a new process instance.
 * 
 * This visitor should only be run on processes with a single start activity.
 */
public class AeCreateInstanceOperationVisitor extends AeAbstractDefVisitor
{
   /** maps the plink and operation to a count of the number of IMA's using the plink/op */
   private Map mOperationCount = new HashMap();
   /** key for the create instance IMA in the above map */
   private String mCreateInstanceKey;
   
   /**
    * ctor 
    */
   public AeCreateInstanceOperationVisitor()
   {
      setTraversalVisitor( new AeTraversalVisitor( new AeDefTraverser(), this ) );
   }
   
   /**
    * Returns true if the create instance operation isn't used by any other IMA's
    */
   public boolean isCreateInstanceOnly()
   {
      Map map = getOperationCount();
      Integer i = (Integer) map.get(getCreateInstanceKey());
      return i.intValue() == 1;
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef)
    */
   public void visit(AeActivityReceiveDef aDef)
   {
      super.visit(aDef);
      recordIMA(aDef, aDef.isCreateInstance());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef)
    */
   public void visit(AeOnMessageDef aDef)
   {
      super.visit(aDef);
      boolean createInstance = false;
      if (aDef.getParent() instanceof AeActivityPickDef)
      {
         createInstance = ((AeActivityPickDef)aDef.getParent()).isCreateInstance();
      }
      recordIMA(aDef, createInstance);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnEventDef)
    */
   public void visit(AeOnEventDef aDef)
   {
      super.visit(aDef);
      // pass false since an onEvent can never be a createInstance
      recordIMA(aDef, false);
   }
   
   /**
    * Records the IMA.
    * @param aDef
    * @param aCreateInstance
    */
   protected void recordIMA(IAeReceiveActivityDef aDef, boolean aCreateInstance)
   {
      String plink = aDef.getPartnerLink();
      String op = aDef.getOperation();
      String key = plink + "." + op; //$NON-NLS-1$
      
      if (aCreateInstance)
      {
         setCreateInstanceKey(key);
      }
      
      Map map = getOperationCount();
      Integer i = (Integer) map.get(key);
      if (i == null)
      {
         i = new Integer(1);
      }
      else
      {
         i = new Integer(i.intValue() + 1);
      }
      map.put(key, i);
   }

   /**
    * @return Returns the operationCount.
    */
   protected Map getOperationCount()
   {
      return mOperationCount;
   }

   /**
    * @return Returns the createInstanceKey.
    */
   protected String getCreateInstanceKey()
   {
      return mCreateInstanceKey;
   }

   /**
    * @param aCreateInstanceKey The createInstanceKey to set.
    */
   protected void setCreateInstanceKey(String aCreateInstanceKey)
   {
      mCreateInstanceKey = aCreateInstanceKey;
   }
}
 
