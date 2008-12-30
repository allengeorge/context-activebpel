//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/tx/AeXMLDBTransaction.java,v 1.1 2007/08/17 00:40:5
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
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource;
import org.activebpel.rt.bpel.server.engine.transaction.AeTransactionException;
import org.activebpel.rt.xmldb.AeMessages;

/**
 * Transaction implementation for XMLDB.
 */
public abstract class AeXMLDBTransaction implements IAeXMLDBTransaction
{
   /** XMLDB connection that is managed by the transaction manager. */
   private AeTxManagerXMLDBConnection mTxConnection; 
   
   /** True if begin has been called and no commit or rollback. */
   private boolean mActive;

   /**
    * Default ctor
    */
   public AeXMLDBTransaction()
   {
   }

   /**
    * Gets the XMLDB DataSource.
    */
   protected abstract IAeXMLDBDataSource getXMLDBDataSource();
   
   /**  
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.tx.IAeXMLDBTransaction#getConnection()
    */
   public IAeXMLDBConnection getConnection() throws AeXMLDBException
   {
      return (IAeXMLDBConnection) getTxConnection();
   }

   /** 
    * @return Transaction manager controlled connection.
    */
   protected AeTxManagerXMLDBConnection getTxConnection() throws AeXMLDBException
   {
      if (mTxConnection == null)
      {
         AeTxManagerXMLDBConnection txConnection;
         try
         {
            IAeXMLDBConnection conn = getXMLDBDataSource().getNewConnection(false);
            txConnection = new AeTxManagerXMLDBConnection(conn);
         }
         catch (Exception e)
         {         
            throw new AeXMLDBException(AeMessages.getString("AeXMLDBTransaction.ERROR_GETTING_CONNECTION"), e); //$NON-NLS-1$
         }
         // Make this transaction active only if everything succeeds.
         setTxConnection(txConnection);   
      }
      return mTxConnection;
   }
   
   /**
    * @param aTxConnection The connection to set.
    */
   protected void setTxConnection(AeTxManagerXMLDBConnection aTxConnection)
   {
      mTxConnection = aTxConnection;
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransaction#isActive()
    */
   public boolean isActive()
   {
      return mActive;
   }   

   /**
    * Sets the active state to the passed boolean.
    */
   protected void setActive(boolean aActive)
   {
      mActive = aActive;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransaction#begin()
    */
   public void begin() throws AeTransactionException
   {
      if (isActive())
      {
         throw new AeTransactionException(AeMessages.getString("AeXMLDBTransaction.ERROR_CANNOT_NEST_TRANSACTIONS")); //$NON-NLS-1$
      }

      setActive(true);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransaction#commit()
    */
   public void commit() throws AeTransactionException
   {
      if (!isActive())
      {
         throw new AeTransactionException(AeMessages.getString("AeXMLDBTransaction.ERROR_NO_ACTIVE_TRANSACTION")); //$NON-NLS-1$
      }

      try
      {
         try
         {
            getTxConnection().reallyCommit();
         }
         finally
         {
            getTxConnection().reallyClose();
         }
      }
      catch (Exception e)
      {
         throw new AeTransactionException(AeMessages.getString("AeXMLDBTransaction.ERROR_COMMITING_TRANSACTION"), e); //$NON-NLS-1$
      }
      finally
      {
         // Make this transaction inactive, so that the next time a thread needs it, a new one is created.
         // (in case the thread pool provider did not clear the threadlocal variable in the tx manager.).
         setTxConnection(null);
         setActive(false);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.transaction.IAeTransaction#rollback()
    */
   public void rollback() throws AeTransactionException
   {
      if (!isActive())
      {
         throw new AeTransactionException(AeMessages.getString("AeXMLDBTransaction.ERROR_MISSING_TRANSACTION_DURING_ROLLBACK")); //$NON-NLS-1$
      }

      try
      {
         try
         {
            getTxConnection().reallyRollback();
         }
         finally
         {
            getTxConnection().reallyClose();
         }
      }
      catch (Exception e)
      {
         throw new AeTransactionException(AeMessages.getString("AeXMLDBTransaction.ERROR_ROLLING_BACK_TRANSACTION"), e); //$NON-NLS-1$
      }
      finally
      {
         // Make this transaction inactive.
         setTxConnection(null);
         setActive(false);
      }   
   }

}
