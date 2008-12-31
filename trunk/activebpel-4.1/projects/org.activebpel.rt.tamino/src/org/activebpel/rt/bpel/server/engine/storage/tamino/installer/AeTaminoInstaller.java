//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/installer/AeTaminoInstaller.java,v 1.9 2007/08/17 00:57:3
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
package org.activebpel.rt.bpel.server.engine.storage.tamino.installer;

import com.softwareag.tamino.db.api.accessor.TSchemaDefinition3Accessor;
import com.softwareag.tamino.db.api.connection.TConnection;
import com.softwareag.tamino.db.api.objectModel.TXMLObject;
import com.softwareag.tamino.db.api.objectModel.dom.TDOMObjectModel;
import com.softwareag.tamino.db.api.response.TResponse;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoConfig;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoDataSource;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoStorageImpl;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoUtil;
import org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade.AeAbstractTaminoStorageEx;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.log.AeXMLDBPerformanceLogger;
import org.activebpel.rt.tamino.AeMessages;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class is used to define the ActiveBPEL schema in a Tamino database.  It is a standalone
 * application that uses the Tamino Java APIs.
 * 
 * TODO (EPW) this now extends the AeAbstractTaminoStorageEx class - move some schema manipulation calls down there...
 */
public class AeTaminoInstaller extends AeAbstractTaminoStorageEx
{
   protected static Map sKeyLookup = new HashMap();
   protected static boolean sVerbose = false;
   
