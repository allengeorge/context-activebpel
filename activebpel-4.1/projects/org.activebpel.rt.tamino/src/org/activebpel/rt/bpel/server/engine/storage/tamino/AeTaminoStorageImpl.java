// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/AeTaminoStorageImpl.java,v 1.1 2007/08/17 00:57:3
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

import com.softwareag.tamino.db.api.accessor.TAccessLocation;
import com.softwareag.tamino.db.api.accessor.TDeleteException;
import com.softwareag.tamino.db.api.accessor.TInsertException;
import com.softwareag.tamino.db.api.accessor.TNonXMLObjectAccessor;
import com.softwareag.tamino.db.api.accessor.TQuery;
import com.softwareag.tamino.db.api.accessor.TQueryException;
import com.softwareag.tamino.db.api.accessor.TXMLObjectAccessor;
import com.softwareag.tamino.db.api.accessor.TXQuery;
import com.softwareag.tamino.db.api.accessor.TXQueryException;
import com.softwareag.tamino.db.api.common.TAccessFailureException;
import com.softwareag.tamino.db.api.common.TException;
import com.softwareag.tamino.db.api.connection.TConnection;
import com.softwareag.tamino.db.api.objectModel.TNonXMLObject;
import com.softwareag.tamino.db.api.objectModel.TXMLObject;
import com.softwareag.tamino.db.api.objectModel.dom.TDOMObjectModel;
import com.softwareag.tamino.db.api.response.TResponse;

import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Random;

import org.activebpel.rt.bpel.server.engine.storage.tamino.handlers.AeTaminoUpdateOrDeleteCountResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeUniqueConstraintViolationXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBObject;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.log.AeXMLDBPerformanceLogger;
import org.activebpel.rt.tamino.AeMessages;

/**
 * A Tamino implementation of the storage impl interface.
 */
public class AeTaminoStorageImpl implements IAeXMLDBStorageImpl
{
   /** The response handler to use when doing an update. */
   private static final AeTaminoUpdateOrDeleteCountResponseHandler UPDATE_COUNT_RESPONSE_HANDLER = new AeTaminoUpdateOrDeleteCountResponseHandler();
   /** The Tamino code for a deadlock. */
   private static final String TAMINO_DEADLOCK_MESSAGE_CODE = "INOXYE9496"; //$NON-NLS-1$
   /** The Tamino code for a unique constraint violation. */
   private static final String TAMINO_UNIQUE_CONTRAINT_VIOLATION_MESSAGE_CODE = "INOXYE9202"; //$NON-NLS-1$
   /** A Random number generator used for deadlock retries. */
   private static final Random sRandom = new Random(System.currentTimeMillis());
   /** The docType to use for non-XML data. */
   private static final String NON_XML_DOCTYPE = "AeNonXMLContent"; //$NON-NLS-1$

   /** The data source. */
   private IAeXMLDBDataSource mDataSource;

