//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/AeAxisPrincipal.java,v 1.1 2007/02/13 15:33:2
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
package org.activebpel.rt.axis;

import org.activebpel.rt.bpel.server.security.IAePrincipal;
import org.apache.axis.security.AuthenticatedUser;
import org.apache.axis.security.servlet.ServletAuthenticatedUser;

/**
 * Principal wrapper for Axis AuthenticatedUser principal
 */
public class AeAxisPrincipal implements IAePrincipal
{
   private AuthenticatedUser mUser;

   /**
    * @param aUser authenticated user from Axis
    */
   public AeAxisPrincipal(AuthenticatedUser aUser)
   {
      mUser = aUser;
   }

   /**
    * @see org.activebpel.rt.bpel.server.security.IAePrincipal#isUserInRole(java.lang.String)
    */
   public boolean isUserInRole(String aRolename)
   {
      AuthenticatedUser user = getUser();
      if (user == null)
      {
         return false;
      }
      else if (user instanceof ServletAuthenticatedUser)
      {
         // Use the servlet request to check user role
         ServletAuthenticatedUser servletUser = (ServletAuthenticatedUser) user;
         if (servletUser.getRequest() != null)
         {
            return servletUser.getRequest().isUserInRole(aRolename);
         }
         else
         {
            return false;
         }
      }
      else
      {
         // just do a simple name match
         return user.getName().equals(aRolename);
      }
   }

   public String getName()
   {
      return getUser().getName();
   }
   
   /**
    * @return the user
    */
   public AuthenticatedUser getUser()
   {
      return mUser;
   }

}
