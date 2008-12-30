// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/process/handlers/AeProcessIDFilteredListResponseHandler.java,v 1.1 2007/08/17 00:40:5
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

import java.util.List;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBFilteredListResponseHandler;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Response handler for converting the XMLDB result set to an array
 * of process IDs (long[]).
 */
public class AeProcessIDFilteredListResponseHandler extends AeXMLDBFilteredListResponseHandler
{
   /**
    * Constructs a response handler.
    */
   public AeProcessIDFilteredListResponseHandler()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBCollectionResponseHandler#handleResponse(org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse)
    */
   public Object handleResponse(IAeXMLDBXQueryResponse aResponse) throws AeXMLDBException
   {
      List list = (List) super.handleResponse(aResponse);
      return convertToArray(list);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBListResponseHandler#handleElement(org.w3c.dom.Element)
    */
   protected Object handleElement(Element aElement) throws AeXMLDBException
   {
      String processId = AeXmlUtil.getText(aElement);
      return new Long(processId);
   }
   
   /**
    * Convert the resulting List of Long objects into a long array.
    * 
    * @param aList
    */
   protected long [] convertToArray(List aList)
   {
      long [] rval = new long[aList.size()];
      for (int idx = 0; idx < aList.size(); idx++)
      {
         Long l = (Long) aList.get(idx);
         rval[idx] = l.longValue();
      }
      return rval;
   }
}
