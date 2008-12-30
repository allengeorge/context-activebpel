// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/handlers/AeXMLDBMapResponseHandler.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers;

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse;
import org.activebpel.rt.xmldb.AeMessages;
import org.w3c.dom.Element;

/**
 * An abstract base class that returns a Map of objects.
 */
public abstract class AeXMLDBMapResponseHandler extends AeXMLDBResponseHandler
{
   /**
    * Default c'tor.
    */
   public AeXMLDBMapResponseHandler()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBResponseHandler#handleResponse(org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse)
    */
   public Object handleResponse(IAeXMLDBXQueryResponse aResponse) throws AeXMLDBException
   {
      try
      {
         Map map = createMap();

         while (aResponse.hasNextElement())
         {
            Element docInst = aResponse.nextElement();
            Object key = getKey(docInst);
            Object value = getValue(docInst);
            map.put(key, value);
         }

         return map;
      }
      catch (Exception e)
      {
         throw new AeXMLDBException(AeMessages.getString("AeXMLDBListResponseHandler.ERROR_ITERATING_THROUGH_XMLDB_RESULT"), e); //$NON-NLS-1$
      }
   }
   
   /**
    * Gets the key to use for the map from the given XMLDB response Element.
    * 
    * @param aElement
    * @throws AeXMLDBException
    */
   protected abstract Object getKey(Element aElement) throws AeXMLDBException;

   /**
    * Gets the value of the Map entry from the given XMLDB response Element.
    * 
    * @param aElement
    * @throws AeXMLDBException
    */
   protected abstract Object getValue(Element aElement) throws AeXMLDBException;

   /**
    * Creates the Map to use for the return value.
    */
   protected Map createMap()
   {
      return new HashMap();
   }
}
