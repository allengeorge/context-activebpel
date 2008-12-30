// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/IAeValidationHandler.java,v 1.2 2005/06/13 17:54:0
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
package org.activebpel.rt.bpel.server.deploy.validate;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter;
import org.activebpel.rt.bpel.server.IAeProcessDeployment;
import org.activebpel.rt.bpel.server.deploy.bpr.IAeBpr;

/**
 * Interface used by the deployment code.  Allows the 
 * deployment code to validate the bpr as a whole 
 * (doPredeploymentValidation) and then validate each
 * IAeProcessDeployment before adding it to the engine.
 */
public interface IAeValidationHandler
{
   
   /**
    * Validate the bpr file.
    * @param aBpr The deployment unit.
    * @param aReporter Absorbs error and warning messages.
    * @throws AeException
    */
   public void doPredeploymentValidation( 
      IAeBpr aBpr,
      IAeBaseErrorReporter aReporter )
   throws AeException;
   
   /**
    * Validate the process deployment.
    * @param aPddLocation The source of the process deployment.
    * @param aDeployment The deployment object.
    * @param aReporter Absorbs error and warning messages.
    * @throws AeException
    */
   public void doDeploymentValidation( 
      String aPddLocation, 
      IAeProcessDeployment aDeployment, 
      IAeBaseErrorReporter aReporter )
   throws AeException;

}
