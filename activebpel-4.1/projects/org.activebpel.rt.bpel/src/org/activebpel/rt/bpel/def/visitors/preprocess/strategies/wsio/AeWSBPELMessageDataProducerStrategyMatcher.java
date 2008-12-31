//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/preprocess/strategies/wsio/AeWSBPELMessageDataProducerStrategyMatcher.java,v 1.2 2006/09/11 23:06:2
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


/**
 * Contains the valid strategy patterns for WS-BPEL 2.0 
 */
public class AeWSBPELMessageDataProducerStrategyMatcher extends AeBaseMessageDataStrategyMatcher
{
   /**
    * Ctor 
    */
   public AeWSBPELMessageDataProducerStrategyMatcher()
   {
      super();
   }
   
   /**
    * Loads the map with all of the valid patterns
    */
   protected void initMaps()
   {
      super.initMaps();
      
      // element variant
      AeMessageDataSpec spec = new AeMessageDataSpec();
      spec.setElementVariable();
      getProducerMap().put(spec, IAeMessageDataStrategyNames.ELEMENT_VARIABLE);
      getConsumerMap().put(spec, IAeMessageDataStrategyNames.ELEMENT_VARIABLE);

      // toPart variant
      spec = new AeMessageDataSpec();
      spec.setToParts();
      getProducerMap().put(spec, IAeMessageDataStrategyNames.TO_PARTS);

      // fromPart variant
      spec = new AeMessageDataSpec();
      spec.setFromParts();
      getConsumerMap().put(spec, IAeMessageDataStrategyNames.FROM_PARTS);

      // empty message variant
      spec = new AeMessageDataSpec();
      getProducerMap().put(spec, IAeMessageDataStrategyNames.EMPTY_MESSAGE);
      getConsumerMap().put(spec, IAeMessageDataStrategyNames.EMPTY_MESSAGE);
   }
}
 
