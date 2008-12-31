//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/process/handlers/AeProcessInstanceDetailFilteredListResponseHandler.java,v 1.1 2007/08/17 00:40:5
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

import org.activebpel.rt.bpel.impl.list.AeProcessInstanceDetail;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBFilteredListResponseHandler;
import org.w3c.dom.Element;

/**
 * A response handler that creates a List of AeProcessInstanceDetail objects.
 */
public class AeProcessInstanceDetailFilteredListResponseHandler extends AeXMLDBFilteredListResponseHandler
{
   /**
    * Constructs a response handler.
    */
   public AeProcessInstanceDetailFilteredListResponseHandler()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBListResponseHandler#handleElement(org.w3c.dom.Element)
    */
   protected Object handleElement(Element aElement) throws AeXMLDBException
   {
      AeProcessInstanceDetail detail = createProcessInstanceDetail();
      AeProcessInstanceDetailResponseHandler.extractProcessInstanceDetailInto(aElement, detail);
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
