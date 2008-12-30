// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/list/AeMessageReceiverListResult.java,v 1.2 2006/06/26 16:50:4
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
package org.activebpel.rt.bpel.impl.list;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.impl.queue.AeMessageReceiver;

/**
 * Wraps a listing of queued message receivers.
 */
public class AeMessageReceiverListResult implements Serializable
{
   /** Total rows that matched selection criteria.  This number may be greater than the number of results in this listing. */
   protected int mTotalRows;
   /** The matching message receivers. */
   protected AeMessageReceiver[] mResults;
   /** Mapping of process ids to location paths. */
   protected Map mLocationIdtoLocationPathMap;
   
   /**
    * Constructor.
    * @param aTotalRows Total rows that matched selection criteria.  This number may be greater than the number of results in this listing.
    * @param aReceivers The matching message receivers.
    */
   public AeMessageReceiverListResult( int aTotalRows, List aReceivers )
   {
      mTotalRows = aTotalRows;
      mResults = (AeMessageReceiver[])aReceivers.toArray( new AeMessageReceiver[aReceivers.size()]);
      mLocationIdtoLocationPathMap = new HashMap();
   }
   
   /**
    * Accessor for total rows.
    */
   public int getTotalRows()
   {
      return mTotalRows;
   }
   
   /**
    * Accessor for message receivers.
    */
   public AeMessageReceiver[] getResults()
   {
      return mResults;
   }
   
   /**
    * Add a location id to location path mapping.
    * @param aLocationId The location path id.
    * @param aLocation The location xpath.
    */
   public void addPathMapping( int aLocationId, String aLocation )
   {
      mLocationIdtoLocationPathMap.put( new Integer( aLocationId ), aLocation );
   }
   
   /**
    * Returns the matching location path for this process id.
    * @param aLocationId
    */
   public String getLocationPath( int aLocationId )
   {
      return (String)mLocationIdtoLocationPathMap.get( new Integer(aLocationId) );
   }
   
   /**
    * Returns true if there are no queued message receivers.
    */
   public boolean isEmpty()
   {
      return mResults == null || mResults.length == 0;
   }
}
