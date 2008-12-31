// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/produce/AeVariableMessageDataProducer.java,v 1.5 2007/04/23 23:26:3
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
package org.activebpel.rt.bpel.impl.activity.wsio.produce;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Implements a message data producer that copies data from a variable.
 */
public class AeVariableMessageDataProducer extends AeAbstractMessageDataProducer implements IAeMessageDataProducer
{
   /**
    * Constructs a variable message data producer for the given
    * context activity.
    *
    * @param aContext
    */
   public AeVariableMessageDataProducer(IAeMessageDataProducerContext aContext)
   {
      super(aContext);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducer#produceMessageData()
    */
   public IAeMessageData produceMessageData() throws AeBusinessProcessException
   {
      IAeVariable variable = getVariable();
      IAeMessageData result = null;

      // By the time we get here, we know that this is either a message type
      // variable or an element variable.
      if (variable.isMessageType())
      {
         result = (IAeMessageData) variable.getMessageData();
      }
      else
      {
         String partName = (String) getMessagePartsMap().getPartNames().next();
         result = createMessageData();
         result.setData(partName, variable.getElementData().getOwnerDocument());
         result.setAttachmentContainer(variable.getAttachmentData());
      }

      return (IAeMessageData) result.clone();
   }
}
