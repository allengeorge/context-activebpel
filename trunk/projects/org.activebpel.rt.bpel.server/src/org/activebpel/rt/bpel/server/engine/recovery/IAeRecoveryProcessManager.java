// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/IAeRecoveryProcessManager.java,v 1.1 2005/07/12 00:27:0
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
package org.activebpel.rt.bpel.server.engine.recovery;

import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.impl.IAeProcessManager;

/**
 * Extends {@link org.activebpel.rt.bpel.impl.IAeProcessManager} to define the
 * interface for a process manager used by an instance of {@link
 * org.activebpel.rt.bpel.server.engine.recovery.AeRecoveryEngine} during
 * recovery.
 */
public interface IAeRecoveryProcessManager extends IAeProcessManager
{
   /**
    * Sets the process that is being recovered.
    */
   public void setRecoveryProcess(IAeBusinessProcess aRecoveryProcess);
}
