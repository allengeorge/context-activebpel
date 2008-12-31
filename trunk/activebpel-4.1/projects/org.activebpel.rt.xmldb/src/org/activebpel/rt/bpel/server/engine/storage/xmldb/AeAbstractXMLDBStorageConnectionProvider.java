//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/AeAbstractXMLDBStorageConnectionProvider.java,v 1.1 2007/08/17 00:40:5
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

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;

/**
 * A base class for a XMLDB storage connection delegate.
 */
public abstract class AeAbstractXMLDBStorageConnectionProvider extends AeAbstractXMLDBStorageProvider
{
   /** <code>true</code> to respect container-managed transaction boundaries. */
   private boolean mContainerManaged;
   /** The shared XMLDB <code>Connection</code>. */
   private IAeXMLDBConnection mSharedConnection;

   /**
    * Constructs a xmldb process state connection from the given config.
    * 
    * @param aConfig
    * @param aPrefix
    * @param aContainerManaged
    * @param aStorageImpl
    */
   public AeAbstractXMLDBStorageConnectionProvider(AeXMLDBConfig aConfig, String aPrefix,
         boolean aContainerManaged, IAeXMLDBStorageImpl aStorageImpl)
   {
      super(aConfig, aPrefix, aStorageImpl);
      setContainerManaged(aContainerManaged);
   }

   /**
    * Returns the XMLDB <code>IAeXMLDBConnection</code> to use.
    * 
    * @throws AeXMLDBException
    */
   protected IAeXMLDBConnection getConnection() throws AeXMLDBException
   {
      if (getSharedConnection() == null)
      {
         if (isContainerManaged())
         {
            // Container managed persistence is not supported.
            // TODO this needs to be supported (feature forthcoming)
            throw new UnsupportedOperationException();
         }
         else
         { 
            setSharedConnection(getTransactionManagerConnection(true));
         }
      }

      return getSharedConnection();
   }

   /**
    * Closes the connection.
    * 
    * @throws AeExistException
    */
   public void close() throws AeXMLDBException
   {
      if (getSharedConnection() != null)
      {
         getSharedConnection().close();
         setSharedConnection(null);
      }
   }

   /**
    * Commits the transaction.
    * 
    * @throws AeStorageException
    */
   public void commit() throws AeXMLDBException
   {
      if (!isContainerManaged())
      {
         getConnection().commit();
      }
   }
   
   /**
    * Rolls back the transaction.
    * 
    * @throws AeStorageException
    */
   public void rollback() throws AeXMLDBException
   {
      if (!isContainerManaged())
      {
         getConnection().rollback();
      }
   }

   /**
    * @return Returns the containerManaged.
    */
   protected boolean isContainerManaged()
   {
      return mContainerManaged;
   }

   /**
    * @param aContainerManaged The containerManaged to set.
    */
   protected void setContainerManaged(boolean aContainerManaged)
   {
      mContainerManaged = aContainerManaged;
   }

   /**
    * @return Returns the sharedConnection.
    */
   protected IAeXMLDBConnection getSharedConnection()
   {
      return mSharedConnection;
   }

   /**
    * @param aSharedConnection The sharedConnection to set.
    */
   protected void setSharedConnection(IAeXMLDBConnection aSharedConnection)
   {
      mSharedConnection = aSharedConnection;
   }
}
