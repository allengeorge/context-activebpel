// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/AeAttachmentDownloadServlet.java,v 1.2 2007/06/12 18:09:2
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
package org.activebpel.rt.bpeladmin.war;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.util.AeBlobInputStream;
import org.activebpel.rt.util.AeCloser;
import org.activebpel.rt.util.AeUtil;

/**
 * Responsible for downloading the contents of an attachment and streaming it to a client.
 */
public class AeAttachmentDownloadServlet extends HttpServlet
{

   /**
    * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse)
    */
   protected void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException,
         IOException
   {
      doPost(aRequest, aResponse);
   }

   /**
    * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse)
    */
   protected void doPost(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException,
         IOException
   {
      long attachmentItemId;
      String mimeType;
      String fileName;
      try
      {
         attachmentItemId = Long.parseLong(aRequest.getParameter("id")); //$NON-NLS-1$
         mimeType = aRequest.getParameter("type"); //$NON-NLS-1$
         fileName = aRequest.getParameter("file"); //$NON-NLS-1$
         
         // Multipart mime types do not download properly in Firefox, need to change mime/type to force proper download
         if (mimeType.startsWith("multipart/")) //$NON-NLS-1$
            mimeType = "application/octet-stream"; //$NON-NLS-1$
      }
      catch (Exception e)
      {
         aResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, AeMessages
               .getString("AeAttachmentDownloadServlet.1")); //$NON-NLS-1$
         return;
      }

      streamAttachment(aResponse, attachmentItemId, mimeType, fileName);
   }

   /**
    * Gets the attachment from the server and streams it to the response.
    * @param aResponse
    * @param aAttachmentItemId
    * @param mimeType
    * @throws IOException
    */
   private void streamAttachment(HttpServletResponse aResponse, long aAttachmentItemId, String mimeType,
         String fileName) throws IOException
   {
      try
      {
         InputStream content = AeEngineFactory.getEngine().getAttachmentManager().deserialize(
               aAttachmentItemId);
         BufferedInputStream in = new BufferedInputStream(content);

         int inLength = -1;
         if ( content instanceof AeBlobInputStream )
         {
            inLength = (int)((AeBlobInputStream)content).length();
         }
         BufferedOutputStream out = new BufferedOutputStream(setupStream(aAttachmentItemId, mimeType,
               inLength, fileName, aResponse));
         consume(in, out);
         out.flush();
         out.close();
      }
      catch (Exception ex)
      {
         aResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   /**
    * Consumes the binary inputstream, writing its contents to the binary outputstream.
    * @param aReader
    * @param aWriter
    * @throws IOException
    */
   private void consume(BufferedInputStream aReader, BufferedOutputStream aWriter) throws IOException
   {
      try
      {
         byte[] buff = new byte[1024 * 4];
         int bytesRead;
         // Simple read/write loop.
         while (-1 != (bytesRead = aReader.read(buff, 0, buff.length)))
         {
            aWriter.write(buff, 0, bytesRead);
         }
      }
      finally
      {
         AeCloser.close(aReader);
      }
   }

   /**
    * Sets up the headers needed for the binary outputstream to do the streaming and returns the binary
    * outputstream.
    * @param aAttachmentItemId
    * @param aMimeType
    * @param aResponse
    * @return
    * @throws IOException
    */
   private ServletOutputStream setupStream(long aAttachmentItemId, String aMimeType, int aLength,
         String aFileName, HttpServletResponse aResponse) throws IOException
   {
      aResponse.setContentType((aMimeType != null && aMimeType.length() > 0) ? aMimeType
            : "application/octet-stream"); //$NON-NLS-1$

      if ( aLength > 0 )
      {
         aResponse.setContentLength(aLength);
      }
      
      if ( AeUtil.isNullOrEmpty(aFileName) )
      {
         aFileName = "attachment_" + aAttachmentItemId + ".bin"; //$NON-NLS-1$ //$NON-NLS-2$
      }

      aResponse.setHeader("Content-disposition", "attachment;filename=" + aFileName); //$NON-NLS-1$ //$NON-NLS-2$
      return aResponse.getOutputStream();
   }
}
