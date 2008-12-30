//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/urn/AeURNMapping.java,v 1.1 2005/06/22 17:17:3
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
package org.activebpel.rt.bpeladmin.war.web.urn; 

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Contains the URN and the URL it maps to.
 */
public class AeURNMapping
{
   /** value for the urn */
   private String mURN;
   /** url that the urn maps to */
   private String mURL;
   
   /**
    * Creates the tuple with the provided values.
    * 
    * @param aURN
    * @param aURL
    */
   public AeURNMapping(String aURN, String aURL)
   {
      mURN = aURN;
      mURL = aURL;
   }
   
   /**
    * Getter for the URL
    */
   public String getURL()
   {
      return mURL;
   }
   
   /**
    * Gets the encoded form of the urn for use as a form submit value.
    */
   public String getEncodedURN()
   {
      try
      {
         return URLEncoder.encode(getURN(), "UTF8"); //$NON-NLS-1$
      }
      catch (UnsupportedEncodingException e)
      {
         return getURN();
      }
   }
   
   /**
    * Special getter for displaying the url in an html page. HTML chars are escaped
    * with their entities.
    */
   public String getDisplayURL()
   {
      return escapeHtmlChars(getURL());      
   }
   
   /**
    * Special getter for displaying the urn in an html page. HTML chars are escaped
    * with their entities.
    */
   public String getDisplayURN()
   {
      return escapeHtmlChars(getURN());
   }
   
   /**
    * Escapes the html chars with their entity equivalents.
    * @param aString
    */
   protected String escapeHtmlChars(String aString)
   {
      aString = aString.replaceAll("\"", "&quot;"); //$NON-NLS-1$ //$NON-NLS-2$
      aString = aString.replaceAll("<", "&lt;"); //$NON-NLS-1$ //$NON-NLS-2$ 
      aString = aString.replaceAll(">", "&gt;"); //$NON-NLS-1$ //$NON-NLS-2$ 

      return aString;
   }

   /**
    * Getter for the URN
    */
   public String getURN()
   {
      return mURN;
   }
}
 
