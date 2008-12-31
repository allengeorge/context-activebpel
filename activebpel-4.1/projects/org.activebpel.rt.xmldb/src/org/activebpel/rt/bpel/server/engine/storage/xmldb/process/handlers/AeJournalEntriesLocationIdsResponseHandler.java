//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/process/handlers/AeJournalEntriesLocationIdsResponseHandler.java,v 1.1 2007/08/17 00:40:5
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

import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBMapResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.journal.IAeJournalElements;
import org.activebpel.rt.util.AeLongMap;
import org.w3c.dom.Element;

/**
 * Implements a Response Handler that converts a XMLDB response to a map from journal entry 
 * ids to location ids.
 */
public class AeJournalEntriesLocationIdsResponseHandler extends AeXMLDBMapResponseHandler
{
   /**
    * Default constructor.
    */
   public AeJournalEntriesLocationIdsResponseHandler()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBMapResponseHandler#getKey(org.w3c.dom.Element)
    */
   protected Object getKey(Element aElement) throws AeXMLDBException
   {
      return getLongFromElement(aElement, IAeJournalElements.JOURNAL_ID);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBMapResponseHandler#getValue(org.w3c.dom.Element)
    */
   protected Object getValue(Element aElement) throws AeXMLDBException
   {
      return getIntFromElement(aElement, IAeJournalElements.LOCATION_PATH_ID);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBMapResponseHandler#createMap()
    */
   protected Map createMap()
   {
      return new AeLongMap();
   }
}
