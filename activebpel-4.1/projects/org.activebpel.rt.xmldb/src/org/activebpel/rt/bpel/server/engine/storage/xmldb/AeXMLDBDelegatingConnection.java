// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/AeXMLDBDelegatingConnection.java,v 1.1 2007/08/17 00:40:5
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

/**
 * Base class for delegating connections.
 */
public abstract class AeXMLDBDelegatingConnection implements IAeXMLDBConnection
{
   /** The connection to delegate to. */
   private IAeXMLDBConnection mDelegate;

   /**
    * C'tor.
    *
    * @param mDelegate
    */
   public AeXMLDBDelegatingConnection(IAeXMLDBConnection mDelegate)
   {
      setDelegate(mDelegate);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#close()
    */
   public void close()
   {
      getDelegate().close();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#commit()
    */
   public void commit() throws AeXMLDBException
   {
      getDelegate().commit();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#getNativeConnection()
    */
   public Object getNativeConnection()
   {
      return getDelegate().getNativeConnection();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBConnection#rollback()
    */
   public void rollback() throws AeXMLDBException
   {
      getDelegate().rollback();
   }

   /**
    * @return Returns the delegate.
    */
   protected IAeXMLDBConnection getDelegate()
   {
      return mDelegate;
   }

   /**
    * @param aDelegate the delegate to set
    */
   protected void setDelegate(IAeXMLDBConnection aDelegate)
   {
      mDelegate = aDelegate;
   }
}
