//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeCoordinatorCompInfo.java,v 1.5 2006/10/05 22:39:0
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
package org.activebpel.rt.bpel.impl.activity.support;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.coord.IAeCoordinator;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;

/**
 * CompensationInfo wrapper for coordinated activities. 
 */
public class AeCoordinatorCompInfo extends AeCompInfo
{
   /**
    * Compensation handler.
    */
   private AeCompensationHandler mHandler = null;
   
   /**
    * Coordination id.
    */
   private String mCoordinationId = null;
   
   /**
    * Constructs a CompInfo given the coordinator. 
    */
   public AeCoordinatorCompInfo(AeActivityScopeImpl aEnclosedScope, IAeCoordinator aCoordinator)
   {
      this(aEnclosedScope, aCoordinator.getCoordinationId());
   }

   /**
    * Constructs a compInfo given enclosing scope and coordination id.
    * @param aEnclosedScope
    * @param aCoordinationId
    */
   public AeCoordinatorCompInfo(AeActivityScopeImpl aEnclosedScope, String aCoordinationId)
   {
      super(aEnclosedScope);

      mCoordinationId = aCoordinationId;
   }

   /** 
    * @return Returns true since this is a coordinated comp info.
    */
   public boolean isCoordinated()
   { 
      return true;
   }
   
   /**
    * @return Returns the coordinationId.
    */
   public String getCoordinationId()
   {
      return mCoordinationId;
   }
     
   /**
    * Extracts matching info objects from the linked list of completed enclosed scopes.
    * @param aScopeName
    */
   public List getEnclosedInfoByScopeName(String aScopeName)
   {
      return Collections.EMPTY_LIST;
   }
   
   /**
    * Creates the variable snapshot.
    */
   public void recordSnapshot()
   {
      // no-op
   }
   
   /**
    * Adds an enclosed scope's compensation information to our collection of
    * enclosed scopes.
    * @param aCompInfo
    */
   public void add(AeCompInfo aCompInfo, AeActivityScopeImpl aScope)
   {
      //no-op
   }
      
   /**
    * Getter for the snapshot.
    */
   public AeScopeSnapshot getSnapshot()
   {
      return new AeScopeSnapshot();
   }

   /**
    * Setter for the snapshot.
    */
   public void setSnapshot(AeScopeSnapshot aSnapshot)
   {
      // no op
   }
   
   /**
    * Getter for the enclosed scopes list
    */
   public LinkedList getEnclosedScopes()
   {
      return new LinkedList(Collections.EMPTY_LIST);
   }
   
   /**
    * Setter for the enclosed scopes list
    */
   public void setEnclosedScopes(List aEnclosedScopes)
   {
      // no -op
   }

   /**
    * Sets the compensation info on the scope and prepares the compensation handler
    * for execution.
    */
   public void prepareForCompensation(IAeCompensationCallback aCallback) throws AeBusinessProcessException
   {
      // typically called by AeActivityCompensateImpl.
      AeCompensationHandler handler = getCompensationHandler();
      getScope().getCoordinationContainer().addCompensationHandler(handler);            
      handler.setCallback(aCallback);
      handler.setCompInfo(this);      
   }
   
   /**
    * Removes the compensation info from the scope and compensation handler after
    * execution. After the comp info is removed it is disabled to prevent further
    * compensation with this same instance data.
    */
   public void compensationComplete()
   {   
      // typically called by AeActivityCompensateImpl. via comp.Handler  when the 
      // compHandler has completed its execution.
      
      AeCompensationHandler handler = getCompensationHandler();
      getScope().getCoordinationContainer().removeCompensationHandler(handler);      
      handler.setCallback(null);
      handler.setCompInfo(null);            
      setEnabled(false);
   }
   
   /**
    * Returns the compensation handler.
    */
   public AeCompensationHandler getCompensationHandler()
   {
      // return the instance of a coordinated compensation handler.
      if (mHandler == null)         
      {  
         // first check and see if there is a comp handler in the scope - which was restored from storage.
         mHandler = getScope().getCoordinationContainer().getCompensationHandler( getCoordinationId() );
         if (mHandler == null)
         {
            // create new one.
            mHandler = new AeCoordinatorCompensationHandler(getScope(), getCoordinationId());
         }
      }
      return mHandler;
   }   
}
