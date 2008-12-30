//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/handlers/AeAlarmListQueryHandler.java,v 1.3 2006/09/18 17:59:5
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
import java.util.Date;
import java.util.LinkedList;

import org.activebpel.rt.bpel.server.engine.storage.AePersistedAlarm;
import org.activebpel.rt.bpel.server.engine.storage.sql.IAeQueueColumns;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * Implements a query handler that can transform a result set into a list of
 * persisted alarm objects.
 */
public class AeAlarmListQueryHandler implements ResultSetHandler
{
   /**
    * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
    */
   public Object handle(ResultSet aResultSet) throws SQLException
   {
      LinkedList list = new LinkedList();
      while (aResultSet.next())
      {
         long processId = aResultSet.getLong(IAeQueueColumns.PROCESS_ID);
         int locationPathId = aResultSet.getInt(IAeQueueColumns.LOCATION_PATH_ID);
         Date deadline = new Date(aResultSet.getLong(IAeQueueColumns.DEADLINE_MILLIS));
         int groupId = aResultSet.getInt(IAeQueueColumns.GROUP_ID);
         int alarmId = aResultSet.getInt(IAeQueueColumns.ALARM_ID);
         list.add(new AePersistedAlarm(processId, locationPathId, deadline, groupId, alarmId));
      }

      return list;
   }
}
