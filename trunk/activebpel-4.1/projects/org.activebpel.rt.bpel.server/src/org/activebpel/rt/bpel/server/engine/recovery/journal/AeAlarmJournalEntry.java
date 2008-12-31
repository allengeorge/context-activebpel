// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/journal/AeAlarmJournalEntry.java,v 1.6 2006/09/18 17:59:5
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
package org.activebpel.rt.bpel.server.engine.recovery.journal;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.impl.fastdom.AeFastElement;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Document;

/**
 * Implements journal entry for alarm.
 */
public class AeAlarmJournalEntry extends AeAbstractJournalEntry implements IAeJournalEntry
{
   private static final String GROUP_ID = "group-id"; //$NON-NLS-1$
   private static final String ALARM_ID = "id"; //$NON-NLS-1$
   private static final String ALARM = "alarm"; //$NON-NLS-1$

   /** The alarm's group id. */
   private int mGroupId;
   
   /** Alarm instance id */
   private int mAlarmId;

   /*
    * Constructs journal entry to persist to storage.
    */
   public AeAlarmJournalEntry(int aLocationId, int aGroupId, int aAlarmId)
   {
      super(JOURNAL_ALARM, aLocationId);
      setGroupId(aGroupId);
      setAlarmId(aAlarmId);
   }

   /**
    * Constructs journal entry to restore from storage.
    */
   public AeAlarmJournalEntry(int aLocationId, long aJournalId, Document aStorageDocument)
   {
      super(JOURNAL_ALARM, aLocationId, aJournalId, aStorageDocument);
   }

   /**
    * Overrides method to dispatch the alarm to the specified process through
    * the recovery engine.
    *
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry#dispatchToProcess(org.activebpel.rt.bpel.IAeBusinessProcess)
    */
   public void dispatchToProcess(IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      IAeBusinessProcessEngineInternal engine = aProcess.getEngine();
      long processId = aProcess.getProcessId();
      int locationId = getLocationId();
      engine.dispatchAlarm(processId, locationId, getGroupId(), getAlarmId() );
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.AeAbstractJournalEntry#internalDeserialize(org.w3c.dom.Document)
    */
   protected void internalDeserialize(Document aStorageDocument)
   {
      int groupId;
      int alarmId = 0;
      try
      {
         groupId = Integer.parseInt(aStorageDocument.getDocumentElement().getAttribute(GROUP_ID));
      }
      catch (Exception ignore)
      {
         // A databases upgraded from ActiveBPEL 1.2 will not have a group id
         // for an alarm journal entry.
         groupId = getLocationId();
      }
      if (AeUtil.notNullOrEmpty(aStorageDocument.getDocumentElement().getAttribute(ALARM_ID)))
      {
       
         try
         {
            alarmId = Integer.parseInt(aStorageDocument.getDocumentElement().getAttribute(ALARM_ID));
         }
         catch(Exception e)
         {
            AeException.logWarning(e.getMessage());
            // ignore
         }
      }
      setGroupId(groupId);
      setAlarmId(alarmId);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.AeAbstractJournalEntry#internalSerialize(org.activebpel.rt.xml.schema.AeTypeMapping)
    */
   protected AeFastDocument internalSerialize(AeTypeMapping aTypeMapping)
   {
      AeFastElement result = new AeFastElement(ALARM);
      result.setAttribute(GROUP_ID, "" + getGroupId()); //$NON-NLS-1$
      result.setAttribute(ALARM_ID, String.valueOf( getAlarmId() )); 
      return new AeFastDocument(result);
   }
   
   /**
    * @return Returns the groupId.
    */
   protected int getGroupId()
   {
      return mGroupId;
   }
   
   /**
    * @param aGroupId The groupId to set.
    */
   protected void setGroupId(int aGroupId)
   {
      mGroupId = aGroupId;
   }

   /**
    * @return Returns the alarmId.
    */
   protected int getAlarmId()
   {
      return mAlarmId;
   }

   /**
    * @param aAlarmId The alarmId to set.
    */
   protected void setAlarmId(int aAlarmId)
   {
      mAlarmId = aAlarmId;
   }
   
}
