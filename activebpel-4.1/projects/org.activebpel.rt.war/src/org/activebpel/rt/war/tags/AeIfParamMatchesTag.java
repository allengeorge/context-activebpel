// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeIfParamMatchesTag.java,v 1.1 2007/04/24 17:23:1
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
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * A tag that will include its body content if
 * the value of the named http servlet request parameter
 * matches the expected value.
 */
public class AeIfParamMatchesTag extends BodyTagSupport
{
   /** The key for the http request parameter */
   protected String mProperty;
   /** The value to match against. */
   protected String mValue;

   /**
    * @see javax.servlet.jsp.tagext.Tag#doStartTag()
    */
   public int doStartTag() throws JspException
   {
      if( shouldEvaluateBody() )
      {
         return EVAL_BODY_INCLUDE;
      }
      return SKIP_BODY;
   }
   
   /**
    * Returns true if the request parameter matches the 
    * expected value.
    */
   protected boolean shouldEvaluateBody()
   {
      String paramValue = pageContext.getRequest().getParameter( getProperty() );
      return getValue().equals( paramValue );
   }
   
   /**
    * Accessor for the request parameter key.
    */
   public String getProperty()
   {
      return mProperty;
   }

   /**
    * Accessor for the "expected" value to compare against.
    */
   public String getValue()
   {
      return mValue;
   }

   /**
    * Setter for the request parameter key value.
    * @param aString
    */
   public void setProperty(String aString)
   {
      mProperty = aString;
   }

   /**
    * Setter for the "expected" value to compare against.
    * @param aString
    */
   public void setValue(String aString)
   {
      mValue = aString;
   }

   /**
    * @see javax.servlet.jsp.tagext.Tag#release()
    */
   public void release()
   {
      super.release();
      mProperty = null;
      mValue = null;
   }
}
