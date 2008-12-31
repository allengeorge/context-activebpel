//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeListingResultSetHandler.java,v 1.3 2006/02/13 22:31:0
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.activebpel.rt.bpel.impl.list.IAeListingFilter;
import org.activebpel.rt.bpel.server.AeMessages;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * Provides a template method for reading the results from a filter type listing
 * query.
 * <br/>
 * Note: This class is not intended to be thread safe. 
 */
abstract public class AeListingResultSetHandler implements ResultSetHandler
{
   /** The row number where to start fetching results. */
   private int mListStart;

   /** The maximum number of results to return. */
   private int mMaxReturn;

   /** The <code>ResultSet</code> to process. */
   private ResultSet mResultSet;

   /** Whether the <code>ResultSet</code> has more rows. */
   private boolean mHasNext = true;

   /** The number of rows in the <code>ResultSet</code>. */
   private int mRowCount = 0;

   /** Max number of rows to scan to determine the upper limit */
   private static final int REPORT_SCAN_LIMIT = 500;

   /** indicates whether the results returned were truncated or not.  */
   private boolean mTruncated;
   
   /**
    * Constructor.
    *
    * @param aFilter
    */
   public AeListingResultSetHandler(IAeListingFilter aFilter)
   {
      setFilter(aFilter);
   }

   /**
    * Returns the <code>ResultSet</code>.
    */
   protected ResultSet getResultSet()
   {
      return mResultSet;
   }

   /**
    * Returns the number of rows.
    */
   protected int getRowCount()
   {
      return mRowCount;
   }

   /**
    * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
    */
   public synchronized Object handle(ResultSet aResultSet) throws SQLException
   {
      // Note: This class is not intended to be thread safe. We need to
      // to do some checks in case some client code uses a shared instance.
      
      if (getResultSet() != null)
      {
         // as per KR - double check - previous resultSet usage should have completed.
         throw new IllegalStateException(AeMessages.getString("AeListingResultSetHandler.ILLEGAL_STATE")); //$NON-NLS-1$);
      }
      
      try
      {
         List results = new ArrayList();      
         // Set ResultSet for next().
         setResultSet(aResultSet);
         setRowCount(0);
         skipRows();
         readRows(aResultSet, results);
         determineRowCount();         
         return convertToType(results);
      }
      finally
      {
         // always reset the result set.
         setResultSet(null);
      }
   }

   /**
    * Attempts to determine the total number of rows in the ResultSet, reading
    * up to a maximum of the <code>REPORT_SCAN_LIMIT</code>. In the event that
    * there are more rows then the limit then we'll indicate that the search
    * for the end was aborted and use KR's PlusAPI in the web console.
    */
   protected void determineRowCount() throws SQLException
   {
      setTruncated(false);
      for (int n = REPORT_SCAN_LIMIT; next() && !isTruncated(); )
      {
         --n;
         setTruncated((n == 0));
      }

      // If we didn't find the end of the rows, then round down the
      // approximate row count to a nice round number.
      int rows = getRowCount();
      if (isTruncated())
      {
         setRowCount((rows / REPORT_SCAN_LIMIT) * REPORT_SCAN_LIMIT);
      }
   }

   /**
    * Walks the result set reading each row and adding the object to its list.
    * This method will keep reading until the max number of rows has been read
    * or until the result set is exhausted.
    * @param aResultSet
    * @param results
    * @throws SQLException
    */
   protected void readRows(ResultSet aResultSet, List results) throws SQLException
   {
      // Iterate through rows until we have nMaxReturn rows.
      while ((results.size() < mMaxReturn) && next())
      {
         // Use helper routine to convert the current row to
         // AeProcessInstanceDetail.
         results.add(readRow(aResultSet));
      }
   }

   /**
    * Skips the required number of rows based on the filter's starting point.
    * @throws SQLException
    */
   protected void skipRows() throws SQLException
   {
      // Skip the first mListStart rows.
      while ((getRowCount() < mListStart) && next())
      {
      }
   }

   /**
    * Reads the type from the result set.
    * @param aResultSet
    * @throws SQLException
    */
   abstract protected Object readRow(ResultSet aResultSet) throws SQLException;

   /**
    * Converts the list of results to the specific type needed.
    * @param aResults
    */
   abstract protected Object convertToType(List aResults);

   /**
    * Wraps <code>mResultSet.next()</code> to count the total number of rows
    * and to avoid calling it more times than necessary.
    */
   protected boolean next() throws SQLException
   {
      if (mHasNext)
      {
         if (getResultSet().next())
         {
            setRowCount(getRowCount() + 1);
         }
         else
         {
            mHasNext = false;
         }
      }

      return mHasNext;
   }

   /**
    * Sets the filter to use.
    */
   protected void setFilter(IAeListingFilter aFilter)
   {
      if (aFilter == null)
      {
         mListStart = 0;
         mMaxReturn = Integer.MAX_VALUE;
      }
      else
      {
         mListStart = aFilter.getListStart();
         mMaxReturn = aFilter.getMaxReturn();

         // aFilter.getMaxReturn() is 0 for no limit.
         if (mMaxReturn == 0)
         {
            mMaxReturn = Integer.MAX_VALUE;
         }
      }
   }

   /**
    * Sets the <code>ResultSet</code> to handle.
    */
   protected void setResultSet(ResultSet aResultSet)
   {
      mResultSet = aResultSet;
      mHasNext = (aResultSet != null);      
   }

   /**
    * Sets the number of rows.
    */
   protected void setRowCount(int aRowCount)
   {
      mRowCount = aRowCount;
   }

   /**
    * @param aborted The aborted to set.
    */
   protected void setTruncated(boolean aborted)
   {
      mTruncated = aborted;
   }

   /**
    * @return Returns the aborted.
    */
   protected boolean isTruncated()
   {
      return mTruncated;
   }

}
 
