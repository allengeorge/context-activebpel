//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/AeMonitoredXMLDBDataSource.java,v 1.1 2007/08/17 00:40:5
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

import org.activebpel.rt.bpel.IAeMonitorListener;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Reports how long it took to acquire a connection
 */
public class AeMonitoredXMLDBDataSource implements IAeXMLDBDataSource
{
   /** source that we delegate to */
   private IAeXMLDBDataSource mDelegate;
   
   /**
    * Ctor
    * @param aSource
    */
   public AeMonitoredXMLDBDataSource(IAeXMLDBDataSource aSource)
   {
      setDelegate(aSource);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource#getNativeDataSource()
    */
   public Object getNativeDataSource()
   {
      return getDelegate().getNativeDataSource();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.IAeTaminoDataSource#destroy()
    */
   public void destroy()
   {
      getDelegate().destroy();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource#getNewConnection()
    */
   public IAeXMLDBConnection getNewConnection() throws AeXMLDBException
   {
      long time = System.currentTimeMillis();
      IAeXMLDBConnection conn = getDelegate().getNewConnection();
      reportTime(time);
      return conn;
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource#getNewConnection(boolean)
    */
   public IAeXMLDBConnection getNewConnection(boolean aAutoCommit) throws AeXMLDBException
   {
      long time = System.currentTimeMillis();
      IAeXMLDBConnection conn = getDelegate().getNewConnection(aAutoCommit);
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

   /**
    * @return the delegate
    */
   protected IAeXMLDBDataSource getDelegate()
   {
      return mDelegate;
   }

   /**
    * @param aDelegate the delegate to set
    */
   protected void setDelegate(IAeXMLDBDataSource aDelegate)
   {
      mDelegate = aDelegate;
   }
}
 
