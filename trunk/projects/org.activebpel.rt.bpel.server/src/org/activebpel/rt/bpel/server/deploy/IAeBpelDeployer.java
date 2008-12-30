// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/IAeBpelDeployer.java,v 1.1 2004/09/17 20:59:5
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

/**
 * Interface for deployers that deploy/undeploy BPEL to the ActiveBPEL
 * engine.
 */
public interface IAeBpelDeployer
{
   
   /**
    * Deploy all BPEL processes to the ActiveBPEL engine.
    * @param aDeployment 
    * @param aReporter Reports any validation issues.
    * @throws AeException
    */
   public void deployBpel( IAeDeploymentSource aDeployment, IAeBaseErrorReporter aReporter )
   throws AeException;
   
   /**
    * Remove all BPEL processes in the container from the ActiveBPEL engine.
    * @param aDeployment
    * @throws AeException
    */
   public void undeployBpel( IAeDeploymentSource aDeployment )
   throws AeException;
}
