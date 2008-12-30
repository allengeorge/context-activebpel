//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/security/IAePrincipal.java,v 1.1 2007/02/13 15:26:5
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

import java.security.Principal;

/**
 * Extension of the java.security.Principal interface for principal implementations that
 * a) do not implement Principal and/or
 * b) support role checks directly
 * 
 * .NET and Axis principals fall into both categories above
 */
public interface IAePrincipal extends Principal
{
   /**
    * Checks if a user is in a given role
    * @param aRolename
    * @return true if user in role
    */
   public boolean isUserInRole(String aRolename);
}
