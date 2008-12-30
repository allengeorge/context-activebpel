// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/AeRecoveryEngineFactory.java,v 1.6 2007/07/09 16:28:4
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
package org.activebpel.rt.bpel.server.engine.recovery;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Implements factory object that constructs a recovery engine along with the
 * recovery engine's managers.
 */
public class AeRecoveryEngineFactory
{
   /** Singleton instance. */
   private static AeRecoveryEngineFactory sInstance = new AeRecoveryEngineFactory();

   /**
    * Private constructor for singleton instance.
    */
   private AeRecoveryEngineFactory()
   {
   }

   /**
    * Returns singleton instance.
    */
   public static AeRecoveryEngineFactory getInstance()
   {
      return sInstance;
   }

   /**
    * Constructs a recovery engine.
    */
   public IAeRecoveryEngine newRecoveryEngine()
   {
      return newRecoveryEngine(AeEngineFactory.getEngine());
   }

   /**
    * Constructs a recovery engine using the engine configuration and partner
    * link strategy from the given engine.
    */
   public IAeRecoveryEngine newRecoveryEngine(IAeBusinessProcessEngineInternal aBaseEngine)
   {
      Map customManagers = getCustomManagers(aBaseEngine);
      
      return new AeRecoveryEngine(
         aBaseEngine.getEngineConfiguration(),
         new AeRecoveryQueueManager(),
         new AeRecoveryProcessManager(),
         new AeRecoveryLockManager(),
         aBaseEngine.getAttachmentManager(),
         aBaseEngine.getPartnerLinkStrategy(),
         aBaseEngine.getCoordinationManager(),
         aBaseEngine.getTransmissionTracker(),
         customManagers,
         aBaseEngine.getEngineId()
      );
   }

   /**
    * Gets the custom managers from the engine.
    * @param aBaseEngine
    */
   private Map getCustomManagers(IAeBusinessProcessEngineInternal aBaseEngine)
   {
      Map customManagers = new HashMap();
      for (Iterator iter = aBaseEngine.getCustomManagerNames(); iter.hasNext();)
      {
         String mgrName = (String) iter.next();
         customManagers.put(mgrName, aBaseEngine.getCustomManager(mgrName));
      }
      return customManagers;
   }
}
