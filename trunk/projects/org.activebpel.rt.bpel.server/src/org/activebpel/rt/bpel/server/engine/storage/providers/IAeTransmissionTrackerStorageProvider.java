//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/providers/IAeTransmissionTrackerStorageProvider.java,v 1.3 2006/12/14 15:18:4
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
package org.activebpel.rt.bpel.server.engine.storage.providers;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry;
import org.activebpel.rt.util.AeLongSet;

/**
 * Defines the storage provider interface for managing transmission ids.
 */
public interface IAeTransmissionTrackerStorageProvider extends IAeStorageProvider
{
   /**
    * Returns the next (unique) id. 
    * @return next transmission id.
    * @throws AeStorageException
    */
   public long getNextTransmissionId() throws AeStorageException;
   
   /**
    * Adds the given entry to the storage.
    * @param aEntry
    * @throws AeStorageException
    */
   public void add(AeTransmissionTrackerEntry aEntry) throws AeStorageException;
   
   /**
    * Retrieves the entry given the entry id.
    * @param aTransmissionId
    * @return entry or <code>null</code> if not found.
    * @throws AeStorageException
    */
   public AeTransmissionTrackerEntry get(long aTransmissionId) throws AeStorageException;
   
   /**
    * Updates the state and or message id.
    * @param aEntry
    * @throws AeStorageException
    */
   public void update(AeTransmissionTrackerEntry aEntry) throws AeStorageException;
   
   /**
    * Deletes a collection of entries.
    * @param aTransmissionIds
    * @throws AeStorageException
    */
   public void remove(AeLongSet aTransmissionIds) throws AeStorageException;
}
