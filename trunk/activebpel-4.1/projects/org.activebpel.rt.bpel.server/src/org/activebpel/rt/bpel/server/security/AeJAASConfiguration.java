//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/security/AeJAASConfiguration.java,v 1.1 2007/02/13 15:26:5
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
package org.activebpel.rt.bpel.server.security;

import java.util.Collections;
import java.util.Map;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

import org.activebpel.rt.bpel.config.IAeEngineConfiguration;
import org.activebpel.rt.util.AeUtil;

/**
 * Extension of the JAAS configuration that allows us to add an additional login module
 * to the existing configuration.   
 */
public class AeJAASConfiguration extends Configuration
{
   /** Name of entry for login module entry. */
   public static final String LOGIN_MODULE_ENTRY = "LoginModule"; //$NON-NLS-1$
   
   private String mAppName;
   private Configuration mDelegateConfig;
   private AppConfigurationEntry[] mEntry;

   /**
    * Constructor that takes the engine configuration
    * @param aConfig
    */
   public AeJAASConfiguration(Map aConfig, String aAppConfigName)
   {
      // Get the current config as the delegate
      mDelegateConfig = Configuration.getConfiguration();
      mEntry = createEntries(aConfig);
      mAppName = aAppConfigName;
   }
   
   /**
    * Retreives the config for the application.  Will check the original configuration first.  
    * If not found, will return the configuration from the bpel engine.
    * 
    * @see javax.security.auth.login.Configuration#getAppConfigurationEntry(java.lang.String)
    */
   public AppConfigurationEntry[] getAppConfigurationEntry(String aConfigAppName)
   {
      AppConfigurationEntry[] entries = null;
      try
      {
         // try from the original config first
         entries = getDelegate().getAppConfigurationEntry(aConfigAppName);
         if (entries == null)
         {
            // Return engine login module.
            entries = getEntriesFromEngineConfig(aConfigAppName);
         }
      }
      catch (IllegalArgumentException e)
      {
         // Weblogic throws a runtime exception if the
         // config is not found, rather than simply returning null
         // so we'll return the entries based the engine configuration 
         if (getEntriesFromEngineConfig(aConfigAppName) != null)
         {
            return getEntriesFromEngineConfig(aConfigAppName);
         }
         else
         {
            throw e;
         }
      }
      return entries;
   }

   /**
    * @see javax.security.auth.login.Configuration#refresh()
    */
   public void refresh()
   {
      getDelegate().refresh();
   }

   /**
    * Creates configuration entries from settings in the engine config
    * 
    * @param aConfig
    */
   protected AppConfigurationEntry[] createEntries(Map aConfig)
   {
      String name = (String) aConfig.get(IAeEngineConfiguration.CLASS_ENTRY);
      if (!AeUtil.isNullOrEmpty(name))
      {
         AppConfigurationEntry entry = new AppConfigurationEntry(name, AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, Collections.EMPTY_MAP);
         return new AppConfigurationEntry[] {entry};
      }
      else
      {
         return null;
      }
   }

   /**
    * @return the original configuration
    */
   protected Configuration getDelegate()
   {
      return mDelegateConfig;
   }

   /**
    * @return the entries
    */
   protected AppConfigurationEntry[] getEntriesFromEngineConfig(String aAppName)
   {
      if (mAppName.equals(aAppName))
      {
         return mEntry;
      }
      else
      {
         return null;
      }
   }
   
}
