//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/upgrade/AeAbstractUpgraderSQLConfig.java,v 1.2 2006/02/10 21:51:1
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
package org.activebpel.rt.bpel.server.engine.storage.sql.upgrade;

import java.util.LinkedList;
import java.util.List;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig;
import org.activebpel.rt.util.AeUtil;

/**
 * Extends the base <code>AeSQLConfig</code> class in order to supply additional SQL statements loaded from
 * the upgrade-sql.xml file.
 */
public abstract class AeAbstractUpgraderSQLConfig extends AeSQLConfig
{
   /** The default config to use when a statement is not found. */
   private AeSQLConfig mDefaultConfig;

   /**
    * Constructs an upgrader sql config object.
    * 
    * @param aDefaultConfig
    */
   public AeAbstractUpgraderSQLConfig(AeSQLConfig aDefaultConfig)
   {
      super(aDefaultConfig.getDatabaseType(), aDefaultConfig.getConstantOverrides());
      setDefaultConfig(aDefaultConfig);
   }

   /**
    * @return Returns the defaultConfig.
    */
   protected AeSQLConfig getDefaultConfig()
   {
      return mDefaultConfig;
   }

   /**
    * @param aDefaultConfig The defaultConfig to set.
    */
   protected void setDefaultConfig(AeSQLConfig aDefaultConfig)
   {
      mDefaultConfig = aDefaultConfig;
   }

   /**
    * First looks up the statement in its own registry.  If not found, the statement will be looked
    * up in the default config.
    * 
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig#getSQLStatement(java.lang.String)
    */
   public String getSQLStatement(String aKey)
   {
      String sql = super.getSQLStatement(aKey);
      if (AeUtil.isNullOrEmpty(sql))
      {
         sql = getDefaultConfig().getSQLStatement(aKey);
      }
      return sql;
   }

   /**
    * First looks up the statement in its own registry.  If not found, the statement will be looked
    * up in the default config.
    * 
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig#getLimitStatement(int)
    */
   public String getLimitStatement(int aLimitValue)
   {
      String stmt = super.getLimitStatement(aLimitValue);
      if (AeUtil.isNullOrEmpty(stmt))
      {
         stmt = getDefaultConfig().getLimitStatement(aLimitValue);
      }
      return stmt;
   }

   /**
    * First looks up the parameter in its own registry.  If not found, the parameter will be looked
    * up in the default config.
    * 
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig#getParameter(java.lang.String)
    */
   public String getParameter(String aKey) throws AeStorageException
   {
      try
      {
         return super.getParameter(aKey);
      }
      catch (AeStorageException se)
      {
         return getDefaultConfig().getParameter(aKey);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig#getStatementConfigFilenames()
    */
   protected List getStatementConfigFilenames()
   {
      String commonFileName = "upgrade-sql.xml"; //$NON-NLS-1$
      String specificFileName = "upgrade-" + mType + "-sql.xml"; //$NON-NLS-1$ //$NON-NLS-2$

      List list = new LinkedList();
      list.add(new AeFilenameClassTuple(commonFileName, getClass()));
      list.add(new AeFilenameClassTuple(specificFileName, getClass()));

      return list;
   }
}
