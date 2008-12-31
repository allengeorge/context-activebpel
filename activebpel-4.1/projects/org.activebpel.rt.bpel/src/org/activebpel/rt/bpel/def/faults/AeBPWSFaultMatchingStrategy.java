//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/faults/AeBPWSFaultMatchingStrategy.java,v 1.2 2006/09/22 22:20:2
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

/**
 * Fault matching strategy for BPEL4WS 1.1
 */
public class AeBPWSFaultMatchingStrategy extends AeBaseFaultMatchingStrategy
{
   /** The rules to execute for 1.1 fault matching. Order isn't important here since there is only 1 rule that is not a "best match". */
   private static final IAeFaultMatchingRule[] RULES = 
   {
      new AeFaultNameOnly(),
      new AeFaultNameAndData(),
      new AeVariableOnly()
   };
   
   /**
    * Initialize the rule's priority.  
    */
   static
   {
      for (int i = 0; i < RULES.length; i++)
      {
         RULES[i].setPriority(i);
      }
   }
   /**
    * @see org.activebpel.rt.bpel.def.faults.AeBaseFaultMatchingStrategy#getRules()
    */
   protected IAeFaultMatchingRule[] getRules()
   {
      return RULES;
   }
} 
