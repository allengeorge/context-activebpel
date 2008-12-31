// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeLockManager.java,v 1.4 2004/12/30 22:28:1
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
package org.activebpel.rt.bpel.impl;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;

/**
 * A generic lock manager that provides a means of acquiring and releasing locks
 * based on a unique resource name and hash value.
 */
public interface IAeLockManager extends IAeManager
{
   /**
    * Called when a lock needs to be acquired. Should always be used in conjunction
    * with the releaseLock method in a finally block.
    * 
    * @param aQName Qualified name of the key
    * @param aLockId id of the value we're trying to lock (should be unique w/in the qname provided)
    * @param aTimeout max number of seconds to wait to acquire lock
    * @return IAeLock The lock that was acquired, should be used in the call to release lock.
    * @throws AeBusinessProcessException
    */
   public IAeLock acquireLock(QName aQName, int aLockId, int aTimeout) throws AeBusinessProcessException;

   /**
    * Called to release a lock that it had previously acquired for a qname and hash value.
    * 
    * @param aLock 
    */
   public void releaseLock(IAeLock aLock);
   
   /**
    * Value returned from the acquire lock call. 
    */
   public interface IAeLock 
   {
      /**
       * Gets the qname of the lock that was acquired.
       */
      public QName getQName();
      
      /**
       * Gets the value of the hashcode of the lock that was acquired
       */
      public int getLockId();
   }
}
