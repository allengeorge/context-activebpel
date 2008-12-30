// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/deploy/AeResourceProvider.java,v 1.1 2006/03/09 14:28:2
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
package org.activebpel.rt.axis.bpel.deploy;

import java.io.InputStream;
import java.net.URL;

import org.apache.axis.AxisEngine;
import org.apache.axis.ConfigurationException;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.deployment.wsdd.WSDDGlobalConfiguration;
import org.apache.axis.utils.XMLUtils;

/**
 * This class extends the axis file provider so we can create our own deployment
 * context, which can then be classloader aware.
 */
public class AeResourceProvider extends FileProvider
{
   /** The configuration we load. */
   protected URL mConfigResource;

   /** Input stream cache */
   protected InputStream mInputStreamCache;

   /**
    * Constructs a resource provider from the passed config url.
    * @param aConfigResource The config file resource to load
    */
   public AeResourceProvider(URL aConfigResource)
   {
      super((InputStream) null);
      mConfigResource = aConfigResource;
   }

   /**
    * Override input stream setter to sync protected cache.
    * @see org.apache.axis.configuration.FileProvider#setInputStream(java.io.InputStream)
    */
   public void setInputStream(InputStream aStream)
   {
      super.setInputStream(aStream);
      mInputStreamCache = aStream;
   }

   /**
    * Configures the given AxisEngine with the given descriptor 
    * @see org.apache.axis.EngineConfiguration#configureEngine(org.apache.axis.AxisEngine)
    */
   public void configureEngine(AxisEngine aEngine) throws ConfigurationException
   {
      buildDeployment();
      getDeployment().configureEngine(aEngine);
      aEngine.refreshGlobalOptions();
   }

   /**
    * @return New deployment, which is classloader context aware.
    * @throws ConfigurationException
    */
   public synchronized AeBprDeployment buildDeployment() throws ConfigurationException
   {
      if (getDeployment() == null)
      {
         try
         {
            if (mInputStreamCache == null)
            {
               setInputStream(mConfigResource.openStream());
            }

            setDeployment(new AeBprDeployment(XMLUtils.newDocument(mInputStreamCache).getDocumentElement()));

            setInputStream(null);

            if (getDeployment().getGlobalConfiguration() == null)
            {
               WSDDGlobalConfiguration config = new WSDDGlobalConfiguration();
               config.setOptionsHashtable(new java.util.Hashtable());
               getDeployment().setGlobalConfiguration(config);
            }

         }
         catch (Exception e)
         {
            throw new ConfigurationException(e);
         }
      }
      return getMyDeployment();
   }

   /**
    * Override, since we will rebuild deployment on startup.
    * @todo should we write out cache?
    * @see org.apache.axis.EngineConfiguration#writeEngineConfig(org.apache.axis.AxisEngine)
    */
   public void writeEngineConfig(AxisEngine engine)
   {
      //
   }

   /** 
    * Helper method for casting our deployment to the type we create.
    */
   public AeBprDeployment getMyDeployment()
   {
      return (AeBprDeployment) getDeployment();
   }

}
