//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/security/AeSimpleNamePrincipal.java,v 1.1 2007/02/13 15:26:5
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

/**
 * Simple implementation of a principal that holds the name of a user, group or role
 */
public class AeSimpleNamePrincipal implements IAePrincipal
{
   private String mName;

   /**
    * Constructor with name of user or group
    * @param aName
    */
   public AeSimpleNamePrincipal(String aName)
   {
      mName = aName;
   }

   /**
    * Returns true if the name matches the rolename
    * 
    * @see org.activebpel.rt.bpel.server.security.IAePrincipal#isUserInRole(java.lang.String)
    */
   public boolean isUserInRole(String aRolename)
   {
      return getName().equals(aRolename);
   }

   /**
    * @see java.security.Principal#getName()
    */
   public String getName()
   {
      return mName;
   }

}
