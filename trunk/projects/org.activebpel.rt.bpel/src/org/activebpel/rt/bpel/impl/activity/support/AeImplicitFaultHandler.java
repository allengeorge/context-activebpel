// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeImplicitFaultHandler.java,v 1.12 2006/06/26 16:50:3
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.AeActivityCompensateImpl;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;

/**
 * Implements the implicit fault handling routine which is simply to run the
 * default compensation routine followed by rethrowing of the fault. 
 */
public class AeImplicitFaultHandler extends AeDefaultFaultHandler
{
   /**
    * Creates the implicit fault handler
    * @param aParent
    * @param aFault
    */
   public AeImplicitFaultHandler(AeActivityScopeImpl aParent, IAeFault aFault)
   {
      super(null, aParent);
      addActivity(AeActivityCompensateImpl.createImplicitCompensation(this));
      setFault(aFault);
   }
   
   /**
    * Since an implicit fault handler runs an implicit compensate activity, all of
    * the compensatable activities are compensated (via implicit compensate activity) 
    * including any coordinated (invoke) activities. Additional implicit compensate
    * activity is not required and this method always returns false.
    *   
    * @return false for implicit false handler.
    */
   protected boolean isEnableCoordinatedActivityCompensation()
   {
      // Return false since by default, the ImplicitFaultHandler runs an implicit compensate
      // activity (which in turn runs all the comp info objects, including coordinated comp infos).  
      return false;
   }   
   
   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept( IAeImplVisitor aVisitor ) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.AeAbstractBpelObject#getDefinition()
    */
   public AeBaseDef getDefinition()
   {
      throw new UnsupportedOperationException(AeMessages.getString("AeImplicitFaultHandler.ERROR_0")); //$NON-NLS-1$
   }

   /**
    * This is overridden because we don't have a def object. Our location path
    * is equal to the parent's path plus _ImplicitFaultHandler
    * @see org.activebpel.rt.bpel.impl.IAeBpelObject#getLocationPath()
    */
   public String getLocationPath()
   {
      IAeBpelObject parent = getParent();
      StringBuffer buffer = new StringBuffer(parent.getLocationPath());
      return buffer.append(AeSupportActivityLocationPathSuffix.IMPLICIT_FAULT_HANDLER).toString(); 
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeBpelObject#hasLocationId()
    */
   public boolean hasLocationId()
   {
      // There is no definition object to give us a location id.
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFCTHandler#notifyScopeOfCompletion()
    */
   protected void notifyScopeOfCompletion() throws AeBusinessProcessException
   {
      boolean allowedToRethrow = isAllowedToRethrow();
      IAeFault fault = getFault();
      setFault(null);

      if (allowedToRethrow)
      {
         // implicit fault handler ALWAYS rethrows the fault.
         notifyScopeOfFaultedCompletion(fault);
      }
      else
      {
         // the only time that we're not allowed to rethrow is if the fault
         // in question is the bpws:forcedTermination or ae:retryFault. If this is the 
         // case then the allowedToRethrow flag will be set for us by the
         // scope so we can then complete silently as opposed to rethrowing.
         super.notifyScopeOfCompletion();
      }
   }
}
