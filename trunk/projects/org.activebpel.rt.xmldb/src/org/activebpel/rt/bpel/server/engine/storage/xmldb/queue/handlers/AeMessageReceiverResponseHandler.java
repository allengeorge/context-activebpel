//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/queue/handlers/AeMessageReceiverResponseHandler.java,v 1.1 2007/08/17 00:40:5
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

import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AePartnerLinkOpKey;
import org.activebpel.rt.bpel.impl.queue.AeMessageReceiver;
import org.activebpel.rt.bpel.server.engine.storage.AePersistedMessageReceiver;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageUtil;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBSingleObjectResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.queue.IAeQueueElements;
import org.w3c.dom.Element;


/**
 * A simple query handler that returns a single message receiver from the result set.
 */
public class AeMessageReceiverResponseHandler extends AeXMLDBSingleObjectResponseHandler
{
   /**
    * Converts the given XML element in a message receiver.
    * 
    * @param aElement
    */
   public AeMessageReceiver readMessageReceiver(Element aElement) throws AeXMLDBException
   {
      try
      {
         int queuedReceiveId = getIntFromElement(aElement, IAeQueueElements.QUEUED_RECEIVE_ID).intValue();
         long processId = getLongFromElement(aElement, IAeQueueElements.PROCESS_ID).longValue();
         int locationPathId = getIntFromElement(aElement, IAeQueueElements.LOCATION_PATH_ID).intValue();
         int groupId = getIntFromElement(aElement, IAeQueueElements.GROUP_ID).intValue();
         String operation = getStringFromElement(aElement, IAeQueueElements.OPERATION);
         String plinkName = getStringFromElement(aElement, IAeQueueElements.PARTNER_LINK_NAME);
         QName portType = getQNameFromElement(aElement, IAeQueueElements.PORT_TYPE);
         Element corrPropsElem = getElementFromElement(aElement, IAeQueueElements.CORRELATION_PROPERTIES);
         Map corrProps = AeStorageUtil.deserializeCorrelationProperties(corrPropsElem);
         QName processName = getQNameFromElement(aElement, IAeQueueElements.PROCESS_NAME);
         boolean allowsConcurrency = getBoolFromElement(aElement, IAeQueueElements.ALLOWS_CONCURRENCY).booleanValue();

         // Note: legacy issue here - the partner link id may be null for old message receivers.
         Integer plinkIdInt = getIntFromElement(aElement, IAeQueueElements.PARTNER_LINK_ID);
         if (plinkIdInt == null)
            plinkIdInt = new Integer(-1);

         int partnerLinkId = plinkIdInt.intValue();
         AePartnerLinkOpKey plOpKey = new AePartnerLinkOpKey(plinkName, partnerLinkId, operation);

         return new AePersistedMessageReceiver(queuedReceiveId, processId, processName, plOpKey, portType,
               corrProps, locationPathId, groupId, allowsConcurrency);
      }
      catch (Exception e)
      {
         throw new AeXMLDBException(e);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBSingleObjectResponseHandler#handleElement(org.w3c.dom.Element)
    */
   protected Object handleElement(Element aElement) throws AeXMLDBException
   {
      return readMessageReceiver(aElement);
   }
}
