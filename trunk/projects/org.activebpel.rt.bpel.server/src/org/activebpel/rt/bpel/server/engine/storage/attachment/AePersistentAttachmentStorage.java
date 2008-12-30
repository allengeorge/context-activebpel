// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/attachment/AePersistentAttachmentStorage.java,v 1.3 2007/07/26 21:10:1
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
package org.activebpel.rt.bpel.server.engine.storage.attachment;

import java.io.InputStream;
import java.util.Map;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeProcessPlan;
import org.activebpel.rt.bpel.impl.attachment.IAeAttachmentStorage;
import org.activebpel.rt.bpel.server.engine.storage.AeAbstractStorage;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeAttachmentStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider;

/**
 * Generic attachment storage.
 */
public class AePersistentAttachmentStorage extends AeAbstractStorage implements IAeAttachmentStorage
{
   /**
    * Constructor
    * @param aProvider
    */
   public AePersistentAttachmentStorage(IAeStorageProvider aProvider)
   {
      super(aProvider);
   }

   /**
    * Convenience method to return the provider cast to a IAeAttachmentStorageProvider.
    */
   protected IAeAttachmentStorageProvider getAttachmentStorageProvider()
   {
      return (IAeAttachmentStorageProvider)getProvider();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.attachment.IAeAttachmentStorage#associateProcess(long, long)
    */
   public void associateProcess(long aAttachmentGroupId, long aProcessId) throws AeStorageException
   {
      getAttachmentStorageProvider().associateProcess(aAttachmentGroupId, aProcessId);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.attachment.IAeAttachmentStorage#createAttachmentGroup(org.activebpel.rt.bpel.impl.IAeProcessPlan)
    */
   public long createAttachmentGroup(IAeProcessPlan aPlan) throws AeStorageException
   {
      return getAttachmentStorageProvider().createAttachmentGroup();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.attachment.IAeAttachmentStorage#storeAttachment(long, java.io.InputStream, java.util.Map)
    */
   public long storeAttachment(long aAttachmentGroupId, InputStream aInputStream, Map aHeaders) throws AeStorageException
   {
      return getAttachmentStorageProvider().storeAttachment(aAttachmentGroupId, aInputStream, aHeaders);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.attachment.IAeAttachmentStorage#getHeaders(long)
    */
   public Map getHeaders(long aAttachmentId) throws AeStorageException
   {
      return getAttachmentStorageProvider().getHeaders(aAttachmentId);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.attachment.IAeAttachmentStorage#getContent(long)
    */
   public InputStream getContent(long aAttachmentId) throws AeStorageException
   {
      return getAttachmentStorageProvider().getContent(aAttachmentId);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.attachment.IAeAttachmentStorage#cleanup()
    */
   public void cleanup() throws AeStorageException
   {
      getAttachmentStorageProvider().cleanup();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.attachment.IAeAttachmentStorage#removeAttachment(long)
    */
   public void removeAttachment(long aAttachmentId) throws AeBusinessProcessException
   {
      getAttachmentStorageProvider().removeAttachment(aAttachmentId);
      
   }
}
