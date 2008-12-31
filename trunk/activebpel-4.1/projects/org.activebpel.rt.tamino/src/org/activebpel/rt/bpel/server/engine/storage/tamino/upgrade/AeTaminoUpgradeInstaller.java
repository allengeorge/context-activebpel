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

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoConfig;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoDataSource;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoStorageImpl;
import org.activebpel.rt.bpel.server.engine.storage.tamino.installer.AeTaminoSchema;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBResponseHandler;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.log.AeXMLDBPerformanceLogger;
import org.activebpel.rt.tamino.AeMessages;

/**
 * The Tamino upgrader is responsible for upgrading a Tamino database from one version of the
 * ActiveBPEL schema to a newer one.  The upgrader will automatically detect the current DB
 * version and then programmatically upgrade the database to the latest version.
 *
 * TODO (EPW) share this code with the AeTaminoInstaller - cmd line params are similar, main()'s are similar, etc.
 */
public class AeTaminoUpgradeInstaller extends AeAbstractTaminoStorageEx
{
   protected static Map sKeyLookup = new HashMap();

   static
   {
      sKeyLookup.put("url", "URL"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("db", "Database"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("coll", "Collection"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("domain", "Domain"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("user", "Username"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("pass", "Password"); //$NON-NLS-1$ //$NON-NLS-2$

      sKeyLookup.put("patchdir", "_PatchDir_"); //$NON-NLS-1$ //$NON-NLS-2$
      sKeyLookup.put("schema", "_Schema_"); //$NON-NLS-1$ //$NON-NLS-2$
   }

   /** The command line params. */
   private Map mParams;
   /** The Tamino schema to install (the latest schema). */
   private AeTaminoSchema mSchema;

   /**
    * Constructs the tamino upgrader given the Tamino config and command line params.
    *
    * @param aSchema
    * @param aConfig
    * @param aParams
    * @param aStorageImpl
    */
   public AeTaminoUpgradeInstaller(AeTaminoSchema aSchema, AeTaminoConfig aConfig, Map aParams,
         IAeXMLDBStorageImpl aStorageImpl)
   {
      super(aConfig, "Upgrader", aStorageImpl); //$NON-NLS-1$
      setSchema(aSchema);
      setParams(aParams);
   }

   /**
    * Upgrades a Tamino database to the latest version of the Tamino schema.  This will upgrade
    * the database from any previous version to the latest (as found in version.properties).
    *
    * @throws AeStorageException
    */
   public void upgrade() throws AeStorageException
   {
      output(AeMessages.getString("AeTaminoUpgradeInstaller.UPGRADING_TAMINO")); //$NON-NLS-1$
      output(AeMessages.getString("AeTaminoUpgradeInstaller.URL"), new Object[] { getParams().get("URL") });  //$NON-NLS-1$//$NON-NLS-2$
      output(AeMessages.getString("AeTaminoUpgradeInstaller.DATABASE"), new Object[] { getParams().get("Database") });  //$NON-NLS-1$//$NON-NLS-2$
      output(AeMessages.getString("AeTaminoUpgradeInstaller.COLLECTION"), new Object[] { getParams().get("Collection") });  //$NON-NLS-1$//$NON-NLS-2$

      AeDBVersion desiredVersion = null;
      AeDBVersion currentVersion = new AeDBVersion(getCurrentDBVersion());
      output(AeMessages.getString("AeTaminoUpgradeInstaller.DATABASE_IS_VERSION"), new Object[] { currentVersion.getVersion() }); //$NON-NLS-1$
      if (currentVersion.isEnterprise())
      {
         desiredVersion = new AeDBVersion(getEnterpriseDBVersion());
      }
      else
      {
         desiredVersion = new AeDBVersion(getDBVersion());
      }
      output(AeMessages.getString("AeTaminoUpgradeInstaller.UPGRADING_TO_DB_VERSION"), new Object[] { desiredVersion.getVersion() }); //$NON-NLS-1$

      // Stop if the current version and the desired version are the same.
      if (currentVersion.equals(desiredVersion))
      {
         output(AeMessages.getString("AeTaminoUpgradeInstaller.DATABASE_IS_UPTODATE")); //$NON-NLS-1$
         return;
      }

      output(AeMessages.getString("AeTaminoUpgradeInstaller.GETTING_CURRENT_SCHEMA_IN_COLLECTION"), //$NON-NLS-1$
            new Object[] { getCollectionName() });
      AeTaminoSchema currentSchema = getCurrentSchema();

      // Get the set of patches found in the patch directory.
      SortedSet patches = loadPatches(getCollectionName(), currentSchema.getSchemaName());

      // Now apply each patch that is in the desired version range.
      for (Iterator iter = patches.iterator(); iter.hasNext(); )
      {
         AeTaminoPatch patch = (AeTaminoPatch) iter.next();
         // Only apply the patch if it is greater than the current version.
         if (patch.getVersion().compareTo(currentVersion) > 0)
         {
            // Only apply the patch if it is less than or equal to the desired version.
            if (patch.getVersion().compareTo(desiredVersion) <= 0)
            {
               output(AeMessages.getString("AeTaminoUpgradeInstaller.APPLYING_PATCH"), new Object[] { patch.getVersion().getVersion() }); //$NON-NLS-1$
               patch.apply(getCollectionName(), currentSchema.getSchemaName());
            }
         }
      }
      output(AeMessages.getString("AeTaminoUpgradeInstaller.ALL_PATCHES_APPLIED")); //$NON-NLS-1$

      // Set the final schema's collection name (to use whatever collection name is installed).
      output(AeMessages.getString("AeTaminoUpgradeInstaller.DEFINING_FINAL_SCHEMA")); //$NON-NLS-1$
      AeTaminoSchema finalSchema = getSchema();
      finalSchema.setCollectionName(getCollectionName());
      finalSchema.setSchemaName(currentSchema.getSchemaName());
      defineSchema(finalSchema);
      output(AeMessages.getString("AeTaminoUpgradeInstaller.FINAL_SCHEMA_DEFINED")); //$NON-NLS-1$

      // Final set of the DB version in case the upgrade did not require an iterative patch.
      setCurrentDBVersion(desiredVersion);

      output(AeMessages.getString("AeTaminoUpgradeInstaller.DATABASE_IS_NOW"), new Object[] { getCurrentDBVersion() }); //$NON-NLS-1$
      output("All done."); //$NON-NLS-1$
   }

   /**
    * Loads the patches from the patch directory.
    *
    * @param aCollectionName
    * @param aSchemaName
    * @throws AeStorageException
    */
   protected SortedSet loadPatches(String aCollectionName, String aSchemaName) throws AeStorageException
   {
      String patchDirName = (String) getParams().get("_PatchDir_"); //$NON-NLS-1$
      File patchDir = new File(patchDirName);
      if (!patchDir.isDirectory())
      {
         throw new AeStorageException(AeMessages.getString("AeTaminoUpgradeInstaller.PATCH_DIR_NOT_FOUND")); //$NON-NLS-1$
      }

      // Get a list of all Tamino patch files in the patch directory.
      File [] patchFiles = patchDir.listFiles(new FilenameFilter()
      {
         /**
          * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
          */
         public boolean accept(File aDir, String aName)
         {
            return aName.endsWith(".xml") && aName.indexOf("Tamino") != -1; //$NON-NLS-1$ //$NON-NLS-2$
         }
      });

      SortedSet patchSet = new TreeSet();

      // For each .tsd patch file, create an AeTaminoPatch object.
      for (int i = 0; i < patchFiles.length; i++)
      {
         File patchFile = patchFiles[i];
         patchSet.add(new AeTaminoPatch(patchFile, getStorageImpl()));
      }

      return patchSet;
   }

   /**
    * Retrieves the current version of the database.
    */
   protected String getCurrentDBVersion() throws AeStorageException
   {
      return (String) query("GetCurrentDBVersion", AeXMLDBResponseHandler.STRING_RESPONSE_HANDLER); //$NON-NLS-1$
   }

   /**
    * Updates the DB with a new version number.
    *
    * @param aDBVersion
    */
   protected void setCurrentDBVersion(AeDBVersion aDBVersion) throws AeStorageException
   {
      updateDocuments("SetCurrentDBVersion", new Object [] { aDBVersion.toString() }); //$NON-NLS-1$
   }

   /**
    * Creates the instance of AeBuildNumber and calls "getDBVersion" on it.
    */
   protected String getDBVersion(String aBuildNumberClassname)
   {
      try
      {
         Class aeBuildNumberClass = Class.forName(aBuildNumberClassname);
         Method method = aeBuildNumberClass.getMethod("getDBVersion", null); //$NON-NLS-1$
         return (String) method.invoke(null, null);
      }
      catch (Exception ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * Gets the DB version from the Enterprise AeBuildNumber class.
    */
   protected String getEnterpriseDBVersion()
   {
      return getDBVersion("com.activee.awf.ddl.AeBuildNumber"); //$NON-NLS-1$
   }

   /**
    * Gets the DB version from the ActiveBPEL AeBuildNumber class.
    */
   protected String getDBVersion()
   {
      return getDBVersion("org.activebpel.ddl.AeBuildNumber"); //$NON-NLS-1$
   }

   /**
    * Checks for all of the required params.
    *
    * @param aParams
    * @throws Exception
    */
   protected static void checkParams(Map aParams) throws Exception
   {
      for (Iterator iter = sKeyLookup.entrySet().iterator(); iter.hasNext(); )
      {
         Map.Entry entry = (Map.Entry) iter.next();
         if (aParams.get(entry.getValue()) == null)
         {
            throw new RuntimeException(AeMessages.getString("AeTaminoUpgradeInstaller.MISSING_COMMAND_LINE_PARAM") + entry.getKey()); //$NON-NLS-1$
         }
      }
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
               else
               {
                  throw new RuntimeException(AeMessages.getString("AeTaminoUpgradeInstaller.UNKNOWN_CMD_LINE_PARAM") + m.group(1)); //$NON-NLS-1$
               }
            }
         }

         checkParams(params);

         // Load the final schema.
         String schemaPath = (String) params.get("_Schema_"); //$NON-NLS-1$
         File schemaFile = new File(schemaPath);
         if (!schemaFile.isFile())
            throw new AeException(AeMessages.getString("AeTaminoUpgradeInstaller.MISSING_SCHEMA") + schemaPath); //$NON-NLS-1$
         AeTaminoSchema schema = new AeTaminoSchema(schemaFile);

         // Create the Tamino config.
         AeTaminoConfig config = new AeTaminoConfig(Collections.EMPTY_MAP);
         // Create the Tamino data source.
         AeTaminoDataSource.MAIN = new AeTaminoDataSource(params);

         AeXMLDBPerformanceLogger.init(null);

         IAeXMLDBStorageImpl storageImpl = new AeTaminoStorageImpl(AeTaminoDataSource.MAIN);;

         // Now construct the Tamino upgrader instance.
         Constructor c = aInstallerClass.getConstructor(new Class [] { AeTaminoSchema.class, AeTaminoConfig.class, Map.class, IAeXMLDBStorageImpl.class });
         AeTaminoUpgradeInstaller upgrader = (AeTaminoUpgradeInstaller) c.newInstance(new Object[] { schema, config, params, storageImpl });
         upgrader.upgrade();
      }
      catch (Throwable t)
      {
         t.printStackTrace();
      }
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
    * Prints out an output message.
    *
    * @param aMessage
    */
   protected void output(String aMessage)
   {
      System.out.println(aMessage);
   }

   /**
    * Prints out an output message.
    *
    * @param aMessage
    */
   protected void output(String aMessage, Object[] aParams)
   {
      System.out.println(MessageFormat.format(aMessage, aParams));
   }

   /**
    * Main entry point.
    *
    * Usage:
    *    AeTaminoUpgrader -schema=[pathtoschemafile] -patchdir=[pathtopatchdirectory] -url=[urltotamino] -db=[databasename] -coll=[collectionname] -domain=[autodomainname] -user=[authusername] -pass=[authpassword]
    *
    *    schema   = Path to the .tsd schema file to install
    *    patchdir = Path to the directory that contains the ActiveBPEL patch scripts (schemas and query files)
    *    url      = The URL to the Tamino server.
    *    db       = The name of the Tamino database to use.
    *    coll     = The name of the collection to use.
    *    domain   = The name of the authentication domain.
    *    user     = The name of the user to authenticate as.
    *    pass     = The password of the above user.
    *
    * @param aArgs
    */
   public static void main(String [] aArgs)
   {
      sharedMain(aArgs, AeTaminoUpgradeInstaller.class);
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
}
