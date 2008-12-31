// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/lock/AeLockHolder.java,v 1.2 2004/07/08 13:10:0
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
package org.activebpel.rt.bpel.impl.lock;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** Impl of the lock holder interface that allows multiple owners */
public class AeLockHolder
{
   /** Current owners of the lock */
   private Set mOwners = new HashSet();
   /** True if the lock holder is exclusive meaning that only 1 owner is allowed */
   private boolean mExclusive = false;

   /**
    * @param aOwnerPath
    */
   public void add(String aOwnerPath)
   {
      mOwners.add(aOwnerPath);
   }

   /**
    * Returns <code>true</code> if and only if this lock holder has no owners.
    */
   public boolean isEmpty()
   {
      return mOwners.isEmpty();
   }

   /**
    * Returns <code>true</code> if and only if the lock is exclusive.
    */
   public boolean isExclusive()
   {
      return mExclusive;
   }

   /**
    * @param aOwnerPath
    */
   public void remove(String aOwnerPath)
   {
      mOwners.remove(aOwnerPath);
   }

   /**
    * @param aFlag
    */
   public void setExclusive(boolean aFlag)
   {
      mExclusive = aFlag;
   }

   /**
    * Returns <code>true</code> if and only if additionals owners can be added to this lock holder.
    */
   public boolean canAdd()
   {
      return isExclusive()? isEmpty() : true;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object o)
   {
      if (!(o instanceof AeLockHolder))
      {
         return false;
      }

      AeLockHolder other = (AeLockHolder) o;

      if (isExclusive() != other.isExclusive())
      {
         return false;
      }

      return getOwners().equals(other.getOwners());
   }

   /**
    * Returns the set of lock owners.
    */
   public Set getOwners()
   {
      return Collections.unmodifiableSet(mOwners);
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return (isExclusive() ? 1 : 0)
           + getOwners().hashCode()
           ;
   }
}
