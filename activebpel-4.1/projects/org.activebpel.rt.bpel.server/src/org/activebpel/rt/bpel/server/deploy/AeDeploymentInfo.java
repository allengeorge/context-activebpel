//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeDeploymentInfo.java,v 1.3 2005/02/04 21:43:0
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class represents information about the deployment of a single deployment unit (PDD)
 * within a BPR.
 */
public class AeDeploymentInfo implements Serializable, IAeDeploymentInfo
{
   /** The name of the element to use for this deployment info when converting to XML. */
   private static final String DEPLOYMENT_INFO_ELEM = "deploymentInfo"; //$NON-NLS-1$
   /** The name of the attribute used to store the value of the deployed flag. */
   private static final String DEPLOYED_ATTR = "deployed"; //$NON-NLS-1$
   /** The name of the attribute used to store the value of numWarnings. */
   private static final String NUM_WARNINGS_ATTR = "numWarnings"; //$NON-NLS-1$
   /** The name of the attribute used to store the value of numErrors. */
   private static final String NUM_ERRORS_ATTR = "numErrors"; //$NON-NLS-1$
   /** The name of the attribute used to store the pdd name. */
   private static final String PDD_NAME_ATTR = "pddName"; //$NON-NLS-1$
   /** The name of the element used to store the log. */
   private static final String LOG_ELEM = "log"; //$NON-NLS-1$
   
   /** The name of the PDD. */
   protected String mPddName;
   /** Flag indicating if the PDD was deployed successfully. */
   protected boolean mDeployed = false;
   /** A counter for the number of errors. */
   protected int mNumErrors = 0;
   /** A counter for the number of warnings. */
   protected int mNumWarnings = 0;
   /** The deployment log. */
   protected String mLog;

   /**
    * Default constructor.
    */
   public AeDeploymentInfo()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentInfo#getPddName()
    */
   public String getPddName()
   {
      return mPddName;
   }

   /**
    * Setter for the PDD name.
    * 
    * @param aPddName
    */
   public void setPddName(String aPddName)
   {
      mPddName = aPddName;
   }

   /**
    * Getter for the 'deployed' flag.
    */
   public boolean isDeployed()
   {
      return mDeployed;
   }

   /**
    * Setter for the 'deployed' flag.
    * 
    * @param aDeployed
    */
   public void setDeployed(boolean aDeployed)
   {
      mDeployed = aDeployed;
   }

   /**
    * Getter for the number of errors.
    */
   public int getNumErrors()
   {
      return mNumErrors;
   }
   
   /**
    * Setter for the number of errors.
    * 
    * @param aNumErrors
    */
   public void setNumErrors(int aNumErrors)
   {
      mNumErrors = aNumErrors;
   }
   
   /**
    * Getter for the number of warnings.
    */
   public int getNumWarnings()
   {
      return mNumWarnings;
   }
   
   /**
    * Setter for the number of warnings.
    * 
    * @param aNumWarnings
    */
   public void setNumWarnings(int aNumWarnings)
   {
      mNumWarnings = aNumWarnings;
   }
   
   /**
    * Increments the number of errors by 1.
    */
   public void incrementNumErrors()
   {
      mNumErrors++;
   }

   /**
    * Increments the number of warnings by 1.
    */
   public void incrementNumWarnings()
   {
      mNumWarnings++;
   }
   
   /**
    * Gets the log.  Will never return null;
    */
   public String getLog()
   {
      if (mLog == null)
      {
         return ""; //$NON-NLS-1$
      }
      else
      {
         return mLog;
      }
   }
   
   /**
    * Sets the log.
    * 
    * @param aLog
    */
   public void setLog(String aLog)
   {
      mLog = aLog;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentInfo#toElement(org.w3c.dom.Document)
    */
   public Element toElement(Document aDom)
   {
      Element elem = aDom.createElement(DEPLOYMENT_INFO_ELEM);
      elem.setAttribute(PDD_NAME_ATTR, getPddName());
      elem.setAttribute(NUM_ERRORS_ATTR, "" + getNumErrors()); //$NON-NLS-1$
      elem.setAttribute(NUM_WARNINGS_ATTR, "" + getNumWarnings()); //$NON-NLS-1$
      elem.setAttribute(DEPLOYED_ATTR, "" + isDeployed()); //$NON-NLS-1$
      
      Element logElem = aDom.createElement(LOG_ELEM);
      logElem.appendChild(aDom.createTextNode(getLog()));
      elem.appendChild(logElem);
      return elem;
   }
}
