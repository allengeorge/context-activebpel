//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/AePooledTaminoDataSource.java,v 1.9 2007/08/17 00:57:3
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
package org.activebpel.rt.bpel.server.engine.storage.tamino;

import com.softwareag.tamino.db.api.connection.TConnection;
import com.softwareag.tamino.db.api.connection.TConnectionNotAvailableException;
import com.softwareag.tamino.db.api.connection.TConnectionPoolDescriptor;
import com.softwareag.tamino.db.api.connection.TConnectionPoolManager;
import com.softwareag.tamino.db.api.connection.TIsolationDegree;
import com.softwareag.tamino.db.api.connection.TLockMode;
import com.softwareag.tamino.db.api.connection.TLockwaitMode;
import com.softwareag.tamino.db.api.connection.TServerNotAvailableException;

import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.tamino.AeMessages;
import org.activebpel.rt.util.AeUtil;

/**
 * A pooled version of the Tamino data source.  This pooled implementation is built on top of the
 * Apache commons pool library.
 */
public class AePooledTaminoDataSource extends AeTaminoDataSource
{
   /** The <code>TIMEOUT</code> field is a key into the engine config map. */
   private static final String TIMEOUT = "TimeOut"; //$NON-NLS-1$
   /** The <code>MAX_TRANSACTION_DURATION</code> field is a key into the engine config map. */
   private static final String MAX_TRANSACTION_DURATION = "MaxTransactionDuration"; //$NON-NLS-1$
   /** The <code>MAX_CONNECTIONS</code> field is a key into the engine config map. */
   public static final String MAX_CONNECTIONS = "MaxConnections"; //$NON-NLS-1$
   /** The <code>POOL_NAME</code> field is a key into the engine config map. */
   private static final String POOL_NAME = "PoolName"; //$NON-NLS-1$
   /** The <code>INITIAL_CONNECTIONS</code> field is a key into the engine config map. */
   public static final String INITIAL_CONNECTIONS = "InitialConnections"; //$NON-NLS-1$

   /** The name of our Tamino connection pool. */
   private static final String DEFAULT_POOL_NAME = "__aei_pool__"; //$NON-NLS-1$

   /** The name of the pool. */
   private String mPoolName;
   
   /**
    * Constructs a data source.  Uses information in the engine configuration map
    * to initialize its state.
    *
    * @param aConfig The engine configuration map for this data source.
    */
   public AePooledTaminoDataSource(Map aConfig) throws AeException
   {
      super(aConfig);

      createConnectionPool(aConfig);
   }
   
   /**
    * Destroys the pooled tamino data source.  Useful for offline testing.
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoDataSource#destroy()
    */
   public void destroy()
   {
      try
      {
         TConnectionPoolManager.getInstance().release(getPoolName());
      }
      catch (TConnectionNotAvailableException ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Creates the connection pool.
    * 
    * @param aConfig
    * @throws AeException
    */
   protected void createConnectionPool(Map aConfig) throws AeException
   {
      TConnectionPoolDescriptor descriptor = new TConnectionPoolDescriptor();
      descriptor.setDatabaseURI(getURL());
      descriptor.setDomain(getDomain());
      descriptor.setUser(getUsername());
      descriptor.setPassword(getPassword());
      descriptor.setIsolationDegree(TIsolationDegree.STABLE_DOCUMENT);
      descriptor.setLockMode(TLockMode.PROTECTED);
      descriptor.setLockwaitMode(TLockwaitMode.YES);
      descriptor.setNonActivityTimeout(300);
      descriptor.setMaxConnections(30);
      descriptor.setInitConnections(15);
      descriptor.setMaximumTransactionDuration(60);
      descriptor.setTimeOut(10);

      // Set some values from the engine config.
      String str = (String) aConfig.get(MAX_CONNECTIONS);
      if (AeUtil.notNullOrEmpty(str))
      {
         int maxConnections = Integer.parseInt(str);
         descriptor.setMaxConnections(maxConnections);
      }
      str = (String) aConfig.get(INITIAL_CONNECTIONS);
      if (AeUtil.notNullOrEmpty(str))
      {
         int initConnections = Integer.parseInt(str);
         descriptor.setInitConnections(initConnections);
      }
      str = (String) aConfig.get(MAX_TRANSACTION_DURATION);
      if (AeUtil.notNullOrEmpty(str))
      {
         int maxTxDuration = Integer.parseInt(str);
         descriptor.setMaximumTransactionDuration(maxTxDuration);
      }
      str = (String) aConfig.get(TIMEOUT);
      if (AeUtil.notNullOrEmpty(str))
      {
         int timeout = Integer.parseInt(str);
         descriptor.setTimeOut(timeout);
      }

      str = (String) aConfig.get(POOL_NAME);
      if (AeUtil.isNullOrEmpty(str))
      {
         str = DEFAULT_POOL_NAME;
      }
      setPoolName(str);

      // Create the Tamino connection pool.
      try
      {
         if (!TConnectionPoolManager.getInstance().addConnectionPool(getPoolName(), descriptor))
         {
            throw new AeXMLDBException(AeMessages.getString("AePooledTaminoDataSource.ERROR_CREATING_TAMINO_CONNECTION_POOL")); //$NON-NLS-1$
         }
      }
      catch (TServerNotAvailableException ex)
      {
         throw new AeXMLDBException(ex);
      }
      catch (TConnectionNotAvailableException ex)
      {
         throw new AeXMLDBException(ex);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoDataSource#getTaminoConnection()
    */
   protected TConnection getTaminoConnection() throws Exception
   {
      return TConnectionPoolManager.getInstance().getConnection(getPoolName());
   }
   
   /**
    * @return Returns the poolName.
    */
   protected String getPoolName()
   {
      return mPoolName;
   }

   /**
    * @param aPoolName The poolName to set.
    */
   protected void setPoolName(String aPoolName)
   {
      mPoolName = aPoolName;
   }
}
