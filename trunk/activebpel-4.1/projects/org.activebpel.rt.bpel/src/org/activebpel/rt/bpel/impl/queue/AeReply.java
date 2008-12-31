// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/queue/AeReply.java,v 1.8 2006/07/10 16:32:4
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
package org.activebpel.rt.bpel.impl.queue;

import org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver;

/**
 * An in memory queue object for replies.
 */
public class AeReply
{
   /** The invoke that's waiting for a reply */
   private IAeReplyReceiver mReplyReceiver;

   /** Process id is needed to differentiate between queued objects */
   private long mProcessId;
   
   /** Reply id assigned to this reply. */
   private long mReplyId;
   
   /**
    * Create reply queue object given partner link name, port type, operation,
    * message exchange path and the open message activity's reply receiver. 
    * @param aProcessId
    * @param aReplyId reply id 
    */   
   public AeReply(long aProcessId, long aReplyId)
   {
      this(aProcessId, aReplyId, null); 
   }    
   /**
    * Create reply queue object given partner link name, port type, operation,
    * message exchange path and the open message activity's reply receiver. 
    * @param aProcessId
    * @param aReplyId reply id 
    * @param aReplyReceiver 
    */   
   public AeReply(long aProcessId, long aReplyId, IAeReplyReceiver aReplyReceiver)
   { 
      setProcessId(aProcessId);
      setReplyReceiver( aReplyReceiver );
      setReplyId (aReplyId );      
   }    
   
   /**
    * @return Returns the replyId.
    */
   public long getReplyId()
   {
      return mReplyId;
   }

   /**
    * @param aReplyId The replyId to set.
    */
   public void setReplyId(long aReplyId)
   {
      mReplyId = aReplyId;
   }

   
   /**
    * Getter for the response receiver.
    */
   public IAeReplyReceiver getReplyReceiver()
   {
      return mReplyReceiver; 
   }

   /**
    * Setter for the reply receiver.
    * @param aReplyReceiver
    */
   public void setReplyReceiver(IAeReplyReceiver aReplyReceiver)
   {
      mReplyReceiver = aReplyReceiver;
   }
   
   /**
    * Sets the process id.
    * @param processId
    */
   public void setProcessId(long processId)
   {
      mProcessId = processId;
   }

   /**
    * Returns the process id.
    */
   public long getProcessId()
   {
      return mProcessId;
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObject)
   {
      if (aObject instanceof AeReply)
      {
         AeReply other = (AeReply) aObject;
         return getReplyId() == other.getReplyId();
      }
      return false;
   }

   /**
    * Overrides to return the hashcode of the replyId, which is unique.
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return new Long(getReplyId()).hashCode();
   }
}
