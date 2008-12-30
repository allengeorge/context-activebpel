//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/IAeCopyStrategy.java,v 1.2 2006/06/26 16:50:3
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
package org.activebpel.rt.bpel.impl.activity.assign; 

import org.activebpel.rt.bpel.impl.AeBpelException;

/**
 * Provides the interface for copying data returned from a <from> impl to a <to> impl 
 */
public interface IAeCopyStrategy
{
   /**
    * Copies the data from the from impl to the destination
    * @param aCopyOperation - provided as a context for the copy 
    * @param aFromData
    * @param aToData
    * @throws AeBpelException
    */
   public void copy(IAeCopyOperation aCopyOperation, Object aFromData, Object aToData) throws AeBpelException;
}
 
