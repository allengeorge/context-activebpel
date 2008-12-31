//$Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/IAeWebServiceAttachment.java,v 1.1 2007/04/24 00:08:1
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
import java.util.Map;

/**
 * WSIO Attachment item itnterface
 */
public interface IAeWebServiceAttachment 
{
   
   /** key content type Mime header */
   public static final String AE_CONTENT_TYPE_MIME = "Content-Type"; //$NON-NLS-1$

   /** content location type Mime header */
   public static final String AE_CONTENT_LOCATION_MIME = "Content-Location"; //$NON-NLS-1$
   
   /** content id Mime header */
   public static final String AE_CONTENT_ID_MIME = "Content-Id"; //$NON-NLS-1$
   
   /**
    * Returns the type of attachment this data is representing. 
    */
   public String getMimeType();
   
   /**
    *returns the mime header that reperesents the content location identification
    */
   public String getLocation();
   
   /**
    *returns the mime header that reperesents the content identifier
    */
   public String getContentId();
   
   /**
    * returns all mime headers associated with attachment
    */
   public Map getMimeHeaders();
   
   /**
    * Get the attachment data.
    */
   public InputStream getContent();
}
