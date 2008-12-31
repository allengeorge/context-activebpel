//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/handlers/AeXMLDBFilteredListResponseHandler.java,v 1.1 2007/08/17 00:40:5
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
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBQueryBuilder;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * This is a filtered list query handler that can be used to retrieve a portion of a result.  The 
 * filtered list includes the result # to start with and the # of results to return.  The handler
 * attempts to figure out how many results there are but stops counting at 500.
 */
public abstract class AeXMLDBFilteredListResponseHandler extends AeXMLDBListResponseHandler
{
   /** The number of rows in the <code>Response</code>. */
   private int mRowCount = 0;
   /** A flag indicating if the row count is complete or cut off. */
   private boolean mCompleteRowCount = false;

   /**
    * Constructor.
    */
   public AeXMLDBFilteredListResponseHandler()
   {
      super();
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBCollectionResponseHandler#handleIterator(org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse, java.util.Collection)
    */
   protected void handleIterator(IAeXMLDBXQueryResponse aResponse, Collection aCollection) throws AeXMLDBException
   {
      // The first item in the response will be the count of items found.  So get that number
      // before processing the rest of the result set as per normal.
      if (aResponse.hasNextElement())
      {
         Element elem = aResponse.nextElement();
         String countStr = AeXmlUtil.getText(elem);
         Integer count = new Integer(countStr);
         setRowCount(count.intValue());
      }

      // Now handle the rest of the items normally.
      super.handleIterator(aResponse, aCollection);
   }
   
   /**
    * Returns the number of rows.
    */
   public int getRowCount()
   {
      return mRowCount;
   }

   /**
    * Sets the number of rows.
    */
   protected void setRowCount(int aRowCount)
   {
      if (aRowCount > AeXMLDBQueryBuilder.LIMIT)
      {
         mRowCount = AeXMLDBQueryBuilder.LIMIT;
         setCompleteRowCount(false);
      }
      else
      {
         mRowCount = aRowCount;
         setCompleteRowCount(true);
      }
   }

   /**
    * @return Returns the completeRowCount.
    */
   public boolean isCompleteRowCount()
   {
      return mCompleteRowCount;
   }

   /**
    * @param aCompleteRowCount The completeRowCount to set.
    */
   protected void setCompleteRowCount(boolean aCompleteRowCount)
   {
      mCompleteRowCount = aCompleteRowCount;
   }
}
