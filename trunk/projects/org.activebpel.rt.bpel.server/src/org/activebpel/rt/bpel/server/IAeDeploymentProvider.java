// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/IAeDeploymentProvider.java,v 1.15 2007/09/02 17:17:1
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
package org.activebpel.rt.bpel.server;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAePlanManager;
import org.activebpel.rt.bpel.server.deploy.AeOperationNotImplementedException;
import org.activebpel.rt.bpel.server.deploy.AeRoutingInfo;
import org.activebpel.rt.bpel.server.deploy.AeServiceNotFoundException;

/**
 * Manages the deployment descriptors which have been deployed for BPEL processes.
 */
public interface IAeDeploymentProvider extends IAePlanManager
{
   /**
    * Add a new plan deployment.
    * @param aDeployment
    */
   public void addDeploymentPlan( IAeProcessDeployment aDeployment );
   
   /**
    * Returns the deployment plan for the plan capable of creating a new instance
    * fo the provided process name.
    * @param aProcessName the process we want the deployment plan for.
    */
   public IAeProcessDeployment findCurrentDeployment(QName aProcessName) throws AeBusinessProcessException;
   
   /**
    * Gets the deployment plan for the given process id.
    * @param aProcessId
    * @param aProcessName
    */
   public IAeProcessDeployment findDeploymentPlan(long aProcessId, QName aProcessName) throws AeBusinessProcessException;

   /**
    * Gets an Iterator of IAeProcessDeployment for all the deployed plans.
    */
   public Iterator getDeployedPlans();

   /**
    * Remove the deployment for the specified process qname.
    * @param aProcessName
    */
   public void removeDeploymentPlan( QName aProcessName );
   
   /**
    * Gets the routing information for the given service name
    * 
    * @param aServiceName
    * @throws AeBusinessProcessException if not found
    */
   public AeRoutingInfo getRoutingInfoByServiceName(String aServiceName) throws AeBusinessProcessException;
   
   /**
    * Gets the routing info for a process that provides a service with the given 
    * name, port type and operation.
    * @param aService
    * @param aPortType
    * @param aOperation
    * @throws AeServiceNotFoundException
    * @throws AeOperationNotImplementedException
    */
   public AeRoutingInfo findService(String aService, QName aPortType, String aOperation) throws AeServiceNotFoundException, AeOperationNotImplementedException;
}
