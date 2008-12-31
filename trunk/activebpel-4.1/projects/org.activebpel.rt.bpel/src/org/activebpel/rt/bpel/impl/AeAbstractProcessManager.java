// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeAbstractProcessManager.java,v 1.2 2007/05/24 00:50:1
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
package org.activebpel.rt.bpel.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.util.AeSafelyViewableCollection;
import org.activebpel.rt.util.AeUtil;

/**
 * Implements a simple in-memory process manager.
 */
public abstract class AeAbstractProcessManager extends AeManagerAdapter implements IAeProcessManager
{
   public static final String CONFIG_DEBUG = "Debug"; //$NON-NLS-1$

   /** <code>true</code> if and only if showing debug output. */
   private static boolean sDebug = false;

   /** Configuration for this process manager. */
   private Map mConfig;

   /** Process purged listeners */
   private Collection mProcessPurgedListeners = new AeSafelyViewableCollection(new LinkedHashSet());

   /**
    * Constructs an in-memory process manager.
    *
    * @param aConfig The configuration map for this manager.
    */
   public AeAbstractProcessManager(Map aConfig)
   {
      setConfig(aConfig);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#addProcessPurgedListener(org.activebpel.rt.bpel.impl.IAeProcessPurgedListener)
    */
   public void addProcessPurgedListener(IAeProcessPurgedListener aListener)
   {
      getProcessPurgedListeners().add(aListener);
   }

   /**
    * Writes debugging output.
    */
   public void debug(String aMessage)
   {
      if (isDebug())
      {
         System.out.println(aMessage);
      }
   }

   /**
    * Writes formatted debugging output.
    */
   public void debug(String aPattern, long aArgument)
   {
      debug(aPattern, new Object[] { new Long(aArgument) });
   }

   /**
    * Writes formatted debugging output.
    */
   public void debug(String aPattern, Object[] aArguments)
   {
      if (isDebug()) // test for debugging before formatting
      {
         debug(MessageFormat.format(aPattern, aArguments));
      }
   }

   /**
    * Notifies all process purged listeners that the given process has been
    * purged by this process manager.
    *
    * @param aProcessId
    */
   protected void fireProcessPurged(long aProcessId)
   {
      for (Iterator i = getProcessPurgedListeners().iterator(); i.hasNext(); )
      {
         try
         {
            ((IAeProcessPurgedListener) i.next()).processPurged(aProcessId);
         }
         catch (Throwable t)
         {
            // Just log exception from listeners, because we should not let them
            // impact us in any way.
            AeException.logError(t);
         }
      }
   }

   /**
    * Returns configuration <code>Map</code>.
    */
   protected Map getConfig()
   {
      return mConfig;
   }

   /**
    * Returns value from configuration <code>Map</code>.
    *
    * @param aKey
    */
   protected String getConfig(String aKey)
   {
      return (String) getConfig().get(aKey);
   }

   /**
    * Returns <code>int</code> value from configuration <code>Map</code>.
    *
    * @param aKey
    */
   protected int getConfigInt(String aKey, int aDefaultValue)
   {
      String value = getConfig(aKey);
      if (!AeUtil.isNullOrEmpty(value))
      {
         try
         {
            return Integer.parseInt(value);
         }
         catch (NumberFormatException e)
         {
            AeException.logError(e, AeMessages.format("AeAbstractProcessManager.ERROR_InvalidConfigValue", new Object[] { value, aKey })); //$NON-NLS-1$
         }
      }

      return aDefaultValue;
   }

   /**
    * Returns the process purged listeners for this process manager.
    */
   protected Collection getProcessPurgedListeners()
   {
      return mProcessPurgedListeners;
   }

   /**
    * @return <code>true</code> if and only if the process manager is in debug
    * mode.
    */
   public static boolean isDebug()
   {
      return sDebug;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessManager#removeProcessPurgedListener(org.activebpel.rt.bpel.impl.IAeProcessPurgedListener)
    */
   public void removeProcessPurgedListener(IAeProcessPurgedListener aListener)
   {
      getProcessPurgedListeners().remove(aListener);
   }

   /**
    * Sets configuration.
    */
   protected void setConfig(Map aConfig)
   {
      mConfig = aConfig;

      // Set our debug flag.
      sDebug = "true".equals(getConfig(CONFIG_DEBUG)); //$NON-NLS-1$
   }
}
