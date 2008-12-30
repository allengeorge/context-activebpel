//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/transreceive/AeXMLDBTransmissionTrackerStorageProvider.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.transreceive;

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeAbstractXMLDBStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBConfig;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBUtil;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.transreceive.handlers.AeTransmissionTrackerResponseHandler;
import org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry;
import org.activebpel.rt.util.AeLongSet;

/**
 * TransmissionTracker storage provider implementation for the XMLDB.
 *
 */
public class AeXMLDBTransmissionTrackerStorageProvider extends AeAbstractXMLDBStorageProvider implements
      IAeTransmissionTrackerStorageProvider
{
   /** The prefix into the xmldb config that this storage object uses. */
   protected static final String CONFIG_PREFIX = "TransmissionTrackerStorage"; //$NON-NLS-1$

   /** The next transmission id. */
   private long mNextTransmissionId = System.currentTimeMillis();

   /** A XMLDB response handler that returns a AeTransmissionTrackerEntry object. */
   private static final IAeXMLDBResponseHandler TRANSMISSION_TRACKER_ENTRY_RESPONSE_HANDLER = new AeTransmissionTrackerResponseHandler();

   /**
    * Constructs the provider given the configuration.
    * 
    * @param aConfig
    * @param aStorageImpl
    */
   public AeXMLDBTransmissionTrackerStorageProvider(AeXMLDBConfig aConfig, IAeXMLDBStorageImpl aStorageImpl)
   {
      super(aConfig, CONFIG_PREFIX, aStorageImpl);
   }


   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider#getNextTransmissionId()
    */
   public long getNextTransmissionId() throws AeStorageException
   {
      return mNextTransmissionId++;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider#add(org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry)
    */
   public void add(AeTransmissionTrackerEntry aEntry) throws AeStorageException
   {
      // eg: insert (?TransmissionId?, ?State?, ?MessageId?)
      LinkedHashMap params = new LinkedHashMap();
      params.put(IAeTransmissionTrackerElements.TRANSMISSION_ID, new Long(aEntry.getTransmissionId()));
      params.put(IAeTransmissionTrackerElements.STATE, new Integer(aEntry.getState()));
      params.put(IAeTransmissionTrackerElements.MESSAGE_ID, AeXMLDBUtil.getStringOrNull(aEntry.getMessageId()) );

      IAeXMLDBConnection txConn = null;
      try
      {
         txConn = getXMLDBTransactionManagerConnection();
         insertDocument(IAeTransmissionTrackerConfigKeys.INSERT_ENTRY, params, txConn);
      }
      finally
      {
         txConn.close();
      }

   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider#get(long)
    */
   public AeTransmissionTrackerEntry get(long aTransmissionId) throws AeStorageException
   {
      // eg: Select (TransmissionId, State, MessageId) where TransmissionId = ?TransmissionId?
      Object[] params = { new Long(aTransmissionId) };
      IAeXMLDBConnection txConn = null;
      try
      {
         txConn = getXMLDBTransactionManagerConnection();
         return (AeTransmissionTrackerEntry) query(IAeTransmissionTrackerConfigKeys.GET_ENTRY, params,
               TRANSMISSION_TRACKER_ENTRY_RESPONSE_HANDLER, txConn);
      }
      finally
      {
         txConn.close();
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider#update(org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry)
    */
   public void update(AeTransmissionTrackerEntry aEntry) throws AeStorageException
   {
      // eg: update (?State?, ?MessageId?) where TransmissionId = ?TransmissionId?
      Object[] params = {
            new Integer(aEntry.getState()),
            AeXMLDBUtil.getStringOrNull(aEntry.getMessageId()),
            new Long(aEntry.getTransmissionId())
      };
      IAeXMLDBConnection txConn = null;
      try
      {
         txConn = getXMLDBTransactionManagerConnection();
         updateDocuments(IAeTransmissionTrackerConfigKeys.UPDATE_ENTRY, params, txConn);
      }
      finally
      {
         txConn.close();
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider#remove(org.activebpel.rt.util.AeLongSet)
    */
   public void remove(AeLongSet aTransmissionIds) throws AeStorageException
   {
      if (!aTransmissionIds.isEmpty())
      {
         IAeXMLDBConnection txConn = null;
         try
         {
            Iterator it = aTransmissionIds.iterator();
            txConn = getXMLDBTransactionManagerConnection();
            while (it.hasNext())
            {
               Object[] params = new Object[] {(Long) it.next() };
               deleteDocuments(IAeTransmissionTrackerConfigKeys.DELETE_ENTRY, params, txConn);
            }// while
         }
         finally
         {
            txConn.close();
         }
      }
   }

}