   static
   {
      sKeyLookup.put("url", "URL"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("db", "Database"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("coll", "Collection"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("domain", "Domain"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("user", "Username"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("pass", "Password"); //$NON-NLS-1$ //$NON-NLS-2$

      sKeyLookup.put("seedfile", "_SeedDataFile_"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("drop", "_Drop_"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("clean", "_Clean_"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("verbose", "_Verbose_"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("schema", "_Schema_"); //$NON-NLS-1$ //$NON-NLS-2$
   }

   /** The command line params. */
   private Map mParams;
   /** The schema. */
   private AeTaminoSchema mSchema;

   /**
    * Constructs the tamino installer given the Tamino config and command line params.
    * 
    * @param aSchema
    * @param aConfig
    * @param aParams
    * @param aStorageImpl
    */
   public AeTaminoInstaller(AeTaminoSchema aSchema, AeTaminoConfig aConfig, Map aParams,
         IAeXMLDBStorageImpl aStorageImpl)
   {
      super(aConfig, "Installer", aStorageImpl); //$NON-NLS-1$
      setSchema(aSchema);
      setParams(aParams);
   }

   /**
    * Installs/defines the ActiveBPEL schema in the Tamino database.
    * 
    * @throws AeException
    */
   public void install(boolean aDrop) throws AeException
   {
      output("Params:"); //$NON-NLS-1$
      for (Iterator iter = getParams().entrySet().iterator(); iter.hasNext(); )
      {
         Map.Entry entry = (Map.Entry) iter.next();
         String key = (String) entry.getKey();
         String val = (String) entry.getValue();
         output("\t" + key + "=" + val); //$NON-NLS-1$ //$NON-NLS-2$
      }
      output("\tSchema=" + getSchema().getSchemaName()); //$NON-NLS-1$

      boolean clean = "true".equals(getParams().get("_Clean_")); //$NON-NLS-1$ //$NON-NLS-2$
      
      try
      {
         boolean installed = isInstalled();
         boolean seeded = false;
         if (installed && !aDrop)
         {
            output(AeMessages.getString("AeTaminoInstaller.SCHEMA_ALREADY_DEFINED")); //$NON-NLS-1$
            if (clean)
            {
               output(AeMessages.getString("AeTaminoInstaller.CLEANING_THE_DB")); //$NON-NLS-1$
               cleanDB();
               seeded = false;
               output(AeMessages.getString("AeTaminoInstaller.DB_CLEAN")); //$NON-NLS-1$
            }
         }
         else if (installed && aDrop)
         {
            dropSchema();
            installed = false;
            seeded = false;
         }

         if (!installed)
         {
            installSchema();
         }
         if (!seeded)
         {
            insertSeedData();
         }
         output("All done."); //$NON-NLS-1$
      }
      catch (Exception t)
      {
         throw new AeException(t);
      }
   }

   /**
    * Gets the schema name.
    */
   protected String getSchemaName()
   {
      return getSchema().getSchemaName();
   }

   /**
    * Cleans the database (deletes all instances).
    * 
    * @throws Exception
    */
   protected void cleanDB() throws Exception
   {
      deleteDocuments("Clean"); //$NON-NLS-1$
   }
   
   /**
    * Determines whether the schema is already defined in the Tamino database.
    */
   protected boolean isInstalled() throws Exception
   {
      output(AeMessages.getString("AeTaminoInstaller.CHECKING_FOR_SCHEMA")); //$NON-NLS-1$
      TConnection connection = (TConnection) getNewConnection().getNativeConnection();
      try
      {
         TSchemaDefinition3Accessor accessor = connection.newSchemaDefinition3Accessor(TDOMObjectModel.getInstance());
         TResponse resp = accessor.getSchema(getCollectionName(), getSchemaName());
         boolean rval = resp.hasQueryContent();
         if (rval)
         {
            output(AeMessages.getString("AeTaminoInstaller.SCHEMA_ALREADY_INSTALLED")); //$NON-NLS-1$
         }
         else
         {
            output(AeMessages.getString("AeTaminoInstaller.NO_SCHEMA_FOUND")); //$NON-NLS-1$
         }
         return rval;
      }
      finally
      {
         AeTaminoUtil.close(connection);
      }
   }

   /**
    * Drops (undefines) the schema from the Tamino db.
    * 
    * @throws Exception
    */
   protected void dropSchema() throws Exception
   {
      output(AeMessages.getString("AeTaminoInstaller.DROPPING_SCHEMA")); //$NON-NLS-1$
      TConnection connection = (TConnection) getNewConnection().getNativeConnection();
      try
      {
         TSchemaDefinition3Accessor accessor = connection.newSchemaDefinition3Accessor(TDOMObjectModel.getInstance());
         accessor.undefine(getCollectionName(), getSchemaName());
         output(AeMessages.getString("AeTaminoInstaller.SCHEMA_UNDEFINED")); //$NON-NLS-1$
      }
      finally
      {
         AeTaminoUtil.close(connection);
      }
   }

   /**
    * Installs (defines) the schema in the Tamino db.
    * 
    * @throws Exception
    */
   protected void installSchema() throws Exception
   {
      output(AeMessages.getString("AeTaminoInstaller.INSTALLING_SCHEMA")); //$NON-NLS-1$
      TConnection connection = (TConnection) getNewConnection().getNativeConnection();
      try
      {
         TSchemaDefinition3Accessor accessor = connection.newSchemaDefinition3Accessor(TDOMObjectModel.getInstance());

         // Instantiate an empty TXMLObject instance using the DOM object model
         TXMLObject xmlObject = TXMLObject.newInstance(TDOMObjectModel.getInstance());
         // Establish the DOM representation by reading the contents from the character input stream
         xmlObject.readFrom(getSchema().getReader());

         accessor.define(xmlObject);
         output(AeMessages.getString("AeTaminoInstaller.FINISHED_DEFINING_SCHEMA")); //$NON-NLS-1$
      }
      finally
      {
         AeTaminoUtil.close(connection);
      }
   }

   /**
    * Inserts some seed data into the Tamino database.
    * 
    * @throws Exception
    */
   protected void insertSeedData() throws Exception
   {
      output(AeMessages.getString("AeTaminoInstaller.INSERTING_SEED_DATA")); //$NON-NLS-1$
      insertDBVersion();
      insertOtherSeedData();
      output(AeMessages.getString("AeTaminoInstaller.SEED_DATA_INSERTED")); //$NON-NLS-1$
   }

   /**
    * Inserts the DB version into the Tamion database.
    * 
    * @throws Exception
    */
   protected void insertDBVersion() throws Exception
   {
      Object [] params = { getDBVersion() };
      insertDocument("InsertVersion", params); //$NON-NLS-1$
      output(AeMessages.getString("AeTaminoInstaller.INSERTED_VERSION") + params[0]); //$NON-NLS-1$
      output(AeMessages.getString("AeTaminoInstaller.COLLECTION_USED") + getSchema().getCollectionName()); //$NON-NLS-1$
   }
   
   /**
    * Inserts any other necessary seed data.
    * 
    * @throws Exception
    */
   protected void insertOtherSeedData() throws Exception
   {
      AeXMLParserBase parser = new AeXMLParserBase();
      parser.setValidating(false);

      String fileName = (String) getParams().get("_SeedDataFile_"); //$NON-NLS-1$
      if (AeUtil.notNullOrEmpty(fileName))
      {
         File file = new File(fileName);
         if (file.isFile())
         {
            Document doc = parser.loadDocument(new FileReader(file), null);
            
            int numSeedData = 0;
            
            NodeList nl = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < nl.getLength(); i++)
            {
               Node node = nl.item(i);
               if (node instanceof Element)
               {
                  Element seedData = (Element) node;
                  insertDocument(seedData);
                  String msg = AeMessages.format("AeTaminoInstaller.INSERTED_DOCUMENT_INSTANCE_SEEDDATA", seedData.getLocalName()); //$NON-NLS-1$
                  output(msg);
                  numSeedData++;
               }
            }

            String msg = AeMessages.format("AeTaminoInstaller.INSERTED_DOCUMENT_INSTANCES_COUNT", new Integer(numSeedData)); //$NON-NLS-1$
            output(msg);
         }
      }
   }
   
   /**
    * Gets the DB version from some AeBuildNumber class.  The full classname of an AeBuildNumber
    * class is passed to this method.  Introspection is then used to retrieve the db version 
    * number.
    * 
    * @param aBuildNumberClassName
    */
   protected String getDBVersion(String aBuildNumberClassName)
   {
      /* 
       * Note here:  this is done dynamically to avoid a compile dependency on the ddl.org.activebpel project.
       * For some reason that I do not understand, we ship a minimal version of ActiveBPEL with
       * ActiveBPEL Designer (rather than simply including a full ActiveBPEL distro).  This
       * stripped-down version does not include the ddl.org.activebpel project and I am told that
       * we do NOT want to include that (even though I think it's the only org.* project that we
       * don't ship for Pro.
       * 
       * The code here USED to look like this:
       * 
       *    return org.activebpel.ddl.AeBuildNumber.getDBVersion();
       * 
       * EPW - 2005-09-27
       */
      try
      {
         Class aeBuildNumberClass = Class.forName(aBuildNumberClassName); 
         Method method = aeBuildNumberClass.getMethod("getDBVersion", null); //$NON-NLS-1$
         return (String) method.invoke(null, null);
      }
      catch (Exception ex)
      {
         throw new RuntimeException(ex);
      }
   }
   
   /**
    * Gets the DB version from the correct instance of AeBuildNumber.
    */
   protected String getDBVersion()
   {
      return getDBVersion("org.activebpel.ddl.AeBuildNumber"); //$NON-NLS-1$
   }
   
   /**
    * Print a debug message.
    * 
    * @param aMessage
    */
   protected void debug(String aMessage)
   {
      if (sVerbose)
      {
         System.out.println(aMessage);
      }
   }
   
   /**
    * Prints out an output message.
    * 
    * @param aMessage
    */
   protected void output(String aMessage)
   {
      System.out.println(aMessage);
   }

   /**
    * @return Returns the params.
    */
   protected Map getParams()
   {
      return mParams;
   }
   
   /**
    * @param aParams The params to set.
    */
   protected void setParams(Map aParams)
   {
      mParams = aParams;
   }

   /**
    * @return Returns the schema.
    */
   protected AeTaminoSchema getSchema()
   {
      return mSchema;
   }
   
   /**
    * @param aSchema The schema to set.
    */
   protected void setSchema(AeTaminoSchema aSchema)
   {
      mSchema = aSchema;
   }

   /**
    * This method is the actual work of the "main" method - but it's broken out so that subclasses
    * can more easily use it.
    * 
    * @param aArgs
    */
   protected static void sharedMain(String [] aArgs, Class aInstallerClass)
   {
      try
      {
         Map params = new HashMap();
         Pattern p = Pattern.compile("-([^=]+)=(.*)"); //$NON-NLS-1$
         for (int i = 0; i < aArgs.length; i++)
         {
            String param = aArgs[i];
            Matcher m = p.matcher(param);
            if (m.matches())
            {
               String key = m.group(1);
               String val = m.group(2);
               key = (String) sKeyLookup.get(key);
               if (key != null)
               {
                  params.put(key, val);
               }
            }
         }

         sVerbose = "true".equals(params.get("_Verbose_")); //$NON-NLS-1$ //$NON-NLS-2$

         String schemaPath = (String) params.get("_Schema_"); //$NON-NLS-1$
         if (schemaPath == null)
         {
            throw new AeException(AeMessages.getString("AeTaminoInstaller.NO_SCHEMA_PATH_FOUND_ERROR")); //$NON-NLS-1$
         }
         File schemaFile = new File(schemaPath);
         if (!schemaFile.isFile())
            throw new AeException(AeMessages.getString("AeTaminoInstaller.COULD_NOT_FIND_FILE_ERROR") + schemaPath); //$NON-NLS-1$
         AeTaminoSchema schema = new AeTaminoSchema(schemaFile);
         params.put("Collection", schema.getCollectionName()); //$NON-NLS-1$

         AeTaminoConfig config = new AeTaminoConfig(Collections.EMPTY_MAP);
         AeTaminoDataSource.MAIN = new AeTaminoDataSource(params);

         AeXMLDBPerformanceLogger.init(null); 
         
         Constructor ctor = aInstallerClass.getConstructor(new Class[] { AeTaminoSchema.class,
               AeTaminoConfig.class, Map.class, IAeXMLDBStorageImpl.class });
         Object[] ctorArgs = new Object[] { schema, config, params, new AeTaminoStorageImpl(AeTaminoDataSource.MAIN) };
         AeTaminoInstaller installer = (AeTaminoInstaller) ctor.newInstance(ctorArgs);
         installer.install("true".equals(params.get("_Drop_"))); //$NON-NLS-1$ //$NON-NLS-2$
      }
      catch (Throwable t)
      {
         t.printStackTrace();
      }
   }

   /**
    * Main entry point.
    * 
    * Usage:
    *    AeTaminoInstaller -schema=[pathtoschema] -url=[urltotamino] -db=[databasename] -domain=[autodomainname] -user=[authusername] -pass=[authpassword] -drop=[true|false] -verbose=[true|false] -seedfile=[pathToSeedFile]
    * 
    *    schema  = Path to the .tsd schema file to install
    *    url     = The URL to the Tamino server.
    *    db      = The name of the Tamino database to use.
    *    domain  = The name of the authentication domain (optional, defaults to empty).
    *    user    = The name of the user to authenticate as (optional, defaults to empty).
    *    pass    = The password of the above user (optional, defaults to emtpy).
    *    drop    = Flag indicating whether to drop the schema if it already exists (optional, defaults to 'false').
    *    clean   = Flag indicating whether the DB should be cleaned (all documents deleted)
    *    verbose = Flag indicating whether debug/status messages are printed out (optional, defaults to 'false').
    *    seedfile = An XML file containing document instances to insert into the DB.
    * 
    * @param aArgs
    */
   public static void main(String [] aArgs)
   {
      sharedMain(aArgs, AeTaminoInstaller.class);
   }

}
