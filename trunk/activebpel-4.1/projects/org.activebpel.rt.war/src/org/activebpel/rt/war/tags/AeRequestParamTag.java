// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeRequestParamTag.java,v 1.1 2007/04/24 17:23:1
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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.war.AeMessages;

/**
 * Writes out the value of the named request parameter to
 * the <code>JspWriter</code>. 
 */
public class AeRequestParamTag extends TagSupport
{
   /** The key value for the request parameter. */
   protected String mParameterName;

   /**
    * @see javax.servlet.jsp.tagext.Tag#doStartTag()
    */
   public int doStartTag() throws JspException
   {
      String value = pageContext.getRequest().getParameter( getParameterName() );
      try
      {
         pageContext.getOut().write( AeUtil.getSafeString(value) );
         pageContext.getOut().flush();
      }
      catch (IOException e)
      {
         throw new JspException(AeMessages.getString("AeRequestParamTag.ERROR_0") + e.getMessage() ); //$NON-NLS-1$
      }      
      return SKIP_BODY;
   }
   
   /**
    * Getter for the parameter name.
    */
   protected String getParameterName()
   {
      return mParameterName;
   }
   
   /**
    * Setter for the parameter name.
    */
   public void setParameterName( String aName )
   {
      mParameterName = aName;
   }
}
