//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/rdebug/client/IAeEventHandlerConstants.java,v 1.2 2007/01/25 16:55:1
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
package org.activebpel.rt.bpel.server.admin.rdebug.client;

/**
 * Constants used for administrative web services on both stub and skeltons.
 */
public interface IAeEventHandlerConstants
{
   /** Target namespace for engine administration web service */
   public final static String EVENT_HANDLER_NS = "http://docs.active-endpoints/wsdl/eventhandler/2007/01/eventhandler.wsdl"; //$NON-NLS-1$
   /** Target namespace for engine administration web service */
   public final static String EVENT_HANDLER_SERVICE = "ActiveBpelEventHandler"; //$NON-NLS-1$
   
   // TODO (RN) These constants will go away in next release
   /** Target namespace for RPC style engine administration web service. */ 
   public final static String RPC_EVENT_HANDLER_NS = "urn:AeRemoteDebugServices"; //$NON-NLS-1$
   /** Service name for RPC style engine administration service */ 
   public final static String RPC_EVENT_HANDLER_SERVICE = "BpelEventHandler"; //$NON-NLS-1$
}
