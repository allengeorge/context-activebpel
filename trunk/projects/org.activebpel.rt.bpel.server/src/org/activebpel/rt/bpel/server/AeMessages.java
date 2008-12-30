//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/AeMessages.java,v 1.3 2005/07/12 00:23:0
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
package org.activebpel.rt.bpel.server;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Accessor class for externalized strings
 */
public class AeMessages
{
   private static final String BUNDLE_NAME = "org.activebpel.rt.bpel.server.messages";//$NON-NLS-1$

   private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

   private AeMessages()
   {
   }

   /**
    * Returns message formatted from externalized format string in resource bundle.
    *
    * @param aKey
    * @param argument
    */
   public static String format(String aKey, int argument)
   {
      return format(aKey, new Integer(argument));
   }

   /**
    * Returns message formatted from externalized format string in resource bundle.
    *
    * @param aKey
    * @param argument
    */
   public static String format(String aKey, long argument)
   {
      return format(aKey, new Long(argument));
   }

   /**
    * Returns message formatted from externalized format string in resource bundle.
    *
    * @param aKey
    * @param argument
    */
   public static String format(String aKey, Object argument)
   {
      Object[] arguments = (argument instanceof Object[]) ? ((Object[]) argument) : new Object[] { argument };
      return format(aKey, arguments);
   }

   /**
    * Returns message formatted from externalized format string in resource bundle.
    *
    * @param aKey
    * @param arguments
    */
   public static String format(String aKey, Object[] arguments)
   {
      return MessageFormat.format(getString(aKey), arguments);
   }

   /**
    * Returns externalized string from resource bundle or key surrounded by exclamation points
    * if not found.
    * @param key - Key of string to return
    * @return - String from bundle
    */       
   public static String getString(String key)
   {
      try
      {
         return RESOURCE_BUNDLE.getString(key);
      }
      catch (MissingResourceException e)
      {
         return '!' + key + '!';
      }
   }
}
