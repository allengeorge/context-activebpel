// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/consume/AeNoopMessageDataConsumer.java,v 1.1 2006/08/03 23:33:0
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

import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Implements a message data consumer that does nothing.
 */
public class AeNoopMessageDataConsumer implements IAeMessageDataConsumer
{
   /**
    * Constructs a noop message data consumer for the given BPEL implementation
    * object.
    */
   public AeNoopMessageDataConsumer(IAeBpelObject aImpl)
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumer#consumeMessageData(org.activebpel.rt.message.IAeMessageData)
    */
   public void consumeMessageData(IAeMessageData aMessageData)
   {
      // Do nothing.
   }
}
