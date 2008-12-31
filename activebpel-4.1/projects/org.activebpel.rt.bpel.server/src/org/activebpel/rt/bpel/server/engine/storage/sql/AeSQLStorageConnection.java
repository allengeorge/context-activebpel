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

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection;
import org.activebpel.rt.util.AeCloser;

/**
 * A SQL implementation of the delegating DB connection interface.  This class is created with 
 * a SQL connection that it will delegate its calls to.
 */
public class AeSQLStorageConnection implements IAeStorageConnection
{
   /** The SQL Connection. */
   private Connection mConnection;

   /**
    * Constructs a SQL DB Connection that will delegate to the given SQL Connection.
    * 
    * @param aConnection
    */
   public AeSQLStorageConnection(Connection aConnection)
   {
      setConnection(aConnection);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection#commit()
    */
   public void commit() throws AeStorageException
   {
      try
      {
         getConnection().commit();
      }
      catch (SQLException ex)
      {
         throw new AeStorageException(ex);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection#rollback()
    */
   public void rollback() throws AeStorageException
   {
      try
      {
         getConnection().rollback();
      }
      catch (SQLException ex)
      {
         throw new AeStorageException(ex);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection#close()
    */
   public void close()
   {
      AeCloser.close(getConnection());
   }

   /**
    * @return Returns the connection.
    */
   public Connection getConnection()
   {
      return mConnection;
   }

   /**
    * @param aConnection The connection to set.
    */
   protected void setConnection(Connection aConnection)
   {
      mConnection = aConnection;
   }

}
