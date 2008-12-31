// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeGetResourceTag.java,v 1.1 2007/04/24 17:23:1
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

import javax.servlet.jsp.JspException;

import org.activebpel.rt.util.AeUtil;

/**
 * <p>
 * This tag will retrieve a value from the configured resource bundle.
 * </p><p>
 * Normal usage is to get the resource given the bundle key name.
 * E.g: &lt;ae:GetResource name="key_name" /&gt;
 * </p><p>
 * Another option is to specify the bundle key name via the request attribute.
 * E.g.  &lt;ae:GetResource attributeName="request_att_name" /&gt;. In this case
 * the bundle key name is the value specified by the http servlet request attribute
 * "request_att_name".
 * </p><p>
 * The final option is to get the bundle key name from a bean property.
 * E.g.  &lt;ae:GetResource name="bean_name" property="bean_property" /&gt;.
 * </p>
 */
public class AeGetResourceTag extends AeAbstractBeanPropertyTag
{
   /** Indirect method to access the key name via request attribute. */
   protected String mAttributeName;

   /**
    * @see javax.servlet.jsp.tagext.Tag#doStartTag()
    */
   public int doStartTag() throws JspException
   {
      String key = null;
      // first check to see if the request attribute name is specified
      if (AeUtil.notNullOrEmpty(getAttributeName()))
      {
         key = (String)pageContext.getRequest().getAttribute( getAttributeName());
      }
      else if (AeUtil.notNullOrEmpty(getName()) && AeUtil.notNullOrEmpty(getProperty()))
      {
         // check if key name is from bean property
         key = String.valueOf( getPropertyFromBean() );
      }
      else
      {
         // default case.
         key = getName();
      }
      String value = getResourceString( AeUtil.getSafeString(key));
      write(AeUtil.getSafeString(value));
      return SKIP_BODY;
   }

   /**
    * @return the attributeName
    */
   public String getAttributeName()
   {
      return mAttributeName;
   }

   /**
    * @param aAttributeName the attributeName to set
    */
   public void setAttributeName(String aAttributeName)
   {
      mAttributeName = aAttributeName;
   }


}
