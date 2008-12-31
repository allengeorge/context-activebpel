//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/security/IAeSecurityProvider.java,v 1.1 2007/02/13 15:26:5
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
package org.activebpel.rt.bpel.server.security;

import org.activebpel.wsio.receive.IAeMessageContext;

/**
 * Security manager interface for Authentication and Authorization of service requests
 */
public interface IAeSecurityProvider extends IAeLoginProvider, IAeAuthorizationProvider
{
   /** Name of entry for login module. */
   public static final String LOGIN_PROVIDER_ENTRY = "LoginProvider"; //$NON-NLS-1$
   /** Name of entry for authorization module. */
   public static final String AUTHORIZATION_PROVIDER_ENTRY = "AuthorizationProvider"; //$NON-NLS-1$
   
   /**
    * Authenticates and authorizes a set of user credentials for a request described in 
    * the message context
    * 
    * @param aUsername
    * @param aPassword
    * @param aContext
    */
   public void login(String aUsername, String aPassword, IAeMessageContext aContext) throws AeSecurityException;
   
}
