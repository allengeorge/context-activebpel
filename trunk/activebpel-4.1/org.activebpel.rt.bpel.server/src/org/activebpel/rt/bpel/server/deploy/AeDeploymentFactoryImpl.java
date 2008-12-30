// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeDeploymentFactoryImpl.java,v 1.5 2006/07/18 20:05:3
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

import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.deploy.validate.IAeValidationHandler;
import org.activebpel.rt.util.AeUtil;

/**
 * Default IAeDeploymentFactory impl.  Gets all of its deployer impls
 * from the config map.  None of its deployers should contain any
 * state info (as they are all treated like singeltons).
 */
public class AeDeploymentFactoryImpl implements IAeDeploymentFactory
{
   // deployer xml constants
   private static final String PDEF_DEPLOYER         = "PdefDeployer"; //$NON-NLS-1$
   private static final String CATALOG_DEPLOYER      = "CatalogDeployer"; //$NON-NLS-1$
   private static final String WEB_SERVICES_DEPLOYER = "WebServicesDeployer"; //$NON-NLS-1$
   private static final String BPEL_DEPLOYER         = "BpelDeployer"; //$NON-NLS-1$
   private static final String VALIDATION_HANDLER    = "ValidationHandler"; //$NON-NLS-1$
   
   /** Validation handler. */
   protected IAeValidationHandler mValidationHandler;
   /** Partner definition deployer. */
   protected IAePdefDeployer mPdefDeployer;
   /** Wsdl catalog deployer. */
   protected IAeCatalogDeployer mCatalogDeployer;
   /** Web services deployer. */   
   protected IAeWebServicesDeployer mWebServicesDeployer;
   /** BPEL process deployer. */
   protected IAeBpelDeployer mBpelDeployer;

   /**
    * Constructos.
    * @param aConfig AeEngine config params map.
    * @throws AeException
    */
   public AeDeploymentFactoryImpl( Map aConfig ) throws AeException
   {
      if (AeUtil.isNullOrEmpty(aConfig))
      {
         throw new AeException(AeMessages.getString("AeDeploymentFactoryImpl.ERROR_0")); //$NON-NLS-1$
      }

      initializeDeployers( aConfig );
   }
   
   /**
    * Initialize the individual depoyers.
    * @param aConfig
    * @throws AeException
    */
   protected void initializeDeployers( Map aConfig ) throws AeException
   {
      mPdefDeployer = (IAePdefDeployer)AeDeployerUtil.createDeployer(PDEF_DEPLOYER, aConfig);   
      mCatalogDeployer = (IAeCatalogDeployer)AeDeployerUtil.createDeployer(CATALOG_DEPLOYER, aConfig);               
      mWebServicesDeployer = (IAeWebServicesDeployer)AeDeployerUtil.createDeployer(WEB_SERVICES_DEPLOYER, aConfig);
      mBpelDeployer = (IAeBpelDeployer)AeDeployerUtil.createDeployer(BPEL_DEPLOYER, aConfig );
      
      mValidationHandler = (IAeValidationHandler)AeDeployerUtil.createDeployer(VALIDATION_HANDLER, aConfig );
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentFactory#getBpelDeployer()
    */
   public IAeBpelDeployer getBpelDeployer()
   {
      return mBpelDeployer;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentFactory#getPDefDeployer()
    */
   public IAePdefDeployer getPDefDeployer()
   {
      return mPdefDeployer;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentFactory#getCatalogDeployer()
    */
   public IAeCatalogDeployer getCatalogDeployer()
   {
      return mCatalogDeployer;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentFactory#getWebServicesDeployer()
    */
   public IAeWebServicesDeployer getWebServicesDeployer()
   {
      return mWebServicesDeployer;
   }
      
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentFactory#getValidationHandler()
    */
   public IAeValidationHandler getValidationHandler()
   {
      return mValidationHandler;
   }
}
