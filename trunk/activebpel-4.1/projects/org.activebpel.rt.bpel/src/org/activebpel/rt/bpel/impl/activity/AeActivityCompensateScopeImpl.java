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

import java.util.List;

import org.activebpel.rt.bpel.def.activity.AeActivityCompensateScopeDef;
import org.activebpel.rt.bpel.impl.IAeActivityParent;

/**
 * The compensateScope activity gets called from a fault handler or from within an
 * already executing compensation handler. The role of the compensationScope activity
 * is to identify the scope that is getting compensated and then to
 * queue the identified scope's compensation handlers.
 */
public class AeActivityCompensateScopeImpl extends AeActivityCompensateImpl
{
   /** 
    * Constructor for activity.
    * 
    * @param aActivityDef
    * @param aParent
    */
   public AeActivityCompensateScopeImpl(AeActivityCompensateScopeDef aActivityDef, IAeActivityParent aParent)
   {
      this(aActivityDef, aParent, false);
   }

   /**
    * Constructor for activity.
    * 
    * @param aActivityDef activity definition
    * @param aParent enclosing scope or fault handler
    */
   public AeActivityCompensateScopeImpl(AeActivityCompensateScopeDef aActivityDef, IAeActivityParent aParent,
         boolean aMatchCoordinated)
   {
      super(aActivityDef, aParent, aMatchCoordinated);
   }

   /**
    * Gets the name of the scope that we're targeting for compensation.
    */
   protected String getScopeNameForCompensation()
   {
      AeActivityCompensateScopeDef def = (AeActivityCompensateScopeDef) getDefinition();
      return def.getTarget();
   }

   /**
    * Overrides in order to return only scopes matching the 'target' scope name.
    * 
    * @see org.activebpel.rt.bpel.impl.activity.AeActivityCompensateImpl#getMatchingScopes()
    */
   protected List getMatchingScopes()
   {
      String scopeName = getScopeNameForCompensation();
      return getCompInfo().getEnclosedInfoByScopeName(scopeName);
   }
}
