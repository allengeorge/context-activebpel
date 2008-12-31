//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeDeploymentSummary.java,v 1.3 2005/02/01 19:56:3
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

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * This class contains all information about the deployment of a single BPR file.  Each
 * BPR may have multiple deployment units.  The result of the deployment of each unit 
 * will exist in a list.
 */
public class AeDeploymentSummary implements IAeDeploymentSummary, Serializable
{
   /** The root element name for the XML representation of this object. */
   private static final String DEPLOYMENT_SUMMARY_ELEM = "deploymentSummary"; //$NON-NLS-1$
   /** The global messages element name. */
   private static final String GLOBAL_MESSAGES_ELEM = "globalMessages"; //$NON-NLS-1$
   /** The attribute name that will hold the number of deployment errors. */
   private static final String NUM_ERRORS_ATTR         = "numErrors"; //$NON-NLS-1$
   /** The attribute name that will hold the number of deployment warnings. */
   private static final String NUM_WARNINGS_ATTR       = "numWarnings"; //$NON-NLS-1$

   /** The list of deployment info objects. */
   protected List mDeploymentInfos;
   /** The global messages found during deployment. */
   protected String mGlobalMessages;

   /**
    * Constructs a deployment summary given a list of deployment info objects.
    * 
    * @param aList
    */
   public AeDeploymentSummary(List aList, String aMessages)
   {
      mDeploymentInfos = aList;
      mGlobalMessages = aMessages;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentSummary#getDeploymentInfoList()
    */
   public List getDeploymentInfoList()
   {
      return mDeploymentInfos;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentSummary#getTotalWarnings()
    */
   public int getTotalWarnings()
   {
      int total = 0;
      Iterator iter = getDeploymentInfoList().iterator();
      while (iter.hasNext())
      {
         IAeDeploymentInfo depInfo = (IAeDeploymentInfo) iter.next();
         total += depInfo.getNumWarnings();
      }
      return total;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentSummary#getTotalErrors()
    */
   public int getTotalErrors()
   {
      int total = 0;
      Iterator iter = getDeploymentInfoList().iterator();
      while (iter.hasNext())
      {
         IAeDeploymentInfo depInfo = (IAeDeploymentInfo) iter.next();
         total += depInfo.getNumErrors();
      }
      return total;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentSummary#toDocument()
    */
   public Document toDocument()
   {
      Document dom = AeXmlUtil.newDocument();

      // Create the root element
      Element rootElem = dom.createElement(DEPLOYMENT_SUMMARY_ELEM);
      dom.appendChild(rootElem);
      rootElem.setAttribute(NUM_ERRORS_ATTR, "" + getTotalErrors()); //$NON-NLS-1$
      rootElem.setAttribute(NUM_WARNINGS_ATTR, "" + getTotalWarnings()); //$NON-NLS-1$

      // Create the global messages element
      Element globalMessages = dom.createElement(GLOBAL_MESSAGES_ELEM);
      globalMessages.appendChild(dom.createTextNode(mGlobalMessages));
      rootElem.appendChild(globalMessages);

      // Iterate through all of the deployment info objects and create elements for each one.
      Iterator iter = getDeploymentInfoList().iterator();
      while (iter.hasNext())
      {
         IAeDeploymentInfo depInfo = (IAeDeploymentInfo) iter.next();
         Element elem = depInfo.toElement(dom);
         rootElem.appendChild(elem);
      }

      return dom;
   }

}
