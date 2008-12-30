// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/to/AeToVariableMessage.java,v 1.7 2007/05/24 00:50:3
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
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.activity.assign.AeMessageVariableWrapper;

/**
 * Returns the message variable which will receive all of the parts of the src message 
 */
public class AeToVariableMessage extends AeToBase
{
   /**
    * Ctor accepts def and context
    * 
    * @param aToDef
    */
   public AeToVariableMessage(AeToDef aToDef)
   {
      super(aToDef);
   }
   
   /**
    * Ctor accepts variable 
    * 
    * @param aVariable
    */
   public AeToVariableMessage(String aVariable)
   {
      setVariableName(aVariable);
   }

   /**
    * Returns the message data for the variable that will get populated
    * 
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeTo#getTarget()
    */
   public Object getTarget() throws AeBpelException
   {
      IAeVariable variable = getVariable();
      return new AeMessageVariableWrapper(variable);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.to.AeToBase#getAttachmentsTarget()
    */
   public IAeAttachmentContainer getAttachmentsTarget()
   {
      IAeAttachmentContainer toContainer = getVariable().getAttachmentData();
      toContainer.clear();
      return toContainer;
   }
}
 
