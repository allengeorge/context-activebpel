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

import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBConfig;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBStorageProviderFactory;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;

/**
 * A storage factory that creates Tamino versions of the store objects.
 */
public class AeTaminoStorageProviderFactory extends AeXMLDBStorageProviderFactory
{
   /**
    * Default constructor.
    */
   public AeTaminoStorageProviderFactory(Map aConfig) throws AeException
   {
      super(aConfig);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBStorageProviderFactory#createXMLDBConfig(java.util.Map)
    */
   protected AeXMLDBConfig createXMLDBConfig(Map aOverrideMap)
   {
      return new AeTaminoConfig(aOverrideMap);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBStorageProviderFactory#setDataSource(org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource)
    */
   protected void setDataSource(IAeXMLDBDataSource aDataSource)
   {
      AeTaminoDataSource.MAIN = aDataSource;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBStorageProviderFactory#createStorageImpl(org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource)
    */
   protected IAeXMLDBStorageImpl createStorageImpl(IAeXMLDBDataSource aDataSource)
   {
      return new AeTaminoStorageImpl(aDataSource);
   }
}
