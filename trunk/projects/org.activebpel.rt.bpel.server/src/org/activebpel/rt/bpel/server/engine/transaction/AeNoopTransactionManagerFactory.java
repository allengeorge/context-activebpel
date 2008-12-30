//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/transaction/AeNoopTransactionManagerFactory.java,v 1.3 2006/05/24 23:16:3
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
package org.activebpel.rt.bpel.server.engine.transaction;

import java.util.Map;

/**
 * Implements a factory for a do-nothing transaction manager for non-persistent
 * engine configurations.
 */
public class AeNoopTransactionManagerFactory implements IAeTransactionManagerFactory
{
   /**
    * Constructs transaction manager factory with the specified configuration.
    *
    * @param aConfigMap
    */
   public AeNoopTransactionManagerFactory(Map aConfigMap)
   {
      // No configuration required.
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManagerFactory#createTransactionManager()
    */
   public IAeTransactionManager createTransactionManager()
   {
      return new AeNoopTransactionManager();
   }

   /**
    * Implements a do-nothing transaction for non-persistent engine
    * configurations.
    */
   private static class AeNoopTransaction implements IAeTransaction
   {
      /**
       * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransaction#begin()
       */
      public void begin() throws AeTransactionException
      {
      }

      /**
       * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransaction#commit()
       */
      public void commit() throws AeTransactionException
      {
      }

      /**
       * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransaction#isActive()
       */
      public boolean isActive()
      {
         return false;
      }

      /**
       * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransaction#rollback()
       */
      public void rollback() throws AeTransactionException
      {
      }
   }

   /**
    * Implements a do-nothing transaction manager for non-persistent engine
    * configurations.
    */
   private static class AeNoopTransactionManager implements IAeTransactionManager
   {
      /** Do-nothing transaction. */
      private IAeTransaction mNoopTransaction = new AeNoopTransaction();

      /**
       * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManager#begin()
       */
      public void begin() throws AeTransactionException
      {
      }

      /**
       * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManager#commit()
       */
      public void commit() throws AeTransactionException
      {
      }

      /**
       * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManager#getTransaction()
       */
      public IAeTransaction getTransaction() throws AeTransactionException
      {
         return mNoopTransaction;
      }

      /**
       * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManager#rollback()
       */
      public void rollback() throws AeTransactionException
      {
      }
   }
}

