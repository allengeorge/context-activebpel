// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.exist/src/org/activebpel/rt/bpel/server/engine/storage/exist/AeExistXQueryResponse.java,v 1.1 2007/08/17 00:59:5
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
package org.activebpel.rt.bpel.server.engine.storage.exist;

import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse;
import org.w3c.dom.Element;

/**
 * An eXist response from an XQuery.
 */
public class AeExistXQueryResponse implements IAeXMLDBXQueryResponse
{
   /** Iterator over the results. */
   private Iterator mIterator;

   /**
    * C'tor.
    * 
    * @param aElements
    */
   public AeExistXQueryResponse(List aElements)
   {
      setIterator(aElements.iterator());
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse#hasNextElement()
    */
   public boolean hasNextElement()
   {
      return getIterator().hasNext();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBXQueryResponse#nextElement()
    */
   public Element nextElement()
   {
      return (Element) getIterator().next();
   }

   /**
    * @return Returns the iterator.
    */
   protected Iterator getIterator()
   {
      return mIterator;
   }

   /**
    * @param aIterator the iterator to set
    */
   protected void setIterator(Iterator aIterator)
   {
      mIterator = aIterator;
   }

}
