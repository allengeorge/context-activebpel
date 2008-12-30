// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/IAeStorageFactory.java,v 1.8 2007/05/08 19:21:0
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

import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.impl.attachment.IAeAttachmentStorage;

/**
 * This interface defines an Active BPEL engine store factory. The storage factory is responsible for
 * creating storage objects for the generic persistent versions of the engine managers.
 */
public interface IAeStorageFactory
{
   /**
    * Sets the configuration information for this persistent store factory.
    * This method is guaranteed to be called prior to any other method in the
    * interface.  Implementers of this interface should save the passed-in
    * configuration map if there is any configuration information needed to
    * create the persistent stores.
    *
    * @param aConfig The engine configuration map.
    */
   public void setConfiguration(Map aConfig) throws AeException;

   /**
    * The init method is called after the store factory has been created.  The factory
    * should do any initialization of its internal values in the init method.
    *
    * @throws AeException
    */
   public void init() throws AeException;

   /**
    * Gets the queue storage.
    */
   public IAeQueueStorage getQueueStorage();

   /**
    * Gets the process state storage.
    */
   public IAeProcessStateStorage getProcessStateStorage();

   /**
    * Gets the coordination storage.
    */
   public IAeCoordinationStorage getCoordinationStorage();

   /**
    * Gets the URN storage.
    */
   public IAeURNStorage getURNStorage();

   /**
    * Returns persistent counter store.
    */
   public IAeCounterStore getCounterStore();

   /**
    * Returns persistent transmission manager store.
    */
   public IAeTransmissionTrackerStorage getTransmissionTrackerStorage();
     
   /**
    * Returns attachment storage. 
    */
   public IAeAttachmentStorage getAttachmentStorage();
   
   
   /**
    * Returns a custom storage object with the given name.
    * 
    * @param aStorageName
    */
   public IAeStorage getCustomStorage(String aStorageName);

}
