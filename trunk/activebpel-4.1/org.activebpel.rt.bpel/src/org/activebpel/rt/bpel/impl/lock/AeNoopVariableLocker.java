// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/lock/AeNoopVariableLocker.java,v 1.1 2004/09/02 16:59:0
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
package org.activebpel.rt.bpel.impl.lock;

import java.util.Set;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;

/**
 * Noop variable locker, does nothing and is used when no serializable scopes 
 * are present.
 */
public class AeNoopVariableLocker implements IAeVariableLocker
{
   /**
    * @see org.activebpel.rt.bpel.impl.lock.IAeVariableLocker#addExclusiveLock(java.util.Set, java.lang.String, org.activebpel.rt.bpel.impl.lock.IAeVariableLockCallback)
    */
   public boolean addExclusiveLock(Set aSetOfVariablePaths, String aOwnerXPath, IAeVariableLockCallback aCallback)
   {
      return true;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.lock.IAeVariableLocker#addSharedLock(java.util.Set, java.lang.String, org.activebpel.rt.bpel.impl.lock.IAeVariableLockCallback)
    */
   public boolean addSharedLock(Set aSetOfVariablePaths, String aOwnerXPath, IAeVariableLockCallback aCallback)
   {
      return true;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.lock.IAeVariableLocker#releaseLocks(java.lang.String)
    */
   public void releaseLocks(String aOwner) throws AeBusinessProcessException
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.lock.IAeVariableLocker#getLockerData(org.activebpel.rt.bpel.impl.lock.IAeVariableLockCallback)
    */
   public DocumentFragment getLockerData(IAeVariableLockCallback aProcess) throws AeBusinessProcessException
   {
      return null;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.lock.IAeVariableLocker#setLockerData(org.w3c.dom.Node, org.activebpel.rt.bpel.impl.lock.IAeVariableLockCallback)
    */
   public void setLockerData(Node aNode, IAeVariableLockCallback aProcess) throws AeBusinessProcessException
   {
   }

}
