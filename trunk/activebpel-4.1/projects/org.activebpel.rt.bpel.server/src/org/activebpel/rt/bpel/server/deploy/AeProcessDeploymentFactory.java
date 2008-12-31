// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeProcessDeploymentFactory.java,v 1.19 2006/06/26 18:28:2
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

import java.util.Iterator;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.impl.IAeProcessPlan;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.IAeProcessDeployment;
import org.activebpel.rt.bpel.server.deploy.pdd.AePartnerLinkDescriptor;

/**
 * Factory to create a process deployment from a deployment source.
 */
public class AeProcessDeploymentFactory
{
   /** Singleton factory instance. */
   private static final AeProcessDeploymentFactory INSTANCE = new AeProcessDeploymentFactory();
   
   /**
    * Accessor for singleton instance.
    */
   public static AeProcessDeploymentFactory getInstance()
   {
      return INSTANCE;
   }

   /**
    * Create a new IAeProcessDeployment from the deployment source.
    * @param aSource
    * @throws AeDeploymentException
    */
   public IAeProcessDeployment newInstance( IAeDeploymentSource aSource )
   throws AeDeploymentException
   {
      AeProcessDeployment deployment = new AeProcessDeployment(aSource);
      initProcessDef(aSource, deployment);
      initPartnerLinks(aSource, deployment);

      preprocessProcessDef( aSource.getBpelSourceLocation(), deployment );      
      return deployment;
   }
   
   /**
    * Add AeProcessDef to the deployment.
    * @param aSource deployment source
    * @param deployment deployment target
    * @throws AeDeploymentException
    */
   protected void initProcessDef( IAeDeploymentSource aSource, 
                  AeProcessDeployment deployment) throws AeDeploymentException
   {
      AeProcessDef processDef = aSource.getProcessDef();
      if( processDef == null )
      {
         throw new AeDeploymentException(AeMessages.getString("AeProcessDeploymentFactory.ERROR_0") + aSource.getPddLocation() ); //$NON-NLS-1$
      }
      deployment.setProcess( processDef );
   }

   /**
    * Add all of the partner link types to the deployment
    * @param aSource deployment source
    * @param deployment deployment target
    */
   protected void initPartnerLinks(IAeDeploymentSource aSource, AeProcessDeployment deployment)
   {
      for( Iterator iter = aSource.getPartnerLinkDescriptors().iterator(); iter.hasNext(); )
      {
         deployment.addPartnerLinkDescriptor( (AePartnerLinkDescriptor)iter.next() );
      }
   }

   /**
    * Preprocess the deployments AeProcessDef.
    * @param aLocationHint provided for error reporting purposes if something goes wrong in preprocessing
    * @param aDeployment contains the AeProcessDef to preprocess
    * @throws AeDeploymentException
    */
   protected void preprocessProcessDef( String aLocationHint, IAeProcessDeployment aDeployment )
   throws AeDeploymentException
   {
      try
      {
         aDeployment.preProcessDefinition();
      }
      catch (AeBusinessProcessException e)
      {
         throw new AeDeploymentException(AeMessages.getString("AeProcessDeploymentFactory.ERROR_1") +  //$NON-NLS-1$
                                    aLocationHint, e );
      }
   }
   
   /**
    * Gets the deployment info for the given plan.  
    * 
    * @param aPlan
    */
   public static IAeProcessDeployment getDeploymentForPlan(IAeProcessPlan aPlan)
   {
      // This method exists to provide a single point where we cast the plan to
      // its deployment interface. 
      return (IAeProcessDeployment) aPlan;
   }
}
