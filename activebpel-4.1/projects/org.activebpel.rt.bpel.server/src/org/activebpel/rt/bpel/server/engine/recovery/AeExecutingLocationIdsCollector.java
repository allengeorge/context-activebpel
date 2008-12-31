// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/AeExecutingLocationIdsCollector.java,v 1.1 2006/06/16 00:25:0
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
package org.activebpel.rt.bpel.server.engine.recovery;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.AeBusinessProcess;
import org.activebpel.rt.bpel.impl.visitors.AeImplTraversingVisitor;
import org.activebpel.rt.util.AeLongSet;

/**
 * Determines the location ids for activities that are currently executing by
 * visiting the tree of BPEL implementation objects.
 */
public class AeExecutingLocationIdsCollector extends AeImplTraversingVisitor
{
   /** The executing location ids set. */
   private AeLongSet mExecutingLocationIds;

   /**
    * Returns the executing location ids set.
    */
   protected AeLongSet getExecutingLocationIds()
   {
      return mExecutingLocationIds;
   }

   /**
    * Returns the location ids for activities that are currently executing in
    * the given process.
    *
    * @param aProcess
    */
   public AeLongSet getExecutingLocationIds(IAeBusinessProcess aProcess) throws AeBusinessProcessException
   {
      setExecutingLocationIds(new AeLongSet());
      
      if (aProcess instanceof AeBusinessProcess)
      {
         ((AeBusinessProcess) aProcess).accept(this);
      }

      return getExecutingLocationIds();
   }

   /**
    * Sets the executing location ids set.
    */
   protected void setExecutingLocationIds(AeLongSet aExecutingLocationIds)
   {
      mExecutingLocationIds = aExecutingLocationIds;
   }

   /**
    * Overrides method to save an activity's location id if the activity is
    * executing.
    * 
    * @see org.activebpel.rt.bpel.impl.visitors.AeImplTraversingVisitor#traverse(org.activebpel.rt.bpel.impl.AeAbstractBpelObject)
    */
   protected void traverse(AeAbstractBpelObject aImpl) throws AeBusinessProcessException
   {
      super.traverse(aImpl);

      // If this activity is executing, then save its location id.
      if (aImpl.getState() == AeBpelState.EXECUTING)
      {
         getExecutingLocationIds().add(aImpl.getLocationId());
      }
   }
}
