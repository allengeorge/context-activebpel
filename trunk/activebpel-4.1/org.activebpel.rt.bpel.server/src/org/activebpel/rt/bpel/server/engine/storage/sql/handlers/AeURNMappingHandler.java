//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/handlers/AeURNMappingHandler.java,v 1.2 2006/01/03 20:34:5
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
import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.sql.AeDbUtils;
import org.activebpel.rt.bpel.server.engine.storage.sql.IAeURNColumns;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * Walks the result set building a map of urn to url values. 
 */
public class AeURNMappingHandler implements ResultSetHandler
{
   /**
    * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
    */
   public Object handle(ResultSet aRs) throws SQLException
   {
      Map map = new HashMap();
      while(aRs.next())
      {
         String urn = aRs.getString(IAeURNColumns.COL_URN);
         String url = AeDbUtils.getString(aRs.getClob(IAeURNColumns.COL_URL));
         map.put(urn, url);
      }
      return map;
   }
}
