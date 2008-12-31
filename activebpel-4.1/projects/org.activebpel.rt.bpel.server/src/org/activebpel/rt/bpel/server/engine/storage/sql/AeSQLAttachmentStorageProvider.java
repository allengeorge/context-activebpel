// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeSQLAttachmentStorageProvider.java,v 1.7 2007/07/26 21:10:1
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
package org.activebpel.rt.bpel.server.engine.storage.sql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.storage.AeCounter;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.attachment.AeAttachmentItemEntry;
import org.activebpel.rt.bpel.server.engine.storage.attachment.AePairSerializer;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeAttachmentStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.sql.handlers.AeAttachmentItemResultSetHandler;
import org.activebpel.rt.util.AeBlobInputStream;
import org.activebpel.rt.util.AeCloser;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * This is a SQL Attachment Storage provider (an implementation of IAeQueueStorageDelegate).
 */
public class AeSQLAttachmentStorageProvider extends AeAbstractSQLStorageProvider implements
      IAeAttachmentStorageProvider
{
   /** The SQL statement prefix for all SQL statements used in this class. */
   public static final String SQLSTATEMENT_PREFIX = "AttachmentStorage."; //$NON-NLS-1$

   /** The SQL statement key for the 'InsertAttachmentGroup lock' statement. */
   protected static final String SQL_ATTACHMENT_GROUP = "InsertAttachmentGroup"; //$NON-NLS-1$

   /** The SQL statement key for the 'AttachProcess' statement. */
   protected static final String SQL_PROCESS_ATTACHMENT_GROUP = "AttachProcess"; //$NON-NLS-1$

   /** The SQL statement key for the 'InsertAttachment' statement. */
   protected static final String SQL_STORE_ATTACHMENT = "InsertAttachment"; //$NON-NLS-1$

   /** The SQL statement key for quering the attachment contents 'QueryAttachmentContents' statement. */
   protected static final String SELECT_ATTACHMENT_CONTENTS = "QueryAttachmentContents"; //$NON-NLS-1$

   /** The SQL statement key for quering the attachment item 'QueryAttachmentHeaders' statement. */
   protected static final String SELECT_ATTACHMENT_HEADERS = "QueryAttachmentHeaders"; //$NON-NLS-1$

   /** The SQL statement key for the 'CleanupAttachments' statement. */
   protected static final String SQL_CLEANUP_ATTACHMENTS = "CleanupAttachments"; //$NON-NLS-1$
   
   /** The SQL statement key for the 'removeAttachment' statement. */
   protected static final String SQL_REMOVE_ATTACHMENT = "RemoveAttachment"; //$NON-NLS-1$


   /** Result set handler for attachment items. */
   protected static final ResultSetHandler sAttachmentItemHandler = new AeAttachmentItemResultSetHandler();

   /**
    * Constructor.
    *
    * @param aConfig
    */
   public AeSQLAttachmentStorageProvider(AeSQLConfig aConfig)
   {
      super(SQLSTATEMENT_PREFIX, aConfig);
   }

   /**
    * Returns next available attachment group id.
    * @throws AeStorageException
    * @throws SQLException
    */
   protected long getNextAttachmentGroupId() throws AeStorageException
   {
      return AeCounter.ATTACHMENT_GROUP_ID_COUNTER.getNextValue();
   }

   /**
    * Returns next available attachment item id.
    * @throws AeStorageException
    * @throws SQLException
    */
   protected long getNextAttachmentItemId() throws AeStorageException
   {
      return AeCounter.ATTACHMENT_ITEM_ID_COUNTER.getNextValue();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeAttachmentStorageProvider#associateProcess(long, long)
    */
   public void associateProcess(long aAttachmentGroupId, long aProcessId) throws AeStorageException
   {
      Connection connection = getTransactionConnection();
      try
      {
         Object[] params = new Object[] { new Long(aProcessId), new Long(aAttachmentGroupId) };
         update(connection, SQL_PROCESS_ATTACHMENT_GROUP, params);
      }
      finally
      {
         AeCloser.close(connection);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeAttachmentStorageProvider#createAttachmentGroup()
    */
   public long createAttachmentGroup() throws AeStorageException
   {
      long attachmentGroupId = getNextAttachmentGroupId();

      Object[] params = new Object[] { new Long(attachmentGroupId) };
      update(SQL_ATTACHMENT_GROUP, params);
      return attachmentGroupId;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeAttachmentStorageProvider#storeAttachment(long, java.io.InputStream, java.util.Map)
    */
   public long storeAttachment(long aAttachmentGroupId, InputStream aInputStream, Map aHeaders) throws AeStorageException
   {
      try
      {
         long attachmentId = getNextAttachmentItemId();
         InputStream content;
         if (aInputStream instanceof AeBlobInputStream)
         {
            content = aInputStream;
         }
         else
         {
            content = new AeBlobInputStream(aInputStream);
         }

         try
         {
            Object[] params = new Object[]
            {
               new Long(aAttachmentGroupId),
               new Long(attachmentId),
               (aHeaders != null) ? (Object)AePairSerializer.serialize(aHeaders) : AeQueryRunner.NULL_CLOB,
               content
            };

            update(SQL_STORE_ATTACHMENT, params);
         }
         finally
         {
            AeCloser.close(content); // remove temp file
         }

         return attachmentId;
      }
      catch (AeStorageException e)
      {
         throw e;
      }
      catch (Exception ex)
      {
         throw new AeStorageException(ex);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeAttachmentStorageProvider#getHeaders(long)
    */
   public Map getHeaders(long aAttachmentId) throws AeStorageException
   {
      try
      {
         Object[] params = { new Long(aAttachmentId) };
         AeAttachmentItemEntry entry = (AeAttachmentItemEntry) query(SELECT_ATTACHMENT_HEADERS, params, sAttachmentItemHandler);
         return entry.getHeaders();
      }
      catch (AeStorageException e)
      {
         throw e;
      }
      catch (AeException ex)
      {
         throw new AeStorageException(ex);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeAttachmentStorageProvider#getContent(long)
    */
   public InputStream getContent(long aAttachmentId) throws AeStorageException
   {
      Object[] params = { new Long(aAttachmentId) };
      return (InputStream) query(SELECT_ATTACHMENT_CONTENTS, params, AeResultSetHandlers.getBlobStreamHandler());
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeAttachmentStorageProvider#cleanup()
    */
   public void cleanup() throws AeStorageException
   {
      update(SQL_CLEANUP_ATTACHMENTS, null);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeAttachmentStorageProvider#removeAttachment(long)
    */
   public void removeAttachment(long aAttachmentId) throws AeStorageException
   {
      Object[] params = { new Long(aAttachmentId) };
      update(SQL_REMOVE_ATTACHMENT, params);
   }
}
