//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/security/IAeAuthorizationProvider.java,v 1.1 2007/02/13 15:26:5
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

import java.util.Set;

import javax.security.auth.Subject;

import org.activebpel.wsio.receive.IAeMessageContext;

/**
 * Interface for pluggable authorization providers
 */
public interface IAeAuthorizationProvider
{
      
   /**
    * Determines if a subject is authorized to invoke the service 
    * described by the message context
    * 
    * @param aSubject Authenticated Subject
    * @param aContext Message Context for request
    * @return true if authorized
    * @throws AeSecurityException unable to authorize request
    */
   public boolean authorize(Subject aSubject, IAeMessageContext aContext ) throws AeSecurityException;

   /**
    * Determines if a subject is authorized to perform an action on a resource by checking
    * if the principals are in the comma-separated list of allowed roles 
    * 
    * @param aSubject Authenticated Subject
    * @param aAllowedRoles Set of allowed role strings
    * 
    * @return true if authorized
    * @throws AeSecurityException unable to authorize request
    */
   public boolean authorize(Subject aSubject, Set aAllowedRoles ) throws AeSecurityException;
}
