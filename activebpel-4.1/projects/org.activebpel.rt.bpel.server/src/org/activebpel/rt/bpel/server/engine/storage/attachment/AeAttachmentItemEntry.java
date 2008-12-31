// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/attachment/AeAttachmentItemEntry.java,v 1.2 2007/05/08 19:19:4
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
package org.activebpel.rt.bpel.server.engine.storage.attachment;

import java.util.Map;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.w3c.dom.Document;

/**
 * Entry containing attachment group id, attachment item id, and serialized
 * attachment headers <code>Document</code> 
 */
public class AeAttachmentItemEntry
{
   /** Attachment group id. */
   private long mAttachmentGroupId;
   
   /** Attachment id. */
   private long mAttachmentId;

   /** Serialized attachment headers <code>Document</code>. */
   private Document mHeadersDocument;

   /** Deserialized attachment headers. */
   private Map mHeaders;

   /**
    * Default constructor.
    */
   private AeAttachmentItemEntry()
   {
   }
   
   /**
    * Constructs attachment item entry from specified values.
    *
    * @param aAttachmentGroupId
    * @param aAttachmentId
    * @param aHeadersDocument
    */
   public AeAttachmentItemEntry(long aAttachmentGroupId, long aAttachmentId, Document aHeadersDocument)
   {
      mAttachmentGroupId  = aAttachmentGroupId;
      mAttachmentId = aAttachmentId;
      mHeadersDocument = aHeadersDocument;
   }
   
   /**
    * @return attachment group id.
    */
   public long getAttachmentGroupId()
   {
      return mAttachmentGroupId;
   }

   /**
    * @return attachment item id.
    */
   public long getAttachmentId()
   {
      return mAttachmentId;
   }

   /**
    * @return attachment headers.
    */
   public Map getHeaders() throws AeBusinessProcessException
   {
      if ((mHeaders == null) && (getHeadersDocument() != null))
      {
         mHeaders = AePairDeserializer.deserialize(getHeadersDocument());
      }

      return mHeaders;
   }

   /**
    * @return serialized attachment headers <code>Document</code>.
    */
   public Document getHeadersDocument()
   {
      return mHeadersDocument;
   }
}
