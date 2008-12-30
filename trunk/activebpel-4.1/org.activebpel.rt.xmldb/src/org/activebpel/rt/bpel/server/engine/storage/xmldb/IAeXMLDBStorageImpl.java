// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/IAeXMLDBStorageImpl.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb;

import java.io.InputStream;
import java.io.Reader;

/**
 * Instances of this interface must implement the parts of the XMLDB storage
 * framework that are implementation specific.
 */
public interface IAeXMLDBStorageImpl
{
   /**
    * Gets the data source for the xml database.
    */
   public IAeXMLDBDataSource getXMLDBDataSource();

   /**
    * Inserts a document into the XML database.
    * 
    * @param aReader
    * @param aConnection
    */
   public long insertDocument(Reader aReader, IAeXMLDBConnection aConnection) throws AeXMLDBException;

   /**
    * Insert the non-xml content in the xml database.
    * 
    * @param aInputStream
    * @param aConnection
    */
   public long insertNonXMLDocument(InputStream aInputStream, IAeXMLDBConnection aConnection)
         throws AeXMLDBException;

   /**
    * Runs the xquery and returns the result.
    * 
    * @param aQuery
    * @param aResponseHandler
    * @param aConnection
    */
   public Object xquery(String aQuery, IAeXMLDBResponseHandler aResponseHandler,
         IAeXMLDBConnection aConnection) throws AeXMLDBException;

   /**
    * Retrieves the non-xml content from the xml database.
    * 
    * @param aDocumentId
    * @param aConnection
    */
   public InputStream retrieveNonXMLDocument(long aDocumentId, IAeXMLDBConnection aConnection)
         throws AeXMLDBException;

   /**
    * Updates the documents in the XML database using the given XQuery and
    * returns the number of documents modified.
    * 
    * @param aQuery
    * @param aConnection
    */
   public int updateDocuments(String aQuery, IAeXMLDBConnection aConnection) throws AeXMLDBException;

   /**
    * Deletes the documents matching the given xquery. The aDeleteDocType param
    * is a string representing the doc type we are deleting. Even if multiple
    * doc types are being deleted, only a count of documents with this name will
    * be returned. Not all XML databases will use this information.
    * 
    * @param aQuery
    * @param aDeleteDocType
    * @param aConnection
    */
   public int deleteDocuments(String aQuery, String aDeleteDocType, IAeXMLDBConnection aConnection)
         throws AeXMLDBException;

   /**
    * Delets the non-xml content with the given ID from the xml database.
    * 
    * @param aDocumentId
    * @param aConnection
    */
   public void deleteNonXMLDocument(long aDocumentId, IAeXMLDBConnection aConnection) throws AeXMLDBException;
}
