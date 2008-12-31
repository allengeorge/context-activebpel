// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/to/AeToBase.java,v 1.6 2007/05/24 00:50:3
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
package org.activebpel.rt.bpel.impl.activity.assign.to; 

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.impl.activity.assign.AeCopyOperationComponentBase;
import org.activebpel.rt.bpel.impl.activity.assign.IAeTo;

/**
 * Base class for the objects implementing the <to> behavior in a copy operation 
 */
public abstract class AeToBase extends AeCopyOperationComponentBase implements IAeTo
{
   /**
    * Ctor accepts def and context
    * 
    * @param aToDef
    */
   public AeToBase(AeToDef aToDef)
   {
      super();
      setVariableName(aToDef.getVariable());
   }
   
   /**
    * Ctor
    */
   protected AeToBase()
   {
   }
   
   /**
    * Gets the variable (for update) from the copy operation context.
    */
   protected IAeVariable getVariable()
   {
      IAeVariable var = getCopyOperation().getContext().getVariableForUpdate(getVariableName(), getPartName());
      return var;
   }
   
   /**
    * Returns the part name for the assign or null if the strategy isn't dealing
    * with a message variable part.
    */
   protected String getPartName()
   {
      return null;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeTo#getAttachmentsTarget()
    */
   public IAeAttachmentContainer getAttachmentsTarget()
   {
      // Default behaviour
      return null;
   }
}
 
