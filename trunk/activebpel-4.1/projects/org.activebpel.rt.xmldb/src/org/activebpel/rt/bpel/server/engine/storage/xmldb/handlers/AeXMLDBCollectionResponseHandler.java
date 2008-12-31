//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/handlers/AeXMLDBCollectionResponseHandler.java,v 1.1 2007/08/17 00:40:5
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

import java.util.Collection;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse;
import org.activebpel.rt.xmldb.AeMessages;
import org.w3c.dom.Element;

/**
 * Implements a base class for any query handler that returns a collection of objects.
 */
public abstract class AeXMLDBCollectionResponseHandler extends AeXMLDBResponseHandler
{
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBResponseHandler#handleResponse(org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse)
    */
   public Object handleResponse(IAeXMLDBXQueryResponse aResponse) throws AeXMLDBException
   {
      try
      {
         Collection coll = createCollection();
         handleIterator(aResponse, coll);
         return coll;
      }
      catch (Exception e)
      {
         throw new AeXMLDBException(AeMessages.getString("AeXMLDBListResponseHandler.ERROR_ITERATING_THROUGH_XMLDB_RESULT"), e); //$NON-NLS-1$
      }
   }
   
   /**
    * Handles the xml iterator.  This method will iterate through all items in the iterator, call
    * handleXMLObject on each item, and add the result to the given List.
    * 
    * @param aResponse
    * @param aCollection
    * @throws AeXMLDBException
    */
   protected void handleIterator(IAeXMLDBXQueryResponse aResponse, Collection aCollection)
         throws AeXMLDBException
   {
      while (aResponse.hasNextElement())
      {
         Element elem = aResponse.nextElement();
         aCollection.add(handleElement(elem));
      }
   }

   /**
    * Called to convert an Element (taken from a TXMLObject) to some other object that will be put in
    * the result List.
    * 
    * @param aElement
    */
   protected abstract Object handleElement(Element aElement) throws AeXMLDBException;
   
   /**
    * Creates the Collection that will be used to tally the results.
    */
   protected abstract Collection createCollection();
}
