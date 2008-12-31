// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/lock/IAeVariableLocker.java,v 1.4 2007/01/27 14:42:5
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
 * Describes a facility for locking access to variables in order to support the
 * concept of serializable scopes. Serializable scopes require that the variables
 * used within the scope (and its fault handlers) do not change by forces outside
 * of the scope while the scope is executing.
 */
public interface IAeVariableLocker
{
   // TODO (MF) rename this interface to IAeResourceLocker since it's not just variables anymore with WS-BPEL
   /**
    * Returns true if all of the variables in the set were able to be
    * locked. If one or more were already locked by a previous call then the
    * method will return false and the callback instance we be notified when
    * the variables have been locked.
    * @param aSetOfVariablePaths The location paths of the variables you want to lock
    * @param aOwnerXPath The location path of the caller
    * @param aCallback The callback object that gets notified when the lock is
    *                  acquired if it's not immediately available
    */
   public boolean addExclusiveLock(Set aSetOfVariablePaths, String aOwnerXPath, IAeVariableLockCallback aCallback);

   /**
    * Returns true if all of the variables in the set were able to be
    * locked. Shared locks will be denied only if an exclusive lock is present
    * on the variable being requested.
    * @param aSetOfVariablePaths The location paths of the variables you want to lock
    * @param aOwnerXPath The location path of the caller
    * @param aCallback The callback object that gets notified when the lock is
    *                  acquired if it's not immediately available
    */
   public boolean addSharedLock(Set aSetOfVariablePaths, String aOwnerXPath, IAeVariableLockCallback aCallback);

   /**
    * This unlocks all of the variables locked by the owner and will possibly
    * result in notifying one or more of the waiting callbacks.
    * @param aOwner - the owner of the lock
    */
   public void releaseLocks(String aOwner) throws AeBusinessProcessException;
   
   /**
    * Persists the lock data into a document fragment.
    * @param aProcess The process we are getting locker data for,
    * @return Document Fragment representing lock data.
    */
   public DocumentFragment getLockerData(IAeVariableLockCallback aProcess) throws AeBusinessProcessException;

   /**
    * Restores the lock data from a node.
    * @param aNode Node containing lock data
    * @param aProcess the process the lock data is associated with
    */
   public void setLockerData(Node aNode, IAeVariableLockCallback aProcess) throws AeBusinessProcessException;
}
