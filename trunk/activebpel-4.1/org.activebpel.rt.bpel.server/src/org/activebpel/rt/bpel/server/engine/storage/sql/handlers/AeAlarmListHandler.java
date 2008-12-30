//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/handlers/AeAlarmListHandler.java,v 1.3 2006/09/18 17:59:5
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
import java.util.List;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.impl.list.AeAlarmExt;
import org.activebpel.rt.bpel.impl.list.AeAlarmFilter;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler;
import org.activebpel.rt.bpel.server.engine.storage.sql.IAeQueueColumns;


/**
 * Creates a result set handler that returns a list of matching AeAlarms.
 * Has the ability to filter the selected alarms based on the filter criteria.
 */
public class AeAlarmListHandler extends AeListingResultSetHandler
{
   /**
    * Default constructor.
    */
   public AeAlarmListHandler()
   {
      this( AeAlarmFilter.NULL_FILTER );
   }

   /**
    * Constructor.
    * @param aFilter The selection criteria.
    */
   public AeAlarmListHandler( AeAlarmFilter aFilter )
   {
      super(aFilter);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler#convertToType(java.util.List)
    */
   protected Object convertToType(List aResults)
   {
      return aResults;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler#readRow(java.sql.ResultSet)
    */
   protected Object readRow(ResultSet aResultSet) throws SQLException
   {
      long processId = aResultSet.getLong(IAeQueueColumns.PROCESS_ID);
      String processName = aResultSet.getString(IAeQueueColumns.PROCESS_NAME);
      String processNamespace = aResultSet.getString(IAeQueueColumns.PROCESS_NAMESPACE);
      int locationPathId = aResultSet.getInt(IAeQueueColumns.LOCATION_PATH_ID);
      Date deadline = new Date(aResultSet.getLong(IAeQueueColumns.DEADLINE_MILLIS));
      QName processQName = new QName(processNamespace, processName);
      int groupId = aResultSet.getInt(IAeQueueColumns.GROUP_ID);
      int alarmId = aResultSet.getInt(IAeQueueColumns.ALARM_ID);
      return new AeAlarmExt(processId, locationPathId, groupId, alarmId, deadline, processQName);
   }
}
