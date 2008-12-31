// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeBpelDeployer.java,v 1.7 2005/06/08 13:30:3
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter;
import org.activebpel.rt.bpel.server.IAeProcessDeployment;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * IAeBpelDeployer impl.
 */
public class AeBpelDeployer implements IAeBpelDeployer
{
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeBpelDeployer#deployBpel(org.activebpel.rt.bpel.server.deploy.IAeDeploymentSource, org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter)
    */
   public void deployBpel(IAeDeploymentSource aSource, IAeBaseErrorReporter aReporter ) throws AeException
   {
      IAeProcessDeployment deployment = create(aSource);
      AeDeploymentHandlerFactory handlerFactory = (AeDeploymentHandlerFactory)AeEngineFactory.getDeploymentHandlerFactory();
      handlerFactory.getDeploymentFactory().getValidationHandler().doDeploymentValidation(
            aSource.getPddLocation(), deployment, aReporter);
      if( !aReporter.hasErrors() )
      {
         AeEngineFactory.getDeploymentProvider().addDeploymentPlan( deployment );
      }
   }
   
   /**
    * Create the process deployment.
    * @param aSource
    * @throws AeDeploymentException
    */
   public IAeProcessDeployment create( IAeDeploymentSource aSource )
   throws AeDeploymentException
   {
      return AeProcessDeploymentFactory.getInstance().newInstance(aSource );
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeBpelDeployer#undeployBpel(org.activebpel.rt.bpel.server.deploy.IAeDeploymentSource)
    */
   public void undeployBpel(IAeDeploymentSource aDeployment) throws AeException
   {
      AeEngineFactory.getDeploymentProvider().removeDeploymentPlan( aDeployment.getProcessName() );
   }
}
