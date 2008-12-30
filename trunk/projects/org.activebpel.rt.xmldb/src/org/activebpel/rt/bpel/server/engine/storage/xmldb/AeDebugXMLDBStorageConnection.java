// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/AeDebugXMLDBStorageConnection.java,v 1.1 2007/08/17 00:40:5
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

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;

/**
 * A version of the XMLDB DB connection that will throw an exception on 'close' if neither
 * commit nor rollback have been called.
 */
public class AeDebugXMLDBStorageConnection extends AeXMLDBStorageConnection
{
   /** Flag indicating if the connection is 'complete' (ie either commit or rollback has been called). */
   private boolean mComplete;

   /**
    * Default constructor.
    *
    * @param aConnection
    */
   protected AeDebugXMLDBStorageConnection(IAeXMLDBConnection aConnection)
   {
      super(aConnection);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBStorageConnection#close()
    */
   public void close()
   {
      super.close();

      if (!isComplete())
         throw new RuntimeException("ERROR: XMLDB connection was closed without first calling commit or rollback!"); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBStorageConnection#commit()
    */
   public void commit() throws AeStorageException
   {
      setComplete(true);
      super.commit();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBStorageConnection#rollback()
    */
   public void rollback() throws AeStorageException
   {
      setComplete(true);
      super.rollback();
   }

   /**
    * @return Returns the complete.
    */
   protected boolean isComplete()
   {
      return mComplete;
   }

   /**
    * @param aComplete The complete to set.
    */
   protected void setComplete(boolean aComplete)
   {
      mComplete = aComplete;
   }
}
