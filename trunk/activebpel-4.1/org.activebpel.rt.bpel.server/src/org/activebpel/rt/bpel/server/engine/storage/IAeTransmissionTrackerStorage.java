//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/IAeTransmissionTrackerStorage.java,v 1.3 2007/04/03 20:54:3
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

import org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry;
import org.activebpel.rt.util.AeLongSet;

/**
 * Interface for the transmission, receive manager storage layer.
 */
public interface IAeTransmissionTrackerStorage extends IAeStorage
{
   /**
    * Returns the next available transmission id. The id is unique across the engine or cluster.
    * @return next transmission id.
    * @throws AeStorageException
    */
   public long getNextTransmissionId() throws AeStorageException;
   
   /**
    * Adds a new entry.
    * @param aEntry
    * @throws AeStorageException
    */
   public void add(AeTransmissionTrackerEntry aEntry) throws AeStorageException;
   
   /**
    * Returns the entry given id.
    * @param aTransmissionId
    * @return entry or <code>null</code> if not found.
    * @throws AeStorageException
    */
   public AeTransmissionTrackerEntry get(long aTransmissionId) throws AeStorageException;
   
   /**
    * Updates entry state and or message id.
    * @param aEntry
    * @throws AeStorageException
    */
   public void update(AeTransmissionTrackerEntry aEntry) throws AeStorageException;
   
   /**
    * Removes entry.
    * @param aTransmissionId
    * @throws AeStorageException
    */
   public void remove(long aTransmissionId) throws AeStorageException;
   
   /**
    * Removes entries given the set of transmission ids.
    * @param aTransmissionIds
    * @throws AeStorageException
    */
   public void remove(AeLongSet aTransmissionIds) throws AeStorageException;
}
