//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/IAeDeploymentInfo.java,v 1.1 2004/12/10 15:59:2
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This interface defines the methods necessary to implement an object that represents the 
 * deployment result for a single PDD in a BPR.
 */
public interface IAeDeploymentInfo
{
   /**
    * Returns the name of the PDD for this deployment.
    */
   public String getPddName();

   /**
    * Returns a flag indicating if the PDD was deployed.
    */
   public boolean isDeployed();
   
   /**
    * Gets the number of errors found when deploying this PDD.
    */
   public int getNumErrors();

   /**
    * Gets the number of warnings found when deploying this PDD.
    */
   public int getNumWarnings();

   /**
    * Gets the deployment log for this PDD.
    */
   public String getLog();
   
   /**
    * Converts this deployment info object into an XML element.  Uses the supplied DOM to 
    * create the element.
    * 
    * @param aDom
    */
   public Element toElement(Document aDom);
}
