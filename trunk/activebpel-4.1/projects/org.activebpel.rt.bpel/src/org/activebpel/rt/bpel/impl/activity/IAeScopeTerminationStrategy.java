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
package org.activebpel.rt.bpel.impl.activity;

import org.activebpel.rt.bpel.AeBusinessProcessException;

/**
 *  BPEL4WS 1.1 and WS-BPEL have different strategies for terminating a scope.
 *  
 *  The 1.1 version will raise a bpws:forcedTermination fault
 *  The 2.0 version will execute a terminationHandler
 */
public interface IAeScopeTerminationStrategy
{
   /**
    * Called from the scope's <code>terminate()</code> in order to start the termination process
    * @param aImpl
    * @throws AeBusinessProcessException 
    */
   public void onStartTermination(AeActivityScopeImpl aImpl) throws AeBusinessProcessException;
   
   /**
    * Called when all of the scope's children are terminated and it's time to process the termination behavior
    * @param aImpl
    * @throws AeBusinessProcessException 
    */
   public void onHandleTermination(AeActivityScopeImpl aImpl) throws AeBusinessProcessException;
}
