// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/IAeDeploymentContext.java,v 1.11 2006/07/18 20:05:3
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

import java.io.InputStream;
import java.net.URL;

/**
 * Wraps information about a deployment and provides access to its
 * resources.
 */
public interface IAeDeploymentContext
{
   /**
    * Accessor for deployment id
    */
   public IAeDeploymentId getDeploymentId();
   
   /**
    * Point to the deployment location.
    */
   public URL getDeploymentLocation();
   
   /**
    * If the deployment is copied to a staging/temp
    * dir, this will return a url pointing to the
    * location, otherwise null.
    */
   public URL getTempDeploymentLocation();
   
   /**
    * Short name of the deployment - the url resource without protocol and path info.
    */
   public String getShortName();
   
   /**
    * Access the named resource.
    * @param aResourceName
    */
   public InputStream getResourceAsStream( String aResourceName );
   
   /**
    * Get the url for the context resource.
    * @param aResourceName
    * @return The url associated with the passed resource.
    */
   public URL getResourceURL( String aResourceName );
   
   /**
    * Return the deployment classloader.  This is the classloader that has
    * access to the resources necessary for bpr deployments.
    */
   public ClassLoader getResourceClassLoader();
}
