//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/rdebug/server/AeAddAttachmentResponse.java,v 1.1 2007/07/26 21:02:3
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
package org.activebpel.rt.bpel.server.admin.rdebug.server;


/**
 * Java bean to hold the response of an add attachment request 
 */
public class AeAddAttachmentResponse
{
   private long mAttachmentId;

   private AeAttachmentAttributeList mAttachmentAttributes;
   
   
   /**
    * No-arg constructor
    */
   public AeAddAttachmentResponse()
   {     
   }
   
   /**
    * Gets the attachmentId value for this AeAddAttachmentResponse.
    * 
    * @return mAttachmentId
    */
   public long getAttachmentId() {
       return mAttachmentId;
   }

   /**
    * Sets the attachmentId value for this AeAddAttachmentResponse.
    * 
    * @param attachmentId
    */
   public void setAttachmentId(long aAttachmentId) {
      mAttachmentId = aAttachmentId;
   }

   /**
    * Gets the attachmentAttributes value for this AeAddAttachmentResponse.
    * 
    * @return attachmentAttributes 
    */
   public AeAttachmentAttributeList getAttachmentAttributes() {
       return mAttachmentAttributes;
   }

   /**
    * Sets the attachmentAttributes value for this AeAddAttachmentResponse.
    * 
    * @param attachmentAttributes 
    */
   public void setAttachmentAttributes(AeAttachmentAttributeList aAttachmentAttributes) {
       mAttachmentAttributes = aAttachmentAttributes;
   }


}
