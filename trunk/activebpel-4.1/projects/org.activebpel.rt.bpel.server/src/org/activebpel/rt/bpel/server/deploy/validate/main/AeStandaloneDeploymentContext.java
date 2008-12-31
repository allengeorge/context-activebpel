// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/main/AeStandaloneDeploymentContext.java,v 1.5 2006/07/18 20:05:3
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
package org.activebpel.rt.bpel.server.deploy.validate.main;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext;
import org.activebpel.rt.bpel.server.deploy.IAeDeploymentId;

/**
 * Simple impl of IAeDeploymentContext for running the stand alone
 * pre-deployment validators.  Most of these methods are no-ops as
 * the validators are primarily concerned with getResourceAsStream
 * (which is wrapped inside the AeBprFile).
 */
public class AeStandaloneDeploymentContext implements IAeDeploymentContext
{
   /** URL class loader */
   private URLClassLoader mContext;
   
   /**
    * Constructor.
    * @param aBprFile The file to be validated.
    * @throws MalformedURLException
    */
   public AeStandaloneDeploymentContext( File aBprFile ) 
   throws MalformedURLException
   {
      URL url = aBprFile.toURL();
      mContext = new URLClassLoader(new URL[] {url});
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getDeploymentId()
    */
   public IAeDeploymentId getDeploymentId()
   {
      return null;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getDeploymentLocation()
    */
   public URL getDeploymentLocation()
   {
      return mContext.getURLs()[0];
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getResourceAsStream(java.lang.String)
    */
   public InputStream getResourceAsStream(String aResourceName)
   {
      return mContext.getResourceAsStream(aResourceName);
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getResourceURL(java.lang.String)
    */
   public URL getResourceURL(String aResourceName)
   {
      return mContext.getResource(aResourceName);
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getShortName()
    */
   public String getShortName()
   {
      return null;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getTempDeploymentLocation()
    */
   public URL getTempDeploymentLocation()
   {
      return null;
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getResourceClassLoader()
    */
   public ClassLoader getResourceClassLoader()
   {
      return mContext;
   }
}
