// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/bpr/AeBprContext.java,v 1.14 2006/07/18 20:05:3
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
package org.activebpel.rt.bpel.server.deploy.bpr;


import java.io.InputStream;
import java.net.URL;

import org.activebpel.rt.bpel.server.deploy.AeAbstractDeploymentContext;

/**
 *  Provides access to deployment context resources.
 */
public class AeBprContext extends AeAbstractDeploymentContext
{
   /** deployment context (url) classloader */
   private ClassLoader mContextLoader;
   /** temp/working url */
   private URL mTempLocation;
   
   /**
    * Constructor.
    * @param aURL the deployment url - points to the bpr archive
    * @param aLoader the context class loader to extract resources from the bpr archive
    */
   public AeBprContext( URL aURL, URL aTempLocation, ClassLoader aLoader )
   {
      super( aURL );
      mTempLocation = aTempLocation;
      mContextLoader = aLoader;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getResourceClassLoader()
    */
   public ClassLoader getResourceClassLoader()
   {
      return mContextLoader;
   }

   /**
    * Gets input stream for given resource  
    * @param aResource resource we want stream for
    */
   public InputStream getResourceAsStream(String aResource)
   {
      return getResourceClassLoader().getResourceAsStream( aResource );
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getResourceURL(java.lang.String)
    */
   public URL getResourceURL( String aContextResource )
   {
      return getResourceClassLoader().getResource( aContextResource );
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getTempDeploymentLocation()
    */
   public URL getTempDeploymentLocation()
   {
      return mTempLocation;
   }
}
