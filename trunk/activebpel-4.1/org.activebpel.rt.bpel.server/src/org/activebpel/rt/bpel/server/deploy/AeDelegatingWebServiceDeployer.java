//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeDelegatingWebServiceDeployer.java,v 1.3 2007/07/27 18:19:0
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

import java.util.List;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeWSDLPolicyHelper;
import org.activebpel.rt.bpel.IAeContextWSDLProvider;
import org.activebpel.rt.bpel.server.engine.AeDelegatingHandlerFactory;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.util.AeUtil;

/**
 * WebServiceDeployer that delegates to the target deployer based on the deployment type 
 * specified on the container or overridden by endpoint policy
 */
public class AeDelegatingWebServiceDeployer extends AeDelegatingHandlerFactory implements IAeWebServicesDeployer
{
   /**
    * Constructor that takes a configuration map
    * 
    * @param aConfig
    * @throws AeException
    */
   public AeDelegatingWebServiceDeployer(Map aConfig) throws AeException
   {
      super(aConfig);
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeWebServicesDeployer#deployToWebServiceContainer(org.activebpel.rt.bpel.server.deploy.IAeDeploymentContainer, java.lang.ClassLoader)
    */
   public void deployToWebServiceContainer(IAeDeploymentContainer aContainer, ClassLoader aLoader) throws AeException
   {
      if (aContainer.isWsddDeployment())
      {
         // non-bpel axis service
         IAeWebServicesDeployer deployer = (IAeWebServicesDeployer) getDelegate(aContainer.getDeploymentType());
         deployer.deployToWebServiceContainer(aContainer, aLoader);
      }
      else
      {
         IAeServiceDeploymentInfo[] services = aContainer.getServiceDeploymentInfo();
         if (services != null)
         {
            for (int i = 0; i < services.length; i++)
            {
               resolveServicePolicies(services[i]);
               IAeWebServicesDeployer deployer = getServiceDeployer(services[i], aContainer.getDeploymentType());
               deployer.deployToWebServiceContainer(services[i], aLoader);
            }
         }
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeWebServicesDeployer#undeployFromWebServiceContainer(org.activebpel.rt.bpel.server.deploy.IAeDeploymentContainer)
    */
   public void undeployFromWebServiceContainer(IAeDeploymentContainer aContainer) throws AeException
   {
      if (aContainer.isWsddDeployment())
      {
         // non-bpel axis service
         IAeWebServicesDeployer deployer = (IAeWebServicesDeployer) getDelegate(aContainer.getDeploymentType());
         deployer.undeployFromWebServiceContainer(aContainer);
      }
      else
      {
         IAeServiceDeploymentInfo[] services = aContainer.getServiceDeploymentInfo();
         if (services != null)
         {
            for (int i = 0; i < services.length; i++)
            {
               IAeWebServicesDeployer deployer = getServiceDeployer(services[i], aContainer.getDeploymentType());
               deployer.undeployFromWebServiceContainer(services[i]);
            }
         }
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeWebServicesDeployer#deployToWebServiceContainer(org.activebpel.rt.bpel.server.deploy.AeServiceDeploymentInfo, java.lang.ClassLoader)
    */
   public void deployToWebServiceContainer(IAeServiceDeploymentInfo aService, ClassLoader aLoader) throws AeException
   {
      IAeWebServicesDeployer deployer = getServiceDeployer(aService, null);
      deployer.deployToWebServiceContainer(aService, aLoader);
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeWebServicesDeployer#undeployFromWebServiceContainer(org.activebpel.rt.bpel.server.deploy.AeServiceDeploymentInfo)
    */
   public void undeployFromWebServiceContainer(IAeServiceDeploymentInfo aService) throws AeException
   {
      IAeWebServicesDeployer deployer = getServiceDeployer(aService, null);
      deployer.undeployFromWebServiceContainer(aService);
   }
   
   /**
    * Returns the handler specified by the deployer type, unless overridden by policy on the service
    * 
    * @param aService
    * @param aDefaultDeployer
    * @return deployer for service
    * @throws AeException
    */
   protected IAeWebServicesDeployer getServiceDeployer(IAeServiceDeploymentInfo aService, String aDeployerType) throws AeException
   {
      String policytype = AeEngineFactory.getPolicyMapper().getDeploymentHandler(aService.getPolicies());
      if (!AeUtil.isNullOrEmpty(policytype))
      {
         return (IAeWebServicesDeployer) getDelegate(policytype);
      }
      else
      {
         return (IAeWebServicesDeployer) getDelegate(aDeployerType);
      }
   }
   
   /**
    * Resolves policy references for service deployment
    * 
    * @param aContainer
    * @param aService
    * @throws AeException
    */
   protected void resolveServicePolicies(IAeServiceDeploymentInfo aService) throws AeException
   {
      if (!AeUtil.isNullOrEmpty(aService.getPolicies()))
      {
         IAeContextWSDLProvider wsdlProvider = AeEngineFactory.getDeploymentProvider().findCurrentDeployment(aService.getProcessQName());
         List policies = AeWSDLPolicyHelper.resolvePolicyReferences(wsdlProvider, aService.getPolicies());
         if (!AeUtil.isNullOrEmpty(policies))
         {
            aService.getPolicies().clear();
            aService.getPolicies().addAll(policies);
         }
      }
   }

}
