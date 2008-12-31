//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/process/handlers/AeLocationVersionSetResponseHandler.java,v 1.1 2007/08/17 00:40:5
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

import org.activebpel.rt.bpel.server.engine.storage.AeLocationVersionSet;
import org.activebpel.rt.bpel.server.engine.storage.IAeLocationVersionSet;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.process.IAeProcessElements;
import org.activebpel.rt.xmldb.AeMessages;
import org.w3c.dom.Element;

/**
 * Converts a XMLDB response to a set of location id and version number pairs (ie an instance of
 * <code>IAeLocationVersionSet</code>).
 */
public class AeLocationVersionSetResponseHandler extends AeXMLDBResponseHandler
{
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBResponseHandler#handleResponse(org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse)
    */
   public Object handleResponse(IAeXMLDBXQueryResponse aResponse) throws AeXMLDBException
   {
      try
      {
         IAeLocationVersionSet rval = new AeLocationVersionSet();
         while (aResponse.hasNextElement())
         {
            Element element = aResponse.nextElement();
            long locationId = getLongFromElement(element, IAeProcessElements.LOCATION_PATH_ID).longValue();
            int versionNumber = getIntFromElement(element, IAeProcessElements.VERSION_NUMBER).intValue();
            rval.add(locationId, versionNumber);
         }
         return rval;
      }
      catch (Exception ex)
      {
         throw new AeXMLDBException(AeMessages.getString("AeLocationVersionSetResponseHandler.ERROR_HANDLING_LOCVERSET_RESPONSE"), ex); //$NON-NLS-1$
      }
   }
}
