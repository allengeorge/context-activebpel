//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/processview/AeAttachmentViewBean.java,v 1.5 2007/08/13 19:36:3
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
package org.activebpel.rt.bpeladmin.war.web.processview;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.util.AeMimeUtil;
import org.activebpel.rt.util.AeUTF8Util;

/**
 * A simple wrapper around attachment viewable items make them accessable by JSP pages
 */
public class AeAttachmentViewBean
{
   /** Mime headers associated with the attachment */
   private Map mHeaders;

   /** attachment content id */
   private long mAttachmentId;
   /** The index of the attachment */
   private int mIndex;

   /**
    * Constructor
    * @param aAttachmentId
    */
   public AeAttachmentViewBean(long aAttachmentId, int aIndex)
   {
      mAttachmentId = aAttachmentId;
      mIndex = aIndex;
   }

   /**
    * Returns the mimetype which is encoded
    */
   public String getMimeTypeEncoded()
   {
      String mimeType = getMimeType();
      try
      {
         return AeUTF8Util.urlEncode(mimeType == null ? "unknown" : mimeType); //$NON-NLS-1$
      }
      catch (UnsupportedEncodingException ex)
      {
         return mimeType;
      }
   }

   /**
    * Returns the mimeType of the attachment
    */
   public String getMimeType()
   {
      return AeMimeUtil.getMimeType(mHeaders);
   }
   
   /**
    * Sets header properties Helper method
    * @param aName
    * @param aValue
    */
   public void setHeader(String aName, String aValue)
   {
      if (mHeaders == null)
         mHeaders = new LinkedHashMap();
      
      mHeaders.put(aName, aValue);
   }

   /**
    * AttachmentId used to get the attachment content for download
    * @return
    */
   public long getAttachmentId()
   {
      return mAttachmentId;
   }

   /**
    * Returns index of attachment
    */
   public long getIndex()
   {
      return mIndex;
   }

   /**
    * Return a reasonable file name for downloading the attachment
    * @return
    */
   public String getFileName()
   {
      return AeMimeUtil.getFileName(mHeaders, "attachment_" + getAttachmentId()); //$NON-NLS-1$
   }

   /**
    * @return true if this bean has top level properties to be displayed.
    */
   public boolean isHasHeaders()
   {
      return (! mHeaders.isEmpty() );
   }

   /**
    * Returns the list of headers in the form of AeAttachmentHeader objects
    */
   public List getHeaders()
   {
      List headers = new ArrayList();
      for (Iterator iter = mHeaders.entrySet().iterator(); iter.hasNext();)
      {
         Map.Entry entry = (Map.Entry)iter.next();
         headers.add(new AeAttachmentHeader((String)entry.getKey(), (String)entry.getValue()));
      }
      
      return headers;
   }

   /**
    * @return  Returns the number of properties. Thus method is used to support Indexed JSP tags.
    */
   public int getHeadersSize()
   {
      return mHeaders.size();
   }

   /**
    * Attachment header data used by bean.
    */
   protected static class AeAttachmentHeader
   {
      private String mName;
      private String mValue;

      /**
       * Constructor
       * @param aName
       * @param aValue
       */
      public AeAttachmentHeader(String aName, String aValue)
      {
         mName  = aName;
         mValue = aValue;
      }

      /**
       * @return the name
       */
      public String getName()
      {
         return mName;
      }

      /**
       * @return the value
       */
      public String getValue()
      {
         return mValue;
      }
   }
}
