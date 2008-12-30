//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/handlers/AeAttachmentUtil.java,v 1.1 2007/04/23 21:31:3
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
package org.activebpel.rt.axis.bpel.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MimeHeader;

import org.activebpel.wsio.AeWebServiceAttachment;
import org.activebpel.wsio.IAeWebServiceAttachment;
import org.apache.axis.Message;

/**
 * Attachment utilities for common attachment related functions
 */
public class AeAttachmentUtil
{

   /**
    * Extracts inbound SOAP Attachments and converts them to WSIO attachments
    * @param aMessage axis soap message
    * @return List of attachments
    * @see IAeWebServiceAttachment
    */
   public static List soap2wsioAttachments(Message aMessage) throws Exception
   {
      Iterator aAttachmentItr = aMessage.getAttachments();
      List attachments = null;

      // A soap message can have 0..n attachment parts
      while (aAttachmentItr.hasNext())
      {
         // Convert the Mime headers of the attachment part to a Map, add the map to the attachment
         AttachmentPart attachPart = (AttachmentPart)aAttachmentItr.next();
         Map mimeHeaderPairs = new HashMap();
         for (Iterator mimeItr = attachPart.getAllMimeHeaders(); mimeItr.hasNext();)
         {
            MimeHeader pair = (MimeHeader)mimeItr.next();
            mimeHeaderPairs.put(pair.getName(), pair.getValue());
         }

         // create an attachment with headers and content
         AeWebServiceAttachment attachment = new AeWebServiceAttachment(attachPart.getDataHandler()
               .getInputStream(), mimeHeaderPairs);

         // Add the attachment to the attachment list of the message
         if ( attachments == null )
         {
            attachments = new LinkedList();
         }
         attachments.add(attachment);
      }
      return attachments;
   }
}
