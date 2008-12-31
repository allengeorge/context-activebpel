//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/AeLocalTxTaminoConnection.java,v 1.3 2007/08/17 00:57:3
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

import com.softwareag.tamino.db.api.accessor.TAccessLocation;
import com.softwareag.tamino.db.api.accessor.TAdministrationAccessor;
import com.softwareag.tamino.db.api.accessor.TNonXMLObjectAccessor;
import com.softwareag.tamino.db.api.accessor.TPreparedXQuery;
import com.softwareag.tamino.db.api.accessor.TSchemaDefinition2Accessor;
import com.softwareag.tamino.db.api.accessor.TSchemaDefinition3Accessor;
import com.softwareag.tamino.db.api.accessor.TStreamAccessor;
import com.softwareag.tamino.db.api.accessor.TSystemAccessor;
import com.softwareag.tamino.db.api.accessor.TXMLObjectAccessor;
import com.softwareag.tamino.db.api.connection.TConnection;
import com.softwareag.tamino.db.api.connection.TConnectionCloseException;
import com.softwareag.tamino.db.api.connection.TGlobalTransaction;
import com.softwareag.tamino.db.api.connection.TGlobalTransactionSpecifier;
import com.softwareag.tamino.db.api.connection.TIsolationDegree;
import com.softwareag.tamino.db.api.connection.TLocalTransaction;
import com.softwareag.tamino.db.api.connection.TLockMode;
import com.softwareag.tamino.db.api.connection.TLockwaitMode;
import com.softwareag.tamino.db.api.connection.TTransaction;
import com.softwareag.tamino.db.api.connection.TTransactionException;
import com.softwareag.tamino.db.api.connection.TTransactionModeChangeException;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.tamino.AeMessages;

/**
 * A delegate version of a tamino connection. This class simply adds "commit" and "rollback" methods for
 * transaction control.
 */
public class AeLocalTxTaminoConnection implements IAeTaminoLocalTxConnection
{
   /** The delegate connection. */
   private TConnection mConnection;
   /** The connection's transaction control object. */
   private TLocalTransaction mLocalTx;
   /** Debug flag to indicate that the connection was closed. */
   private boolean mDebugClosed = false;
   /** Debug flag to indicate that the connection was committed. */
   private boolean mDebugCommitted = false;
   /** Debug flag to indicate that the connection was rolledback. */
   private boolean mDebugRolledback = false;

