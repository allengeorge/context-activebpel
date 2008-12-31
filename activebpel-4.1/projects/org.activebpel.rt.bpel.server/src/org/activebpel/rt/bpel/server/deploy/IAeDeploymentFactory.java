// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/IAeDeploymentFactory.java,v 1.2 2006/07/18 20:05:3
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

import org.activebpel.rt.bpel.server.deploy.validate.IAeValidationHandler;

/**
 * Factory interface for obtaining individual ActiveBpel deployers and
 * validators.
 */
public interface IAeDeploymentFactory
{
   /**
    * Provides access to the predeployment and deployment
    * validators.
    */
   public IAeValidationHandler getValidationHandler();   
   
   /**
    * Accessor for the deployer responsible for deploying BPEL processes
    * to the engine.
    */
   public IAeBpelDeployer getBpelDeployer();
   
   /**
    * Accessor for the deployer responsible for deploying entries
    * from a BPR resource catalog.
    */
   public IAeCatalogDeployer getCatalogDeployer();
   
   /**
    * Accessor for the deployer responsible for deploying partner 
    * definitions.
    */
   public IAePdefDeployer getPDefDeployer();
   
   /**
    * Accessor for the deployer responsible for deploying web services
    * to the web services container.
    */
   public IAeWebServicesDeployer getWebServicesDeployer();

}
