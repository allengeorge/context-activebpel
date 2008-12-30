// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/urn/AeXMLDBURNStorageProvider.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.urn;

import java.util.LinkedHashMap;
import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeURNStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeAbstractXMLDBStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBConfig;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.urn.handlers.AeURNMappingResponseHandler;

/**
 * A XMLDB implementation of a URN storage provider.
 */
public class AeXMLDBURNStorageProvider extends AeAbstractXMLDBStorageProvider implements
      IAeURNStorageProvider
{
   /** The prefix into the xmldb config that this storage object uses. */
   protected static final String CONFIG_PREFIX = "URNStorage"; //$NON-NLS-1$
   /** A response handler that returns a urn->url Map. */
   private static final AeURNMappingResponseHandler URN_MAPPING_HANDLER = new AeURNMappingResponseHandler();

   /**
    * Constructs the XMLDB URN storage with the given XMLDB config.
    * 
    * @param aConfig
    * @param aStorageImpl
    */
   public AeXMLDBURNStorageProvider(AeXMLDBConfig aConfig, IAeXMLDBStorageImpl aStorageImpl)
   {
      super(aConfig, CONFIG_PREFIX, aStorageImpl);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeURNStorageProvider#getMappings()
    */
   public Map getMappings() throws AeStorageException
   {
      return (Map) query(IAeURNConfigKeys.GET_MAPPINGS, URN_MAPPING_HANDLER);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeURNStorageProvider#addMapping(java.lang.String, java.lang.String, org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection)
    */
   public void addMapping(String aURN, String aURL, IAeStorageConnection aConnection) throws AeStorageException
   {
      LinkedHashMap params = new LinkedHashMap(2);
      params.put(IAeURNElements.URN, aURN);
      params.put(IAeURNElements.URL, aURL);
      insertDocument(IAeURNConfigKeys.INSERT_MAPPING, params, getXMLDBConnection(aConnection));
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.providers.IAeURNStorageProvider#removeMapping(java.lang.String, org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection)
    */
   public void removeMapping(String aURN, IAeStorageConnection aConnection) throws AeStorageException
   {
      updateDocuments(IAeURNConfigKeys.DELETE_MAPPING, new Object[] { aURN }, getXMLDBConnection(aConnection));
   }
}
