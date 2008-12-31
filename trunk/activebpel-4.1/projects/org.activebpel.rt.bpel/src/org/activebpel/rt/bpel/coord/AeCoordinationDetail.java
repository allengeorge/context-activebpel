//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/coord/AeCoordinationDetail.java,v 1.1 2006/01/25 20:43:4
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
package org.activebpel.rt.bpel.coord;

/**
 * Contains instance information about a coordination.
 */
public class AeCoordinationDetail
{
   /** Process Id */
   private long mProcessId;
   /** Coordination Id */
   private String mCoordinationId;
   /** Current State */
   private String mState;
   /** Location path of activity. */
   private String mLocationPath;

   /**
    * Default ctor.
    * @param aProcessId
    * @param aCoordinationId
    * @param aState
    * @param aLocationPath
    */
   public AeCoordinationDetail(long aProcessId, String aCoordinationId, String aState, String aLocationPath)
   {
      setProcessId(aProcessId);
      setCoordinationId(aCoordinationId);
      setState(aState);
      setLocationPath(aLocationPath);
   }
   
   /**
    * Constructs the detail given a coordination.
    * @param aCoordinating
    */
   public AeCoordinationDetail(IAeCoordinating aCoordinating)
   {
      setProcessId(aCoordinating.getProcessId());
      setCoordinationId(aCoordinating.getCoordinationId());
      setState(aCoordinating.getState().getState());
      setLocationPath(aCoordinating.getLocationPath());
   }

   /**
    * @return Returns the coordinationId.
    */
   public String getCoordinationId()
   {
      return mCoordinationId;
   }

   /**
    * @param aCoordinationId The coordinationId to set.
    */
   public void setCoordinationId(String aCoordinationId)
   {
      mCoordinationId = aCoordinationId;
   }

   /**
    * @return Returns the locationPath.
    */
   public String getLocationPath()
   {
      return mLocationPath;
   }

   /**
    * @param aLocationPath The locationPath to set.
    */
   public void setLocationPath(String aLocationPath)
   {
      mLocationPath = aLocationPath;
   }

   /**
    * @return Returns the processId.
    */
   public long getProcessId()
   {
      return mProcessId;
   }

   /**
    * @param aProcessId The processId to set.
    */
   public void setProcessId(long aProcessId)
   {
      mProcessId = aProcessId;
   }

   /**
    * @return Returns the state.
    */
   public String getState()
   {
      return mState;
   }

   /**
    * @param aState The state to set.
    */
   public void setState(String aState)
   {
      mState = aState;
   }   
}
