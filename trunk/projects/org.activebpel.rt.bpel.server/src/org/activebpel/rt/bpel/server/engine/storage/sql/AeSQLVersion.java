// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeSQLVersion.java,v 1.5 2005/02/08 15:35:5
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
 * Implements database version lookup in the database.
 */
public class AeSQLVersion extends AeSQLObject
{
   /** Singleton instance. */
   private static final AeSQLVersion mInstance = new AeSQLVersion(); 

   /**
    * Constructor.
    */
   protected AeSQLVersion()
   {
   }

   /**
    * Returns singleton instance.
    */
   public static AeSQLVersion getInstance()
   {
      return mInstance;
   }

   /**
    * Returns the version of the databse structure.
    * @return The version of the databsse structure/
    * @throws AeStorageException
    * @throws SQLException
    */
   public String getVersion() throws AeStorageException
   {
      try
      {
         Connection connection = getConnection();

         try
         {
            return getVersion(connection);
         }
         finally
         {
            AeCloser.close(connection);
         }
      }
      catch (SQLException e)
      {
         throw new AeStorageException(AeMessages.getString("AeSQLVersion.ERROR_0"), e); //$NON-NLS-1$
      }
   }

   /**
    * Returns current version of the database structure as set in the AeMetaInfo table.
    * @param aConnection The database connection to use.
    * @return String
    * @throws AeStorageException
    * @throws SQLException
    */
   private String getVersion(Connection aConnection) throws AeStorageException, SQLException
   {
      String sql = getDataSource().getSQLConfig().getSQLStatement("MetaInfo.GetVersion"); //$NON-NLS-1$
      if (AeUtil.isNullOrEmpty(sql))
      {
         throw new AeStorageException(AeMessages.getString("AeSQLVersion.ERROR_2")); //$NON-NLS-1$
      }

      String value = (String) getQueryRunner().query(aConnection, sql, AeResultSetHandlers.getStringHandler());
      if (value == null)
      {
         throw new AeStorageException(AeMessages.getString("AeSQLVersion.ERROR_0")); //$NON-NLS-1$
      }

      return value;
   }
}
