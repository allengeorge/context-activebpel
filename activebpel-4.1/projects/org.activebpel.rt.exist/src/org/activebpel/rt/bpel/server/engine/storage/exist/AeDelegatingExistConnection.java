// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.exist/src/org/activebpel/rt/bpel/server/engine/storage/exist/AeDelegatingExistConnection.java,v 1.2 2007/08/17 00:59:5
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
package org.activebpel.rt.bpel.server.engine.storage.exist;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBResponseHandler;


/**
 * Delegating implementation of an exist connection - delegates all calls
 * to the delegate connection.
 */
public abstract class AeDelegatingExistConnection implements IAeExistConnection
{
   /** The delegate connection. */
   private IAeExistConnection mConnection;

   /**
    * C'tor.
    *
    * @param aConnection
    * @throws AeXMLDBException
    */
   public AeDelegatingExistConnection(IAeExistConnection aConnection)
   {
      setConnection(aConnection);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistConnection#insertDocument(java.lang.String)
    */
   public long insertDocument(String aXMLContent) throws AeXMLDBException
   {
      return getConnection().insertDocument(aXMLContent);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistConnection#xquery(java.lang.String, org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBResponseHandler)
    */
   public Object xquery(String aQuery, IAeXMLDBResponseHandler aResponseHandler) throws AeXMLDBException
   {
      return getConnection().xquery(aQuery, aResponseHandler);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistConnection#updateDocuments(java.lang.String)
    */
   public int updateDocuments(String aQuery) throws AeXMLDBException
   {
      return getConnection().updateDocuments(aQuery);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistConnection#deleteDocuments(java.lang.String)
    */
   public int deleteDocuments(String aQuery) throws AeXMLDBException
   {
      return getConnection().deleteDocuments(aQuery);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistConnection#close()
    */
   public void close()
   {
      getConnection().close();
   }

   /**
    * Overrides method to do nothing.
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.tx.IAeExistLocalTxConnection#rollback()
    */
   public void rollback() throws AeXMLDBException
   {
      getConnection().rollback();
   }

   /**
    * Overrides method to do nothing.
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.tx.IAeExistLocalTxConnection#commit()
    */
   public void commit() throws AeXMLDBException
   {
      getConnection().commit();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#getNativeConnection()
    */
   public Object getNativeConnection()
   {
      return getConnection().getNativeConnection();
   }

   /**
    * @return Returns the connection.
    */
   protected IAeExistConnection getConnection()
   {
      return mConnection;
   }

   /**
    * @param aConnection the connection to set
    */
   protected void setConnection(IAeExistConnection aConnection)
   {
      mConnection = aConnection;
   }
}
