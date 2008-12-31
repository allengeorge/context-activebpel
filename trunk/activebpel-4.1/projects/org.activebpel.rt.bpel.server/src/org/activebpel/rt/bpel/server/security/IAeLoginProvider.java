//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/security/IAeLoginProvider.java,v 1.1 2007/02/13 15:26:5
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

import javax.security.auth.Subject;

/**
 * Interface for pluggable login providers
 */
public interface IAeLoginProvider
{
   
   public static final String USERNAME_ENTRY = "Username"; //$NON-NLS-1$
   public static final String PASSWORD_ENTRY = "Password"; //$NON-NLS-1$
   
   /**
    * Authenticates a user with a set of username/password credentials
    * 
    * @param aUsername
    * @param aPassword
    * @throws AeSecurityException if user not authenticated
    */
   public void authenticate(String aUsername, String aPassword) throws AeSecurityException;

   /**
    * Authenticates a user with a set of username/password credentials, updating the subject
    * given in the parameters.
    * 
    * @param aUsername
    * @param aPassword
    * @param aSubject
    * @throws AeSecurityException if user not authenticated
    */
   public void authenticate(String aUsername, String aPassword, Subject aSubject) throws AeSecurityException;
   
}
