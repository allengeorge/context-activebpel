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
package org.activebpel.rt.bpel.server.engine.storage.providers;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;

/**
 * A storage connection interface.  This interface exists in order to have a common "face" to the
 * specific database (SQL vs Tamino) connection.  The delegating storages often make multiple calls
 * to their delegate that must all exist in the same transaction.  This connection object is what
 * is acquired and then used for that purpose.
 */
public interface IAeStorageConnection
{
   /**
    * Commits the transaction associated with this connection.
    * 
    * @throws AeStorageException
    */
   public void commit() throws AeStorageException;

   /**
    * Rolls back the transaction associated with this connection.
    * 
    * @throws AeStorageException
    */
   public void rollback() throws AeStorageException;
   
   /**
    * Closes the connection.
    */
   public void close();
}
