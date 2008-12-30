// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/consume/AeVariableMessageDataConsumer.java,v 1.5 2007/06/28 22:00:4
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
package org.activebpel.rt.bpel.impl.activity.wsio.consume;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.message.IAeMessageData;
import org.w3c.dom.Document;

/**
 * Implements a message data producer that copies data to a variable.
 */
public class AeVariableMessageDataConsumer extends AeAbstractMessageDataConsumer
{
   /**
    * Constructs a variable message data consumer for the given
    * context.
    *
    * @param aContext
    */
   public AeVariableMessageDataConsumer(IAeMessageDataConsumerContext aContext)
   {
      super(aContext);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumer#consumeMessageData(org.activebpel.rt.message.IAeMessageData)
    */
   public void consumeMessageData(IAeMessageData aMessageData) throws AeBusinessProcessException
   {
      IAeVariable variable = getVariable();

      // By the time we get here, we know that this is either a message type
      // variable or an element variable.
      if (variable.isMessageType())
      {
         variable.setMessageData(aMessageData);
      }
      else
      {
         try
         {
            String partName = (String) aMessageData.getPartNames().next();
            Document doc = (Document) aMessageData.getData(partName); 
            variable.setElementData(doc.getDocumentElement());
            
            if (aMessageData.hasAttachments())
               variable.setAttachmentData(aMessageData.getAttachmentContainer());
         }
         catch (Throwable t)
         {
            // Note: The only way that we should fault here is if validation is
            // turned off and we are consuming an incomplete message.
            //
            if (t instanceof AeBusinessProcessException)
            {
               throw (AeBusinessProcessException) t;
            }
            
            throw new AeBusinessProcessException(t.getLocalizedMessage(), t);
         }
      }
   }
}
