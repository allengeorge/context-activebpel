//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/transreceive/AeInMemoryTransmissionTracker.java,v 1.2 2006/07/25 17:37:4
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.util.AeLongSet;
import org.activebpel.wsio.invoke.IAeInvoke;

/**
 * In memory implementation of the transmission tracker. 
 */
public class AeInMemoryTransmissionTracker extends AeNoopTransmissionTracker
{
   /** In memory map of entries. */
   private Map mEntries;
   
   /** Next transmission id. */
   private long mNextId;
   
   /**
    * Default constructor.
    */
   public AeInMemoryTransmissionTracker(Map aConfig) throws AeException
   {
      super(aConfig);
      mEntries = new HashMap();
      mNextId = 0;
   }   
      
   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#getNextId()
    */
   public synchronized long getNextId()
   {
      mNextId++;
      return mNextId;
   }

   /** 
    * @return Returns entries map.0
    */
   protected Map getEntries()
   {
      return mEntries;
   }
   
   /** 
    * Convinience method the access an entry given its id.
    * @param aTransmissionId
    * @return
    */
   protected AeTransmissionTrackerEntry getEntry(long aTransmissionId) throws AeException
   {
      AeTransmissionTrackerEntry entry = (AeTransmissionTrackerEntry) getEntries().get( new Long(aTransmissionId) );
      return entry;
   }
   
   /**
    * Convinience method to add a new entry.
    * @param aEntry
    */
   protected void addEntry(AeTransmissionTrackerEntry aEntry) throws AeException
   {
      getEntries().put(new Long(aEntry.getTransmissionId()), aEntry); 
   }
      
   /**
    * Adds the message id with the given state. This method returns a unique
    * transmission id.
    * @param aMessageId Invoke handler dependent message id.
    * @param aState transmitted or received state.
    * @return transmission id.
    * @throws AeException
    */
   public void add(long aTransmissionId, String aMessageId, int aState) throws AeException
   {
      AeTransmissionTrackerEntry entry = new AeTransmissionTrackerEntry(aTransmissionId, aState, aMessageId);
      addEntry(entry);
   }
   
   /**
    * Returns true if the given transmission id and state already exists.
    * @param aTransmissionId transmission id.
    * @return true if id exists.
    * @throws AeException
    */
   public boolean exists(long aTransmissionId) throws AeException
   {
      return getEntry(aTransmissionId) != null;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#exists(long, int)
    */
   public boolean exists(long aTransmissionId, int aState) throws AeException
   {
      AeTransmissionTrackerEntry entry = getEntry(aTransmissionId);
      return (entry != null && entry.getState() == aState);
   }
   
   /**
    * Updates the state given transmission id.
    * @param aTransmissionId transmission id.
    * @param aState transmitted or received state.
    * @throws AeException
    */
   public void update(long aTransmissionId, int aState) throws AeException
   {
      AeTransmissionTrackerEntry entry = getEntry(aTransmissionId);
      if (entry != null)
      {
         entry.setState(aState);
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#getState(long)
    */
   public int getState(long aTransmissionId) throws AeException
   {
      AeTransmissionTrackerEntry entry = getEntry(aTransmissionId);
      if (entry != null)
      {
         return entry.getState();
      }
      else
      {
         return IAeTransmissionTracker.NULL_STATE;
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#getMessageId(long)
    */
   public String getMessageId(long aTransmissionId) throws AeException
   {
      AeTransmissionTrackerEntry entry = getEntry(aTransmissionId);
      if (entry != null)
      {
         return entry.getMessageId();
      }
      else
      {
         return null;
      }
   }
   
   /**
    * Removed the entry associated with the given id.
    * @param aTransmissionId
    * @throws AeException
    */
   public void remove(long aTransmissionId) throws AeException
   {
      getEntries().remove( new Long(aTransmissionId) );
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#remove(org.activebpel.rt.util.AeLongSet)
    */
   public void remove(AeLongSet aTransmissionIds) throws AeException
   {
      Iterator it = aTransmissionIds.iterator();
      while ( it.hasNext() )
      {
         getEntries().remove( (Long) it.next() );
      }
   }
   
   
   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#isTransmitted(org.activebpel.wsio.invoke.IAeInvoke)
    */
   public boolean isTransmitted(IAeInvoke aInvoke) throws AeException
   {
      // Check if this invoke has already been (reliably) delivered based on the existence of 
      // the transmission id in the storage layer.
      // Perform this check only if the transmission id is positive (persistent/durable invoke transmission id)
      return (aInvoke.getTransmissionId() > IAeTransmissionTracker.NULL_TRANSREC_ID) && exists( aInvoke.getTransmissionId() );
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#assignTransmissionId(org.activebpel.wsio.invoke.IAeInvoke)
    */
   public void assignTransmissionId(IAeInvoke aInvoke) throws AeException
   { 
      // Assign a new id only if a durable/persistent id  (positive #) has not already been assigned.
      if (aInvoke.getTransmissionId() <= IAeTransmissionTracker.NULL_TRANSREC_ID)
      {      
         // get the next tranmission id.
         long txId = getNextId();
         // set the tx id in the process state
         aInvoke.setTransmissionId( txId );
         // journal this action.
         AeEngineFactory.getEngine().getProcessManager().journalInvokeTransmitted(aInvoke.getProcessId(), aInvoke.getLocationId(), txId);
      }
   }   
}
