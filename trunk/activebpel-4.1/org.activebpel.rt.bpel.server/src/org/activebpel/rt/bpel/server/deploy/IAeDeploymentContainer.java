// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/IAeDeploymentContainer.java,v 1.5 2007/02/13 15:26:5
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

import org.activebpel.rt.bpel.server.deploy.bpr.IAeBpr;
import org.w3c.dom.Document;

/**
 * Top level interface for wrapping of the deployment details.
 */
public interface IAeDeploymentContainer extends IAeBpr, IAeDeploymentContext
{
   /**
    * Get the web services specific deployment/undeployment document.
    */
   public Document getWsddData();

   /**
    * Set the web services specific deployment/undeployment document.
    * @param aDocument
    */
   public void setWsddData( Document aDocument );
   
   /**
    * @return service deployment information
    */
   public IAeServiceDeploymentInfo[] getServiceDeploymentInfo();
   
   /**
    * @param aServiceInfo service deployment information
    */
   public void setServiceDeploymentInfo(IAeServiceDeploymentInfo[] aServiceInfo);

   /**
    * @param aServiceInfo service deployment information
    */
   public void addServiceDeploymentInfo(IAeServiceDeploymentInfo[] aServiceInfo);
   
   /**
    * Return any special classloaders needed for web services deployment.
    */
   public ClassLoader getWebServicesClassLoader();
   
   /**
    * @return the type of deployment (wsdd, bpel, etc)
    */
   public String getDeploymentType();
}
