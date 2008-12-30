//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/handlers/AeSQLCoordinatingResultSetHandler.java,v 1.2 2006/02/10 21:51:1
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

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.activebpel.rt.bpel.coord.IAeCoordinating;
import org.activebpel.rt.bpel.coord.IAeCoordinationManager;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageUtil;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeDbUtils;
import org.activebpel.rt.bpel.server.engine.storage.sql.IAeCoordinationColumns;
import org.apache.commons.dbutils.ResultSetHandler;
import org.w3c.dom.Document;


/**
 * Implements a <code>ResultSetHandler</code> that converts the next row of
 * a <code>ResultSet</code> to an <code>IAeCoordinating</code>.
 */
public class AeSQLCoordinatingResultSetHandler implements ResultSetHandler
{
   /** The coordination manager. */
   private IAeCoordinationManager mManager = null;
   
   /**
    * Simple constructor.
    * 
    * @param aManager
    */
   public AeSQLCoordinatingResultSetHandler(IAeCoordinationManager aManager)
   {
      mManager = aManager;
   }

   /**
    * Creates a coordinating object given the result set.
    * 
    * @param aResultSet
    * @param aManager
    * @throws SQLException
    */
   protected static IAeCoordinating createCoordinating(ResultSet aResultSet, IAeCoordinationManager aManager) throws SQLException
   {
      int coordRole = aResultSet.getInt(IAeCoordinationColumns.COORDINATION_ROLE);
      String coordId = aResultSet.getString(IAeCoordinationColumns.COORDINATION_ID);
      String state = aResultSet.getString(IAeCoordinationColumns.STATE);
      long processId = aResultSet.getLong(IAeCoordinationColumns.PROCESS_ID);
      Clob clob = aResultSet.getClob(IAeCoordinationColumns.COORDINATION_DOC);
      
      Document contextDocument = null;
      if (!aResultSet.wasNull())
      {
         contextDocument = AeDbUtils.getDocument(clob);
      }
      else
      {
         SQLException sqe = new SQLException(AeMessages.getString("AeSQLCoordinationStorage.NULL_RESULTSET")); //$NON-NLS-1$);
         throw sqe;
      }
      
      try
      {
         return AeStorageUtil.createCoordinating(processId, coordId, state, coordRole, contextDocument, aManager);
      }
      catch (AeStorageException ex)
      {
         SQLException sqe = new SQLException();
         sqe.initCause(ex);
         throw sqe;
      }
   }

   /**
    * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
    */
   public Object handle(ResultSet aResultSet) throws SQLException
   {
      return aResultSet.next() ? createCoordinating(aResultSet, mManager) : null;
   }
}
