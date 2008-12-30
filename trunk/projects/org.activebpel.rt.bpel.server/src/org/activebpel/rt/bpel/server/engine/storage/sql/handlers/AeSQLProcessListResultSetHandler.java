//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/handlers/AeSQLProcessListResultSetHandler.java,v 1.1 2005/11/16 16:48:1
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
import java.util.List;

import org.activebpel.rt.bpel.impl.list.AeProcessFilter;
import org.activebpel.rt.bpel.impl.list.AeProcessListResult;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler;


/**
 * Helper class to convert a <code>ResultSet</code> to an
 * <code>AeProcessListResult</code>.
 */
public class AeSQLProcessListResultSetHandler extends AeListingResultSetHandler
{
   /**
    * Constructor.
    *
    * @param aFilter
    */
   public AeSQLProcessListResultSetHandler(AeProcessFilter aFilter)
   {
      super(aFilter);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler#readRow(java.sql.ResultSet)
    */
   protected Object readRow(ResultSet aResultSet) throws SQLException
   {
      return AeSQLProcessInstanceResultSetHandler.createProcessInstanceDetail(aResultSet);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler#convertToType(java.util.List)
    */
   protected Object convertToType(List aResults)
   {
      return new AeProcessListResult(getRowCount(), aResults, !isTruncated());
   }
}
