// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/attachment/IAeAttachmentContainer.java,v 1.3 2007/05/09 20:28:2
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

import java.util.Iterator;
import java.util.List;

/**
 * Base interface required to be implemented by all internal attachment containers and wrappers. Basically
 * forces containers to extend List
 */
public interface IAeAttachmentContainer extends List
{
   /** Return iterator to attachment items */
   public Iterator getAttachmentItems();
    
   /**
    * @return true when there are attachment items, otherwise false
    */
   public boolean hasAttachments();
   
   /**
    * Copy attachments from another source 
    * @param aAttachmentSource
    */
   public void copy(IAeAttachmentContainer aAttachmentSource);
}
