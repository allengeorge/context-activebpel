//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/IAeFrom.java,v 1.3 2007/04/23 23:23:1
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
package org.activebpel.rt.bpel.impl.activity.assign; 

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.bpel.AeBusinessProcessException;


/**
 * Interface for an impl object that knows how to extract data from an AeFromDef 
 */
public interface IAeFrom
{
   /**
    * Extracts the data for the copy operation based on the info in the <from> definition
    * @throws AeBusinessProcessException
    */
   public Object getFromData() throws AeBusinessProcessException;
   
   /**
    * Setter for the copy operation that is the parent of this <from>
    * @param aCopyOperation
    */
   public void setCopyOperation(IAeCopyOperation aCopyOperation);
   
   /**
    * Returns the attachment container associated with the from data
    * @return
    */
   public IAeAttachmentContainer getAttachmentsSource();
}
 
