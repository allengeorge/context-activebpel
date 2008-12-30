// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/child/AeConfigAwareChildWorkManager.java,v 1.1 2007/06/20 19:40:0
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
package org.activebpel.work.child;

import commonj.work.WorkManager;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.config.IAeConfigChangeListener;
import org.activebpel.rt.bpel.config.IAeEngineConfiguration;
import org.activebpel.rt.bpel.config.IAeUpdatableEngineConfig;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Extends {@link AeChildWorkManager} to update maximum work count from
 * configuration whenever configuration changes. 
 */
public class AeConfigAwareChildWorkManager extends AeChildWorkManager implements IAeConfigChangeListener
{
   /** Path to max. work count entry in engine configuration. */
   private final String mConfigMaxWorkCountPath;
   
   /**
    * Constructor.
    *
    * @param aName
    * @param aMaxWorkCount
    */
   public AeConfigAwareChildWorkManager(String aName, int aMaxWorkCount)
   {
      this(aName, aMaxWorkCount, AeEngineFactory.getWorkManager());
   }

   /**
    * Constructor.
    *
    * @param aName
    * @param aMaxWorkCount
    * @param aParentWorkManager
    */
   public AeConfigAwareChildWorkManager(String aName, int aMaxWorkCount, WorkManager aParentWorkManager)
   {
      super(aName, aMaxWorkCount, aParentWorkManager);

      mConfigMaxWorkCountPath = IAeEngineConfiguration.WORK_MANAGER_ENTRY
         + "/" + IAeEngineConfiguration.CHILD_WORK_MANAGERS_ENTRY //$NON-NLS-1$
         + "/" + aName //$NON-NLS-1$
         + "/" + IAeEngineConfiguration.MAX_WORK_COUNT_ENTRY; //$NON-NLS-1$
      
      initConfig();
   }

   /**
    * Returns the configuration value for the maximum number of work items to
    * schedule from this work manager to its parent.
    *
    * @param aConfig
    * @return a <code>Number</code> representing the configuration value or <code>null</code> if the configuration value cannot be found or parsed
    */
   protected Number getConfigMaxWorkCount(IAeUpdatableEngineConfig aConfig)
   {
      Number result = null;

      // Get the config entry.
      Object entry = aConfig.getEntryByPath(getConfigMaxWorkCountPath());

      if (entry instanceof String)
      {
         try
         {
            result = Integer.valueOf((String) entry);
         }
         catch (Exception e)
         {
            AeException.logError(e);
         }
      }

      return result;
   }

   /**
    * @return path to entry in engine configuration
    */
   protected String getConfigMaxWorkCountPath()
   {
      return mConfigMaxWorkCountPath;
   }

   /**
    * Initializes configuration.
    */
   protected void initConfig()
   {
      IAeUpdatableEngineConfig config = AeEngineFactory.getEngineConfig().getUpdatableEngineConfig();
      config.addConfigChangeListener(this);
      updateConfig(config);
   }

   /**
    * @see org.activebpel.rt.bpel.config.IAeConfigChangeListener#updateConfig(org.activebpel.rt.bpel.config.IAeUpdatableEngineConfig)
    */
   public void updateConfig(IAeUpdatableEngineConfig aConfig)
   {
      Number maxWorkCount = getConfigMaxWorkCount(aConfig);
      if (maxWorkCount != null)
      {
         // Set the new maximum work count.
         setMaxWorkCount(maxWorkCount.intValue());
      }
   }
}
