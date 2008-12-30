// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/attachment/IAeAttachmentItem.java,v 1.3 2007/05/08 18:45:5
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

import java.util.Map;

/**
 * Interface describing the access to an internal bpel attachment item
 */
public interface IAeAttachmentItem
{
   /**
    * @return <code>Map</code> of attachment headers
    */
   public Map getHeaders();
   
   /**
    * Returns the value of the header corresponding to the given header name.
    *
    * @param aHeaderName
    * @return the value of the specified header
    */
   public String getHeader(String aHeaderName);
   
   /**
    * Returns the attachment's id.
    */
   public long getAttachmentId();

   /**
    * Returns the id of the attachment's associated process.
    */
   public long getProcessId();
}
