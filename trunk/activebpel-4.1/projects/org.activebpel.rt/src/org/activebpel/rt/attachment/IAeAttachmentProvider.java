//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/attachment/IAeAttachmentProvider.java,v 1.1 2007/05/20 23:56:5
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
package org.activebpel.rt.attachment;

public interface IAeAttachmentProvider
{
   /**
    * Allows for an attachment to be downloaded given the attachment id, mime type and target file.
    * @param aAttachmentId The id of the attachment on the ActiveBPEL server
    * @param aMimeType The mime type of the attachment
    * @param aFilename The target filename
    */
   public void downloadAttachment(long aAttachmentId, String aMimeType, String aFilename);
}
