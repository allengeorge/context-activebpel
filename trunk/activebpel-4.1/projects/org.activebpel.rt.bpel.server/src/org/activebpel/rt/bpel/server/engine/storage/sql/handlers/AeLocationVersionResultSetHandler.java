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

import org.activebpel.rt.bpel.server.engine.storage.AeLocationVersionSet;
import org.activebpel.rt.bpel.server.engine.storage.IAeLocationVersionSet;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * A SQL result set handler that returns a set of location id/version number tuples.
 */
public class AeLocationVersionResultSetHandler implements ResultSetHandler
{
   /**
    * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
    */
   public Object handle(ResultSet rs) throws SQLException
   {
      IAeLocationVersionSet set = new AeLocationVersionSet();

      while (rs.next())
      {
         long locationId = rs.getLong(1);

         if (rs.wasNull())
         {
            continue;
         }

         int versionNumber = rs.getInt(2);

         if (rs.wasNull())
         {
            continue;
         }

         set.add(locationId, versionNumber);
      }

      return set;
   }
}
