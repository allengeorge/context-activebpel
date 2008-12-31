// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/produce/IAeMessageDataProducer.java,v 1.2 2006/11/02 17:53:2
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
import org.activebpel.rt.message.IAeMessageData;

/**
 * Defines interface for producing outgoing message data.
 */
public interface IAeMessageDataProducer
{
   /**
    * Produces outgoing message data. The data produced must not be modifiable
    * by any of the other activities within the process. As such, it should be
    * a clone of any data that was used to construct the message data. 
    * 
    * @return IAeMessageData 
    */
   public IAeMessageData produceMessageData() throws AeBusinessProcessException;
}
