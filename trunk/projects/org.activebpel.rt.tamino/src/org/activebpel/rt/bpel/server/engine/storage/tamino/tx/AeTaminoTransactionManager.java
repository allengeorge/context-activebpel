//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/tx/AeTaminoTransactionManager.java,v 1.4 2007/08/17 00:57:3
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
package org.activebpel.rt.bpel.server.engine.storage.tamino.tx;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.tx.AeXMLDBTransactionManager;
import org.activebpel.rt.bpel.server.engine.transaction.AeTransactionException;
import org.activebpel.rt.bpel.server.engine.transaction.IAeTransaction;

/**
 * Transaction manager implementation for the Tamino storage.
 */
public class AeTaminoTransactionManager extends AeXMLDBTransactionManager
{
   /**
    * Overrides method to return a Tamino implementation.
    * 
    * @see org.activebpel.rt.bpel.server.engine.transaction.AeTransactionManager#createTransaction()
    */
   protected IAeTransaction createTransaction() throws AeTransactionException
   {
      return new AeTaminoTransaction();
   }
}
