// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/AeDefaultValidationHandler.java,v 1.5 2005/06/13 17:54:0
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
 * The default validation handler for the system.
 */
public class AeDefaultValidationHandler implements IAeValidationHandler
{
   
   /** The top level predeployment validator */
   private static final IAePredeploymentValidator PREDEPLOY_VALIDATOR =
      AePredeploymentValidator.createDefault();

   /**
    * @see org.activebpel.rt.bpel.server.deploy.validate.IAeValidationHandler#doPredeploymentValidation(org.activebpel.rt.bpel.server.deploy.bpr.IAeBpr, org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter)
    */
   public void doPredeploymentValidation(
      IAeBpr aBpr,
      IAeBaseErrorReporter aReporter)
      throws AeException
   {
      PREDEPLOY_VALIDATOR.validate( aBpr, aReporter );
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.validate.IAeValidationHandler#doDeploymentValidation(java.lang.String, org.activebpel.rt.bpel.server.IAeProcessDeployment, org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter)
    */
   public void doDeploymentValidation(
      String aPddLocation,
      IAeProcessDeployment aDeployment,
      IAeBaseErrorReporter aReporter)
      throws AeException
   {
      AeDeploymentValidator deploymentValidator = 
         new AeDeploymentValidator(aPddLocation,aDeployment,aReporter);
      
      deploymentValidator.validate();
   }
}
