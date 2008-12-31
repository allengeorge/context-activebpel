//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/tx/AeTxManagerXMLDBConnection.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.tx;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection;
import org.activebpel.rt.xmldb.AeMessages;

/**
 * A delegate version of a xmldb local tx connection where the close, commit and rollback methods are
 * ignored. The connection is really closed on commit or rollback operations initiated by the
 * TransactionManager.
 */
public class AeTxManagerXMLDBConnection implements IAeXMLDBConnection
{
   /** XML DB connection. */
   private IAeXMLDBConnection mConnection;

   /**
    * Default ctor.
    *
    * @param aConnection
    * @throws AeXMLDBException
    */
   public AeTxManagerXMLDBConnection(IAeXMLDBConnection aConnection) throws AeXMLDBException
   {
      setConnection(aConnection);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#getNativeConnection()
    */
   public Object getNativeConnection()
   {
      return getConnection().getNativeConnection();
   }

   /**
    * Overrides method to do nothing.
    *
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#close()
    */
   public void close()
   {
      // ignore!
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#rollback()
    */
   public void rollback() throws AeXMLDBException
   {
      throw new AeXMLDBException(AeMessages.getString("AeXMLDBTransaction.ERROR_CANNOT_NEST_TRANSACTIONS")); //$NON-NLS-1$)
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#commit()
    */
   public void commit() throws AeXMLDBException
   {
      throw new AeXMLDBException(AeMessages.getString("AeXMLDBTransaction.ERROR_CANNOT_NEST_TRANSACTIONS")); //$NON-NLS-1$)
   }

   /**
    * Rolls back the underlying connection.
    * @throws AeXMLDBException
    */
   public void reallyRollback() throws AeXMLDBException
   {
      getConnection().rollback();
   }

   /**
    * Commits the underlying connection.
    * @throws AeXMLDBException
    */
   public void reallyCommit() throws AeXMLDBException
   {
      getConnection().commit();
   }

   /**
    * @see com.softwareag.xmldb.db.api.connection.IAeXMLDBConnection#close()
    */
   public void reallyClose()
   {
      getConnection().close();
   }

   /**
    * @return Returns the connection.
    */
   protected IAeXMLDBConnection getConnection()
   {
      return mConnection;
   }

   /**
    * @param aConnection the connection to set
    */
   protected void setConnection(IAeXMLDBConnection aConnection)
   {
      mConnection = aConnection;
   }
}
