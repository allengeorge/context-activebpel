// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeIntSet.java,v 1.1 2004/10/29 21:02:3
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

import java.util.HashSet;
import java.util.Set;

/**
 * Wraps a <code>Set</code> with convenience methods to simplify using
 * <code>int</code> values for members.
 */
public class AeIntSet extends AeTypedSet
{
   /**
    * Default constructor.
    */
   public AeIntSet()
   {
      this(new HashSet());
   }

   /**
    * Constructor.
    *
    * @param aSet The <code>Set</code> to use.
    */
   public AeIntSet(Set aSet)
   {
      super( aSet );
   }

   /*======================================================================
    * Convenience methods for int keys
    *======================================================================
    */

   /**
    * Returns <code>add(new Integer(aInt))</code>.
    */
   public boolean add(int aInt)
   {
      return mSet.add(new Integer(aInt));
   }

   /**
    * Returns <code>contains(new Integer(aInt))</code>.
    */
   public boolean contains(int aInt)
   {
      return mSet.contains(new Integer(aInt));
   }

   /**
    * Returns <code>remove(new Integer(aInt))</code>.
    */
   public boolean remove(int aInt)
   {
      return mSet.remove(new Integer(aInt));
   }
}
