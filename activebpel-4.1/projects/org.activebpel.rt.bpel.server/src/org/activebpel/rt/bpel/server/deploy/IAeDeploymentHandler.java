// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/IAeDeploymentHandler.java,v 1.5 2006/02/24 16:37:3
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
import org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger;

/**
 * Course grained deployment interface.
 */
public interface IAeDeploymentHandler
{
   /**
    * Deploy container resources to the system.
    * 
    * @param aContainer
    * @param aLogger
    * @throws AeException
    */
   public void deploy(IAeDeploymentContainer aContainer, IAeDeploymentLogger aLogger) throws AeException;
   
   /**
    * Undeploy container resources from the system.
    * 
    * @param aContainerS
    * @throws AeException
    */
   public void undeploy( IAeDeploymentContainer aContainerS ) throws AeException;
}
