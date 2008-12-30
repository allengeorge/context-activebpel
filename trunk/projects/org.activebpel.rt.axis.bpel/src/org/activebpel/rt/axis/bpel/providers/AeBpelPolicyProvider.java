// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/providers/AeBpelPolicyProvider.java,v 1.2 2006/09/26 15:15:0
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
package org.activebpel.rt.axis.bpel.providers;

import org.activebpel.rt.IAePolicyConstants;
import org.activebpel.rt.axis.bpel.AeMessages;
import org.activebpel.rt.axis.bpel.handlers.AeBpelDocumentHandler;
import org.activebpel.rt.axis.bpel.handlers.AeBpelHandler;
import org.activebpel.rt.axis.bpel.handlers.IAePolicyHandler;
import org.activebpel.rt.bpel.server.deploy.AeDeploymentException;
import org.activebpel.rt.bpel.server.deploy.IAeWsddConstants;
import org.activebpel.rt.util.AeUtil;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.Handler;
import org.apache.axis.deployment.wsdd.WSDDProvider;
import org.apache.axis.deployment.wsdd.WSDDService;

/**
 * Defines a provider for the BPEL Policy invocations.
 */
public class AeBpelPolicyProvider extends WSDDProvider
{
   /**
    * @see org.apache.axis.deployment.wsdd.WSDDProvider#newProviderInstance(org.apache.axis.deployment.wsdd.WSDDService, org.apache.axis.EngineConfiguration)
    */
   public Handler newProviderInstance(WSDDService service, EngineConfiguration registry) throws Exception
   {
      String handlerClass = service.getParameter(IAePolicyConstants.PARAM_HANDLER_CLASS);
      if (AeUtil.isNullOrEmpty(handlerClass))
      {
         AeDeploymentException.logError(null, AeMessages.format("AeBpelPolicyProvider.0", service.getServiceDesc().getName())); //$NON-NLS-1$
         return new AeBpelDocumentHandler();
      }
      IAePolicyHandler handler = (IAePolicyHandler) Class.forName(handlerClass).newInstance();
      handler.init(service, registry); 
      return (AeBpelHandler) handler;
   }

   /**
    * @see org.apache.axis.deployment.wsdd.WSDDProvider#getName()
    */
   public String getName()
   {
      return IAeWsddConstants.NAME_POLICY_BINDING;
   }
}
