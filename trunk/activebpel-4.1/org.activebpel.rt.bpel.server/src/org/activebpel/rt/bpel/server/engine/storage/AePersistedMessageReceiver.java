// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/AePersistedMessageReceiver.java,v 1.3 2006/09/22 19:56:1
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
package org.activebpel.rt.bpel.server.engine.storage;

import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AePartnerLinkOpKey;
import org.activebpel.rt.bpel.impl.queue.AeMessageReceiver;

/**
 * This class is used by the SQL version of the QueueStorage to conveniently
 * return AeMessageReceiver objects with a database ID attached.  This database
 * ID is often used to delete the receive object from the database.
 */
public class AePersistedMessageReceiver extends AeMessageReceiver
{
   /** The queued receive id from the database. */
   protected int mQueuedReceiveId;

   /**
    * Constructs a SQL message receiver.
    * @param aQueuedReceiveId The database ID.
    * @param aProcessId The process id.
    * @param aProcessName Qualified name of the process
    * @param aPartnerLinkOpKey The partner link op key.
    * @param aPortType The port type.
    * @param aCorrelation The correlation set.
    * @param aMessageReceiverPathId The message receiver path id.
    * @param aGroupId The group id of the message receiver.
    * @param aConcurrent True if message receiver supports concurrent messages
    */
   public AePersistedMessageReceiver(int aQueuedReceiveId, long aProcessId, QName aProcessName,
         AePartnerLinkOpKey aPartnerLinkOpKey, QName aPortType, Map aCorrelation,
         int aMessageReceiverPathId, int aGroupId, boolean aConcurrent)
   {
      super(aProcessId, aProcessName, aPartnerLinkOpKey, aPortType, aCorrelation,
            aMessageReceiverPathId, aGroupId, aConcurrent);
      mQueuedReceiveId = aQueuedReceiveId;
   }

   /**
    * Returns the queued receive database id.
    */
   public int getQueuedReceiveId()
   {
      return mQueuedReceiveId;
   }
}
