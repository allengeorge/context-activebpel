// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/AeAbstractXMLDBStorageProvider.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider;

/**
 * Base class for all XMLDB storage providers.
 */
public abstract class AeAbstractXMLDBStorageProvider extends AeAbstractXMLDBStorage implements IAeStorageProvider
{
   /** Debug flag. */
   private static final boolean DEBUG = false;

   /**
    * Constructs a XMLDB Storage Delegate.
    *
    * @param aConfig
    * @param aPrefix
    * @param aStorageImpl
    */
   public AeAbstractXMLDBStorageProvider(AeXMLDBConfig aConfig, String aPrefix, IAeXMLDBStorageImpl aStorageImpl)
   {
      super(aConfig, aPrefix, aStorageImpl);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider#getDBConnection()
    */
   public IAeStorageConnection getDBConnection() throws AeStorageException
   {
      return new AeXMLDBStorageConnection(getNewConnection());
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider#getCommitControlDBConnection()
    */
   public IAeStorageConnection getCommitControlDBConnection() throws AeStorageException
   {
      // Note: IAeStorageConnection impl should not be wrapped around a TransactionManager connection.
      // (Always use a new connection).
      IAeXMLDBConnection connection = getNewConnection(false);
      if (DEBUG)
      {
         return new AeDebugXMLDBStorageConnection(connection);
      }
      else
      {
         return new AeXMLDBStorageConnection(connection);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider#getTxCommitControlDBConnection()
    */
   public IAeStorageConnection getTxCommitControlDBConnection() throws AeStorageException
   {
      IAeXMLDBConnection connection = getTransactionManagerConnection(true);
      if (DEBUG)
      {
         return new AeDebugXMLDBStorageConnection(connection);
      }
      else
      {
         return new AeXMLDBStorageConnection(connection);
      }
   }

   /**
    * Convenience method to return the XMLDB connection from the abstract IAeDBConnection.
    *
    * @param aConnection
    */
   protected IAeXMLDBConnection getXMLDBConnection(IAeStorageConnection aConnection)
   {
      return ((AeXMLDBStorageConnection) aConnection).getConnection();
   }

   /**
    * Convenience method to return a Transaction manager XMLDB connection.
    * If the current thread is not participating in an active transaction, then
    * a normal (with autocommit) connection is returned.
    *
    * @throws AeStorageException
    */
   protected IAeXMLDBConnection getXMLDBTransactionManagerConnection() throws AeStorageException
   {
      return getTransactionManagerConnection(false);
   }
}