   /**
    * Creates a local transaction (non auto-commit) connection. Users of this class must call commit or
    * rollback. If commit is not called explicitely, it will be called when the connection is closed.
    *
    * @param aConnection
    * @throws TTransactionModeChangeException
    */
   public AeLocalTxTaminoConnection(TConnection aConnection) throws AeXMLDBException
   {
      setConnection(aConnection);
      try
      {
         setLocalTx(getConnection().useLocalTransactionMode());
      }
      catch (TTransactionModeChangeException tmce)
      {
         throw new AeXMLDBException(AeMessages.getString("AeLocalTxTaminoConnection.ERROR_SETTING_LOCALTX"), tmce); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.IAeTaminoLocalTxConnection#commit()
    */
   public void commit() throws AeXMLDBException
   {
      try
      {
         getLocalTx().commit();
         mDebugCommitted = true;
      }
      catch (TTransactionException te)
      {
         throw new AeXMLDBException(AeMessages.getString("AeLocalTxTaminoConnection.ERROR_COMMITTING_TX"), te); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.IAeTaminoLocalTxConnection#rollback()
    */
   public void rollback() throws AeXMLDBException
   {
      try
      {
         getLocalTx().rollback();
         mDebugRolledback = true;
      }
      catch (TTransactionException te)
      {
         throw new AeXMLDBException(AeMessages.getString("AeLocalTxTaminoConnection.ERROR_ROLLING_BACK_TX"), te); //$NON-NLS-1$
      }
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#newStreamAccessor(com.softwareag.tamino.db.api.accessor.TAccessLocation)
    */
   public TStreamAccessor newStreamAccessor(TAccessLocation aAccessLoc)
   {
      return getConnection().newStreamAccessor(aAccessLoc);
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#newNonXMLObjectAccessor(com.softwareag.tamino.db.api.accessor.TAccessLocation)
    */
   public TNonXMLObjectAccessor newNonXMLObjectAccessor(TAccessLocation aAccessLoc)
   {
      return getConnection().newNonXMLObjectAccessor(aAccessLoc);
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#newXMLObjectAccessor(com.softwareag.tamino.db.api.accessor.TAccessLocation,
    *      java.lang.Object)
    */
   public TXMLObjectAccessor newXMLObjectAccessor(TAccessLocation aAccessLoc, Object aObj)
   {
      return getConnection().newXMLObjectAccessor(aAccessLoc, aObj);
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#newSchemaDefinition2Accessor(java.lang.Object)
    */
   public TSchemaDefinition2Accessor newSchemaDefinition2Accessor(Object aObj)
   {
      return getConnection().newSchemaDefinition2Accessor(aObj);
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#newSchemaDefinition3Accessor(java.lang.Object)
    */
   public TSchemaDefinition3Accessor newSchemaDefinition3Accessor(Object aObj)
   {
      return getConnection().newSchemaDefinition3Accessor(aObj);
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#newSystemAccessor()
    */
   public TSystemAccessor newSystemAccessor()
   {
      return getConnection().newSystemAccessor();
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#newAdministrationAccessor()
    */
   public TAdministrationAccessor newAdministrationAccessor()
   {
      return getConnection().newAdministrationAccessor();
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#useAutoCommitMode()
    */
   public void useAutoCommitMode() throws TTransactionModeChangeException
   {
      throw new UnsupportedOperationException(AeMessages.getString("AeLocalTxTaminoConnection.AUTO_COMMIT_NOT_SUPPORTED_ERROR")); //$NON-NLS-1$
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#usesAutoCommitMode()
    */
   public boolean usesAutoCommitMode()
   {
      return false;
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#useLocalTransactionMode()
    */
   public TLocalTransaction useLocalTransactionMode() throws TTransactionModeChangeException
   {
      return getLocalTx();
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#usesLocalTransactionMode()
    */
   public boolean usesLocalTransactionMode()
   {
      return true;
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#getTransaction()
    */
   public TTransaction getTransaction()
   {
      return getLocalTx();
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#reset()
    */
   public void reset() throws TTransactionModeChangeException
   {
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#setLockwaitMode(com.softwareag.tamino.db.api.connection.TLockwaitMode)
    */
   public void setLockwaitMode(TLockwaitMode aLockwaitMode)
   {
      getConnection().setLockwaitMode(aLockwaitMode);
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#getLockwaitMode()
    */
   public TLockwaitMode getLockwaitMode()
   {
      return getConnection().getLockwaitMode();
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#setIsolationDegree(com.softwareag.tamino.db.api.connection.TIsolationDegree)
    */
   public void setIsolationDegree(TIsolationDegree aIsoDegree)
   {
      getConnection().setIsolationDegree(aIsoDegree);
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#getIsolationDegree()
    */
   public TIsolationDegree getIsolationDegree()
   {
      return getConnection().getIsolationDegree();
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#setLockMode(com.softwareag.tamino.db.api.connection.TLockMode)
    */
   public void setLockMode(TLockMode aLockMode)
   {
      getConnection().setLockMode(aLockMode);
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#getLockMode()
    */
   public TLockMode getLockMode()
   {
      return getConnection().getLockMode();
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#setNonActivityTimeout(long)
    */
   public void setNonActivityTimeout(long aLong)
   {
      getConnection().setNonActivityTimeout(aLong);
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#getNonActivityTimeout()
    */
   public long getNonActivityTimeout()
   {
      return getConnection().getNonActivityTimeout();
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#setMaximumTransactionDuration(long)
    */
   public void setMaximumTransactionDuration(long aDuration)
   {
      getConnection().setMaximumTransactionDuration(aDuration);
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#getMaximumTransactionDuration()
    */
   public long getMaximumTransactionDuration()
   {
      return getConnection().getMaximumTransactionDuration();
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#close()
    */
   public void close() throws TConnectionCloseException
   {
      AeTaminoUtil.close(getConnection());
      mDebugClosed = true;
      
      if (!mDebugCommitted && !mDebugRolledback)
      {
         throw new RuntimeException("Local TX Tamino connection closed without being committed or rolled-back."); //$NON-NLS-1$
      }
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#isClosed()
    */
   public boolean isClosed()
   {
      return getConnection().isClosed();
   }

   /**
    * @return Returns the connection.
    */
   protected TConnection getConnection()
   {
      return mConnection;
   }

   /**
    * @param aConnection The connection to set.
    */
   protected void setConnection(TConnection aConnection)
   {
      mConnection = aConnection;
   }

   /**
    * @return Returns the localTx.
    */
   protected TLocalTransaction getLocalTx()
   {
      return mLocalTx;
   }

   /**
    * @param aLocalTx The localTx to set.
    */
   protected void setLocalTx(TLocalTransaction aLocalTx)
   {
      mLocalTx = aLocalTx;
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#useGlobalTransactionMode(com.softwareag.tamino.db.api.connection.TGlobalTransactionSpecifier)
    */
   public TGlobalTransaction useGlobalTransactionMode(TGlobalTransactionSpecifier aGlobalTxSpec) throws TTransactionModeChangeException
   {
      return getConnection().useGlobalTransactionMode(aGlobalTxSpec);
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#usesGlobalTransactionMode()
    */
   public boolean usesGlobalTransactionMode()
   {
      return getConnection().usesGlobalTransactionMode();
   }

   /**
    * @see com.softwareag.tamino.db.api.connection.TConnection#prepareQuery(java.lang.String)
    */
   public TPreparedXQuery prepareQuery(String aQuery)
   {
      return getConnection().prepareQuery(aQuery);
   }

   /**
    * Overrides method to print status of connection debug flags if the connection was not closed, committed or rolled back.
    * @see java.lang.Object#finalize()
    */
   protected void finalize() throws Throwable
   {
      if (!mDebugClosed)
      {
         AeException.logWarning("*** TAMINO CONNECTION NOT CLOSED ***"); //$NON-NLS-1$
      }
   }

}
