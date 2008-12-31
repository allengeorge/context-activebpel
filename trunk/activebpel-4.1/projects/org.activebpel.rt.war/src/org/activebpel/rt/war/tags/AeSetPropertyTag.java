//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeSetPropertyTag.java,v 1.1 2007/04/24 17:23:1
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
 * Property setter that is equivalent to the jsp:setProperty.
 * This tags also allows one to set the property from another bean.
 * Eg: &lt;ae:SetProperty name="beanName" property="methodname" fromName="fromBean" fromProperty="fromProperty" /&gt;
 * </p>
 * <p>
 * You can also set a value supplied as an attribute.
 * Eg: &lt;ae:SetProperty name="beanName" property="methodname" value="property value" /&gt; 
 * </p>
 * <p>
 * Finally, you can also set a value supplied from the http request parameter.
 * Eg: &lt;ae:SetProperty name="beanName" property="methodname" param="http param name" /&gt; 
 * </p>
 * 
 */
public class AeSetPropertyTag extends AeAbstractBeanPropertyTag
{
   /** Name of the param to read from the request */
   private String mParam;
   /** Value attribute. */
   private String mValue;
   /**  Copy from bean name */
   private String mFromName;
   /** Copy from bean property name. */
   private String mFromProperty;

   /**
    * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
    */
   public int doStartTag() throws JspException
   {
      if (AeUtil.notNullOrEmpty( getFromName() ) && AeUtil.notNullOrEmpty( getFromProperty() ) )
      {
         // set value obtained from another bean.
         Object fromBean = pageContext.findAttribute( getFromName() );
         if (fromBean != null)
         {
            Object value = getPropertyFromBean(fromBean, getFromProperty());
            if (value != null)
            {
               setPropertyOnBean(value, value.getClass());
            }
         }
      }
      else if (getValue() != null)
      {
         // set value from 'value' attribute in the tag.
         setPropertyOnBean(getValue(), String.class);
      }
      else if ( getParamValue() != null)
      {
         // set value from request parameter
         setPropertyOnBean(getParamValue(), String.class);
      }
      return SKIP_BODY;
   }

   /**
    * Gets the param value that's converted to a date.
    */
   protected String getParamValue()
   {
      return pageContext.getRequest().getParameter(getParam());
   }

   /**
    * @return Returns the param.
    */
   public String getParam()
   {
      return mParam;
   }

   /**
    * @param aParam The param to set.
    */
   public void setParam(String aParam)
   {
      mParam = aParam;
   }

   /**
    * @return the fromName
    */
   public String getFromName()
   {
      return mFromName;
   }

   /**
    * @param aFromName the fromName to set
    */
   public void setFromName(String aFromName)
   {
      mFromName = aFromName;
   }

   /**
    * @return the fromProperty
    */
   public String getFromProperty()
   {
      return mFromProperty;
   }

   /**
    * @param aFromProperty the fromProperty to set
    */
   public void setFromProperty(String aFromProperty)
   {
      mFromProperty = aFromProperty;
   }

   /**
    * @return the value
    */
   public String getValue()
   {
      return mValue;
   }

   /**
    * @param aValue the value to set
    */
   public void setValue(String aValue)
   {
      mValue = aValue;
   }
}
