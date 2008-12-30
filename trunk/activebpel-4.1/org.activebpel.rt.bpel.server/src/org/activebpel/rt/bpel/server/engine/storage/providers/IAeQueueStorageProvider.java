// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/providers/IAeQueueStorageProvider.java,v 1.6 2007/05/24 01:06:4
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

import java.util.Date;
import java.util.List;

import org.activebpel.rt.bpel.impl.list.AeAlarmFilter;
import org.activebpel.rt.bpel.impl.list.AeAlarmListResult;
import org.activebpel.rt.bpel.impl.list.AeMessageReceiverFilter;
import org.activebpel.rt.bpel.impl.list.AeMessageReceiverListResult;
import org.activebpel.rt.bpel.impl.queue.AeInboundReceive;
import org.activebpel.rt.bpel.impl.queue.AeMessageReceiver;
import org.activebpel.rt.bpel.server.engine.storage.AePersistedMessageReceiver;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;

/**
 * A queue storage delegate. This interface defines methods that the delegating queue storage will call in
 * order to store/read date in the underlying database.
 */
public interface IAeQueueStorageProvider extends IAeStorageProvider
{
   /**
    * Stores a single receive object in the database.
    * 
    * @param aReceiveObject
    * @throws AeStorageException
    */
   public void storeReceiveObject(AeMessageReceiver aReceiveObject) throws AeStorageException;

   /**
    * Stores a single alarm in the database.
    * 
    * @param aProcessId
    * @param aLocationPathId
    * @param aGroupId
    * @param aAlarmId
    * @param aDeadline
    * @throws AeStorageException
    */
   public void storeAlarm(long aProcessId, int aLocationPathId, int aGroupId, int aAlarmId, Date aDeadline)
         throws AeStorageException;

   /**
    * Removes all receive objects in the given group. This removes all rows in the receive table by their
    * GroupID.
    * 
    * @param aProcessId
    * @param aGroupId
    * @param aLocationPathId
    * @param aConnection
    * @throws AeStorageException
    */
   public int removeReceiveObjectsInGroup(long aProcessId, int aGroupId, int aLocationPathId,
         IAeStorageConnection aConnection) throws AeStorageException;

   /**
    * Removes a single receive object by its queued receive id.
    * 
    * @param aQueuedReceiveId
    * @throws AeStorageException
    */
   public boolean removeReceiveObjectById(int aQueuedReceiveId) throws AeStorageException;


   /**
    * Removes a single alarm from the database by its process ID and location ID.
    * 
    * @param aProcessId
    * @param aLocationPathId
    * @param aAlarmId
    * @param aConnection
    * @throws AeStorageException
    */ 
   public boolean removeAlarm(long aProcessId, int aLocationPathId, int aAlarmId,  IAeStorageConnection aConnection)
         throws AeStorageException;

   /**
    * Removes all alarms in the given group. This will remove all rows in the alarm table by their GroupID.
    * 
    * @param aProcessId
    * @param aGroupId
    * @param aConnection
    * @throws AeStorageException
    */
   public int removeAlarmsInGroup(long aProcessId, int aGroupId, IAeStorageConnection aConnection)
         throws AeStorageException;

   /**
    * Gets a single receive object from the DB, identified by process ID and location ID.
    * 
    * @param aProcessId
    * @param aMessageReceiverPathId
    * @throws Exception
    */
   public AePersistedMessageReceiver getReceiveObject(long aProcessId, int aMessageReceiverPathId)
         throws AeStorageException;

   /**
    * Gets a list of receives that match the given match and correlation hashes.
    * 
    * @param aMatchHash
    * @param aCorrelatesHash
    * @throws AeStorageException
    */
   public List getReceives(int aMatchHash, int aCorrelatesHash) throws AeStorageException;

   /**
    * Gets a list of message receivers that match the given filter.
    * 
    * @param aFilter
    */
   public AeMessageReceiverListResult getQueuedMessageReceivers(AeMessageReceiverFilter aFilter)
         throws AeStorageException;

   /**
    * Gets a list of all alarms in the database.
    */
   public List getAlarms() throws AeStorageException;

   /**
    * Gets a list of all alarm in the database that match the given filter.
    * 
    * @param aFilter
    * @throws AeStorageException
    */
   public AeAlarmListResult getAlarms(AeAlarmFilter aFilter) throws AeStorageException;

   /**
    * Journals an inbound receive by putting a record of it in the database and associating attachments to the process.
    * 
    * @param aProcessId
    * @param aLocationId
    * @param aInboundReceive
    * @param aConnection
    * @throws AeStorageException
    */
   public long journalInboundReceive(long aProcessId, int aLocationId, AeInboundReceive aInboundReceive,
         IAeStorageConnection aConnection) throws AeStorageException;

   /**
    * Journals an alarm in the database.
    * 
    * @param aProcessId
    * @param aGroupId
    * @param aLocationPathId
    * @param aAlarmId
    * @param aConnection
    * @throws AeStorageException
    */
   public long journalAlarm(long aProcessId, int aGroupId, int aLocationPathId, int aAlarmId, IAeStorageConnection aConnection)
         throws AeStorageException;

   /**
    * Increments the hash collision counter.
    */
   public void incrementHashCollisionCounter();
}
