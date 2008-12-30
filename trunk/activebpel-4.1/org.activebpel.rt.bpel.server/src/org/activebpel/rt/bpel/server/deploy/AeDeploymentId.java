// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeDeploymentId.java,v 1.3 2004/10/05 23:00:3
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
package org.activebpel.rt.bpel.server.deploy;

import java.net.URL;

/**
 *  Uniquely identifies a deployment.  This impl uses the url 
 *  of the BPR archive as the id.
 */
public class AeDeploymentId implements IAeDeploymentId
{
   /** url string */
   private String mId;
   
   /**
    * Constructor.
    * @param aURL deployment url
    */
   public AeDeploymentId( URL aURL )
   {
      mId = aURL.toExternalForm();   
   }
   
   /**
    * Constructor.
    * @param aId
    */
   public AeDeploymentId( String aId )
   {
      mId = aId;
   }
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentId#getId()
    */
   public String getId()
   {
      return mId;
   }
   
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObj)
   {
      if( aObj != null && aObj instanceof IAeDeploymentId )
      {
         IAeDeploymentId other = (IAeDeploymentId)aObj;
         return getId().equals( other.getId() );
      }
      return false;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return getId().hashCode();
   }

}
