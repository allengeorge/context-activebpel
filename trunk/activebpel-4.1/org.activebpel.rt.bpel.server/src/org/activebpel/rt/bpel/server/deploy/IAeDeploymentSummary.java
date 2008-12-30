//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/IAeDeploymentSummary.java,v 1.1 2004/12/10 15:59:2
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

import java.util.List;

import org.w3c.dom.Document;

/**
 * This interface defines the methods necessary to implement a deployment summary.
 */
public interface IAeDeploymentSummary
{
   /**
    * Returns a list of all deployment info objects.
    */
   public List getDeploymentInfoList();

   /**
    * Returns the total number of warnings for the BPR deployment.
    */
   public int getTotalWarnings();

   /**
    * Returns the total number of errors for the BPR deployment.
    */
   public int getTotalErrors();

   /**
    * Converts the deployment summary to an XML dom.
    */
   public Document toDocument();
}
