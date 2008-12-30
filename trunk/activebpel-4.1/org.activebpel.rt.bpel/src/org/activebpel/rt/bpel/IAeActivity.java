// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAeActivity.java,v 1.4 2005/12/06 22:27:1
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

import org.activebpel.rt.bpel.impl.IAeBpelObject;

/** Describes the interface used for interacting with business process activities */
public interface IAeActivity extends IAeBpelObject
{
   /**
    * Terminates the activity without the activity completing abnormally or 
    * executing its fault handler. This is used for the root activity within
    * a loop activity that gets terminated as a result of a continue or break
    * activity executing within the loop. 
    * @throws AeBusinessProcessException
    */
   public void terminateEarly() throws AeBusinessProcessException;
}
