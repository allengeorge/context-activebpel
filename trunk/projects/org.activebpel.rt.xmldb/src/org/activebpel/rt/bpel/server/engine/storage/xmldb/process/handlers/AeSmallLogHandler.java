//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/process/handlers/AeSmallLogHandler.java,v 1.1 2007/08/17 00:40:5
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

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.process.IAeProcessElements;
import org.activebpel.rt.xmldb.AeMessages;
import org.w3c.dom.Element;


/**
 * A ResponseHandler that's responsible for walking the entire result set
 * and returning a String that represents the concatenation of all of the logs.
 * As its name implies, this is designed for reading a small number of logs since
 * it reads the entire contents of the logs into memory.
 */
public class AeSmallLogHandler extends AeXMLDBResponseHandler
{
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBResponseHandler#handleResponse(org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse)
    */
   public Object handleResponse(IAeXMLDBXQueryResponse aResponse) throws AeXMLDBException
   {
      try
      {
         StringBuffer sb = new StringBuffer();

         while (aResponse.hasNextElement())
         {
            Element elem = aResponse.nextElement();
            String log = getStringFromElement(elem, IAeProcessElements.PROCESS_LOG);
            sb.ensureCapacity((int) (sb.length() + log.length()));
            sb.append(log);
         }

         return sb.toString();
      }
      catch (Exception ex)
      {
         throw new AeXMLDBException(AeMessages.getString("AeXMLDBLogReader.ERROR_FOUND_IN_LOG_HANDLER"), ex); //$NON-NLS-1$
      }
   }
}
