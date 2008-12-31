// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeAbstractDeploymentContext.java,v 1.5 2005/06/17 21:51:1
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
 * Base class for implementing a deployment context.  
 * The URL serves as the unique identifier for each deployment.
 */
abstract public class AeAbstractDeploymentContext implements IAeDeploymentContext
{
   /** deployment location */
   private URL mLocation;
   /** deployment id */
   private IAeDeploymentId mDeploymentId;   

   /**
    * Constructor.
    * @param aURL the deployment url
    */
   public AeAbstractDeploymentContext( URL aURL )
   {
      mLocation = aURL;
      mDeploymentId = new AeDeploymentId( aURL );
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getDeploymentId()
    */
   public IAeDeploymentId getDeploymentId()
   {
      return mDeploymentId;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return mDeploymentId.hashCode();
   }

   /**
    * Returns - deploymentId
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return mDeploymentId.getId();
   }

   /**
    * Determines equality based ONLY on the deploymentId
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObj)
   {
      if( aObj != null && aObj instanceof IAeDeploymentContext )
      {
         IAeDeploymentContext other = (IAeDeploymentContext)aObj;
         return getDeploymentId().equals( other.getDeploymentId() );
      }
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getDeploymentLocation()
    */
   public URL getDeploymentLocation()
   {
      return mLocation;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getShortName()
    */
   public String getShortName()
   {
      String urlString = getDeploymentLocation().toString();
      return urlString.substring( urlString.lastIndexOf('/')+1 );
   }

}
