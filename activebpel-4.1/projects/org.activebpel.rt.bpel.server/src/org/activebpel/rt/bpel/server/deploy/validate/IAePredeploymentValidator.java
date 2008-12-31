// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/IAePredeploymentValidator.java,v 1.6 2006/07/18 20:05:3
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
package org.activebpel.rt.bpel.server.deploy.validate;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter;
import org.activebpel.rt.bpel.server.deploy.bpr.IAeBpr;

/**
 * Perform validation on the IAeBprFile before attempting to
 * create deployment objects.
 */
public interface IAePredeploymentValidator extends IAeConstants
{
   ////////////////////////////////////////////////////////////////////////////
   // constants for attribute names 
   ////////////////////////////////////////////////////////////////////////////
   /** location attribute */
   public static final String LOCATION_ATTR         = "location"; //$NON-NLS-1$
   /** name attribute */
   public static final String NAME_ATTR             = "name"; //$NON-NLS-1$
   /** name attribute */
   public static final String TYPE_URI_ATTR         = "typeURI"; //$NON-NLS-1$
   /** service attribute */
   public static final String SERVICE_ATTR          = "service"; //$NON-NLS-1$
   /** classpath attribute */
   public static final String CLASSPATH_ATTR        = "classpath"; //$NON-NLS-1$
   /** targetNamespace attribute */
   public static final String TARGET_NAMESPACE_ATTR = "targetNamespace"; //$NON-NLS-1$

   ////////////////////////////////////////////////////////////////////////////
   // constants for element tag names 
   ////////////////////////////////////////////////////////////////////////////
   /** myRole element constant */
   public static final String MYROLE_ELEMENT       = "myRole"; //$NON-NLS-1$
   /** partnerRole element constant */
   public static final String PARTNERROLE_ELEMENT  = "partnerRole"; //$NON-NLS-1$
   /** ServiceName element constant */
   public static final String SERVICE_NAME_ELEMENT = "ServiceName"; //$NON-NLS-1$
   /** wsdl element constant */
   public static final String WSDL_ELEMENT         = "wsdl"; //$NON-NLS-1$
   /** otherlEntry element constant */
   public static final String OTHER_ENTRY_ELEMENT  = "otherEntry"; //$NON-NLS-1$
   /** wsdlEntry element constant */
   public static final String WSDL_ENTRY_ELEMENT   = "wsdlEntry"; //$NON-NLS-1$
   /** schemaEntry element constant */
   public static final String SCHEMA_ENTRY_ELEMENT = "schemaEntry"; //$NON-NLS-1$
   /** import element constant */
   public static final String IMPORT_ELEMENT       = "import"; //$NON-NLS-1$
   /** definitions element constant */
   public static final String DEFINITIONS_ELEMENT  = "definitions"; //$NON-NLS-1$
   /** partnerLink element constant */
   public static final String PARTNER_LINK_ELEMENT  = "partnerLink"; //$NON-NLS-1$
   

   /**
    * Validate the bpr file.
    * @param aBprFile the deployment bpr
    * @param aReporter absorbs error and warning messages
    * @throws AeException 
    */
   public void validate( IAeBpr aBprFile, IAeBaseErrorReporter aReporter )
   throws AeException;

}
