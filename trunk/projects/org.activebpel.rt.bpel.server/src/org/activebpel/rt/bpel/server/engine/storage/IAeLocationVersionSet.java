// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/IAeLocationVersionSet.java,v 1.1 2004/08/13 20:42:0
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

import java.util.Iterator;

/**
 * Defines interface for a set of objects identified by location id and version
 * number.
 */
public interface IAeLocationVersionSet
{
   /**
    * Adds the specified location id and version number to the set.
    *
    * @param aLocationId
    * @param aVersionNumber
    */
   public void add(long aLocationId, int aVersionNumber);

   /**
    * Returns <code>true</code> if and only if the set contains the specified
    * location id and version number.
    *
    * @param aLocationId
    * @param aVersionNumber
    */
   public boolean contains(long aLocationId, int aVersionNumber);

   /**
    * Returns <code>true</code> if and only if the set contains the location id
    * and version number in the specified entry.
    *
    * @param aEntry
    */
   public boolean contains(IEntry aEntry);

   /**
    * Returns an <code>Iterator</code> over the entries in the set, where
    * entries are instances of <code>IAeLocationVersionEntry</code>.
    */
   public Iterator iterator();

   /**
    * Defines the interface for an entry in an <code>IAeLocationVersionSet</code>.
    */
   public interface IEntry
   {
      /**
       * Returns the location id.
       */
      public long getLocationId();

      /**
       * Returns the version number.
       */
      public int getVersionNumber();
   }
}
