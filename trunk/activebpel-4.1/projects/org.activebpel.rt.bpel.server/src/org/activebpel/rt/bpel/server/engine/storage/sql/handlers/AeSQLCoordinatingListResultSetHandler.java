//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/handlers/AeSQLCoordinatingListResultSetHandler.java,v 1.3 2006/02/24 16:37:3
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
import java.util.ArrayList;
import java.util.List;

import org.activebpel.rt.bpel.coord.IAeCoordinationManager;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * Resultset handler which creates and returns a list of IAeCoordinating objects.
 */
public class AeSQLCoordinatingListResultSetHandler implements ResultSetHandler
{
   /** The coordination manager. */
   private IAeCoordinationManager mManager;

   /**
    * Constructor.
    *
    * @param aManager
    */
   public AeSQLCoordinatingListResultSetHandler(IAeCoordinationManager aManager)
   {
      mManager = aManager;
   }

   /**
    * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
    */
   public Object handle(ResultSet aResultSet) throws SQLException
   {
      List results = new ArrayList();
      // Iterate through rows
      while (aResultSet.next())
      {
         results.add(readRow(aResultSet));
      }      
      return results;
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler#readRow(java.sql.ResultSet)
    */
   protected Object readRow(ResultSet aResultSet) throws SQLException
   {
      return AeSQLCoordinatingResultSetHandler.createCoordinating(aResultSet, mManager);
   }

}
