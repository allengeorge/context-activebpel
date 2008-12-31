// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/handlers/AeSQLProcessIdsResultSetHandler.java,v 1.2 2007/02/02 14:32:1
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
package org.activebpel.rt.bpel.server.engine.storage.sql.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.impl.list.AeProcessFilter;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler;

/**
 * Helper class to convert a <code>ResultSet</code> to a long[].
 */
public class AeSQLProcessIdsResultSetHandler extends AeListingResultSetHandler
{
   /**
    * Constructor.
    *
    * @param aFilter
    */
   public AeSQLProcessIdsResultSetHandler(AeProcessFilter aFilter)
   {
      super(aFilter);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler#readRow(java.sql.ResultSet)
    */
   protected Object readRow(ResultSet aResultSet) throws SQLException
   {
      return new Long(aResultSet.getLong(1));
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler#convertToType(java.util.List)
    */
   protected Object convertToType(List aResults)
   {
      // aResults is already sorted, because the SELECT includes ORDER BY ProcessId.
      int count = aResults.size();
      long[] result = new long[count];
      Iterator iter = aResults.iterator();
      
      for (int i = 0; (i < count) && iter.hasNext(); ++i)
      {
         result[i] = ((Number) iter.next()).longValue();
      }

      return result;
   }
}
