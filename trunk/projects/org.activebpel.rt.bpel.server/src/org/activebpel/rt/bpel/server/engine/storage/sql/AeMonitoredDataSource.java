//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeMonitoredDataSource.java,v 1.1 2007/08/13 17:57:0
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

import javax.sql.DataSource;

import org.activebpel.rt.bpel.IAeMonitorListener;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Wraps a DataSource in order to monitor how long it takes to get a connection. 
 */
public class AeMonitoredDataSource extends AeDelegatingDataSource
{
   /**
    * Ctor accepts delegate
    * @param aDelegate
    */
   public AeMonitoredDataSource(DataSource aDelegate)
   {
      super(aDelegate);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeDelegatingDataSource#getConnection()
    */
   public Connection getConnection() throws SQLException
   {
      long time = System.currentTimeMillis();
      Connection conn = super.getConnection();
      reportTime(time);
      return conn;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeDelegatingDataSource#getConnection(java.lang.String, java.lang.String)
    */
   public Connection getConnection(String aUsername, String aPassword) throws SQLException
   {
      long time = System.currentTimeMillis();
      Connection conn = super.getConnection(aUsername, aPassword);
      reportTime(time);
      return conn;
   }
   
   /**
    * Reports how long it took to get a connection.
    * @param aStartTime
    */
   protected void reportTime(long aStartTime)
   {
      long durationInMillis = System.currentTimeMillis() - aStartTime;
      IAeBusinessProcessEngineInternal engine = AeEngineFactory.getEngine();
      // engine could be null during startup only. At that point, we hit the db in order to verify the storage setup.
      if (engine != null)
         engine.fireMonitorEvent(IAeMonitorListener.MONITOR_DB_CONN_TIME, durationInMillis);
   }
}
 
