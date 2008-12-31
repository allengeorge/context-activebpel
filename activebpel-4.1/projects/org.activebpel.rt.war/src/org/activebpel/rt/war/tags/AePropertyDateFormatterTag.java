//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AePropertyDateFormatterTag.java,v 1.1 2007/04/24 17:23:1
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

import java.text.Format;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.war.AeMessages;

/**
 * Formats a Date value to be displayed.
 */
public class AePropertyDateFormatterTag extends AeAbstractPropertyFormatterTag
{
   
   /**
    * Returns the formatted date string to be displayed.
    * @return formatted date string to be displayed.
    * @throws AeException if error occur during text formatting.
    */
   protected String getFormattedText() throws AeException, JspException
   {
      Date date = (Date) getPropertyFromBean();
      if (date != null)
      {
         // call base class to get the appropriate text formatter.
         return getResolvedFormatter().format(date);
      }
      else
      {
         // if property is null, then display empty string.
         return "";  //$NON-NLS-1$
      }     
   }
   
   /**
    * Creates and returns the concrete DateFormat object.
    * 
    * @param aPattern
    * @return DateFormat object.
    * @throws AeException if unable to create and return a DateFormat object. 
    */
   protected Format createFormatter(String aPattern) throws AeException
   {
      try
      {
         Format formatter = null;
         if (!AeUtil.isNullOrEmpty(aPattern) )   
         {
            formatter = new SimpleDateFormat(aPattern);
         }
         else
         {
            // pattern not given - use default locale
            formatter = new SimpleDateFormat();
         }
         return formatter;
      }   
      catch(Exception e)
      {
         // Catch:
         // NullPointerException - if the given pattern is null 
         // IllegalArgumentException - if the given pattern is invalid
         String err = MessageFormat.format(AeMessages.getString("AePropertyDateFormatterTag.1"), //$NON-NLS-1$
               new Object[] {e.getMessage()} );            
         throw new AeException(err);
         
      }
   }   
}
 
