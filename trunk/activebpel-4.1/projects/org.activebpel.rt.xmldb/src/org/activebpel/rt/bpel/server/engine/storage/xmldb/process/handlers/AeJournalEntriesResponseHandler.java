//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/process/handlers/AeJournalEntriesResponseHandler.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.process.handlers;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.recovery.journal.AeJournalEntryFactory;
import org.activebpel.rt.bpel.server.engine.recovery.journal.IAeJournalEntryFactory;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBListResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.journal.IAeJournalElements;
import org.activebpel.rt.xmldb.AeMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A response handler that returns a List of IAeJournalEntry objects.
 */
public class AeJournalEntriesResponseHandler extends AeXMLDBListResponseHandler
{
   /**
    * Overrides method to return an instance of IAeJournalEntry.
    * 
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBListResponseHandler#handleElement(org.w3c.dom.Element)
    */
   protected Object handleElement(Element aElement) throws AeXMLDBException
   {
      IAeJournalEntryFactory factory = AeJournalEntryFactory.getInstance();

      long journalId = getLongFromElement(aElement, IAeJournalElements.JOURNAL_ID).longValue();
      int entryType = getIntFromElement(aElement, IAeJournalElements.ENTRY_TYPE).intValue();
      int locationId = getIntFromElement(aElement, IAeJournalElements.LOCATION_PATH_ID).intValue();
      Document document = getDocumentFromElement(aElement, IAeJournalElements.ENTRY_DOCUMENT);

      try
      {
         return factory.newJournalEntry(entryType, locationId, journalId, document); 
      }
      catch (AeException e)
      {
         throw new AeXMLDBException(AeMessages.getString("AeJournalEntriesResponseHandler.ERROR_CREATING_JOURNAL_ENTRY"), e); //$NON-NLS-1$
      }
   }

}
