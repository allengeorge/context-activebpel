// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/process/IAeProcessWrapperMap.java,v 1.4 2006/06/15 18:45:1
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
package org.activebpel.rt.bpel.server.engine.process;

import org.activebpel.rt.bpel.IAeBusinessProcess;

/**
 * Defines interface used by persistent process manager to map process ids to
 * process wrappers (which are instances of {@link
 * org.activebpel.rt.bpel.server.engine.process.AeProcessWrapper}.
 */
public interface IAeProcessWrapperMap
{
   /**
    * Returns the current process wrapper for the given process id or
    * <code>null</code> if there is no process wrapper for the given process id.
    * Does not increment the wrapper's reference count the way {@link
    * #getWrapper(long)} does, so does not require a matching call to {@link
    * #releaseWrapper(AeProcessWrapper)}. However, this means that successive
    * calls to {@link #getCurrentWrapper(long)} with the same process id may
    * return different process wrappers.
    *
    * @param aProcessId
    */
   public AeProcessWrapper getCurrentWrapper(long aProcessId);

   /**
    * Returns an empty process wrapper. It manufactures a unique id for the map.
    * This call should be followed by a call to set the process id.
    * <em>Each call to {@link #getWrapper()} or {@link #getWrapper(long)} must be 
    * followed eventually by a matching call to {@link #releaseWrapper(AeProcessWrapper)}</em>.
    */
   public AeProcessWrapper getWrapper();

   /**
    * Returns process wrapper for the specified process id. Adds a process
    * wrapper for the process if one does not yet exist. <em>Each call to
    * {@link #getWrapper(long)} must be followed eventually by a matching call
    * to {@link #releaseWrapper(AeProcessWrapper)}</em>.
    *
    * @param aProcessId
    */
   public AeProcessWrapper getWrapper(long aProcessId);

   /**
    * Releases a process wrapper allocated by {@link #getWrapper(long)}, if
    * there are no more references to the process.
    *
    * @param aWrapper
    */
   public void releaseWrapper(AeProcessWrapper aWrapper);

   /**
    * Sets the callback object for this process wrapper map.
    */
   public void setCallback(IAeProcessWrapperMapCallback aCallback);

   /**
    * Waits until this map is empty.
    */
   public void waitUntilEmpty();

   /** 
    * Sets the passed wrappers process to point to the passed process and updates
    * the map to point to the new process id.
    * @param aWrapper
    * @param aProcess
    */
   public void setProcessWrapperProcess(AeProcessWrapper aWrapper, IAeBusinessProcess aProcess);
}
