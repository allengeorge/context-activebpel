//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeDebugConnection.java,v 1.1 2006/06/15 18:45:1
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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

public class AeDebugConnection implements Connection
{
   private Connection mDelegate;
   private int mCloseCount;
   private static Hashtable sOpenConnections = new Hashtable();
   
   public AeDebugConnection(Connection aDelegate)
   {
      mDelegate = aDelegate;
      mCloseCount = 0;
      sOpenConnections.put(this, new RuntimeException("Opened Connection on thread: " + Thread.currentThread().getName())); //$NON-NLS-1$
   }

   public int getHoldability() throws SQLException
   {
      return mDelegate.getHoldability();
   }

   public int getTransactionIsolation() throws SQLException
   {
      return mDelegate.getTransactionIsolation();
   }

   public void clearWarnings() throws SQLException
   {
      mDelegate.clearWarnings();
   }

   public void close() throws SQLException
   {
      mCloseCount++;
      mDelegate.close();
      sOpenConnections.remove(this);
      mCloseCount++;
   }
   
   protected void finalize() throws Throwable
   {
      if (mCloseCount != 2)
      {
         throw new RuntimeException("** Unexpected close count: " + mCloseCount); //$NON-NLS-1$
      }
      super.finalize();
   }

   public void commit() throws SQLException
   {
      mDelegate.commit();
   }

   public void rollback() throws SQLException
   {
      mDelegate.rollback();
   }

   public boolean getAutoCommit() throws SQLException
   {
      return mDelegate.getAutoCommit();
   }

   public boolean isClosed() throws SQLException
   {
      return mDelegate.isClosed();
   }

   public boolean isReadOnly() throws SQLException
   {
      return mDelegate.isReadOnly();
   }

   public void setHoldability(int aHoldability) throws SQLException
   {
      mDelegate.setHoldability(aHoldability);
   }

   public void setTransactionIsolation(int aLevel) throws SQLException
   {
      mDelegate.setTransactionIsolation(aLevel);
   }

   public void setAutoCommit(boolean autoCommit) throws SQLException
   {
      mDelegate.setAutoCommit(autoCommit);
   }

   public void setReadOnly(boolean aReadOnly) throws SQLException
   {
      mDelegate.setReadOnly(aReadOnly);
   }

   public String getCatalog() throws SQLException
   {
      return mDelegate.getCatalog();
   }

   public void setCatalog(String aCatalog) throws SQLException
   {
      mDelegate.setCatalog(aCatalog);

   }

   public DatabaseMetaData getMetaData() throws SQLException
   {
      return mDelegate.getMetaData();
   }

   public SQLWarning getWarnings() throws SQLException
   {
      return mDelegate.getWarnings();
   }

   public Savepoint setSavepoint() throws SQLException
   {
      return mDelegate.setSavepoint();
   }

   public void releaseSavepoint(Savepoint aSavepoint) throws SQLException
   {
      mDelegate.releaseSavepoint(aSavepoint);
   }

   public void rollback(Savepoint aSavepoint) throws SQLException
   {
      mDelegate.rollback(aSavepoint);
   }

   public Statement createStatement() throws SQLException
   {
      return mDelegate.createStatement();
   }

   public Statement createStatement(int aResultSetType, int aResultSetConcurrency) throws SQLException
   {
      return mDelegate.createStatement(aResultSetType, aResultSetConcurrency);
   }

   public Statement createStatement(int aResultSetType, int aResultSetConcurrency, int aResultSetHoldability)
         throws SQLException
   {
      return mDelegate.createStatement(aResultSetType, aResultSetConcurrency, aResultSetHoldability);
   }

   public Map getTypeMap() throws SQLException
   {
      return mDelegate.getTypeMap();
   }

   public void setTypeMap(Map aMap) throws SQLException
   {
      mDelegate.setTypeMap(aMap);

   }

   public String nativeSQL(String aSql) throws SQLException
   {
      return mDelegate.nativeSQL(aSql);
   }

   public CallableStatement prepareCall(String aSql) throws SQLException
   {
      return mDelegate.prepareCall(aSql);
   }

   public CallableStatement prepareCall(String aSql, int aResultSetType, int aResultSetConcurrency)
         throws SQLException
   {
      return mDelegate.prepareCall(aSql, aResultSetType, aResultSetConcurrency);
   }

   public CallableStatement prepareCall(String aSql, int aResultSetType, int aResultSetConcurrency,
         int aResultSetHoldability) throws SQLException
   {
      return mDelegate.prepareCall(aSql, aResultSetType, aResultSetConcurrency, aResultSetHoldability);
   }

   public PreparedStatement prepareStatement(String aSql) throws SQLException
   {
      return mDelegate.prepareStatement(aSql);
   }

   public PreparedStatement prepareStatement(String aSql, int autoGeneratedKeys) throws SQLException
   {
      return mDelegate.prepareStatement(aSql, autoGeneratedKeys);
   }

   public PreparedStatement prepareStatement(String aSql, int aResultSetType, int aResultSetConcurrency)
         throws SQLException
   {
      return mDelegate.prepareStatement(aSql, aResultSetType, aResultSetConcurrency);
   }

   public PreparedStatement prepareStatement(String aSql, int aResultSetType, int aResultSetConcurrency,
         int aResultSetHoldability) throws SQLException
   {
      return mDelegate.prepareStatement(aSql, aResultSetType, aResultSetConcurrency, aResultSetHoldability);
   }

   public PreparedStatement prepareStatement(String aSql, int[] aColumnIndexes) throws SQLException
   {
      return mDelegate.prepareStatement(aSql, aColumnIndexes);
   }

   public Savepoint setSavepoint(String aName) throws SQLException
   {
      return mDelegate.setSavepoint(aName);
   }

   public PreparedStatement prepareStatement(String aSql, String[] aColumnNames) throws SQLException
   {
      return mDelegate.prepareStatement(aSql, aColumnNames);
   }
   
   public static void printStackTraces()
   {
      for(Enumeration en = sOpenConnections.elements(); en.hasMoreElements(); )
      {
         RuntimeException ex = (RuntimeException)en.nextElement();
         ex.printStackTrace();
      }
   }

}
