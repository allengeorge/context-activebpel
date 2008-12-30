// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/AePersistentAttachmentManager.java,v 1.5 2007/05/24 01:06:1
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
package org.activebpel.rt.bpel.server.engine.storage;

import java.util.Map;

import org.activebpel.rt.bpel.impl.AeFileAttachmentManager;
import org.activebpel.rt.bpel.impl.attachment.IAeAttachmentStorage;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.storage.attachment.AeCompositeAttachmentStorage;

/**
 * Implements a persistent attachment manager.
 */
public class AePersistentAttachmentManager extends AeFileAttachmentManager
{
   /** The composite storage object. */
   private IAeAttachmentStorage mCompositeStorage;

   /** The default persistent storage object. */
   private IAeAttachmentStorage mPersistentStorage;

   /**
    * Constructs the attachment manager with the given engine configuration.
    * @param aConfig The engine configuration for this manager.
    */
   public AePersistentAttachmentManager(Map aConfig)
   {
      super(aConfig);
   }

   /**
    * Returns the persistent (database) storage implementation.
    */
   public IAeAttachmentStorage getPersistentStorage() throws AeStorageException
   {
      if (mPersistentStorage == null)
      {
         mPersistentStorage = AePersistentStoreFactory.getInstance().getAttachmentStorage();

         if (mPersistentStorage == null)
         {
            throw new AeStorageException(AeMessages.getString("AePersistentAttachmentManager.ERROR_GettingStorage")); //$NON-NLS-1$ 
         }
      }

      return mPersistentStorage;
   }

   /**
    * Overrides method to return the composite storage implementation.
    * 
    * @see org.activebpel.rt.bpel.impl.AeAbstractAttachmentManager#getStorage()
    */
   public IAeAttachmentStorage getStorage() throws AeStorageException
   {
      if (mCompositeStorage == null)
      {
         mCompositeStorage = new AeCompositeAttachmentStorage(getPersistentStorage(), getFileStorage());
      }
      
      return mCompositeStorage;
   }
}
