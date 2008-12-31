//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AePropertyNumberFormatterTag.java,v 1.1 2007/04/24 17:23:1
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

import java.text.DecimalFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;

import javax.servlet.jsp.JspException;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeHTMLFormatter;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.war.AeMessages;

/**
 * Formats a Number value to be displayed.
 */
public class AePropertyNumberFormatterTag extends AeAbstractPropertyFormatterTag
{   

   private Object getNumberBean()
   {
      Object rObject = null;
      try
      {
         rObject = getPropertyFromBean();
      }
      catch(Exception e)
      {
      }
      return rObject;
   }
   
   /**
    * Returns the formatted number to be displayed.
    * 
    * @return formatted number string to be displayed.
    * @throws AeException if error occur during text formatting.
    */
   protected String getFormattedText() throws AeException, JspException
   {
      // Get the appropriate number formatter
      NumberFormat nf = (NumberFormat) getResolvedFormatter();
      
      // Get the property value
      Object obj = getNumberBean();

      return AeHTMLFormatter.formatNumber(obj, nf);
   }

   /**
    * Creates and returns the concrete DecimalFormat object.
    * 
    * @param aPattern
    * @return DecimalFormat object.
    * @throws AeException if unable to create and return a DecimalFormat object. 
    */
   protected Format createFormatter(String aPattern) throws AeException
   {
      try
      {
         if (!AeUtil.isNullOrEmpty(aPattern))
         {
            return new DecimalFormat(aPattern);
         }
         else
         {
            // returns default format based on default locale.
            return new DecimalFormat();
         }
      }
      catch(Exception e)
      {
         // Catch:
         // NullPointerException - if the given pattern is null 
         // IllegalArgumentException - if the given pattern is invalid
         String err = MessageFormat.format(AeMessages.getString("AePropertyNumberFormatterTag.2"), //$NON-NLS-1$
               new Object[] {e.getMessage()} );            
         throw new AeException(err);                  
      }
   }   
}
 
