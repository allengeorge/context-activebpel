// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.exist/src/org/activebpel/rt/bpel/server/engine/storage/exist/AeExistStorageConnection.java,v 1.2 2007/08/17 00:59:5
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

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection;

/**
 * A Exist version of the DB Connection wrapper interface.
 */
public class AeExistStorageConnection implements IAeStorageConnection
{
   /** The Exist Connection. */
   private IAeExistConnection mConnection;

   /**
    * Constructs a SQL DB Connection that will delegate to the given SQL Connection.
    * 
    * @param aConnection
    */
   protected AeExistStorageConnection(IAeExistConnection aConnection)
   {
      setConnection(aConnection);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection#commit()
    */
   public void commit() throws AeStorageException
   {
      getConnection().commit();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection#rollback()
    */
   public void rollback() throws AeStorageException
   {
      getConnection().rollback();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection#close()
    */
   public void close()
   {
      getConnection().close();
   }

   /**
    * @return Returns the connection.
    */
   protected IAeExistConnection getConnection()
   {
      return mConnection;
   }

   /**
    * @param aConnection The connection to set.
    */
   protected void setConnection(IAeExistConnection aConnection)
   {
      mConnection = aConnection;
   }
}
