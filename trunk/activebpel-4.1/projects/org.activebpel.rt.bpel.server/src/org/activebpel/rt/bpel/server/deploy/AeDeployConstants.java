//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeDeployConstants.java,v 1.7 2007/05/18 20:15:1
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
 * Constants related to process deployment
 */
public class AeDeployConstants
{
   /** binding for policy services */
   public static final String BIND_POLICY = "POLICY"; //$NON-NLS-1$
   /** binding for external services */
   public static final String BIND_EXTERNAL = "EXTERNAL"; //$NON-NLS-1$
   /** binding for rpc style services */
   public static final String BIND_RPC = "RPC"; //$NON-NLS-1$
   /** binding for rpc literal style services */
   public static final String BIND_RPC_LIT = "RPC-LIT"; //$NON-NLS-1$
   /** binding for msg style services */
   public static final String BIND_MSG = "MSG"; //$NON-NLS-1$
   
   /** Process disposition Maintain old version.*/
   public static final String MAINTAIN = "maintain"; //$NON-NLS-1$
   /** Process disposition Terminate. */
   public static final String TERMINATE = "terminate"; //$NON-NLS-1$
   /** Process disposition Migrate old version. */
   public static final String MIGRATE = "migrate"; //$NON-NLS-1$
   /** Process disposition Migrate and suspend old version. */
   public static final String MIGRATE_AND_SUSPEND = "migrateAndSuspend"; //$NON-NLS-1$
   
   /** Version element from pdd */
   public static final String VERSION_EL = "version"; //$NON-NLS-1$
   
   /** Version id attribute from pdd */
   public static final String VERSIONID_ATTR = "id"; //$NON-NLS-1$
   /** expiration date attribute from pdd */
   public static final String EXPDATE_ATTR = "expirationDate"; //$NON-NLS-1$
   /** expiration date attribute from pdd */
   public static final String PROCESS_RETENTION_DAYS_ATTR = "processRetentionDays"; //$NON-NLS-1$
   /** process group attribute from pdd */
   public static final String PROCESS_GROUP = "processGroup"; //$NON-NLS-1$
   /** effective date attribute from pdd */
   public static final String EFFDATE_ATTR = "effectiveDate"; //$NON-NLS-1$
   /** process disposition attribute from pdd */
   public static final String PROCESSDISP_ATTR = "runningProcessDisposition";  //$NON-NLS-1$
   /** process persistence type attribute from pdd */
   public static final String PERSISTENCE_TYPE_ATTR = "persistenceType"; //$NON-NLS-1$
   /** process transactino type attribute from pdd */
   public static final String TRANSACTION_TYPE_ATTR = "transactionType"; //$NON-NLS-1$

   /** Process persistence type: default. */
   public static final String PERSISTENCE_TYPE_DEFAULT = "default"; //$NON-NLS-1$
   /** Process persistence type: full. */
   public static final String PERSISTENCE_TYPE_FULL = "full"; //$NON-NLS-1$
   /** Process persistence type: none (never persist). */
   public static final String PERSISTENCE_TYPE_NONE = "none"; //$NON-NLS-1$

   /** Process suspend on uncaught fault: default. */
   public static final String SUSPEND_TYPE_DEFAULT = "default"; //$NON-NLS-1$
   /** Process suspend on uncaught fault: true. */
   public static final String SUSPEND_TYPE_TRUE = "true"; //$NON-NLS-1$
   /** Process suspend on uncaught fault: false. */
   public static final String SUSPEND_TYPE_FALSE = "false"; //$NON-NLS-1$
   
   
   
   /** Process transaction type: bean. */
   public static final String TRANSACTION_TYPE_BEAN = "bean"; //$NON-NLS-1$
   /** Process transaction type: container. */
   public static final String TRANSACTION_TYPE_CONTAINER = "container"; //$NON-NLS-1$
   
   /** Exception management type: defer to engine setting. */
   public static final String EXCEPTION_MANAGEMENT_TYPE_ENGINE = "engine"; //$NON-NLS-1$
   /** Exception management type: suspend the process (override any engine setting). */
   public static final String EXCEPTION_MANAGEMENT_TYPE_SUSPEND = "suspend"; //$NON-NLS-1$
   /** Exception management type: normal behavior  (override any engine setting). */
   public static final String EXCEPTION_MANAGEMENT_TYPE_NORMAL = "normal"; //$NON-NLS-1$
}
