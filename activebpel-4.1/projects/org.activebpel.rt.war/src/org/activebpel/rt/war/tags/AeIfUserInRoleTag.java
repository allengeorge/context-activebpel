//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeIfUserInRoleTag.java,v 1.1 2007/04/24 17:23:1
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
package org.activebpel.rt.war.tags; 

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Custom tag that will evaluate its contents if the user is in the role specified
 * or if there was no authentication information available. This is used to hide
 * some UI elements that aren't available to all users. It should only be used
 * in cases where there are a few elements on a page to hide. If this tag is used
 * a lot on a page then it's probably better to have a separate JSP for each role.
 */
public class AeIfUserInRoleTag extends TagSupport
{
   /** name of the role to check for */
   private String mRole;
   
   /**
    * Will evaluate the body contents if the user is in the specified role or
    * if there is no principal available.
    * 
    * @see javax.servlet.jsp.tagext.Tag#doStartTag()
    */
   public int doStartTag() throws JspException
   {
      if( isUserInRole() )
      {
         return EVAL_BODY_INCLUDE;
      }
      else
      {
         return SKIP_BODY;
      }
   }
   
   /**
    * Returns true if the user is in the specified role or if there is no principal
    * available
    */
   protected boolean isUserInRole()
   {
      HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
      Principal p = req.getUserPrincipal();
      if (p == null)
      {
         return true;
      }
      else
      {
         return req.isUserInRole(getRole());
      }
   }

   /**
    * @return Returns the role.
    */
   public String getRole()
   {
      return mRole;
   }
   
   /**
    * @param aRole The role to set.
    */
   public void setRole(String aRole)
   {
      mRole = aRole;
   }
}
 
