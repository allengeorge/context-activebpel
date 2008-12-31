//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeSetResourceTag.java,v 1.1 2007/04/24 17:23:1
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

/**
 * Tag to set the resource bundle value on a bean.
 * E.g &lt;ae:SetResource key="bundle_key" name="bean_name" property="bean_property" /&gt;
 */
public class AeSetResourceTag extends AeAbstractBeanPropertyTag
{
   /** Key name. */
   private String mKey;

   /**
    * @see javax.servlet.jsp.tagext.Tag#doStartTag()
    */
   public int doStartTag() throws JspException
   {
      String value = getResourceString( getKey() );
      setPropertyOnBean( value, String.class);
      return SKIP_BODY;
   }
      
   /**
    * @return the key
    */
   public String getKey()
   {
      return mKey;
   }

   /**
    * @param aKey the key to set
    */
   public void setKey(String aKey)
   {
      mKey = aKey;
   }
}
