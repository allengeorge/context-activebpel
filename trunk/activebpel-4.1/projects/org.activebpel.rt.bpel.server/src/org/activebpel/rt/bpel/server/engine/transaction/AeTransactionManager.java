//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/transaction/AeTransactionManager.java,v 1.9 2007/09/07 20:52:1
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

import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Implements abstract base class for managing instances of
 * {@link org.activebpel.rt.bpel.server.engine.transaction.IAeTransaction}.
 */
public abstract class AeTransactionManager implements IAeTransactionManager
{
   /** Singleton instance. */
   private static IAeTransactionManager sInstance;

   /** Per-thread storage for transaction reference. */
   private ThreadLocal mTransactionThreadLocal = new ThreadLocal();

   /**
    * Protected constructor for singleton instance.
    */
   protected AeTransactionManager()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManager#begin()
    */
   public void begin() throws AeTransactionException
   {
      getTransaction().begin();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManager#commit()
    */
   public void commit() throws AeTransactionException
   {
      try
      {
         getTransaction().commit();
      }
      finally
      {
         setTransaction(null);
      }
   }

   /**
    * Returns a new transaction.
    */
   protected abstract IAeTransaction createTransaction() throws AeTransactionException;

   /**
    * Returns the singleton instance, constructing it if necessary.
    */
   public static IAeTransactionManager getInstance()
   {
      // fixme (double-check) remove in favor of inline init
      if (sInstance == null)
      {
         synchronized(AeTransactionManager.class)
         {
            if (sInstance == null)
            {
               sInstance = getTransactionManagerFactory().createTransactionManager();
            }
         }
      }
      return sInstance;
   }

   /**
    * Shuts down the transaction manager.
    */
   public static void shutdown()
   {
      sInstance = null;
   }

   /**
    * Overrides method to return transaction from thread local storage,
    * creating the transaction if necessary.
    *
    * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManager#getTransaction()
    */
   public IAeTransaction getTransaction() throws AeTransactionException
   {
      IAeTransaction transaction = (IAeTransaction) mTransactionThreadLocal.get();

      if (transaction == null)
      {
         // Construct a new transaction.
         transaction = createTransaction();
         setTransaction(transaction);
      }

      return transaction;
   }

   /**
    * Returns the singleton transaction manager factory.
    */
   protected static IAeTransactionManagerFactory getTransactionManagerFactory()
   {
      return AeEngineFactory.getTransactionManagerFactory();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManager#rollback()
    */
   public void rollback() throws AeTransactionException
   {
      try
      {
         getTransaction().rollback();
      }
      finally
      {
         setTransaction(null);
      }

   }

   /**
    * Sets the transaction for the current thread.
    */
   protected void setTransaction(IAeTransaction aTransaction)
   {
      mTransactionThreadLocal.set(aTransaction);
   }
}
