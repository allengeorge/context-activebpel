// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeDeploymentHandlerFactory.java,v 1.3 2007/04/24 00:45:5
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

import java.lang.reflect.Constructor;
import java.util.Map;

import org.activebpel.rt.bpel.server.logging.IAeLogWrapper;

/**
 * Factory for creating new deployment handlers.
 * In this impl, the handlers ARE NOT thread safe and <code>newInstance</code>
 * should be called for each deployment.  
 */
public class AeDeploymentHandlerFactory implements IAeDeploymentHandlerFactory
{
   /**
    * Deployment factory used by the handler impl.
    */
   protected IAeDeploymentFactory mDeploymentFactory;
   
   /**
    * Constructor.
    * @param aParams Any aeEngineConfig params.
    */
   public AeDeploymentHandlerFactory( Map aParams ) throws Exception
   {
      Map deployerParams = (Map)aParams.get("DeploymentFactory"); //$NON-NLS-1$
      String deploymentFactoryImpl = (String)deployerParams.get("Class"); //$NON-NLS-1$
      Class deployerFactoryImplClass = Class.forName(deploymentFactoryImpl);
      Constructor xTor = deployerFactoryImplClass.getConstructor( new Class[]{Map.class} );
      mDeploymentFactory = (IAeDeploymentFactory)xTor.newInstance( new Object[]{deployerParams} );
   }
   
   /**
    * Accessor for the deployment factory impl.
    */
   public IAeDeploymentFactory getDeploymentFactory()
   {
      return mDeploymentFactory;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentHandlerFactory#getWebServicesDeployer()
    */
   public IAeWebServicesDeployer getWebServicesDeployer()
   {
      return getDeploymentFactory().getWebServicesDeployer();  
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentHandlerFactory#newInstance(org.activebpel.rt.bpel.server.logging.IAeLogWrapper)
    */
   public IAeDeploymentHandler newInstance(IAeLogWrapper aLogWrapper)
   {
      return new AeDeploymentHandler( aLogWrapper, getDeploymentFactory() );
   }
}
