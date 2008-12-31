// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/IAeWebServicesDeployer.java,v 1.4 2007/02/13 15:26:5
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

/**
 * Handle the platform specific details of deploying a web service.
 */
public interface IAeWebServicesDeployer
{
   /**
    * Deploy all web services in the container 
    * 
    * @param aContainer
    * @param aLoader
    * @throws AeException
    */
   public void deployToWebServiceContainer(IAeDeploymentContainer aContainer, ClassLoader aLoader) throws AeException;
   
   /**
    * Undeploy all web services in the container
    * 
    * @param aContainer
    * @throws AeException
    */
   public void undeployFromWebServiceContainer(IAeDeploymentContainer aContainer) throws AeException;
   
   /**
    * Deploy one web service 
    * 
    * @param aContainer
    * @param aLoader
    * @throws AeException
    */
   public void deployToWebServiceContainer(IAeServiceDeploymentInfo aService, ClassLoader aLoader) throws AeException;
   
   /**
    * Undeploy one web service
    * 
    * @param aContainer
    * @throws AeException
    */
   public void undeployFromWebServiceContainer(IAeServiceDeploymentInfo aService) throws AeException;
}
