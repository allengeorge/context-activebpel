//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/AeMessages.java,v 1.2 2007/08/07 18:43:1
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
package org.activebpel.rt.war;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Accessor class for externalized strings
 */
public class AeMessages
{
   private static final String BUNDLE_NAME = "org.activebpel.rt.war.messages";//$NON-NLS-1$

   private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

   private AeMessages()
   {
   }

   /**
    * Convenience method for formatting an externalized string with
    * a single replacement value.
    * @param aKey key of string from bundle
    * @param aParam first parameter to substitute
    * @return the formatted string
    */
   public static String format( String aKey, Object aParam )
   {
      return format( aKey, new Object[]{aParam} );
   }
   
   /**
    * Return message formatted externalized string.
    * @param aKey key of string from bundle
    * @param aArgs array of arguments for substitution
    * @return the formatted string
    */
   public static String format( String aKey, Object[] aArgs )
   {
      String templateString = getString( aKey );
      return MessageFormat.format( templateString, aArgs );
   }

   /**
    * Returns externalized string from resource bundle or key surrounded by exclamation points
    * if not found.
    * @param aKey - Key of string to return
    * @return - String from bundle
    */
   public static String getString(String aKey)
   {
      return getString(aKey, '!' + aKey + '!');
   }
   
   /**
    * Returns externalized string from resource bundle or the default message 
    * the key is not found.
    * @param aKey - Key of string to return
    * @param aDefaultMessage - string to return if the key is not found.
    * @return - String from bundle
    */
   public static String getString(String aKey, String aDefaultMessage)
   {
      try
      {
         return RESOURCE_BUNDLE.getString(aKey);
      }
      catch (MissingResourceException e)
      {
         return aDefaultMessage;
      }
   }
   
}
