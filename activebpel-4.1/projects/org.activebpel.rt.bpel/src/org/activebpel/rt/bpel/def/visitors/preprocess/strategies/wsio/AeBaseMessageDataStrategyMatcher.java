//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/preprocess/strategies/wsio/AeBaseMessageDataStrategyMatcher.java,v 1.1 2006/09/11 23:06:2
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
package org.activebpel.rt.bpel.def.visitors.preprocess.strategies.wsio; 

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef;
import org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef;

/**
 * Base class for message data consumer and producer strategy matchers.  
 */
public abstract class AeBaseMessageDataStrategyMatcher implements IAeMessageDataStrategyMatcher
{
   /** map of producer specs to strategy names */
   private Map mProducerMap = new HashMap();
   /** map of consumer specs to strategy names */
   private Map mConsumerMap = new HashMap();
   
   /**
    * Ctor inits the maps 
    */
   public AeBaseMessageDataStrategyMatcher()
   {
      initMaps();
   }

   /**
    * Loads the map with all of the valid patterns
    */
   protected void initMaps()
   {
      AeMessageDataSpec spec = new AeMessageDataSpec();
      spec.setMessageVariable();
      getProducerMap().put(spec, IAeMessageDataStrategyNames.MESSAGE_VARIABLE);
      getConsumerMap().put(spec, IAeMessageDataStrategyNames.MESSAGE_VARIABLE);
   }

   /**
    * Getter for the map
    */
   protected Map getProducerMap()
   {
      return mProducerMap;
   }

   /**
    * Getter for the map
    */
   protected Map getConsumerMap()
   {
      return mConsumerMap;
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.preprocess.strategies.wsio.IAeMessageDataStrategyMatcher#getProducerStrategy(org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef)
    */
   public String getProducerStrategy(IAeMessageDataProducerDef aDef)
   {
      return (String) getProducerMap().get(AeMessageDataSpec.create(aDef));
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.preprocess.strategies.wsio.IAeMessageDataStrategyMatcher#getConsumerStrategy(org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef)
    */
   public String getConsumerStrategy(IAeMessageDataConsumerDef aDef)
   {
      return (String) getConsumerMap().get(AeMessageDataSpec.create(aDef));
   }
}
 
