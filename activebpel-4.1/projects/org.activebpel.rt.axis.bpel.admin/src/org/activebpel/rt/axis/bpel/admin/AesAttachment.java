//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel.admin/src/org/activebpel/rt/axis/bpel/admin/AesAttachment.java,v 1.1 2007/07/26 20:40:0
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
package org.activebpel.rt.axis.bpel.admin;

import java.io.File;
import java.util.Map;

/**
 * Wrapper for an attachment added to a process variable on a remote engine
 */
public class AesAttachment
{

   /** Attachment header attributes */
   private Map mAttributes;

   /** File with attachment content */
   private File mSource;

   /**
    * Private inhibitor Constructor
    */
   private AesAttachment()
   {
   }

   /**
    * Constructor
    * @param aAttachmentProperties
    * @param aAttachmentSource
    */
   public AesAttachment(Map aAttachmentAttributes, File aAttachmentSource)
   {
      mAttributes = aAttachmentAttributes;
      mSource = aAttachmentSource;
   }

   /** returns the file with the attachment contents */
   public File getSource()
   {
      return mSource;
   }

   /** return attachment attrubute Map */
   public Map getAttributes()
   {
      return mAttributes;
   }
}
