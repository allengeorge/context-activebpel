// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/IAeStorageProviderFactory.java,v 1.6 2007/04/23 23:38:5
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
package org.activebpel.rt.bpel.server.engine.storage;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeAttachmentStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeCoordinationStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeProcessStateStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeQueueStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeURNStorageProvider;

/**
 * Defines methods needed to create storage providers (Database i/o classes).
 */
public interface IAeStorageProviderFactory
{
   /**
    * Returns the DB config that the storage provider will use.
    */
   public AeStorageConfig getDBConfig();

   /**
    * Called after the storage provider factory has been created.
    * 
    * @throws AeException
    */
   public void init() throws AeException;

   /**
    * Creates the queue storage provider.
    */
   public IAeQueueStorageProvider createQueueStorageProvider();

   /**
    * Creates the process state storage object.
    */
   public IAeProcessStateStorageProvider createProcessStateStorageProvider();
   /**
    * Creates the coordination storage object.
    */
   public IAeCoordinationStorageProvider createCoordinationStorageProvider();
   
   /**
    * Creates the URN storage object.
    */
   public IAeURNStorageProvider createURNStorageProvider();
   
   /**
    * Creates and returns the storage provider for the TransmissionTracker manager store.
    */
   public IAeTransmissionTrackerStorageProvider createTransmissionTrackerStorageProvider();
   
   /**
    * Creates and returns the storage provider for the attachment manager store.
    */
   public IAeAttachmentStorageProvider createAttachmentStorageProvider();
   
   /**
    * Creates a custom storage provider with the given name.
    * 
    * @param aProviderName
    */
   public IAeStorageProvider createCustomStorageProvider(String aProviderName);
}
