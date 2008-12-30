// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/attachment/AeStoredAttachmentItem.java,v 1.2 2007/05/24 00:56:3
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
package org.activebpel.rt.bpel.impl.attachment;

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.attachment.IAeAttachmentItem;
import org.activebpel.rt.bpel.IAeBusinessProcess;

/**
 * Represents an attachment item that has been stored by the attachment manager.
 */
public class AeStoredAttachmentItem implements IAeAttachmentItem
{
   /** <code>Map</code> of attachment's MIME headers. */
   private Map mHeaders = null;

   /** Attachment's id. */
   private long mAttachmentId;

   /** The id of the attachment group that contains this attachment item. */
   private long mGroupId;
   
   /**
    * The id of the associated process.
    * {@link IAeBusinessProcess#NULL_PROCESS_ID} means not yet associated with a
    * process.
    */
   private long mProcessId = IAeBusinessProcess.NULL_PROCESS_ID;

   /**
    * Default constructor.
    */
   public AeStoredAttachmentItem()
   {
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObject)
   {
      if ( aObject != null && aObject instanceof AeStoredAttachmentItem )
      {
         AeStoredAttachmentItem other = (AeStoredAttachmentItem) aObject;
         return (getAttachmentId() == other.getAttachmentId());
      }
      return false;
   }
   
   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return (int) getAttachmentId();
   }

   /**
    * @see org.activebpel.rt.attachment.IAeAttachmentItem#getAttachmentId()
    */
   public long getAttachmentId()
   {
      return mAttachmentId;
   }

   /**
    * @return attachment group id
    */
   public long getGroupId()
   {
      return mGroupId;
   }

   /**
    * @see org.activebpel.rt.attachment.IAeAttachmentItem#getHeader(java.lang.String)
    */
   public String getHeader(String aHeaderName)
   {
      return (String) getHeaders().get(aHeaderName);
   }

   /**
    * @see org.activebpel.rt.attachment.IAeAttachmentItem#getHeaders()
    */
   public Map getHeaders()
   {
      if (mHeaders == null)
      {
         mHeaders = new HashMap();
      }

      return mHeaders;
   }

   /**
    * @see org.activebpel.rt.attachment.IAeAttachmentItem#getProcessId()
    */
   public long getProcessId()
   {
      return mProcessId;
   }
   
   /**
    * Sets the attachment's id.
    *
    * @param aAttachmentId
    */
   public void setAttachmentId(long aAttachmentId)
   {
      mAttachmentId = aAttachmentId;
   }

   /**
    * Sets attachment's group id.
    *
    * @param aGroupId
    */
   public void setGroupId(long aGroupId)
   {
      mGroupId = aGroupId;
   }

   /**
    * Sets the attachment's headers.
    *
    * @param aHeaders
    */
   public void setHeaders(Map aHeaders)
   {
      mHeaders = aHeaders;
   }

   /**
    * Sets associated process id.
    *
    * @param aProcessId
    */
   public void setProcessId(long aProcessId)
   {
      mProcessId = aProcessId;
   }
}
