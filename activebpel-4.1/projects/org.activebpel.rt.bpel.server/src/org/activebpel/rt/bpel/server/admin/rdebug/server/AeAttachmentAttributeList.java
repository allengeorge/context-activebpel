//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/rdebug/server/AeAttachmentAttributeList.java,v 1.1 2007/07/26 21:02:3
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

import java.io.Serializable;

/**
 * Wrapper for the list of attachment attributes sent by the designer during remote debugging.
 */
public class AeAttachmentAttributeList implements Serializable
{
   
   /** The attributes associated with the attachment. */
   private AeAttachmentAttribute[] mAttachmentAttribute;
   
   /**
    * No-arg constructor
    */
   public AeAttachmentAttributeList()
   {     
   }
   
   /**
    * Gets the attributeName value for this AeAttachmentAttributeList.
    * 
    * @return attributeName
    */
   public AeAttachmentAttribute[] getAttributeName() {
       return mAttachmentAttribute;
   }

   /**
    * Sets the attributeName value for this AesAttachmentAttributeList.
    * 
    * @param attributeName
    */
   public void setAttributeName(AeAttachmentAttribute[] attributeName) {
      mAttachmentAttribute = attributeName;
   }

   /**
    * Returns the indexed attributeName value of this AesAttachmentAttributeList.
    * @param i
    * @return
    */
   public AeAttachmentAttribute getAttributeName(int i) {
       return mAttachmentAttribute[i];
   }

   /**
    * Sets the indexed attributeName value for this AesAttachmentAttributeList.
    * @param i
    * @param aAttachmentAttribute
    */
   public void setAttributeName(int i, AeAttachmentAttribute aAttachmentAttribute) {
      mAttachmentAttribute[i] = aAttachmentAttribute;
   }
}
