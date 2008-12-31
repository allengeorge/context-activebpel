// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.exist/src/org/activebpel/rt/bpel/server/engine/storage/exist/AeExistConnection.java,v 1.3 2007/08/23 21:26:5
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBResponseHandler;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.exist.xmldb.XQueryService;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

/**
 * An Exist database connection.
 */
public class AeExistConnection implements IAeExistConnection
{
   /** The map of counter values. */
   private static Map sCounters = new HashMap();

   /**
    * Returns the next counter value.
    * 
    * @param aType
    */
   private static synchronized long getNextCounterValue(String aType)
   {
      Long l = (Long) sCounters.get(aType);
      if (l == null)
         l = new Long(1);
      long rval = l.longValue();
      sCounters.put(aType, new Long(rval + 1));
      return rval;
   }

   /** Auto-commit flag. */
   private boolean mAutoCommit;
   /** The collection. */
   private Collection mCollection;
   /** Parser to use when querying the DB. */
   private AeXMLParserBase mParser = new AeXMLParserBase(true, false);
   /** The number of operations performed on this connection since it was opened. */
   private int mNumOperations = 0;

   /**
    * C'tor.
    *
    * @param aAutoCommit
    */
   protected AeExistConnection(boolean aAutoCommit, Collection aCollection)
   {
      setAutoCommit(aAutoCommit);
      setCollection(aCollection);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#getNativeConnection()
    */
   public Object getNativeConnection()
   {
      return this;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistConnection#insertDocument(java.lang.String)
    */
   public long insertDocument(String aXMLContent) throws AeXMLDBException
   {
      mNumOperations++;

      try
      {
         String docType = getDocType(aXMLContent);
         long counter = getNextCounterValue(docType);
         String xml = "<AeResourceRoot aeid=\"{0,number,#}\">{1}</AeResourceRoot>"; //$NON-NLS-1$
         xml = MessageFormat.format(xml, new Object[] { new Long(counter), aXMLContent });

         // create new XMLResource; an id will be assigned to the new resource
         XMLResource document = (XMLResource) getCollection().createResource(null, "XMLResource"); //$NON-NLS-1$
         document.setContent(xml);
         getCollection().storeResource(document);

         return counter;
      }
      catch (AeException ex)
      {
         throw new AeXMLDBException(ex);
      }
      catch (XMLDBException ex)
      {
         throw new AeXMLDBException(ex);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistConnection#xquery(java.lang.String, org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistResponseHandler)
    */
   public Object xquery(String aQuery, IAeXMLDBResponseHandler aResponseHandler) throws AeXMLDBException
   {
      List results = new ArrayList();

      try
      {
         XQueryService service = (XQueryService) getCollection().getService("XQueryService", "1.0"); //$NON-NLS-1$ //$NON-NLS-2$
         service.setProperty("indent", "yes"); //$NON-NLS-1$ //$NON-NLS-2$

         ResourceSet result = service.query(aQuery);
         for (ResourceIterator iter = result.getIterator(); iter.hasMoreResources(); )
         {
            Resource r = iter.nextResource();
            Object content = r.getContent();
            content = getParser().loadDocumentFromString((String) content, null).getDocumentElement();
            results.add(content);
         }
      }
      catch (Exception ex)
      {
         throw new AeXMLDBException(ex);
      }

      if (aResponseHandler != null)
         return aResponseHandler.handleResponse(new AeExistXQueryResponse(results));
      else
         return null;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistConnection#updateDocuments(java.lang.String)
    */
   public int updateDocuments(String aQuery) throws AeXMLDBException
   {
      mNumOperations++;

      Integer count = (Integer) xquery(aQuery, AeXMLDBResponseHandler.INTEGER_RESPONSE_HANDLER);
      return count.intValue();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistConnection#deleteDocuments(java.lang.String)
    */
   public int deleteDocuments(String aQuery) throws AeXMLDBException
   {
      mNumOperations++;

      Integer count = (Integer) xquery(aQuery, AeXMLDBResponseHandler.INTEGER_RESPONSE_HANDLER);
      return count.intValue();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistConnection#close()
    */
   public void close()
   {
      if (mNumOperations > 1 && isAutoCommit())
      {
         throw new RuntimeException("Too many operations performed using this auto-commit eXist connection."); //$NON-NLS-1$
      }
      mNumOperations = 0;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistConnection#commit()
    */
   public void commit() throws AeXMLDBException
   {
      if (isAutoCommit())
      {
         throw new UnsupportedOperationException("Commit not supported for auto-commit connections."); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.exist.IAeExistConnection#rollback()
    */
   public void rollback() throws AeXMLDBException
   {
      if (isAutoCommit())
      {
         throw new UnsupportedOperationException("Rollback not supported for auto-commit connections."); //$NON-NLS-1$
      }
   }

   /**
    * Gets the doc type of the given xml content.
    * 
    * @param aContent
    */
   private String getDocType(String aXMLContent) throws AeException
   {
      // FIXMEQ regular expression here
      return getParser().loadDocumentFromString(aXMLContent, null).getDocumentElement().getLocalName();
   }

   /**
    * @return Returns the autoCommit.
    */
   public boolean isAutoCommit()
   {
      return mAutoCommit;
   }

   /**
    * @param aAutoCommit the autoCommit to set
    */
   protected void setAutoCommit(boolean aAutoCommit)
   {
      mAutoCommit = aAutoCommit;
   }

   /**
    * @return Returns the collection.
    */
   protected Collection getCollection()
   {
      return mCollection;
   }

   /**
    * @param aCollection the collection to set
    */
   protected void setCollection(Collection aCollection)
   {
      mCollection = aCollection;
   }

   /**
    * @return Returns the parser.
    */
   protected AeXMLParserBase getParser()
   {
      return mParser;
   }

   /**
    * @param aParser the parser to set
    */
   protected void setParser(AeXMLParserBase aParser)
   {
      mParser = aParser;
   }
}
