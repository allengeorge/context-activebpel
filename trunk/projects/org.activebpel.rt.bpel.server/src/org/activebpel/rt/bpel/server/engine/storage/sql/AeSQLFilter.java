//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeSQLFilter.java,v 1.10 2007/04/03 20:54:3
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

import java.util.ArrayList;
import java.util.List;

import org.activebpel.rt.bpel.impl.list.IAeListingFilter;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.util.AeUtil;

/**
 * Base class for building a SQL statement from a filter object.
 */
abstract public class AeSQLFilter
{
   /** The WHERE clause of the SQL statement. */
   private final StringBuffer mConditions = new StringBuffer();

   /** The values of the replacement parameters specified by the WHERE clause. */
   private final List mParams = new ArrayList();

   /** The process filter specification. */
   private IAeListingFilter mFilter;

   /** Config object used to load sql statements */
   private AeSQLConfig mConfig;

   /** The prefix to use when retrieving keys from the SQL config. */
   private String mConfigPrefix;

   /** max number of rows that we'll read in order to determine the total number of rows in result set */
   public static final int REPORT_SCAN_LIMIT = 500;
   
   /** select clause of the sql statement */
   private String mSelectClause = ""; //$NON-NLS-1$
   
   /** count clause of the sql statement */
   private String mCountClause = ""; //$NON-NLS-1$

   /** delete clause of the sql statement */
   private String mDeleteClause = ""; //$NON-NLS-1$
   
   /** order by clause of the sql statement */
   private String mOrderBy = ""; //$NON-NLS-1$

   /**
    * Constructor.
    * 
    * @param aFilter
    * @param aConfig
    * @param aPrefix
    * @throws AeStorageException
    */
   public AeSQLFilter(IAeListingFilter aFilter, AeSQLConfig aConfig, String aPrefix)
   	throws AeStorageException
   {
      mConfig = aConfig;
      mConfigPrefix = aPrefix;
      setFilter(aFilter);
      processFilter();
   }

   /**
    * Clears the where clause and any of the params set
    */
   protected void clearWhereClause()
   {
      getConditions().setLength(0);
      mParams.clear();
   }

   /**
    * Extracts all of the data from the filter in order to build the query.
    * @throws AeStorageException
    */
   abstract protected void processFilter() throws AeStorageException;

   /**
    * Appends a filter condition w/o a value.
    * 
    * @param aCondition
    */
   protected void appendCondition(String aCondition)
   {
      appendCondition(aCondition, (Object[])null);
   }

   /**
    * Appends a filter condition and the corresponding parameter value.
    * 
    * @param aCondition
    * @param aParam
    */
   protected void appendCondition(String aCondition, Object aParam)
   {
      if (AeUtil.notNullOrEmpty(aCondition))
      {
         appendCondition(aCondition, new Object[] { aParam });
      }
   }

   /**
    * Appends a filter condition with corresponding parameter values.
    * 
    * @param aCondition
    * @param aParams
    */
   protected void appendCondition(String aCondition, Object[] aParams)
   {
      if (AeUtil.notNullOrEmpty(aCondition))
      {
         mConditions.append((mConditions.length() == 0) ? "" : " AND "); //$NON-NLS-1$ //$NON-NLS-2$
         mConditions.append(aCondition);
   
         if (aParams != null)
         {
            for (int i = 0; i < aParams.length; ++i)
            {
               mParams.add(aParams[i]);
            }
         }
      }
   }

   /**
    * Returns DELETE clause.
    */
   protected String getDeleteClause() throws AeStorageException
   {
      return mDeleteClause;
   }
   
   /**
    * Setter for the DELETE clause
    * @param aDeleteClause
    */
   protected void setDeleteClause(String aDeleteClause)
   {
      mDeleteClause = aDeleteClause;
   }

   /**
    * Returns the generated SQL statement for DELETE.
    */
   public String getDeleteStatement() throws AeStorageException
   {
      String delete = getDeleteClause();
      if (AeUtil.isNullOrEmpty(delete))
      {
         throw new AeStorageException(AeMessages.getString("AeSQLFilter.DeletesNotSupported")); //$NON-NLS-1$
      }
      StringBuffer buffer = new StringBuffer();
      buffer.append(delete).append(' ');
      String conditions = getConditions().toString();
      if (!AeUtil.isNullOrEmpty(conditions))
      {
         buffer.append(getWhereKeyword());
         buffer.append(conditions).append(' ');
      }
      return buffer.toString();
   }

