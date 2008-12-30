// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/attachment/AeStreamAttachmentItem.java,v 1.1 2007/05/24 00:55:1
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

import java.io.InputStream;
import java.util.Map;

import org.activebpel.rt.attachment.IAeAttachmentItem;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeBusinessProcess;

/**
 * Implements an attachment item that has not yet been stored by the attachment
 * manager.
 */
public class AeStreamAttachmentItem implements IAeAttachmentItem
{
   /** <code>InputStream</code> to the attachment's content. */
   private final InputStream mContent;
   
   /** <code>Map</code> of attachment's MIME headers. */
   private final Map mHeaders;

   /**
    * Default constructor.
    */
   public AeStreamAttachmentItem(InputStream aContent, Map aHeaders)
   {
      mContent = aContent;
      mHeaders = aHeaders;
   }

   /**
    * @see org.activebpel.rt.attachment.IAeAttachmentItem#getAttachmentId()
    */
   public long getAttachmentId()
   {
      throw new IllegalStateException(AeMessages.getString("AeStreamAttachmentItem.ERROR_AttachmentId")); //$NON-NLS-1$
   }

   /**
    * Return the <code>InputStream</code> to the attachment's content.
    */
   public InputStream getContent()
   {
      return mContent;
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
      return mHeaders;
   }

   /**
    * @see org.activebpel.rt.attachment.IAeAttachmentItem#getProcessId()
    */
   public long getProcessId()
   {
      // Not associated with a process.
      return IAeBusinessProcess.NULL_PROCESS_ID;
   }
}
