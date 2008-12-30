// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/providers/AeBpelMSGProvider.java,v 1.4 2005/10/05 18:51:3
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

import org.activebpel.rt.axis.bpel.handlers.AeBpelDocumentHandler;
import org.activebpel.rt.bpel.server.deploy.IAeWsddConstants;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.Handler;
import org.apache.axis.deployment.wsdd.WSDDProvider;
import org.apache.axis.deployment.wsdd.WSDDService;

/**
 * Defines a provider for the BPEL MSG invocations.
 */
public class AeBpelMSGProvider extends WSDDProvider
{
   /**
    * @see org.apache.axis.deployment.wsdd.WSDDProvider#newProviderInstance(org.apache.axis.deployment.wsdd.WSDDService, org.apache.axis.EngineConfiguration)
    */
   public Handler newProviderInstance(WSDDService service, EngineConfiguration registry) throws Exception
   {
      return new AeBpelDocumentHandler();
   }

   /**
    * @see org.apache.axis.deployment.wsdd.WSDDProvider#getName()
    */
   public String getName()
   {
      return IAeWsddConstants.NAME_MSG_BINDING;
   }
}
