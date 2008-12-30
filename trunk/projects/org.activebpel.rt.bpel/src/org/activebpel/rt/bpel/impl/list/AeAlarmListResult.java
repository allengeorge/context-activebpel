// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/list/AeAlarmListResult.java,v 1.3 2006/06/26 16:50:4
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

/**
 * Wraps a listing of alarm objects.
 */
public class AeAlarmListResult extends AeListResult implements Serializable
{
   /** Mapping of process ids to location paths. */
   protected Map mLocationIdtoLocationPathMap;
   
   /**
    * Constructor.
    * @param aTotalRows Total rows that matched selection criteria.  This number may be greater than the number of results in this listing.
    * @param aAlarms The matching alarms.
    */
   public AeAlarmListResult( int aTotalRows, List aAlarms )
   {
      super( aTotalRows, aAlarms, true );
      mLocationIdtoLocationPathMap = new HashMap();
   }
   
   /**
    * Accessor for alarms.
    */
   public AeAlarmExt[] getResults()
   {
      return (AeAlarmExt[])getResultsInternal().toArray( new AeAlarmExt[getResultsInternal().size()]);
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
}
