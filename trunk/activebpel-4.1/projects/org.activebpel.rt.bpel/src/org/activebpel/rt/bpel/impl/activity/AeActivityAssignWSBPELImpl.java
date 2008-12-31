//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeActivityAssignWSBPELImpl.java,v 1.5 2006/11/13 17:06:1
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
package org.activebpel.rt.bpel.impl.activity; 

import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.AeActivityAssignDef;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.activity.assign.AeAtomicCopyOperationContext;

/**
 * Assign impl for 2.0. Extends the base class by adding support for the validate attribute
 * as well as an additional hook for locking variables referenced by extension operations. 
 */
public class AeActivityAssignWSBPELImpl extends AeActivityAssignImpl
{
   /**
    * Ctor accepts the def and parent
    * @param aAssign
    * @param aParent
    */
   public AeActivityAssignWSBPELImpl(AeActivityAssignDef aAssign, IAeActivityParent aParent)
   {
      super(aAssign, aParent);
   }

   /**
    * Looks for optional validate flag and validates all of the variables assigned to after the 
    * copy operations have completed without an error.
    * 
    * @see org.activebpel.rt.bpel.impl.activity.AeActivityAssignImpl#executeOperations()
    */
   protected void executeOperations() throws AeBusinessProcessException
   {
      super.executeOperations();
      
      // if we got here, then there were no errors
      if (getDef().isValidate())
      {
         validate();
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.AeActivityImpl#acquireResourceLocks()
    */
   protected boolean acquireResourceLocks()
   {
      // fixme extensibleAssign: need to walk the copy operations to lock any variables used by extensible operations  
      return super.acquireResourceLocks();
   }

   /**
    * Validates all of the variables that have been modified by the assign's operations.
    */
   protected void validate() throws AeBpelException
   {
      // TODO (MF) change to validate all of the variables and then report all failures at once
      Map rollbackMap = ((AeAtomicCopyOperationContext)getCopyOperationContext()).getRollbackMap();
      for(Iterator it = rollbackMap.keySet().iterator(); it.hasNext();)
      {
         IAeVariable var = (IAeVariable) it.next();
         var.validate();
      }
   }
   
   /**
    * Getter for the assign def.
    */
   protected AeActivityAssignDef getDef()
   {
      return (AeActivityAssignDef) getDefinition();
   }
}
 
