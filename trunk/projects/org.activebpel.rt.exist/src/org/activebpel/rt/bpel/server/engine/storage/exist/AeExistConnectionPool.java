// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.exist/src/org/activebpel/rt/bpel/server/engine/storage/exist/AeExistConnectionPool.java,v 1.2 2007/08/17 00:59:5
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
package org.activebpel.rt.bpel.server.engine.storage.exist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.xmldb.api.base.Collection;

/**
 * Implements a connection pool for the test in-memory database.  This connection
 * pool is, by default, limited to a single connection.
 */
public class AeExistConnectionPool
{
   /** The object used to lock the pool. */
   private Object mMutex = new Object();
   /** Available connections. */
   private List mAvailableConnections;
   /** Connections that have been checked out. */
   private List mCheckedOutConnections;

   /**
    * C'tor.
    * 
    * @param aSize
    * @param aCollection
    */
   public AeExistConnectionPool(int aSize, Collection aCollection)
   {
      setAvailableConnections(new ArrayList());
      setCheckedOutConnections(new ArrayList());
      
      for (int i = 0; i < aSize; i++)
      {
         getAvailableConnections().add(new AePooledExistConnection(aCollection));
      }
   }
   
   /**
    * Checks out a connection.
    * 
    * @throws AeXMLDBException
    */
   public IAeExistConnection checkoutConnection(boolean aAutoCommit) throws AeXMLDBException
   {
      AeExistConnection connection = null;
      while (connection == null)
      {
         synchronized (getMutex())
         {
            if (getAvailableConnections().size() == 0)
            {
               try
               {
                  getMutex().wait();
               }
               catch (InterruptedException ex)
               {
                  throw new AeXMLDBException("Interrupted waiting for a connection.", ex); //$NON-NLS-1$
               }
            }
            else
            {
               connection = (AeExistConnection) getAvailableConnections().remove(0);
               connection.setAutoCommit(aAutoCommit);
               getCheckedOutConnections().add(new AeCheckedOutConnectionInfo(connection));
            }
         }
      }
      return connection;
   }
   
   /**
    * Check a connection back into the pool.
    * 
    * @param aConnection
    * @throws AeXMLDBException
    */
   public void checkinConnection(IAeExistConnection aConnection)
   {
      synchronized (getMutex())
      {
         // Move the connection from the checked-out collection to the 
         // available collection.
         boolean found = false;
         for (Iterator iter = getCheckedOutConnections().iterator(); iter.hasNext(); )
         {
            AeCheckedOutConnectionInfo info = (AeCheckedOutConnectionInfo) iter.next();
            if (info.mConnection == aConnection)
            {
               iter.remove();
               found = true;
            }
         }
         if (found)
         {
            getAvailableConnections().add(aConnection);
            getMutex().notifyAll();
         }
         else
         {
            throw new RuntimeException("Failed to return a connection to the pool!"); //$NON-NLS-1$
         }
      }
   }
   
   /**
    * @return Returns the availableConnections.
    */
   protected List getAvailableConnections()
   {
      return mAvailableConnections;
   }

   /**
    * @param aAvailableConnections the availableConnections to set
    */
   protected void setAvailableConnections(List aAvailableConnections)
   {
      mAvailableConnections = aAvailableConnections;
   }

   /**
    * @return Returns the checkedOutConnections.
    */
   protected List getCheckedOutConnections()
   {
      return mCheckedOutConnections;
   }

   /**
    * @param aCheckedOutConnections the checkedOutConnections to set
    */
   protected void setCheckedOutConnections(List aCheckedOutConnections)
   {
      mCheckedOutConnections = aCheckedOutConnections;
   }

   /**
    * @return Returns the mutex.
    */
   protected Object getMutex()
   {
      return mMutex;
   }

   /**
    * @param aMutex the mutex to set
    */
   protected void setMutex(Object aMutex)
   {
      mMutex = aMutex;
   }

   /**
    * An inner class that holds information about a connection that has been
    * checked out of the pool.
    */
   protected class AeCheckedOutConnectionInfo
   {
      public Thread mThread;
      public Exception mStack;
      public IAeExistConnection mConnection;
      
      /**
       * C'tor.
       */
      public AeCheckedOutConnectionInfo(IAeExistConnection aConnection)
      {
         mConnection = aConnection;
         mThread = Thread.currentThread();
         mStack = new Exception();
         mStack.fillInStackTrace();
      }
   }

   /**
    * When closed, returns the connection to the pool.
    */
   protected class AePooledExistConnection extends AeExistConnection
   {
      /**
       * C'tor.
       * 
       * @param aCollection
       */
      public AePooledExistConnection(Collection aCollection)
      {
         super(true, aCollection);
      }
      
      /**
       * @see org.activebpel.rt.bpel.server.engine.storage.exist.AeExistConnection#close()
       */
      public void close()
      {
         super.close();
         
         checkinConnection((IAeExistConnection) this.getNativeConnection());
      }
   }
}
