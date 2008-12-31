//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/AeTransmissionTrackerStorage.java,v 1.2 2006/07/25 17:37:4
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

import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageProvider;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeTransmissionTrackerStorageProvider;
import org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry;
import org.activebpel.rt.util.AeLongSet;

/**
 * A delegating implementation of a transmission/receive storage. 
 */
public class AeTransmissionTrackerStorage extends AeAbstractStorage implements IAeTransmissionTrackerStorage
{
   /**
    * Constructs the storage given the delegate provider.
    * @param aDelegate storage provider delegate.
    */
   public AeTransmissionTrackerStorage(IAeStorageProvider aDelegate)
   {
      super(aDelegate);
   }

   /**
    * Convenience method to get the storage provider cast to a TransmissionTracker storage provider.
    */
   protected IAeTransmissionTrackerStorageProvider getTransmissionTrackerStorageProvider()
   {
      return (IAeTransmissionTrackerStorageProvider) getProvider();
   }   
  
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeTransmissionTrackerStorage#getNextTransmissionId()
    */
   public long getNextTransmissionId() throws AeStorageException
   {
      return  getTransmissionTrackerStorageProvider().getNextTransmissionId();
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeTransmissionTrackerStorage#add(org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry)
    */
   public void add(AeTransmissionTrackerEntry aEntry) throws AeStorageException
   {   
      getTransmissionTrackerStorageProvider().add(aEntry);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeTransmissionTrackerStorage#get(long)
    */
   public AeTransmissionTrackerEntry get(long aTransmissionId) throws AeStorageException
   {
      return getTransmissionTrackerStorageProvider().get(aTransmissionId);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeTransmissionTrackerStorage#update(org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry)
    */
   public void update(AeTransmissionTrackerEntry aEntry) throws AeStorageException
   {     
      getTransmissionTrackerStorageProvider().update(aEntry);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeTransmissionTrackerStorage#remove(long)
    */
   public void remove(long aTransmissionId) throws AeStorageException
   {
      AeLongSet set = new AeLongSet();
      set.add(aTransmissionId);
      remove(set);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeTransmissionTrackerStorage#remove(org.activebpel.rt.util.AeLongSet)
    */
   public void remove(AeLongSet aTransmissionIds) throws AeStorageException
   {
      getTransmissionTrackerStorageProvider().remove(aTransmissionIds);
   }

}
