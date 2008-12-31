// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/process/IAeProcessStateWriter.java,v 1.8 2006/11/08 18:18:1
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
package org.activebpel.rt.bpel.server.engine.process;

import java.util.Map;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.impl.queue.AeInboundReceive;
import org.activebpel.rt.bpel.impl.queue.AeReply;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeLongSet;

/**
 * Defines interface for writing process state to storage.
 */
public interface IAeProcessStateWriter
{
   /**
    * Creates journal entry to recover alarm in the event of engine failure.
    *
    * @param aProcessId
    * @param aLocationId The location id of the BPEL object that received the alarm.
    * @param aGroupId The group id of the alarm.
    * @param aAlarmId alarm execution reference id.
    */
   public long journalAlarm(long aProcessId, int aLocationId, int aGroupId, int aAlarmId) throws AeBusinessProcessException;

   /**
    * Creates journal entry to recover inbound receive in the event of engine
    * failure.
    *
    * @param aProcessId
    * @param aLocationId The location id of the BPEL object that received the message.
    * @param aInboundReceive The received message.
    */
   public long journalInboundReceive(long aProcessId, int aLocationId, AeInboundReceive aInboundReceive) throws AeBusinessProcessException;

   /**
    * Creates journal entry to recover invoke data in the event of engine
    * failure.
    *
    * @param aProcessId
    * @param aLocationId The location id of the BPEL object that received the message.
    * @param aTransmissionId The invoke activity's transmission id.
    * @param aMessageData The message data received from the invoke.
    * @param aProcessProperties
    */
   public long journalInvokeData(long aProcessId, int aLocationId, long aTransmissionId, IAeMessageData aMessageData, Map aProcessProperties) throws AeBusinessProcessException;

   /**
    * Creates journal entry to recover invoke fault in the event of engine
    * failure.
    *
    * @param aProcessId
    * @param aLocationId The location id of the BPEL object that received the fault.
    * @param aTransmissionId The invoke activity's tranmission id.
    * @param aFault The fault received from the invoke.
    * @param aProcessProperties
    */
   public long journalInvokeFault(long aProcessId, int aLocationId, long aTransmissionId, IAeFault aFault, Map aProcessProperties) throws AeBusinessProcessException;

   /**
    * Creates journal entry to recover sent reply in the event of engine
    * failure.
    *
    * @param aProcessId
    * @param aSentReply The sent reply.
    * @param aProcessProperties
    */
   public long journalSentReply(long aProcessId, AeReply aSentReply, Map aProcessProperties) throws AeBusinessProcessException;
   
   /**
    * Creates journal entry to recover durable invoke's transmission id the event of engine
    * failure.
    *
    * @param aProcessId
    * @param aLocationId The location id of the BPEL object that received the message. 
    * @param aTransmissionId The durable invoke transmission id.
    * @return journal id.
    */
   public long journalInvokeTransmitted(long aProcessId, int aLocationId, long aTransmissionId) throws AeBusinessProcessException;
   
   /**
    * Creates journal entry to recover sub process instance compensate in the event of engine
    * failure.
    *
    * @param aProcessId
    * @param aCoordinationId The coordination id.
    * @return journal id.
    */
   public long journalCompensateSubprocess(long aProcessId, String aCoordinationId) throws AeBusinessProcessException;

   /**
    * Creates journal entry to recover pending invoke in the event of engine
    * failure.
    *
    * @param aProcessId
    * @param aLocationId
    */
   public long journalInvokePending(long aProcessId, int aLocationId) throws AeBusinessProcessException;

   /**
    * Creates journal entry to recover an engine failure in the event that the
    * current recovery engine itself fails. 
    *
    * @param aProcessId
    * @param aDeadEngineId
    */
   public long journalEngineFailure(long aProcessId, int aDeadEngineId) throws AeBusinessProcessException;

   /**
    * Writes the state of the given process to storage.
    *
    * @param aProcess
    * @param aCompletedJournalIds
    * @param aCompletedTransmissionIds
    * @return The number of pending invoke activities (for debugging output).
    */ 
   public int writeProcess(IAeBusinessProcess aProcess, AeLongSet aCompletedJournalIds, AeLongSet aCompletedTransmissionIds) throws AeBusinessProcessException;
   
   /** 
    * @return returns next journal id.
    * @throws AeBusinessProcessException
    */
   public long getNextJournalId() throws AeBusinessProcessException;
}
