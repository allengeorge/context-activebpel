// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeScopedObject.java,v 1.1 2006/09/19 20:15:5
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

import org.activebpel.rt.bpel.IAeLocatableObject;
import org.activebpel.rt.bpel.def.AeNamedDef;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;

/**
 * Implements a convenience base class for implementation objects that are
 * bound to a scope.
 */
public class AeScopedObject implements IAeLocatableObject
{
   /** The scope containing the object. */
   private AeActivityScopeImpl mScope;

   /** The object's definition. */
   private AeNamedDef mDefinition;

   /** The object's location id. */
   private int mLocationId;

   /** The object's location path. */
   private String mLocationPath;

   /**
    * Constructs a scoped object with the given scope and definition.
    *
    * @param aScope
    * @param aDefinition
    */
   public AeScopedObject(AeActivityScopeImpl aScope, AeNamedDef aDefinition)
   {
      mScope = aScope;
      mDefinition = aDefinition;
   }

   /**
    * Returns the object's definition.
    */
   public AeNamedDef getBaseDef()
   {
      return mDefinition;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeLocatableObject#getLocationId()
    */
   public int getLocationId()
   {
      return hasCustomLocationPath() ? mLocationId : getBaseDef().getLocationId();
   }

   /**
    * @see org.activebpel.rt.bpel.IAeLocatableObject#getLocationPath()
    */
   public String getLocationPath()
   {
      return hasCustomLocationPath() ? mLocationPath : getBaseDef().getLocationPath();
   }

   /**
    * Returns the object's name.
    */
   public String getName()
   {
      return getBaseDef().getName();
   }
   
   /**
    * Returns the business process.
    */
   public IAeBusinessProcessInternal getProcess()
   {
      return getScope().getProcess();
   }

   /**
    * Returns the object's scope.
    */
   protected AeActivityScopeImpl getScope()
   {
      return mScope;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeLocatableObject#hasCustomLocationPath()
    */
   public boolean hasCustomLocationPath()
   {
      return mLocationPath != null;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeLocatableObject#hasLocationId()
    */
   public boolean hasLocationId()
   {
      return true;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeLocatableObject#setLocationId(int)
    */
   public void setLocationId(int aLocationId)
   {
      mLocationId = aLocationId;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeLocatableObject#setLocationPath(java.lang.String)
    */
   public void setLocationPath(String aLocationPath)
   {
      mLocationPath = aLocationPath;
   }
}
