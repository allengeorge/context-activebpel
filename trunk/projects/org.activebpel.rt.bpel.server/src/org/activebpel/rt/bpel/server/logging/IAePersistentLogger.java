// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/logging/IAePersistentLogger.java,v 1.4 2005/04/13 17:26:4
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
package org.activebpel.rt.bpel.server.logging;

import org.activebpel.rt.bpel.server.engine.IAeProcessLogger;

/**
 * Interface for handling logging in a persistent environment.  
 */
public interface IAePersistentLogger extends IAeProcessLogger
{
   /**
    * Returns the current log entry from the logger.
    * @param aProcessId
    */
   public IAeProcessLogEntry getLogEntry(long aProcessId);
}
