//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/AeXMLDBValidator.java,v 1.1 2007/08/17 00:40:5
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

import java.text.MessageFormat;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.config.IAeEngineConfiguration;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBResponseHandler;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xmldb.AeMessages;

/**
 * This class validates the state of the XMLDB database.
 */
public class AeXMLDBValidator
{
   /** The storage to use. */
   private AeAbstractXMLDBStorage mStorage;
   /** The engine config map. */
   private Map mEngineConfigMap;
   /** Storage impl. */
   private IAeXMLDBStorageImpl mStorageImpl;
   
   /**
    * Creates a xmldb validator with the given config.
    * 
    * @param aConfig
    * @param aEngineConfigMap
    * @param aStorageImpl
    */
   public AeXMLDBValidator(AeXMLDBConfig aConfig, Map aEngineConfigMap, IAeXMLDBStorageImpl aStorageImpl)
   {
      setEngineConfigMap(aEngineConfigMap);
      setStorageImpl(aStorageImpl);
      setStorage(createStorage(aConfig, aEngineConfigMap));
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBValidator#createStorage(org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBConfig, java.util.Map)
    */
   protected AeAbstractXMLDBStorage createStorage(AeXMLDBConfig aConfig, Map aEngineConfigMap)
   {
      return new AeXMLDBValidatorStorage(aConfig, getStorageImpl());
   }

   /**
    * Validates that the version of the schema defined in the XMLDB database is the correct version.
    */
   public void validateDBVersion() throws AeException
   {
      String versionInDB = (String) getStorage().query("GetVersion", AeXMLDBResponseHandler.STRING_RESPONSE_HANDLER); //$NON-NLS-1$
      String versionInConfig = (String) getEngineConfigMap().get(IAeEngineConfiguration.PERSISTENT_VERSION_ENTRY);

      if (AeUtil.isNullOrEmpty(versionInDB))
      {
         throw new AeException(AeMessages.getString("AeXMLDBValidator.NO_VERSION_INFO_ERROR")); //$NON-NLS-1$
      }

      if (!versionInDB.equals(versionInConfig))
      {
         String msg = MessageFormat.format(AeMessages.getString("AeXMLDBValidator.INCORRECT_DB_VERSION_ERROR"), //$NON-NLS-1$
               new Object[] { versionInConfig, versionInDB });
         throw new AeException(msg);
      }
   }

   /**
    * @return Returns the storage.
    */
   protected AeAbstractXMLDBStorage getStorage()
   {
      return mStorage;
   }

   /**
    * @param aStorage the storage to set
    */
   protected void setStorage(AeAbstractXMLDBStorage aStorage)
   {
      mStorage = aStorage;
   }

   /**
    * @return Returns the engineConfigMap.
    */
   protected Map getEngineConfigMap()
   {
      return mEngineConfigMap;
   }

   /**
    * @param aEngineConfigMap the engineConfigMap to set
    */
   protected void setEngineConfigMap(Map aEngineConfigMap)
   {
      mEngineConfigMap = aEngineConfigMap;
   }


   /**
    * Storage class used by the validator.
    */
   protected class AeXMLDBValidatorStorage extends AeAbstractXMLDBStorage
   {
      /**
       * C'tor.
       *
       * @param aConfig
       * @param aStorageImpl
       */
      public AeXMLDBValidatorStorage(AeXMLDBConfig aConfig, IAeXMLDBStorageImpl aStorageImpl)
      {
         super(aConfig, "Validator", aStorageImpl); //$NON-NLS-1$
      }
   }


   /**
    * @return Returns the storageImpl.
    */
   protected IAeXMLDBStorageImpl getStorageImpl()
   {
      return mStorageImpl;
   }

   /**
    * @param aStorageImpl the storageImpl to set
    */
   protected void setStorageImpl(IAeXMLDBStorageImpl aStorageImpl)
   {
      mStorageImpl = aStorageImpl;
   }
}
