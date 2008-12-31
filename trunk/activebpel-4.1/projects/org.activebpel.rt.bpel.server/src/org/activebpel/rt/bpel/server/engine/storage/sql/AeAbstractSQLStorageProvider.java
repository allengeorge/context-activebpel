// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeAbstractSQLStorageProvider.java,v 1.7 2007/05/11 17:55:3
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

import java.sql.Connection;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider;

/**
 * An abstract storage provider implementation for SQL.
 */
public abstract class AeAbstractSQLStorageProvider extends AeAbstractSQLStorage implements IAeStorageProvider
{
   /**
    * Constructs a SQL storage delegate with the given storage prefix and config.
    * 
    * @param aPrefix
    * @param aConfig
    */
   public AeAbstractSQLStorageProvider(String aPrefix, AeSQLConfig aConfig)
   {
      super(aPrefix, aConfig);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider#getDBConnection()
    */
   public IAeStorageConnection getDBConnection() throws AeStorageException
   {
      return new AeSQLStorageConnection(getNewConnection());
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider#getCommitControlDBConnection()
    */
   public IAeStorageConnection getCommitControlDBConnection() throws AeStorageException
   {
      // Note: AeSQLStorageConnection is NOT wrapped around a TransactionManager connection
      // (always returns a new commit control connection
      return new AeSQLStorageConnection( getCommitControlConnection() );
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider#getTxCommitControlDBConnection()
    */
   public IAeStorageConnection getTxCommitControlDBConnection() throws AeStorageException
   {
      // Fall back to a commit control connection if no active TX.
      return new AeSQLStorageConnection(getTransactionConnection(true, false));
   }
   
   /**
    * Convenience method to get the SQL connection from the IAeDBConnection.
    * 
    * @param aConnection
    */
   protected Connection getSQLConnection(IAeStorageConnection aConnection)
   {
      return ((AeSQLStorageConnection) aConnection).getConnection();
   }
}
