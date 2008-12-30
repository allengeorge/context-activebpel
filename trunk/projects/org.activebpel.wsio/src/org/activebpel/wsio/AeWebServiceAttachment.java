// $Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/AeWebServiceAttachment.java,v 1.2 2007/05/24 01:13:0
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
package org.activebpel.wsio;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Reference implementation of <code>IAeWebServiceAttachment</code>.
 */
public class AeWebServiceAttachment implements IAeWebServiceAttachment
{
   /** Attachment Mime headers. stored as Strings. key=mimeId */
   protected Map mMimeHeaders;

   /** Attachment Data Content */
   InputStream mDataContent;

   /**
    * Constructor.
    * @param aMimeHeaders
    */
   public AeWebServiceAttachment(InputStream aAttachmentData, Map aMimeHeaders)
   {
      mDataContent = aAttachmentData;
      mMimeHeaders = new HashMap(aMimeHeaders);
   }

   /**
    */
   public InputStream getContent()
   {
      return mDataContent;
   }

   /**
    * @see org.activebpel.wsio.IAeWebServiceAttachment#getMimeHeaders()
    */
   public Map getMimeHeaders()
   {
      return mMimeHeaders;
   }

   /**
    * @see org.activebpel.wsio.IAeWebServiceAttachment#getMimeType()
    */
   public String getMimeType()
   {
      return (String)mMimeHeaders.get(AE_CONTENT_TYPE_MIME);
   }

   /**
    * @see org.activebpel.wsio.IAeWebServiceAttachment#getLocation()
    */
   public String getLocation()
   {
      return (String)mMimeHeaders.get(AE_CONTENT_LOCATION_MIME);
   }

   /**
    * @see org.activebpel.wsio.IAeWebServiceAttachment#getContentId()
    */
   public String getContentId()
   {
      return (String)mMimeHeaders.get(AE_CONTENT_ID_MIME);
   }
}
