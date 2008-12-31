//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/faults/AeFaultMatchingStrategyFactory.java,v 1.1 2006/09/11 23:06:2
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
package org.activebpel.rt.bpel.def.faults; 

import org.activebpel.rt.bpel.def.IAeBPELConstants;

/**
 * Strategy that implements the fault matching rules for the version of BPEL we're executing/validating 
 */
public class AeFaultMatchingStrategyFactory
{
   /** strategy for fault matching for a 1.1 scope */
   private static final IAeFaultMatchingStrategy BPEL4WS_FaultMatchingStrategy = new AeBPWSFaultMatchingStrategy();

   /** strategy for fault matching for a 2.0 scope */
   private static final IAeFaultMatchingStrategy WSBPEL_FaultMatchingStrategy = new AeWSBPELFaultMatchingStrategy();

   /**
    * Returns an instance of the strategy for the given namespace
    * @param aBPELNamespace
    */
   public static IAeFaultMatchingStrategy getInstance(String aBPELNamespace)
   {
      if (aBPELNamespace.equals(IAeBPELConstants.BPWS_NAMESPACE_URI))
      {
         return BPEL4WS_FaultMatchingStrategy;
      }
      else
      {
         return WSBPEL_FaultMatchingStrategy;
      }
   }
}
 
