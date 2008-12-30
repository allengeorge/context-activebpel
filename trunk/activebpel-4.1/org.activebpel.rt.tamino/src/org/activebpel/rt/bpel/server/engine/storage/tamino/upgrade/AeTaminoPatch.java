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
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.tamino.installer.AeTaminoSchema;
import org.activebpel.rt.bpel.server.engine.storage.upgrade.IAeStorageUpgrader;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBCollectionResponseHandler;
import org.activebpel.rt.tamino.AeMessages;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A Tamino patch.  The patch is backed by two files, a Tamino tsd schema file and an XML file
 * that contains a list of xquery statements to run after upgrading the Tamino schema.
 */
public class AeTaminoPatch extends AeAbstractTaminoStorageEx implements Comparable
{
   /** The Tamino patch schema (the temp schema, for example ActiveBPEL_Enterprise-Tamino-Patch-2.1.tsd). */
   private AeTaminoSchema mSchema;
   /** The patch file (for example ActiveBPEL_Enterprise-Tamino-Patch-2.1.xml). */
   private Document mPatchDom;
   /** The patch version. */
   private AeDBVersion mVersion;

   /**
    * Default constructor.
    *
    * @param aPatchFile
    * @param aStorageImpl
    * @throws AeStorageException
    */
   public AeTaminoPatch(File aPatchFile, IAeXMLDBStorageImpl aStorageImpl) throws AeStorageException
   {
      super(null, null, aStorageImpl);

      setPatchDom(loadPatchDom(aPatchFile));
      setSchema(loadSchema(getSchemaFile(aPatchFile)));
      setVersion(new AeDBVersion(getPatchDom().getDocumentElement().getAttribute("version"))); //$NON-NLS-1$
   }

   /**
    * Gets the schema file.
    *
    * @param aPatchFile
    * @throws AeStorageException
    */
   protected File getSchemaFile(File aPatchFile) throws AeStorageException
   {
      String schemaName = getPatchDom().getDocumentElement().getAttribute("schema"); //$NON-NLS-1$
      File file = new File(aPatchFile.getParentFile(), schemaName);
      if (!file.isFile())
      {
         throw new AeStorageException(AeMessages.getString("AeTaminoPatch.NO_SCHEMA_FOUND")); //$NON-NLS-1$
      }
      return file;
   }

   /**
    * Loads the patch file.
    *
    * @param aPatchFile
    */
   protected Document loadPatchDom(File aPatchFile) throws AeStorageException
   {
      try
      {
         InputStream is = new FileInputStream(aPatchFile);
         return new AeXMLParserBase(false, false).loadDocument(is, null);
      }
      catch (Exception ex)
      {
         throw new AeStorageException(ex);
      }
   }

   /**
    * Returns true if this patch is within the desired range of DB versions.
    *
    * @param aCurrentVersion
    * @param aDesiredVersion
    */
   public boolean isInRange(AeDBVersion aCurrentVersion, AeDBVersion aDesiredVersion)
   {
      // True if the patch version is GREATER THAN the current version
      //     and the patch version is LESS THAN OR EQUAL TO the desired version
      return (getVersion().compareTo(aCurrentVersion) > 0)
            && (getVersion().compareTo(aDesiredVersion) <= 0);
   }

   /**
    * Applies this patch to the Tamino database.
    *
    * @param aCollectionName
    * @param aSchemaName
    * @throws AeStorageException
    */
   public void apply(String aCollectionName, String aSchemaName) throws AeStorageException
   {
      AeTaminoSchema schema = getSchema();

      // Set the patch schema's name and collection name.
      schema.setCollectionName(aCollectionName);
      schema.setSchemaName(aSchemaName);

      // First, define the patch schema.
      output(AeMessages.getString("AeTaminoPatch.DEFINING_PATCH_SCHEMA"), new Object[] { getVersion().getVersion() }); //$NON-NLS-1$
      defineSchema(schema);
      output(AeMessages.getString("AeTaminoPatch.PATCH_SCHEMA_DEFINED"), new Object[] { getVersion().getVersion() }); //$NON-NLS-1$

      // Next, run all of the upgrade statements found in the queries file.
      try
      {
         // Note - loadDocument closes the input stream.
         NodeList nl = getPatchDom().getDocumentElement().getElementsByTagName("upgrade-statement"); //$NON-NLS-1$
         for (int i = 0; i < nl.getLength(); i++)
         {
            Element upgradeStatementElem = (Element) nl.item(i);
            String name = upgradeStatementElem.getAttribute("name"); //$NON-NLS-1$
            String type = upgradeStatementElem.getAttribute("type"); //$NON-NLS-1$
            String val = AeXmlUtil.getText(upgradeStatementElem);

            runUpgradeStatement(name, type, val);
         }
      }
      catch (AeStorageException ex)
      {
         throw ex;
      }
      catch (Exception ex)
      {
         throw new AeStorageException(ex);
      }
   }

