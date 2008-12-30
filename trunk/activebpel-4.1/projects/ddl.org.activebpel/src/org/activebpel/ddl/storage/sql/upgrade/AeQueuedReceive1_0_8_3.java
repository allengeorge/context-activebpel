//$Header: /Development/AEDevelopment/projects/ddl.org.activebpel/src/org/activebpel/ddl/storage/sql/upgrade/AeQueuedReceive1_0_8_3.java,v 1.2 2005/07/19 19:24:3
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
package org.activebpel.ddl.storage.sql.upgrade;


/**
 * Represents a Queued Receive object in the database for version 1.0.8.3 of the DB (or at least the subset of
 * the object that is required for upgrading).
 */
public class AeQueuedReceive1_0_8_3
{
   /** The queued receive id. */
   private int mQueuedReceiveId;
   /** The queued receive's new match hash. */
   private int mNewMatchHash;
   /** The queued receive's new correlation hash. */
   private int mNewCorrelationHash;

   /**
    * Constructs the queued receive from the given id and hash values.
    * 
    * @param aQueuedReceiveId
    * @param aMatchHash
    * @param aCorrelationHash
    */
   public AeQueuedReceive1_0_8_3(int aQueuedReceiveId, int aMatchHash, int aCorrelationHash)
   {
      setQueuedReceiveId(aQueuedReceiveId);
      setNewMatchHash(aMatchHash);
      setNewCorrelationHash(aCorrelationHash);
   }
   
   /**
    * @return Returns the newCorrelationHash.
    */
   public int getNewCorrelationHash()
   {
      return mNewCorrelationHash;
   }

   /**
    * @param aNewCorrelationHash The newCorrelationHash to set.
    */
   protected void setNewCorrelationHash(int aNewCorrelationHash)
   {
      mNewCorrelationHash = aNewCorrelationHash;
   }

   /**
    * @return Returns the newMatchHash.
    */
   public int getNewMatchHash()
   {
      return mNewMatchHash;
   }

   /**
    * @param aNewMatchHash The newMatchHash to set.
    */
   protected void setNewMatchHash(int aNewMatchHash)
   {
      mNewMatchHash = aNewMatchHash;
   }

   /**
    * @return Returns the queuedReceiveId.
    */
   public int getQueuedReceiveId()
   {
      return mQueuedReceiveId;
   }

   /**
    * @param aQueuedReceiveId The queuedReceiveId to set.
    */
   protected void setQueuedReceiveId(int aQueuedReceiveId)
   {
      mQueuedReceiveId = aQueuedReceiveId;
   }
}
