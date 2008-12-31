//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/preprocess/strategies/wsio/AeBPWSMessageDataStrategyMatcher.java,v 1.2 2006/09/11 23:06:2
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
 * Contains the valid strategy patterns for BPEL4WS 1.1 
 * 
 * The only valid message data consumer or producer strategy for 1.1 is the message-variable
 * strategy. The base class provides this strategy so there's really nothing here.
 */
public class AeBPWSMessageDataStrategyMatcher extends AeBaseMessageDataStrategyMatcher
{
   /**
    * Ctor
    */
   public AeBPWSMessageDataStrategyMatcher()
   {
   }
}
 
