// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/AeURNStorage.java,v 1.3 2006/06/05 20:45:4
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
package org.activebpel.rt.bpel.server.engine.storage;

import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.providers.IAeStorageConnection;
import org.activebpel.rt.bpel.server.engine.storage.providers.IAeURNStorageProvider;

/**
 * A delegating implementation of a URN storage.  This class delegates all of the database
 * calls to an instance of IAeURNStorageProvider.  The purpose of this class is to encapsulate
 * storage 'logic' so that it can be shared across multiple storage implementations (such as SQL
 * and Tamino).
 */
public class AeURNStorage extends AeAbstractStorage implements IAeURNStorage
{
   /**
    * Default constructor that takes the queue storage provider to use.
    * 
    * @param aProvider
    */
   public AeURNStorage(IAeURNStorageProvider aProvider)
   {
      super(aProvider);
   }

   /**
    * Convenience method to get the storage provider cast to a URN storage provider.
    */
   protected IAeURNStorageProvider getURNStorageProvider()
   {
      return (IAeURNStorageProvider) getProvider();
   }


   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeURNStorage#getMappings()
    */
   public Map getMappings() throws AeStorageException
   {
      return getURNStorageProvider().getMappings();
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeURNStorage#addMapping(java.lang.String, java.lang.String)
    */
   public void addMapping(String aURN, String aURL) throws AeStorageException
   {
      IAeStorageConnection connection = getCommitControlDBConnection();

      try
      {
         getURNStorageProvider().removeMapping(aURN, connection);
         getURNStorageProvider().addMapping(aURN, aURL, connection);

         connection.commit();
      }
      catch (AeStorageException se)
      {
         AeStorageUtil.rollback(connection);
         throw se;
      }
      finally
      {
         connection.close();
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeURNStorage#removeMappings(java.lang.String[])
    */
   public void removeMappings(String[] aURNArray) throws AeStorageException
   {
      IAeStorageConnection connection = getCommitControlDBConnection();
      try
      {
         for (int i = 0; i < aURNArray.length; i++)
         {
            getURNStorageProvider().removeMapping(aURNArray[i], connection);
         }
         connection.commit();
      }
      catch (AeStorageException e)
      {
         AeStorageUtil.rollback(connection);
         throw e;
      }
      finally
      {
         connection.close();
      }
   }
}
