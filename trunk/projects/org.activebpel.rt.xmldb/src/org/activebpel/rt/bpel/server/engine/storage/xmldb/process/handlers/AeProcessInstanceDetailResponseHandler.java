//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/process/handlers/AeProcessInstanceDetailResponseHandler.java,v 1.1 2007/08/17 00:40:5
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

import java.util.Date;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.impl.list.AeProcessInstanceDetail;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBSingleObjectResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.process.IAeProcessElements;
import org.w3c.dom.Element;

/**
 * Implements a response handler that return a <code>AeProcessInstanceDetail</code>.
 */
public class AeProcessInstanceDetailResponseHandler extends AeXMLDBSingleObjectResponseHandler
{
   /**
    * Extracts the information found in the element into the proc instance detail object.
    * 
    * @param aElement
    * @param aDetail
    */
   public static void extractProcessInstanceDetailInto(Element aElement, AeProcessInstanceDetail aDetail)
   {
      long processId = getLongFromElement(aElement, IAeProcessElements.PROCESS_ID).longValue();
      QName processName = getQNameFromElement(aElement, IAeProcessElements.PROCESS_NAME);
      Integer processState = getIntFromElement(aElement, IAeProcessElements.PROCESS_STATE);
      if (processState == null)
      {
         processState = new Integer(IAeBusinessProcess.PROCESS_LOADED);
      }
      Integer processStateReason = getIntFromElement(aElement, IAeProcessElements.PROCESS_STATE_REASON);
      if (processStateReason == null)
      {
         processStateReason = new Integer(IAeBusinessProcess.PROCESS_REASON_NONE);
      }

      Date startDate = getDateTimeFromElement(aElement, IAeProcessElements.START_DATE);
      Date endDate = getDateTimeFromElement(aElement, IAeProcessElements.END_DATE);

      if (startDate == null)
      {
         // Always return a non-null start date. The start date will be null
         // if the process hasn't been saved yet.
         startDate = new Date();
      }

      aDetail.setProcessId(processId);
      aDetail.setName(processName);
      aDetail.setState(processState.intValue());
      aDetail.setStateReason(processStateReason.intValue());
      aDetail.setStarted(startDate);
      aDetail.setEnded(endDate);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBSingleObjectResponseHandler#handleElement(org.w3c.dom.Element)
    */
   protected Object handleElement(Element aElement) throws AeXMLDBException
   {
      AeProcessInstanceDetail detail = createProcessInstanceDetail();
      extractProcessInstanceDetailInto(aElement, detail);
      return detail;
   }

   /**
    * Creates the process instance detail object.
    */
   protected AeProcessInstanceDetail createProcessInstanceDetail()
   {
      return new AeProcessInstanceDetail();
   }
}
