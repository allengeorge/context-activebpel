//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/reply/IAeTransmissionTracker.java,v 1.3 2006/07/25 17:30:3
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
import org.activebpel.rt.util.AeLongSet;
import org.activebpel.wsio.invoke.IAeInvoke;

/**
 * Interface for the Invoke and Reply transmission and receive id tracker.
 */
public interface IAeTransmissionTracker 
{
   /** Value of an uninitialized id. */
   public static long NULL_TRANSREC_ID = 0;
   /** Indictates an unitialized state. */
   public static int NULL_STATE = 0;   
   /** Indictates a transmitted state. */
   public static int TRANSMITTED_STATE = 1;
   /** Indictates a received state. */
   public static int RECEIVED_STATE = 2;
   
   /** 
    * @return Returns an instance of a factory to create durable replies.
    */
   public IAeDurableReplyFactory getDurableReplyFactory();
   
   /**
    * Returns the next transmission id.
    * @return next id.
    */
   public long getNextId();
   
   /**
    * Adds the transmission id, message id with the given state.
    * @param aTransmissionId transmission id. 
    * @param aMessageId Invoke handler dependent message id.
    * @param aState transmitted or received state.
    * @throws AeException
    */
   public void add(long aTransmissionId, String aMessageId, int aState) throws AeException;
   
   /**
    * Returns true if the given transmission id exists.
    * @param aTxId transmission id.
    * @return true if the id exists
    * @throws AeException
    */
   public boolean exists(long aTxId) throws AeException;
   
   /**
    * Returns true if the given transmission id exists with the given state.
    * @param aTxId transmission id.
    * @param aState state.
    * @return true if the id exists with the given state 
    * @throws AeException
    */
   public boolean exists(long aTxId, int aState) throws AeException;
   
   
   /**
    * Updates the state given transmission id.
    * @param aTxId transmission id.
    * @param aState transmitted or received state.
    * @throws AeException
    */
   public void update(long aTxId, int aState) throws AeException;
   
   /**
    * Returns the state associated with transmission id. 
    * @param aTxId
    * @return current state.
    * @throws AeException
    */
   public int getState(long aTxId) throws AeException;
   
   /**
    * Returns the message id associated with transmission id. 
    * @param aTxId
    * @return message id or <code>null</code> if not found.
    * @throws AeException
    */
   public String getMessageId(long aTxId) throws AeException;
   
   /**
    * Removes the entry associated with the given id.
    * @param aTxId
    * @throws AeException
    */
   public void remove(long aTxId) throws AeException;

   /**
    * Removes the set of entries based on the given transmission id collection.
    * @param aTransmissionIds
    * @throws AeException
    */
   public void remove(AeLongSet aTransmissionIds) throws AeException;
   
   /**
    * Convenience method that returns true if the invoke was already reliably transmitted based on
    * the existence of a transmission id in a IAeInvoke and the storage layer.
    * @param aInvoke
    * @return true if the invoke was already sent
    */
   public boolean isTransmitted(IAeInvoke aInvoke) throws AeException;
   
   /**
    * Assigns a new transmission id.
    * @param aInvoke
    */   
   public void assignTransmissionId(IAeInvoke aInvoke) throws AeException;
}
