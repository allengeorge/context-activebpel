// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/produce/AeEmptyMessageDataProducer.java,v 1.2 2006/08/18 22:20:3
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
import org.activebpel.rt.message.AeEmptyMessage;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Implements a message data producer that produces an empty message.
 */
public class AeEmptyMessageDataProducer extends AeAbstractMessageDataProducer implements IAeMessageDataProducer
{
   /**
    * Constructs a message data producer that produces an empty message for the
    * given context
    *
    * @param aContext
    */
   public AeEmptyMessageDataProducer(IAeMessageDataProducerContext aContext)
   {
      super(aContext);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducer#produceMessageData()
    */
   public IAeMessageData produceMessageData() throws AeBusinessProcessException
   {
      return new AeEmptyMessage(getMessageType());
   }
}
