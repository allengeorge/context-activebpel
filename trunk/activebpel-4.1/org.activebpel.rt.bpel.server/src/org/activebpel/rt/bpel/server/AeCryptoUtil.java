//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/AeCryptoUtil.java,v 1.5 2007/08/02 19:57:0
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.config.IAeEngineConfiguration;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.util.AeJCEUtil;

/**
 *  Static utility methods supporting encryption, decryption, and keystore management using
 *  configuration settings from the engine configuration
 */
public class AeCryptoUtil
{
   /** JCE Util */
   private static AeJCEUtil sJCEUtil;
   
   /**
    * Initialize provider and load static key information
    */
   static
   {
      loadKey();
   }

   /**
    * Decrypts a string using our internal key
    * @param aInput String to decrypt
    * @return String decrypted
    * @throws Exception
    */
   public static String decryptString(String aInput)
   {
      return sJCEUtil.decryptString(aInput);
   }

   /**
    * Encrypts a string using our internal key
    * 
    * @param aInput String to encrypt
    * @return decrypted string
    * @throws Exception
    */
   public static String encryptString(String aInput)
   {
      return sJCEUtil.encryptString(aInput);
   }

   /**
    * Generates the internal key from the config entry
    */
   public static void loadKey()
   {
      try
      {
         // Get the string from the engine config to use to generate the key
         IAeEngineConfiguration config = AeEngineFactory.getEngineConfig();
         // Don't try anything if we can't get the config. If we are running in the designer, we will set the
         // key later
         if ( config == null )
            return;

         String password = config.getEntry("SharedSecret", "terces"); //$NON-NLS-1$ //$NON-NLS-2$
         loadKey(password);
      }
      catch (Exception e)
      {
         AeException.logWarning(AeMessages.getString("AeCryptoUtil.2")); //$NON-NLS-1$
      }
   }

   /**
    * Generates the internal key using the seed passed in
    */
   public static synchronized void loadKey(String aSeed)
   {
      sJCEUtil = AeJCEUtil.getInstance(aSeed);
   }
}
