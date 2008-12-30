//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/handlers/AeTransmissionTrackerResultSetHandler.java,v 1.1 2006/05/24 23:16:3
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

import org.activebpel.rt.bpel.server.engine.storage.sql.IAeTransmissionTrackerColumns;
import org.activebpel.rt.bpel.server.transreceive.AeTransmissionTrackerEntry;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * Creates a <code>AeTransmissionTrackerEntry</code> from the SQL result set.
 */
public class AeTransmissionTrackerResultSetHandler implements ResultSetHandler
{
   /**
    * 
    * Overrides method to create and return a AeTransmissionTrackerEntry object.
    * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
    */
   public Object handle(ResultSet aResultSet) throws SQLException
   {
      AeTransmissionTrackerEntry rval = null;
      if (aResultSet.next())
      {         
         int state = aResultSet.getInt(IAeTransmissionTrackerColumns.STATE);
         long transmissionId = aResultSet.getLong(IAeTransmissionTrackerColumns.TRANSMISSION_ID);
         String messageId = aResultSet.getString(IAeTransmissionTrackerColumns.MESSAGE_ID);   
         if (aResultSet.wasNull())
         {
            messageId = null;
         }
         rval = new AeTransmissionTrackerEntry(transmissionId, state, messageId);
      }
      return rval;   
   }

}
