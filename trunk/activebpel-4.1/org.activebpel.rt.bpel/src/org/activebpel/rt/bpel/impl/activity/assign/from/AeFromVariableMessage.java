// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/from/AeFromVariableMessage.java,v 1.5 2007/05/24 00:50:3
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
package org.activebpel.rt.bpel.impl.activity.assign.from; 

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.impl.activity.assign.AeMessageVariableWrapper;

/**
 * Handles selecting the whole message 
 */
public class AeFromVariableMessage extends AeFromBase
{
   /**
    * Ctor takes def 
    * 
    * @param aFromDef
    */
   public AeFromVariableMessage(AeFromDef aFromDef)
   {
      super(aFromDef);
   }
   
   /**
    * Ctor accepts variable name
    * @param aVariableName
    */
   public AeFromVariableMessage(String aVariableName)
   {
      setVariableName(aVariableName);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeFrom#getFromData()
    */
   public Object getFromData() throws AeBusinessProcessException
   {
      return new AeMessageVariableWrapper(getVariable());
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.from.AeFromBase#getAttachmentsSource()
    */
   public IAeAttachmentContainer getAttachmentsSource()
   {
      return getVariable().getAttachmentData();
   }
}
 
