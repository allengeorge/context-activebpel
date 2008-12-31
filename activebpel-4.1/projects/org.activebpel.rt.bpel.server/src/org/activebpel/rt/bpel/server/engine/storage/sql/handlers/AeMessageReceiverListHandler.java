//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/handlers/AeMessageReceiverListHandler.java,v 1.2 2006/02/10 21:51:1
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

import org.activebpel.rt.bpel.impl.list.AeMessageReceiverFilter;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeListingResultSetHandler;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLQueueStorageProvider;

/**
 * Creates a result set handler that returns a list of matching AeMessageReceivers.
 * Has the ability to filter the selected receivers based on the filter criteria.
 */
public class AeMessageReceiverListHandler extends AeListingResultSetHandler
{
   /**
    * Default constructor - uses a null message receiver filter.
    */
   public AeMessageReceiverListHandler()
   {
      super(AeMessageReceiverFilter.NULL_FILTER);
   }
   
   /**
    * Constructor.
    * 
    * @param aFilter The selection criteria.
    */
   public AeMessageReceiverListHandler(AeMessageReceiverFilter aFilter)
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
      return AeSQLQueueStorageProvider.readSQLMessageReceiver(aResultSet);
   }
}
