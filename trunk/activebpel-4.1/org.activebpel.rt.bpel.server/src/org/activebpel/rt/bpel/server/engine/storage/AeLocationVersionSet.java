// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/AeLocationVersionSet.java,v 1.1 2004/08/13 20:42:0
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implements a set of location id + version number pairs.
 */
public class AeLocationVersionSet implements IAeLocationVersionSet
{
   /** Set of <code>AeLocationVersionEntry</code> instances. */
   private final Set mLocationVersionSet;

   /**
    * Default constructor.
    */
   public AeLocationVersionSet()
   {
      this(new HashSet());
   }

   /**
    * Constructor.
    *
    * @param aSet The set to use.
    */
   public AeLocationVersionSet(Set aSet)
   {
      mLocationVersionSet = aSet;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeLocationVersionSet#add(long, int)
    */
   public void add(long aLocationId, int aVersionNumber)
   {
      getLocationVersionSet().add(new AeLocationVersionEntry(aLocationId, aVersionNumber));
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeLocationVersionSet#contains(long, int)
    */
   public boolean contains(long aLocationId, int aVersionNumber)
   {
      return contains(new AeLocationVersionEntry(aLocationId, aVersionNumber));
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeLocationVersionSet#contains(org.activebpel.rt.bpel.server.engine.storage.IAeLocationVersionSet.IEntry)
    */
   public boolean contains(IEntry aEntry)
   {
      return getLocationVersionSet().contains(aEntry);
   }

   /**
    * Returns the underlying Java <code>Set</code>.
    */
   protected Set getLocationVersionSet()
   {
      return mLocationVersionSet;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeLocationVersionSet#iterator()
    */
   public Iterator iterator()
   {
      return getLocationVersionSet().iterator();
   }

   /**
    * Combines a location id and version number for use as a key in a Java
    * <code>Set</code> or <code>Map</code>
    */
   protected static class AeLocationVersionEntry implements IEntry
   {
      /** The variable or correlation set location id. */
      private final long mLocationId;

      /** The variable or correlation set version number. */
      private final int mVersionNumber;

      /** Hash code. */
      private final int mHashCode;

      /**
       * Constructor.
       *
       * @param aLocationId
       * @param aVersionNumber
       */
      public AeLocationVersionEntry(long aLocationId, int aVersionNumber)
      {
         mLocationId = aLocationId;
         mVersionNumber = aVersionNumber;
         mHashCode = new Long(aLocationId).hashCode() + aVersionNumber;
      }

      /**
       * @see java.lang.Object#equals(java.lang.Object)
       */
      public boolean equals(Object aObject)
      {
         if (aObject instanceof AeLocationVersionEntry)
         {
            AeLocationVersionEntry other = (AeLocationVersionEntry) aObject;
            return (getLocationId() == other.getLocationId()) && (getVersionNumber() == other.getVersionNumber());
         }

         return false;
      }

      /**
       * Returns the location id.
       */
      public long getLocationId()
      {
         return mLocationId;
      }

      /**
       * Returns the version number.
       */
      public int getVersionNumber()
      {
         return mVersionNumber;
      }

      /**
       * @see java.lang.Object#hashCode()
       */
      public int hashCode()
      {
         return mHashCode;
      }
   }
}
