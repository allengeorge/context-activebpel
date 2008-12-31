// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/AeRecoveryLockManager.java,v 1.1 2005/07/12 00:27:0
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
package org.activebpel.rt.bpel.server.engine.recovery;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.AeManagerAdapter;
import org.activebpel.rt.bpel.impl.IAeLockManager;

/**
 * Implements a lock manager for recovery.
 */
public class AeRecoveryLockManager extends AeManagerAdapter implements IAeLockManager
{
   /** Dummy "do nothing" lock. */
   private final IAeLock mDummyLock = new IAeLock()
   {
      public QName getQName()
      {
         return null;
      }

      public int getLockId()
      {
         return 0;
      }
   };

   /**
    * Constructs a lock manager for recovery.
    */
   public AeRecoveryLockManager()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeLockManager#acquireLock(javax.xml.namespace.QName, int, int)
    */
   public IAeLock acquireLock(QName aQName, int aLockId, int aTimeout) throws AeBusinessProcessException
   {
      return mDummyLock;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeLockManager#releaseLock(org.activebpel.rt.bpel.impl.IAeLockManager.IAeLock)
    */
   public void releaseLock(IAeLock aLock)
   {
   }
}
