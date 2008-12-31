//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeSQLTransmissionTrackerStorageProvider.java,v 1.4 2007/04/03 20:54:3
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
package org.activebpel.rt.bpel.server.engine.storage.sql;

import java.sql.Connection;
import java.util.Iterator;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.storage.AeCounter;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.sql.handlers.AeTransmissionTrackerResultSetHandler;
import org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry;
import org.activebpel.rt.util.AeCloser;
import org.activebpel.rt.util.AeLongSet;

/**
 * SQL implementation of the storage provider for the transmission - receive manager.
 */
public class AeSQLTransmissionTrackerStorageProvider extends AeAbstractSQLStorageProvider implements
      IAeTransmissionTrackerStorageProvider
{
   /** Config prefix. */
   protected static final String TRANSMISSION_TRACKER_STORAGE_PREFIX = "TransmissionTrackerStorage."; //$NON-NLS-1$
   
   /**
    * Default ctor.
    * @param aConfig SQL configuration.
    */
   public AeSQLTransmissionTrackerStorageProvider(AeSQLConfig aConfig)
   {
      super(TRANSMISSION_TRACKER_STORAGE_PREFIX, aConfig);
   }

   /**  
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider#getNextTransmissionId()
    */
   public long getNextTransmissionId() throws AeStorageException
   {
      return AeCounter.TRANSMISSION_ID_COUNTER.getNextValue();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider#add(org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry)
    */
   public void add(AeTransmissionTrackerEntry aEntry) throws AeStorageException
   { 
      Object[] params = new Object[] {
            new Long(aEntry.getTransmissionId()),
            new Integer(aEntry.getState()),
            getStringOrSqlNullVarchar( aEntry.getMessageId() )
      };
      // note: when calling update, we also pass the aClose=true to close the connection in case the connection is not from the TxManager.
      Connection conn = null;
      try
      {
         conn = getTransactionConnection();
         update(conn, IAeTransmissionTrackerSQLKeys.INSERT_ENTRY, params);
      }
      catch(Throwable t)
      {
         AeException.logError(t);
         throw new AeStorageException(t);
      }
      finally
      {
         AeCloser.close(conn);
      }      
      
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider#get(long)
    */
   public AeTransmissionTrackerEntry get(long aTransmissionId) throws AeStorageException
   {
      Object param = new Long(aTransmissionId);
      return (AeTransmissionTrackerEntry) query(IAeTransmissionTrackerSQLKeys.GET_ENTRY, param, new AeTransmissionTrackerResultSetHandler());
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider#update(org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry)
    */
   public void update(AeTransmissionTrackerEntry aEntry) throws AeStorageException
   {
      // Note: This method updates both - message id and state.
      Object[] params = new Object[] {
            new Integer(aEntry.getState()),
            getStringOrSqlNullVarchar( aEntry.getMessageId() ),
            new Long(aEntry.getTransmissionId())
      };
      // note: when calling update, we also pass the aClose=true to close the connection in case the connection is not from the TxManager.      
      Connection conn = null;
      try
      {
         conn = getTransactionConnection();
         update(conn, IAeTransmissionTrackerSQLKeys.UPDATE_ENTRY, params);
      }
      finally
      {
         AeCloser.close(conn);
      }            
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider#remove(org.activebpel.rt.util.AeLongSet)
    */
   public void remove(AeLongSet aTransmissionIds) throws AeStorageException
   {     
      if (!aTransmissionIds.isEmpty())
      {                  
         Connection conn = null;
         // note: when calling update, we also pass the aClose=true to close the connection in case the connection is not from the TxManager.
         try
         {
            Iterator it = aTransmissionIds.iterator();
            conn = getTransactionConnection();         
            while (it.hasNext()) 
            {
               Object[] params = new Object[] {(Long) it.next() };
               update(conn, IAeTransmissionTrackerSQLKeys.DELETE_ENTRY, params);
            }// while
         }
         finally
         {
            AeCloser.close(conn);
         }                     
      }
   }
}