   /**
    * C'tor.
    *
    * @param aDataSource
    */
   public AeTaminoStorageImpl(IAeXMLDBDataSource aDataSource)
   {
      setDataSource(aDataSource);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl#deleteDocuments(java.lang.String, java.lang.String, org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection)
    */
   public int deleteDocuments(String aQuery, String aDeleteDocType, IAeXMLDBConnection aConnection) throws AeXMLDBException
   {
      IAeXMLDBResponseHandler handler = new AeTaminoUpdateOrDeleteCountResponseHandler(aDeleteDocType);
      Integer deleteCount = (Integer) xquery(aQuery, handler, aConnection);
      return deleteCount.intValue();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl#deleteNonXMLDocument(long, org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection)
    */
   public void deleteNonXMLDocument(long aDocumentId, IAeXMLDBConnection aConnection) throws AeXMLDBException
   {
      try
      {
         String query = MessageFormat.format("{0}[@ino:id={1,number,#}]", //$NON-NLS-1$
               new Object[] { NON_XML_DOCTYPE, new Long(aDocumentId) });
         TQuery tquery = TQuery.newInstance(query);
         TConnection connection = (TConnection) aConnection.getNativeConnection();
         TNonXMLObjectAccessor nonXMLObjectAccessor = connection.newNonXMLObjectAccessor(
               TAccessLocation.newInstance(getCollectionName()) );
         nonXMLObjectAccessor.delete(tquery);
      }
      catch (TDeleteException ex)
      {
         throw new AeXMLDBException(AeMessages.getString("AeAbstractTaminoStorage.FailedToDeleteNonXMLContent"), ex); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl#getXMLDBDataSource()
    */
   public IAeXMLDBDataSource getXMLDBDataSource()
   {
      return getDataSource();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl#insertDocument(java.io.Reader, org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection)
    */
   public long insertDocument(Reader aReader, IAeXMLDBConnection aConnection) throws AeXMLDBException
   {
      TConnection connection = (TConnection) aConnection.getNativeConnection();
      
      long rval = -1L;
      try
      {
         long istart = System.currentTimeMillis();

         TXMLObject xmlObject = TXMLObject.newInstance(TDOMObjectModel.getInstance());

         // Establish the DOM representation by reading the contents from the character input stream
         xmlObject.readFrom(aReader);

         // Obtain a TXMLObjectAccessor with a DOM object model
         TXMLObjectAccessor xmlObjectAccessor = connection.newXMLObjectAccessor(
               TAccessLocation.newInstance(getCollectionName()), TDOMObjectModel.getInstance());

         // Invoke the insert operation
         xmlObjectAccessor.insert(xmlObject);
         rval = Long.parseLong(xmlObject.getId());

         long iend = System.currentTimeMillis();
         AeXMLDBPerformanceLogger.getInstance().logInsertTime(AeXMLDBObject.getCurrentStatementName(), iend - istart);

         return rval;
      }
      catch (TInsertException ex)
      {
         TException te = ex.getDeepestTException();
         if (te instanceof TAccessFailureException)
         {
            if (TAMINO_UNIQUE_CONTRAINT_VIOLATION_MESSAGE_CODE.equals(((TAccessFailureException) te).getCode()))
            {
               throw new AeUniqueConstraintViolationXMLDBException(ex);
            }
         }
         throw new AeXMLDBException(ex);
      }
      catch (Exception t)
      {
         throw new AeXMLDBException(AeMessages.getString("AeAbstractTaminoStorage.FAILED_TO_INSERT_ERROR"), t); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl#insertNonXMLDocument(java.io.InputStream, org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection)
    */
   public long insertNonXMLDocument(InputStream aInputStream, IAeXMLDBConnection aConnection) throws AeXMLDBException
   {
      TConnection connection = (TConnection) aConnection.getNativeConnection();

      long rval = -1L;
      try
      {
         long istart = System.currentTimeMillis();

         TNonXMLObject nonXMLObject = TNonXMLObject.newInstance(aInputStream, getCollectionName(),
               NON_XML_DOCTYPE, String.valueOf(System.currentTimeMillis()), "application/octet-stream"); //$NON-NLS-1$
         TNonXMLObjectAccessor nonXMLObjectAccessor = connection.newNonXMLObjectAccessor(
               TAccessLocation.newInstance(getCollectionName()) );
         nonXMLObjectAccessor.insert( nonXMLObject );

         rval = Long.parseLong(nonXMLObject.getId());

         long iend = System.currentTimeMillis();
         AeXMLDBPerformanceLogger.getInstance().logInsertTime(AeXMLDBObject.getCurrentStatementName(), iend - istart);

         return rval;
      }
      catch (TInsertException ex)
      {
         TException te = ex.getDeepestTException();
         if (te instanceof TAccessFailureException)
         {
            if (TAMINO_UNIQUE_CONTRAINT_VIOLATION_MESSAGE_CODE.equals(((TAccessFailureException) te).getCode()))
            {
               throw new AeUniqueConstraintViolationXMLDBException(ex);
            }
         }
         throw new AeXMLDBException(ex);
      }
      catch (Exception t)
      {
         throw new AeXMLDBException(AeMessages.getString("AeAbstractTaminoStorage.FAILED_TO_INSERT_ERROR"), t); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl#retrieveNonXMLDocument(long, org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection)
    */
   public InputStream retrieveNonXMLDocument(long aDocumentId, IAeXMLDBConnection aConnection) throws AeXMLDBException
   {
      TConnection connection = (TConnection) aConnection.getNativeConnection();

      InputStream rval = null;
      try
      {
         String query = MessageFormat.format("{0}[@ino:id={1,number,#}]", //$NON-NLS-1$
               new Object[] { NON_XML_DOCTYPE, new Long(aDocumentId) });
         TQuery tquery = TQuery.newInstance(query);
         TNonXMLObjectAccessor nonXMLObjectAccessor = connection.newNonXMLObjectAccessor(
               TAccessLocation.newInstance(getCollectionName()) );
         TResponse response = nonXMLObjectAccessor.query(tquery);
         if (response.hasFirstNonXMLObject())
            rval = response.getFirstNonXMLObject().getInputStream();
      }
      catch (TQueryException ex)
      {
         throw new AeXMLDBException(AeMessages.getString("AeAbstractTaminoStorage.FailedToGetNonXMLContent"), ex); //$NON-NLS-1$
      }

      return rval;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl#updateDocuments(java.lang.String, org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection)
    */
   public int updateDocuments(String aQuery, IAeXMLDBConnection aConnection) throws AeXMLDBException
   {
      Integer updateCount = (Integer) xquery(aQuery, UPDATE_COUNT_RESPONSE_HANDLER, aConnection);
      return updateCount.intValue();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl#xquery(java.lang.String, org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBResponseHandler, org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection)
    */
   public Object xquery(String aQuery, IAeXMLDBResponseHandler aResponseHandler, IAeXMLDBConnection aConnection) throws AeXMLDBException
   {
      TConnection connection = (TConnection) aConnection.getNativeConnection();

      ClassLoader previousClassLoader = Thread.currentThread().getContextClassLoader();
      try
      {
         // Set class loader to that which loaded us, to ensure we load the xerces parser
         Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

         long qstart = System.currentTimeMillis();

         // Obtain a TXMLObjectAccessor with a DOM object model
         TXMLObjectAccessor xmlObjectAccessor = connection.newXMLObjectAccessor(
               TAccessLocation.newInstance(getCollectionName()),
               TDOMObjectModel.getInstance());
         // Prepare to read the instance
         TXQuery query = TXQuery.newInstance(aQuery);
         // Invoke the query operation
         TResponse response = xmlObjectAccessor.xquery(query);

         long qend = System.currentTimeMillis();

         long hstart = System.currentTimeMillis();
         Object obj = aResponseHandler.handleResponse(new AeTaminoXQueryResponse(response));
         long hend = System.currentTimeMillis();

         AeXMLDBPerformanceLogger.getInstance().logXQueryTime(AeXMLDBObject.getCurrentStatementName(), qend - qstart, hend - hstart);

         return obj;
      }
      catch (TXQueryException ex)
      {
         TException te = ex.getDeepestTException();
         if (te instanceof TAccessFailureException)
         {
            if (TAMINO_DEADLOCK_MESSAGE_CODE.equals(((TAccessFailureException) te).getCode()))
            {
               try { Thread.sleep(sRandom.nextInt(500)); } catch (Exception e) { }
               // A Tamino deadlock has been detected - we may want to retry the transaction in
               // that case.  The AeProcessStateWriter looks for SQLException in its retry loop
               // logic - so we're going to provide it with one.
               // TODO (EPW) We should probably throw some sort of AeDeadlockException rather than a SQLException
               throw new AeXMLDBException(new SQLException("Tamino deadlock detected.")); //$NON-NLS-1$
            }
            else if (TAMINO_UNIQUE_CONTRAINT_VIOLATION_MESSAGE_CODE.equals(((TAccessFailureException) te).getCode()))
            {
               throw new AeUniqueConstraintViolationXMLDBException(ex);
            }
         }
         throw new AeXMLDBException(ex);
      }
      finally
      {
         Thread.currentThread().setContextClassLoader(previousClassLoader);
      }
   }

   /**
    * Gets the collection name.
    */
   private String getCollectionName()
   {
      return ((IAeTaminoDataSource) getXMLDBDataSource()).getCollectionName();
   }

   /**
    * @return Returns the dataSource.
    */
   protected IAeXMLDBDataSource getDataSource()
   {
      return mDataSource;
   }

   /**
    * @param aDataSource the dataSource to set
    */
   protected void setDataSource(IAeXMLDBDataSource aDataSource)
   {
      mDataSource = aDataSource;
   }
}
