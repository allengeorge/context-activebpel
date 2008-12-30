//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/visitors/AeRegisterLocationVisitor.java,v 1.3 2006/07/20 20:45:0
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
package org.activebpel.rt.bpel.impl.visitors;

import java.util.Iterator;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.AeBusinessProcess;
import org.activebpel.rt.bpel.impl.AePartnerLink;
import org.activebpel.rt.bpel.impl.AeVariable;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;

/**
 * Visit the objects creating unique ids from process ids and their paths.  This
 * is neccesary during restore state when the state has not included a path in the 
 * document and the path itself has custom locations.
 */
public class AeRegisterLocationVisitor extends AeImplTraversingVisitor
{
   private AeBusinessProcess mProcess;

   /**
    * Construct the register location visitr with the associated business process.
    */
   public AeRegisterLocationVisitor(AeBusinessProcess aProcess)
   {
      super();
      mProcess = aProcess;
   }
   
   /**
    * Extends the method to assign a unique id and register the path with the process. 
    * @see org.activebpel.rt.bpel.impl.visitors.AeImplTraversingVisitor#traverse(org.activebpel.rt.bpel.impl.AeAbstractBpelObject)
    */
   protected void traverse(AeAbstractBpelObject aImpl) throws AeBusinessProcessException
   {
      // assign a unique id
      int locationId = getProcess().getMaxLocationId() + 1;
      aImpl.setLocationId(locationId);
      getProcess().setMaxLocationId(locationId);
      
      // add the bpel object to the process
      getProcess().addBpelObject(aImpl.getLocationPath(), aImpl);
      
      // continue traverse
      super.traverse(aImpl);
   }
   
   /**
    * Extends the method to assign a unique id to variables and partner links and register the variable 
    * paths with the process. 
    * @see org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor#visit(org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl)
    */
   public void visit(AeActivityScopeImpl aImpl) throws AeBusinessProcessException
   {
      if (aImpl.getVariableContainer() != null)
      {
         for(Iterator iter = aImpl.getVariableContainer().iterator(); iter.hasNext(); )
         {
            AeVariable variable = (AeVariable)iter.next();
            getProcess().addVariableMapping(variable);
         }
      }
      for (Iterator iter = aImpl.getPartnerLinks().values().iterator(); iter.hasNext(); )
      {
         AePartnerLink plink = (AePartnerLink) iter.next();
         getProcess().addPartnerLinkMapping(plink);
      }
      super.visit(aImpl);
   }
   
   /**
    * @return Returns the process.
    */
   public AeBusinessProcess getProcess()
   {
      return mProcess;
   }
}
