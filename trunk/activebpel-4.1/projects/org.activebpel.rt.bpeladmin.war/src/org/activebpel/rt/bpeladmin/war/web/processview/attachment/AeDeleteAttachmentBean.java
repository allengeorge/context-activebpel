//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/processview/attachment/AeDeleteAttachmentBean.java,v 1.1 2007/08/13 19:36:3
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
package org.activebpel.rt.bpeladmin.war.web.processview.attachment;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeUtil;

/**
 * Bean responsible for deleteing a process variable attachment from the engine by request from the BpelAdmin
 * console.
 */
public class AeDeleteAttachmentBean extends AeAttachmentBeanBase
{
   /**
    * Delete the attachment item number from the process variable
    * @param aItemNumber
    */
   public void setDeleteItem(String aItemNumber)
   {
      try
      {
         int[] itemNumber = new int[1];
         // Adjust to internal offset
         itemNumber[0] = AeUtil.getNumeric(aItemNumber) - 1;

         getAdmin().removeVariableAttachments(getPidAsLong(), getPath(), itemNumber);
      }
      catch (AeException ex)
      {
         setError(ex);
      }
   }
}
