//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeImplicitTerminationHandler.java,v 1.2 2006/06/26 16:50:3
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
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.AeActivityCompensateImpl;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;

/**
 * Implicit version of termination handler that contains the default compensate activity
 */
public class AeImplicitTerminationHandler extends AeTerminationHandler
{
   /**
    * ctor accepts the def
    * @param aParent
    */
   public AeImplicitTerminationHandler(AeActivityScopeImpl aParent)
   {
      super(null, aParent);
      addActivity(AeActivityCompensateImpl.createImplicitCompensation(this));
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeTerminationHandler#isEnableCoordinatedActivityCompensation()
    */
   protected boolean isEnableCoordinatedActivityCompensation()
   {
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeImplicitCompensationHandler#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept(IAeImplVisitor aVisitor) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeImplicitCompensationHandler#getLocationPath()
    */
   public String getLocationPath()
   {
      IAeBpelObject parent = getParent();
      StringBuffer buffer = new StringBuffer(parent.getLocationPath());
      return buffer.append(AeSupportActivityLocationPathSuffix.IMPLICIT_TERMINATION_HANDLER).toString(); 
   }

   /**
    * This object gets created because there was no compensation handler defined for the scope.
    * As such, there is no definition object and calling this method results in an exception.
    * @see org.activebpel.rt.bpel.impl.AeAbstractBpelObject#getDefinition()
    */
   public AeBaseDef getDefinition()
   {
      throw new UnsupportedOperationException(AeMessages.getString("AeImplicitTerminationHandler.ERROR_1")); //$NON-NLS-1$
   }

   /**
    * Overrides method to return <code>false</code>, since {@link
    * #getDefinition()} will fail. 
    * 
    * @see org.activebpel.rt.bpel.impl.IAeBpelObject#hasLocationId()
    */
   public boolean hasLocationId()
   {
      return false;
   }
}
 
