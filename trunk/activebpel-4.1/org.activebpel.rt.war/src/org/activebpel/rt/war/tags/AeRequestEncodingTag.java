// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeRequestEncodingTag.java,v 1.1 2007/04/24 17:23:1
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

import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.activebpel.rt.war.AeMessages;

/**
 * Sets the character encoding for the page's request parameters. 
 */
public class AeRequestEncodingTag extends TagSupport
{
   /** The character encoding value. */
   protected String mValue;

   /**
    * @see javax.servlet.jsp.tagext.Tag#doStartTag()
    */
   public int doStartTag() throws JspException
   {
      try
      {
         pageContext.getRequest().setCharacterEncoding(getValue());
      }
      catch (UnsupportedEncodingException e)
      {
         throw new JspException(AeMessages.getString("AeRequestEncodingTag.0") + getValue(), e); //$NON-NLS-1$
      }

      return SKIP_BODY;
   }

   /**
    * Returns the character encoding value.
    */
   protected String getValue()
   {
      return mValue;
   }

   /**
    * Sets the character encoding value.
    */
   public void setValue(String aValue)
   {
      mValue = aValue;
   }
}
