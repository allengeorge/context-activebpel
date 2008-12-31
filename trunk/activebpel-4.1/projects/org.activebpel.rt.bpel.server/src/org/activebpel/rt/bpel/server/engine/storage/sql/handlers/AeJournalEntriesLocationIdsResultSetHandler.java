//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/handlers/AeJournalEntriesLocationIdsResultSetHandler.java,v 1.1 2005/11/16 16:48:1
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

import org.activebpel.rt.util.AeLongMap;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * Implements a {@link org.apache.commons.dbutils.ResultSetHandler} that
 * converts a {@link java.sql.ResultSet} to a map from journal entry ids to
 * location ids.
 */
public class AeJournalEntriesLocationIdsResultSetHandler implements ResultSetHandler
{
   /**
    * Default constructor that is visible to classes derived from
    * <code>AeSQLProcessStateStorage</code>.
    */
   public AeJournalEntriesLocationIdsResultSetHandler()
   {
   }

   /**
    * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
    */
   public Object handle(ResultSet rs) throws SQLException
   {
      AeLongMap result = new AeLongMap();

      while (rs.next())
      {
         long journalId = rs.getLong(1);

         if (rs.wasNull())
         {
            continue;
         }

         int locationId = rs.getInt(2);

         if (rs.wasNull())
         {
            continue;
         }

         result.put(journalId, new Integer(locationId));
      }

      return result;
   }
}
