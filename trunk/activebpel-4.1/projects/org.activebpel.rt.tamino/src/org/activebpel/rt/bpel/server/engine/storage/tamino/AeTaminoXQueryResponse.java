// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/AeTaminoXQueryResponse.java,v 1.1 2007/08/17 00:57:3
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
package org.activebpel.rt.bpel.server.engine.storage.tamino;

import com.softwareag.tamino.db.api.objectModel.TIteratorException;
import com.softwareag.tamino.db.api.objectModel.TNoSuchXMLObjectException;
import com.softwareag.tamino.db.api.objectModel.TXMLObject;
import com.softwareag.tamino.db.api.objectModel.TXMLObjectIterator;
import com.softwareag.tamino.db.api.response.TResponse;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse;
import org.w3c.dom.Element;

/**
 * A Tamino implementation of an XQuery response.
 */
public class AeTaminoXQueryResponse implements IAeXMLDBXQueryResponse
{
   /** Iterator over the XML objects. */
   private TXMLObjectIterator mXMLIterator;

   /**
    * C'tor.
    * 
    * @param aResponse
    */
   public AeTaminoXQueryResponse(TResponse aResponse)
   {
      if (aResponse.hasQueryContent() && aResponse.hasFirstXMLObject())
      {
         setXMLIterator(aResponse.getXMLObjectIterator());
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse#hasNextElement()
    */
   public boolean hasNextElement()
   {
      if (getXMLIterator() != null)
         return getXMLIterator().hasNext();
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse#nextElement()
    */
   public Element nextElement()
   {
      try
      {
         TXMLObject xmlObject = getXMLIterator().next();
         return (Element) xmlObject.getElement();
      }
      catch (TNoSuchXMLObjectException ex)
      {
         throw new RuntimeException(ex);
      }
      catch (TIteratorException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * @return Returns the xMLIterator.
    */
   protected TXMLObjectIterator getXMLIterator()
   {
      return mXMLIterator;
   }

   /**
    * @param aIterator the xMLIterator to set
    */
   protected void setXMLIterator(TXMLObjectIterator aIterator)
   {
      mXMLIterator = aIterator;
   }
}
