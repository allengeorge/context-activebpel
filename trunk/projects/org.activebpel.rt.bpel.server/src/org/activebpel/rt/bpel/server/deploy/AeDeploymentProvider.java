// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeDeploymentProvider.java,v 1.19 2007/09/02 17:17:1
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeProcessPlan;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.IAeProcessDeployment;

/**
 * This class manages the deployment plans defined for BPEL processes.
 */
public class AeDeploymentProvider extends AeAbstractDeploymentProvider
{
   /** The deployment plans which are currently deployed */
   private HashMap mDeploymentPlans = new HashMap();

   /**
    * Constructor for the deployment provider.
    */
   public AeDeploymentProvider(Map aMap)
   {
   }

   /**
    * @see org.activebpel.rt.bpel.server.IAeDeploymentProvider#findCurrentDeployment(javax.xml.namespace.QName)
    */
   public IAeProcessDeployment findCurrentDeployment(QName aProcessName) throws AeBusinessProcessException
   {
      return (IAeProcessDeployment)mDeploymentPlans.get(aProcessName);
   }
   
   /**
    * @see org.activebpel.rt.bpel.IAePlanManager#findCurrentPlan(javax.xml.namespace.QName)
    */
   public IAeProcessPlan findCurrentPlan(QName aProcessName) throws AeBusinessProcessException
   {
      return findCurrentDeployment(aProcessName);
   }

   /**
    * @see org.activebpel.rt.bpel.server.IAeDeploymentProvider#addDeploymentPlan(org.activebpel.rt.bpel.server.IAeProcessDeployment)
    */
   public void addDeploymentPlan(IAeProcessDeployment aDeploymentPlan)
   {
      mDeploymentPlans.put(aDeploymentPlan.getProcessDef().getQName(), aDeploymentPlan);
   }

   /**
    * @see org.activebpel.rt.bpel.server.IAeDeploymentProvider#getDeployedPlans()
    */
   public Iterator getDeployedPlans()
   {
      synchronized(mDeploymentPlans)
      {
         return new ArrayList(mDeploymentPlans.values()).iterator();
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.IAeDeploymentProvider#removeDeploymentPlan(javax.xml.namespace.QName)
    */
   public void removeDeploymentPlan(QName aProcessName)
   {
      mDeploymentPlans.remove( aProcessName );
      AeServiceMap.getInstance().processUndeployed(aProcessName);
   }

   /**
    * @see org.activebpel.rt.bpel.server.IAeDeploymentProvider#findDeploymentPlan(long, javax.xml.namespace.QName)
    */
   public IAeProcessDeployment findDeploymentPlan(long aProcessId, QName aProcessName) throws AeBusinessProcessException
   {
      return (IAeProcessDeployment) mDeploymentPlans.get(aProcessName);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.IAeDeploymentProvider#getRoutingInfoByServiceName(java.lang.String)
    */
   public AeRoutingInfo getRoutingInfoByServiceName(String aServiceName) throws AeBusinessProcessException
   {
      AeRoutingInfo routingInfo = null;

      IAeServiceDeploymentInfo data = AeServiceMap.getInstance().getServiceData(aServiceName);
      if (data != null)
      {
         IAeProcessDeployment deployment = findCurrentDeployment(data.getProcessQName());
         
         if (deployment != null)
         {
            routingInfo = new AeRoutingInfo(deployment, data);
         }
      }
      
      if (routingInfo == null)
      {
         throw new AeBusinessProcessException(AeMessages.format("AeDeploymentProvider.NO_PLAN_FOR_SERVICE", aServiceName)); //$NON-NLS-1$
      }
      
      return routingInfo;
   }
}
