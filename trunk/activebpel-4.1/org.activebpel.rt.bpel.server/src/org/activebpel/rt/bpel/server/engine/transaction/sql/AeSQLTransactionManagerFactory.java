//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/transaction/sql/AeSQLTransactionManagerFactory.java,v 1.3 2006/05/24 23:16:3
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
package org.activebpel.rt.bpel.server.engine.transaction.sql;

import java.util.Map;

import org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManager;
import org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManagerFactory;

/**
 * Implements a transaction manager factory that constructs instances of
 * {@link org.activebpel.rt.bpel.server.engine.transaction.sql.AeSQLTransactionManager}.
 */
public class AeSQLTransactionManagerFactory implements IAeTransactionManagerFactory
{
   /**
    * Constructs transaction manager factory with the specified configuration.
    *
    * @param aConfigMap
    */
   public AeSQLTransactionManagerFactory(Map aConfigMap)
   {
      // No configuration required.
   }

   /**
    * Overrides method to return an instance of <code>AeSQLTransactionManager</code>.
    *
    * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManagerFactory#createTransactionManager()
    */
   public IAeTransactionManager createTransactionManager()
   {
      return new AeSQLTransactionManager();
   }
}
