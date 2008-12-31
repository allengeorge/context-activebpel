//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeDelegatingDataSource.java,v 1.1 2007/08/13 17:57:0
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

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * A delegating DataSource
 */
public class AeDelegatingDataSource implements DataSource
{
   /** our delegate */
   private DataSource mDelegate;
   
   /**
    * Ctor accepts delegate
    * @param aDelegate
    */
   public AeDelegatingDataSource(DataSource aDelegate)
   {
      setDelegate(aDelegate);
   }

   /**
    * @see javax.sql.DataSource#getConnection()
    */
   public Connection getConnection() throws SQLException
   {
      return getDelegate().getConnection();
   }

   /**
    * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
    */
   public Connection getConnection(String aUsername, String aPassword)
         throws SQLException
   {
      return getDelegate().getConnection(aUsername, aPassword);
   }

   /**
    * @see javax.sql.DataSource#getLogWriter()
    */
   public PrintWriter getLogWriter() throws SQLException
   {
      return getDelegate().getLogWriter();
   }

   /**
    * @see javax.sql.DataSource#getLoginTimeout()
    */
   public int getLoginTimeout() throws SQLException
   {
      return getDelegate().getLoginTimeout();
   }

   /**
    * @see javax.sql.DataSource#setLogWriter(java.io.PrintWriter)
    */
   public void setLogWriter(PrintWriter aOut) throws SQLException
   {
      getDelegate().setLogWriter(aOut);
   }

   /**
    * @see javax.sql.DataSource#setLoginTimeout(int)
    */
   public void setLoginTimeout(int aSeconds) throws SQLException
   {
      getDelegate().setLoginTimeout(aSeconds);
   }

   /**
    * @return the delegate
    */
   protected DataSource getDelegate()
   {
      return mDelegate;
   }

   /**
    * @param aDelegate the delegate to set
    */
   protected void setDelegate(DataSource aDelegate)
   {
      mDelegate = aDelegate;
   }
}
 
