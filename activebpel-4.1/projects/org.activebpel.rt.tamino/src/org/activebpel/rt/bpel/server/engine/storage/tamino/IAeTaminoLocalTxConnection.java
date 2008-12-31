//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/IAeTaminoLocalTxConnection.java,v 1.1 2007/08/17 00:57:3
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

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;


/**
 * An interface defining methods required to impl a local tx (commit-able) Tamino
 * connection.
 */
public interface IAeTaminoLocalTxConnection extends TConnection
{
   /**
    * Commits the transaction.
    * 
    * @throws AeXMLDBException
    */
   public void commit() throws AeXMLDBException;

   /**
    * Rolls back the transaction.
    * 
    * @throws AeXMLDBException
    */
   public void rollback() throws AeXMLDBException;

}
