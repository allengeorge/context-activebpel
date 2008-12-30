//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/transreceive/handlers/AeTransmissionTrackerResponseHandler.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.transreceive.handlers;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBSingleObjectResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.transreceive.IAeTransmissionTrackerElements;
import org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry;
import org.w3c.dom.Element;

/**
 * Handler to create a AeTransmissionTrackerEntry object from the XMLDB response.
 *
 */
public class AeTransmissionTrackerResponseHandler extends AeXMLDBSingleObjectResponseHandler
{
   /**
    * Overrides method to create and return a  <code>AeTransmissionTrackerEntry</code> object.
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBSingleObjectResponseHandler#handleElement(org.w3c.dom.Element)
    */
   protected Object handleElement(Element aElement) throws AeXMLDBException
   {
      long transmissionId = getLongFromElement(aElement, IAeTransmissionTrackerElements.TRANSMISSION_ID).longValue();
      int state  = getIntFromElement(aElement, IAeTransmissionTrackerElements.STATE).intValue();
      String messageId = getStringFromElement(aElement, IAeTransmissionTrackerElements.MESSAGE_ID);
      return new AeTransmissionTrackerEntry(transmissionId, state, messageId);
   }

}
