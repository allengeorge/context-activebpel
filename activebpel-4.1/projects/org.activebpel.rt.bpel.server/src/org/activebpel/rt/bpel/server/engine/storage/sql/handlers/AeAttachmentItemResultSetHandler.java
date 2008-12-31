// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/handlers/AeAttachmentItemResultSetHandler.java,v 1.1 2007/05/08 19:21:1
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

import org.activebpel.rt.bpel.server.engine.storage.attachment.AeAttachmentItemEntry;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeDbUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.w3c.dom.Document;

/**
 * Implements a <code>ResultSetHandler</code> that returns the first row of a
 * <code>ResultSet</code> as a <code>AeAttachmentItemEntry</code>.
 */
public class AeAttachmentItemResultSetHandler implements ResultSetHandler
{
   /**
    * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
    */
   public  Object handle(ResultSet rs) throws SQLException
   {
      if (rs.next())
      {
         long attachmentGroupId = rs.getLong(1);
         long attachmentItemId = rs.getLong(2);

         Clob clob = rs.getClob(3);
         if (rs.wasNull())
         {
            clob = null;
         }

         Document document = (clob == null) ? null : AeDbUtils.getDocument(clob);
       
         return new AeAttachmentItemEntry(attachmentGroupId, attachmentItemId, document); 
      }

      return null;
   }
}
