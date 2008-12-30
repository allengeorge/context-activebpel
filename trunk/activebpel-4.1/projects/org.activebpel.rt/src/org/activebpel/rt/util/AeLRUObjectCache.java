// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeLRUObjectCache.java,v 1.8 2006/05/08 17:53:3
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
package org.activebpel.rt.util;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Generic object caching.  If max size is exceeded, the least recently
 * usesd (lru) object in the cache is removed to make room for the 
 * most current.
 */
public class AeLRUObjectCache
{
   /** Cache map. */
   protected LinkedHashMap mCache;
   /** Max size parameter. */
   protected int mMaxSize;
   
   /**
    * Contructor.  
    * @param aMaxSize The cache size or -1 for no size limit.
    */
   public AeLRUObjectCache( int aMaxSize )
   {
      mMaxSize = aMaxSize;
      // special constructor for LinkedHashMap that provides us w/ the LRU functionality
      mCache = new LinkedHashMap(16, .75f, true);
   }
   
   /**
    * Retrieve an object from the cache.  If an object was found,
    * its status in the lru list will be updated.
    * @param aKey The object key.
    * @return The cached object or null if none was found.
    */
   public synchronized Object get( Object aKey )
   {
      return mCache.get( aKey );
   }
   
   /**
    * Returns true if the cache contains a value mapped to this key.
    * 
    * @param aKey
    */
   public synchronized boolean containsKey(Object aKey)
   {
      return mCache.containsKey(aKey);
   }
   
   /**
    * Removes an object from the cache.
    * @param aKey The key of the object to be removed.
    * @return The object that was removed or null if none was removed.
    */
   public synchronized Object remove( Object aKey )
   {
      return mCache.remove(aKey);
   }
   
   /**
    * Caches the key/value pair.  If the cache is full,
    * the oldest key/value pair in the cache will be 
    * removed and the KEY to the removed object will
    * be returned.  Otherwise, null will be returned.
    * @param aNewKey The mapping key.
    * @param aNewValue The mapping value.
    * @return The key value of the object that was removed to make room
    * for the new cache pair, or null if the cache is not full.
    */
   public synchronized Object cache( Object aNewKey, Object aNewValue )
   {
      Object keyToRemove = null;
      if (getMaxSize() != 0)
      {
         mCache.put(aNewKey, aNewValue);
         
         // check to see if we need to trim the size of the map based on the new add
         if (mCache.size() > getMaxSize())
         {
            keyToRemove = mCache.keySet().iterator().next();
            mCache.remove(keyToRemove);
         }
      }
      return keyToRemove;
   }
   
   /**
    * Setter for the max number of objects to cache.
    * Set to -1 for unbounded.
    * @param aSize
    */
   public synchronized void setMaxSize( int aSize )
   {
      boolean needToShrinkMap = aSize != -1 && aSize < mCache.size();
      mMaxSize = aSize;
      
      if (needToShrinkMap)
      {
         // keep removing entries until our size is w/in the max
         for(Iterator it = mCache.keySet().iterator(); it.hasNext() && mCache.size() > getMaxSize();)
         {
            it.next();
            it.remove();
         }
      }
   }
   
   /**
    * Getter for the max size. A value of -1 means the size in unbounded.
    */
   public int getMaxSize()
   {
      return mMaxSize;
   }
   
   /**
    * Clears the cache 
    */
   public synchronized void clear()
   {
      mCache.clear();
   }
}
