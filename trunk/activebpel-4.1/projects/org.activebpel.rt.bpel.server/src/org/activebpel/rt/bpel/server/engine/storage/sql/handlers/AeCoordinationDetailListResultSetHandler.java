//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/handlers/AeCoordinationDetailListResultSetHandler.java,v 1.3 2006/02/13 22:31:0
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

import org.activebpel.rt.bpel.coord.AeCoordinationDetail;
import org.activebpel.rt.bpel.server.engine.storage.sql.IAeCoordinationColumns;

/**
 * Implements a <code>ResultSetHandler</code> that converts the next row of
 * a <code>ResultSet</code> to an <code>AeCoordinationDetail</code>.
 * <br/>
 */

public class AeCoordinationDetailListResultSetHandler extends AeSQLCoordinatingListResultSetHandler
{

   /**
    * Default ctor
    */
   public AeCoordinationDetailListResultSetHandler()
   {
      super(null);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler#readRow(java.sql.ResultSet)
    */
   protected Object readRow(ResultSet aResultSet) throws SQLException
   {
      String coordId = aResultSet.getString(IAeCoordinationColumns.COORDINATION_ID);
      String state = aResultSet.getString(IAeCoordinationColumns.STATE);
      long processId = aResultSet.getLong(IAeCoordinationColumns.PROCESS_ID);
      String locationPath = aResultSet.getString(IAeCoordinationColumns.LOCATION_PATH);
      return new AeCoordinationDetail(processId, coordId, state, locationPath);  
   }   
}
