//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/transreceive/AeTransmissionTrackerEntry.java,v 1.1 2006/05/24 23:16:3
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
package org.activebpel.rt.bpel.server.transreceive;

/**
 * Entry containing transmission id, state and associated message id.
 *
 */
public class AeTransmissionTrackerEntry
{
   /** Entry id */
   private long mTransmissionId;
   /** Entry state */
   private int mState;
   /** Optional message id. */
   private String mMessageId;

   /**
    * Constructs an entry given entry id and state.
    * @param aTransmissionId
    * @param aState
    */
   public AeTransmissionTrackerEntry(long aTransmissionId, int aState)
   {
      this(aTransmissionId, aState, null);
   }
   
   /**
    * Constructs entry given id, state and message id.
    * @param aTransmissionId
    * @param aState
    * @param aMessageId
    */
   public AeTransmissionTrackerEntry(long aTransmissionId, int aState, String aMessageId)
   {
      mTransmissionId = aTransmissionId;
      mState = aState;
      mMessageId = aMessageId;
   }

   /**
    * @return Returns the messageId.
    */
   public String getMessageId()
   {
      return mMessageId;
   }
     
   /**
    * @param aMessageId The messageId to set.
    */
   public void setMessageId(String aMessageId)
   {
      mMessageId = aMessageId;
   }

   /**
    * @param aState The state to set.
    */
   public void setState(int aState)
   {
      mState = aState;
   }

   /**
    * @return Returns the state.
    */
   public int getState()
   {
      return mState;
   }

   /**
    * @return Returns the transmissionId.
    */
   public long getTransmissionId()
   {
      return mTransmissionId;
   }

}
