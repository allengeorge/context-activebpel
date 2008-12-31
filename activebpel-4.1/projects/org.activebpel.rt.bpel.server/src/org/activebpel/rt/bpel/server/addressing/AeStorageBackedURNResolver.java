//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/addressing/AeStorageBackedURNResolver.java,v 1.1 2007/05/22 00:19:1
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
package org.activebpel.rt.bpel.server.addressing; 

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.bpel.server.engine.storage.AePersistentStoreFactory;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.IAeURNStorage;
import org.activebpel.rt.bpel.urn.AeURNResolver;

/**
 * Keeps all of the mappings in memory.
 * 
 * TODO Note - this class uses the IAeURNStorage interface directly when it should not.  The reason is that it can be constructed with a config map - see the constructor for details.
 */
public class AeStorageBackedURNResolver extends AeURNResolver implements IAeStorageBackedURNResolver
{
   /** storage contains the values for the urn addressing */
   private IAeURNStorage mStorage;

   /**
    * Standard manager constructor takes a map with config options.
    * 
    * @param aConfigMap Map must contain entry for the storage class.
    * @throws AeException
    */
   public AeStorageBackedURNResolver(Map aConfigMap) throws AeException
   {
      Map storageMap = (Map) aConfigMap.get("Storage"); //$NON-NLS-1$
      if (storageMap != null)
      {
         setStorage( (IAeURNStorage) AeEngineFactory.createConfigSpecificClass(storageMap) );
      }
      else
      {
         setStorage( AePersistentStoreFactory.getInstance().getURNStorage() );
      }
      getMap().putAll(getStorage().getMappings());
   }

   /**
    * Protected ctor for testing.
    * 
    * @throws AeStorageException
    */
   protected AeStorageBackedURNResolver(IAeURNStorage aStorage) throws AeStorageException
   {
      setStorage(aStorage);
      getMap().putAll(getStorage().getMappings());
   }
   
   /**
    * protected constructor for subclasses. 
    */
   protected AeStorageBackedURNResolver()
   {
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.addressing.IAeURNAddressResolver#addMapping(java.lang.String, java.lang.String)
    */
   public synchronized void addMapping(String aURN, String aURL)
   {
      try
      {
         getStorage().addMapping(aURN, aURL);
         super.addMapping(aURN, aURL);
      }
      catch (AeStorageException e)
      {
         e.logError();
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.addressing.IAeURNAddressResolver#removeMappings(java.lang.String[])
    */
   public synchronized void removeMappings(String[] aURNArray)
   {
      try
      {
         getStorage().removeMappings(aURNArray);
         super.removeMappings(aURNArray);
      }
      catch (AeStorageException e)
      {
         e.logError();
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.addressing.IAeStorageBackedURNResolver#reload()
    */
   public synchronized void reload()
   {
      try
      {
         setMap(new HashMap(getStorage().getMappings()));
      }
      catch (AeStorageException e)
      {
         e.logError();
      }
   }
   
   /**
    * Getter for the storage.
    */
   protected IAeURNStorage getStorage()
   {
      return mStorage;
   }
   
   /**
    * Setter for the storage.
    * 
    * @param aStorage
    */
   protected void setStorage(IAeURNStorage aStorage)
   {
      mStorage = aStorage;
   }
}
 
