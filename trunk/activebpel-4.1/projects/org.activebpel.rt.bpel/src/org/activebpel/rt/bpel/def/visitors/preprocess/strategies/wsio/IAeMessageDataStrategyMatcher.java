//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/preprocess/strategies/wsio/IAeMessageDataStrategyMatcher.java,v 1.1 2006/08/18 22:20:3
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

import org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef;
import org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef;

/**
 * Interface for matching a def to the strategy used to produce message data
 */
public interface IAeMessageDataStrategyMatcher
{
   /**
    * Matches the attributes and child elements for the def to one of the 
    * prescribed message producer strategies. If there is no match found
    * then this is not a legal construct. 
    * @param aDef
    * @return strategy name or null if not a valid def
    */
   public String getProducerStrategy(IAeMessageDataProducerDef aDef);

   /**
    * Matches the attributes and child elements for the def to one of the 
    * prescribed message producer strategies. If there is no match found
    * then this is not a legal construct. 
    * @param aDef
    * @return strategy name or null if not a valid def
    */
   public String getConsumerStrategy(IAeMessageDataConsumerDef aDef);
}
 
