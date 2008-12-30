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

import org.activebpel.rt.bpel.IAeContextWSDLProvider;
import org.activebpel.rt.bpel.IAeFaultTypeInfo;

/**
 * interface for the fault matching rules
 */
public interface IAeFaultMatchingRule
{
   /**
    * Return a match if the given fault handler and the fault data matches.
    * @param aProvider
    * @param aCatch
    * @param aFault
    * @return IAeMatch if it is a match, otherwise return null
    */
   public IAeMatch getMatch(IAeContextWSDLProvider aProvider, IAeCatch aCatch, IAeFaultTypeInfo aFault);

   /**
    * Set the priority of this match
    * @param aPriority
    */
   public void setPriority(int aPriority);

}
