//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/IAeEngineLifecycleWrapper.java,v 1.1 2005/06/20 20:14:3
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
package org.activebpel.rt.bpel.server.engine;

import org.activebpel.rt.AeException;

/**
 * Interface for controlling interactions with the BPEL engine from the 
 * servlet entry point.
 */
public interface IAeEngineLifecycleWrapper
{
   /**
    * Initialize the engine and any required resources.
    * @throws AeException
    */
   public void init() throws AeException;
   
   /**
    * Kick off the sequence that is responsible for starting the engine.
    * NOTE: this call may return immediately without the engine having
    * actually been started.
    * @throws AeException
    */
   public void start() throws AeException;
   
   /**
    * Stop the engine and release any associated resources.
    * @throws AeException
    */
   public void stop() throws AeException;

}
