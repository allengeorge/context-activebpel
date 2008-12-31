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
package org.activebpel.rt.bpel.impl.activity;

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.visitors.AeDefPathParallelVisitor;
import org.activebpel.rt.bpel.def.visitors.IAeDefPathSegmentVisitor;

/**
 * Extension of the location path visitor that creates a custom path for the
 * root scope of the forEach/onEvent. All of the other nodes visited will get their
 * standard path which will include the scope's special instance predicate which
 * serves to make the paths unique. 
 */
public class AeImplPathParallelVisitor extends AeDefPathParallelVisitor
{
   /** map of def objects to their path information */
   private Map mDefToPathMap = new HashMap();
   /** flag that tells us if we're in create mode or not */
   private boolean mCreateMode;
   
   /**
    * Ctor
    * 
    * @param aSegmentVisitor provides the values for the segment paths
    * @param aNextLocationId provides the next location id
    * @param aBasePath the base path that provides the context of our instance
    * @param aDef the root def for our instance path
    * @param aCreateFlag if true, we're creating new instances as opposed to restoring existing ones
    */
   public AeImplPathParallelVisitor(IAeDefPathSegmentVisitor aSegmentVisitor, int aNextLocationId, String aBasePath, AeBaseDef aDef, boolean aCreateFlag)
   {
      super(aSegmentVisitor, aBasePath, aDef);
      setCreateMode(aCreateFlag);
      
      setNextLocationId(aNextLocationId);
   }
         
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeDefPathVisitor#recordLocationPathAndId(org.activebpel.rt.bpel.def.AeBaseDef, java.lang.String, int)
    */
   protected void recordLocationPathAndId(AeBaseDef aDef,
         String aLocationPath, int aLocationId)
   {
      // the mapping of location path to id will be done already
      // we just need to record the mapping of the def to its location path
      mDefToPathMap.put(aDef, aLocationPath);
   }
   
   /**
    * Getter for the location path
    * 
    * @param aDef
    */
   protected String getLocationPath(AeBaseDef aDef)
   {
      return (String) mDefToPathMap.get(aDef);
   }
   
   /**
    * If we're being called as part of the forEach's/onEvent's execution, then we create
    * the objects and set their location ids in place. We callback onto the
    * process to record each new location id since the process will need to track
    * the ids to ensure that they're unique.
    * 
    * If we're being called from the restoreChildren() method then we create
    * the child instances with a location id of -1 since the real id will be
    * set by the restore visitor from the state document. 
    * 
    * @see org.activebpel.rt.bpel.def.visitors.AeDefPathVisitor#getNextLocationId()
    */
   protected void setNextLocationId(int aId)
   {
      if (isCreateMode())
      {
         super.setNextLocationId(aId);
      }
      else
      {
         super.setNextLocationId(-1);
      }
   }

   /**
    * @return Returns the createMode.
    */
   public boolean isCreateMode()
   {
      return mCreateMode;
   }

   /**
    * @param aCreateMode The createMode to set.
    */
   public void setCreateMode(boolean aCreateMode)
   {
      mCreateMode = aCreateMode;
   }
}
