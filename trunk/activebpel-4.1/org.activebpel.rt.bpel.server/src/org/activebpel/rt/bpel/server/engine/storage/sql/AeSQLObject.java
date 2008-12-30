// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeSQLObject.java,v 1.21 2007/06/10 19:11:1
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
import java.util.Date;

import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.util.AeUtil;
import org.apache.commons.dbutils.QueryRunner;

/**
 * Base class for objects that access the database.
 */
public class AeSQLObject
{
   /** Executes SQL queries. */
   private final QueryRunner mQueryRunner = new AeQueryRunner(getDataSource());

   /**
    * Returns database connection.
    *
    * @throws AeStorageException
    */
   protected Connection getConnection() throws AeStorageException
   {
      try
      {
         return getDataSource().getConnection();
      }
      catch (SQLException e)
      {
         throw new AeStorageException(AeMessages.getString("AeSQLObject.ERROR_0"), e); //$NON-NLS-1$
      }
   }
   
   /**
    * Returns a new database connection, outside of the current transaction if 
    * one is active.
    * 
    * @throws AeStorageException
    */
   protected Connection getNewConnection() throws AeStorageException
   {
      try
      {
         return getDataSource().getNewConnection();
      }
      catch (SQLException e)
      {
         throw new AeStorageException(AeMessages.getString("AeSQLObject.ERROR_0"), e); //$NON-NLS-1$
      }
   }

   /**
    * Accessor for connection with auto-commit flag set to false.
    * Callers must explicitly call commit to persist.
    * @throws AeStorageException
    */
   protected Connection getCommitControlConnection() throws AeStorageException
   {
      return getCommitControlConnection(false);
   }

   /**
    * Accessor for connection with auto-commit flag set to false.
    * Callers must explicitly call commit to persist.
    *
    * @param aVerifyFlag Verify that {@link java.sql.Connection#commit()} or {@link java.sql.Connection#rollback()} is called.
    * @throws AeStorageException
    */
   protected Connection getCommitControlConnection( boolean aVerifyFlag ) throws AeStorageException
   {
      try
      {
         return getDataSource().getCommitControlConnection(aVerifyFlag);
      }
      catch (SQLException e)
      {
         throw new AeStorageException(AeMessages.getString("AeSQLObject.ERROR_1"), e); //$NON-NLS-1$
      }
   }
   
   /**
    * Accessor for a transaction manager based connection. If the current thread is not 
    * participating in a transaction, then a new (normal) connection is return instead
    * of a commit control connection.
    * @return 
    * @throws AeStorageException
    */
   protected Connection getTransactionConnection() throws AeStorageException
   {
      return getTransactionConnection(false, false);
   }
   
   /**
    * Accessor for a transaction manager based connection. If the current thread is not 
    * participating in a transaction, then a either a normal connection or a commit control
    * connection is returned.
    * 
    * @param aCommitControlOnFallback if true, returns a commit control connection if the thread is not part of a active transaction.
    * @param aVerifyFlag Verify that {@link java.sql.Connection#commit()} or {@link java.sql.Connection#rollback()} is called for commit control connections. 
    * @throws AeStorageException
    */   
   protected Connection getTransactionConnection(boolean aCommitControlOnFallback, boolean aVerifyFlag) throws AeStorageException
   {
      try
      {
         return getDataSource().getTransactionManagerConnection(aCommitControlOnFallback, aVerifyFlag);
      }
      catch (SQLException e)
      {
         throw new AeStorageException(AeMessages.getString("AeSQLObject.ERROR_1"), e); //$NON-NLS-1$
      }
   }   
     
   /**
    * Returns database connection directly from container data source.
    *
    * @throws SQLException
    */
   protected Connection getContainerManagedConnection() throws AeStorageException
   {
      try
      {
         return getDataSource().getContainerManagedConnection();
      }
      catch (SQLException e)
      {
         throw new AeStorageException(AeMessages.getString("AeSQLObject.ERROR_2"), e); //$NON-NLS-1$
      }
   }

   /**
    * Returns the <code>DataSource</code> to use for SQL operations.
    */
   protected AeDataSource getDataSource()
   {
      return AeDataSource.MAIN;
   }

   /**
    * Returns the <code>QueryRunner</code> to use for queries and updates.
    */
   protected QueryRunner getQueryRunner()
   {
      return mQueryRunner;
   }

   /**
    * Convenience method that returns the date passed in or the sql type null for
    * timestamp.
    *
    * @param aDate
    */
   protected Object getDateOrSqlNull(Date aDate)
   {
      if( aDate == null )
      {
         return AeQueryRunner.NULL_TIMESTAMP;
      }
      return aDate;
   }

   /**
    * Convenience method that returns the date passed as a Long (time in millis) or a
    * NULL_BIGINT if the date is null.
    *
    * @param aDate
    */
   protected Object getDateAsLongOrSqlNull(Date aDate)
   {
      if (aDate == null)
      {
         return AeQueryRunner.NULL_BIGINT;
      }
      return new Long(aDate.getTime());
   }

   /**
    * Convenience method that returns the Long or the NULL_BIGINT 
    * value.
    * 
    * @param aLong
    */
   protected Object getLongOrSqlNull(Long aLong)
   {
      if (aLong == null)
         return AeQueryRunner.NULL_BIGINT;
      return aLong;
   }

   /**
    * Convenience method that returns the String or the NULL_VARCHAR 
    * value.
    * 
    * @param aString
    */
   protected Object getStringOrSqlNullVarchar(String aString)
   {
      if (aString == null)
         return AeQueryRunner.NULL_VARCHAR;
      return aString;
   }

   /**
    * Convenience method that returns the String or the NULL_VARCHAR 
    * value.  If the String is null or empty string, then null varchar
    * is returned.
    * 
    * @param aString
    */
   protected Object getStringOrSqlNullVarcharNoEmpty(String aString)
   {
      if (AeUtil.isNullOrEmpty(aString))
         return AeQueryRunner.NULL_VARCHAR;
      return aString;
   }
}
