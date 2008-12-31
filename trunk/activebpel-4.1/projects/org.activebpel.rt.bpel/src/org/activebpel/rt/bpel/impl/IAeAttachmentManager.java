// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeAttachmentManager.java,v 1.5 2007/07/26 20:50:2
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
package org.activebpel.rt.bpel.impl;

import java.io.InputStream;
import java.util.List;

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.wsio.IAeWebServiceMessageData;

/**
 * A generic attachment manager that provides a means of serializing and converting attachments formats. 
 */
public interface IAeAttachmentManager extends IAeManager
{
   /**
    * Associates the attachments in the given attachment container with the
    * given process.
    *
    * @param aContainer
    * @param aProcessId
    */
   public void associateProcess(IAeAttachmentContainer aContainer, long aProcessId) throws AeBusinessProcessException;
   
   /**
    * Convert attachment items from {@link IAeAttachmentData bpel} to {@link IAeWebServiceMessageData wsio}
    * format.
    *
    * @param aBpelContainer
    * @return list of wsio attachments in IAeWebServiceAttachment format
    */
   public List bpel2wsio(IAeAttachmentContainer aBpelContainer) throws AeBusinessProcessException;

   /**
    * Convert attachment items from {@link IAeWebServiceMessageData wsio} to {@link IAeAttachmentData bpel}
    * format.
    *
    * @param aWsioAttachments
    * @return IAeAttachmenContainer
    */
   public IAeAttachmentContainer wsio2bpel(List aWsioAttachments) throws AeBusinessProcessException;

   /**
    * Deserializes a stored attachment to a stream.
    *
    * @param aAttachmentId
    * @return attachment binary input stream
    */
   public InputStream deserialize(long aAttachmentId) throws AeBusinessProcessException;

   /**
    * Notifies the attachment manager that the engine has finished populating a
    * synchronous response for the given process.
    *
    * @param aProcessId
    */
   public void responseFilled(long aProcessId);

   /**
    * Notifies the attachment manager that the engine is waiting to populate a
    * synchronous response for the given process. Since the response may include
    * attachments, the attachment manager must hold onto any attachments for
    * the process until the engine calls {@link #responseFilled(long)}.
    *
    * @param aProcessId
    */
   public void responsePending(long aProcessId);

   /**
    * Stores the attachments in the given container.
    *
    * @param aContainer
    * @param aPlan May determine whether the attachments are stored locally or persistently
    * @throws AeBusinessProcessException
    */
   public void storeAttachments(IAeAttachmentContainer aContainer, IAeProcessPlan aPlan) throws AeBusinessProcessException;

   /**
    * Stores the attachments in the given container and associates the
    * attachments with the given process (unless <code>aProcessId</code> is
    * {@link IAeBusinessProcess#NULL_PROCESS_ID}).
    *
    * @param aContainer
    * @param aPlan May determine whether the attachments are stored locally or persistently
    * @param aProcessId
    * @throws AeBusinessProcessException
    */
   public void storeAttachments(IAeAttachmentContainer aContainer, IAeProcessPlan aPlan, long aProcessId) throws AeBusinessProcessException;
   
   /**
    * Removes an attachment.
    * @param aAttachmentId
    * @throws AeBusinessProcessException
    */
   public void removeAttachment(long aAttachmentId) throws AeBusinessProcessException;
}
