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
package org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade;

import com.softwareag.tamino.db.api.accessor.TAccessLocation;
import com.softwareag.tamino.db.api.accessor.TDefineMode;
import com.softwareag.tamino.db.api.accessor.TSchemaDefinition3Accessor;
import com.softwareag.tamino.db.api.accessor.TStreamAccessor;
import com.softwareag.tamino.db.api.accessor.TUndefineItem;
import com.softwareag.tamino.db.api.connection.TConnection;
import com.softwareag.tamino.db.api.objectModel.TXMLObject;
import com.softwareag.tamino.db.api.objectModel.dom.TDOMObjectModel;
import com.softwareag.tamino.db.api.response.TResponse;

import java.text.MessageFormat;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoConfig;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoDataSource;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoUtil;
import org.activebpel.rt.bpel.server.engine.storage.tamino.IAeTaminoDataSource;
import org.activebpel.rt.bpel.server.engine.storage.tamino.installer.AeTaminoSchema;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeAbstractXMLDBStorage;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBCollectionResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBResponseHandler;
import org.activebpel.rt.tamino.AeMessages;
import org.w3c.dom.Element;

/**
 * An abstract Tamino storage class that extends the basic one.  This one provide additional
 * methods, primarily for upgrades.
 */
public abstract class AeAbstractTaminoStorageEx extends AeAbstractXMLDBStorage
{
   /**
    * Creates a tamino storage object from the config and config prefix.
    * 
    * @param aConfig
    * @param aPrefix
    * @param aStorageImpl
    */
   public AeAbstractTaminoStorageEx(AeTaminoConfig aConfig, String aPrefix, IAeXMLDBStorageImpl aStorageImpl)
   {
      super(aConfig, aPrefix, aStorageImpl);
   }

   /**
    * Installs (defines) the schema in the Tamino DB.
    * 
    * @param aSchema
    * @throws AeStorageException
    */
   protected void defineSchema(AeTaminoSchema aSchema) throws AeStorageException
   {
      TConnection connection = (TConnection) getNewConnection().getNativeConnection();
      try
      {
         TSchemaDefinition3Accessor accessor = connection.newSchemaDefinition3Accessor(TDOMObjectModel.getInstance());

         // Instantiate an empty TXMLObject instance using the DOM object model
         TXMLObject xmlObject = TXMLObject.newInstance(TDOMObjectModel.getInstance());
         // Establish the DOM representation by reading the contents from the character input stream
         xmlObject.readFrom(aSchema.getReader());

         accessor.define(xmlObject, new TDefineMode(false, true));
      }
      catch (Exception ex)
      {
         throw new AeStorageException(ex);
      }
      finally
      {
         AeTaminoUtil.close(connection);
      }
   }

   /**
    * Gets the current schema configured for the collection.
    * 
    * @throws AeStorageException
    */
   protected AeTaminoSchema getCurrentSchema() throws AeStorageException
   {
      TConnection connection = (TConnection) getNewConnection().getNativeConnection();
      try
      {
         TSchemaDefinition3Accessor accessor = connection.newSchemaDefinition3Accessor(TDOMObjectModel.getInstance());
         TResponse response = accessor.getSchema(getCollectionName(), null);
         if (!response.hasFirstXMLObject())
         {
            String msg = MessageFormat.format(AeMessages.getString("AeTaminoUpgradeInstaller.NO_SCHEMAS_DEFINED"), new Object[] { getCollectionName() }); //$NON-NLS-1$
            throw new AeStorageException(msg);
         }
         TXMLObject txmlObj = response.getFirstXMLObject();
         Element schemaElem = (Element) txmlObj.getElement();
         
         return new AeTaminoSchema(schemaElem);
      }
      catch (AeStorageException ex)
      {
         throw ex;
      }
      catch (Exception ex)
      {
         throw new AeStorageException(ex);
      }
      finally
      {
         AeTaminoUtil.close(connection);
      }
   }
   
   /**
    * Gets the collection name.
    */
   protected String getCollectionName()
   {
      IAeTaminoDataSource taminoDS = (IAeTaminoDataSource) AeTaminoDataSource.MAIN.getNativeDataSource();
      return taminoDS.getCollectionName();
   }

   /**
    * Undefines a single doc type from the collection.
    * 
    * @param aCollectionName
    * @param aSchemaName
    * @param aDocTypeName
    * 
    * @throws AeStorageException
    */
   protected void undefineDocType(String aCollectionName, String aSchemaName, String aDocTypeName)
         throws AeStorageException
   {
      TConnection connection = (TConnection) getNewConnection().getNativeConnection();
      try
      {
         TAccessLocation accessLocation = TAccessLocation.newInstance(aCollectionName);
         TStreamAccessor accessor = connection.newStreamAccessor(accessLocation);
         TUndefineItem item = new TUndefineItem(aCollectionName, aSchemaName, aDocTypeName);
         accessor.undefine(new TUndefineItem[] { item });
      }
      catch (Exception ex)
      {
         throw new AeStorageException(ex);
      }
      finally
      {
         AeTaminoUtil.close(connection);
      }
   }

   /**
    * Run the given XQuery.
    * 
    * @param aXQuery
    */
   protected void doXQuery(String aXQuery) throws AeXMLDBException
   {
      xquery(aXQuery, AeXMLDBResponseHandler.NULL_XMLDB_RESPONSE_HANDLER, getNewConnection());
   }

   /**
    * Run the given XQuery.
    * 
    * @param aXQuery
    * @param aHandler
    */
   protected void doXQuery(String aXQuery, AeXMLDBCollectionResponseHandler aHandler) throws AeXMLDBException
   {
      xquery(aXQuery, aHandler, getNewConnection());
   }
}
