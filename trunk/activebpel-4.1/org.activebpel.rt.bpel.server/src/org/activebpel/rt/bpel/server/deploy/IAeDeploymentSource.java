// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/IAeDeploymentSource.java,v 1.19 2007/02/13 15:26:5
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

import java.util.Collection;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AeProcessDef;
import org.w3c.dom.Element;

/**
 * Interface for deploying bpel process to the engine.
 */
public interface IAeDeploymentSource
{
   /**
    * Gets the plan in id for this deployment source. Only applies when versioning is enabled.
    */
   public int getPlanId();

   /**
    * Get the name of the current pdd resource.
    * @return name of pdd resource
    */
   public String getPddLocation();

   /**
    * Accessor for bpel dom resource location.
    * @return bpel resource path
    */
   public String getBpelSourceLocation();
   
   /**
    * QName for the bpel process
    * @return bpel process QName
    */
   public QName getProcessName();
   
   /**
    * AeProcessDef for the bpel process.
    */
   public AeProcessDef getProcessDef();
   
   /**
    * Accessor for the dom process element.
    * @return dom process element
    */
   public Element getProcessSourceElement();
   
   /**
    * Set of keys for resource imports associated with this deployment.
    * @return Set of AeResourceKey objects.
    */
   public Set getResourceKeys();
   
   /**
    * Return the collection of partner link descriptors.
    */
   public Collection getPartnerLinkDescriptors();

   /**
    * Returns persistence type.
    */
   public AeProcessPersistenceType getPersistenceType();

   /**
    * Returns transaction type.
    */
   public AeProcessTransactionType getTransactionType();
   
   /**
    * Return the process exception management type.
    */
   public AeExceptionManagementType getExceptionManagementType();
   
   /**
    * Gets the services for the plan
    */
   public IAeServiceDeploymentInfo[] getServices() throws AeDeploymentException;
}
