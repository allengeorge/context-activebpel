//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/AeTaminoUtil.java,v 1.6 2007/08/17 00:57:3
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
package org.activebpel.rt.bpel.server.engine.storage.tamino;

import com.softwareag.tamino.db.api.connection.TConnection;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBUtil;

/**
 * A class with some static methods to do some standard Tamino related operations.
 */
public class AeTaminoUtil extends AeXMLDBUtil
{
   /**
    * Commits a connection (only if it is a local tx connection).
    * 
    * @param aConnection
    */
   public static void commit(TConnection aConnection) throws AeXMLDBException
   {
      if (aConnection != null && aConnection instanceof IAeTaminoLocalTxConnection)
      {
         IAeTaminoLocalTxConnection c = (IAeTaminoLocalTxConnection) aConnection;
         c.commit();
      }
   }

   /**
    * Rolls back a connection (only if it is a local tx connection).
    * 
    * @param aConnection
    */
   public static void rollback(TConnection aConnection) throws AeXMLDBException
   {
      if (aConnection != null && aConnection instanceof IAeTaminoLocalTxConnection)
      {
         IAeTaminoLocalTxConnection c = (IAeTaminoLocalTxConnection) aConnection;
         c.rollback();
      }
   }

   /**
    * Closes a Tamino connection.
    * 
    * @param aConnection
    */
   public static void close(TConnection aConnection)
   {
      try
      {
         if (aConnection != null)
         {
            aConnection.close();
         }
      }
      catch (Throwable t)
      {
         AeException.logError(t, t.getLocalizedMessage());
      }
   }
}
