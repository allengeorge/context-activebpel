//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/IAePddXmlConstants.java,v 1.13 2007/05/18 20:15:1
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

/**
 * Interface for the pdd xml constants.
 */
public interface IAePddXmlConstants
{
   // PDD XML constants.
   public static final String ATT_PROCESS_LOCATION   = "location"; //$NON-NLS-1$
   public static final String ATT_MYROLE_SERVICE     = "service"; //$NON-NLS-1$
   public static final String ATT_NAME               = "name"; //$NON-NLS-1$
   public static final String ATT_LOCATION           = "location"; //$NON-NLS-1$
   public static final String ATT_PROCESS_GROUP      = "processGroup"; //$NON-NLS-1$
   public static final String ATT_PROCESS_RETENTION_DAYS = "processRetentionDays"; //$NON-NLS-1$
   public static final String ATT_CUSTOM_INVOKER     = "customInvokerUri"; //$NON-NLS-1$
   public static final String ATT_INVOKE_HANDLER     = "invokeHandler"; //$NON-NLS-1$
   public static final String ATT_ENDPOINT_REF       = "endpointReference"; //$NON-NLS-1$
   public static final String ATT_PERSISTENCE_TYPE   = "persistenceType"; //$NON-NLS-1$
   public static final String ATT_TRANSACTION_TYPE   = "transactionType"; //$NON-NLS-1$
   public static final String ATT_SERVICE            = "service"; //$NON-NLS-1$
   public static final String ATT_BINDING            = "binding"; //$NON-NLS-1$
   public static final String ATT_ALLOWED_ROLES      = "allowedRoles"; //$NON-NLS-1$
   public static final String ATT_SUSPEND_PROCESS_ON_UNCAUGHT_FAULT = "suspendProcessOnUncaughtFault"; //$NON-NLS-1$
   public static final String ATT_TYPE_URI           = "typeURI"; //$NON-NLS-1$
   public static final String ATT_PORT_NAME          = "PortName"; //$NON-NLS-1$
   public static final String ATT_EFFECTIVE_DATE     = "effectiveDate"; //$NON-NLS-1$

   public static final String TAG_MYROLE             = "myRole"; //$NON-NLS-1$
   public static final String TAG_PARTNER_LINK       = "partnerLink"; //$NON-NLS-1$
   public static final String TAG_PARTNER_ROLE       = "partnerRole"; //$NON-NLS-1$
   public static final String TAG_ENDPOINT_REF       = "EndpointReference"; //$NON-NLS-1$
   public static final String TAG_ADDRESS            = "Address"; //$NON-NLS-1$
   public static final String TAG_SERVICE_NAME       = "ServiceName"; //$NON-NLS-1$
   public static final String TAG_VERSION            = "version"; //$NON-NLS-1$
   
   public static final String TAG_WSDL_REFERENCES    = "wsdlReferences"; //$NON-NLS-1$
   public static final String TAG_REFERENCES         = "references"; //$NON-NLS-1$
   public static final String TAG_WSDL               = "wsdl"; //$NON-NLS-1$
   public static final String TAG_SCHEMA             = "schema"; //$NON-NLS-1$
   public static final String TAG_OTHER              = "other"; //$NON-NLS-1$
}
