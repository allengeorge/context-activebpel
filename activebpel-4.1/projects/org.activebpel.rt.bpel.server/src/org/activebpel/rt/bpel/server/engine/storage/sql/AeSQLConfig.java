// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeSQLConfig.java,v 1.20 2006/02/10 21:51:1
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
package org.activebpel.rt.bpel.server.engine.storage.sql;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageConfig;
import org.activebpel.rt.util.AeUtil;

/**
 * This class encapsulates the SQL statements used by the Active BPEL persistence
 * layer.  This class uses a DOM parser to first parse a "common" xml file that
 * contains the SQL statements.  It then parses a second xml file that is
 * specific to the database being used.  The values in the specific xml file
 * will override the values in the common file.
 */
public class AeSQLConfig extends AeStorageConfig
{
   private static final String SQL_ELEM_NAME = "sql"; //$NON-NLS-1$
   private static final String SQL_STATEMENT_ELEM_NAME = "sql-statement"; //$NON-NLS-1$

   /** The specific type of database being used.  For example, "mysql". */
   protected String mType;

   /**
    * Creates a SQL config object that will use the given type as the specific
    * database configuration to use.
    *
    * @param aType A type of database configuration to load, such as "mysql".
    */
   public AeSQLConfig(String aType)
   {
      this(aType, Collections.EMPTY_MAP);
   }
   
   /**
    * Creates a SQL config object with the given db type and a map of
    * constant overrides.
    * 
    * @param aType
    * @param aConstantOverrides
    */
   public AeSQLConfig(String aType, Map aConstantOverrides)
   {
      super(SQL_STATEMENT_ELEM_NAME, SQL_ELEM_NAME, aConstantOverrides);
      mType = aType;
   }

   /**
    * Returns the database type.
    */
   public String getDatabaseType()
   {
      return mType;
   }

   /**
    * Gets a SQL statement given a key (the name of the statement as configured
    * in the file).  This is a convenience method that simply delegates to the base
    * class's <code>getStatement</code> method.
    *
    * @param aKey A key that references a SQL statement in the config file.
    * @return A SQL statement.
    */
   public String getSQLStatement(String aKey)
   {
      return getStatement(aKey);
   }

   /**
    * Gets a SQL statement fragment that limits the query to the specified number
    * of rows or an empty string if the db doesn't support the operation.
    * @param aLimitValue
    */
   public String getLimitStatement(int aLimitValue)
   {
      String stmt = getSQLStatement("Generic.Limit"); //$NON-NLS-1$
      if (!AeUtil.isNullOrEmpty(stmt))
      {
         return " " + stmt.replaceFirst("\\?", "" + aLimitValue); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      }
      return ""; //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.AeStorageConfig#getStatementConfigFilenames()
    */
   protected List getStatementConfigFilenames()
   {
      String commonFileName = "common-sql.xml"; //$NON-NLS-1$
      String specificFileName = mType + "-sql.xml"; //$NON-NLS-1$

      List list = new LinkedList();
      list.add(new AeFilenameClassTuple(commonFileName, AeSQLConfig.class));
      list.add(new AeFilenameClassTuple(specificFileName, AeSQLConfig.class));
      
      return list;
   }
}
