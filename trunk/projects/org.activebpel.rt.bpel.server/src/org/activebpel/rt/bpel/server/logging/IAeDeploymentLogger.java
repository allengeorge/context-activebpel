//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/logging/IAeDeploymentLogger.java,v 1.3 2004/12/10 15:59:2
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
package org.activebpel.rt.bpel.server.logging; 

import org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter;

/**
 * Interface for reporting errors, warnings, and progress information during
 * deployments. An instance will be used for the deployment of a single BPR file
 * which may contain multiple .pdd files.
 */
public interface IAeDeploymentLogger extends IAeBaseErrorReporter
{
   /**
    * Sets the name of the container that we're deploying. This is typically the
    * name of the BPR file that was uploaded.
    * @param aContainerName
    */
   public void setContainerName(String aContainerName);
   
   /**
    * Sets the name of the pdd currently being deployed.  This method is called each time the engine
    * begins deploying a new deployment unit (PDD). 
    * @param aPddName
    */
   public void setPddName(String aPddName);

   /**
    * Called when the deployment is done. 
    */
   public void close();
   
   /**
    * Resets the warning and error flags. 
    */
   public void resetWarningAndErrorFlags();
   
   /**
    * Adds an info message to the log
    * @param aMessage
    */
   public void addInfo(String aMessage);
   
   /**
    * This method is called when the processing of a PDD has finished (either successfully or
    * not).
    * 
    * @param aBool true if the PDD was actually deployed, false if it was not (for whatever reason)
    */
   public void processDeploymentFinished(boolean aBool);
}
