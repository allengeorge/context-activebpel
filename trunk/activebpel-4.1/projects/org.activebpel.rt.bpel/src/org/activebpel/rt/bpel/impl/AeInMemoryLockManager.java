// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeInMemoryLockManager.java,v 1.9 2005/04/15 13:27:0
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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.activebpel.rt.bpel.AeBusinessProcessException;

/**
 * This class implements a simple in-memory process def lock manager.
 */
public class AeInMemoryLockManager extends AeAbstractLockManager
{
   /** Contains all currently acquired locks. */
   private Set mLocks;

   /**
    * Constructs a new in-memory lock manager.
    * 
    * @param aConfig The configuration map for this manager.
    */
   public AeInMemoryLockManager(Map aConfig)
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#create()
    */
   public void create() throws Exception
   {
      super.create();
      
      mLocks = new HashSet();
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#stop()
    */
   public void stop()
   {
      mLocks.clear();
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.AeAbstractLockManager#acquireLockInternal(org.activebpel.rt.bpel.impl.IAeLockManager.IAeLock)
    */
   protected boolean acquireLockInternal(IAeLock aLock)
         throws AeBusinessProcessException
   {
      synchronized (mLocks)
      {
         if (!mLocks.contains(aLock))
         {
            mLocks.add(aLock);
            return true;
         }
      }
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeAbstractLockManager#releaseLockInternal(org.activebpel.rt.bpel.impl.IAeLockManager.IAeLock)
    */
   protected void releaseLockInternal(IAeLock aLock)
   {
      synchronized (mLocks)
      {
         mLocks.remove(aLock);
      }
   }
}

