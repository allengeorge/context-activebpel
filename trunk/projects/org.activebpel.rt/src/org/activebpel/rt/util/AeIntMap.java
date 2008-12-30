// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeIntMap.java,v 1.1 2004/08/13 20:12:5
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Wraps a <code>Map</code> with convenience methods to simplify using
 * <code>int</code> values for map keys.
 */
public class AeIntMap implements Map
{
   /** The underlying physical representation. */
   private final Map mMap;

   /**
    * Default constructor.
    */
   public AeIntMap()
   {
      this(new HashMap());
   }

   /**
    * Constructor.
    *
    * @param aMap The <code>Map</code> to use.
    */
   public AeIntMap(Map aMap)
   {
      mMap = aMap;
   }

   /*======================================================================
    * Convenience methods for int keys
    *======================================================================
    */

   /**
    * Returns <code>containsKey(new Integer(aKey))</code>.
    *
    * @param aKey
    */
   public boolean containsKey(int aKey)
   {
      return containsKey(new Integer(aKey));
   }

   /**
    * Returns <code>get(new Integer(aKey))</code>.
    *
    * @param aKey
    */
   public Object get(int aKey)
   {
      return get(new Integer(aKey));
   }

   /**
    * Returns <code>put(new Integer(aKey), aValue)</code>.
    *
    * @param aKey
    * @param aValue
    */
   public Object put(int aKey, Object aValue)
   {
      return put(new Integer(aKey), aValue);
   }

   /**
    * Returns <code>remove(new Integer(aKey))</code>.
    *
    * @param aKey
    */
   public Object remove(int aKey)
   {
      return remove(new Integer(aKey));
   }

   /*======================================================================
    * java.util.Map methods
    *======================================================================
    */

   /**
    * @see java.util.Map#containsKey(java.lang.Object)
    */
   public boolean containsKey(Object aKey)
   {
      return mMap.containsKey(aKey);
   }

   /**
    * @see java.util.Map#containsValue(java.lang.Object)
    */
   public boolean containsValue(Object aValue)
   {
      return mMap.containsValue(aValue);
   }

   /**
    * @see java.util.Map#clear()
    */
   public void clear()
   {
      mMap.clear();
   }

   /**
    * @see java.util.Map#entrySet()
    */
   public Set entrySet()
   {
      return mMap.entrySet();
   }

   /**
    * @see java.util.Map#get(java.lang.Object)
    */
   public Object get(Object aKey)
   {
      return mMap.get(aKey);
   }

   /**
    * @see java.util.Map#isEmpty()
    */
   public boolean isEmpty()
   {
      return mMap.isEmpty();
   }

   /**
    * @see java.util.Map#keySet()
    */
   public Set keySet()
   {
      return mMap.keySet();
   }

   /**
    * @see java.util.Map#put(java.lang.Object, java.lang.Object)
    */
   public Object put(Object aKey, Object aValue)
   {
      return mMap.put(aKey, aValue);
   }

   /**
    * @see java.util.Map#putAll(java.util.Map)
    */
   public void putAll(Map aMap)
   {
      mMap.putAll(aMap);
   }

   /**
    * @see java.util.Map#remove(java.lang.Object)
    */
   public Object remove(Object aKey)
   {
      return mMap.remove(aKey);
   }

   /**
    * @see java.util.Map#size()
    */
   public int size()
   {
      return mMap.size();
   }

   /**
    * @see java.util.Map#values()
    */
   public Collection values()
   {
      return mMap.values();
   }
}