   /**
    * Returns the WHERE keyword.  Subclasses may want to override if they already have a 
    * WHERE line in the SELECT portion of their statement.
    */
   protected String getWhereKeyword()
   {
      return " WHERE "; //$NON-NLS-1$
   }
   
   /**
    * Returns the process filter specification.
    */
   protected IAeListingFilter getFilter()
   {
      return mFilter;
   }

   /**
    * Returns MySQL-style LIMIT clause, if applicable.
    */
   protected String getLimit() throws AeStorageException
   {
      if (mFilter == null)
      {
         return ""; //$NON-NLS-1$
      }

      // The limit should be large enough to accomodate the requested rows
      // plus the additional scanning that we perform in
      // AeSQLProcessListResultSetHandler#handle to count the total number
      // of rows.
      int rows = mFilter.getListStart() + mFilter.getMaxReturn() + REPORT_SCAN_LIMIT + 1;

      return getSQLConfig().getLimitStatement(rows);
   }

   /**
    * Getter for the sql config
    */
   protected AeSQLConfig getSQLConfig()
   {
      return mConfig;
   }

   /**
    * Returns ORDER BY clause (which should include ORDER BY keywords)
    * @throws AeStorageException
    */
   protected String getOrderBy() throws AeStorageException
   {
      return mOrderBy;
   }
   
   /**
    * Setter for the ORDER BY clause of the sql statement
    * @param aOrderBy
    */
   protected void setOrderBy(String aOrderBy)
   {
      mOrderBy = aOrderBy;
   }

   /**
    * Returns the array of replacement parameter values.
    */
   public Object[] getParams()
   {
      return mParams.toArray();
   }

   /**
    * Returns SELECT clause.
    */
   protected String getSelectClause() throws AeStorageException
   {
      return mSelectClause;
   }
   
   /**
    * Setter for the select clause
    * @param aSelectClause
    */
   protected void setSelectClause(String aSelectClause)
   {
      mSelectClause = aSelectClause;
   }

   /**
    * Returns Join clause for select, default is null.
    */
   protected String getJoinClause() throws AeStorageException
   {
      return null;
   }
   
   /**
    * Appends the remaining select statement to the buffer.  This is broken out so that the
    * count and select statements can share it.
    * 
    * @param aBuffer
    * @param aForCount
    * @throws AeStorageException
    */
   protected void appendRemainingSelectStatement(StringBuffer aBuffer, boolean aForCount) throws AeStorageException
   {
      aBuffer.append(' ');
      
      // append joins if any
      String joinClause = getJoinClause();
      if(AeUtil.notNullOrEmpty(joinClause))
         aBuffer.append(joinClause).append(' ');
      
      // append conditions
      String conditions = getConditions().toString();
      if (AeUtil.notNullOrEmpty(conditions))
         aBuffer.append(getWhereKeyword()).append(conditions).append(' ');
      
      if (!aForCount)
      {
         // append order by
         aBuffer.append(getOrderBy()).append(' ');
         
         // append limits
         aBuffer.append(getLimit());
      }
   }
   
   /**
    * Returns the generated SQL statement for the count.
    * 
    * @throws AeStorageException
    */
   public String getCountStatement() throws AeStorageException
   {
      // create the count statement and setup main count/select clause
      StringBuffer buffer = new StringBuffer(getCountClause());
      
      appendRemainingSelectStatement(buffer, true);
      
      // return full select statement
      return buffer.toString();
   }
   
   /**
    * Returns the generated SQL statement for SELECT.
    */
   public String getSelectStatement() throws AeStorageException
   {
      // create the select statement and setup main select clause
      StringBuffer buffer = new StringBuffer(getSelectClause());
      buffer.append(' ');
      
      appendRemainingSelectStatement(buffer, false);
      
      // return full select statement
      return buffer.toString();
   }

   /**
    * Returns generated WHERE clause.
    */
   protected StringBuffer getConditions()
   {
      return mConditions;
   }

   /**
    * Sets the filter to use for generating the SQL statement.
    * 
    * @param aFilter
    */
   protected void setFilter(IAeListingFilter aFilter)
   {
      mFilter = aFilter;
   }

   /**
    * Returns a SQL statement from the SQL config given a key.
    * 
    * @param aSqlKey
    */
   protected String getSQLStatement(String aSqlKey)
   {
      return getSQLConfig().getSQLStatement(mConfigPrefix + aSqlKey);
   }

   /**
    * @return Returns the countClause.
    */
   protected String getCountClause()
   {
      return mCountClause;
   }

   /**
    * @param aCountClause The countClause to set.
    */
   protected void setCountClause(String aCountClause)
   {
      mCountClause = aCountClause;
   }
}