   /**
    * Applies a single upgrade statement found in the queries file.
    *
    * @param aName
    * @param aType
    * @param aValue
    * @throws AeStorageException
    */
   protected void runUpgradeStatement(String aName, String aType, String aValue) throws AeStorageException
   {
      output(AeMessages.getString("AeTaminoPatch.APPLYING_UPGRADE_STATEMENT"), new Object[] { aName }); //$NON-NLS-1$
      if ("xquery".equals(aType)) //$NON-NLS-1$
      {
         runXQueryUpgradeStatement(aValue);
      }
      else if ("select-into".equals(aType)) //$NON-NLS-1$
      {
         runSelectIntoUpgradeStatement(aValue);
      }
      else if ("undefine-doctype".equals(aType)) //$NON-NLS-1$
      {
         runUndefineDoctypeUpgradeStatement(aValue);
      }
      else if ("java".equals(aType)) //$NON-NLS-1$
      {
         runJavaUpgradeStatement(aValue);
      }
      else
      {
         throw new AeStorageException(AeMessages.getString("AeTaminoPatch.UNKNOWN_UPGRADE_STATEMENT_TYPE") + aType); //$NON-NLS-1$
      }
   }

   /**
    * Runs a select-into upgrade statement.  This type of statement is an XQuery FLOWR statement
    * whose return List is then inserted back into the database.  Each item in the result set
    * is expected to be XML conforming to some other doc type in the schema.
    *
    * @param aValue
    * @throws AeStorageException
    */
   private void runSelectIntoUpgradeStatement(String aValue) throws AeStorageException
   {
      doXQuery(aValue, new AeXMLDBCollectionResponseHandler() {
         /**
          * @see org.activebpel.rt.bpel.server.engine.storage.tamino.handlers.AeTaminoCollectionResponseHandler#handleElement(org.w3c.dom.Element)
          */
         protected Object handleElement(Element aElement) throws AeXMLDBException
         {
            insertDocument(aElement);
            return null;
         }

         /**
          * @see org.activebpel.rt.bpel.server.engine.storage.tamino.handlers.AeTaminoCollectionResponseHandler#createCollection()
          */
         protected Collection createCollection()
         {
            // This doesn't matter - this handler is not going to return anything meaningful.  It
            // is only even used because it iterates through the result set and we want to leverage
            // that logic.
            return new HashSet();
         }
      });
   }

   /**
    * Runs an undefine-doctype upgrade statement.  This type of statement simply indicates the
    * name of a top-level doc type that should be undefined.
    *
    * @param aValue
    * @throws AeStorageException
    */
   private void runUndefineDoctypeUpgradeStatement(String aValue) throws AeStorageException
   {
      undefineDocType(getCollectionName(), getSchema().getSchemaName(), aValue);
   }

   /**
    * Runs a Java upgrade statement.
    *
    * @param aValue
    * @throws AeStorageException
    */
   private void runJavaUpgradeStatement(String aValue) throws AeStorageException
   {
      try
      {
         String classname = aValue;
         Class clazz = Class.forName(classname);
         Constructor constructor = clazz.getConstructor(new Class[] { String.class, IAeXMLDBStorageImpl.class });
         IAeStorageUpgrader upgrader = (IAeStorageUpgrader) constructor.newInstance(new Object[] { getSchema().getSchemaName(), getStorageImpl() });
         upgrader.upgrade();
      }
      catch (AeStorageException ex)
      {
         throw ex;
      }
      catch (Exception ex)
      {
         throw new AeStorageException(ex);
      }
   }

   /**
    * Runs a simple xquery upgrade statement.  The XQuery is run as-is without processing any
    * return values.  This is useful when the XQuery is an update or delete based XQuery.
    *
    * @param aValue
    * @throws AeStorageException
    */
   private void runXQueryUpgradeStatement(String aValue) throws AeStorageException
   {
      String xquery = aValue;
      doXQuery(xquery);
   }

   /**
    * Gets the Tamino schema object for this patch.
    */
   protected AeTaminoSchema getSchema() throws AeStorageException
   {
      return mSchema;
   }

   /**
    * Loads the schema from the file.
    *
    * @param aFile
    * @throws AeStorageException
    */
   protected AeTaminoSchema loadSchema(File aFile) throws AeStorageException
   {
      try
      {
         return new AeTaminoSchema(aFile);
      }
      catch (Exception ex)
      {
         throw new AeStorageException(ex);
      }
   }

   /**
    * @return Returns the version.
    */
   public AeDBVersion getVersion()
   {
      return mVersion;
   }

   /**
    * @param aVersion The version to set.
    */
   protected void setVersion(AeDBVersion aVersion)
   {
      mVersion = aVersion;
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
    * @param aSchema The schema to set.
    */
   protected void setSchema(AeTaminoSchema aSchema)
   {
      mSchema = aSchema;
   }

   /**
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(Object aOther)
   {
      AeTaminoPatch other = (AeTaminoPatch) aOther;
      return getVersion().compareTo(other.getVersion());
   }

   /**
    * @return Returns the patchDom.
    */
   protected Document getPatchDom()
   {
      return mPatchDom;
   }

   /**
    * @param aPatchDom The patchDom to set.
    */
   protected void setPatchDom(Document aPatchDom)
   {
      mPatchDom = aPatchDom;
   }
}
