//$Header: /Development/AEDevelopment/projects/ddl.org.activebpel/src/org/activebpel/ddl/storage/sql/upgrade/AeSQLUpgrader1_0_8_2_AlarmTable.java,v 1.2 2006/01/03 20:46:4
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
package org.activebpel.ddl.storage.sql.upgrade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig;
import org.activebpel.rt.bpel.server.engine.storage.sql.IAeQueueColumns;
import org.activebpel.rt.bpel.server.engine.storage.sql.upgrade.AeAbstractSQLUpgrader;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * This class is responsible for upgrading the alarm storage tables from an older version
 * to a newer one.  Typically it checks the AeMetaInf table for upgrade directives 
 * inserted by the various DB patch scripts.
 */
public class AeSQLUpgrader1_0_8_2_AlarmTable extends AeAbstractSQLUpgrader
{
   /**
    * Constructs an alarm storage upgrader.
    * 
    * @param aUpgradeName
    * @param aSQLConfig
    */
   public AeSQLUpgrader1_0_8_2_AlarmTable(String aUpgradeName, AeSQLConfig aSQLConfig)
   {
      super(aUpgradeName, aSQLConfig);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.upgrade.AeAbstractSQLUpgrader#wrapSQLConfig(org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig)
    */
   public AeSQLConfig wrapSQLConfig(AeSQLConfig aSQLConfig)
   {
      return new AeUpgraderSQLConfig(aSQLConfig);
   }

   /**
    * Upgrades the alarm storage to the most recent version.  Does any data conversion necessary
    * to bring the information in the databse in line with what is required for successful
    * engine operation.
    * 
    * @see org.activebpel.rt.bpel.server.engine.storage.sql.upgrade.AeAbstractSQLUpgrader#doUpgrade(java.sql.Connection)
    */
   protected void doUpgrade(Connection aConnection) throws AeException
   {
      try
      {
         fixDeadlineMillisColumnData(aConnection);
      }
      catch (Exception e)
      {
         throw new AeException(e);
      }
   }

   /**
    * This method is called in order to fix up the data found in the DeadlineMillis column of 
    * the AeAlarm table.  This column was added in the second version of the ActiveBPEL product.
    * The upgrade SQL script for the second version of ActiveBPEL adds the DeadlineMillis 
    * column and initializes its values to 0.  This method reads the value out of the Deadline
    * column and then sets the value of the DeadlineMillis column.
    */
   protected void fixDeadlineMillisColumnData(Connection aConnection) throws Exception
   {
      List alarms = (List) query(IAeUpgraderSQLKeys.GET_ALARMS_WITHBADDEADLINEMILLIS, null, new AeAlarmInfoListResultSetHandler());

      // Iterate through the list, updating each alarm.
      for (Iterator iter = alarms.iterator(); iter.hasNext(); )
      {
         AeAlarmInfoForMillisFixup ai = (AeAlarmInfoForMillisFixup) iter.next();
         Object [] params = new Object[] {
               new Long(ai.getDeadline().getTime()),
               new Long(ai.getProcessId()),
               new Integer(ai.getLocPathId())
         };
         int cols = update(aConnection, IAeUpgraderSQLKeys.UPDATE_DEADLINEMILLIS, params);
         if (cols != 1)
         {
            AeException.info(AeMessages.getString(org.activebpel.ddl.AeMessages.getString("AeSQLAlarmStorageUpgrader.FAILED_TO_FIX_ALARM_MILLIS"))); //$NON-NLS-1$
         }
      }
   }

   /**
    * Implements a result set handler that returns a list of alarm info objects from data in the alarm
    * table.
    */
   private class AeAlarmInfoListResultSetHandler implements ResultSetHandler
   {
      /**
       * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
       */
      public Object handle(ResultSet aResult) throws SQLException
      {
         List rval = new ArrayList();
         while (aResult.next())
         {
            long procId = aResult.getLong(IAeQueueColumns.PROCESS_ID);
            int locPathId = aResult.getInt(IAeQueueColumns.LOCATION_PATH_ID);
            Date deadline = new Date(aResult.getTimestamp(IAeQueueColumns.DEADLINE).getTime());
            rval.add(new AeAlarmInfoForMillisFixup(procId, locPathId, deadline));
         }
         return rval;
      }
   }

}
