//$Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/IAeWsAddressingConstants.java,v 1.1 2006/08/08 16:37:5
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
package org.activebpel.wsio;

public interface IAeWsAddressingConstants
{
   /** local part of the Anonymous role URI */
   public static final String WSA_ANONYMOUS_ROLE = "/role/anonymous"; //$NON-NLS-1$
   /** default ws-addressing namespace prefix */
   public static final String WSA_NS_PREFIX = "wsa"; //$NON-NLS-1$
   /** local part of the relationship type used in 2004 versions of the spec*/
   public static final String WSA_REPLY_RELATION = "Reply";  //$NON-NLS-1$
   /** local part of the relationship type used in 2003 versions of the spec*/
   public static final String WSA_RESPONSE_RELATION = "Response";  //$NON-NLS-1$
   /** URI of default action */
   public static final String WSA_DEFAULT_ACTION = "http://schemas.active-endpoints.com/ws/2006/08/DefaultAction"; //$NON-NLS-1$
   /** URI of WSA fault action */
   public static final String WSA_FAULT_ACTION_ = "/fault"; //$NON-NLS-1$
   /** Attribute name for RelatesTo RelationshipType */
   public static final String WSA_RELATIONSHIP_TYPE = "RelationshipType"; //$NON-NLS-1$
   /** Web Services Addressing namespace declaration. */
   public static final String WSA_NAMESPACE_URI = "http://schemas.xmlsoap.org/ws/2003/03/addressing";    //$NON-NLS-1$
   /** Web Services Addressing namespace declaration. */
   public static final String WSA_NAMESPACE_URI_2004_03 = "http://schemas.xmlsoap.org/ws/2004/03/addressing";    //$NON-NLS-1$
   /** Web Services Addressing namespace declaration. */
   public static final String WSA_NAMESPACE_URI_2004_08 = "http://schemas.xmlsoap.org/ws/2004/08/addressing";    //$NON-NLS-1$
   
}
