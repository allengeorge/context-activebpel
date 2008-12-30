// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeInboundReceiveDetailBean.java,v 1.1 2004/08/25 20:53:0
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
package org.activebpel.rt.bpeladmin.war.web;

import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.server.admin.AeQueuedReceiveDetail;

/**
 * This bean controls the display of queued receives - either unmatched or
 * message receivers depending on how the bean is configured.
 */
public class AeInboundReceiveDetailBean extends AeAbstractAdminBean
{
   /** The queue details. */
   protected List mDetails;
   /** The key identifying the receives to examine. */
   protected String mKey;
   
   /**
    * Default constructor.
    */
   public AeInboundReceiveDetailBean()
   {
   }
   
   /**
    * Setter for the unique key value.
    * @param aKey Uniquely identifies a queued item (via partner link, port type and operation).
    */
   public void setKey( String aKey )
   {
      mKey = aKey;
      AeQueuedReceiveDetail[] details = null;
      details = getAdmin().getUnmatchedQueuedReceives();
            
      if( details != null )
      {
        Map parsedDetails = AeInboundReceivesBean.parse( details );
        mDetails = (List)parsedDetails.get( mKey );
      }
   }
   
   /**
    * Returns true if there are no queue details.
    */
   public boolean isEmpty()
   {
      return mDetails == null || mDetails.size() == 0;
   }
   
   /**
    * Getter for the partner link name.
    */
   public String getPartnerLinkName()
   {
      return getDetail(0).getPartnerLinkName();
   }
   
   /**
    * Getter for the port type qname as a string.
    */
   public String getPortTypeAsString()
   {
      return getDetail(0).getPortTypeAsString();
   }
   
   /**
    * Getter for the operation.
    */
   public String getOperation()
   {
      return getDetail(0).getOperation();
   }
   
   /**
    * Indexed accessor for the detail row.
    * @param aIndex
    */
   public AeQueuedReceiveDetail getDetail( int aIndex )
   {
      return (AeQueuedReceiveDetail)mDetails.get( aIndex );
   }
   
   /**
    * Returns the size of the detail rows.
    */
   public int getDetailSize()
   {
      if( mDetails == null )
      {
         return 0;
      }
      return mDetails.size();
   }
}
