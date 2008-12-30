// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeActivityValidateImpl.java,v 1.2 2006/09/22 19:52:3
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

import java.util.Iterator;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.AeActivityValidateDef;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;

/**
 * Implementation of the BPEL validate activity.
 */
public class AeActivityValidateImpl extends AeActivityImpl
{
   /**
    * Ctor accepts the def and parent
    * 
    * @param aValidate
    * @param aParent
    */
   public AeActivityValidateImpl(AeActivityValidateDef aValidate, IAeActivityParent aParent)
   {
      super(aValidate, aParent);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeAbstractBpelObject#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept(IAeImplVisitor aVisitor) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeExecutableQueueItem#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      try
      {
         // Loop through all variables and validate each one
         for(Iterator iter = ((AeActivityValidateDef)getDefinition()).getVariables(); iter.hasNext();)
         {
            IAeVariable var = findVariable(iter.next().toString());
            var.validate();
         }

         objectCompleted();
      }
      catch(AeBpelException e)
      {
         objectCompletedWithFault(getFaultFactory().getInvalidVariables());
      }
   }
} 
