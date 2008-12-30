// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeTypedSet.java,v 1.4 2007/05/08 18:46:5
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

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Base class for typed sets.
 */
abstract public class AeTypedSet implements Set, Serializable
{
   /** The underlying physical representation. */
   protected final Set mSet;

   /**
    * Default constructor.
    */
   public AeTypedSet()
   {
      this(new HashSet());
   }

   /**
    * Constructor.
    *
    * @param aSet The <code>Set</code> to use.
    */
   public AeTypedSet(Set aSet)
   {
      mSet = aSet;
   }

   /**
    * Returns the underlying <code>Set</code> object for synchronization
    * purposes.
    */
   public Object synchObject()
   {
      return mSet;
   }

   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return mSet.toString();
   }

   /*======================================================================
    * java.util.Set methods
    *======================================================================
    */

   /**
    * @see java.util.Set#add(java.lang.Object)
    */
   public boolean add(Object aObject)
   {
      return mSet.add(aObject);
   }

   /**
    * @see java.util.Set#addAll(java.util.Collection)
    */
   public boolean addAll(Collection aCollection)
   {
      return mSet.addAll(aCollection);
   }

   /**
    * @see java.util.Set#clear()
    */
   public void clear()
   {
      mSet.clear();
   }

   /**
    * @see java.util.Set#contains(java.lang.Object)
    */
   public boolean contains(Object aObject)
   {
      return mSet.contains(aObject);
   }

   /**
    * @see java.util.Set#containsAll(java.util.Collection)
    */
   public boolean containsAll(Collection aCollection)
   {
      return mSet.containsAll(aCollection);
   }

   /**
    * @see java.util.Set#isEmpty()
    */
   public boolean isEmpty()
   {
      return mSet.isEmpty();
   }

   /**
    * @see java.util.Set#iterator()
    */
   public Iterator iterator()
   {
      return mSet.iterator();
   }

   /**
    * @see java.util.Set#remove(java.lang.Object)
    */
   public boolean remove(Object aObject)
   {
      return mSet.remove(aObject);
   }

   /**
    * @see java.util.Set#removeAll(java.util.Collection)
    */
   public boolean removeAll(Collection aCollection)
   {
      return mSet.removeAll(aCollection);
   }

   /**
    * @see java.util.Set#retainAll(java.util.Collection)
    */
   public boolean retainAll(Collection aCollection)
   {
      return mSet.retainAll(aCollection);
   }

   /**
    * @see java.util.Set#size()
    */
   public int size()
   {
      return mSet.size();
   }

   /**
    * @see java.util.Set#toArray()
    */
   public Object[] toArray()
   {
      return mSet.toArray();
   }

   /**
    * @see java.util.Set#toArray(java.lang.Object[])
    */
   public Object[] toArray(Object[] aArray)
   {
      return mSet.toArray(aArray);
   }
}
