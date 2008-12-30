//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/journal/AeCompensateSubprocessJournalEntry.java,v 1.2 2006/07/26 21:49:3
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.coord.AeCoordinationException;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.impl.fastdom.AeFastElement;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Journal entry for subprocess compensation.
 */
public class AeCompensateSubprocessJournalEntry extends AeAbstractJournalEntry implements IAeJournalEntry
{
   /** XML tag name for serialization. */
   private static final String TAG_COMPENSATE_SUBPROCESS = "compensateSubprocess"; //$NON-NLS-1$
   /** Coordination id. */
   private String mCoordinationId;

   /**
    * Construct for serializing given coordination id.
    * @param aCoordinationId
    */
   public AeCompensateSubprocessJournalEntry(String aCoordinationId)
   {
      super(JOURNAL_COMPENSATE_SUBPROCESS, 0);
      setCoordinationId(aCoordinationId);
   }

   /**
    * Constructor used by the journal entry factory.
    * @param aLocationId
    * @param aJournalId
    * @param aStorageDocument
    */
   public AeCompensateSubprocessJournalEntry( int aLocationId, long aJournalId, Document aStorageDocument)
   {
      super(JOURNAL_COMPENSATE_SUBPROCESS, aLocationId, aJournalId, aStorageDocument);
   }

   /**
    * @return Returns the coordinationId.
    */
   protected String getCoordinationId() throws AeBusinessProcessException
   {
      deserialize();
      return mCoordinationId;
   }

   /**
    * @param aCoordinationId The coordinationId to set.
    */
   protected void setCoordinationId(String aCoordinationId)
   {
      mCoordinationId = aCoordinationId;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.AeAbstractJournalEntry#internalDeserialize(org.w3c.dom.Document)
    */
   protected void internalDeserialize(Document aStorageDocument) throws AeBusinessProcessException
   {
      Element root = aStorageDocument.getDocumentElement();
      setCoordinationId(  root.getAttribute( STATE_COORDINATION_ID)  );
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.AeAbstractJournalEntry#internalSerialize(org.activebpel.rt.xml.schema.AeTypeMapping)
    */
   protected AeFastDocument internalSerialize(AeTypeMapping aTypeMapping) throws AeBusinessProcessException
   {
      if (aTypeMapping == null)
      {
         throw new IllegalStateException(AeMessages.getString("AeCompensateSubprocessJournalEntry.MISSING_TYPE_MAPPING")); //$NON-NLS-1$
      }
      AeFastElement root = new AeFastElement(TAG_COMPENSATE_SUBPROCESS);
      root.setAttribute( STATE_COORDINATION_ID, String.valueOf( getCoordinationId() ) );
      return new AeFastDocument(root);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntry#dispatchToProcess(org.activebpel.rt.bpel.IAeBusinessProcess)
    */
   public void dispatchToProcess(IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      try
      {
         IAeBusinessProcessEngineInternal engine = (IAeBusinessProcessEngineInternal) aProcess.getEngine();
         engine.getProcessCoordination().compensateSubProcess( aProcess.getProcessId(), getCoordinationId(), getJournalId());
      }
      catch(AeCoordinationException ace)
      {
        throw new AeBusinessProcessException(ace.getMessage(), ace);  
      }
      
   }

}
