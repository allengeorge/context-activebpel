// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/AeXMLDBDebugConnection.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb;

import org.activebpel.rt.AeException;


/**
 * Debug connection - used in development to find connections that
 * aren't being closed properly.
 */
public class AeXMLDBDebugConnection extends AeXMLDBDelegatingConnection
{
   /** Number of open connections found when this object is garbage collected. */
   private static int sOpenConnCount = 0;
   /** Indicates if the connection was closed. */
   private boolean mClosedByClient = false;

   /**
    * C'tor.
    *
    * @param aConnection
    */
   public AeXMLDBDebugConnection(IAeXMLDBConnection aConnection)
   {
      super(aConnection);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBDelegatingConnection#close()
    */
   public void close()
   {
      setClosedByClient(true);
      super.close();
   }

   /**
    * @return Returns the closedByClient.
    */
   protected boolean isClosedByClient()
   {
      return mClosedByClient;
   }

   /**
    * @param aClosedByClient The closedByClient to set.
    */
   protected void setClosedByClient(boolean aClosedByClient)
   {
      mClosedByClient = aClosedByClient;
   }

   /**
    * @see java.lang.Object#finalize()
    */
   protected void finalize() throws Throwable
   {
      if (!isClosedByClient())
      {
         sOpenConnCount++;
         AeException
               .logWarning("*** XMLDB CONN FINALIZE HAS OPEN CONNECTION *** TotalOpen=" + sOpenConnCount); //$NON-NLS-1$
      }
   }
}
