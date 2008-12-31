// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/AeProcessDeploymentDetail.java,v 1.6 2004/10/29 21:14:1
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
package org.activebpel.rt.bpel.server.admin;

import javax.xml.namespace.QName;

/**
 * JavaBean for holding some data for a deployed process. It includes the
 * name of the process as well as any process ids for running instances.
 */
public class AeProcessDeploymentDetail
{
   /** Name of the deployed process */
   private QName mName;
   /** Deployment xml for this process */
   private String mSourceXml;
   /** The src bpel for the deployed process */
   private String mBpelSourceXml;
   
   /**
    * Default Ctor
    */
   public AeProcessDeploymentDetail()
   {
   }

   /**
    * Getter for the process name
    */
   public QName getName()
   {
      return mName;
   }
   
   /**
    * Accessor for process deployment qname local part.
    */
   public String getLocalName()
   {
      return getName().getLocalPart();
   }

   /**
    * Sets the name of the deployed process
    * @param aName
    */
   public void setName(QName aName)
   {
      mName = aName;
   }

   /**
    * Setter for the source xml
    * @param sourceXml
    */
   public void setSourceXml(String sourceXml)
   {
      mSourceXml = sourceXml;
   }

   /**
    * Getter for the source xml
    */
   public String getSourceXml()
   {
      return mSourceXml;
   }

   /**
    * @param aBpelSourceXml
    */
   public void setBpelSourceXml(String aBpelSourceXml)
   {
      mBpelSourceXml = aBpelSourceXml;
   }
   
   /**
    * Getter for bpel source xml.
    */
   public String getBpelSourceXml()
   {
      return mBpelSourceXml;
   }
}
