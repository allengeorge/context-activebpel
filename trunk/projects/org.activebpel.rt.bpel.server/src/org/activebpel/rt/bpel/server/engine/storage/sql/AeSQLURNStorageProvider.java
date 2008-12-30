// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeSQLURNStorageProvider.java,v 1.3 2007/01/30 22:32:4
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

import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeURNStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.sql.handlers.AeURNMappingHandler;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * A SQL implementation of a URN storage provider.
 */
public class AeSQLURNStorageProvider extends AeAbstractSQLStorageProvider implements IAeURNStorageProvider
{
   /** The SQL statement prefix for all SQL statements used in this class. */
   protected static final String SQLSTATEMENT_PREFIX = "URNStorage."; //$NON-NLS-1$

   /** resultset handler used to read the urn mappings into a map */
   private static final ResultSetHandler URN_MAPPING_HANDLER = new AeURNMappingHandler();

   /**
    * Creates the SQL URN storage delegate with the given sql config.
    * 
    * @param aConfig
    */
   public AeSQLURNStorageProvider(AeSQLConfig aConfig)
   {
      super(SQLSTATEMENT_PREFIX, aConfig);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeURNStorageProvider#getMappings()
    */
   public Map getMappings() throws AeStorageException
   {
      return (Map) query(IAeURNSQLKeys.SQL_GET_MAPPINGS, new Object[0], URN_MAPPING_HANDLER);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeURNStorageProvider#addMapping(java.lang.String, java.lang.String, org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection)
    */
   public void addMapping(String aURN, String aURL, IAeStorageConnection aConnection) throws AeStorageException
   {
      Object[] args = new Object[] { aURN, aURL.toCharArray()};
      update(getSQLConnection(aConnection), IAeURNSQLKeys.SQL_INSERT_MAPPING, args);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeURNStorageProvider#removeMapping(java.lang.String, org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection)
    */
   public void removeMapping(String aURN, IAeStorageConnection aConnection) throws AeStorageException
   {
      update(getSQLConnection(aConnection), IAeURNSQLKeys.SQL_DELETE_MAPPING, new Object[] { aURN });
   }
}
