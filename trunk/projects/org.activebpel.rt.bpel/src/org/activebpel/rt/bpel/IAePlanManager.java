// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAePlanManager.java,v 1.3 2004/10/08 14:01:2
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
package org.activebpel.rt.bpel;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.impl.IAeProcessPlan;

/**
 * Provides a mechanism to map/cache a process QName to its description which
 * contains the required information to dispatch an inbound request to a
 * specific process instance or create a new process. 
 */
public interface IAePlanManager
{
   /**
    * Looks up the process plan for a process by its QName. 
    * @param aProcessName
    * @throws AeBusinessProcessException if the plan is not found
    */
   public IAeProcessPlan findCurrentPlan(QName aProcessName) throws AeBusinessProcessException;
}
