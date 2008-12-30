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

import java.util.Iterator;

import org.activebpel.rt.bpel.IAeContextWSDLProvider;
import org.activebpel.rt.bpel.IAeFaultTypeInfo;

/**
 * Determines which fault handler is capable of catching a fault. The fault
 * matching rules differ between bpws and wsbpel.
 */
public interface IAeFaultMatchingStrategy
{
   /**
    * Select ths catch that is the best match for the given fault.
    * @param aProvider
    * @param aIterOfCatches
    * @param aFault
    * @return IAeCatch or null. In the case of null, the catchAll or implicit fault handler will handle the fault.
    */
   public IAeCatch selectMatchingCatch(IAeContextWSDLProvider aProvider, Iterator aIterOfCatches, IAeFaultTypeInfo aFault);
}
