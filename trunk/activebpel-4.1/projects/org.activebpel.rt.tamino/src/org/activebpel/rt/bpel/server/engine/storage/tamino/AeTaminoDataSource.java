//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/AeTaminoDataSource.java,v 1.4 2007/08/17 00:57:3
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
import com.softwareag.tamino.db.api.connection.TConnectionFactory;
import com.softwareag.tamino.db.api.connection.TLockMode;
import com.softwareag.tamino.db.api.connection.TLockwaitMode;
import com.softwareag.tamino.db.api.connection.TServerNotAvailableException;

import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.config.IAeEngineConfiguration;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource;
import org.activebpel.rt.tamino.AeMessages;
import org.activebpel.rt.util.AeUtil;

/**
 * The Tamino data source.
 */
public class AeTaminoDataSource implements IAeTaminoDataSource
{
   public static IAeXMLDBDataSource MAIN = null;

   /** The URL to the Tamino installation. */
   private String mURL;
   /** The name of the collection within the database. */
   private String mCollection;
   /** The domain in which the username and password are found (optional). */
   private String mDomain;
   /** The data source username (optional). */
   private String mUsername;
   /** The data source password (optional). */
   private String mPassword;

   /**
    * Constructs a data source.  Uses information in the engine configuration map
    * to initialize its state.
    *
    * @param aConfig The engine configuration map for this data source.
    */
   public AeTaminoDataSource(Map aConfig) throws AeException
   {
      try
      {
         String url = (String) aConfig.get(URL);
         String db = (String) aConfig.get(DATABASE);
         String collection = (String) aConfig.get(COLLECTION);
         String domain = (String) aConfig.get(DOMAIN);
         String username = (String) aConfig.get(IAeEngineConfiguration.DS_USERNAME_ENTRY);
         String password = (String) aConfig.get(IAeEngineConfiguration.DS_PASSWORD_ENTRY);

         if (!url.endsWith("/")) //$NON-NLS-1$
         {
            url = url + "/"; //$NON-NLS-1$
         }
         url = url + db;

         setURL(url);
         setCollection(collection);
         setDomain(domain);
         setUsername(username);
         setPassword(password);
      }
      catch (Exception e)
      {
         throw new AeException(AeMessages.getString("AeTaminoDataSource.ERROR_CREATING_DATASOURCE"), e); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.IAeTaminoDataSource#destroy()
    */
   public void destroy()
   {
      // no-op
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.IAeTaminoDataSource#getCollectionName()
    */
   public String getCollectionName()
   {
      return getCollection();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource#getNewConnection(boolean)
    */
   public IAeXMLDBConnection getNewConnection(boolean aAutoCommit) throws AeXMLDBException
   {
      try
      {
         TConnection connection = getTaminoConnection();
         if (!aAutoCommit)
            connection = new AeLocalTxTaminoConnection(connection);
         return new AeTaminoXMLDBConnection(connection);
      }
      catch (Throwable t)
      {
         throw new AeXMLDBException(AeMessages.getString("AeTaminoDataSource.ERROR_GETTING_CONNECTION"), t); //$NON-NLS-1$
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource#getNewConnection()
    */
   public IAeXMLDBConnection getNewConnection() throws AeXMLDBException
   {
      return getNewConnection(true);
   }

   /**
    * Gets a new Tamino connection.
    * 
    * @throws TServerNotAvailableException
    */
   protected TConnection getTaminoConnection() throws Exception
   {
      TConnection connection = null;
      if (AeUtil.notNullOrEmpty(getDomain()))
      {
         connection = TConnectionFactory.getInstance().newConnection(getURL(), getDomain(), getUsername(),
               getPassword());
      }
      else if (AeUtil.notNullOrEmpty(getUsername()))
      {
         connection = TConnectionFactory.getInstance().newConnection(getURL(), getUsername(), getPassword());
      }
      else
      {
         connection = TConnectionFactory.getInstance().newConnection(getURL());
      }
      connection.setLockMode(TLockMode.SHARED);
      connection.setLockwaitMode(TLockwaitMode.YES);
      return connection;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource#getNativeDataSource()
    */
   public Object getNativeDataSource()
   {
      return this;
   }

   /**
    * @return Returns the password.
    */
   protected String getPassword()
   {
      return mPassword;
   }

   /**
    * @param aPassword The password to set.
    */
   protected void setPassword(String aPassword)
   {
      mPassword = aPassword;
   }

   /**
    * @return Returns the username.
    */
   protected String getUsername()
   {
      return mUsername;
   }

   /**
    * @param aUsername The username to set.
    */
   protected void setUsername(String aUsername)
   {
      mUsername = aUsername;
   }

   /**
    * @return Returns the uRL.
    */
   protected String getURL()
   {
      return mURL;
   }
   
   /**
    * @param aUrl The uRL to set.
    */
   protected void setURL(String aUrl)
   {
      mURL = aUrl;
   }
   
   /**
    * @return Returns the collection.
    */
   protected String getCollection()
   {
      return mCollection;
   }
   
   /**
    * @param aCollection The collection to set.
    */
   protected void setCollection(String aCollection)
   {
      mCollection = aCollection;
   }
   
   /**
    * @return Returns the domain.
    */
   protected String getDomain()
   {
      return mDomain;
   }
   
   /**
    * @param aDomain The domain to set.
    */
   protected void setDomain(String aDomain)
   {
      mDomain = aDomain;
   }
}
