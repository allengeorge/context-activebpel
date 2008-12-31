//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/rdebug/server/AeAttachmentAttribute.java,v 1.1 2007/07/26 21:02:3
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
 * JavaBean for holding some data for a single attachment attribute definition.
 */
public class AeAttachmentAttribute implements Serializable
{

   /** name of the attachment attribute */
   private String mAttributeName;

   /** value of the attachment attribute */
   private String mAttributeValue;

   /**
    * 
    * Constructor
    */
   public AeAttachmentAttribute() {
   }

   /**
    * Gets the attributeName value for this AeAttachmentAttribute.
    * 
    * @return attributeName
    */
   public String getAttributeName() {
       return mAttributeName;
   }


   /**
    * Sets the attributeName value for this AeAttachmentAttribute.
    * 
    * @param attributeName
    */
   public void setAttributeName(String aAttributeName) {
       mAttributeName = aAttributeName;
   }


   /**
    * Gets the attributeValue value for this AeAttachmentAttribute.
    * 
    * @return attributeValue
    */
   public String getAttributeValue() {
       return mAttributeValue;
   }


   /**
    * Sets the attributeValue value for this AeAttachmentAttribute.
    * 
    * @param aAttributeValue
    */
   public void setAttributeValue(String aAttributeValue) {
      mAttributeValue = aAttributeValue;
   }

}
