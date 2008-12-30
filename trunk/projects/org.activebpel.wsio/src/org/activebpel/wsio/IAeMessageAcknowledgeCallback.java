//$Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/IAeMessageAcknowledgeCallback.java,v 1.1 2006/05/25 00:06:5
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
package org.activebpel.wsio;

/**
 * Callback interface that is used to acknowledge a receipt of a inbound 
 * or out bound message.
 */
public interface IAeMessageAcknowledgeCallback
{

   /**
    * Called after a message has been to handed over to another module.
    * @param aMessageId unique id such as a transmission or journal id. This parameter maybe null.
    * @throws AeException
    */
   public void onAcknowledge(String aMessageId) throws AeMessageAcknowledgeException;
   
   /**
    * Called if the message cannot be delivered. A typical use case if a one-way correlated message
    * was queued to the engine and the engine did not find matching message receiver with in a
    * predetermined time.
    * @param aReason Optional exception that was the root cause of non-delivery. This parameter may be null.
    */
   public void onNotAcknowledge(Throwable aReason);
}
