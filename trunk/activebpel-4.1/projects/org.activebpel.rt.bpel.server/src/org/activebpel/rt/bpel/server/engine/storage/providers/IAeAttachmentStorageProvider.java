// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/providers/IAeAttachmentStorageProvider.java,v 1.4 2007/07/26 21:10:1
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
package org.activebpel.rt.bpel.server.engine.storage.providers;

import java.io.InputStream;
import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;

/**
 * An attachment queue storage delegate. This interface defines methods that the delegating attachment storage will call in
 * order to store/read date in the underlying database.
 */
public interface IAeAttachmentStorageProvider extends IAeStorageProvider
{
   /**
    * Stores an attachment item (headers and content) in the database and returns the stored attachment id.
    *
    * @param aAttachmentGroupId
    * @param aInputStream
    * @param aHeaders
    * @return attachment item token number
    * @throws AeStorageException
    */
   public long storeAttachment(long aAttachmentGroupId, InputStream aInputStream, Map aHeaders) throws AeStorageException;
   
   /**
    * Loads an attachment's headers from the database.
    * 
    * @param aAttachmentId attachment id
    * @return Map of header name/value pairs
    * @throws AeStorageException
    */
   public Map getHeaders(long aAttachmentId) throws AeStorageException;
   
   /**
    * Returns a new attachment group id.
    */
   public long createAttachmentGroup() throws AeStorageException;
   
   /**
    * Associates an existing attachment group with a process in the database.
    * @param aAttachmentGroupId the attachment group id to be associated with the process
    * @param aProcessId the active process instance id
    */
   public void associateProcess(long aAttachmentGroupId, long aProcessId) throws AeStorageException;
   
   /**
    * Retrieves the binary content of an attachment.
    * 
    * @param aAttachmentId the attachment id
    * @return the binary stream
    * @throws AeStorageException
    */
   public InputStream getContent(long aAttachmentId) throws AeStorageException;
   
   /**
    * Cleanup unassociated attachments.
    * 
    * @throws AeStorageException
    */
   public void cleanup() throws AeStorageException;
   
   /**
    * Removes an attachment.
    * 
    * @param aAttachmentId the attachment id
    * @throws AeStorageException
    */
   public void removeAttachment(long aAttachmentId) throws AeStorageException;
}
