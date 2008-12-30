// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/recovered/AeRecoveredLocationIdItem.java,v 1.2 2006/06/16 00:24:1
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
package org.activebpel.rt.bpel.server.engine.recovery.recovered;

/**
 * Base class for recovered items that match by location id.
 */
public abstract class AeRecoveredLocationIdItem implements IAeRecoveredItem
{
   /** The process id. */
   private final long mProcessId;

   /** The location id. */
   private final int mLocationId;

   /**
    * Constructs a recovered item that matches by location id.
    */
   protected AeRecoveredLocationIdItem(long aProcessId, int aLocationId)
   {
      mProcessId = aProcessId;
      mLocationId = aLocationId;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aOther)
   {
      return (aOther instanceof AeRecoveredLocationIdItem)
         && ((AeRecoveredLocationIdItem) aOther).getLocationId() == getLocationId();
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.recovery.recovered.IAeRecoveredItem#getLocationId()
    */
   public int getLocationId()
   {
      return mLocationId;
   }

   /**
    * Returns the process id.
    */
   protected long getProcessId()
   {
      return mProcessId;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return getLocationId();
   }
}
