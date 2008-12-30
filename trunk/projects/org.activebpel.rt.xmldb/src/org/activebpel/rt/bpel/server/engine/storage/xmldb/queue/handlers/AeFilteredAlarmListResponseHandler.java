//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/queue/handlers/AeFilteredAlarmListResponseHandler.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.queue.handlers;

import java.util.Date;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.impl.list.AeAlarmExt;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBFilteredListResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.queue.IAeQueueElements;
import org.w3c.dom.Element;


/**
 * A Query Handler that converts a TResponse to a List of AeXMLDBAlarm objects (filtered).
 */
public class AeFilteredAlarmListResponseHandler extends AeXMLDBFilteredListResponseHandler
{
   /**
    * Creates a query handler.
    */
   public AeFilteredAlarmListResponseHandler()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBListResponseHandler#handleElement(org.w3c.dom.Element)
    */
   protected Object handleElement(Element aElement) throws AeXMLDBException
   {
      try
      {
         long procId = getLongFromElement(aElement, IAeQueueElements.PROCESS_ID).longValue();
         int locPathId = getIntFromElement(aElement, IAeQueueElements.LOCATION_PATH_ID).intValue();
         int groupId = getIntFromElement(aElement, IAeQueueElements.GROUP_ID).intValue();
         Date deadline = getDateTimeFromElement(aElement, IAeQueueElements.DEADLINE);
         QName procName = getQNameFromElement(aElement, IAeQueueElements.PROCESS_NAME);
         int alarmId = getIntFromElement(aElement, IAeQueueElements.ALARM_ID).intValue();
         return new AeAlarmExt(procId, locPathId, groupId, alarmId, deadline, procName);
      }
      catch (Exception e)
      {
         throw new AeXMLDBException(e);
      }
   }
}
