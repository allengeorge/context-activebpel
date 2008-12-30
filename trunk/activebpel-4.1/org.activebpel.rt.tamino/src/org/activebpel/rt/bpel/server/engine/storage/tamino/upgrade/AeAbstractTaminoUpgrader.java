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

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoConfig;
import org.activebpel.rt.bpel.server.engine.storage.upgrade.IAeStorageUpgrader;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;

/**
 * A Tamino upgrader base class.  Upgraders are responsible for  
 */
public abstract class AeAbstractTaminoUpgrader extends AeAbstractTaminoStorageEx implements IAeStorageUpgrader
{
   /** The statement prefix for all statements used in this class. */
   private static final String STATEMENT_PREFIX = "Upgrade"; //$NON-NLS-1$

   /** The name of the schema being upgraded. */
   private String mSchemaName;
   
   /**
    * Creates a Tamino upgrader.
    */
   public AeAbstractTaminoUpgrader(String aSchemaName, IAeXMLDBStorageImpl aStorageImpl)
   {
      super(null, STATEMENT_PREFIX, aStorageImpl);
      setTaminoConfig(createTaminoConfig());
      setSchemaName(aSchemaName);
   }

   /**
    * Sets the config.
    * 
    * @param aConfig
    */
   protected void setTaminoConfig(AeTaminoConfig aConfig)
   {
      setXMLDBConfig(aConfig);
   }

   /**
    * Creates the Tamino config object.
    */
   protected abstract AeTaminoConfig createTaminoConfig();
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.upgrade.IAeStorageUpgrader#upgrade()
    */
   public abstract void upgrade() throws AeStorageException;

   /**
    * @return Returns the schemaName.
    */
   protected String getSchemaName()
   {
      return mSchemaName;
   }

   /**
    * @param aSchemaName The schemaName to set.
    */
   protected void setSchemaName(String aSchemaName)
   {
      mSchemaName = aSchemaName;
   }
}
