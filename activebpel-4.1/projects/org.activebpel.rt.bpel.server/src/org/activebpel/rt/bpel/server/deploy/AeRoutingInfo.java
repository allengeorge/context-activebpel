//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeRoutingInfo.java,v 1.3 2007/09/02 17:17:1
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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.server.IAeProcessDeployment;

/**
 * Simple container for returning the routing information for a request.  
 */
public class AeRoutingInfo
{
   /** the deployment we'll route to */
   private IAeProcessDeployment mDeployment;
   /** the service being hit */
   private IAeServiceDeploymentInfo mServiceData;
   
   /**
    * Ctor
    * 
    * @param aDeployment
    * @param aData
    */
   public AeRoutingInfo(IAeProcessDeployment aDeployment, IAeServiceDeploymentInfo aData)
   {
      setDeployment(aDeployment);
      setServiceData(aData);
   }
   
   /**
    * @return Returns the deployment.
    */
   public IAeProcessDeployment getDeployment()
   {
      return mDeployment;
   }
   
   /**
    * @param aDeployment The deployment to set.
    */
   public void setDeployment(IAeProcessDeployment aDeployment)
   {
      mDeployment = aDeployment;
   }
   
   /**
    * @return Returns the serviceData.
    */
   public IAeServiceDeploymentInfo getServiceData()
   {
      return mServiceData;
   }
   
   /**
    * @param aServiceData The serviceData to set.
    */
   public void setServiceData(IAeServiceDeploymentInfo aServiceData)
   {
      mServiceData = aServiceData;
   }
   
   /**
    * Returns true if the service operation is implemented by this process
    * @param aPortType
    * @param aOperation
    */
   public boolean isImplemented(QName aPortType, String aOperation)
   {
      AePartnerLinkDef plinkDef = getDeployment().getProcessDef().findPartnerLink(getServiceData().getPartnerLinkDefKey());
      QName portTypeQName = plinkDef.getMyRolePortType();
      if (aPortType.equals(portTypeQName))
      {
         AeImplementsOperationVisitor visitor = new AeImplementsOperationVisitor(aOperation, plinkDef);
         getDeployment().getProcessDef().accept(visitor);
         return visitor.isFound();
      }
      return false;
   }
}
 
