//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/reply/IAeDurableReplyFactory.java,v 1.3 2006/07/10 16:32:4
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
package org.activebpel.rt.bpel.impl.reply;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;

/**
 * Interface for the factory which is responsible for recreating reply receivers
 * using the prototype pattern.
 */
public interface IAeDurableReplyFactory
{
   
   /**
    * Creates a durable reply given the reply type and its properties.
    * @param aReplyId replyId of the corresponding inbound receive.
    * @param aInfo durable reply properties.
    * @return durable reply receiver or AeMissingReplyReceiver if the type is not supported.
    * @throws AeException
    */ 
   public IAeReplyReceiver createReplyReceiver(long aReplyId, IAeDurableReplyInfo aInfo) throws AeBusinessProcessException;
   
   /**
    * Creates and returns the default missing reply receiver implementation.
    * 
    * @param aReplyId
    * @return AeMissingReplyReceiver class.
    */
   public IAeReplyReceiver createMissingReplyReceiver(long aReplyId);
}
