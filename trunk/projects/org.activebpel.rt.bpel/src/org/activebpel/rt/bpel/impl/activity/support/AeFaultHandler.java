// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeFaultHandler.java,v 1.11 2006/09/22 19:52:3
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
package org.activebpel.rt.bpel.impl.activity.support;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.AeCatchDef;
import org.activebpel.rt.bpel.def.faults.IAeCatch;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Models the <code>catch</code> and <code>catchAll</code> providing a place to
 * store the child activity for the fault.
 */
public class AeFaultHandler extends AeDefaultFaultHandler implements IAeCatch
{
   /**
    * Ctor for creating <code>catch</code> implementation.
    * @param aDef
    * @param aParent
    */
   public AeFaultHandler(AeCatchDef aDef, AeActivityScopeImpl aParent)
   {
      super(aDef, aParent);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept(IAeImplVisitor aVisitor ) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }   
   
   /**
    * Returns true since the coordinated activities (invokes) should also be compensated 
    * after executing the normal list of CompInfo objects. 
    * @return true for explicit compensation handler.
    */
   protected boolean isEnableCoordinatedActivityCompensation()
   {
      // return true since the coordinated invokes should be compensated after running
      // the fault handler
      return true;
   }   
   
   /**
    * Convenience getter for the fault name, delegates to the def object.
    */
   public QName getFaultName()
   {
      AeCatchDef def = (AeCatchDef) getDefinition();
      return def.getFaultName(); 
   }
   
   /**
    * Gets the variable for this catch block or null if there is not fault variable
    * defined here.
    */
   public IAeVariable getFaultVariable()
   {
      IAeVariable variable = null;
      AeCatchDef def = (AeCatchDef) getDefinition();
      String variableName = def.getFaultVariable();
      if (!AeUtil.isNullOrEmpty(variableName))
      {
         variable = findVariable(variableName);
      }
      return variable;
   }

   /**
    * Getter for the definition
    */
   public AeCatchDef getDef()
   {
      return (AeCatchDef) getDefinition();
   }

   /**
    * @see org.activebpel.rt.bpel.def.faults.IAeCatch#getFaultElementName()
    */
   public QName getFaultElementName()
   {
      return null;
   }

   /**
    * @see org.activebpel.rt.bpel.def.faults.IAeCatch#getFaultMessageType()
    */
   public QName getFaultMessageType()
   {
      if (getFaultVariable() != null)
      {
         IAeVariable variable = getFaultVariable();
         return variable.getMessageType();
      }
      return null;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.faults.IAeCatch#hasFaultVariable()
    */
   public boolean hasFaultVariable()
   {
      return getFaultVariable() != null;
   }
   
   

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeDefaultFaultHandler#setHandledFault(org.activebpel.rt.bpel.IAeFault)
    */
   public void setHandledFault(IAeFault aHandledFault)
   {
      super.setHandledFault(aHandledFault);
      if (getFaultVariable() != null)
      {
         getFaultVariable().setMessageData(aHandledFault.getMessageData());
      }
   }
}
