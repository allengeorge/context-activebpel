//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeDateSetterTag.java,v 1.1 2007/04/24 17:23:1
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.war.AeMessages;

/**
 * Enables the setting of date properties on a bean from date values passed
 * in as request params. 
 */
public class AeDateSetterTag extends AePropertyDateFormatterTag
{
   /** name of the param to read from the request */
   private String mParam;
   
   /**
    * Sets the value of the parsed date from the request param on the bean.
    * 
    * @see javax.servlet.jsp.tagext.Tag#doStartTag()
    */
   public int doStartTag() throws JspException
   {
      try
      {         
         Date date = (Date) getDateFromParam();
         setPropertyOnBean(date, Date.class);
      }
      catch(AeException ae)
      {
         Object bean = getBean();
         if (bean instanceof IAeErrorAwareBean)
         {
            ((IAeErrorAwareBean)bean).addError(getProperty(), getParamValue(), AeMessages.getString("AeDateSetterTag.0") + getParamValue()); //$NON-NLS-1$
         }
      }
      return SKIP_BODY;
   }
   
   /**
    * Parses a <code>java.util.Date</code> from the request param identified by 
    * the param property.
    * @throws ParseException
    */
   protected Date getDateFromParam() throws AeException
   {
      Date d = null;
      String value = getParamValue();
      if (AeUtil.notNullOrEmpty(value))
      {
         try 
         {
            SimpleDateFormat sdf = (SimpleDateFormat) getResolvedFormatter();
            d = sdf.parse(value);
         }
         catch(ParseException parseException)
         {
            throw new AeException(parseException);
         }
         catch(AeException ae)
         { 
            throw ae;
         }
      }
      return d;
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
}
 
