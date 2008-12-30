// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/invokers/AeAttachmentDataSource.java,v 1.2 2007/05/24 00:39:5
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
package org.activebpel.rt.axis.bpel.invokers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import org.activebpel.wsio.IAeWebServiceAttachment;

/**
 * Provides a datasource for WSIO attachments 
 * @see javax.activation.DataHandler
 */
public class AeAttachmentDataSource implements DataSource
{
   /** WSIO attachments being sourced */
   private IAeWebServiceAttachment mAeWebServiceAttachmentData;
  
   /**
    * Constructor 
    * @param aAeWebServiceAttachmentData WSIO attachments
    */
   public AeAttachmentDataSource(IAeWebServiceAttachment aAeWebServiceAttachmentData)
   {
      mAeWebServiceAttachmentData = aAeWebServiceAttachmentData;
   }

   /**
    * @see javax.activation.DataSource#getContentType()
    */
   public String getContentType()
   {
      return mAeWebServiceAttachmentData.getMimeType();
   }

   /**
    * @see javax.activation.DataSource#getInputStream()
    */
   public InputStream getInputStream() throws IOException
   {
      return mAeWebServiceAttachmentData.getContent();
   }

   /**
    * @see javax.activation.DataSource#getName()
    */
   public String getName()
   {
      return mAeWebServiceAttachmentData.getContentId();
   }

   /**
    * Overrides method to return <code>null</code>.
    * 
    * @see javax.activation.DataSource#getOutputStream()
    */
   public OutputStream getOutputStream() throws IOException
   {
      return null;
   }
}
