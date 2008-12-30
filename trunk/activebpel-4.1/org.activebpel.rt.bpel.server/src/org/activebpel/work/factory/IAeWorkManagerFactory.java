// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/work/factory/IAeWorkManagerFactory.java,v 1.1 2007/07/31 20:54:2
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
package org.activebpel.work.factory;

import commonj.work.WorkManager;

import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.work.input.IAeInputMessageWorkManager;

/**
 * Defines the interface for the factory that provides the CommonJ
 * <code>WorkManager</code> and the {@link IAeInputMessageWorkManager} (input
 * message work manager) to the engine.
 */
public interface IAeWorkManagerFactory
{
   /**
    * Initializes the work manager factory from the engine configuration.
    *
    * @param aConfigMap
    */
   public void init(Map aConfigMap) throws AeException;

   /**
    * Returns the CommonJ <code>WorkManager</code> for scheduling CommonJ
    * <code>Work</code>.
    */
   public WorkManager getWorkManager() throws AeException;

   /**
    * Returns <code>true</code> if and only if the work manager returned by
    * {@link #getWorkManager()} is our internal implementation.
    */
   public boolean isInternalWorkManager() throws AeException;

   /**
    * Returns the {@link IAeInputMessageWorkManager} for scheduling input
    * message work.
    */
   public IAeInputMessageWorkManager getInputMessageWorkManager() throws AeException;
}
