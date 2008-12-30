//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeSQLDatabaseType.java,v 1.3 2005/02/08 15:35:5
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
import java.sql.SQLException;

import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.util.AeCloser;
import org.activebpel.rt.util.AeUtil;

/**
 * Implements a database type lookup from the database.  The database has been configured with
 * a value for DatabaseType in the meta info table.  This row will contain the type of the 
 * database, for example:  mysql, db2, sqlserver, or oracle.  The application checks this 
 * value against the value found in the engine configuration as an extra configuration check.
 */
public class AeSQLDatabaseType extends AeSQLObject
{
   /** Singleton instance. */
   private static final AeSQLDatabaseType mInstance = new AeSQLDatabaseType(); 

   /**
    * Constructor.
    */
   protected AeSQLDatabaseType()
   {
   }

   /**
    * Returns singleton instance.
    */
   public static AeSQLDatabaseType getInstance()
   {
      return mInstance;
   }

   /**
    * Returns the type of database.
    * 
    * @return The type of the database.
    * @throws AeStorageException
    * @throws SQLException
    */
   public String getDatabaseType() throws AeStorageException
   {
      try
      {
         Connection connection = getConnection();

         try
         {
            return getDatabaseType(connection);
         }
         finally
         {
            AeCloser.close(connection);
         }
      }
      catch (SQLException e)
      {
         throw new AeStorageException(AeMessages.getString("AeSQLDatabaseType.ERROR_0"), e); //$NON-NLS-1$
      }
   }

   /**
    * Returns the database type as configured in the meta info table.
    * 
    * @param aConnection The database connection to use.
    * @return The configured database type.
    * @throws AeStorageException
    * @throws SQLException
    */
   private String getDatabaseType(Connection aConnection) throws AeStorageException, SQLException
   {
      String sql = getDataSource().getSQLConfig().getSQLStatement("MetaInfo.GetDatabaseType"); //$NON-NLS-1$
      if (AeUtil.isNullOrEmpty(sql))
      {
         throw new AeStorageException(AeMessages.getString("AeSQLDatabaseType.ERROR_2")); //$NON-NLS-1$
      }

      String value = (String) getQueryRunner().query(aConnection, sql, AeResultSetHandlers.getStringHandler());
      if (value == null)
      {
         throw new AeStorageException(AeMessages.getString("AeSQLDatabaseType.ERROR_3")); //$NON-NLS-1$
      }

      return value;
   }

}
