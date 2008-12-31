//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/rdebug/server/IAeRemoteDebugConstants.java,v 1.2 2007/01/25 16:55:1
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
package org.activebpel.rt.bpel.server.admin.rdebug.server;

/**
 * Constants used for administrative web services on both stub and skeltons.
 */
public interface IAeRemoteDebugConstants
{
   /** Marker node path attribute. */
   public static final String NODE_PATH = "node_path" ; // $NON-NLS-1$ //$NON-NLS-1$
   
   /** Marker process QName attribute. */
   public static final String PROCESS_QNAME_NAMESPACE = "process_qname_namespace" ; // $NON-NLS-1$ //$NON-NLS-1$
   
   /** Marker process QName attribute. */
   public static final String PROCESS_QNAME_LOCAL_PART = "process_qname_local_part" ; // $NON-NLS-1$ //$NON-NLS-1$

   /** Target namespace for engine administration web service */
   public final static String ENGINE_ADMIN_NS = "http://docs.active-endpoints/wsdl/activebpeladmin/2007/01/activebpeladmin.wsdl"; //$NON-NLS-1$
   /** Service name for engine administration service */
   public final static String ENGINE_ADMIN_SERVICE = "ActiveBpelAdmin"; //$NON-NLS-1$

   // TODO (RN) These constants will go away in next release
   /** Target namespace for RPC style engine administration web service. */ 
   public final static String RPC_ENGINE_ADMIN_NS = "urn:AeAdminServices"; //$NON-NLS-1$
   /** Service name for RPC style engine administration service. */ 
   public final static String RPC_ENGINE_ADMIN_SERVICE = "BpelEngineAdmin"; //$NON-NLS-1$
}
